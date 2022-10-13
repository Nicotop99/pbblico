package com.pubmania.Pubblico.Array;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.Pubblico.HomePage;
import com.pubmania.Pubblico.Profile_Pub;
import com.pubmania.Pubblico.R;
import com.pubmania.Pubblico.String.StringPost_coupon;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArrayHome extends ArrayAdapter<StringPost_coupon> {

    Activity context;
    ArrayList<StringPost_coupon> arrayList;
    String nomePub;
    String uri,emailPub,email;

    public ArrayHome(@NonNull Context context, ArrayList<StringPost_coupon> arrayList,String email) {
        super( context, 0, arrayList );
        this.email = email;
        this.arrayList = arrayList;
        this.context = (Activity) context;

    }


int count = 5;
    int c = 1;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate( R.layout.array_home,null,false );
        StringPost_coupon stringPost_coupon = getItem( position );
        ConstraintLayout layourPost = (ConstraintLayout) view.findViewById( R.id.layoutPost );
        ConstraintLayout layoutCoupon = (ConstraintLayout) view.findViewById( R.id.layoutCoupon );
        ConstraintLayout layoutplus = (ConstraintLayout) view.findViewById( R.id.conspluss );
        TextView prezzo = (TextView) view.findViewById( R.id.textView49 );
        TextView titolo = (TextView) view.findViewById( R.id.textView54 );
        CircleImageView photoProfiloCoupon = (CircleImageView) view.findViewById( R.id.circleImageView3 );
        TextView nomePubCoupon = (TextView) view.findViewById( R.id.textView20 );
        TextView textView = (TextView) view.findViewById( R.id.textView16 );
        Log.d( "psdmfklsmd", String.valueOf( arrayList.size() ) );
        TextView nomePubPost = (TextView) view.findViewById( R.id.textView12 );
        ImageSlider postImage = (ImageSlider) view.findViewById( R.id.image_slider );
        CircleImageView photoProfilePost = (CircleImageView) view.findViewById( R.id.circleImageView2 );
        Log.d("jjjjjjjjj","primo");

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task != null) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){


                        if(stringPost_coupon.getEmail().equals( documentSnapshot.getString( "email" ) )) {
                            emailPub = documentSnapshot.getString( "email" );
                            uri = documentSnapshot.getString( "fotoProfilo" );
                            Log.d("dndmdmmd" ,uri);
                            nomePub = documentSnapshot.getString( "nomeLocale" );
                            Log.d( "osefmodsf",nomePub + "a" );
                            textView.setText( emailPub );
                            PackageManager packageManager = context.getPackageManager();
                            try {
                                ActivityInfo info = packageManager.getActivityInfo(context.getComponentName(), 0);
                                Log.e("ssfdsfsdfsdf", "Activity name:" + info.packageName);

                                if(info.name.equals( "com.pubmania.Pubblico.HomePage" )){
                                    Glide.with( context ).load( uri  ).into( photoProfilePost );
                                    Glide.with( context ).load( uri  ).into( photoProfiloCoupon );
                                }


                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                                Log.d("Ojdnkjsadja",e.getMessage());
                            }

                            nomePubPost.setText( nomePub );
                            nomePubCoupon.setText( nomePub );
                        }

                    }
                }
            }
        } ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("jjjjjjjjj",e.getMessage());
            }
        });

        ImageView plusBotton = (ImageView) view.findViewById( R.id.imageView27 );


        TextView desc = (TextView) view.findViewById( R.id.textView15 );
        postImage.setItemClickListener( new ItemClickListener() {
            @Override
            public void onItemSelected(int in) {
                Intent i = new Intent(context, Profile_Pub.class );
                i.putExtra( "emailPub", emailPub );
                context.startActivity( i );
            }
        } );
        postImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Profile_Pub.class );
                i.putExtra( "emailPub", emailPub );
                context.startActivity( i );
            }
        } );

        plusBotton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = 1;
                arrayList.remove( position );
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection(email+"follower"  ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task != null){
                            Log.d("pfmdskfmsdkfm", String.valueOf( task.getResult().size() ) );
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                                for (int i = 0; i<documentSnapshot.getData().size();i++){
                                    Log.d( "psfkpsdkm", String.valueOf( documentSnapshot.getData().get( "1" )) );
                                    int finalI = i +1;
                                    firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task != null){
                                                Log.d("pfmdskfmsdkfm","3");

                                                for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()){

                                                    if(documentSnapshot1.getString( "email" ).equals( documentSnapshot.getData().get( String.valueOf( finalI ) ) )){


                                                        firebaseFirestore.collection( documentSnapshot.getData().get( String.valueOf( finalI ) ) + "Post" )
                                                                .limit( count +1 )
                                                                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                if(queryDocumentSnapshots.size() >0){
                                                                    Log.d("pfmdskfmsdkfm","5");
                                                                    Log.d( "psfkpssssssssdkm", String.valueOf( documentSnapshot.getData().get( String.valueOf( finalI ) )) );

                                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                                    for (DocumentSnapshot documentSnapshot1 : list){
                                                                        c+=1;
                                                                        if(count < c ){
                                                                            Log.d( "kfmldsaòaaaa",count + " " + c );
                                                                            StringPost_coupon stringPost_coupon = documentSnapshot1.toObject( StringPost_coupon.class );
                                                                            arrayList.add( stringPost_coupon );
                                                                        }


                                                                    }
                                                                    count +=1;

                                                                    StringPost_coupon stringPost_coupon = new StringPost_coupon();
                                                                    stringPost_coupon.setCategoria( "plus" );
                                                                    arrayList.add( stringPost_coupon );
                                                                    notifyDataSetChanged();
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
                    }
                } );

            }
        } );




        Log.d( "fdsòfmò",stringPost_coupon.getCategoria() );
        if(stringPost_coupon.getCategoria().equals( "Coupon" )){
            Log.d("mfdskmflskdmf","pf,dsò");

            layoutCoupon.setVisibility( View.VISIBLE );

            prezzo.setText( stringPost_coupon.getPrezzo() );
            if(stringPost_coupon.tipo.equals( "Percentuale" )){
                prezzo.setText(stringPost_coupon.getPrezzo() + " %" );

                if(stringPost_coupon.getQualeProdotto().equals( context.getString( R.string.tutti ) )){
                    titolo.setText( stringPost_coupon.getPrezzo() + "% " + context.getString( R.string.sututtiiprodotti ));
                }else{
                    titolo.setText( stringPost_coupon.getPrezzo() + "% " + stringPost_coupon.getQualeProdotto() );
                }
            }
            else if(stringPost_coupon.tipo.equals( "Prezzo" )){
                prezzo.setText(stringPost_coupon.getPrezzo() + " ,00€" );

                if(stringPost_coupon.getQualeProdotto().equals( context.getString( R.string.tutti ) )){
                    titolo.setText( context.getString( R.string.scontoDi ) +" "+ stringPost_coupon.getPrezzo() + " ,00€ " + context.getString( R.string.sututtolimporto ));
                }else{

                    titolo.setText( context.getString( R.string.scontoDi )+" " + stringPost_coupon.getPrezzo() + " ,00€ " + context.getString( R.string.su ) + stringPost_coupon.getQualeProdotto());
                }
            }

        }else if(stringPost_coupon.getCategoria().equals( "Post" )){
            layourPost.setVisibility( View.VISIBLE );
            List<SlideModel> slideModels = new ArrayList<>();
            for (int i = 0;i<stringPost_coupon.getFoto().size();i++ ) {
                slideModels.add(new SlideModel(stringPost_coupon.getFoto().get( i ), ScaleTypes.CENTER_INSIDE));
            }
            postImage.setImageList(slideModels);
            desc.setText( stringPost_coupon.getDescrizione() );

        }else if(stringPost_coupon.getCategoria().equals( "plus" )){
            layoutplus.setVisibility( View.VISIBLE );
        }


        return view;
    }
}
