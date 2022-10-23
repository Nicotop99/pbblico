package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pubmania.Pubblico.String.StringNotifiche;
import com.pubmania.Pubblico.String.StringRecensione;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static com.pubmania.Pubblico.FaiUnaRecensione.emailPub;
import static com.pubmania.Pubblico.FaiUnaRecensione.idPost;
import static com.pubmania.Pubblico.FaiUnaRecensione.token;

public class FaiUnaRecensione2 extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String email,fotoCliente;
    String miPicc = "";
    int valStruttura,valProdotti,valServizio,valBagni,valQuantitaPersone,valRagazze,valRagazzi,valPrezzi,valDivertimento = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fai_una_recensione2 );
        email = "nicolino.oliverio@gmail.com";
        checkMiPiacciono();
        setTitle();
        setStrutture();
        setProdotti();
        setServizio();
        setBagni();
        setQuantitagente();
        setRagazze();
        setRagazzi();
        setPrezzi();
        setDivertimento();
        setRecensisci();
        firebaseFirestore.collection("Pubblico").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString("email").equals(email)){
                           uriProfilo = documentSnapshot.getString("fotoProfilo");
                           fotoCliente = documentSnapshot.getString("fotoProfilo");
                           nomeCognome = documentSnapshot.getString("nome") + " " + documentSnapshot.getString("cognome");
                        }
                    }
                }
            }
        });
    }

    String nomeCognome,uriProfilo;
    ImageView faiRecensione;
    ArrayList<String> arrayUrl = new ArrayList<>();
    Group group1,group2;
    int d = 0;
    int countMedia,countTot,media;
    ConstraintLayout strutura,prodotti,servizio,bagni,quantitaGente,ragazze,regazzi,prezzi,divertimento;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAbWZozX0:APA91bElDXMdAF898t_M5ai0z5cCTkG9po-deDqqirLm5zL9FI_UgxdQtlUdH0k7fToZIClrylH5LXbEZeVXsXpbr1rpYj6FpD20mFTLOVot-YbiYjhSf85Ca7qbHI9zzCCh0nCktwYF";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    String TOPIC;
    private void propvaNotifica(String token, String idPosttt) {

        TOPIC = "/topics/userABC"; //topic must match with what the receiver subscribed to
        Log.d("jknkjandjan",token);

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", getString(R.string.nuovarecensioneda) + " " + nomeCognome );
            notifcationBody.put("message", getString(R.string.cliccalanotificaperidettagli));
            notifcationBody.put("tipo","Recensione");
            notifcationBody.put("idPost",idPosttt);
            notification.put("to", token);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e("adlskfdlsklfsd", "onCreate: " + e.getMessage() );
        }
        sendNotification(notification,idPosttt,token);




    }
