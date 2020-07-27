package com.example.piscigranja.Maps;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.piscigranja.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsIot extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
     DatabaseReference mDatabase, mDatabaseSerie;
     FirebaseAuth mAuth;
    private ProgressDialog mDialogActualizeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_iot);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseSerie = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDialogActualizeData = new ProgressDialog(this,R.style.MyAlertDialogData);
        mDialogActualizeData.setCancelable(false);
        mDialogActualizeData.show();
        mDialogActualizeData.setContentView(R.layout.dialog_data);
        mDialogActualizeData.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mDialogActualizeData.show();
        mMap = googleMap;
        String id = mAuth.getCurrentUser().getUid();
        mDatabaseSerie.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // String creado debe ser al de la base de datos
                if(dataSnapshot.exists()){
                    String serie_data = dataSnapshot.child("Dispositivo").getValue().toString();
                    mDatabase.child("dispositivos").child(serie_data).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                String lat = dataSnapshot.child("lat").getValue().toString();
                                String lon = dataSnapshot.child("lon").getValue().toString();
                                double lati = Double.valueOf(lat);
                                double longi= Double.valueOf(lon);
                                LatLng iot = new LatLng(lati, longi);
                                mMap.addMarker(new MarkerOptions().position(iot).title("Dispositivo IoT").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_google_markerfish)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(iot));
                                mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
                                mDialogActualizeData.dismiss();
                            }

                            else {
                                mDialogActualizeData.dismiss();
                                finish();
                                Toast.makeText(MapsIot.this , "Error , añada dispositivo" ,Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            mDialogActualizeData.dismiss();
                            finish();
                            Toast.makeText(MapsIot.this , "Error de base de datos" ,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDialogActualizeData.dismiss();
                finish();
                Toast.makeText(MapsIot.this , "Error de base de datos" ,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down);
    }

}
