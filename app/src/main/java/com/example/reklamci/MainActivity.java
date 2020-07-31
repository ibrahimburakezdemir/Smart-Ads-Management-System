package com.example.reklamci;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText ad, passwd,mes,kate,store;
    Button kayit, giris, degis, r_gir;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ad = findViewById(R.id.ad);
        passwd = findViewById(R.id.passwd);
        kayit = findViewById(R.id.kayit);
        degis = findViewById(R.id.degis);
        giris = findViewById(R.id.giris);
        r_gir = findViewById(R.id.ReklamBtn);
        mes=findViewById(R.id.mesafetxt2);
        kate=findViewById(R.id.kategoritxt);
        store=findViewById(R.id.storetxt);



        mAuth = FirebaseAuth.getInstance();

        //kayıt oluşturma
        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, sifre;

                email = ad.getText().toString().trim();
                sifre = passwd.getText().toString().trim();


                if(!email.equals("") && !sifre.equals("")){
                    mAuth.createUserWithEmailAndPassword(email, sifre)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Kullanıcı Kaydı Başarılı",Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Kullanıcı Kaydı Başarısız",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                }
                else{
                    Toast.makeText(MainActivity.this, "Lütfen Boş Alan Bırakmayınız!",Toast.LENGTH_LONG).show();
                }
            }
        });

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, sifre;
                double uzaklik;

                email = ad.getText().toString().trim();
                sifre = passwd.getText().toString().trim();


                if(!email.equals("") && !sifre.equals("")){
                    mAuth.signInWithEmailAndPassword(email, sifre)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Kullanıcı Girişi Başarılı",Toast.LENGTH_LONG).show();
                                        

                                        Intent intent = new Intent(MainActivity.this, harita.class);
                                        intent.putExtra("mesafe",mes.getText().toString());
                                        Intent intent1 = new Intent(MainActivity.this, harita.class);
                                        intent1.putExtra("cat",kate.getText().toString());
                                        Intent intent2 = new Intent(MainActivity.this, harita.class);
                                        intent2.putExtra("store",store.getText().toString());
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Kullanıcı Girişi Başarısız",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                }
                else{
                    Toast.makeText(MainActivity.this, "Lütfen Boş Alan Bırakmayınız!",Toast.LENGTH_LONG).show();
                }
            }
        });

        degis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, sifre;

                email = ad.getText().toString().trim();
                sifre = passwd.getText().toString().trim();

                if(!email.equals("") && !sifre.equals("")){
                    mAuth.signInWithEmailAndPassword(email, sifre)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Kullanıcı Girişi Başarılı",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, SifreDegistirActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Kullanıcı Girişi Başarısız",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                }
                else{
                    Toast.makeText(MainActivity.this, "Lütfen Boş Alan Bırakmayınız!",Toast.LENGTH_LONG).show();
                }
            }
        });

        r_gir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReklamEkleActivity.class);
                startActivity(intent);
            }
        }
        );



    }
}
