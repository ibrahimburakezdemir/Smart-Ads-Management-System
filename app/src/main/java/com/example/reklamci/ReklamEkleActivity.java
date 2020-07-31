package com.example.reklamci;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReklamEkleActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText firma_id, firma_adi, x_lok, y_lok, kampanya_icerik, kampanya_suresi,kategori;
    private Button ekle, goster;
    private TextView kayitlar;

    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reklam_ekle);
        db= FirebaseDatabase.getInstance();
        firma_id=findViewById(R.id.FirmaIDTxt);
        firma_adi=findViewById(R.id.FirmaAdiTxt);
        x_lok=findViewById(R.id.XLokasyonTxt);
        y_lok=findViewById(R.id.YLokasyonTxt);
        kampanya_icerik=findViewById(R.id.KampanyaIcerikTxt);
        kampanya_suresi=findViewById(R.id.KampanyaSuresiTxt);
        ekle=findViewById(R.id.EkleBtn);
        goster=findViewById(R.id.GosterBtn);
        kayitlar=findViewById(R.id.gosterge);
        kategori=findViewById(R.id.Kategoritxt);
        ekle.setOnClickListener(this);
        goster.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.EkleBtn:
                String f_ad, icerik,ksure,kat;
                int f_id;
                double fx,fy;
                f_id=Integer.valueOf(firma_id.getText().toString().trim());
                f_ad=firma_adi.getText().toString().trim();
                fx=Double.valueOf(x_lok.getText().toString().trim());
                fy=Double.valueOf(y_lok.getText().toString().trim());
                icerik=kampanya_icerik.getText().toString().trim();
                ksure=kampanya_suresi.getText().toString().trim();
                kat=kategori.getText().toString().trim();
                reklamKaydet(f_id,f_ad,fx,fy,icerik,ksure,kat);
                break;
            case R.id.GosterBtn:
                kayitlariGetir();
                break;
        }
    }
    private  void reklamKaydet(int fid,String fad,double x, double y, String icerik, String ksure,String kat){

        DatabaseReference dbRef=db.getReference("Reklamlar");
        String key =dbRef.push().getKey();
        DatabaseReference dbRefKeyli= db.getReference("Reklamlar/"+key);

        dbRefKeyli.setValue(new Reklam(fid,fad,x,y,icerik,ksure,kat));
    }
    private void kayitlariGetir(){
        kayitlar.setText("");
        DatabaseReference dbGelenler = db.getReference("Reklamlar");
        dbGelenler.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot gelenler: dataSnapshot.getChildren()){
                    kayitlar.append(gelenler.getValue(Reklam.class).getF_ad());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
