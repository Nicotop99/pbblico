package com.pubmania.Pubblico;

import static com.pubmania.Pubblico.FaiUnaRecensione.emailPub;
import static com.pubmania.Pubblico.FaiUnaRecensione.idPost;
import static com.pubmania.Pubblico.FaiUnaRecensione.token;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.pubmania.Pubblico.Array.ArrayIngredienti;
import com.pubmania.Pubblico.Array.ArrayProdotti;
import com.pubmania.Pubblico.Array.ArraySearchprodotti;
import com.pubmania.Pubblico.String.StringCoutVisite;
import com.pubmania.Pubblico.String.StringNotifiche;
import com.pubmania.Pubblico.String.StringPost_coupon;
import com.pubmania.Pubblico.String.StringProdotto;

import org.json.JSONException;
import org.json.JSONObject;

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
    String email,tokenClienteeee;
    LocationManager locationManager;
    String nomeCognomeee,uriFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile__pub );
        HomePage.click = false;
        email = "nicolino.oliverio@gmail.com";
        emailPub = getIntent().getExtras().getString( "emailPub" );
        firebaseFirestore.collection("Pubblico").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if (documentSnapshot.getString("email").equals(email)){
                            uriFoto = documentSnapshot.getString("fotoProfilo");
                            nomeCognomeee = documentSnapshot.getString("nome") + " " + documentSnapshot.getString("cognome");
                        }
                    }
                }
            }
        });
        Log.d( "fòkdmskfm",emailPub );
        setImageSlider();
        setImageButton();
        setSearch();
        setTop();
        setButtonprofile();
        setVisite();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    return;
                }
                tokenClienteeee = task.getResult();

            }
        });
        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        setMenu();



    }

    ImageView setting;
    PopupWindow popupWindow;
    boolean coup_switch = false;
    boolean post_switch = false;
    private void setMenu() {
        setting = (ImageView) findViewById(R.id.imageView53);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.settings_profile_pub, null);

                popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //TODO do sth here on dismiss
                    }
                });

                Switch n_coupon = (Switch) popupView.findViewById(R.id.switch3);
                Switch n_post = (Switch) popupView.findViewById(R.id.switch2);
                firebaseFirestore.collection(email+"notificheCoupon").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                String[] em = documentSnapshot.getString("emailPub").split(" ");
                                for (int i = 0;i<em.length;i++){
                                    if(em[i].equals(emailPub)){
                                        coup_switch = true;
                                        n_coupon.setChecked(true);

                                    }
                                }
                            }
                        }
                    }
                });
                firebaseFirestore.collection(email+"notifichePost").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                String[] em = documentSnapshot.getString("emailPub").split(" ");
                                for (int i = 0;i<em.length;i++){
                                    if(em[i].equals(emailPub)){
                                        post_switch = true;
                                        n_post.setChecked(true);

                                    }
                                }

                            }
                        }
                    }
                });




                n_coupon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(coup_switch == false) {
                            if (b == true) {
                                Map<String, Object> user = new HashMap<>();
                                firebaseFirestore.collection(emailPub + "notificheCoupon").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful() && task.getResult().size() > 0) {
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                DocumentReference documentReference = firebaseFirestore.collection(emailPub + "notificheCoupon").document(documentSnapshot.getId());
                                                documentReference.update("token", documentSnapshot.getString("token") + tokenClienteeee + "=" + email + " ");
                                            }
                                        } else {
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("token", tokenClienteeee + "=" + email + " ");
                                            firebaseFirestore.collection(emailPub + "notificheCoupon").add(user);

                                        }


                                        firebaseFirestore.collection(email + "notificheCoupon").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful() && task.getResult().size() > 0) {
                                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                        DocumentReference documentReference = firebaseFirestore.collection(email + "notificheCoupon").document(documentSnapshot.getId());
                                                        documentReference.update("emailPub", documentSnapshot.getString("emailPub") + emailPub + " ");
                                                    }
                                                } else {
                                                    Map<String, Object> user = new HashMap<>();
                                                    user.put("emailPub", emailPub);
                                                    firebaseFirestore.collection(email + "notificheCoupon").add(user);

                                                }

                                            }
                                        });

                                    }


                                });
                            } else {

                                firebaseFirestore.collection(emailPub + "notificheCoupon").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful() && task.getResult().size() > 0) {
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                String[] getTok = documentSnapshot.getString("token").split(" ");
                                                Log.d("jndjland", getTok[1]);
                                                for (int i = 0; i < getTok.length; i++) {
                                                    String[] geto = getTok[i].split("=");
                                                    for (int in = 0; in < geto.length; in++) {
                                                        if (geto[in].equals(tokenClienteeee)) {
                                                            DocumentReference documentReference = firebaseFirestore.collection(emailPub + "notificheCoupon").document(documentSnapshot.getId());
                                                            documentReference.update("token", documentSnapshot.getString("token").replace(tokenClienteeee + "=" + email, ""));

                                                        }
                                                    }
                                                }
                                            }
                                        }


                                        firebaseFirestore.collection(email + "notificheCoupon").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful() && task.getResult().size() > 0) {
                                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                                        String[] getTok = documentSnapshot.getString("emailPub").split(" ");
                                                        for (int i = 0; i < getTok.length; i++) {
                                                            if (getTok[i].equals(emailPub)) {
                                                                Log.d("jnasljnds", "oasl");
                                                                DocumentReference documentReference = firebaseFirestore.collection(email + "notificheCoupon").document(documentSnapshot.getId());
                                                                documentReference.update("emailPub", documentSnapshot.getString("emailPub").replace(emailPub, ""));

                                                            }
                                                        }

                                                    }
                                                }

                                            }
                                        });

                                    }


                                });
                            }
                        }
                        coup_switch = false;
                    }
                });
                n_post.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        if(post_switch == false) {
                            if (b == true) {
                                Map<String, Object> user = new HashMap<>();
                                firebaseFirestore.collection(emailPub + "notifichePost").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful() && task.getResult().size() > 0) {
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                Log.d("djasdjlask", "primo");
                                                DocumentReference documentReference = firebaseFirestore.collection(emailPub + "notifichePost").document(documentSnapshot.getId());
                                                documentReference.update("token", documentSnapshot.getString("token") + tokenClienteeee + "=" + email + " ");
                                            }
                                        } else {
                                            Log.d("djasdjlask", "sec");

                                            Map<String, Object> user = new HashMap<>();
                                            user.put("token", tokenClienteeee + "=" + email + " ");

                                            firebaseFirestore.collection(emailPub + "notifichePost").add(user);

                                        }


                                        firebaseFirestore.collection(email + "notifichePost").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful() && task.getResult().size() > 0) {
                                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                        DocumentReference documentReference = firebaseFirestore.collection(email + "notifichePost").document(documentSnapshot.getId());
                                                        documentReference.update("emailPub", documentSnapshot.getString("emailPub") + emailPub + " ");
                                                    }
                                                } else {
                                                    Map<String, Object> user = new HashMap<>();
                                                    user.put("emailPub", emailPub);
                                                    firebaseFirestore.collection(email + "notifichePost").add(user);

                                                }

                                            }
                                        });

                                    }


                                });
                            } else {
                                Map<String, Object> user = new HashMap<>();
                                firebaseFirestore.collection(emailPub + "notifichePost").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful() && task.getResult().size() > 0) {
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                                String[] getTok = documentSnapshot.getString("token").split(" ");
                                                for (int i = 0; i < getTok.length; i++) {
                                                    String[] geto = getTok[i].split(String.valueOf("="));
                                                    for (int in = 0; in < geto.length; in++) {
                                                        if (geto[in].equals(tokenClienteeee)) {
                                                            DocumentReference documentReference = firebaseFirestore.collection(emailPub + "notifichePost").document(documentSnapshot.getId());
                                                            documentReference.update("token", documentSnapshot.getString("token").replace(tokenClienteeee + "=" + email, ""));

                                                        }
                                                    }
                                                }

                                            }
                                        }


                                        firebaseFirestore.collection(email + "notifichePost").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful() && task.getResult().size() > 0) {
                                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                                        String[] getTok = documentSnapshot.getString("emailPub").split(" ");
                                                        for (int i = 0; i < getTok.length; i++) {
                                                            if (getTok[i].equals(emailPub)) {
                                                                DocumentReference documentReference = firebaseFirestore.collection(email + "notifichePost").document(documentSnapshot.getId());
                                                                documentReference.update("emailPub", documentSnapshot.getString("emailPub").replace(emailPub, ""));

                                                            }
                                                        }

                                                    }
                                                }

                                            }
                                        });

                                    }


                                });
                            }
                        }
                        post_switch = false;
                    }
                });

                popupWindow.showAsDropDown(setting);


                /*
                Log.e("fklmzdlkdsa","doksal");
                PopupMenu popup = new PopupMenu(Profile_Pub.this, setting);
                popup.getMenuInflater().inflate(R.menu.menu_profilo_pub, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        //handle clicks
                        if (id==R.id.primo){
                            //Copy clicked
                            View view = (View) findViewById(R.id.primo);
                            PopupMenu popup = new PopupMenu(Profile_Pub.this,view);
                            popup.getMenuInflater().inflate(R.menu.menu_bottom, popup.getMenu());
                            popup.show();

                        }
                        else if (id==R.id.sec){
                            //Share clicked
                            //set text

                        }
                        else if (id==R.id.ter){
                            //Save clicked
                            //set text

                        }

                        return false;
                    }
                });

                 */
            }
        });
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
        firebaseFirestore.collection(email+"follower").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot l : documentSnapshots){
                        for(int i = 1;i<l.getData().size();i++){
                            if(!l.getString(String.valueOf(i)).equals("")){
                                if(l.getString(String.valueOf(i)).equals(emailPub)){
                                    seguiText.setText(getString(R.string.smettidiseguire));
                                }else{
                                    seguiText.setText(getString(R.string.segui));
                                }
                            }
                        }
                    }
                }
            }
        });
        i_segui.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d( "fdskòm", ",,,l" );
                if (seguiText.getText().toString().equals( getString( R.string.segui ) )) {
                    seguiText.setText( getString( R.string.smettidiseguire ) );

                    if(token.size() >0) {
                        for (int i = 0; i < token.size(); i++) {
                            propvaNotifica(token.get(i), "ddd");

                        }
                    }

                    firebaseFirestore.collection( email + "follower" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task != null) {
                                if(task.getResult().size() > 0) {
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        followeInt = documentSnapshot.getData().size() + 1;
                                        idFol = documentSnapshot.getId();
                                    }


                                    DocumentReference documentReference = firebaseFirestore.collection(email + "follower").document(idFol);
                                    documentReference.update(String.valueOf(followeInt), emailPub).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Map<String, Object> user = new HashMap<>();
                                                    user.put("emailCliente", email);
                                                    user.put("emailPub", emailPub);
                                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                                                    String currentDateandTime = sdf.format(new Date());
                                                    user.put("ora", currentDateandTime);
                                                    firebaseFirestore.collection(emailPub + "follower").add(user);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    seguiText.setText(getString(R.string.segui));

                                                }
                                            });
                                }else{
                                    Map<String,String> map = new HashMap<>();
                                    map.put("1",emailPub);
                                    firebaseFirestore.collection(email+"follower").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("emailCliente", email);
                                            user.put("emailPub", emailPub);
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                                            String currentDateandTime = sdf.format(new Date());
                                            user.put("ora", currentDateandTime);
                                            firebaseFirestore.collection(emailPub + "follower").add(user);
                                        }
                                    });
                                }
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
                    firebaseFirestore.collection(emailPub+"Notifiche").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                    if(documentSnapshot.getString("categoria").equals("Follower") && documentSnapshot.getString("emailCliente").equals(email)){
                                        DocumentReference documentReference = firebaseFirestore.collection(emailPub+"Notifiche").document(documentSnapshot.getId());
                                        documentReference.delete();
                                    }
                                }
                            }
                        }
                    });
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

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAbWZozX0:APA91bElDXMdAF898t_M5ai0z5cCTkG9po-deDqqirLm5zL9FI_UgxdQtlUdH0k7fToZIClrylH5LXbEZeVXsXpbr1rpYj6FpD20mFTLOVot-YbiYjhSf85Ca7qbHI9zzCCh0nCktwYF";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    String TOPIC;
    private void propvaNotifica(String token, String idPost) {

        TOPIC = "/topics/userABC"; //topic must match with what the receiver subscribed to

        Log.d("mndflmdlmdk",token);
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", nomeCognomeee + " " +  getString(R.string.hainiziatoaseguirti)  );
            notifcationBody.put("message", getString(R.string.cliccalanotificaperidettagli));
            notifcationBody.put("tipo","NuovoFollower");
            notifcationBody.put("idPost",idPost);
            notification.put("to", token);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        sendNotification(notification,token);




    }
    boolean entrato = true;

    private void sendNotification(JSONObject notification,String tok) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("jnjknfajndasndlkm", String.valueOf(response.getString("failure")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            if(response.getString("failure").equals("1")){
                                Log.d("jkfnakjsdnfkjsadn",emailPub);
                                // token non valido
                                firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if (documentSnapshot.getString("email").equals(emailPub)){
                                                    DocumentReference d  = firebaseFirestore.collection("Professionisti").document(documentSnapshot.getId());
                                                    ArrayList<String > a = (ArrayList<String>) documentSnapshot.get("token");
                                                    Log.d("njanfd", String.valueOf(a.size()));
                                                    for (int i = 0;i<a.size();i++){
                                                        if(a.get(i).equals(tok)){
                                                            a.remove(i);
                                                        }
                                                    }
                                                    d.update("token",a).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Log.d("onjlm","ok");
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d("onjlm","ok" + e.getMessage());

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("fhfdaskdnjand",tok);
                        // ok
                        if(entrato == true) {
                            StringNotifiche stringNotifiche = new StringNotifiche();
                            stringNotifiche.setVisualizzato("false");
                            stringNotifiche.setIdPost("");
                            stringNotifiche.setFotoProfilo(uriFoto);
                            stringNotifiche.setNomecognomeCliente(nomeCognomeee);
                            stringNotifiche.setCategoria("Follower");
                            stringNotifiche.setEmailPub(emailPub);
                            stringNotifiche.setEmailCliente(email);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());
                            stringNotifiche.setOra(currentDateandTime);
                            firebaseFirestore.collection(emailPub + "Notifiche").add(stringNotifiche)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                DocumentReference documentReference = firebaseFirestore.collection(emailPub + "Notifiche").document(task.getResult().getId());
                                                documentReference.update("id", task.getResult().getId());
                                            }
                                        }
                                    });
                            Log.d("duiskajnd", "djnsakd");
                            entrato = false;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        for (int i = 0;i<token.size();i++){
                            propvaNotifica(token.get(i),"ddd");

                        }
                        Log.d("onfljdsnfl",error.getMessage() + " ciao");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }


    TextView followe,recensioni;
    ArrayList<String> token;
    ImageView r1,r2,r3,r4,r5;
    boolean segui = false;
    int media = 0;
    private void setTop() {
        r1 = (ImageView) findViewById(R.id.imageView16);
        r2 = (ImageView) findViewById(R.id.imageView15);
        r3 = (ImageView) findViewById(R.id.imageView14);
        r4 = (ImageView) findViewById(R.id.imageView13);
        r5 = (ImageView) findViewById(R.id.imageView12);
        followe = (TextView) findViewById( R.id.textView18 );
        recensioni = (TextView) findViewById(R.id.textView17);
        firebaseFirestore.collection( email+"follower" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task != null) {
                    followe.setText(task.getResult().size() +" " + "follower");
                }
            }
        } );
        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task != null){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString( "email" ).equals( emailPub )){
                            followe.setText( documentSnapshot.getString( "follower" ) + " follower");
                            DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                            token = (ArrayList<String>) documentSnapshot.get("token");
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



        firebaseFirestore.collection(emailPub+"Rec").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    recensioni.setText(task.getResult().size() + " " + getString(R.string.recensioniii));
                    if (task.getResult().size() > 0) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            media += Integer.parseInt(documentSnapshot.getString("media"));
                        }
                        r1.setVisibility(View.VISIBLE);
                        r2.setVisibility(View.VISIBLE);
                        r3.setVisibility(View.VISIBLE);
                        r4.setVisibility(View.VISIBLE);
                        r5.setVisibility(View.VISIBLE);

                        int totMedia = media / task.getResult().size();
                        if (totMedia == 1) {
                            r1.setImageResource(R.drawable.recensione_si);
                            r2.setImageResource(R.drawable.recensione_no);
                            r3.setImageResource(R.drawable.recensione_no);
                            r4.setImageResource(R.drawable.recensione_no);
                            r5.setImageResource(R.drawable.recensione_no);
                        } else if (totMedia == 2) {
                            r1.setImageResource(R.drawable.recensione_si);
                            r2.setImageResource(R.drawable.recensione_si);
                            r3.setImageResource(R.drawable.recensione_no);
                            r4.setImageResource(R.drawable.recensione_no);
                            r5.setImageResource(R.drawable.recensione_no);
                        } else if (totMedia == 3) {
                            r1.setImageResource(R.drawable.recensione_si);
                            r2.setImageResource(R.drawable.recensione_si);
                            r3.setImageResource(R.drawable.recensione_si);
                            r4.setImageResource(R.drawable.recensione_no);
                            r5.setImageResource(R.drawable.recensione_no);
                        } else if (totMedia == 4) {
                            r1.setImageResource(R.drawable.recensione_si);
                            r2.setImageResource(R.drawable.recensione_si);
                            r3.setImageResource(R.drawable.recensione_si);
                            r4.setImageResource(R.drawable.recensione_si);
                            r5.setImageResource(R.drawable.recensione_no);
                        } else if (totMedia == 5) {
                            r1.setImageResource(R.drawable.recensione_si);
                            r2.setImageResource(R.drawable.recensione_si);
                            r3.setImageResource(R.drawable.recensione_si);
                            r4.setImageResource(R.drawable.recensione_si);
                            r5.setImageResource(R.drawable.recensione_si);
                        }
                    }
                }
            }
        });

    }


    TextInputEditText t_search;
    Group groupSearch;
    ArrayList<StringProdotto> arrayProdottis;
    ListView listSearch;
    boolean c = false;
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
                listSearch.setAdapter( null );
                firebaseFirestore.collection( emailPub ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot l: list){
                                if(!cat.equals("tutti")) {
                                    if (l.getId().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT)) && l.getString("categoria").equals(cat)) {
                                        StringProdotto stringProdotto = l.toObject(StringProdotto.class);
                                        Log.d("ndlasdklad", "lkmdsklm");
                                        // after getting data from Firebase we are
                                        // storing that data in our array list
                                        arrayProdottis.add(stringProdotto);
                                        ArraySearchprodotti arraySearchprodotti = new ArraySearchprodotti(Profile_Pub.this, arrayProdottis, "no");
                                        listSearch.setAdapter(arraySearchprodotti);
                                    } else {
                                        Log.d("pfdòs", "fkd");
                                    }
                                }else{
                                    if (l.getId().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT))) {
                                        StringProdotto stringProdotto = l.toObject(StringProdotto.class);
                                        Log.d("ndlasdklad", "lkmdsklm");
                                        // after getting data from Firebase we are
                                        // storing that data in our array list
                                        arrayProdottis.add(stringProdotto);
                                        ArraySearchprodotti arraySearchprodotti = new ArraySearchprodotti(Profile_Pub.this, arrayProdottis, "no");
                                        listSearch.setAdapter(arraySearchprodotti);
                                    } else {
                                        Log.d("pfdòs", "fkd");
                                    }
                                }

                            }
                            Log.d("jndljasnd", String.valueOf(arrayProdottis.size()));
                            if(arrayProdottis.size()>0) {
                                Log.d("djnajdn","dnsalnd");

                            }else{
                                StringProdotto stringProdotto = new StringProdotto();
                                stringProdotto.setCategoria("ciao");

                                arrayProdottis.add(stringProdotto);
                                ArraySearchprodotti arraySearchprodotti = new ArraySearchprodotti(Profile_Pub.this, arrayProdottis, "si");
                                listSearch.setAdapter( arraySearchprodotti );
                            }
                            listSearch.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    TextView idText = (TextView) view.findViewById( R.id.textView42 );
                                    String idPost = idText.getText().toString();
                                    if(!idPost.equals("s")) {
                                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Profile_Pub.this, R.style.MyDialogTheme);
