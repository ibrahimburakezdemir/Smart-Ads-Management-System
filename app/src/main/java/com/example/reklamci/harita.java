package com.example.reklamci;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class harita extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    static final LatLng Restoran = new LatLng(40.786831, 29.990211);
    static final LatLng Market = new LatLng(40.785149, 29.990589);
    private static final int MY_REQUST_INT = 177;

    private GoogleMap mMap;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mUsers;
    Marker marker;
    private  static final int REQUEST_LOCATION=1;
    LocationManager locationManager;
    Location konum;
    String lattitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harita);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ChildEventListener mChildEventListener;
        mUsers = FirebaseDatabase.getInstance().getReference("Reklamlar");
        mUsers.push().setValue(marker);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

    }
   /* @Override
    public void onClick(View view){
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

        }
        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER){
            getLocation();
        }
    }*/

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(harita.this,Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission
                (harita.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(harita.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }
        else{
            konum=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(konum!=null){
                double latti = konum.getLatitude();
                double longi = konum.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
            }
            else{
                Toast.makeText(this,"Konum bilgisi yok",Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

        }
        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            getLocation();
        }

        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);


        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent=getIntent();
                String mesafe=intent.getStringExtra("mesafe");
                Intent intent1=getIntent();
                String cat=intent1.getStringExtra("cat");
                Intent intent2=getIntent();
                String sto=intent2.getStringExtra("store");
                double m=Double.valueOf(mesafe);

                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Reklam user = s.getValue(Reklam.class);
                    LatLng location = new LatLng(user.getFx(), user.getFy());
                    String kategori= user.getKat();
                    Location kon= new Location("");
                    //String magaza=user.getF_ad();
                    kon.setLatitude(user.getFx());
                    kon.setLongitude(user.getFy());
                    float distanceInMeters = konum.distanceTo(kon);

                    if(distanceInMeters<=m){
                        mMap.addMarker(new MarkerOptions().position(location).title(user.getF_ad()).snippet(user.getIcerik())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                    }
                    else{
                        mMap.addMarker(new MarkerOptions().position(location).title(user.getF_ad()).snippet(user.getIcerik())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    }





                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*if (mMap != null) {
            mMap.addMarker(new MarkerOptions().position(Restoran)
                    .title("Restoran").snippet("Ramazanda indirimli menü!"));
            mMap.addMarker(new MarkerOptions().position(Market)
                    .title("Market").snippet("1 cips alana 2.si bedava!")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round)));

            //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUST_INT);
                }
                return;
            }
            else{

            }
            mMap.setMyLocationEnabled(true);

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Market,10));*/
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
