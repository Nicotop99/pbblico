package com.pubmania.Pubblico.Array;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.pubmania.Pubblico.R;
import com.pubmania.Pubblico.String.StringPost_coupon;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class arrayCouponUtilizzati extends ArrayAdapter<StringPost_coupon> {

    Activity context;
    ArrayList<StringPost_coupon> arrayList;
    String nomeL,image;

    public arrayCouponUtilizzati(@NonNull Context context, ArrayList<StringPost_coupon>arrayList,String nomeLocale,String image) {
        super( context, 0,arrayList);
        this.context = (Activity) context;
        this.arrayList = arrayList;
        this.nomeL = nomeLocale;
        this.image = image;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate( R.layout.coupon_utilizzati,null,false );



        StringPost_coupon stringCoupon = getItem( position );
        TextView titolo = (TextView) view.findViewById( R.id.textView47 );
        TextView scont = (TextView) view.findViewById( R.id.textView46 );
        CircleImageView circleImageView = (CircleImageView) view.findViewById( R.id.circleImageView5 );
        TextView nomePub = (TextView) view.findViewById( R.id.textView45 );

        nomePub.setText( nomeL );
        Glide.with( context ).load( image ).into( circleImageView );




        if(stringCoupon.tipo.equals( "Percentuale" )){
            scont.setText(stringCoupon.getPrezzo() + " %" );

            if(stringCoupon.getQualeProdotto().equals( context.getString( R.string.tutti ) )){
                titolo.setText( stringCoupon.getPrezzo() + "% " + context.getString( R.string.sututtiiprodotti ));
            }else{
                titolo.setText( stringCoupon.getPrezzo() + "% " + stringCoupon.getQualeProdotto() );
            }
        }
        else if(stringCoupon.tipo.equals( "Prezzo" )){
            scont.setText(stringCoupon.getPrezzo() + " ,00€" );

            if(stringCoupon.getQualeProdotto().equals( context.getString( R.string.tutti ) )){
                titolo.setText( context.getString( R.string.scontoDi ) +" "+ stringCoupon.getPrezzo() + " ,00€ " + context.getString( R.string.sututtolimporto ));
            }else{
                titolo.setText( context.getString( R.string.scontoDi )+" " + stringCoupon.getPrezzo() + " ,00€ " + context.getString( R.string.su ) + stringCoupon.getQualeProdotto());
            }
        }





        return view;
    }
}