// ...Irrelevant code for customizing the buttons and title
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View viewView = inflater.inflate(R.layout.layout_item_list_profile_pub, null);
                                        dialogBuilder.setView(viewView);
                                        AlertDialog alertDialogg = dialogBuilder.create();

                                        TextView titolo = (TextView) viewView.findViewById(R.id.textView36);
                                        TextView prezzo = (TextView) viewView.findViewById(R.id.textView41);
                                        Spinner spinner = (Spinner) viewView.findViewById(R.id.spinner);
                                        spinner.setEnabled(false);
                                        ListView listngredienti = (ListView) viewView.findViewById(R.id.listingr);
                                        ImageSlider imageSlider = (ImageSlider) viewView.findViewById(R.id.image_slider);
                                        TextView ing = (TextView) viewView.findViewById(R.id.textView43);

                                        firebaseFirestore.collection(emailPub).document(idPost).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    titolo.setText(task.getResult().getString("nome"));
                                                    prezzo.setText(task.getResult().getString("prezzo") + " ,00€");
                                                    if (task.getResult().getString("categoria").equals("Cocktail")) {
                                                    } else if (task.getResult().getString("categoria").equals("Bevande")) {
                                                        spinner.setSelection(3);
                                                    } else if (task.getResult().getString("categoria").equals("Dolci")) {
                                                        spinner.setSelection(1);
                                                    } else if (task.getResult().getString("categoria").equals("Salati")) {
                                                        spinner.setSelection(2);
                                                    }

                                                    ArrayList<String> group = (ArrayList<String>) task.getResult().get("ingredienti");
                                                    ArrayList<String> fotoList = (ArrayList<String>) task.getResult().get("foto");
                                                    ArrayList<SlideModel> arrMod = new ArrayList<>();

                                                    Log.d("mldkmkdmd", String.valueOf(task.getResult().get("ingredienti")));

                                                    if (group != null) {
                                                        if (group.size() > 0) {
                                                            ArrayIngredienti arraySearchprodotti = new ArrayIngredienti(Profile_Pub.this, group);
                                                            listngredienti.setAdapter(arraySearchprodotti);
                                                        } else {
                                                            listngredienti.setVisibility(View.GONE);
                                                            ing.setVisibility(View.GONE);
                                                        }

                                                    } else {
                                                        listngredienti.setVisibility(View.GONE);
                                                        ing.setVisibility(View.GONE);
                                                    }


                                                    if (fotoList != null) {
                                                        if (fotoList.size() > 0) {
                                                            for (int i = 0; i < fotoList.size(); i++) {

                                                                arrMod.add(new SlideModel(fotoList.get(i), ScaleTypes.CENTER_CROP));
                                                            }
                                                            imageSlider.setImageList(arrMod);

                                                        } else {
                                                            imageSlider.setVisibility(View.GONE);
                                                        }
                                                    } else {
                                                        imageSlider.setVisibility(View.GONE);
                                                    }

                                                }
                                            }

                                        });


                                        alertDialogg.setOnKeyListener(new DialogInterface.OnKeyListener() {
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
                                        });
                                        alertDialogg.show();
                                    }
                                }
                            } );
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
    String cat = "tutti";
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
    TextView nessunaFoto;
    private void setImageSlider() {
        imageSlider = (ImageSlider) findViewById( R.id.image_slider );
        nessunaFoto = (TextView) findViewById(R.id.textView90);
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
                    if(arrayPost.size() == 0){
                        String k = "https://firebasestorage.googleapis.com/v0/b/pub-mania.appspot.com/o/pubmania.png?alt=media&token=e264ba22-734a-4edc-94d7-24ba2e3b5580";
                        arrayPost.add(new SlideModel(k,ScaleTypes.CENTER_INSIDE));
                        imageSlider.setImageList(arrayPost);
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
            cat = "tutti";
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
                Log.d("jdnasjndna","kkkk");
            }else {
                Log.d("jdnasjndna","ddddd");

                Intent i = new Intent( getApplicationContext(), HomePage.class );
                i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity( i );
                finish();
            }
        }


    }
}