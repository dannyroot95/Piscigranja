package com.example.piscigranja.GetData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.example.piscigranja.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class getDataTurbidez extends AppCompatActivity {

    private TextView txtData,serie_txt,txtTurbVoltage,txtnv,txtestado;
    private DatabaseReference mDatabase, mDatabaseSerie;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialogActualizeData;
    private LottieAnimationView ani0,ani1,ani2;
    private String E = "Excelente";
    private String P = "Pésimo";
    private String T = "Transparente";
    private String N = "Normal";
    private String TR = "Turbio";
    private String ER = "Error";
    private ImageView img0,img1,img2,pezbien,pezmal,img_error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme4);
        setContentView(R.layout.activity_get_data_turbidez);
        getSupportActionBar().hide();

        mDialogActualizeData = new ProgressDialog(this,R.style.MyAlertDialogData);
        mDialogActualizeData.setCancelable(false);
        mDialogActualizeData.show();
        mDialogActualizeData.setContentView(R.layout.dialog_data);
        mDialogActualizeData.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        txtData = (TextView) findViewById(R.id.txtData);
        txtTurbVoltage = (TextView) findViewById(R.id.txtturbvoltage);
        serie_txt = (TextView)findViewById(R.id.txtReciveSerie);
        txtestado = (TextView) findViewById(R.id.estado);
        txtnv = (TextView) findViewById(R.id.nvturb);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mDatabaseSerie = FirebaseDatabase.getInstance().getReference();

        img0 = (ImageView) findViewById(R.id.img0);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img_error = (ImageView) findViewById(R.id.imgerror);
        pezbien = (ImageView) findViewById(R.id.pezbien);
        pezmal =  (ImageView) findViewById (R.id.pezmal);

        ani0 = (LottieAnimationView) findViewById(R.id.ani0);
        ani1 = (LottieAnimationView) findViewById(R.id.ani1);
        ani2 = (LottieAnimationView) findViewById(R.id.ani2);

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
                                String turb = dataSnapshot.child("ntu").getValue().toString();
                                String turbvoltage = dataSnapshot.child("ntuvoltage").getValue().toString();
                                txtData.setText(turb+" NTU");
                                txtTurbVoltage.setText(turbvoltage+"v");

                                double turb_value = Double.parseDouble(turb);

                                if(turb_value < -1201.55 )

                                {
                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    ani0.setVisibility(View.VISIBLE);
                                    ani1.setVisibility(View.INVISIBLE);
                                    ani2.setVisibility(View.INVISIBLE);

                                    txtestado.setText(ER);
                                    txtnv.setText(ER);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img_error.setVisibility(View.VISIBLE);

                                }

                                if(turb_value < 600.00 )

                                {
                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    ani0.setVisibility(View.VISIBLE);
                                    ani1.setVisibility(View.INVISIBLE);
                                    ani2.setVisibility(View.INVISIBLE);

                                    txtestado.setText(P);

                                    txtnv.setText(T);

                                    img0.setVisibility(View.VISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img_error.setVisibility(View.INVISIBLE);

                                }

                                if(turb_value > 600.00 && turb_value <= 2900.99 )

                                {
                                    pezbien.setVisibility(View.VISIBLE);
                                    pezmal.setVisibility(View.INVISIBLE);

                                    ani0.setVisibility(View.INVISIBLE);
                                    ani1.setVisibility(View.VISIBLE);
                                    ani2.setVisibility(View.INVISIBLE);

                                    txtestado.setText(E);

                                    txtnv.setText(N);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.VISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img_error.setVisibility(View.INVISIBLE);

                                }

                                if(turb_value > 2900.99 && turb_value <= 3000.00 )

                                {
                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    ani0.setVisibility(View.INVISIBLE);
                                    ani1.setVisibility(View.INVISIBLE);
                                    ani2.setVisibility(View.VISIBLE);

                                    txtestado.setText(P);

                                    txtnv.setText(TR);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.VISIBLE);
                                    img_error.setVisibility(View.INVISIBLE);

                                }

                                if( turb_value > 3000.00 )

                                {
                                    pezbien.setVisibility(View.INVISIBLE);
                                    pezmal.setVisibility(View.VISIBLE);

                                    ani0.setVisibility(View.INVISIBLE);
                                    ani1.setVisibility(View.INVISIBLE);
                                    ani2.setVisibility(View.VISIBLE);

                                    txtestado.setText(ER);

                                    txtnv.setText(ER);

                                    img0.setVisibility(View.INVISIBLE);
                                    img1.setVisibility(View.INVISIBLE);
                                    img2.setVisibility(View.INVISIBLE);
                                    img_error.setVisibility(View.VISIBLE);

                                }

                                mDialogActualizeData.dismiss();
                            }

                            else {
                                mDialogActualizeData.dismiss();
                                finish();
                                Toast.makeText(getDataTurbidez.this , "Error , añada su dispositivo" ,Toast.LENGTH_LONG).show();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            mDialogActualizeData.dismiss();
                            finish();
                            Toast.makeText(getDataTurbidez.this , "Error" ,Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                else {
                    mDialogActualizeData.dismiss();
                    finish();
                    Toast.makeText(getDataTurbidez.this , "Error" ,Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDialogActualizeData.dismiss();
                finish();
                Toast.makeText(getDataTurbidez.this , "Error" ,Toast.LENGTH_SHORT).show();
            }
        });

    }


}
