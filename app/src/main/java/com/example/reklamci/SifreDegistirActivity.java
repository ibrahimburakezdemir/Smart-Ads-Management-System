package com.example.reklamci;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SifreDegistirActivity extends AppCompatActivity {
    EditText sifreText;
    Button changebtn;
    ProgressDialog dialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_degistir2);
        dialog = new ProgressDialog(this);
        sifreText = findViewById(R.id.sifreText);
        changebtn = findViewById(R.id.changebtn);
        mAuth = FirebaseAuth.getInstance();

        //kayıt oluşturma
        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){
                    dialog.setMessage("Şifre değişiyor, lütfen bekleyiniz!");
                    user.updatePassword(sifreText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Şifre başarıyla değişti.",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Şifre değişimi başarısız!",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                }
                else{
                    Toast.makeText(SifreDegistirActivity.this, "Lütfen Boş Alan Bırakmayınız!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
