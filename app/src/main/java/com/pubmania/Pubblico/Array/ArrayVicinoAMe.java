package com.pubmania.Pubblico.Array;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.pubmania.Pubblico.Profile_Pub;
import com.pubmania.Pubblico.R;
import com.pubmania.Pubblico.String.StringRegistrazionePub;
import com.pubmania.Pubblico.vicinoAMe;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArrayVicinoAMe   extends ArrayAdapter<StringRegistrazionePub> {

    Activity context;
    ArrayList<StringRegistrazionePub> arrayList;
    ArrayList<Double> arrDist;
    String dista;
    public ArrayVicinoAMe(@NonNull Context context, ArrayList<StringRegistrazionePub> arrayList , ArrayList<Double> arrDist) {
        super( context, 0,arrayList );
        this.context= (Activity)context;
        this.arrayList = arrayList;
        this.arrDist = arrDist;

    }
    double latitude,longitude;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate( R.layout.layout_list_vicino_a_me,null,false );
        StringRegistrazionePub stringRegistrazionePub = getItem( position );
        Log.d( "kmfkdsmf",stringRegistrazionePub.getEmail() );

        ImageView fotoProfilo = (ImageView) view.findViewById( R.id.imageView2www9 );
        TextView nomePub = (TextView) view.findViewById( R.id.textView30 );
        TextView distance = (TextView) view.findViewById( R.id.textView31 );
        TextView apertura = (TextView) view.findViewById( R.id.textView32 );
        TextView chiusura = (TextView) view.findViewById( R.id.textView33 );
        ImageView indicazioni = (ImageView) view.findViewById( R.id.imageView35 );
        ImageView profilo = (ImageView) view.findViewById( R.id.imageView36 );


        profilo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Profile_Pub.class );
                i.putExtra( "emailPub", stringRegistrazionePub.getEmail() );
                i.putExtra( "i","i" );
                context.startActivity( i );
            }
        } );
        indicazioni.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder geocoder = new Geocoder( context );
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName( stringRegistrazionePub.getViaLocale(), 1 );
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d( "ksmflkdmsl", e.getMessage() );
                }
                if (addresses.size() > 0) {
                    latitude = addresses.get( 0 ).getLatitude();
                    longitude = addresses.get( 0 ).getLongitude();
                }
                String uri = String.format( Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", latitude, longitude );
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( uri ) );
                context.startActivity( intent );
            }
        } );
        Glide.with( context ).load( stringRegistrazionePub.fotoProfilo ).into( fotoProfilo );
        nomePub.setText( stringRegistrazionePub.getNomeLocale() );
        double di = arrDist.get( position ) / 1000;
        distance.setText( "( " + new DecimalFormat("##.##").format(di) +" KM )" );
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String giono = new SimpleDateFormat("EEEE", Locale.ITALIAN).format(date.getTime());

        if(giono.equals( "lunedì" )){
            if(stringRegistrazionePub.aLunedi == null){
                apertura.setVisibility( View.INVISIBLE );
            }else {
                apertura.setText( context.getString( R.string.apertura ) + " " + stringRegistrazionePub.getaLunedi() );
            }
        }
        else if(giono.equals( "martedì" )){
            if(stringRegistrazionePub.aMartedi == null){
                apertura.setVisibility( View.INVISIBLE );
            }else {
                apertura.setText( context.getString( R.string.apertura ) + " " + stringRegistrazionePub.getaMartedi() );
            }

        }
        else if(giono.equals( "mercoldì" )){
            if(stringRegistrazionePub.aMercoledi == null){
                apertura.setVisibility( View.INVISIBLE );
            }else {
                apertura.setText( context.getString( R.string.apertura ) + " " + stringRegistrazionePub.getaMercoledi() );
            }
        }
        else if(giono.equals( "giovedì" )){if(stringRegistrazionePub.aGiovedi == null){
            apertura.setVisibility( View.INVISIBLE );
        }else {
            apertura.setText( context.getString( R.string.apertura ) + " " + stringRegistrazionePub.getaGiovedi() );
        }
        }
        else if(giono.equals( "venerdì" )){
            if(stringRegistrazionePub.aVenerdi == null){
                apertura.setVisibility( View.INVISIBLE );
            }else {
                apertura.setText( context.getString( R.string.apertura ) + " " + stringRegistrazionePub.getaVenerdi() );
            }
        }
        else if(giono.equals( "sabato" )){
            if(stringRegistrazionePub.aSabato == null){
                apertura.setVisibility( View.INVISIBLE );
            }else {
                apertura.setText( context.getString( R.string.apertura ) + " " + stringRegistrazionePub.getaSabato() );
            }
        }
        else if(giono.equals( "domenica" )){
            if(stringRegistrazionePub.aDomenica == null){
                apertura.setVisibility( View.INVISIBLE );
            }else {
                apertura.setText( context.getString( R.string.apertura ) + " " + stringRegistrazionePub.getaDomenica() );
            }
        }




        if(giono.equals( "lunedì" )){

            if(stringRegistrazionePub.cLunedi == null){
                chiusura.setVisibility( View.INVISIBLE );
            }else{
                chiusura.setText( context.getString( R.string.chiusura )+" " + stringRegistrazionePub.getcLunedi() );

            }

        }
        else if(giono.equals( "martedì" )){
            if(stringRegistrazionePub.cMartedi == null){
                chiusura.setVisibility( View.INVISIBLE );
            }else {
                chiusura.setText( context.getString( R.string.chiusura ) + " " + stringRegistrazionePub.getcMartedi() );
            }
        }
        else if(giono.equals( "mercoledì" )){
            if(stringRegistrazionePub.cMercoledi == null){
                chiusura.setVisibility( View.INVISIBLE );
            }else {
                chiusura.setText( context.getString( R.string.chiusura ) + " " + stringRegistrazionePub.getcMercoledi() );
            }
        }
        else if(giono.equals( "giovedì" )){
            if(stringRegistrazionePub.cGiovedi == null){
                chiusura.setVisibility( View.INVISIBLE );
            }else {
                chiusura.setText( context.getString( R.string.chiusura ) + " " + stringRegistrazionePub.getcGiovedi() );
            }
        }
        else if(giono.equals( "venerdì" )){
            if(stringRegistrazionePub.cVenerdi == null){
                chiusura.setVisibility( View.INVISIBLE );
            }else {
                chiusura.setText( context.getString( R.string.chiusura ) + " " + stringRegistrazionePub.getcVenerdi() );
            }
        }
        else if(giono.equals( "sabato" )){
            if(stringRegistrazionePub.cSabato == null){
                chiusura.setVisibility( View.INVISIBLE );
            }else {
                chiusura.setText( context.getString( R.string.chiusura ) + " " + stringRegistrazionePub.getcSabato() );
            }
        }
        else if(giono.equals( "domenica" )){
            if(stringRegistrazionePub.cDomenica == null){
                chiusura.setVisibility( View.INVISIBLE );
            }else {
                chiusura.setText( context.getString( R.string.chiusura ) + " " + stringRegistrazionePub.getcDomenica() );
            }
        }





        return view;
    }
}
