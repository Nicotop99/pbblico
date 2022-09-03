package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FaiUnaRecensione2 extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String email;
    String miPicc = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fai_una_recensione2 );
        email = "nicolino.oliverio@gmail.com";
        checkMiPiacciono();
        setTitle();
        setStrutture();
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