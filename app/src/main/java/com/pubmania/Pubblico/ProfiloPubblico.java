package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.pubmania.Pubblico.Array.ArrayRecensioniDisponibili;
import com.pubmania.Pubblico.Array.ArraySegutiiProgilo;
import com.pubmania.Pubblico.Array.arrayCouponUtilizzati;
import com.pubmania.Pubblico.String.StringPost_coupon;
import com.pubmania.Pubblico.String.StringRecensioni;
import com.pubmania.Pubblico.String.Stringdoppia;

import java.util.ArrayList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfiloPubblico extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profilo_pubblico );
        email = "nicolino.oliverio@gmail.com";
        setCouponUtilizzati();
        setaccountSeguiti();
        setecensioniDisponibili();
        setProfile();

        Log.e("dljsnadjnas","dijsoajdi");


    }



    CircleImageView fotoProfilo;
    TextView nome;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private void setProfile() {
        fotoProfilo = (CircleImageView) findViewById( R.id.circleImageView4 );
        nome = (TextView) findViewById( R.id.textView44 );
        firebaseFirestore.collection( "Pubblico" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString( "email" ).equals( email )){
                            nome.setText( documentSnapshot.getString( "nome" ) + " " + documentSnapshot.getString( "cognome" ));
                            Glide.with( ProfiloPubblico.this ).load( documentSnapshot.getString( "fotoProfilo" ) ).into( fotoProfilo );

                        }
                    }
                }
            }
        } );

    }

    String tokenEmail;
    GridView gridView;
    ArrayList<StringRecensioni> arrayString = new ArrayList<>();
    ArrayRecensioniDisponibili arrayRecensioniDisponibili;
    private void setecensioniDisponibili() {
        Log.e("dljsnadjnas","dijsoajdi");

        gridView = (GridView) findViewById( R.id.gridView );
        firebaseFirestore.collection( email+"Rec" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    Log.d("dljsnadjnas","dijsoajdi");
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : list){
                        StringRecensioni stringRecensioni = documentSnapshot.toObject( StringRecensioni.class );
                        arrayString.add( stringRecensioni );
                        arrayRecensioniDisponibili = new ArrayRecensioniDisponibili( ProfiloPubblico.this,arrayString );
                        gridView.setAdapter( arrayRecensioniDisponibili );
                        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                TextView textView = (TextView) view.findViewById( R.id.textView56 );
                                TextView idPo = (TextView) view.findViewById( R.id.textView69 );
                                firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()){
                                                if(documentSnapshot1.getString("email").equals(textView.getText().toString())){
                                                    tokenEmail = documentSnapshot1.getString("token");
                                                    Intent intent = new Intent(getApplicationContext(),FaiUnaRecensione.class);
                                                    intent.putExtra( "emailPub",textView.getText().toString()  );
                                                    intent.putExtra("idPost",idPo.getText().toString());
                                                    intent.putExtra("token",tokenEmail);
                                                    startActivity( intent );

                                                }
                                            }

                                        }
                                    }
                                });


                            }
                        } );


                    }
                }
                else{
                    Log.d("ojfnkfnsdn","odnsjandl");
                }
            }
        } );
    }


    ListView listPubSeguiti;
    ArrayList<Stringdoppia> arrayListSeguiti = new ArrayList<>();


    private void setaccountSeguiti() {
        listPubSeguiti = (ListView) findViewById( R.id.listaccountSeguiti );
        firebaseFirestore.collection( email+"follower" ).limit( 15 )
                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : list){
                        for (int i = 1;i<documentSnapshot.getData().size() +1;i++){
                            if(!documentSnapshot.getString( String.valueOf( i ) ).equals( "" )){
                                int finalI = i;
                                Log.d( "fskldfmskf",documentSnapshot.getString( String.valueOf( i ) ) );
                                firebaseFirestore.collection( "Professionisti")
                                        .get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()){
                                                if(documentSnapshot.getString( String.valueOf( finalI ) ).equals( documentSnapshot1.getString( "email" ) ) ){
                                                    Stringdoppia stringdoppia = new Stringdoppia();
                                                    stringdoppia.setUno( documentSnapshot1.getString( "nomeLocale" ) );
                                                    stringdoppia.setDue( documentSnapshot1.getString( "fotoProfilo" ) );
                                                    arrayListSeguiti.add( stringdoppia );
                                                    ArraySegutiiProgilo arraySegutiiProgilo = new ArraySegutiiProgilo( ProfiloPubblico.this,arrayListSeguiti );
                                                    listPubSeguiti.setAdapter( arraySegutiiProgilo );
                                                }
                                            }
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



    ListView listCouponUtilizzati;
    ArrayList<StringPost_coupon> arrayList = new ArrayList<>();
    private void setCouponUtilizzati() {
        listCouponUtilizzati = (ListView) findViewById( R.id.listCouponutilizzati );
        firebaseFirestore.collection( email+"Coupon" )
                .limit( 8 )
               .get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        firebaseFirestore.collection( documentSnapshot.getString( "emailPub" ) + "Post" )
                                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots != null){
                                    List<DocumentSnapshot> l  = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot documentSnapshot1 : l){
                                        StringPost_coupon stringPost_coupon = documentSnapshot1.toObject( StringPost_coupon.class );
                                        Log.d("sdfmòsdkmf",stringPost_coupon.getCategoria());
                                        if(stringPost_coupon.getId().equals( documentSnapshot.getString( "idPost" ) )){
                                            firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful()){
                                                        for (QueryDocumentSnapshot d :task.getResult()){
                                                            if(d.getString( "email" ).equals( documentSnapshot.getString( "emailPub" ) )){
                                                                arrayList.add( stringPost_coupon );
                                                                arrayCouponUtilizzati arrayCouponUtilizzati = new arrayCouponUtilizzati( ProfiloPubblico.this,arrayList,d.getString( "nomeLocale" ),d.getString( "fotoProfilo" ) );
                                                                listCouponUtilizzati.setAdapter( arrayCouponUtilizzati );


                                                            }
                                                        }
                                                    }
                                                }
                                            } );



                                        }
                                    }
                                }
                            }
                        } );
                    }
                }
            }
        } );



    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent(getApplicationContext(),HomePage.class) );

        super.onBackPressed();
    }
}