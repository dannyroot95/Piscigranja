package com.example.piscigranja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class getDataPh extends AppCompatActivity {


    private TextView txtData,serie_txt,txtPhVoltage,txtscala,txt_estado;
    private DatabaseReference mDatabase, mDatabaseSerie;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialogActualizeData;
    private ImageView fl0,fl1,fl2,fl3,fl4,fl5,fl6,fl7,fl8,fl9,fl10,fl11,fl12,fl13,fl14,pezbien,pezmal;
    private ImageView img0,img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12,img13,img14,img_error;
    private String E = "Excelente";
    private String P = "Pésimo";
    private String AL = "Alcalino";
    private String N = "Neutro";
    private String AC = "Ácido";
    private String ER = "Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme4);
        setContentView(R.layout.activity_get_data_ph);
        getSupportActionBar().hide();

        mDialogActualizeData = new ProgressDialog(this,R.style.MyAlertDialogData);
        mDialogActualizeData.setCancelable(false);
        mDialogActualizeData.show();
        mDialogActualizeData.setContentView(R.layout.dialog_data);
        mDialogActualizeData.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        txtData = (TextView) findViewById(R.id.txtData);
        txtPhVoltage = (TextView) findViewById(R.id.txtphvoltage);
        serie_txt = (TextView)findViewById(R.id.txtReciveSerie);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mDatabaseSerie = FirebaseDatabase.getInstance().getReference();

        txtscala = (TextView) findViewById(R.id.txtScala);
        txt_estado = (TextView) findViewById(R.id.estado);

        fl0 = (ImageView) findViewById(R.id.fl0);
        fl1 = (ImageView) findViewById(R.id.fl1);
        fl2 = (ImageView) findViewById(R.id.fl2);
        fl3 = (ImageView) findViewById(R.id.fl3);
        fl4 = (ImageView) findViewById(R.id.fl4);
        fl5 = (ImageView) findViewById(R.id.fl5);
        fl6 = (ImageView) findViewById(R.id.fl6);
        fl7 = (ImageView) findViewById(R.id.fl7);
        fl8 = (ImageView) findViewById(R.id.fl8);
        fl9 = (ImageView) findViewById(R.id.fl9);
        fl10 = (ImageView) findViewById(R.id.fl10);
        fl11 = (ImageView) findViewById(R.id.fl11);
        fl12 = (ImageView) findViewById(R.id.fl12);
        fl13 = (ImageView) findViewById(R.id.fl13);
        fl14 = (ImageView) findViewById(R.id.fl14);

        img0 = (ImageView) findViewById(R.id.img0);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        img6 = (ImageView) findViewById(R.id.img6);
        img7 = (ImageView) findViewById(R.id.img7);
        img8 = (ImageView) findViewById(R.id.img8);
        img9 = (ImageView) findViewById(R.id.img9);
        img10 = (ImageView) findViewById(R.id.img10);
        img11 = (ImageView) findViewById(R.id.img11);
        img12 = (ImageView) findViewById(R.id.img12);
        img13 = (ImageView) findViewById(R.id.img13);
        img14 = (ImageView) findViewById(R.id.img14);
        img_error = (ImageView) findViewById(R.id.img_error);

        pezbien = (ImageView) findViewById(R.id.pezbien);
        pezmal =  (ImageView) findViewById (R.id.pezmal);

    String id = mAuth.getCurrentUser().getUid();
        mDatabaseSerie.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // String creado debe ser al de la base de datos
                if(dataSnapshot.exists()){
                    String serie_data = dataSnapshot.child("Dispositivo").getValue().toString();
                    serie_txt.setText(serie_data);
                    mDatabase.child("dispositivos").child(serie_data).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                String ph = dataSnapshot.child("ph").getValue().toString();
                                String phvoltage = dataSnapshot.child("phvoltage").getValue().toString();
                                txtData.setText(ph);
                                txtPhVoltage.setText(phvoltage+"mv");

                                double ph_value = Double.parseDouble(ph);

                                if(ph_value  < 0.00)

                                {

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img_error.setVisibility(View.VISIBLE);
                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(ER);
                                    txt_estado.setText(ER);

                                }

                                if(ph_value >= 0.00 && ph_value <= 0.99){

                                    fl0.setVisibility(View.VISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.VISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AC);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 1.00 && ph_value <= 1.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.VISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.VISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AC);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 2.00 && ph_value <= 2.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.VISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.VISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AC);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 1.00 && ph_value <= 1.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.VISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.VISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AC);
                                    txt_estado.setText(P);

                                }


                                if(ph_value >= 3.00 && ph_value <= 3.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.VISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.VISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AC);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 4.00 && ph_value <= 4.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.VISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.VISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AC);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 5.00 && ph_value <= 5.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.VISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.VISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AC);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 6.00 && ph_value <= 6.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.VISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.VISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AC);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 7.00 && ph_value <= 8.50){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.VISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.VISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.VISIBLE);
                                    pezmal.setVisibility(View.INVISIBLE);

                                    txtscala.setText(N);
                                    txt_estado.setText(E);

                                }

                                if(ph_value >= 8.50 && ph_value <= 8.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.VISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.VISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.VISIBLE);
                                    pezmal.setVisibility(View.INVISIBLE);

                                    txtscala.setText(AL);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 9.00 && ph_value <= 9.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.VISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.VISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AL);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 10.00 && ph_value <= 10.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.VISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.VISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AL);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 11.00 && ph_value <= 11.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.VISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.VISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AL);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 12.00 && ph_value <= 12.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.VISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.VISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AL);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 13.00 && ph_value <= 13.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.VISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.VISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AL);
                                    txt_estado.setText(P);

                                }

                                if(ph_value >= 14.00 && ph_value <= 14.99){

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.VISIBLE);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.VISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(AL);
                                    txt_estado.setText(P);

                                }

                                if(ph_value  > 14.99)

                                {

                                    fl0.setVisibility(View.INVISIBLE);
                                    fl1.setVisibility(View.INVISIBLE);
                                    fl2.setVisibility(View.INVISIBLE);
                                    fl3.setVisibility(View.INVISIBLE);
                                    fl4.setVisibility(View.INVISIBLE);
                                    fl5.setVisibility(View.INVISIBLE);
                                    fl6.setVisibility(View.INVISIBLE);
                                    fl7.setVisibility(View.INVISIBLE);
                                    fl8.setVisibility(View.INVISIBLE);
                                    fl9.setVisibility(View.INVISIBLE);
                                    fl10.setVisibility(View.INVISIBLE);
                                    fl11.setVisibility(View.INVISIBLE);
                                    fl12.setVisibility(View.INVISIBLE);
                                    fl13.setVisibility(View.INVISIBLE);
                                    fl14.setVisibility(View.INVISIBLE);

                                    img_error.setVisibility(View.VISIBLE);
                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img3.setVisibility(View.INVISIBLE);
                                    img4.setVisibility(View.INVISIBLE);
                                    img5.setVisibility(View.INVISIBLE);
                                    img6.setVisibility(View.INVISIBLE);
                                    img7.setVisibility(View.INVISIBLE);
                                    img8.setVisibility(View.INVISIBLE);
                                    img9.setVisibility(View.INVISIBLE);
                                    img10.setVisibility(View.INVISIBLE);
                                    img11.setVisibility(View.INVISIBLE);
                                    img12.setVisibility(View.INVISIBLE);
                                    img13.setVisibility(View.INVISIBLE);
                                    img14.setVisibility(View.INVISIBLE);

                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    txtscala.setText(ER);
                                    txt_estado.setText(ER);

                                }

                                mDialogActualizeData.dismiss();

                            }

                            else {
                                mDialogActualizeData.dismiss();
                                finish();
                                Toast.makeText(getDataPh.this , "Error , añada su dispositivo" ,Toast.LENGTH_LONG).show();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            mDialogActualizeData.dismiss();
                            finish();
                            Toast.makeText(getDataPh.this , "Error" ,Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                else {
                    mDialogActualizeData.dismiss();
                    finish();
                    Toast.makeText(getDataPh.this , "Error" ,Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDialogActualizeData.dismiss();
                finish();
                Toast.makeText(getDataPh.this , "Error" ,Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down);
    }

}
