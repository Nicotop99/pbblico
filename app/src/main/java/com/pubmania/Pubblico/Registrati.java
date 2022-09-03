package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pubmania.Pubblico.String.StringRegistrazione;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registrati extends AppCompatActivity {


    TextInputEditText t_email,t_confEmail,t_pass,t_confPass;
    TextInputLayout l_email,l_confEmail,l_pass, l_confpass;
    ImageButton chooseImage,registrati;
    CircleImageView photoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registrati );
        setId();
        setChooseImage();
        setRegistrati();
    }


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private void setRegistrati() {
        registrati.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = t_email.getText().toString();
                String confEmail = t_confEmail.getText().toString();
                String pass = t_pass.getText().toString();
                String confPass = t_confPass.getText().toString();
                l_pass.setError( null );
                l_email.setError( null );
                l_confpass.setError( null );
                l_confEmail.setError( null );


                if(email.isEmpty()){
                    l_email.setError( getString( R.string.emailnoninserita ) );
                }else if(confEmail.isEmpty()){
                    l_confEmail.setError( getString( R.string.confemailnoninserita ) );
                }else if(!email.equals( confEmail )){
                    l_confEmail.setError( getString( R.string.leemailnoncoincidono ) );
                }else if(pass.isEmpty()){
                    l_pass.setError( getString( R.string.passwordnoninserita ) );
                }else if(confPass.isEmpty()){
                    l_confpass.setError( getString( R.string.confpassnoninserita ) );
                }else if(pass.length() <7){
                    l_pass.setError( getString( R.string.lapassworddeveavereminimo8caratteri ) );
                }else {
                    firebaseAuth.createUserWithEmailAndPassword(email,pass  ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                StringRegistrazione stringRegistrazione = new StringRegistrazione();
                                stringRegistrazione.setEmail( email );
                                stringRegistrazione.setCogonome( "" );
                                stringRegistrazione.setNome( "" );
                                stringRegistrazione.setMiPiacciono( "" );

                                if(uri.equals( "uri" )){
                                    stringRegistrazione.setFotoProfilo( "https://firebasestorage.googleapis.com/v0/b/pubmania-404db.appspot.com/o/download%20(5).jfif?alt=media&token=9fbd065e-5e01-445c-a9fe-60a02bbbfcca" );
                                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                    firebaseFirestore.collection( "Pubblico" ).add( stringRegistrazione ).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            if(documentReference != null){
                                                documentReference.update( "id", documentReference.getId() ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        FirebaseAuth auth = FirebaseAuth.getInstance();
                                                        FirebaseUser user = auth.getCurrentUser();

                                                        user.sendEmailVerification().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                //intent login
                                                                Snackbar.make( findViewById( android.R.id.content ),getString( R.string.verificalatuaemail ),Snackbar.LENGTH_LONG ).show();


                                                            }
                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {

                                                            }
                                                        } );
                                                    }
                                                } );
                                            }
                                        }
                                    } );


                                }else{


                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    StorageReference storageReference = storage.getReference();
                                    StorageReference storageReference1 = storageReference.child( email + "/" + UUID.randomUUID().toString()  );

                                    storageReference1.putFile( Uri.parse( uri ) ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            storageReference1.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    stringRegistrazione.setFotoProfilo( String.valueOf( uri ) );
                                                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                    firebaseFirestore.collection( "Pubblico" ).add( stringRegistrazione ).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            if(documentReference != null){
                                                                documentReference.update( "id", documentReference.getId() ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        FirebaseAuth auth = FirebaseAuth.getInstance();
                                                                        FirebaseUser user = auth.getCurrentUser();

                                                                        user.sendEmailVerification().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                //intent login
                                                                                Snackbar.make( findViewById( android.R.id.content ),getString( R.string.verificalatuaemail ),Snackbar.LENGTH_LONG ).show();

                                                                                startActivity( new Intent(getApplicationContext(), Login.class ) );
                                                                                finish();
                                                                            }
                                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {

                                                                            }
                                                                        } );
                                                                    }
                                                                } );
                                                            }
                                                        }
                                                    } );
                                                }
                                            } );
                                        }
                                    } );


                                }




                            }
                            else{
                                Snackbar.make( findViewById( android.R.id.content ),getString( R.string.siagiaregistratoooo ),Snackbar.LENGTH_LONG )

                                        .setAction( "Login", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                //intent login
                                                startActivity( new Intent(getApplicationContext(), Login.class ) );
                                                finish();
                                            }
                                        } )
                                        .show();
                            }
                        }
                    } );

                }



            }
        } );
    }

    private void setChooseImage() {
        chooseImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType( "image/*" );
                intent.putExtra( Intent.EXTRA_ALLOW_MULTIPLE, true );
                intent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( intent, "Choose Picture" ), 1 );
            }
        } );
    }

    private void setId() {
        t_email = (TextInputEditText) findViewById( R.id.textEmail );
        t_confEmail = (TextInputEditText) findViewById( R.id.textConfEmail );
        t_pass = (TextInputEditText) findViewById( R.id.textPassword );
        t_confPass = (TextInputEditText) findViewById( R.id.textConfPass );
        l_email = (TextInputLayout) findViewById( R.id.inputEmail );
        l_confEmail = (TextInputLayout) findViewById( R.id.inputConfEmail );
        l_pass = (TextInputLayout) findViewById( R.id.inputPassword );
        l_confpass = (TextInputLayout) findViewById( R.id.inputConfPass );
        chooseImage = (ImageButton) findViewById( R.id.imageButton2 );
        registrati = (ImageButton) findViewById( R.id.imageButton3 );
        photoProfile = (CircleImageView) findViewById( R.id.circleImageView );
    }
    String uri = "uri";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(resultCode == RESULT_OK){
            if(requestCode ==1){
                Picasso.get().load( data.getData() ).into( photoProfile );
                uri = String.valueOf( data.getData() );
            }
        }
    }
}