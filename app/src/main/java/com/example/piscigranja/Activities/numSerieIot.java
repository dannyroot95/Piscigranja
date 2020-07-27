package com.example.piscigranja.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.piscigranja.R;
import com.example.piscigranja.Servicios.serviceNotificacion;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class numSerieIot extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnPerfil;
    private TextInputLayout etxSerie;
    private String Sserie = "";
    private DatabaseReference mDatabase ;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme6);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_num_serie_iot);
        mAuth = FirebaseAuth.getInstance();
        btnPerfil = (Button) findViewById(R.id.btnPerfil);
        etxSerie = (TextInputLayout) findViewById(R.id.etxSerie);

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Añadiendo dispositivo")
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();


        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                Sserie = etxSerie.getEditText().getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dispositivos").child(Sserie);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        if (dataSnapshot.exists() ) {
                            if (!Sserie.isEmpty()){
                                mDialog.show();
                                Sserie = etxSerie.getEditText().getText().toString();
                                Map<String,Object> numSerie = new HashMap<>();
                                numSerie.put("Dispositivo" , Sserie);
                                String id = mAuth.getCurrentUser().getUid();
                                mDatabase.child("Usuarios").child(id).updateChildren(numSerie);
                                stopService(new Intent(numSerieIot.this, serviceNotificacion.class));
                                startService(new Intent(numSerieIot.this,serviceNotificacion.class));
                                startActivity(new Intent(numSerieIot.this, Succesfull.class));
                                finish();
                            }
                            else {
                                mDialog.dismiss();
                                Toast.makeText(numSerieIot.this , "Complete los campos" ,Toast.LENGTH_SHORT).show();

                            }

                        }

                        else {
                            mDialog.dismiss();
                            Toast.makeText(numSerieIot.this , "Error de número de serie" ,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down);
    }

}
