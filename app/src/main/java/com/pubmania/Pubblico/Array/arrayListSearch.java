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
import com.pubmania.Pubblico.String.doppiaStringa;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class arrayListSearch extends ArrayAdapter<doppiaStringa> {

    ArrayList<doppiaStringa> arrayList;
    Activity context;
    public arrayListSearch(@NonNull Context context, ArrayList<doppiaStringa> arrayList) {
        super(context, 0,arrayList);
        this.arrayList = arrayList;
        this.context = (Activity) context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.list_search,null,false);
        doppiaStringa stringa = getItem(position);
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.circleImageView7);
        TextView textView = (TextView) view.findViewById(R.id.textView74);
        TextView textViewEmail = (TextView) view.findViewById(R.id.textView75);
        Glide.with(context).load(stringa.getDue()).into(circleImageView);
        textView.setText(stringa.getUno());
        textViewEmail.setText(stringa.getTre());
        notifyDataSetChanged();


        return view;
    }
}
