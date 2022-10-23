package com.pubmania.Pubblico.Array;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pubmania.Pubblico.R;
import com.pubmania.Pubblico.String.StringProdotto;

import java.util.ArrayList;

public class ArraySearchprodotti extends ArrayAdapter<StringProdotto> {

    Activity context;
    ArrayList<StringProdotto> arrayList;
    String boh;


    public ArraySearchprodotti(@NonNull Context context, ArrayList<StringProdotto> arrayList, String boh) {
        super( context, 0, arrayList );
        this.context = (Activity) context;
        this.arrayList = arrayList;
        this.boh = boh;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate( R.layout.array_list_search,null,false );
        TextView textView = (TextView) view.findViewById(R.id.textView21);
        TextView idText = (TextView) view.findViewById(R.id.textView42);
        if(boh.equals("no")) {
            StringProdotto stringProdotto = getItem(position);

            textView.setText(stringProdotto.getNome());
            idText.setText(stringProdotto.getId());
        }
        else{
            Log.d("dnasjlda","dnmasljd");
            textView.setText(context.getString(R.string.nessuprodotto));
            idText.setText("s");
        }
        return view;
    }
}
