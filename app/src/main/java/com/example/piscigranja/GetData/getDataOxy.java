package com.example.piscigranja.GetData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.piscigranja.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getDataOxy extends AppCompatActivity {

    private TextView txtData,serie_txt,txtOxyVoltage,txtanalisis,txtestado,lati,longi;
    private DatabaseReference mDatabase, mDatabaseSerie;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialogActualizeData;
    private String E = "Excelente";
    private String ER = "Error";
    private String P = "Pésimo";
    private String LVL0 = "Concentración de oxigeno letal";
    private String LVL1 = "Crecimiento lento";
    private String LVL2 = "Oxigenación pecfecta";
    private ImageView pezbien,pezmal,icon_nube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme4);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data_oxy);
        getSupportActionBar().hide();

        mDialogActualizeData = new ProgressDialog(this,R.style.MyAlertDialogData);
        mDialogActualizeData.setCancelable(false);
        mDialogActualizeData.show();
        mDialogActualizeData.setContentView(R.layout.dialog_data);
        mDialogActualizeData.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        txtData = (TextView) findViewById(R.id.txtData);
        txtOxyVoltage = (TextView) findViewById(R.id.txtoxyvoltage);
        serie_txt = (TextView)findViewById(R.id.txtReciveSerie);
        txtestado = (TextView) findViewById(R.id.estado);
        txtanalisis = (TextView) findViewById(R.id.txtestadoxy);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mDatabaseSerie = FirebaseDatabase.getInstance().getReference();

        pezbien = (ImageView) findViewById(R.id.pezbien);
        pezmal =  (ImageView) findViewById (R.id.pezmal);

        lati = (TextView) findViewById(R.id.lat);
        longi = (TextView) findViewById(R.id.lon);
        icon_nube = (ImageView) findViewById(R.id.icono_nube);

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
                                String oxy = dataSnapshot.child("oxigeno").getValue().toString();
                                String oxyvoltage = dataSnapshot.child("oxigenovoltage").getValue().toString();
                                String lat = dataSnapshot.child("lat").getValue().toString();
                                String lon = dataSnapshot.child("lon").getValue().toString();
                                lati.setText(lat);
                                longi.setText(lon);
                                txtData.setText(oxy);
                                txtOxyVoltage.setText(oxyvoltage+"v");
                                double oxy_value = Double.parseDouble(oxy);

                                    if(oxy_value < 0.3){
                                        txtestado.setText(ER);
                                        txtanalisis.setText(ER);
                                        pezmal.setVisibility(View.VISIBLE);
                                        pezbien.setVisibility(View.INVISIBLE);
                                    }

                                    if(oxy_value >= 0.3 && oxy_value <= 1.0){
                                        txtestado.setText(P);
                                        txtanalisis.setText(LVL0);
                                        pezmal.setVisibility(View.VISIBLE);
                                        pezbien.setVisibility(View.INVISIBLE);
                                    }

                                if(oxy_value > 1.0 && oxy_value <= 5.0){
                                    txtestado.setText(P);
                                    txtanalisis.setText(LVL1);
                                    pezmal.setVisibility(View.VISIBLE);
                                    pezbien.setVisibility(View.INVISIBLE);
                                }

                                if(oxy_value > 5.0 && oxy_value <= 8.5){
                                    txtestado.setText(E);
                                    txtanalisis.setText(LVL2);
                                    pezmal.setVisibility(View.INVISIBLE);
                                    pezbien.setVisibility(View.VISIBLE);
                                }

                                if(oxy_value > 8.5){
                                    txtestado.setText(P);
                                    txtanalisis.setText("Sobre oxigeno");
                                    pezmal.setVisibility(View.VISIBLE);
                                    pezbien.setVisibility(View.INVISIBLE);
                                }

                                find_weather();
                                mDialogActualizeData.dismiss();
                            }

                            else {
                                mDialogActualizeData.dismiss();
                                finish();
                                Toast.makeText(getDataOxy.this , "Error , añada su dispositivo" ,Toast.LENGTH_LONG).show();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            mDialogActualizeData.dismiss();
                            finish();
                            Toast.makeText(getDataOxy.this , "Error" ,Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                else {
                    mDialogActualizeData.dismiss();
                    finish();
                    Toast.makeText(getDataOxy.this , "Error" ,Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDialogActualizeData.dismiss();
                finish();
                Toast.makeText(getDataOxy.this , "Error" ,Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void find_weather ()
    {
        String latitude = lati.getText().toString();
        String longitude = longi.getText().toString();
        String url ="http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=b1bb0be6342ff88227cd385d84a486ae&units=metric&lang=es";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    /* String nublado = "overcast clouds";*/
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String description = object.getString("description");
                    String icono = object.getString("icon");
                    String iconUrl = "http://openweathermap.org/img/w/" + icono + ".png";
                    Picasso.get().load(iconUrl).into(icon_nube);
                }catch(JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);

    }

}
