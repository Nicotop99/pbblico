package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pubmania.Pubblico.Array.ArrayRecensioniDisponibili;
import com.pubmania.Pubblico.Array.ArraySegutiiProgilo;
import com.pubmania.Pubblico.Array.arrayCouponUtilizzati;
import com.pubmania.Pubblico.String.StringPost_coupon;
import com.pubmania.Pubblico.String.StringRecensioni;
import com.pubmania.Pubblico.String.Stringdoppia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfiloPubblico extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profilo_pubblico );
        email = "nicolino.oliverio@gmail.com";
        setCouponUtilizzati();
        setaccountSeguiti();
        setecensioniDisponibili();
        setProfile();
        setCambiaFoto();
        setMenuBasso();
        /*
        firebaseFirestore.collection(email+"Rec").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getId().equals("lQx35rBMpvVZRwiVT2dQ")){
                            StringRecensioni stringRecensioni = documentSnapshot.toObject(StringRecensioni.class);
                            for (int i = 0;i<50;i++){
                                firebaseFirestore.collection(email+"Rec").add(stringRecensioni).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if(task.isSuccessful()){
                                            DocumentReference documentReference = firebaseFirestore.collection(email+"Rec").document(task.getResult().getId());
                                            documentReference.update("id",task.getResult().getId());
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });



         */
        Log.e("dljsnadjnas","dijsoajdi");


    }


    BottomNavigationView bottomAppBar;
    FloatingActionButton searchButton;
    private void setMenuBasso() {

        searchButton = (FloatingActionButton) findViewById(R.id.floatBotton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), searchActivty.class));
            }
        });


        {

            bottomAppBar = (BottomNavigationView) findViewById( R.id.bottomNavView );
            bottomAppBar.setSelectedItemId(R.id.homeBotton);
            Menu menu = bottomAppBar.getMenu();
            firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task != null){
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            if(documentSnapshot.getString( "email" ).equals( email )){
                                menu.findItem(R.id.profile_bottom).setTitle( documentSnapshot.getString( "nome" ) );

                            }
                        }
                    }
                }
            } );
            bottomAppBar.setOnItemSelectedListener( new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.homeBotton:
                            startActivity( new Intent( getApplicationContext(), HomePage.class ) );
                            Log.d( "kfmdslfmlsd","fkdmlfk" );
                            finish();
                            arrayList.clear();
                            break;
                        case R.id.mapsBotton:
                            startActivity( new Intent( getApplicationContext(), vicinoAMe.class ) );
                            finish();
                            arrayList.clear();
                            break;
                        case R.id.profile_bottom:
                            startActivity( new Intent( getApplicationContext(), ProfiloPubblico.class ) );
                            finish();
                            arrayList.clear();
                    }
                    return true;
                }
            } );


        }
    }


    ImageView cambiaFoto;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    ImageButton editPhotoProfile;
    ImageView imageView;
    ImageButton salvaChangeFoto;
    String idEmail;
    ImageButton nuovaFoto;
    TextView salvaChangeFotoText;
    ConstraintLayout cFoto;
    Uri uriFotoProfilo;
    ConstraintLayout ccreafoto;
    private void setCambiaFoto() {
        cambiaFoto = (ImageView) findViewById(R.id.imageView39);
        cambiaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( ProfiloPubblico.this, R.style.MyDialogThemed );
// ...Irrelevant code for customizing the buttons and title

        LayoutInflater inflater = getLayoutInflater();
        View viewView = inflater.inflate( R.layout.layotu_cambia_foto_prodfilo, null );
        dialogBuilder.setView( viewView );
        AlertDialog alertDialog = dialogBuilder.create();
        imageView = (ImageView) viewView.findViewById( R.id.imageView14 ) ;
        Group group = (Group) viewView.findViewById( R.id.groupEditPhotoProfile );
        Group group2 = (Group) viewView.findViewById( R.id.group23 );
        nuovaFoto = (ImageButton) viewView.findViewById( R.id.imageButton32 );
        cFoto = (ConstraintLayout) viewView.findViewById(R.id.cfoto);
        ccreafoto = (ConstraintLayout) viewView.findViewById(R.id.cofotoNuova);
        SharedPreferences sharedPreferences = getSharedPreferences("fotoProfilo",MODE_PRIVATE);
        if(!sharedPreferences.getString("fotoProfilo","null").equals("null")) {
            Glide.with(ProfiloPubblico.this).load(sharedPreferences.getString("fotoProfilo", "null")).into(imageView);
        }else {
            Glide.with(ProfiloPubblico.this).load(urlFo).into(imageView);
        }
        nuovaFoto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType( "image/*" );
                intent.putExtra( Intent.EXTRA_ALLOW_MULTIPLE, true );
                intent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( intent, "Choose Picture" ), 4 );
            }
        } );
        salvaChangeFoto = (ImageButton) viewView.findViewById( R.id.imageButton33 ) ;
        salvaChangeFotoText = (TextView) viewView.findViewById( R.id.textView67 );
        salvaChangeFoto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group.setVisibility( View.VISIBLE );
                group2.setVisibility( View.GONE );
                ccreafoto.setVisibility(View.GONE);
                salvaChangeFoto.setVisibility( View.GONE );
                salvaChangeFotoText.setVisibility( View.GONE );
                firebaseFirestore.collection( "Pubblico" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task != null){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                if(documentSnapshot.getString( "email" ).equals( email )) {
                                    idEmail = documentSnapshot.getId();
                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    StorageReference storageReference = storage.getReference();
                                    StorageReference storageReference1 = storageReference.child( email + "/" + UUID.randomUUID().toString()  );
                                    storageReference1.putFile( uriFotoProfilo ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            storageReference1.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Pubblico" ).document(idEmail);

                                                    Glide.with(ProfiloPubblico.this).load(uri).into(fotoProfilo);
                                                    documentReference.update( "fotoProfilo", String.valueOf( uri ) ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            alertDialog.dismiss();
                                                            FirebaseStorage storagee = FirebaseStorage.getInstance();

                                                            SharedPreferences sharedPreferences = getSharedPreferences("fotoProfilo",MODE_PRIVATE);

                                                            StorageReference httpsReference = storagee.getReferenceFromUrl(String.valueOf(uri));
                                                            try {
                                                                final File localFile = File.createTempFile("Images", ".jpg");
                                                                httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                                        Log.d("janasnd", localFile.getAbsolutePath());
                                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                        editor.putString("fotoProfilo", localFile.getAbsolutePath());
                                                                        editor.commit();
                                                                        File imgFile = new File(localFile.getAbsolutePath());

                                                                        if (imgFile.exists()) {

                                                                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                                                            Drawable profileImage = new BitmapDrawable(getResources(), myBitmap);
                                                                            bottomAppBar.getMenu().getItem(4).setIcon(profileImage);


                                                                        }
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Log.d("ijnksaf", e.getMessage());
                                                                    }
                                                                });
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }

                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.fotoProfiloCambiata ),Snackbar.LENGTH_LONG ).show();
                                                        }
                                                    } ).addOnFailureListener( new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            group.setVisibility( View.GONE );
                                                            group2.setVisibility( View.VISIBLE );
                                                            salvaChangeFoto.setVisibility( View.VISIBLE );
                                                            salvaChangeFotoText.setVisibility( View.VISIBLE );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.erroreriprovapiutardi ),Snackbar.LENGTH_LONG ).show();

                                                        }
                                                    } );
                                                }
                                            } ).addOnFailureListener( new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Snackbar.make( findViewById( android.R.id.content ),getString( R.string.erroreriprovapiutardi ),Snackbar.LENGTH_LONG ).show();
                                                    group.setVisibility( View.GONE );
                                                    group2.setVisibility( View.VISIBLE );
                                                    salvaChangeFoto.setVisibility( View.VISIBLE );
                                                    salvaChangeFotoText.setVisibility( View.VISIBLE );
                                                }
                                            } );
                                        }
                                    } ).addOnFailureListener( new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.erroreriprovapiutardi ),Snackbar.LENGTH_LONG ).show();
                                            group.setVisibility( View.GONE );
                                            group2.setVisibility( View.VISIBLE );
                                            salvaChangeFoto.setVisibility( View.VISIBLE );
                                            salvaChangeFotoText.setVisibility( View.VISIBLE );
                                        }
                                    } );
                                }
                            }
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make( findViewById( android.R.id.content ),getString( R.string.erroreriprovapiutardi ),Snackbar.LENGTH_LONG ).show();

                    }
                } );;

            }
        } );



        alertDialog.show();
            }
        });
    }


    CircleImageView fotoProfilo;
    TextView nome;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String urlFo;
    private void setProfile() {
        fotoProfilo = (CircleImageView) findViewById( R.id.circleImageView4 );
        nome = (TextView) findViewById( R.id.textView44 );
        firebaseFirestore.collection( "Pubblico" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString( "email" ).equals( email )){
                            nome.setText( documentSnapshot.getString( "nome" ) + " " + documentSnapshot.getString( "cognome" ));
                            Glide.with( ProfiloPubblico.this ).load( documentSnapshot.getString( "fotoProfilo" ) ).into( fotoProfilo );
                            urlFo = documentSnapshot.getString("fotoProfilo");
                        }
                    }
                }
            }
        } );

    }

    ArrayList<String> tokenEmail = new ArrayList<>();
    GridView gridView;
    ArrayList<StringRecensioni> arrayString = new ArrayList<>();
    ArrayRecensioniDisponibili arrayRecensioniDisponibili;
    private void setecensioniDisponibili() {
        Log.e("dljsnadjnas","dijsoajdi");




        gridView = (GridView) findViewById( R.id.gridView );
        firebaseFirestore.collection( email+"Rec" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    Log.d("dljsnadjnas","dijsoajdi");
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : list){
                        StringRecensioni stringRecensioni = documentSnapshot.toObject( StringRecensioni.class );
                        arrayString.add( stringRecensioni );
                        arrayRecensioniDisponibili = new ArrayRecensioniDisponibili( ProfiloPubblico.this,arrayString );
                        gridView.setAdapter( arrayRecensioniDisponibili );
                        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Log.d("jkdnaskjnd", String.valueOf(adapterView.getAdapter().getItem(i)));
                                TextView textView = (TextView) view.findViewById( R.id.textView56 );
                                TextView idPo = (TextView) view.findViewById( R.id.textView69 );
                                firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()){
                                                if(documentSnapshot1.getString("email").equals(textView.getText().toString())){
                                                    tokenEmail = new ArrayList<>();
                                                    tokenEmail = (ArrayList<String>) documentSnapshot1.get("token");
                                                    Intent intent = new Intent(getApplicationContext(),FaiUnaRecensione.class);
                                                    intent.putExtra( "emailPub",textView.getText().toString()  );
                                                    intent.putExtra("idPost",idPo.getText().toString());
                                                    intent.putExtra("token",tokenEmail);
                                                    startActivity( intent );

                                                }
                                            }

                                        }
                                    }
                                });


                            }
                        } );


                    }
                }
                else{
                    Log.d("ojfnkfnsdn","odnsjandl");
                }
            }
        } );
    }


    ListView listPubSeguiti;
    ArrayList<Stringdoppia> arrayListSeguiti = new ArrayList<>();


    private void setaccountSeguiti() {
        listPubSeguiti = (ListView) findViewById( R.id.listaccountSeguiti );
        firebaseFirestore.collection( email+"follower" ).limit( 15 )
                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : list){
                        for (int i = 1;i<documentSnapshot.getData().size() +1;i++){
                            if(!documentSnapshot.getString( String.valueOf( i ) ).equals( "" )){
                                int finalI = i;
                                Log.d( "fskldfmskf",documentSnapshot.getString( String.valueOf( i ) ) );
                                firebaseFirestore.collection( "Professionisti")
                                        .get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()){
                                                if(documentSnapshot.getString( String.valueOf( finalI ) ).equals( documentSnapshot1.getString( "email" ) ) ){
                                                    Stringdoppia stringdoppia = new Stringdoppia();
                                                    stringdoppia.setUno( documentSnapshot1.getString( "nomeLocale" ) );
                                                    stringdoppia.setDue( documentSnapshot1.getString( "fotoProfilo" ) );
                                                    stringdoppia.setTre(documentSnapshot1.getString("email"));
                                                    arrayListSeguiti.add( stringdoppia );
                                                    ArraySegutiiProgilo arraySegutiiProgilo = new ArraySegutiiProgilo( ProfiloPubblico.this,arrayListSeguiti );
                                                    listPubSeguiti.setAdapter( arraySegutiiProgilo );
                                                    listPubSeguiti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                            TextView emailll = (TextView) view.findViewById(R.id.textView73);
                                                            Intent intent = new Intent(getApplicationContext(),Profile_Pub.class);
                                                            intent.putExtra("emailPub", emailll.getText().toString());
                                                            startActivity(intent);
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                } );
                            }
                        }
                    }

                }
            }
        } );


    }



    ListView listCouponUtilizzati;
    ArrayList<StringPost_coupon> arrayList = new ArrayList<>();
    private void setCouponUtilizzati() {
        listCouponUtilizzati = (ListView) findViewById( R.id.listCouponutilizzati );
        firebaseFirestore.collection( email+"Coupon" )
                .limit( 8 )
               .get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        firebaseFirestore.collection( documentSnapshot.getString( "emailPub" ) + "Post" )
                                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots != null){
                                    List<DocumentSnapshot> l  = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot documentSnapshot1 : l){
                                        StringPost_coupon stringPost_coupon = documentSnapshot1.toObject( StringPost_coupon.class );
                                        Log.d("sdfmòsdkmf",stringPost_coupon.getCategoria());
                                        if(stringPost_coupon.getId().equals( documentSnapshot.getString( "idPost" ) )){
                                            firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful()){
                                                        for (QueryDocumentSnapshot d :task.getResult()){
                                                            if(d.getString( "email" ).equals( documentSnapshot.getString( "emailPub" ) )){
                                                                arrayList.add( stringPost_coupon );
                                                                arrayCouponUtilizzati arrayCouponUtilizzati = new arrayCouponUtilizzati( ProfiloPubblico.this,arrayList,d.getString( "nomeLocale" ),d.getString( "fotoProfilo" ) );
                                                                listCouponUtilizzati.setAdapter( arrayCouponUtilizzati );


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
                }
            }
        } );



    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent(getApplicationContext(),HomePage.class) );

        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 4){
                uriFotoProfilo = data.getData();
                Glide.with(ProfiloPubblico.this).load(data.getData()).into(imageView);
                salvaChangeFoto.setVisibility( View.VISIBLE );
                salvaChangeFotoText.setVisibility( View.VISIBLE );
                cFoto.setVisibility(View.VISIBLE);
                salvaChangeFoto.setImageTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.succesGreen));
                nuovaFoto.setImageTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.grigio));

            }
        }
    }
}