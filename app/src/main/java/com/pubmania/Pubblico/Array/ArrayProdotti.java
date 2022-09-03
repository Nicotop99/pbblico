package com.pubmania.Pubblico.Array;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.pubmania.Pubblico.Profile_Pub;
import com.pubmania.Pubblico.R;
import com.pubmania.Pubblico.String.StringProdotto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArrayProdotti extends ArrayAdapter<StringProdotto> {
    Activity contenxt;
    ArrayList<StringProdotto> arrayList;

    public ArrayProdotti(@NonNull Context context, ArrayList<StringProdotto> arrayList) {
        super( context, 0, arrayList);

        this.contenxt = (Activity) context;
        this.arrayList = arrayList;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = contenxt.getLayoutInflater();
        View view = layoutInflater.inflate( R.layout.array_prodotti_profilo_pub,null,false );
        StringProdotto stringProdotto = getItem( position );


        TextView titolo = (TextView) view.findViewById( R.id.textView38 );
        TextView prezzo = (TextView) view.findViewById( R.id.textView39 );
        TextView ingredienti = (TextView) view.findViewById( R.id.textView40 );
        ListView listIngredienti = (ListView) view.findViewById( R.id.list_prodotti );
        CircleImageView circleImageView = (CircleImageView) view.findViewById( R.id.profile_image );

        titolo.setText( stringProdotto.getNome() );
        prezzo.setText( stringProdotto.getPrezzo() + " ,00 €" );
        if(stringProdotto.getIngredienti().size() >0){
            ingredienti.setVisibility( View.VISIBLE );

            ArrayList<String> a = new ArrayList<>();
            for (int i = 0;i<stringProdotto.getIngredienti().size();i++){
                a.add( stringProdotto.getIngredienti().get( i ) );
            }

            ArrayIngredienti arraySearchprodotti = new ArrayIngredienti( contenxt,a );
            listIngredienti.setAdapter( arraySearchprodotti );

        }else{
            listIngredienti.setVisibility( View.GONE );



        }
        if(stringProdotto.getFoto() != null){
            if(stringProdotto.getFoto().size() >0) {
                Glide.with( contenxt )
                        .load( stringProdotto.getFoto().get( 0 ) )
                        .into( circleImageView );

            }else{
                circleImageView.setVisibility( View.GONE );

            }
        }else{
            circleImageView.setVisibility( View.GONE );
        }






        return view;
    }
}
