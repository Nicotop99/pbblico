package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        setLogin();

    }
    private void getAutoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("emailPassAutoLogin",MODE_PRIVATE);
        if(!sharedPreferences.getString( "email","null" ).equals( "null" )){
            auth.signInWithEmailAndPassword( sharedPreferences.getString( "email",null ),sharedPreferences.getString( "pass",null ) ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(auth.getCurrentUser().isEmailVerified() == true) {

                        startActivity( new Intent(getApplicationContext(), HomePage.class) );
                        finish();

                    }else{
                        Toast.makeText( getApplicationContext(),getApplicationContext().getString( R.string.emailnonverificata ),Toast.LENGTH_LONG ).show();
                    }

                }
            } );

        }



    }

    ImageButton login;
    TextInputLayout l_email,l_pass;
    TextInputEditText t_email,t_pass;
    Switch aSwitch;
    private void setLogin() {
        login = (ImageButton) findViewById( R.id.imageButton2 );
        l_email = (TextInputLayout) findViewById( R.id.layoutPass );
        l_pass = (TextInputLayout) findViewById( R.id.textInputLayout2 );
        t_email = (TextInputEditText) findViewById( R.id.textEmail );
        t_pass = (TextInputEditText) findViewById( R.id.textPass );
        aSwitch = (Switch) findViewById( R.id.switch1 ) ;
        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = t_email.getText().toString();
                String pass = t_pass.getText().toString();


                if(email.isEmpty()){
                    l_email.setError( getString( R.string.emailnoninserita ) );
                }else if(pass.isEmpty()){
                    l_pass.setError( getString( R.string.passwordnoninserita ) );

                }
                else{
                    auth.signInWithEmailAndPassword( email,pass ).addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if(authResult.getUser() != null) {
                                if(authResult.getUser().isEmailVerified()){

                                    if(aSwitch.isChecked() == true){
                                        SharedPreferences sharedPreferences = getSharedPreferences("emailPassAutoLogin",MODE_PRIVATE);
                                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                        myEdit.putString("email", email);
                                        myEdit.putString("pass",pass);
                                        myEdit.commit();
                                    }

                                    startActivity( new Intent(getApplicationContext(), HomePage.class) );
                                    finish();



                                    //intenti
                                }else{
                                    auth.signOut();
                                    Snackbar.make( findViewById( android.R.id.content ), getString( R.string.emailnonverificata ),Snackbar.LENGTH_LONG ).show();
                                }
                            }
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            if(error.equals( "The password is invalid or the user does not have a password." )){
                               l_pass.setError( getString( R.string.passwordErrata ) );
                            }else if(error.equals( "There is no user record corresponding to this identifier. The user may have been deleted." )){
                                Snackbar.make( findViewById( android.R.id.content ),getString( R.string.nonseiancoraregistrato ),Snackbar.LENGTH_LONG )
                                        .setAction( getString( R.string.registrati ), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startActivity( new Intent(getApplicationContext(), Registrati.class) );
                                                finish();
                                            }
                                        } ).show();
                            }
                        }
                    } );



                }



            }
        } );




    }
}