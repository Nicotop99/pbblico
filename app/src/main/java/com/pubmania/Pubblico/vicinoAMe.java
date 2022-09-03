package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.ListView;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.Pubblico.Array.ArrayVicinoAMe;
import com.pubmania.Pubblico.String.StringRegistrazione;
import com.pubmania.Pubblico.String.StringRegistrazionePub;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class vicinoAMe extends AppCompatActivity {

    String email;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vicino_a_me );
        email = "oliveri.enicola@gmail.com";
        setGoogleMaps();
        setMenuBasso();

    }
    public static SupportMapFragment mapFragment;
    boolean reloadMap = true;
    LocationManager locationManager;
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

                }
                currentPostition = new LatLng( locationn.getLatitude(), locationn.getLongitude() );
                mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById( R.id.map );
                if (mapFragment != null)
                {
                    mapFragment.getMapAsync( new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            mMap = googleMap;
                            mMap.addMarker( new MarkerOptions().position( currentPostition ).title( getString( R.string.seiqua ) ) );

                            if (reloadMap == true) {

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
    int i = 0;


    ArrayList<Double> arrLong = new ArrayList<>();
    ArrayList<String> arrIdProf = new ArrayList<>();
    ArrayList<Double> arrLati = new ArrayList<>();
    ArrayList<String> arridPr = new ArrayList<>();
    int ia = 0;
    private void setAll() {
        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                        Location temp = new Location( LocationManager.GPS_PROVIDER );
                        temp.setLongitude( Double.parseDouble( documentSnapshot.getString( "longi" ) ) );
                        temp.setLatitude( Double.parseDouble( documentSnapshot.getString( "lati" ) ) );
                        Log.d( "mfldskmflk", String.valueOf( currentLocation ) );
                        Log.d( "mfldskmflk", String.valueOf( temp ) );
                        arrayDistance.add( Double.valueOf( currentLocation.distanceTo( temp ) ) );
                        Log.d( "ofjldsjf", String.valueOf( arrayDistance ) );
                        arrLong.add( Double.parseDouble( documentSnapshot.getString( "longi" ) )  );
                        arrLati.add( Double.parseDouble( documentSnapshot.getString( "lati" ) )  );
                        arridPr.add( documentSnapshot.getId() );
                }
                    ia = 0;

                    int count = 0;
                    Collections.sort( arrayDistance );
                    for (int i = 0; i<arrayDistance.size();i++){
                        Collections.sort( arrayDistance);

                        Location temp = new Location( LocationManager.GPS_PROVIDER );
                        temp.setLatitude( arrLati.get( i ) );
                        temp.setLongitude( arrLong.get( i ) );
                        Log.d( "ofmdsf", String.valueOf( arrayDistance.get( count ) ) );
                        Log.d( "lkmflsdkmfldmsf", String.valueOf( arrayDistance.size() ) );
                        if(currentLocation.distanceTo( temp ) == arrayDistance.get( count )){
                            Log.d( "omflsdmfl",currentLocation.distanceTo( temp ) + " " + arrayDistance.get( 0 ) );

                            arrIdProf.add( arridPr.get( i  ) );

                            Log.d( "komdlkmdlk",count + " " + arrayDistance.size() );
                            if(count < arrayDistance.size() -1) {
                                count += 1;
                            }
                        }
                    }
                 /*
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        ia+=1;
                        Log.d( "fsdmkfmddddddsd",documentSnapshot.getString( "email" ) );
                        if(count<12){
                            count +=1;
                            Geocoder coder = new Geocoder(getApplicationContext());
                            List<Address> address;
                            LatLng p1 = null;

                            try {
                                // May throw an IOException
                                address = coder.getFromLocationName(documentSnapshot.getString( "viaLocale" ), 1);
                                Log.d( "mfdskmòsdkm","fmdskòmf" );
                                if (address == null) {

                                }else{
                                    Address location = address.get(0);
                                    arrLati.add( location.getLatitude() );
                                    arrLong.add( location.getLongitude() );
                                    p1 = new LatLng(location.getLatitude(), location.getLongitude() );
                                    Location temp = new Location(LocationManager.GPS_PROVIDER);
                                    temp.setLongitude( location.getLongitude() );
                                    temp.setLatitude( location.getLatitude() );
                                        for (int i = 0; i < arrayDistance.size(); i++) {
                                            int finalI = i + 1;
                                            Log.d( "kmfldmf", documentSnapshot.getString( "email" ));
                                            double men = arrayDistance.get( i ) - 20;
                                            double piu = arrayDistance.get( i ) + 20;
                                            if(men < arrayDistance.get( i ) && piu > arrayDistance.get( i )){
                                                Log.d( "pfmsdkfm",documentSnapshot.getString( "email" ) );
                                                StringRegistrazionePub stringRegistrazionePub = documentSnapshot.toObject( StringRegistrazionePub.class );
                                                arrayList.add( stringRegistrazionePub );
                                                ArrayVicinoAMe arrayVicinoAMe = new ArrayVicinoAMe( vicinoAMe.this, arrayList, arrayDistance );
                                                listView.setAdapter( arrayVicinoAMe );
                                                mMap.addMarker( new MarkerOptions().position( new LatLng( temp.getLatitude(), temp.getLongitude() ) ).title( documentSnapshot.getString( "nomeLocale" ) ) );
                                                mMap.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {
                                                    @Override
                                                    public boolean onMarkerClick(@NonNull Marker marker) {
                                                        Toast.makeText( getApplicationContext(), "Da fare", Toast.LENGTH_LONG ).show();
                                                        return false;
                                                    }
                                                } );
                                            }else{
                                                Log.d( "ofmlsdkmlfkm","mfdlms" );
                                                Log.d( "osdmflmsdf",men + " " + arrayDistance.get( i ) + " " + piu );
                                            }














                                        }
                                }



                            } catch (IOException ex) {

                                ex.printStackTrace();
                            }
                        }


                    }


                  */
                    Log.d( "fldsmfl", String.valueOf( arrIdProf ) );
                    int c = 0;
                        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                        if(documentSnapshot.getId().equals( arrIdProf.get( i ) )){
                                            i+=1;

                                            StringRegistrazionePub stringRegistrazionePub = documentSnapshot.toObject( StringRegistrazionePub.class );
                                            arrayList.add( stringRegistrazionePub );
                                        }
                                    }
                                }
                                if(i == arrIdProf.size()){
                                    ArrayVicinoAMe arrayVicinoAMe = new ArrayVicinoAMe( vicinoAMe.this, arrayList, arrayDistance );
                                    listView.setAdapter( arrayVicinoAMe );
                                }
                            }
                        } );
                        c+=1;




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