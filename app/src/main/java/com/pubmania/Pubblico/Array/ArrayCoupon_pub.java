package com.pubmania.Pubblico.Array;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.WriterException;
import com.pubmania.Pubblico.R;
import com.pubmania.Pubblico.String.StringPost_coupon;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.ContentValues.TAG;

public class ArrayCoupon_pub extends ArrayAdapter<StringPost_coupon> {

    Activity context;
    ArrayList<StringPost_coupon> arrayList;
    String email,emailPub;

    public ArrayCoupon_pub(@NonNull Context context, ArrayList<StringPost_coupon> arrayList,String email,String emailPub) {
        super( context, 0,  arrayList);
        this.context = (Activity) context;
        this.arrayList = arrayList;
        this.email = email;
        this.emailPub = emailPub;
    }
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    int followeInt;
    String idFol;
    boolean fo = false;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate( R.layout.array_coupon,null,false );
        StringPost_coupon stringCoupon = getItem( position );

        TextView percentuale = (TextView) view.findViewById( R.id.textView49 );
        TextView titolo = (TextView) view.findViewById( R.id.textView54 );
        TextView quantevolte = (TextView) view.findViewById( R.id.textView27 );
        TextView chi = (TextView) view.findViewById( R.id.textView28 );
        ImageButton qrcorde = (ImageButton) view.findViewById( R.id.imageButton8 );


        Log.d( "kfmdskfmksd",arrayList.get( position ).getId() );
        Log.d( "kmfdlksmfs", String.valueOf( position ) );
        if(stringCoupon.getQuanteVolte().equals( "Sempre" )){
            quantevolte.setText( context.getString( R.string.puoiusarlotsempre ) );
        }else {
            quantevolte.setText( context.getString( R.string.puoiusarlosolo ) + stringCoupon.getQuanteVolte() + " " + context.getString( R.string.voltae ) );

        }

        if(stringCoupon.getChi().equals( "Tutti" )){
            chi.setText( context.getString( R.string.pertutti ) );
        }else{
            chi.setText( context.getString( R.string.perifollower ) );
        }
        if(stringCoupon.tipo.equals( "Percentuale" )){
            percentuale.setText(stringCoupon.getPrezzo() + " %" );

            if(stringCoupon.getQualeProdotto().equals( context.getString( R.string.tutti ) )){
                titolo.setText( stringCoupon.getPrezzo() + "% " + context.getString( R.string.sututtiiprodotti ));
            }else{
                titolo.setText( stringCoupon.getPrezzo() + "% " + stringCoupon.getQualeProdotto() );
            }
        }
        else if(stringCoupon.tipo.equals( "Prezzo" )){
            percentuale.setText(stringCoupon.getPrezzo() + " ,00€" );

            if(stringCoupon.getQualeProdotto().equals( context.getString( R.string.tutti ) )){
                titolo.setText( context.getString( R.string.scontoDi ) +" "+ stringCoupon.getPrezzo() + " ,00€ " + context.getString( R.string.sututtolimporto ));
            }else{
                titolo.setText( context.getString( R.string.scontoDi )+" " + stringCoupon.getPrezzo() + " ,00€ " + context.getString( R.string.su ) + stringCoupon.getQualeProdotto());
            }
        }

        Log.d("onflsdnlf",stringCoupon.getToken() + "tolen");

