package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.Pubblico.Array.ArrayCoupon_pub;
import com.pubmania.Pubblico.String.StringPost_coupon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class coupon_pub extends AppCompatActivity {

    String emailPub,nome;
    ArrayList<String> tokenProf;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_coupon_pub );
        emailPub = getIntent().getExtras().getString( "emailPub" );
        email = "nicolino.oliverio@gmail";
        firebaseFirestore.collection("Pubblico").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString("email").equals(email)){
                            nome = documentSnapshot.getString("nome") + " " + documentSnapshot.getString("cognome");
                        }
                    }
                }
            }
        });
        firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString("email").equals(emailPub)){
                            tokenProf = (ArrayList<String>) documentSnapshot.get("token");
                            setListView();
                            filtri();
                        }
                    }
                }
            }
        });

    }

    ListView listView;
    ArrayList<StringPost_coupon> arrayList;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private void setListView() {
        listView = (ListView) findViewById( R.id.list_coupon );
        arrayList = new ArrayList<>();
        firebaseFirestore.collection( emailPub+"Post" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : list){
                        StringPost_coupon stringPost_coupon = documentSnapshot.toObject( StringPost_coupon.class );
                        if(stringPost_coupon.getCategoria().equals( "Coupon" )){
                            arrayList.add( stringPost_coupon );
                            ArrayCoupon_pub arrayCoupon_pub = new ArrayCoupon_pub( coupon_pub.this,arrayList,email,emailPub ,nome,tokenProf);
                            listView.setAdapter( arrayCoupon_pub );
                            Log.d("fkmdslmfsd",documentSnapshot.getString("token"));

                        }


                    }
                }
            }
        } );


    }

    ArrayList<Integer>prova = new ArrayList<>();
    ArrayList<Integer>provaCheck = new ArrayList<>();

    ImageButton piuusato,menousato,piurecente,menorecente;
    ArrayCoupon_pub array_list_coupon;
    private void filtri() {






        piuusato = (ImageButton) findViewById( R.id.imageButton25 );
        piurecente = (ImageButton) findViewById( R.id.imageButton26 );
        menousato = (ImageButton) findViewById( R.id.imageButton27 );
        menorecente = (ImageButton) findViewById( R.id.imageButton28 );
        piuusato.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.clear();
                prova.clear();
                provaCheck.clear();
                array_list_coupon = null;
                listView.setAdapter( array_list_coupon );
                piuusato.setImageResource( R.drawable.back_filtro_select );
                menorecente.setImageResource( R.drawable.back_filtro_non_select );
                menousato.setImageResource( R.drawable.back_filtro_non_select );
                piurecente.setImageResource( R.drawable.back_filtro_non_select );

                firebaseFirestore.collection( emailPub+"Post" )
                        .orderBy( "volteUtilizzate", Query.Direction.DESCENDING ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null) {
                            List<DocumentSnapshot> l = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : l) {
                                StringPost_coupon stringCoupon = documentSnapshot.toObject( StringPost_coupon.class );
                                if (stringCoupon.getCategoria().equals( "Coupon" )) {

                                    Log.d( "kfmslfsd", stringCoupon.getPrezzo() );
                                    arrayList.add( stringCoupon );
                                    array_list_coupon = new ArrayCoupon_pub( coupon_pub.this, arrayList, email, emailPub,nome ,tokenProf);

                                    listView.setAdapter( array_list_coupon );
                                }
                            }
                        }
                    }
                } );


            }
        } );

        piurecente.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prova.clear();
                provaCheck.clear();
                array_list_coupon = null;
                listView.setAdapter( array_list_coupon );
                Log.d( "fmdskfmosdk","pd,sòk,òds" );
                arrayList.clear();
                piuusato.setImageResource( R.drawable.back_filtro_non_select );
                menorecente.setImageResource( R.drawable.back_filtro_non_select );
                menousato.setImageResource( R.drawable.back_filtro_non_select );
                piurecente.setImageResource( R.drawable.back_filtro_select );
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");


                firebaseFirestore.collection( emailPub+"Post" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null) {
                            List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                StringPost_coupon stringCoupon = documentSnapshot.toObject( StringPost_coupon.class );
                                if (stringCoupon.getCategoria().equals( "Coupon" )) {


                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                                        String currentDateandTime = sdf.format( new Date() );
                                        Date date1 = simpleDateFormat.parse( currentDateandTime );

                                        Date date2 = simpleDateFormat.parse( stringCoupon.getOra() );

                                        int different = (int) (date1.getTime() - date2.getTime());
                                        prova.add( different );
                                        provaCheck.add( different );
                                        Log.d( "mdlfsld", String.valueOf( prova.size() ) );


                                        Collections.sort( prova );


                                        //chi lo ha piu basso è piu recente


                                    } catch (ParseException e) {
                                        e.printStackTrace();

                                    }


                                }
                            }
                            Collections.sort( provaCheck );

                            for (int i = 0; i < prova.size() ; i++) {
                                int finalI = i;
                                Log.d( "kfmsdklmf", String.valueOf( i ) + " " +prova.size() );
                                firebaseFirestore.collection( emailPub + "Post" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (queryDocumentSnapshots != null) {
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot doc : list) {
                                                StringPost_coupon stringCoupon1 = doc.toObject( StringPost_coupon.class );
                                                if (stringCoupon1.getCategoria().equals( "Coupon" )) {
                                                    SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                                                    String currentDateandTime = sdf.format( new Date() );

                                                    Date date1 = null;
                                                    try {
                                                        date1 = simpleDateFormat.parse( currentDateandTime );
                                                        Date date2 = simpleDateFormat.parse( stringCoupon1.getOra() );

                                                        int different = (int) (date1.getTime() - date2.getTime());


                                                        if (provaCheck.size() > 0) {
                                                            if (different == prova.get( finalI )) {
                                                                provaCheck.remove( 0 );
                                                                arrayList.add( stringCoupon1 );
                                                            }

                                                        } else {

                                                            if (arrayList.size() > 0) {


                                                                array_list_coupon = new ArrayCoupon_pub( coupon_pub.this, arrayList, email, emailPub,nome,tokenProf );

                                                                listView.setAdapter( array_list_coupon );
                                                            }
                                                        }



                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }


                                                }
                                            }
                                        }
                                    }
                                } );
                            }

                        }
                        else{

                            }
                        }

                } );



            }
        } );

        menorecente.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prova.clear();
                provaCheck.clear();
                array_list_coupon = null;
                listView.setAdapter( array_list_coupon );
                Log.d( "fmdskfmosdk","pd,sòk,òds" );
                arrayList.clear();
                piuusato.setImageResource( R.drawable.back_filtro_non_select );
                menorecente.setImageResource( R.drawable.back_filtro_select );
                menousato.setImageResource( R.drawable.back_filtro_non_select );
                piurecente.setImageResource( R.drawable.back_filtro_non_select );
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");


                firebaseFirestore.collection( emailPub+"Post" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null) {
                            List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                StringPost_coupon stringCoupon = documentSnapshot.toObject( StringPost_coupon.class );
                                if (stringCoupon.getCategoria().equals( "Coupon" )) {


                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                                        String currentDateandTime = sdf.format( new Date() );
                                        Date date1 = simpleDateFormat.parse( currentDateandTime );

                                        Date date2 = simpleDateFormat.parse( stringCoupon.getOra() );

                                        int different = (int) (date1.getTime() - date2.getTime());
                                        prova.add( different );
                                        provaCheck.add( different );
                                        Log.d( "mdlfsld", String.valueOf( prova.size() ) );




                                        //chi lo ha piu basso è piu recente


                                    } catch (ParseException e) {
                                        e.printStackTrace();

                                    }


                                }
                            }
                            Collections.sort(provaCheck, Collections.reverseOrder());
                            Collections.sort(prova, Collections.reverseOrder());
                            for (int i = 0; i < prova.size() ; i++) {
                                int finalI = i;
                                Log.d( "kfmsdklmf", String.valueOf( i ) + " " +prova.size() );
                                firebaseFirestore.collection( emailPub + "Post" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (queryDocumentSnapshots != null) {
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot doc : list) {
                                                StringPost_coupon stringCoupon1 = doc.toObject( StringPost_coupon.class );
                                                if (stringCoupon1.getCategoria().equals( "Coupon" )) {
                                                    SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                                                    String currentDateandTime = sdf.format( new Date() );

                                                    Date date1 = null;
                                                    try {
                                                        date1 = simpleDateFormat.parse( currentDateandTime );
                                                        Date date2 = simpleDateFormat.parse( stringCoupon1.getOra() );

                                                        int different = (int) (date1.getTime() - date2.getTime());


                                                        if (provaCheck.size() > 0) {
                                                            if (different == prova.get( finalI )) {
                                                                provaCheck.remove( 0 );
                                                                arrayList.add( stringCoupon1 );
                                                            }

                                                        } else {

                                                            if (arrayList.size() > 0) {


                                                                array_list_coupon = new ArrayCoupon_pub( coupon_pub.this, arrayList, email, emailPub,nome ,tokenProf);

                                                                listView.setAdapter( array_list_coupon );
                                                            }
                                                        }



                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }


                                                }
                                            }
                                        }
                                    }
                                } );
                            }

                        }
                        else{

                        }
                    }

                } );



            }
        } );






        menousato.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.clear();
                prova.clear();
                array_list_coupon = null;
                listView.setAdapter( array_list_coupon );
                piuusato.setImageResource( R.drawable.back_filtro_non_select );
                menorecente.setImageResource( R.drawable.back_filtro_non_select );
                menousato.setImageResource( R.drawable.back_filtro_select );
                piurecente.setImageResource( R.drawable.back_filtro_non_select );
                firebaseFirestore.collection( emailPub+"Post" )
                        .orderBy( "volteUtilizzate", Query.Direction.ASCENDING ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null){
                            List<DocumentSnapshot> l = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : l){

                                StringPost_coupon stringCoupon = documentSnapshot.toObject( StringPost_coupon.class );
                                if(stringCoupon.getCategoria().equals( "Coupon" )) {
                                    arrayList.add( stringCoupon );
                                    array_list_coupon = new ArrayCoupon_pub( coupon_pub.this, arrayList, email, emailPub,nome,tokenProf );
                                    listView.setAdapter( array_list_coupon );
                                }
                            }
                        }
                    }
                } );
            }
        } );



    }

}