boolean entrato = false;
    private void sendNotification(JSONObject notification,String idPostt,String tok) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getString("failure").equals("1")){
                                Log.d("jkfnakjsdnfkjsadn",emailPub);
                                // token non valido
                                firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if (documentSnapshot.getString("email").equals(emailPub)){
                                                    DocumentReference d  = firebaseFirestore.collection("Professionisti").document(documentSnapshot.getId());
                                                    ArrayList<String > a = (ArrayList<String>) documentSnapshot.get("token");
                                                    Log.d("njanfd", String.valueOf(a.size()));
                                                    for (int i = 0;i<a.size();i++){
                                                        if(a.get(i).equals(tok)){
                                                            a.remove(i);
                                                        }
                                                    }
                                                    d.update("token",a).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Log.d("onjlm","ok");
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d("onjlm","ok" + e.getMessage());

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (entrato == false) {
                            StringNotifiche stringNotifiche = new StringNotifiche();
                            stringNotifiche.setCategoria("Recensione");
                            stringNotifiche.setVisualizzato("false");
                            stringNotifiche.setEmailCliente(email);
                            stringNotifiche.setEmailPub(emailPub);
                            stringNotifiche.setFotoProfilo(fotoCliente);
                            stringNotifiche.setIdPost(idPostt);
                            stringNotifiche.setNomecognomeCliente(nomeCognome);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());
                            stringNotifiche.setOra(currentDateandTime);
                            firebaseFirestore.collection(emailPub + "Notifiche").add(stringNotifiche)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                DocumentReference documentReference = task.getResult();
                                                documentReference.update("id", task.getResult().getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        startActivity(new Intent(getApplicationContext(), ProfiloPubblico.class));
                                                        finish();
                                                    }
                                                });
                                            }
                                        }
                                    });
                            entrato = true;
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        for (int i = 0;i<token.size();i++){
                            propvaNotifica(token.get(i),idPost);
                            Log.d("llllll",token.get(i));
                        }
                        Log.d("onfljdsnfl",error.getMessage() + " ciao");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
    private void setRecensisci() {




        strutura = (ConstraintLayout) findViewById(R.id.cStruttura);
        prodotti = (ConstraintLayout) findViewById(R.id.cProdotti);
        servizio = (ConstraintLayout) findViewById(R.id.cServizi);
        bagni = (ConstraintLayout) findViewById(R.id.cBagni);
        quantitaGente = (ConstraintLayout) findViewById(R.id.cQuantitaGente);
        ragazze = (ConstraintLayout) findViewById(R.id.cRagazze);
        regazzi = (ConstraintLayout) findViewById(R.id.cRagazzi);
        prezzi = (ConstraintLayout) findViewById(R.id.cPrezzi);
        divertimento = (ConstraintLayout) findViewById(R.id.cDivertineto);
        faiRecensione = (ImageView) findViewById(R.id.imageView54);
        group1 = (Group) findViewById(R.id.group1);
        group2 = (Group) findViewById(R.id.group2);
        faiRecensione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valStruttura > 0) {
                    countMedia += 1;
                    countTot += valStruttura;
                }
                if (valProdotti > 0) {
                    countMedia += 1;
                    countTot += valProdotti;
                }
                if (valServizio > 0) {
                    countMedia += 1;
                    countTot += valServizio;
                }
                if (valBagni > 0) {
                    countMedia += 1;
                    countTot += valBagni;
                }
                if (valQuantitaPersone > 0) {
                    countMedia += 1;
                    countTot += valQuantitaPersone;
                }
                if (valRagazze > 0) {
                    countMedia += 1;
                    countTot += valRagazze;
                }
                if (valRagazzi > 0) {
                    countMedia += 1;
                    countTot += valRagazzi;
                }
                if (valPrezzi > 0) {
                    countMedia += 1;
                    countTot += valPrezzi;
                }
                if (valDivertimento > 0) {
                    countMedia += 1;
                    countTot += valDivertimento;
                }

                if(valStruttura == 0){
                    strutura.setBackgroundResource(R.drawable.red_recange);
                } if(valProdotti == 0){
                    prodotti.setBackgroundResource(R.drawable.red_recange);
                } if(valServizio == 0){
                    servizio.setBackgroundResource(R.drawable.red_recange);
                } if(valBagni == 0){
                    bagni.setBackgroundResource(R.drawable.red_recange);
                } if(valQuantitaPersone == 0){
                    quantitaGente.setBackgroundResource(R.drawable.red_recange);
                }
                 if(valRagazzi == 0 && valRagazze == 0){
                    ragazze.setBackgroundResource(R.drawable.red_recange);
                    regazzi.setBackgroundResource(R.drawable.red_recange);
                }
                 if(valPrezzi == 0){
                    prezzi.setBackgroundResource(R.drawable.red_recange);
                }
                 if(valDivertimento == 0){
                    divertimento.setBackgroundResource(R.drawable.red_recange);
                }
                if(countMedia > 8 ) {

                    group1.setVisibility(View.GONE);
                    group2.setVisibility(View.VISIBLE);
                    if (FaiUnaRecensione.arrayUri.size() > 0) {
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference storageReference = firebaseStorage.getReference();
                        StorageReference storageReference1 = storageReference.child(email + "/" + UUID.randomUUID().toString());
                        for (int i = 0; i < FaiUnaRecensione.arrayUri.size(); i++) {
                            storageReference1.putFile(Uri.parse(FaiUnaRecensione.arrayUri.get(i))).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    d += 1;
                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            arrayUrl.add(String.valueOf(uri));
                                            if (arrayUrl.size() == FaiUnaRecensione.arrayUri.size()) {
                                                StringRecensione stringRecensione = new StringRecensione();
                                                stringRecensione.setDesc(FaiUnaRecensione.desc);
                                                stringRecensione.setEmailpub(FaiUnaRecensione.emailPub);
                                                stringRecensione.setTitolo(FaiUnaRecensione.titolo);
                                                stringRecensione.setValDivertimento(String.valueOf(valDivertimento));
                                                stringRecensione.setValBagni(String.valueOf(valBagni));
                                                stringRecensione.setValStruttura(String.valueOf(valStruttura));
                                                stringRecensione.setValProdotti(String.valueOf(valProdotti));
                                                stringRecensione.setValServizio(String.valueOf(valServizio));
                                                stringRecensione.setValQuantitaGente(String.valueOf(valQuantitaPersone));
                                                stringRecensione.setValRagazze(String.valueOf(valRagazze));
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                                                String currentDateandTime = sdf.format(new Date());
                                                stringRecensione.setOra(currentDateandTime);
                                                stringRecensione.setValRagazzi(String.valueOf(valRagazzi));
                                                stringRecensione.setValPrezzi(String.valueOf(valPrezzi));
                                                stringRecensione.setEmailPubblico(email);



                                                media = countTot / countMedia;
                                                stringRecensione.setMedia(String.valueOf(media));
                                                String[] arr = arrayUrl.toArray(new String[arrayUrl.size()]);
                                                List<String> listIngg = Arrays.asList(arr);

                                                stringRecensione.setArrayList(listIngg);
                                                firebaseFirestore.collection(FaiUnaRecensione.emailPub + "Rec").add(stringRecensione).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if (task.isSuccessful()) {
                                                            Map<String, Object> user = new HashMap<>();
                                                            user.put("idPost", task.getResult().getId());
                                                            firebaseFirestore.collection(email + "recensioni").add(user);
                                                            DocumentReference documentReference = firebaseFirestore.collection(FaiUnaRecensione.emailPub + "Rec").document(task.getResult().getId());
                                                            documentReference.update("id", task.getResult().getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> taskkk) {
                                                                    DocumentReference documentReference1 = firebaseFirestore.collection(email + "Rec").document(FaiUnaRecensione.idPost);
                                                                    documentReference1.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> taskk) {
                                                                            for (int i = 0;i<token.size();i++){
                                                                                propvaNotifica(token.get(i),task.getResult().getId());
                                                                                Log.d("llllllll",token.get(i));

                                                                            }
                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            group2.setVisibility(View.GONE);
                                                                            group1.setVisibility(View.VISIBLE);
                                                                            Toast.makeText(getApplicationContext(), getString(R.string.errore), Toast.LENGTH_LONG).show();
                                                                        }
                                                                    });

                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    group2.setVisibility(View.GONE);
                                                                    group1.setVisibility(View.VISIBLE);
                                                                    Toast.makeText(getApplicationContext(), getString(R.string.errore), Toast.LENGTH_LONG).show();
                                                                }
                                                            });
                                                        }
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        group2.setVisibility(View.GONE);
                                                        group1.setVisibility(View.VISIBLE);
                                                        Toast.makeText(getApplicationContext(), getString(R.string.errore), Toast.LENGTH_LONG).show();
                                                    }
                                                });

                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            group2.setVisibility(View.GONE);
                                            group1.setVisibility(View.VISIBLE);
                                            Toast.makeText(getApplicationContext(), getString(R.string.errore), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    group2.setVisibility(View.GONE);
                                    group1.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), getString(R.string.errore), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                    else {
                        StringRecensione stringRecensione = new StringRecensione();
                        stringRecensione.setDesc(FaiUnaRecensione.desc);
                        stringRecensione.setEmailpub(FaiUnaRecensione.emailPub);
                        stringRecensione.setEmailPubblico(email);
                        stringRecensione.setTitolo(FaiUnaRecensione.titolo);
                        stringRecensione.setValDivertimento(String.valueOf(valDivertimento));
                        stringRecensione.setValBagni(String.valueOf(valBagni));
                        stringRecensione.setValStruttura(String.valueOf(valStruttura));
                        stringRecensione.setValProdotti(String.valueOf(valProdotti));
                        stringRecensione.setValServizio(String.valueOf(valServizio));
                        stringRecensione.setValQuantitaGente(String.valueOf(valQuantitaPersone));
                        stringRecensione.setValRagazze(String.valueOf(valRagazze));
                        stringRecensione.setValRagazzi(String.valueOf(valRagazzi));
                        stringRecensione.setValPrezzi(String.valueOf(valPrezzi));
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        stringRecensione.setOra(currentDateandTime);
                        if (valStruttura > 0) {
                            countMedia += 1;
                            countTot += valStruttura;
                        }
                        if (valProdotti > 0) {
                            countMedia += 1;
                            countTot += valProdotti;
                        }
                        if (valServizio > 0) {
                            countMedia += 1;
                            countTot += valServizio;
                        }
                        if (valBagni > 0) {
                            countMedia += 1;
                            countTot += valBagni;
                        }
                        if (valQuantitaPersone > 0) {
                            countMedia += 1;
                            countTot += valQuantitaPersone;
                        }
                        if (valRagazze > 0) {
                            countMedia += 1;
                            countTot += valRagazze;
                        }
                        if (valRagazzi > 0) {
                            countMedia += 1;
                            countTot += valRagazzi;
                        }
                        if (valPrezzi > 0) {
                            countMedia += 1;
                            countTot += valPrezzi;
                        }
                        if (valDivertimento > 0) {
                            countMedia += 1;
                            countTot += valDivertimento;
                        }


                        media = countTot / countMedia;
                        stringRecensione.setMedia(String.valueOf(media));
                        Log.d("nfdjklsnfsf", String.valueOf(media));
                        firebaseFirestore.collection(FaiUnaRecensione.emailPub + "Rec").add(stringRecensione).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    DocumentReference documentReference = firebaseFirestore.collection(FaiUnaRecensione.emailPub + "Rec").document(task.getResult().getId());
                                    documentReference.update("id", task.getResult().getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task2) {
                                            DocumentReference documentReference1 = firebaseFirestore.collection(email + "Rec").document(FaiUnaRecensione.idPost);
                                            documentReference1.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> tas1k) {


                                                    for (int i = 0;i<token.size();i++){
                                                        propvaNotifica(token.get(i),task.getResult().getId());
                                                        Log.d("llllllll",token.get(i));
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    group2.setVisibility(View.GONE);
                                                    group1.setVisibility(View.VISIBLE);
                                                    Toast.makeText(getApplicationContext(), getString(R.string.errore), Toast.LENGTH_LONG).show();
                                                }
                                            });

                                        }
                                    });
                                }
                            }
                        });

                    }
                }
            }
        });
    }





    ImageView uno8,due8,tre8,quattro8,cinque8;
    private void setDivertimento() {
        uno8 = (ImageView) findViewById(R.id.imageView48467);
        due8 = (ImageView) findViewById(R.id.imageView49467);
        tre8 = (ImageView) findViewById(R.id.imageView50467);
        quattro8 = (ImageView) findViewById(R.id.imageView516467);
        cinque8 = (ImageView) findViewById(R.id.imageView52467);

        uno8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valDivertimento = 1;
                divertimento.setBackgroundResource(0);

                uno8.setImageResource(R.drawable.recensione_si);
                due8.setImageResource(R.drawable.recensione_no);
                tre8.setImageResource(R.drawable.recensione_no);
                quattro8.setImageResource(R.drawable.recensione_no);
                cinque8.setImageResource(R.drawable.recensione_no);
            }
        });
        due8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valDivertimento = 2;
                divertimento.setBackgroundResource(0);


                uno8.setImageResource(R.drawable.recensione_si);
                due8.setImageResource(R.drawable.recensione_si);
                tre8.setImageResource(R.drawable.recensione_no);
                quattro8.setImageResource(R.drawable.recensione_no);
                cinque8.setImageResource(R.drawable.recensione_no);
            }
        });
        tre8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valDivertimento = 3;
                divertimento.setBackgroundResource(0);


                uno8.setImageResource(R.drawable.recensione_si);
                due8.setImageResource(R.drawable.recensione_si);
                tre8.setImageResource(R.drawable.recensione_si);
                quattro8.setImageResource(R.drawable.recensione_no);
                cinque8.setImageResource(R.drawable.recensione_no);
            }
        });
        quattro8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valDivertimento = 4;
                divertimento.setBackgroundResource(0);

                uno8.setImageResource(R.drawable.recensione_si);
                due8.setImageResource(R.drawable.recensione_si);
                tre8.setImageResource(R.drawable.recensione_si);
                quattro8.setImageResource(R.drawable.recensione_si);
                cinque8.setImageResource(R.drawable.recensione_no);
            }
        });
        cinque8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valDivertimento = 5;
                divertimento.setBackgroundResource(0);
                uno8.setImageResource(R.drawable.recensione_si);
                due8.setImageResource(R.drawable.recensione_si);
                tre8.setImageResource(R.drawable.recensione_si);
                quattro8.setImageResource(R.drawable.recensione_si);
                cinque8.setImageResource(R.drawable.recensione_si);
            }
        });
    }

    ImageView uno7,due7,tre7,quattro7,cinque7;
    private void setPrezzi() {
        uno7 = (ImageView) findViewById(R.id.imageView4846);
        due7 = (ImageView) findViewById(R.id.imageView4946);
        tre7 = (ImageView) findViewById(R.id.imageView5046);
        quattro7 = (ImageView) findViewById(R.id.imageView51646);
        cinque7 = (ImageView) findViewById(R.id.imageView5246);

        uno7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valPrezzi = 1;
                prezzi.setBackgroundResource(0);
                uno7.setImageResource(R.drawable.recensione_si);
                due7.setImageResource(R.drawable.recensione_no);
                tre7.setImageResource(R.drawable.recensione_no);
                quattro7.setImageResource(R.drawable.recensione_no);
                cinque7.setImageResource(R.drawable.recensione_no);
            }
        });
        due7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valPrezzi = 2;                prezzi.setBackgroundResource(0);

                uno7.setImageResource(R.drawable.recensione_si);
                due7.setImageResource(R.drawable.recensione_si);
                tre7.setImageResource(R.drawable.recensione_no);
                quattro7.setImageResource(R.drawable.recensione_no);
                cinque7.setImageResource(R.drawable.recensione_no);
            }
        });
        tre7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valPrezzi = 3;                prezzi.setBackgroundResource(0);

                uno7.setImageResource(R.drawable.recensione_si);
                due7.setImageResource(R.drawable.recensione_si);
                tre7.setImageResource(R.drawable.recensione_si);
                quattro7.setImageResource(R.drawable.recensione_no);
                cinque7.setImageResource(R.drawable.recensione_no);
            }
        });
        quattro7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valPrezzi = 4;                prezzi.setBackgroundResource(0);

                uno7.setImageResource(R.drawable.recensione_si);
                due7.setImageResource(R.drawable.recensione_si);
                tre7.setImageResource(R.drawable.recensione_si);
                quattro7.setImageResource(R.drawable.recensione_si);
                cinque7.setImageResource(R.drawable.recensione_no);
            }
        });
        cinque7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valPrezzi = 5;                prezzi.setBackgroundResource(0);

                uno7.setImageResource(R.drawable.recensione_si);
                due7.setImageResource(R.drawable.recensione_si);
                tre7.setImageResource(R.drawable.recensione_si);
                quattro7.setImageResource(R.drawable.recensione_si);
                cinque7.setImageResource(R.drawable.recensione_si);
            }
        });
    }

    ImageView uno6,due6,tre6,quattro6,cinque6;
    TextView textView;
    ConstraintLayout maschileLayout,femminileLayout;

    private void setRagazze() {
        uno6 = (ImageView) findViewById(R.id.imageView4845);
        due6 = (ImageView) findViewById(R.id.imageView4945);
        tre6 = (ImageView) findViewById(R.id.imageView5045);
        quattro6 = (ImageView) findViewById(R.id.imageView5145);
        cinque6 = (ImageView) findViewById(R.id.imageView5245);
        textView = (TextView) findViewById(R.id.textView6933345);
        maschileLayout = (ConstraintLayout) findViewById(R.id.cRagazzi);
        femminileLayout = (ConstraintLayout) findViewById(R.id.cRagazze);
        firebaseFirestore.collection("Pubblico").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        if(documentSnapshot.getString("email").equals(email)){
                            miPicc = documentSnapshot.getString("miPiacciono");
                            if(miPicc.equals(getString(R.string.maschile))){
                                maschileLayout.setVisibility(View.VISIBLE);
                                femminileLayout.setVisibility(View.GONE);
                            }else if(miPicc.equals(getString(R.string.entrambe))){
                                maschileLayout.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }
        });


        uno6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valRagazze = 1;
                ragazze.setBackgroundResource(0);

                uno6.setImageResource(R.drawable.recensione_si);
                due6.setImageResource(R.drawable.recensione_no);
                tre6.setImageResource(R.drawable.recensione_no);
                quattro6.setImageResource(R.drawable.recensione_no);
                cinque6.setImageResource(R.drawable.recensione_no);
            }
        });
        due6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valRagazze = 2;

                ragazze.setBackgroundResource(0);

                uno6.setImageResource(R.drawable.recensione_si);
                due6.setImageResource(R.drawable.recensione_si);
                tre6.setImageResource(R.drawable.recensione_no);
                quattro6.setImageResource(R.drawable.recensione_no);
                cinque6.setImageResource(R.drawable.recensione_no);
            }
        });
        tre6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valRagazze = 3;
                ragazze.setBackgroundResource(0);

                uno6.setImageResource(R.drawable.recensione_si);
                due6.setImageResource(R.drawable.recensione_si);
                tre6.setImageResource(R.drawable.recensione_si);
                quattro6.setImageResource(R.drawable.recensione_no);
                cinque6.setImageResource(R.drawable.recensione_no);
            }
        });
        quattro6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valRagazze = 4;
                ragazze.setBackgroundResource(0);

                uno6.setImageResource(R.drawable.recensione_si);
                due6.setImageResource(R.drawable.recensione_si);
                tre6.setImageResource(R.drawable.recensione_si);
                quattro6.setImageResource(R.drawable.recensione_si);
                cinque6.setImageResource(R.drawable.recensione_no);
            }
        });
        cinque6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valRagazze = 5;
                ragazze.setBackgroundResource(0);

                uno6.setImageResource(R.drawable.recensione_si);
                due6.setImageResource(R.drawable.recensione_si);
                tre6.setImageResource(R.drawable.recensione_si);
                quattro6.setImageResource(R.drawable.recensione_si);
                cinque6.setImageResource(R.drawable.recensione_si);
            }
        });


    }

    ImageView uno10,due10,tre10,quattro10,cinque10;
    private void setRagazzi(){
        uno10 = (ImageView) findViewById(R.id.imageView48459);
        due10 = (ImageView) findViewById(R.id.imageView49459);
        tre10 = (ImageView) findViewById(R.id.imageView50459);
        quattro10 = (ImageView) findViewById(R.id.imageView51459);
        cinque10 = (ImageView) findViewById(R.id.imageView52459);

        uno10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valRagazzi = 1;
                regazzi.setBackgroundResource(0);

                uno10.setImageResource(R.drawable.recensione_si);
                due10.setImageResource(R.drawable.recensione_no);
                tre10.setImageResource(R.drawable.recensione_no);
                quattro10.setImageResource(R.drawable.recensione_no);
                cinque10.setImageResource(R.drawable.recensione_no);
            }
        });
        due10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valRagazzi = 2;
                regazzi.setBackgroundResource(0);

                uno10.setImageResource(R.drawable.recensione_si);
                due10.setImageResource(R.drawable.recensione_si);
                tre10.setImageResource(R.drawable.recensione_no);
                quattro10.setImageResource(R.drawable.recensione_no);
                cinque10.setImageResource(R.drawable.recensione_no);
            }
        });
        tre10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valRagazzi = 3;
                regazzi.setBackgroundResource(0);

                uno10.setImageResource(R.drawable.recensione_si);
                due10.setImageResource(R.drawable.recensione_si);
                tre10.setImageResource(R.drawable.recensione_si);
                quattro10.setImageResource(R.drawable.recensione_no);
                cinque10.setImageResource(R.drawable.recensione_no);
            }
        });
        quattro10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regazzi.setBackgroundResource(0);
                valRagazzi = 4;

                uno10.setImageResource(R.drawable.recensione_si);
                due10.setImageResource(R.drawable.recensione_si);
                tre10.setImageResource(R.drawable.recensione_si);
                quattro10.setImageResource(R.drawable.recensione_si);
                cinque10.setImageResource(R.drawable.recensione_no);
            }
        });
        cinque10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valRagazzi = 5;
                regazzi.setBackgroundResource(0);

                uno10.setImageResource(R.drawable.recensione_si);
                due10.setImageResource(R.drawable.recensione_si);
                tre10.setImageResource(R.drawable.recensione_si);
                quattro10.setImageResource(R.drawable.recensione_si);
                cinque10.setImageResource(R.drawable.recensione_si);
            }
        });

    }

    ImageView uno5,due5,tre5,quattro5,cinque5;
    private void setQuantitagente() {
        uno5 = (ImageView) findViewById(R.id.imageView484);
        due5 = (ImageView) findViewById(R.id.imageView494);
        tre5 = (ImageView) findViewById(R.id.imageView504);
        quattro5 = (ImageView) findViewById(R.id.imageView514);
        cinque5 = (ImageView) findViewById(R.id.imageView524);

        uno5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valQuantitaPersone = 1;
                quantitaGente.setBackgroundResource(0);

                uno5.setImageResource(R.drawable.recensione_si);
                due5.setImageResource(R.drawable.recensione_no);
                tre5.setImageResource(R.drawable.recensione_no);
                quattro5.setImageResource(R.drawable.recensione_no);
                cinque5.setImageResource(R.drawable.recensione_no);
            }
        });
        due5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valQuantitaPersone = 2;
                quantitaGente.setBackgroundResource(0);

                uno5.setImageResource(R.drawable.recensione_si);
                due5.setImageResource(R.drawable.recensione_si);
                tre5.setImageResource(R.drawable.recensione_no);
                quattro5.setImageResource(R.drawable.recensione_no);
                cinque5.setImageResource(R.drawable.recensione_no);
            }
        });
        tre5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valQuantitaPersone = 3;
                quantitaGente.setBackgroundResource(0);

                uno5.setImageResource(R.drawable.recensione_si);
                due5.setImageResource(R.drawable.recensione_si);
                tre5.setImageResource(R.drawable.recensione_si);
                quattro5.setImageResource(R.drawable.recensione_no);
                cinque5.setImageResource(R.drawable.recensione_no);
            }
        });
        quattro5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valQuantitaPersone = 4;
                quantitaGente.setBackgroundResource(0);

                uno5.setImageResource(R.drawable.recensione_si);
                due5.setImageResource(R.drawable.recensione_si);
                tre5.setImageResource(R.drawable.recensione_si);
                quattro5.setImageResource(R.drawable.recensione_si);
                cinque5.setImageResource(R.drawable.recensione_no);
            }
        });
        cinque5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valQuantitaPersone = 5;
                quantitaGente.setBackgroundResource(0);
                uno5.setImageResource(R.drawable.recensione_si);
                due5.setImageResource(R.drawable.recensione_si);
                tre5.setImageResource(R.drawable.recensione_si);
                quattro5.setImageResource(R.drawable.recensione_si);
                cinque5.setImageResource(R.drawable.recensione_si);
            }
        });
    }

    ImageView uno4,due4,tre4,quattro4,cinque4;
    private void setBagni() {

        uno4 = (ImageView) findViewById(R.id.imageView482);
        due4 = (ImageView) findViewById(R.id.imageView492);
        tre4 = (ImageView) findViewById(R.id.imageView502);
        quattro4 = (ImageView) findViewById(R.id.imageView512);
        cinque4 = (ImageView) findViewById(R.id.imageView522);

        uno4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valBagni = 1;
                bagni.setBackgroundResource(0);
                uno4.setImageResource(R.drawable.recensione_si);
                due4.setImageResource(R.drawable.recensione_no);
                tre4.setImageResource(R.drawable.recensione_no);
                quattro4.setImageResource(R.drawable.recensione_no);
                cinque4.setImageResource(R.drawable.recensione_no);
            }
        });
        due4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valBagni = 2;
                bagni.setBackgroundResource(0);
                uno4.setImageResource(R.drawable.recensione_si);
                due4.setImageResource(R.drawable.recensione_si);
                tre4.setImageResource(R.drawable.recensione_no);
                quattro4.setImageResource(R.drawable.recensione_no);
                cinque4.setImageResource(R.drawable.recensione_no);
            }
        });
        tre4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valBagni = 3;
                bagni.setBackgroundResource(0);
                uno4.setImageResource(R.drawable.recensione_si);
                due4.setImageResource(R.drawable.recensione_si);
                tre4.setImageResource(R.drawable.recensione_si);
                quattro4.setImageResource(R.drawable.recensione_no);
                cinque4.setImageResource(R.drawable.recensione_no);
            }
        });
        quattro4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valBagni = 4;
                bagni.setBackgroundResource(0);
                uno4.setImageResource(R.drawable.recensione_si);
                due4.setImageResource(R.drawable.recensione_si);
                tre4.setImageResource(R.drawable.recensione_si);
                quattro4.setImageResource(R.drawable.recensione_si);
                cinque4.setImageResource(R.drawable.recensione_no);
            }
        });
        cinque4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valBagni = 5;
                bagni.setBackgroundResource(0);
                uno4.setImageResource(R.drawable.recensione_si);
                due4.setImageResource(R.drawable.recensione_si);
                tre4.setImageResource(R.drawable.recensione_si);
                quattro4.setImageResource(R.drawable.recensione_si);
                cinque4.setImageResource(R.drawable.recensione_si);
            }
        });

    }

    ImageView uno3,due3,tre3,quattro3,cinque3;
    private void setServizio() {
        uno3 = (ImageView) findViewById(R.id.imageView4831);
        due3 = (ImageView) findViewById(R.id.imageView4931);
        tre3 = (ImageView) findViewById(R.id.imageView5031);
        quattro3 = (ImageView) findViewById(R.id.imageView5131);
        cinque3 = (ImageView) findViewById(R.id.imageView5231);
        uno3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valServizio = 1;
                servizio.setBackgroundResource(0);
                uno3.setImageResource(R.drawable.recensione_si);
                due3.setImageResource(R.drawable.recensione_no);
                tre3.setImageResource(R.drawable.recensione_no);
                quattro3.setImageResource(R.drawable.recensione_no);
                cinque3.setImageResource(R.drawable.recensione_no);
            }
        });
        due3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valServizio = 2;
                servizio.setBackgroundResource(0);
                uno3.setImageResource(R.drawable.recensione_si);
                due3.setImageResource(R.drawable.recensione_si);
                tre3.setImageResource(R.drawable.recensione_no);
                quattro3.setImageResource(R.drawable.recensione_no);
                cinque3.setImageResource(R.drawable.recensione_no);
            }
        });
        tre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valServizio = 3;
                servizio.setBackgroundResource(0);
                uno3.setImageResource(R.drawable.recensione_si);
                due3.setImageResource(R.drawable.recensione_si);
                tre3.setImageResource(R.drawable.recensione_si);
                quattro3.setImageResource(R.drawable.recensione_no);
                cinque3.setImageResource(R.drawable.recensione_no);
            }
        });
        quattro3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valServizio = 4;
                servizio.setBackgroundResource(0);
                uno3.setImageResource(R.drawable.recensione_si);
                due3.setImageResource(R.drawable.recensione_si);
                tre3.setImageResource(R.drawable.recensione_si);
                quattro3.setImageResource(R.drawable.recensione_si);
                cinque3.setImageResource(R.drawable.recensione_no);
            }
        });
        cinque3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valServizio = 5;
                servizio.setBackgroundResource(0);
                uno3.setImageResource(R.drawable.recensione_si);
                due3.setImageResource(R.drawable.recensione_si);
                tre3.setImageResource(R.drawable.recensione_si);
                quattro3.setImageResource(R.drawable.recensione_si);
                cinque3.setImageResource(R.drawable.recensione_si);
            }
        });

    }

    ImageView uno2,due2,tre2,quattro2,cinque2;
    private void setProdotti() {
        uno2 = (ImageView) findViewById(R.id.imageView483);
        due2 = (ImageView) findViewById(R.id.imageView493);
        tre2 = (ImageView) findViewById(R.id.imageView503);
        quattro2 = (ImageView) findViewById(R.id.imageView513);
        cinque2 = (ImageView) findViewById(R.id.imageView523);

        uno2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valProdotti = 1;
                prodotti.setBackgroundResource(0);
                uno2.setImageResource(R.drawable.recensione_si);
                due2.setImageResource(R.drawable.recensione_no);
                tre2.setImageResource(R.drawable.recensione_no);
                quattro2.setImageResource(R.drawable.recensione_no);
                cinque2.setImageResource(R.drawable.recensione_no);
            }
        });
        due2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valProdotti = 2;
                prodotti.setBackgroundResource(0);
                uno2.setImageResource(R.drawable.recensione_si);
                due2.setImageResource(R.drawable.recensione_si);
                tre2.setImageResource(R.drawable.recensione_no);
                quattro2.setImageResource(R.drawable.recensione_no);
                cinque2.setImageResource(R.drawable.recensione_no);
            }
        });
        tre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valProdotti = 3;
                prodotti.setBackgroundResource(0);
                uno2.setImageResource(R.drawable.recensione_si);
                due2.setImageResource(R.drawable.recensione_si);
                tre2.setImageResource(R.drawable.recensione_si);
                quattro2.setImageResource(R.drawable.recensione_no);
                cinque2.setImageResource(R.drawable.recensione_no);
            }
        });
        quattro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valProdotti = 4;
                prodotti.setBackgroundResource(0);
                uno2.setImageResource(R.drawable.recensione_si);
                due2.setImageResource(R.drawable.recensione_si);
                tre2.setImageResource(R.drawable.recensione_si);
                quattro2.setImageResource(R.drawable.recensione_si);
                cinque2.setImageResource(R.drawable.recensione_no);
            }
        });
        cinque2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valProdotti = 5;
                prodotti.setBackgroundResource(0);
                uno2.setImageResource(R.drawable.recensione_si);
                due2.setImageResource(R.drawable.recensione_si);
                tre2.setImageResource(R.drawable.recensione_si  );
                quattro2.setImageResource(R.drawable.recensione_si);
                cinque2.setImageResource(R.drawable.recensione_si);
            }
        });
    }


    ImageView uno1,due1,tre1,quattro1,cinque1;
    private void setStrutture() {
        uno1 = (ImageView) findViewById( R.id.imageView48 );
        due1 = (ImageView) findViewById( R.id.imageView49 );
        tre1 = (ImageView) findViewById( R.id.imageView50 );
        quattro1 = (ImageView) findViewById( R.id.imageView51 );
        cinque1 = (ImageView) findViewById( R.id.imageView52 );
        uno1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valStruttura = 1;
                strutura.setBackgroundResource(0);
                uno1.setImageResource( R.drawable.recensione_si );
                due1.setImageResource( R.drawable.recensione_no );
                tre1.setImageResource( R.drawable.recensione_no );
                quattro1.setImageResource( R.drawable.recensione_no );
                cinque1.setImageResource( R.drawable.recensione_no );
            }
        } );
        due1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valStruttura = 2;
                strutura.setBackgroundResource(0);

                uno1.setImageResource( R.drawable.recensione_si );
                due1.setImageResource( R.drawable.recensione_si );
                tre1.setImageResource( R.drawable.recensione_no );
                quattro1.setImageResource( R.drawable.recensione_no );
                cinque1.setImageResource( R.drawable.recensione_no );
            }
        } );
        tre1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valStruttura = 3;
                strutura.setBackgroundResource(0);

                uno1.setImageResource( R.drawable.recensione_si );
                due1.setImageResource( R.drawable.recensione_si );
                tre1.setImageResource( R.drawable.recensione_si );

                quattro1.setImageResource( R.drawable.recensione_no );
                cinque1.setImageResource( R.drawable.recensione_no );
            }
        } );
        quattro1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valStruttura = 4;
                strutura.setBackgroundResource(0);

                uno1.setImageResource( R.drawable.recensione_si );
                due1.setImageResource( R.drawable.recensione_si );
                tre1.setImageResource( R.drawable.recensione_si );
                quattro1.setImageResource( R.drawable.recensione_si );
                cinque1.setImageResource( R.drawable.recensione_no );
            }
        } );
        cinque1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valStruttura = 5;
                strutura.setBackgroundResource(0);


                uno1.setImageResource( R.drawable.recensione_si );
                due1.setImageResource( R.drawable.recensione_si );
                tre1.setImageResource( R.drawable.recensione_si );
                quattro1.setImageResource( R.drawable.recensione_si );
                cinque1.setImageResource( R.drawable.recensione_si );
            }
        } );
    }



    TextView rec;
    private void setTitle() {
        rec = (TextView) findViewById( R.id.textView66 );
        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot d : task.getResult()){
                        if(d.getString( "email" ).equals( FaiUnaRecensione.emailPub )){
                            rec.setText( d.getString( "nomeLocale" ) );
                        }
                    }
                }
            }
        } );
    }

    private void checkMiPiacciono() {
        firebaseFirestore.collection( "Pubblico" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString( "email" ).equals( email )){
                            if(documentSnapshot.getString( "miPiacciono" ).equals( "" )){

                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( FaiUnaRecensione2.this,R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title
                                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                View viewView = inflater.inflate( R.layout.check_mi_piacciono, null );
                                dialogBuilder.setView( viewView );
                                dialogBuilder.setCancelable( false );
                                AlertDialog alertDialogg = dialogBuilder.create();
                                alertDialogg.show();
                                Spinner spinner = (Spinner) viewView.findViewById( R.id.spinner );
                                ImageView salva = (ImageView) viewView.findViewById( R.id.imageView47 );
                                salva.setOnClickListener( new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String sele = spinner.getSelectedItem().toString();
                                        if(sele.equals( getString( R.string.seleziona ) )){
                                            Toast.makeText( getApplicationContext(),getString( R.string.larispostanonecorretta ),Toast.LENGTH_LONG ).show();
                                        }else{
                                            DocumentReference documentReference = firebaseFirestore.collection( "Pubblico" ).document(documentSnapshot.getId());
                                            documentReference.update( "miPiacciono",sele ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    alertDialogg.dismiss();
                                                    miPicc = sele;
                                                }
                                            } );
                                        }
                                    }
                                } );




                            }
                        }
                    }
                }
            }
        } );

    }
}