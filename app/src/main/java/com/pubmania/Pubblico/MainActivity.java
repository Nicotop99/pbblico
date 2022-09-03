package com.pubmania.Pubblico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        setRegistrati();
        setLogin();
    }


    TextView login;
    private void setLogin() {
        login = (TextView) findViewById( R.id.textView7 );
        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(getApplicationContext(), Login.class ) );
                finish();
            }
        } );
    }


    ImageButton registratiB;
    private void setRegistrati() {
        registratiB = (ImageButton) findViewById( R.id.imageButton );
        registratiB.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(getApplicationContext(), Registrati.class) );
                finish();
            }
        } );
    }
}