        qrcorde.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!stringCoupon.getChi().equals( "Tutti" )) {

                    firebaseFirestore.collection( email + "follower" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task != null) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    for (int i = 1; i < documentSnapshot.getData().size(); i++) {
                                        if (!documentSnapshot.getData().get( String.valueOf( i ) ).equals( emailPub ) && fo == false) {
                                            Snackbar.make( view, context.getString( R.string.questoscontoèsoloperifollower ), Snackbar.LENGTH_LONG )
                                                    .setAction( context.getString( R.string.segui ), new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            firebaseFirestore.collection( email + "follower" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task != null) {
                                                                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                                            followeInt = documentSnapshot.getData().size() + 1;
                                                                            idFol = documentSnapshot.getId();
                                                                        }
                                                                        Log.d("ojnfsjklndfj",stringCoupon.getToken());

                                                                        DocumentReference documentReference = firebaseFirestore.collection( email + "follower" ).document( idFol );
                                                                        documentReference.update( String.valueOf( followeInt ), emailPub ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                fo = true;
                                                                                ImageView image = new ImageView( context );
                                                                                Bitmap bitmap;
                                                                                QRGEncoder qrgEncoder = new QRGEncoder(
                                                                                        email + ":" + stringCoupon.getId()+":" + stringCoupon.getQuanteVolte()+":" + stringCoupon.getTipo() +":" + stringCoupon.getPrezzo() + ":" + stringCoupon.getQuanteVolte() + ":" + stringCoupon.getQualeProdotto() + ":" + stringCoupon.getToken(), null, QRGContents.Type.TEXT, 800 );
                                                                                try {
                                                                                    // Getting QR-Code as Bitmap
                                                                                    bitmap = qrgEncoder.encodeAsBitmap();
                                                                                    // Setting Bitmap to ImageView
                                                                                    image.setImageBitmap( bitmap );
                                                                                } catch (WriterException e) {
                                                                                    Log.v( TAG, e.toString() );
                                                                                }

                                                                                AlertDialog.Builder builder =
                                                                                        new AlertDialog.Builder( context ).
                                                                                                setMessage( context.getString( R.string.fattiscansiarequestoqrdalbarista ) )
                                                                                                .
                                                                                                        setPositiveButton( context.getString( R.string.fine ), new DialogInterface.OnClickListener() {
                                                                                                            @Override
                                                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                                                dialog.dismiss();
                                                                                                            }
                                                                                                        } ).
                                                                                                setView( image );
                                                                                builder.create().show();
                                                                            }
                                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {

                                                                            }
                                                                        } );
                                                                    }
                                                                }
                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {

                                                                }
                                                            } );
                                                        }
                                                    } ).show();
                                        }else{
                                            ImageView image = new ImageView( context );

                                            Bitmap bitmap;
                                            QRGEncoder qrgEncoder = new QRGEncoder(
                                                    email + ":" + stringCoupon.getId()+":"+ stringCoupon.getQuanteVolte()+":" + stringCoupon.getTipo() +":" + stringCoupon.getPrezzo() + ":" + stringCoupon.getQuanteVolte() + ":" + stringCoupon.getQualeProdotto() + ":" + stringCoupon.getToken(), null, QRGContents.Type.TEXT, 800 );
                                            try {
                                                // Getting QR-Code as Bitmap
                                                bitmap = qrgEncoder.encodeAsBitmap();
                                                // Setting Bitmap to ImageView
                                                image.setImageBitmap( bitmap );
                                            } catch (WriterException e) {
                                                Log.v( TAG, e.toString() );
                                            }

                                            AlertDialog.Builder builder =
                                                    new AlertDialog.Builder( context ).
                                                            setMessage( context.getString( R.string.fattiscansiarequestoqrdalbarista ) )
                                                            .
                                                                    setPositiveButton( context.getString( R.string.fine ), new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                        }
                                                                    } ).
                                                            setView( image );
                                            builder.create().show();
                                        }
                                    }


                                }
                            }
                        }
                    } );

                } else {


                    ImageView image = new ImageView( context );

                    Bitmap bitmap;
                    QRGEncoder qrgEncoder = new QRGEncoder(
                            email + ":" + stringCoupon.getId() + ":" + stringCoupon.getQuanteVolte()+":" + stringCoupon.getTipo() +":" + stringCoupon.getPrezzo() + ":" + stringCoupon.getQuanteVolte() + ":" + stringCoupon.getQualeProdotto() + ":" + stringCoupon.getToken(), null, QRGContents.Type.TEXT, 800 );
                    try {
                        // Getting QR-Code as Bitmap
                        bitmap = qrgEncoder.encodeAsBitmap();
                        // Setting Bitmap to ImageView
                        image.setImageBitmap( bitmap );
                    } catch (WriterException e) {
                        Log.v( TAG, e.toString() );
                    }

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder( context ).
                                    setMessage( context.getString( R.string.fattiscansiarequestoqrdalbarista ) )
                                    .
                                            setPositiveButton( context.getString( R.string.fine ), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            } ).
                                    setView( image );
                    builder.create().show();
                }
            }
        } );






        return view;
    }
}
