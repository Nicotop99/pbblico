package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.Pubblico.Array.ArrayVicinoAMe;
import com.pubmania.Pubblico.String.StringRegistrazionePub;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.CollationKey;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class vicinoAMe extends AppCompatActivity {

    String email;
    private GoogleMap mMap;

    TextView countPub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vicino_a_me );
        email = "oliveri.enicola@gmail.com";
        countPub = (TextView) findViewById(R.id.textView29);
        setGoogleMaps();
        setMenuBasso();
        setButton();

        ArrayList<String> add = new ArrayList<>();
        add.add("a");
        add.add("v");
        add.add("s");
        add.add("t");
        add.add("b");



    }
    ArrayVicinoAMe arrayVicinoAMe;
    ConstraintLayout c1,c2,c3;
    ImageButton i1,i2,i3;
    String categoria = "primavolta";
    private void setButton() {
        c1 = (ConstraintLayout) findViewById(R.id.c1);
        c2 = (ConstraintLayout) findViewById(R.id.c2);
        c3 = (ConstraintLayout) findViewById(R.id.c3);
        i1 = (ImageButton) findViewById(R.id.imageButton25);
        i2 = (ImageButton) findViewById(R.id.imageButton26);
        i3 = (ImageButton) findViewById(R.id.imageButton27);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGoogleMaps();
                countColl = 0;
                if(!categoria.equals("piuvicino")) {
                    categoria = "piuvicino";
                    piuVicino();


                }
                countColl = 0;
                i1.setImageResource(R.drawable.back_filtro_select);
                i2.setImageResource(R.drawable.back_filtro_non_select);
                i3.setImageResource(R.drawable.back_filtro_non_select);
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGoogleMaps();
                countColl = 0;
                fort = 0;

                if(!categoria.equals("recensioni")){
                    setRecensioni();
                    categoria = "recensioni";




                }

                i1.setImageResource(R.drawable.back_filtro_non_select);
                i2.setImageResource(R.drawable.back_filtro_select);
                i3.setImageResource(R.drawable.back_filtro_non_select);
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGoogleMaps();
                countColl = 0;

                if(!categoria.equals("chiusura")){
                    chiusura();
                    categoria = "chiusura";




                }


                i1.setImageResource(R.drawable.back_filtro_non_select);
                i2.setImageResource(R.drawable.back_filtro_non_select);
                i3.setImageResource(R.drawable.back_filtro_select);
            }
        });
    }

    private void chiusura() {
        for (int i=0; i<orario.size()-1; i++) {
            for (int j=i+1; j<orario.size(); j++) {
                if (orario.get(i).compareTo(orario.get(j)) > 0) {

                    String or = String.valueOf(orario.get(i));
                    orario.set(i,orario.get(j));
                    orario.set(j, or);

                    //... Exchange elements in first array
                    String temp = String.valueOf(mediaRec.get(i));
                    mediaRec.set(i, mediaRec.get(j));
                    mediaRec.set(j, Integer.valueOf(temp));

                    //... Exchange elements in second array
                    StringRegistrazionePub stringRegistrazionePub;
                    stringRegistrazionePub = arrayList.get(i);
                    arrayList.set(i, arrayList.get(j));
                    arrayList.set(j, stringRegistrazionePub);


                    String ts = String.valueOf(arridPr.get(i));
                    arridPr.set(i,arridPr.get(j));
                    arridPr.set(j, ts);



                    String es = String.valueOf(emailString.get(i));
                    emailString.set(i,emailString.get(j));
                    emailString.set(j, es);

                    Double d = arrayDistance.get(i);
                    arrayDistance.set(i,arrayDistance.get(j));
                    arrayDistance.set(j,d);



                }
            }
        }
        Collections.reverse(emailString);
        Collections.reverse(mediaRec);
        Collections.reverse(arridPr);
        Collections.reverse(arrayList);
        Collections.reverse(arrayDistance);
        Collections.reverse(orario);
        arrayVicinoAMe = new ArrayVicinoAMe(vicinoAMe.this, arrayList, arrayDistance);
        arrayVicinoAMe.notifyDataSetChanged();
        listView.setAdapter(arrayVicinoAMe);
    }

    public static SupportMapFragment mapFragment;
    boolean reloadMap = true;
    LocationManager locationManager;
    ArrayList<String> emailString = new ArrayList<>();
    Location currentLocation;
    LatLng currentPostition;
    ListView listView;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ArrayList<Double> arrayDistance = new ArrayList<>();
    boolean locatio = false;
    ArrayList<StringRegistrazionePub> arrayList = new ArrayList<>();
    private void setGoogleMaps() {

        listView = (ListView) findViewById( R.id.listviewVicinoAme );
        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(vicinoAMe.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            return;
        }
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location locationn) {
                if(locatio == false) {
                    currentLocation = locationn;
                    locatio = true;


                    currentPostition = new LatLng(locationn.getLatitude(), locationn.getLongitude());
                    mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                }
                if (mapFragment != null)
                {
                    mapFragment.getMapAsync( new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            mMap = googleMap;


                            if (reloadMap == true) {
                                mMap.clear();
                                mMap.addMarker( new MarkerOptions().position( currentPostition ).title( getString( R.string.seiqua ) ) );
                                CameraPosition cameraPosition = new CameraPosition.Builder().target( currentPostition ).zoom( 13 ).build();
                                reloadMap = false;
                                mMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ), 2000, new GoogleMap.CancelableCallback() {
                                    @Override
                                    public void onCancel() {
                                        setAll();

                                    }

                                    @Override
                                    public void onFinish() {
                                        setAll();

                                    }
                                } );

                            }


                        }
                    } );
            }
            }
        } );



    }
    int totMedia;
    StringRegistrazionePub stringRegistrazionePub;
    int countMedia = 0;
    ArrayList<String> orderEmail;
    ArrayList<Integer> orderMedia;
    ArrayList<Integer> mediaRec = new ArrayList<>();
    int countMediaaa = 0;
    int fort = 0;
    private void setRecensioni() {


        Log.d("jdnsajkn", String.valueOf(mediaRec));
        Log.d("jdnsajkn", String.valueOf(emailString));
        for (int i=0; i<mediaRec.size()-1; i++) {
            for (int j=i+1; j<mediaRec.size(); j++) {
                if (mediaRec.get(i).compareTo(mediaRec.get(j)) > 0) {
                    //... Exchange elements in first array
                    String temp = String.valueOf(mediaRec.get(i));
                    mediaRec.set(i, mediaRec.get(j));
                    mediaRec.set(j, Integer.valueOf(temp));

                    //... Exchange elements in second array
                    StringRegistrazionePub stringRegistrazionePub;
                    stringRegistrazionePub = arrayList.get(i);
                    arrayList.set(i, arrayList.get(j));
                    arrayList.set(j, stringRegistrazionePub);


                    String ts = String.valueOf(arridPr.get(i));
                    arridPr.set(i,arridPr.get(j));
                    arridPr.set(j, ts);



                    String es = String.valueOf(emailString.get(i));
                    emailString.set(i,emailString.get(j));
                    emailString.set(j, es);

                    Double d = arrayDistance.get(i);
                    arrayDistance.set(i,arrayDistance.get(j));
                    arrayDistance.set(j,d);


                    String or = String.valueOf(orario.get(i));
                    orario.set(i,orario.get(j));
                    orario.set(j, or);
                }
            }
        }

        Collections.reverse(emailString);
        Collections.reverse(mediaRec);
        Collections.reverse(arridPr);
        Collections.reverse(arrayList);
        Collections.reverse(arrayDistance);
        Collections.reverse(orario);
        Log.d("jdnsajkn", String.valueOf(mediaRec));
        Log.d("jdnsajkn", String.valueOf(emailString));


            arrayVicinoAMe = new ArrayVicinoAMe(vicinoAMe.this, arrayList, arrayDistance);
        arrayVicinoAMe.notifyDataSetChanged();
        listView.setAdapter(arrayVicinoAMe);
            Log.d("ndfljjlsd", String.valueOf(listView.getAdapter()));
        }


    int i = 0;


    ArrayList<Double> arrLong = new ArrayList<>();
    ArrayList<String> arrIdProf = new ArrayList<>();
    int counttt = 0;
    ArrayList<Double> arrLati = new ArrayList<>();
    ArrayList<String> arridPr = new ArrayList<>();
    int count = 0;
    int ia = 0;
    int countColl = 0;


    private void piuVicino(){
        Log.d("najsndjan", String.valueOf(arrayDistance));
        for (int i=0; i<arrayDistance.size()-1; i++) {
            for (int j=i+1; j<arrayDistance.size(); j++) {
                if (arrayDistance.get(i).compareTo(arrayDistance.get(j)) > 0) {
                    //... Exchange elements in first array
                    String temp = String.valueOf(mediaRec.get(i));
                    mediaRec.set(i, mediaRec.get(j));
                    mediaRec.set(j, Integer.valueOf(temp));

                    //... Exchange elements in second array
                    StringRegistrazionePub stringRegistrazionePub;
                    stringRegistrazionePub = arrayList.get(i);
                    arrayList.set(i, arrayList.get(j));
                    arrayList.set(j, stringRegistrazionePub);


                    String ts = String.valueOf(arridPr.get(i));
                    arridPr.set(i,arridPr.get(j));
                    arridPr.set(j, ts);



                    String es = String.valueOf(emailString.get(i));
                    emailString.set(i,emailString.get(j));
                    emailString.set(j, es);

                    Double d = arrayDistance.get(i);
                    arrayDistance.set(i,arrayDistance.get(j));
                    arrayDistance.set(j,d);


                    String or = String.valueOf(orario.get(i));
                    orario.set(i,orario.get(j));
                    orario.set(j, or);
                }
            }
        }
        Log.d("assssssssssss", String.valueOf(orario));
        Log.d("jndasldnl", String.valueOf(emailString));
        Log.d("jndasldnl", String.valueOf(arridPr));


            arrayVicinoAMe = new ArrayVicinoAMe(vicinoAMe.this, arrayList, arrayDistance);
            listView.setAdapter(arrayVicinoAMe);
            arrayVicinoAMe.notifyDataSetChanged();



    }

    ArrayList<String> orario = new ArrayList<>();
    private void setAll() {
        emailString.clear();
        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                        Log.d("dojnasjd", String.valueOf(orario.size()));
                        Location temp = new Location( LocationManager.GPS_PROVIDER );
                        temp.setLongitude( Double.parseDouble(String.valueOf(documentSnapshot.get( "longi" ))) );
                        temp.setLatitude( Double.parseDouble(String.valueOf( documentSnapshot.get( "lati" ))) );
                        Log.d( "mfldskmflk", String.valueOf( currentLocation ) );
                        Log.d( "mfldskmflk", String.valueOf( temp ) );
                        arrayDistance.add( Double.valueOf( currentLocation.distanceTo( temp ) ));
                        Log.d( "ofjldsjf", String.valueOf( arrayDistance ) );
                        arrLong.add( Double.parseDouble(String.valueOf(  documentSnapshot.get( "longi" )))  );
                        arrLati.add( Double.parseDouble(String.valueOf(  documentSnapshot.get( "lati" )) ) );
                        arridPr.add( documentSnapshot.getId() );

                }
                    ia = 0;
                    Log.d("jnalnda", String.valueOf(arrayDistance));
                    for (int i=0; i<mediaRec.size()-1; i++) {
                        for (int j=i+1; j<mediaRec.size(); j++) {
                            if (mediaRec.get(i).compareTo(mediaRec.get(j)) > 0) {
                                //... Exchange elements in first array
                                String temp = String.valueOf(mediaRec.get(i));
                                mediaRec.set(i, mediaRec.get(j));
                                mediaRec.set(j, Integer.valueOf(temp));

                                //... Exchange elements in second array
                                StringRegistrazionePub stringRegistrazionePub;
                                stringRegistrazionePub = arrayList.get(i);
                                arrayList.set(i, arrayList.get(j));
                                arrayList.set(j, stringRegistrazionePub);


                                String ts = String.valueOf(arridPr.get(i));
                                arridPr.set(i,arridPr.get(j));
                                arridPr.set(j, ts);



                                String es = String.valueOf(emailString.get(i));
                                emailString.set(i,emailString.get(j));
                                emailString.set(j, es);

                                Double d = arrayDistance.get(i);
                                arrayDistance.set(i,arrayDistance.get(j));
                                arrayDistance.set(j,d);

                                String or = String.valueOf(orario.get(i));
                                orario.set(i,orario.get(j));
                                orario.set(j, or);

                            }
                        }
                    }
                    Log.d("jnalnda", String.valueOf(arrayDistance));

                    for (int i = 0;i< arrayDistance.size();i++){
                        if(i<11) {
                            int finalI = i;
                            firebaseFirestore.collection("Professionisti").document(arridPr.get(i)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Geocoder coder = new Geocoder(getApplicationContext());
                                        List<Address> address;
                                        LatLng p1 = null;
                                        firebaseFirestore.collection(task.getResult().getString("email")+ "Rec").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful()){
                                                    countMediaaa = 0;
                                                    if (task.getResult().size() > 0) {
                                                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                            countMediaaa += Integer.parseInt(documentSnapshot.getString("media"));
                                                        }
                                                        mediaRec.add(countMediaaa / task.getResult().size());

                                                    } else {
                                                        countMediaaa = 0;
                                                        mediaRec.add(0);

                                                    }

                                                    if(mediaRec.size() == arrayDistance.size()){

                                                    }
                                                }
                                            }
                                        });

                                        try {
                                            // May throw an IOException
                                            address = coder.getFromLocationName(task.getResult().getString( "viaLocale" ), 1);
                                            Log.d( "mfdskmòsdkm","fmdskòmf" );
                                            if (address == null) {

                                            }else{


                                                Calendar calendar = Calendar.getInstance();
                                                Date date = calendar.getTime();
                                                String giono = new SimpleDateFormat("EEEE", Locale.ITALIAN).format(date.getTime());
                                                if(giono.equals("lunedì")){
                                                    if(task.getResult().getString("cLunedì") != null) {
                                                        orario.add(task.getResult().getString("cLunedi"));
                                                    }else{
                                                        orario.add("00.00");

                                                    }

                                                }else if(giono.equals("martedi")){
                                                    if(task.getResult().getString("cMartedi") != null){
                                                        orario.add(task.getResult().getString("cMartedi"));
                                                    }else{
                                                        orario.add("00:00");
                                                    }
                                                }else if(giono.equals("mercoledì")){
                                                    if(task.getResult().getString("cMercoledi") != null){
                                                        orario.add(task.getResult().getString("cMercoldi"));
                                                    }else{
                                                        orario.add("00:00");
                                                    }
                                                }else if(giono.equals("giovedì")){
                                                    if(task.getResult().getString("cGiovedi") != null){
                                                        orario.add(task.getResult().getString("cGiovedi"));
                                                    }else{
                                                        orario.add("00:00");
                                                    }
                                                }else if(giono.equals("venerdì")){
                                                    if(task.getResult().getString("cVenerdi") != null){
                                                        orario.add(task.getResult().getString("cVenerdi"));
                                                    }else{
                                                        orario.add("00:00");
                                                    }
                                                }else if(giono.equals("sabato")){
                                                    if(task.getResult().getString("cSabato") != null){
                                                        orario.add(task.getResult().getString("cSabato"));
                                                    }else{
                                                        orario.add("00:00");
                                                    }
                                                }else if(giono.equals("domenica")){
                                                    if(task.getResult().getString("cDomenica") != null) {
                                                        orario.add(task.getResult().getString("cDomenica"));
                                                    }else{
                                                        orario.add("00.00");
                                                    }
                                                }



                                                stringRegistrazionePub = task.getResult().toObject( StringRegistrazionePub.class );

                                                arrayList.add( stringRegistrazionePub );
                                                Log.d("jnddjsandj", String.valueOf(arrayList));
                                                emailString.add(task.getResult().getString("email"));
                                                if(arridPr.size() == finalI +1) {
                                                    arrayVicinoAMe = new ArrayVicinoAMe(vicinoAMe.this, arrayList, arrayDistance);

                                                    listView.setAdapter(arrayVicinoAMe);

                                                    arrayVicinoAMe.notifyDataSetChanged();
                                                }


                                                Address location = address.get(0);
                                                arrLati.add( location.getLatitude() );
                                                arrLong.add( location.getLongitude() );
                                                p1 = new LatLng(location.getLatitude(), location.getLongitude() );
                                                Location temp = new Location(LocationManager.GPS_PROVIDER);
                                                temp.setLongitude( location.getLongitude() );
                                                temp.setLatitude( location.getLatitude() );
                                                mMap.addMarker( new MarkerOptions().position( new LatLng( temp.getLatitude(), temp.getLongitude() ) ).title( task.getResult().getString( "nomeLocale" ) ) );
                                                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                                    @Override
                                                    public void onInfoWindowClick(@NonNull Marker marker) {
                                                        Intent intent = new Intent(getApplicationContext(),Profile_Pub.class);
                                                        intent.putExtra("emailPub",task.getResult().getString("email"));
                                                        startActivity(intent);
                                                    }
                                                });


                                            }



                                        }
                                        catch (IOException ex) {

                                            ex.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                        else{
                            if(arrayDistance.size()>= 11){
                                arrayDistance.remove(i);
                            }

                            if(arridPr.size() >= 11){
                                arridPr.remove(i);
                            }

                            if(mediaRec.size() >= 11){
                                mediaRec.remove(i);
                            }
                            if(emailString.size() >= 11){
                                emailString.remove(i);
                            }

                            if(orario.size() >= 11){
                                orario.remove(i);
                            }


                        }
                    }
                    countPub.setText(getString(R.string.cisono) + " " + arrayDistance.size() + " " + getString(R.string.pubvicinoate));

                }
            }
        } );



    }


    BottomNavigationView bottomAppBar;
    private void setMenuBasso() {
        {

            bottomAppBar = (BottomNavigationView) findViewById( R.id.bottomNavView );
            bottomAppBar.setSelectedItemId(R.id.mapsBotton);
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
                            mapFragment.onDestroyView();
                            break;
                        case R.id.mapsBotton:
                            startActivity( new Intent( getApplicationContext(), vicinoAMe.class ) );
                            finish();
                            if(mapFragment != null) {
                                mapFragment.onDestroyView();
                            }
                            break;

                    }
                    return true;
                }
            } );


        }
    }

}