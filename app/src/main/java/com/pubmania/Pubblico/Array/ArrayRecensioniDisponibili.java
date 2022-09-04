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
import com.pubmania.Pubblico.String.StringRecensioni;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArrayRecensioniDisponibili extends ArrayAdapter<StringRecensioni> {

    Activity context;
    ArrayList<StringRecensioni>arrayList;

    public ArrayRecensioniDisponibili(@NonNull Context context,ArrayList<StringRecensioni> arrayList) {
        super( context, 0, arrayList );
        this.context = (Activity) context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate( R.layout.layout_recensioni_profilo,null,false );

        StringRecensioni stringRecensioni = getItem( position );

        CircleImageView circleImageView = (CircleImageView) view.findViewById( R.id.circleImageView6 );
        TextView textView = (TextView) view.findViewById( R.id.textView52 );
        TextView email = (TextView) view.findViewById( R.id.textView56 );
        TextView idPost = (TextView) view.findViewById(R.id.textView69);
        idPost.setText(stringRecensioni.getId());
        email.setText( stringRecensioni.getEmailPub() );

        Glide.with( context ).load( stringRecensioni.getUrlFotoProfilo() ).into( circleImageView );
        textView.setText( stringRecensioni.getNomeLocale() );



        return view;
    }
}
