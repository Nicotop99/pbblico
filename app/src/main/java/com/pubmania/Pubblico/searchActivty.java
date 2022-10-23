package com.pubmania.Pubblico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.Pubblico.Array.arrayListSearch;
import com.pubmania.Pubblico.String.doppiaStringa;

import java.util.ArrayList;
import java.util.Locale;

public class searchActivty extends AppCompatActivity {
    String email;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    TextView nessunRisultato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activty);
        email = "nicolino.oliverio@gmail.com";
        setListProfessionisti();
        setList();
        setRecenti();

    }
    ArrayList<String> nomeLocale = new ArrayList<>();
    ArrayList<String> fotoProfilo = new ArrayList<>();
    ArrayList<String> emailString = new ArrayList<>();
    private void setListProfessionisti() {
        firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        nomeLocale.add(documentSnapshot.getString("nomeLocale"));
                        fotoProfilo.add(documentSnapshot.getString("fotoProfilo"));
                        emailString.add(documentSnapshot.getString("email"));
                    }
                }
            }
        });
    }


    ListView listView;
    TextInputEditText t_search;
    arrayListSearch arrayListSearch;
    ArrayList<String> arrayListNomeProfilo = new ArrayList<>();
    ArrayList<doppiaStringa> arrayList = new ArrayList<>();
    boolean e = false;
    private void setList() {
        nessunRisultato = (TextView) findViewById(R.id.textView79);
        listView = (ListView) findViewById(R.id.listProfili);
        t_search = (TextInputEditText) findViewById(R.id.textSearch);
        SharedPreferences sharedPreferences = getSharedPreferences("ricercaRecente",MODE_PRIVATE);


        t_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String cerca = charSequence.toString().toLowerCase();


                if(e == false){
                    arrayList.clear();
                    e = true    ;
                }
                Log.d("jndjkandfj",cerca    + "ciao");
                if(!cerca.isEmpty()) {
                    for (int in = 0; in < nomeLocale.size(); in++) {
                        if (nomeLocale.get(in).toLowerCase(Locale.ROOT).contains(cerca)) {
                            nessunRisultato.setVisibility(View.GONE);
                            Log.d("dodnjalsdn",nomeLocale.get(in));
                            doppiaStringa doppiaStringa = new doppiaStringa();
                            Log.d("ojdasd",nomeLocale.get(i));
                                doppiaStringa.setUno(nomeLocale.get(in));
                                doppiaStringa.setDue(fotoProfilo.get(in));
                                doppiaStringa.setTre(emailString.get(in));
                                Log.d("oflajdk", String.valueOf(doppiaStringa));
                                Log.d("jodnajdn",arrayList + " " + doppiaStringa);
                            if(!arrayListNomeProfilo.contains(nomeLocale.get(in).toLowerCase(Locale.ROOT))) {
                                arrayListNomeProfilo.add(nomeLocale.get(in).toLowerCase(Locale.ROOT));
                                Log.d("kkkkk","kkdkdlk");
                                arrayList.add(doppiaStringa);
                                Log.d("ojndasnd", String.valueOf(arrayList.get(0)));
                                arrayListSearch = new arrayListSearch(searchActivty.this, arrayList);
                                arrayListSearch.notifyDataSetChanged();
                                listView.setAdapter(arrayListSearch);

                            }
                        } else {
                            for (int i4 = 0;i4<arrayList.size();i4++){
                                Log.d("jndlad",arrayList.get(i4).getUno());
                                if(arrayList.get(i4).getUno().equals(nomeLocale.get(in))){
                                    arrayList.remove(i4);
                                    arrayListSearch.notifyDataSetChanged();
                                    arrayListNomeProfilo.remove(i4);
                                }
                            }


                        }
                    }
                }else{
                    Log.d("jnasjlkdnas","odsal");
                        arrayList.clear();
                        arrayListNomeProfilo.clear();
                        arrayListSearch arrayListSearch = new arrayListSearch(searchActivty.this, arrayList);
                        listView.setAdapter(arrayListSearch);


                }
                if(arrayList.size() == 0){
                    nessunRisultato.setVisibility(View.VISIBLE);
                    if(cerca.isEmpty()){
                        setRecenti();
                        nessunRisultato.setVisibility(View.GONE);
                    }
                }else{
                    nessunRisultato.setVisibility(View.GONE);
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView textViewEmail = (TextView) view.findViewById(R.id.textView75);
                        Intent intent = new Intent(getApplicationContext(), Profile_Pub.class);
                        intent.putExtra("emailPub",textViewEmail.getText().toString());
                        intent.putExtra("i","i");
                        startActivity(intent);
                        doppiaStringa doppiaStringa = (com.pubmania.Pubblico.String.doppiaStringa) adapterView.getItemAtPosition(i);

                        if(!sharedPreferences.getString("salvati","").contains(doppiaStringa.getUno()+"<" + doppiaStringa.getDue() + "<" + doppiaStringa.getTre())) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("salvati", sharedPreferences.getString("salvati", "")  + doppiaStringa.getUno() + "<" + doppiaStringa.getDue() + "<" + doppiaStringa.getTre() + "ù");
                            editor.commit();
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setRecenti() {
        e = false;
        SharedPreferences sharedPreferences = getSharedPreferences("ricercaRecente",MODE_PRIVATE);
        /*
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
         */
        if(!sharedPreferences.getString("salvati","").equals("")){
            String[] recenti = sharedPreferences.getString("salvati","").split("ù");
            Log.d("jndkajnsd",recenti[0]);
            for (int i = 0;i<recenti.length;i++){
                Log.d("dddjasjdalsj",recenti[i]);

                String[] split = recenti[i].split("<");
                for (int in = 0;in<split.length;in++){
                    Log.d("ndjas",split[in]);
                }
                doppiaStringa doppiaStringa = new doppiaStringa();

                doppiaStringa.setUno(split[0]);
                doppiaStringa.setDue(split[1]);
                doppiaStringa.setTre(split[2]);
                arrayList.add(doppiaStringa);
                arrayListSearch = new arrayListSearch(searchActivty.this, arrayList);
                arrayListSearch.notifyDataSetChanged();
                listView.setAdapter(arrayListSearch);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView textViewEmail = (TextView) view.findViewById(R.id.textView75);
                        Intent intent = new Intent(getApplicationContext(), Profile_Pub.class);
                        intent.putExtra("emailPub",textViewEmail.getText().toString());
                        intent.putExtra("i","i");

                        startActivity(intent);
                    }
                });
            }
        }
    }
}