package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pubmania.Pubblico.String.StringRecensione;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FaiUnaRecensione2 extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String email;
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
    }

    ImageView faiRecensione;
    ArrayList<String> arrayUrl = new ArrayList<>();
    int d = 0;
    private void setRecensisci() {
        faiRecensione = (ImageView) findViewById(R.id.imageView54);
        faiRecensione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FaiUnaRecensione.arrayUri.size() > 0) {
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
                                            stringRecensione.setValRagazzi(String.valueOf(valRagazzi));
                                            stringRecensione.setValPrezzi(String.valueOf(valPrezzi));
                                            stringRecensione.setEmailPubblico(email);
                                            String[] arr = arrayUrl.toArray(new String[arrayUrl.size()]);
                                            List<String> listIngg = Arrays.asList(arr);
                                            stringRecensione.setArrayList(listIngg);
                                            firebaseFirestore.collection(FaiUnaRecensione.emailPub + "Rec").add(stringRecensione).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentReference documentReference = firebaseFirestore.collection(FaiUnaRecensione.emailPub + "Rec").document(task.getResult().getId());
                                                        documentReference.update("id", task.getResult().getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Intent i = new Intent(getApplicationContext(), ProfiloPubblico.class);
                                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(i);
                                                                finish();
                                                            }
                                                        });
                                                    }
                                                }
                                            });

                                        }

                                    }
                                });
                            }
                        });
                    }
                }else{
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
                    stringRecensione.setValRagazzi(String.valueOf(valRagazzi));
                    stringRecensione.setValPrezzi(String.valueOf(valPrezzi));



                    firebaseFirestore.collection(FaiUnaRecensione.emailPub+"Rec").add(stringRecensione).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                DocumentReference documentReference = firebaseFirestore.collection(FaiUnaRecensione.emailPub+"Rec").document(task.getResult().getId());
                                documentReference.update("id",task.getResult().getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent i = new Intent(getApplicationContext(),ProfiloPubblico.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                            }
                        }
                    });

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
                valPrezzi = 2;
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
                valPrezzi = 3;
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
                valPrezzi = 4;
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
                valPrezzi = 5;
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
        maschileLayout = (ConstraintLayout) findViewById(R.id.entrambi);
        femminileLayout = (ConstraintLayout) findViewById(R.id.constRagazze);
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