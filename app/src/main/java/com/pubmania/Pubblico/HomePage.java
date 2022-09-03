package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
import com.google.zxing.Result;
import com.pubmania.Pubblico.Array.ArrayHome;
import com.pubmania.Pubblico.String.StringPost_coupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends AppCompatActivity {

    String email;
    public static boolean click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_page );
        email = "nicolino.oliverio@gmail.com";
        click = false;




        setListView();
        setMenuBasso();
    }
BottomNavigationView bottomAppBar;
    private void setMenuBasso() {
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

                    }
                    return true;
                }
            } );


        }
    }


    ListView listView;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ArrayList<StringPost_coupon> arrayList;
    String nome,uri,emailPub;
    int emilCount = 1;
    public static boolean flag_loading = false;
    int count,in;
    ArrayList<String> arrEmail =  new ArrayList<>();
    int coEmail = 0;

    ArrayHome arrayHome;
    private void setListView() {
        listView = (ListView) findViewById( R.id.list_home );
        arrayList = new ArrayList<>();

        firebaseFirestore.collection(email+"follower"  ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task != null){
                    Log.d("pfmdskfmsdkfm", String.valueOf( task.getResult().size() ) );
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Log.d( "okfldks", String.valueOf( documentSnapshot.getData() ) );
                        for (int i = 0; i<documentSnapshot.getData().size();i++){
                            int finalI = i +1;

                            if(!documentSnapshot.getData().get( String.valueOf( finalI ) ).equals( "" )){
                                arrEmail.add( String.valueOf( documentSnapshot.getData().get(String.valueOf(  finalI ))) );
                            }
                            Log.d( "psfkpsdkm", String.valueOf( documentSnapshot.getData().get( String.valueOf(  finalI ) )) );


                        }

                    }
                    firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task != null){
                                count = 0;
                                Log.d("pfmdskfmsdkfm","3");

                                for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()){

                                    if(documentSnapshot1.getString( "email" ).equals( arrEmail.get( coEmail ))){
                                        Log.d("pfmdskfmsdkfm","4");
                                        nome = documentSnapshot1.getString( "nomeLocale" );
                                        uri = documentSnapshot1.getString( "fotoProfilo" );
                                        emailPub = documentSnapshot1.getString( "email" );

                                        firebaseFirestore.collection( arrEmail.get( coEmail ) + "Post" )
                                                .limit( 5 )
                                                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                if(queryDocumentSnapshots.size() <5){
                                                    Log.d( "ofodsofosdf","1" );

                                                    for (int i = 0; i< arrEmail.size(); i++){
                                                        Log.d( "ofodsofosdf",arrEmail.get( i ) );

                                                        firebaseFirestore.collection( arrEmail.get( i ) + "Post" )
                                                                .limit( 5 - arrayList.size() )
                                                                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                if(arrayList.size()<5){
                                                                    Log.d( "pkfmsdkfm", String.valueOf( queryDocumentSnapshots.size() ) );
                                                                    Log.d( "ofodsofosdf","3" );

                                                                    Log.d("pfmdskfmsdkfm","5");
                                                                    Log.d("pff",arrEmail.get( coEmail ));

                                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                                    for (DocumentSnapshot documentSnapshot1 : list){
                                                                        count +=1;
                                                                        StringPost_coupon stringPost_coupon = documentSnapshot1.toObject( StringPost_coupon.class );
                                                                        arrayList.add( stringPost_coupon );
                                                                        if(arrayList.size()>5){
                                                                            arrayHome = new ArrayHome( HomePage.this,  arrayList,email);
                                                                            listView.setAdapter( arrayHome );
                                                                            listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                                                                @Override
                                                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                    Log.d( "kfmdslkfm","pf,dsp" );
                                                                                    if(click == false) {
                                                                                        click = true;
                                                                                        TextView textView = (TextView) view.findViewById( R.id.textView16 );
                                                                                        Intent intent = new Intent( getApplicationContext(), Profile_Pub.class );
                                                                                        intent.putExtra( "emailPub", textView.getText().toString() );
                                                                                        startActivity( intent );
                                                                                    }
                                                                                }
                                                                            } );

                                                                            listView.setOnScrollListener( new AbsListView.OnScrollListener() {
                                                                                @Override
                                                                                public void onScrollStateChanged(AbsListView absListView, int i) {


                                                                                }

                                                                                @Override
                                                                                public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                                                                    if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                                                                                    {
                                                                                        if(flag_loading == false) {
                                                                                            in = 0;
                                                                                            Log.d( "kdfsmklfmsdkf", "lkfmdlskm" );

                                                                                            flag_loading = true;
                                                                                            int cll = count + 4;
                                                                                            Log.d( "ofdlskflsd", coEmail + "a" );
                                                                                            Log.d( "ofdlskflsd", arrEmail.size() + "a" );
                                                                                            if (coEmail== arrEmail.size()) {
                                                                                                firebaseFirestore.collection( arrEmail.get( coEmail - 1 ) + "Post" )
                                                                                                        .limit( cll )
                                                                                                        .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                                                        if (queryDocumentSnapshots.size() > 0) {
                                                                                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                                                                            for (DocumentSnapshot documentSnapshot1 : list) {
                                                                                                                in += 1;
                                                                                                                if (count < in) {
                                                                                                                    Log.d( "kmsadò", count + " " + in );
                                                                                                                    StringPost_coupon stringPost_coupon = documentSnapshot1.toObject( StringPost_coupon.class );
                                                                                                                    Log.d( "kmfdsklmfl", stringPost_coupon.getCategoria() );
                                                                                                                    Log.d( "fdslfjlsdjflsd", "entrato" );
                                                                                                                    arrayList.add( stringPost_coupon );
                                                                                                                    arrayHome.notifyDataSetChanged();

                                                                                                                } else {

                                                                                                                }
                                                                                                            }
                                                                                                            count = count + 1;
                                                                                                            if (count > in) {
                                                                                                                coEmail += 1;
                                                                                                            }

                                                                                                        }


                                                                                                    }
                                                                                                } );
                                                                                            }
                                                                                        }



                                                                                    }
                                                                                    else{
                                                                                        flag_loading = false;
                                                                                    }
                                                                                }
                                                                            } );
                                                                        }
                                                                    }
                                                                    coEmail +=1;
                                                                    Log.d( "skdfmsdk", String.valueOf( arrayList.size() ) );


                                                                }
                                                                else{
                                                                    Log.d( "ofodsofosdf","4" );
                                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                                    for (DocumentSnapshot documentSnapshot1 : list){
                                                                        count +=1;
                                                                        StringPost_coupon stringPost_coupon = documentSnapshot1.toObject( StringPost_coupon.class );
                                                                        Log.d( "kmfdsklmfl",stringPost_coupon.getCategoria() );
                                                                        Log.d( "kmfdsklmfl", String.valueOf( count ) );
                                                                        arrayList.add( stringPost_coupon );
                                                                        arrayHome = new ArrayHome( HomePage.this,  arrayList,email);
                                                                        listView.setAdapter( arrayHome );
                                                                        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                                                            @Override
                                                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                Log.d( "kfmdslkfm","pf,dsp" );
                                                                                if(click == false) {
                                                                                    click = true;
                                                                                    TextView textView = (TextView) view.findViewById( R.id.textView16 );
                                                                                    Intent intent = new Intent( getApplicationContext(), Profile_Pub.class );
                                                                                    intent.putExtra( "emailPub", textView.getText().toString() );
                                                                                    startActivity( intent );
                                                                                }
                                                                            }
                                                                        } );

                                                                        listView.setOnScrollListener( new AbsListView.OnScrollListener() {
                                                                            @Override
                                                                            public void onScrollStateChanged(AbsListView absListView, int i) {


                                                                            }

                                                                            @Override
                                                                            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                                                                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                                                                                {
                                                                                    if(flag_loading == false)
                                                                                    {
                                                                                        in = 0;
                                                                                        Log.d( "kdfsmklfmsdkf","lkfmdlskm" );
                                                                                        Log.d( "kdfsmklfmsdkf", String.valueOf( arrEmail.get( coEmail )  ) );
                                                                                        flag_loading = true;
                                                                                        int cll = count +1;
                                                                                        firebaseFirestore.collection( arrEmail.get( coEmail ) + "Post" )
                                                                                                .limit(cll)
                                                                                                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                                                                            @Override
                                                                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                                                if (queryDocumentSnapshots.size() > 0) {


                                                                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                                                                    for (DocumentSnapshot documentSnapshot1 : list) {
                                                                                                        in +=1;
                                                                                                        Log.d( "kmfdskòmfò",documentSnapshot1.getString( "titolo" ) );
                                                                                                        if(count < in){
                                                                                                            Log.d( "kmsadò",count + " " + in );

                                                                                                            StringPost_coupon stringPost_coupon = documentSnapshot1.toObject( StringPost_coupon.class );
                                                                                                            Log.d( "kmfdsklmfl", stringPost_coupon.getCategoria() );
                                                                                                            Log.d( "fdslfjlsdjflsd","entrato" );
                                                                                                            arrayList.add( stringPost_coupon );
                                                                                                            arrayHome.notifyDataSetChanged();

                                                                                                        }else{

                                                                                                        }
                                                                                                    }
                                                                                                    count = count +1;
                                                                                                    if(count > in){
                                                                                                        coEmail +=1;
                                                                                                    }

                                                                                                }


                                                                                            }
                                                                                        } );
                                                                                    }



                                                                                }
                                                                                else{
                                                                                    flag_loading = false;
                                                                                }
                                                                            }
                                                                        } );


                                                                    }
                                                                }
                                                            }
                                                        } );
                                                    }





                                                }
                                                else{
                                                    Log.d("pfmdskfmsdkfm","5");
                                                    Log.d("pfmdskfmsdasdasdasdsdkfm",arrEmail.get( coEmail ));

                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                    for (DocumentSnapshot documentSnapshot1 : list){
                                                        count +=1;
                                                        StringPost_coupon stringPost_coupon = documentSnapshot1.toObject( StringPost_coupon.class );
                                                        Log.d( "kmfdsklmfl",stringPost_coupon.getCategoria() );
                                                        Log.d( "kmfdsklmfl", String.valueOf( count ) );
                                                        arrayList.add( stringPost_coupon );
                                                        arrayHome = new ArrayHome( HomePage.this,  arrayList,email);
                                                        listView.setAdapter( arrayHome );
                                                        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                Log.d( "kfmdslkfm","pf,dsp" );
                                                                if(click == false) {
                                                                    click = true;
                                                                    TextView textView = (TextView) view.findViewById( R.id.textView16 );
                                                                    Intent intent = new Intent( getApplicationContext(), Profile_Pub.class );
                                                                    intent.putExtra( "emailPub", textView.getText().toString() );
                                                                    startActivity( intent );
                                                                }
                                                            }
                                                        } );

                                                        listView.setOnScrollListener( new AbsListView.OnScrollListener() {
                                                            @Override
                                                            public void onScrollStateChanged(AbsListView absListView, int i) {


                                                            }

                                                            @Override
                                                            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                                                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                                                                {
                                                                    if(flag_loading == false)
                                                                    {
                                                                        in = 0;
                                                                        Log.d( "kdfsmklfmsdkf","lkfmdlskm" );
                                                                        Log.d( "kdfsmklfmsdkf", String.valueOf( arrEmail.get( coEmail )  ) );
                                                                        flag_loading = true;
                                                                        int cll = count +1;
                                                                        firebaseFirestore.collection( arrEmail.get( coEmail ) + "Post" )
                                                                                .limit(cll)
                                                                                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                                if (queryDocumentSnapshots.size() > 0) {


                                                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                                                    for (DocumentSnapshot documentSnapshot1 : list) {
                                                                                        in +=1;
                                                                                        Log.d( "kmfdskòmfò",documentSnapshot1.getString( "titolo" ) );
                                                                                        if(count < in){
                                                                                            Log.d( "kmsadò",count + " " + in );

                                                                                            StringPost_coupon stringPost_coupon = documentSnapshot1.toObject( StringPost_coupon.class );
                                                                                            Log.d( "kmfdsklmfl", stringPost_coupon.getCategoria() );
                                                                                            Log.d( "fdslfjlsdjflsd","entrato" );
                                                                                            arrayList.add( stringPost_coupon );
                                                                                            arrayHome.notifyDataSetChanged();

                                                                                        }else{

                                                                                        }
                                                                                    }
                                                                                    count = count +1;
                                                                                    if(count > in){
                                                                                        coEmail +=1;
                                                                                    }

                                                                                }


                                                                            }
                                                                        } );
                                                                    }



                                                                }
                                                                else{
                                                                    flag_loading = false;
                                                                }
                                                            }
                                                        } );


                                                    }
                                                }
                                            }
                                        } ).addOnFailureListener( new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d( "errorrrrr",e.getMessage()+" bb" );
                                            }
                                        } );
                                    }
                                }
                            }
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d( "errorrrrr",e.getMessage()+" aa" );
                        }
                    } );

                }
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d( "errorrrrr",e.getMessage()+" cc" );
            }
        } );

    }
}