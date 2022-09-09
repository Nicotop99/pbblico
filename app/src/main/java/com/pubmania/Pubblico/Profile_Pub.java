package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.Pubblico.Array.ArrayIngredienti;
import com.pubmania.Pubblico.Array.ArrayProdotti;
import com.pubmania.Pubblico.Array.ArraySearchprodotti;
import com.pubmania.Pubblico.String.StringCoutVisite;
import com.pubmania.Pubblico.String.StringPost_coupon;
import com.pubmania.Pubblico.String.StringProdotto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Profile_Pub extends AppCompatActivity {

    String emailPub;
    String email;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile__pub );
        HomePage.click = false;
        email = "nicolino.oliverio@gmail.com";
        emailPub = getIntent().getExtras().getString( "emailPub" );

        Log.d( "fòkdmskfm",emailPub );
        setImageSlider();
        setImageButton();
        setSearch();
        setTop();
        setButtonprofile();
        setVisite();

        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
    }

    private void setVisite() {

    }


    ImageView i_segui, i_indicazioni, i_recensioni, i_coupon,i_orario;
    TextView seguiText;
    double latitude,longitude;
    String viaLocale;
    int followeInt = 0;
    String idFol;
    TextInputEditText tnomeLocale,taLunedi,tamartedi,tamercoledi,tagiovedi,tavenerdi,tasabato,tadomenica,tclunedi,tcmartedi,tcmercoledi,tcgiovedi,tcvenerdi,tcsabato,tcdomenica;

    private void setButtonprofile() {
        i_segui = (ImageView) findViewById( R.id.imageButton6 );
        i_indicazioni = (ImageView) findViewById( R.id.imageButton5 );
        i_recensioni = (ImageView) findViewById( R.id.imageButton4 );
        i_coupon = (ImageView) findViewById( R.id.imageButton3 );
        seguiText = (TextView) findViewById( R.id.textView12 );
        i_orario = (ImageView) findViewById( R.id.imageButton90 );
        followeInt = 0;
        i_segui.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d( "fdskòm", ",,,l" );
                if (seguiText.getText().toString().equals( getString( R.string.segui ) )) {
                    seguiText.setText( getString( R.string.smettidiseguire ) );
                    firebaseFirestore.collection( email + "follower" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task != null) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    followeInt = documentSnapshot.getData().size() + 1;
                                    idFol = documentSnapshot.getId();
                                }


                                DocumentReference documentReference = firebaseFirestore.collection( email + "follower" ).document( idFol );
                                documentReference.update( String.valueOf( followeInt ), emailPub ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("emailCliente", email);
                                        user.put("emailPub", emailPub);
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                                        String currentDateandTime = sdf.format(new Date());
                                        user.put( "ora" ,currentDateandTime);
                                        firebaseFirestore.collection( emailPub+"follower" ).add( user );
                                    }
                                } ).addOnFailureListener( new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        seguiText.setText( getString( R.string.segui ) );

                                    }
                                } );
                            }
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            seguiText.setText( getString( R.string.segui ) );

                        }
                    } );
                } else {
                    seguiText.setText( getString( R.string.segui ) );

                    firebaseFirestore.collection( email + "follower" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task != null) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    for (int i = 0; i < documentSnapshot.getData().size(); i++) {
                                        Log.d( "lkfmdslmfl",documentSnapshot.getData().size() + " " +(i+1));
                                        if (documentSnapshot.getData().get( String.valueOf( i + 1 ) ).equals( emailPub )) {
                                            idFol = documentSnapshot.getId();
                                            followeInt = i +1;
                                        }
                                    }
                                }


                                DocumentReference documentReference = firebaseFirestore.collection( email + "follower" ).document( idFol );
                                documentReference.update( String.valueOf( followeInt ), "" ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        firebaseFirestore.collection( emailPub+"follower" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful()){
                                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                        if(documentSnapshot.getString( "emailCliente" ).equals( email )){
                                                            DocumentReference documentReference1 = firebaseFirestore.collection( emailPub+"follower" ).document(documentSnapshot.getId());
                                                            documentReference1.delete();
                                                        }
                                                    }
                                                }
                                            }
                                        } );
                                    }
                                } ).addOnFailureListener( new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        seguiText.setText( getString( R.string.segui ) );

                                    }
                                } );
                            }
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            seguiText.setText( getString( R.string.segui ) );

                        }
                    } );
                }
            }
        } );


        i_indicazioni.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task != null) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (documentSnapshot.getString( "email" ).equals( emailPub )) {
                                    viaLocale = documentSnapshot.getString( "viaLocale" );


                                    if (ActivityCompat.checkSelfPermission( Profile_Pub.this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( Profile_Pub.this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions( Profile_Pub.this,
                                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                                1 );
                                        return;
                                    }
                                    locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                                        @Override
                                        public void onLocationChanged(@NonNull Location location) {
                                            Geocoder geocoder = new Geocoder( getApplicationContext() );
                                            List<Address> addresses = null;
                                            try {
                                                addresses = geocoder.getFromLocationName( viaLocale, 1 );
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
                                            startActivity( intent );
                                        }
                                    } );


                                }
                            }
                        }
                    }
                } );

            }
        } );

        i_coupon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( getApplicationContext(), coupon_pub.class );
                i.putExtra( "emailPub", emailPub );
                startActivity( i );
            }
        } );


        i_orario.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Profile_Pub.this,R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View viewView = inflater.inflate( R.layout.orario_pub, null );
                dialogBuilder.setView( viewView );
                AlertDialog alertDialogg = dialogBuilder.create();
                alertDialogg.show();
                alertDialogg.setOnKeyListener( new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        if (i == KeyEvent.KEYCODE_BACK
                                && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            // TODO do the "back pressed" work here

                            alertDialogg.dismiss();
                            return true;
                        }
                        return false;
                    }
                } );
                tnomeLocale = (TextInputEditText) viewView.findViewById( R.id.textEmail );
                taLunedi = (TextInputEditText)viewView. findViewById( R.id.textalunedi);
                tamartedi = (TextInputEditText) viewView.findViewById( R.id.textamartedi);
                tamercoledi = (TextInputEditText)viewView. findViewById( R.id.textamercoledi);
                tagiovedi = (TextInputEditText)viewView. findViewById( R.id.textagiovedi);
                tavenerdi = (TextInputEditText)viewView. findViewById( R.id.textavenerdi);
                tasabato = (TextInputEditText) viewView.findViewById( R.id.textasabato);
                tadomenica = (TextInputEditText) viewView.findViewById( R.id.textadomenica);
                tclunedi = (TextInputEditText) viewView.findViewById( R.id.textcLunedi);
                tcmartedi = (TextInputEditText)viewView. findViewById( R.id.textcMartedi);
                tcmercoledi = (TextInputEditText)viewView. findViewById( R.id.textcMercoledi);
                tcgiovedi = (TextInputEditText)viewView. findViewById( R.id.textcGiovedi);
                tcvenerdi = (TextInputEditText)viewView. findViewById( R.id.textcVenerdi);
                tcsabato = (TextInputEditText)viewView. findViewById( R.id.textcSabato);
                tcdomenica = (TextInputEditText)viewView. findViewById( R.id.textcDomenica);
                    firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                    if(documentSnapshot.getString( "email" ).equals( emailPub )){


                                        String aLunedi = documentSnapshot.getString( "aLunedi" );
                                        String aMartedi = documentSnapshot.getString( "aMartedi" );
                                        String aMercoledi = documentSnapshot.getString( "aMercoledi" );
                                        String aGiovedi = documentSnapshot.getString( "aGiovedi" );
                                        String aVenerdi = documentSnapshot.getString( "aVenerdi" );
                                        String aSabato = documentSnapshot.getString( "aSabato" );
                                        String aDomenica = documentSnapshot.getString( "aDomenica" );
                                        String cLunedi = documentSnapshot.getString( "cLunedi" );
                                        String cMartedi = documentSnapshot.getString( "cMartedi" );
                                        String cMercoledi = documentSnapshot.getString( "cMercoledi" );
                                        String cGiovedi = documentSnapshot.getString( "cGiovedi" );
                                        String cVenerdi = documentSnapshot.getString( "cVenerdi" );
                                        String cSabato = documentSnapshot.getString( "cSabato" );
                                        String cDomenica = documentSnapshot.getString( "cDomenica" );
                                        {
                                            if (aLunedi != null) {
                                                taLunedi.setText( aLunedi );
                                            }
                                            if (aMartedi != null) {
                                                tamartedi.setText( aMartedi );
                                            }
                                            if (aMercoledi != null) {
                                                tamercoledi.setText( aMercoledi );
                                            }
                                            if (aGiovedi != null) {
                                                tagiovedi.setText( aGiovedi );
                                            }
                                            if (aVenerdi != null) {
                                                tavenerdi.setText( aVenerdi );
                                            }
                                            if (aSabato != null) {
                                                tasabato.setText( aSabato );
                                            }
                                            if (aDomenica != null) {
                                                tadomenica.setText( aDomenica );
                                            }
                                            if (cLunedi != null) {
                                                tclunedi.setText( cLunedi );
                                            }
                                            if (cMartedi != null) {
                                                tcmartedi.setText( cMartedi );
                                            }
                                            if (cMercoledi != null) {
                                                tcmercoledi.setText( cMercoledi );
                                            }
                                            if (cGiovedi != null) {
                                                tcgiovedi.setText( cGiovedi );
                                            }
                                            if (cVenerdi != null) {
                                                tcvenerdi.setText( cVenerdi );
                                            }
                                            if (cSabato != null) {
                                                tcsabato.setText( cSabato );
                                            }
                                            if (cDomenica != null) {
                                                tcdomenica.setText( cDomenica );
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } );




            }
        } );

    }


    TextView followe,recensioni;
    boolean segui = false;
    private void setTop() {
        followe = (TextView) findViewById( R.id.textView18 );

        firebaseFirestore.collection( email+"follower" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task != null){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                        for (int i = 0;i<documentSnapshot.getData().size();i++){
                            int l = i+1;

                            if(documentSnapshot.getData().get( String.valueOf( l ) ) != null) {
                                if (documentSnapshot.getData().get( String.valueOf( l ) ).equals( emailPub )) {
                                    seguiText.setText( getString( R.string.smettidiseguire ) );
                                    segui = true;
                                } else {
                                    seguiText.setText( getString( R.string.segui ) );
                                    segui = false;
                                }
                            }
                        }

                    }
                    firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task != null){
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                    if(documentSnapshot.getString( "email" ).equals( emailPub )){
                                        followe.setText( documentSnapshot.getString( "follower" ) + " follower");
                                        DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());

                                        if(documentSnapshot.getString( "personeCheTiHannoVisitato" ) == null){
                                            documentReference.update( "personeCheTiHannoVisitato","1" );
                                        }else{
                                            int person = Integer.parseInt( documentSnapshot.getString( "personeCheTiHannoVisitato" ) ) + 1;

                                            documentReference.update( "personeCheTiHannoVisitato",String.valueOf( person ) );
                                        }
                                        StringCoutVisite stringCoutVisite = new StringCoutVisite(  );
                                        stringCoutVisite.setEmailCliente( email );
                                        stringCoutVisite.setEmailPub( emailPub );
                                        stringCoutVisite.setTisegue( String.valueOf( segui ) );
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                                        String currentDateandTime = sdf.format(new Date());
                                        stringCoutVisite.setOra( currentDateandTime );
                                        firebaseFirestore.collection( emailPub+"Dash" ).add( stringCoutVisite ).addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if(task.isSuccessful()){
                                                    DocumentReference documentReference1 = firebaseFirestore.collection( emailPub+ "Dash" ).document(task.getResult().getId());
                                                    documentReference1.update( "id",task.getResult().getId() );
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
        } );


    }


    TextInputEditText t_search;
    Group groupSearch;
    ArrayList<StringProdotto> arrayProdottis;
    ListView listSearch;
    ImageView tran;
    private void setSearch() {
        t_search = (TextInputEditText) findViewById( R.id.textPass );
        groupSearch = (Group) findViewById( R.id.group );
        listSearch = (ListView) findViewById( R.id.list_ricerca_articolo );
        tran = (ImageView) findViewById( R.id.imageView25 );
        tran.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(groupSearch.getVisibility() == View.VISIBLE){
                    groupSearch.setVisibility( View.GONE );
                    InputMethodManager imm = (InputMethodManager) getSystemService( Context.
                            INPUT_METHOD_SERVICE );
                    imm.hideSoftInputFromWindow( t_search.getWindowToken(), 0 );
                    t_search.clearFocus();
                }
                Log.d( "kfmkòdmsf","pfmdks" );
            }
        } );
        t_search.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(groupSearch.getVisibility() == View.VISIBLE){

                }else{
                    groupSearch.setVisibility( View.VISIBLE );
                }
                arrayProdottis = new ArrayList<>();
                arrayProdottis.clear();
                listSearch.setAdapter( null );
                firebaseFirestore.collection( emailPub ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot l: list){
                                if (l.getId().contains( charSequence.toString() ) == true) {
                                    StringProdotto stringProdotto = l.toObject( StringProdotto.class );

                                    // after getting data from Firebase we are
                                    // storing that data in our array list
                                    arrayProdottis.add( stringProdotto );
                                } else {
                                    Log.d( "pfdòs","fkd" );
                                }

                                ArraySearchprodotti arraySearchprodotti = new ArraySearchprodotti( Profile_Pub.this,arrayProdottis );
                                listSearch.setAdapter( arraySearchprodotti );
                                listSearch.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        TextView idText = (TextView) view.findViewById( R.id.textView42 );
                                        String idPost = idText.getText().toString();
                                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Profile_Pub.this,R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title
                                        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                        View viewView = inflater.inflate( R.layout.layout_item_list_profile_pub, null );
                                        dialogBuilder.setView( viewView );
                                        AlertDialog alertDialogg = dialogBuilder.create();

                                        TextView titolo = (TextView) viewView.findViewById( R.id.textView36 );
                                        TextView prezzo = (TextView) viewView.findViewById( R.id.textView41 );
                                        Spinner spinner = (Spinner) viewView.findViewById( R.id.spinner );
                                        spinner.setEnabled( false );
                                        ListView listngredienti = (ListView) viewView.findViewById( R.id.listingr );
                                        ImageSlider imageSlider = (ImageSlider) viewView.findViewById( R.id.image_slider );
                                        TextView ing = (TextView) viewView.findViewById( R.id.textView43);

                                        firebaseFirestore.collection( emailPub ).document(idPost).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    titolo.setText(task.getResult().getString( "nome" ));
                                                    prezzo.setText( task.getResult().getString( "prezzo" ) + " ,00€" );
                                                    if(task.getResult().getString( "categoria" ).equals( "Cocktail" )){
                                                    }else if(task.getResult().getString( "categoria" ).equals( "Bevande" )){
                                                        spinner.setSelection( 3 );
                                                    }
                                                    else if(task.getResult().getString( "categoria" ).equals( "Dolci" )){
                                                        spinner.setSelection( 1 );
                                                    }
                                                    else if(task.getResult().getString( "categoria" ).equals( "Salati" )){
                                                        spinner.setSelection( 2 );
                                                    }

                                                    ArrayList<String> group = (ArrayList<String>) task.getResult().get("ingredienti");
                                                    ArrayList<String> fotoList = (ArrayList<String>) task.getResult().get("foto");
                                                    ArrayList<SlideModel> arrMod = new ArrayList<>();

                                                    Log.d( "mldkmkdmd", String.valueOf( task.getResult().get("ingredienti") ) );

                                                        if (group != null) {
                                                            if(group.size() >0){
                                                                ArrayIngredienti arraySearchprodotti = new ArrayIngredienti( Profile_Pub.this, group );
                                                                listngredienti.setAdapter( arraySearchprodotti );
                                                            }else {
                                                                listngredienti.setVisibility( View.GONE );
                                                                ing.setVisibility( View.GONE );
                                                            }

                                                        } else {
                                                            listngredienti.setVisibility( View.GONE );
                                                            ing.setVisibility( View.GONE );
                                                        }


                                                        if (fotoList != null) {
                                                            if(fotoList.size() >0) {
                                                                for (int i = 0; i < fotoList.size(); i++) {

                                                                    arrMod.add( new SlideModel( fotoList.get( i ), ScaleTypes.CENTER_CROP ) );
                                                                }
                                                                imageSlider.setImageList( arrMod );

                                                            }
                                                            else {
                                                                imageSlider.setVisibility( View.GONE );
                                                            }
                                                        } else {
                                                            imageSlider.setVisibility( View.GONE );
                                                        }

                                                    }
                                                }

                                        } );



                                        alertDialogg.setOnKeyListener( new DialogInterface.OnKeyListener() {
                                            @Override
                                            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                                                if (i == KeyEvent.KEYCODE_BACK
                                                        && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                                                    // TODO do the "back pressed" work here

                                                    alertDialogg.dismiss();
                                                    return true;
                                                }
                                                return false;
                                            }
                                        } );
                                        alertDialogg.show();
                                    }
                                } );
                            }
                        }
                    }
                } );





            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );

    }

    ImageButton cocktailImage,dolciImage,salatiImage,bevandeImage;
    ConstraintLayout consCat,conOgg;
    String cat;
    ArrayList<StringProdotto> arrayList = new ArrayList<>();
    ListView listView;
    ArrayList<Integer> coutProdotti = new ArrayList<>();
    TextView titloC;
    TextView nessunProdotto;
    private void setImageButton() {
        cocktailImage = (ImageButton) findViewById( R.id.imageButton12 );
        dolciImage = (ImageButton) findViewById( R.id.imageButton11 );
        salatiImage = (ImageButton) findViewById( R.id.imageButton13 );
        bevandeImage = (ImageButton) findViewById( R.id.imageButton14 );
        consCat = (ConstraintLayout) findViewById( R.id.constraintLayout );
        conOgg = (ConstraintLayout) findViewById( R.id.constraintLayout2 );
        listView = (ListView) findViewById( R.id.list_cockta );
        nessunProdotto = (TextView) findViewById( R.id.textView19 );
        titloC = (TextView) findViewById( R.id.textView37 );

        cocktailImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat = "Cocktail";
                titloC.setText( getString( R.string.cocktail ) );
                arrayList.clear();
                coutProdotti.clear();
                Log.d( "kmfld",emailPub );
                nessunProdotto.setVisibility( View.GONE );

                conOgg.setVisibility( View.VISIBLE );
                consCat.setVisibility( View.GONE );
                firebaseFirestore.collection( emailPub ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(queryDocumentSnapshots != null){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot l:list){
                                StringProdotto stringPost_coupon = l.toObject( StringProdotto.class );
                                coutProdotti.add( 1 );

                                if(stringPost_coupon.getCategoria().equals( cat )){
                                    arrayList.add( stringPost_coupon );
                                    ArrayProdotti arrayProdotti = new ArrayProdotti( Profile_Pub.this, arrayList );
                                    listView.setAdapter( arrayProdotti );
                                }

                                if(arrayList.size() == 0 && coutProdotti.size() == queryDocumentSnapshots.size()){
                                    //nessun prodott
                                    nessunProdotto.setVisibility( View.VISIBLE );
                                }

                            }
                        }


                    }
                } );
            }
        } );
        dolciImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat = "Dolci";
                arrayList.clear();
                coutProdotti.clear();
                titloC.setText( getString( R.string.dolci ) );
                nessunProdotto.setVisibility( View.GONE );

                Log.d( "kmfld",emailPub );
                conOgg.setVisibility( View.VISIBLE );
                consCat.setVisibility( View.GONE );
                firebaseFirestore.collection( emailPub ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(queryDocumentSnapshots != null){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot l:list){
                                StringProdotto stringPost_coupon = l.toObject( StringProdotto.class );
                                coutProdotti.add( 1 );

                                if(stringPost_coupon.getCategoria().equals( cat )){
                                    arrayList.add( stringPost_coupon );
                                    ArrayProdotti arrayProdotti = new ArrayProdotti( Profile_Pub.this, arrayList );
                                    listView.setAdapter( arrayProdotti );
                                }


                                if(arrayList.size() == 0 && coutProdotti.size() == queryDocumentSnapshots.size()){
                                    //nessun prodott
                                    nessunProdotto.setVisibility( View.VISIBLE );

                                }
                            }
                        }


                    }
                } );
            }
        } );

        bevandeImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat = "Bevande";
                arrayList.clear();
                coutProdotti.clear();
                titloC.setText( getString( R.string.bevande ) );
                nessunProdotto.setVisibility( View.GONE );

                Log.d( "kmfld",emailPub );
                conOgg.setVisibility( View.VISIBLE );
                consCat.setVisibility( View.GONE );
                firebaseFirestore.collection( emailPub ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(queryDocumentSnapshots != null){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot l:list){
                                StringProdotto stringPost_coupon = l.toObject( StringProdotto.class );
                                coutProdotti.add( 1 );

                                if(stringPost_coupon.getCategoria().equals( cat )){
                                    arrayList.add( stringPost_coupon );
                                    ArrayProdotti arrayProdotti = new ArrayProdotti( Profile_Pub.this, arrayList );
                                    listView.setAdapter( arrayProdotti );
                                }

                                if(arrayList.size() == 0 && coutProdotti.size() == queryDocumentSnapshots.size()){
                                    //nessun prodott
                                    nessunProdotto.setVisibility( View.VISIBLE );

                                }


                            }
                        }


                    }
                } );
            }
        } );

        salatiImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat = "Salati";
                arrayList.clear();
                coutProdotti.clear();
                titloC.setText( getString( R.string.salati ) );
                nessunProdotto.setVisibility( View.GONE );

                Log.d( "kmfld",emailPub );
                conOgg.setVisibility( View.VISIBLE );
                consCat.setVisibility( View.GONE );
                firebaseFirestore.collection( emailPub ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(queryDocumentSnapshots != null){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot l:list){
                                StringProdotto stringPost_coupon = l.toObject( StringProdotto.class );
                                coutProdotti.add( 1 );
                                if(stringPost_coupon.getCategoria().equals( cat )){
                                    arrayList.add( stringPost_coupon );
                                    ArrayProdotti arrayProdotti = new ArrayProdotti( Profile_Pub.this, arrayList );
                                    listView.setAdapter( arrayProdotti );
                                }
                                if(arrayList.size() == 0 && coutProdotti.size() == queryDocumentSnapshots.size()){
                                    //nessun prodott
                                    nessunProdotto.setVisibility( View.VISIBLE );
                                }
                            }
                        }else{
                            Log.d( "fkmdsfk","kfmdlsk" );
                        }


                    }
                } );
            }
        } );
    }


    ImageSlider imageSlider;
    ArrayList<SlideModel> arrayPost;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private void setImageSlider() {
        imageSlider = (ImageSlider) findViewById( R.id.image_slider );
        arrayPost = new ArrayList<>();
        firebaseFirestore.collection( emailPub+"Post" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null){
                    List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : documentSnapshots){
                        StringPost_coupon stringPost_coupon = documentSnapshot.toObject( StringPost_coupon.class );

                        if(stringPost_coupon.getCategoria().equals( "Post" )){

                            if(stringPost_coupon.getPinnato().equals( "si" )){
                                for (int i = 0; i < stringPost_coupon.getFoto().size(); i++) {
                                    arrayPost.add( new SlideModel( String.valueOf( stringPost_coupon.getFoto().get( i ) ), ScaleTypes.CENTER_INSIDE ) );
                                    imageSlider.setImageList( arrayPost );

                                }
                            }

                        }


                    }
                }
            }
        } );
    }


    @Override
    public void onBackPressed() {
        Log.d( "oflskfm","ssss2" );

        if(consCat.getVisibility() == View.GONE){
            consCat.setVisibility( View.VISIBLE );
            conOgg.setVisibility( View.GONE );
            Log.d( "oflskfm","2" );

            if(arrayProdottis != null){
                arrayList.clear();
                listView.setAdapter( null );
            }

        }else if(groupSearch.getVisibility() == View.VISIBLE){
            groupSearch.setVisibility( View.GONE );
            Log.d( "oflskfm","3" );
            InputMethodManager imm = (InputMethodManager) getSystemService( Context.
                    INPUT_METHOD_SERVICE );
            imm.hideSoftInputFromWindow( t_search.getWindowToken(), 0 );
            t_search.clearFocus();
        }


        else{
            if(getIntent().getExtras().getString( "i" ) != null){
                finish();
            }else {
                Intent i = new Intent( getApplicationContext(), HomePage.class );
                i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity( i );
                finish();
            }
        }


    }
}