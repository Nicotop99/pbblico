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
import androidx.annotation.StringDef;

import com.bumptech.glide.Glide;
import com.pubmania.Pubblico.R;
import com.pubmania.Pubblico.String.Stringdoppia;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArraySegutiiProgilo extends ArrayAdapter<Stringdoppia> {


    Activity context;
    ArrayList<Stringdoppia> arrayList;

    public ArraySegutiiProgilo(@NonNull Context context, ArrayList<Stringdoppia> arrayList) {
        super( context, 0,arrayList);
        this.context = (Activity) context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate( R.layout.layout_pub_segutii,null,false );

        Stringdoppia stringdoppia = getItem( position );
        CircleImageView circleImageView = (CircleImageView) view.findViewById( R.id.circle );
        TextView nome = (TextView) view.findViewById( R.id.textView51 );
        nome.setText(stringdoppia.getUno());
        Glide.with( context ).load( stringdoppia.getDue() ).into( circleImageView );







        return view;
    }
}
