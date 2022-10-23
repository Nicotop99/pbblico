package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class FaiUnaRecensione extends AppCompatActivity {

    String email;
    public static String emailPub,idPost;
    public static ArrayList<String> token;
    TextView rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fai_una_recensione );
        email = "nicolino.oliverio@gmail.com";
        emailPub = getIntent().getExtras().getString( "emailPub" );
        idPost = getIntent().getExtras().getString("idPost");
        token = (ArrayList<String>) getIntent().getExtras().get("token");
        Log.d("kdnfkanjkn",token.size() + " ");
        rec = (TextView) findViewById( R.id.textView58 );
        Log.d("odsmflsdm",emailPub);
        chooseImage();
        setAvanti();
        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot d : task.getResult()){
                        if(d.getString( "email" ).equals( emailPub )){
                            rec.setText( d.getString( "nomeLocale" ) );
                        }
                    }
                }
            }
        } );
    }
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    TextInputEditText t_desc,t_titolo;
    public static String titolo,desc;
    TextInputLayout l_desc,l_titolo;
    ImageView avanti;
    int d = 0;

    private void setAvanti() {
        l_titolo = (TextInputLayout) findViewById( R.id.layoutPass );
        l_desc = (TextInputLayout) findViewById( R.id.layoutdesc );
        t_titolo = (TextInputEditText) findViewById( R.id.textEmail );
        t_desc = (TextInputEditText) findViewById( R.id.textdesc );
        avanti = (ImageView) findViewById( R.id.imageView46 );
        avanti.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titolo = t_titolo.getText().toString();
                desc = t_desc.getText().toString();

                Intent i = new Intent(getApplicationContext(), FaiUnaRecensione2.class);
                startActivity( i );

            }
        } );
    }

    ImageView chosseImage;
    ImageSlider imageSlider;
    private void chooseImage() {
        chosseImage = (ImageView) findViewById( R.id.imageView45 );
        imageSlider = (ImageSlider) findViewById( R.id.imageSlider );
        chosseImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 99);
            }
        } );
    }

   public static ArrayList<SlideModel> arrayList = new ArrayList<>();
    public static ArrayList<String> arrayUri = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if(resultCode == RESULT_OK && data != null){
            if(requestCode == 99){
                arrayUri = new ArrayList<>();
                ClipData selectedimg = data.getClipData();
                arrayList = new ArrayList<>();
                if(selectedimg == null){
                    //selezionata 1 immagine
                    arrayList.add( new SlideModel( data.getData().toString() , ScaleTypes.CENTER_INSIDE ));
                    arrayUri.add(String.valueOf(data.getData()));
                    imageSlider.setImageList( arrayList );

                }else{
                    for (int i = 0;i<selectedimg.getItemCount();i++){
                        ClipData.Item item = selectedimg.getItemAt( i );
                        arrayList.add( new SlideModel( String.valueOf( item.getUri() ), ScaleTypes.CENTER_INSIDE ));

                        arrayUri.add(String.valueOf(item.getUri()));
                    }
                    imageSlider.setImageList( arrayList );
                }
            }
        }


    }
}