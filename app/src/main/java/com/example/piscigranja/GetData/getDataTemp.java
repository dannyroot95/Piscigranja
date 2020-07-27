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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class getDataTemp extends AppCompatActivity {

    private TextView txtData,lati,longi;
    private DatabaseReference mDatabase, mDatabaseSerie;
    private TextView serie_txt,temp_amb,ciudad,descripcion_nub,fecha,humedad,presion,viento,hora_txt;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialogActualizeData;
    ImageView icon_nube;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme4);
        setContentView(R.layout.activity_get_data_temp);
        getSupportActionBar().hide();

        mDialogActualizeData = new ProgressDialog(this,R.style.MyAlertDialogData);
        mDialogActualizeData.setCancelable(false);
        mDialogActualizeData.show();
        mDialogActualizeData.setContentView(R.layout.dialog_data);
        mDialogActualizeData.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        txtData = (TextView) findViewById(R.id.txtData);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        serie_txt = (TextView)findViewById(R.id.txtReciveSerie);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseSerie = FirebaseDatabase.getInstance().getReference();

        temp_amb = (TextView)findViewById(R.id.temp_amb);
        ciudad = (TextView)findViewById(R.id.ciudad);
        descripcion_nub = (TextView)findViewById(R.id.descrip);
        fecha = (TextView)findViewById(R.id.fecha_hoy);
        presion = (TextView)findViewById(R.id.presion);
        humedad = (TextView) findViewById(R.id.humedad);
        viento = (TextView) findViewById(R.id.viento);
        icon_nube = (ImageView) findViewById(R.id.icono_nube);
        hora_txt = (TextView) findViewById(R.id.hora);
        lati = (TextView) findViewById(R.id.lat);
        longi = (TextView) findViewById(R.id.lon);





        String id = mAuth.getCurrentUser().getUid();
        mDatabaseSerie.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // String creado debe ser al de la base de datos
                if(dataSnapshot.exists()){
                    String serie_data = dataSnapshot.child("Dispositivo").getValue().toString();
                    serie_txt.setVisibility(View.INVISIBLE);
                    serie_txt.setText(serie_data);
                    mDatabase.child("dispositivos").child(serie_data).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                String temp = dataSnapshot.child("temperatura").getValue().toString();
                                String lat = dataSnapshot.child("lat").getValue().toString();
                                String lon = dataSnapshot.child("lon").getValue().toString();
                                txtData.setText(temp+"C°");
                                lati.setText(lat);
                                longi.setText(lon);
                                find_weather();
                            }

                            else {
                                mDialogActualizeData.dismiss();
                                finish();
                                Toast.makeText(getDataTemp.this , "Error , añada su dispositivo" ,Toast.LENGTH_LONG).show();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            mDialogActualizeData.dismiss();
                            finish();
                            Toast.makeText(getDataTemp.this , "Error" ,Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                else {
                    mDialogActualizeData.dismiss();
                    finish();
                    Toast.makeText(getDataTemp.this , "Error" ,Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDialogActualizeData.dismiss();
                finish();
                Toast.makeText(getDataTemp.this , "Error" ,Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void find_weather()
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

                    JSONObject main_object = response.getJSONObject("main");
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String pressure = String.valueOf(main_object.getDouble("pressure"));
                    String humidity = String.valueOf(main_object.getDouble("humidity"));

                    JSONObject wind_object = response.getJSONObject("wind");
                    String speed =  String.valueOf(wind_object.getDouble("speed"));

                    String city = response.getString("name");

                    //  t1_temp.setText(temp);
                    ciudad.setText(city);

                    double inttemp = Double.valueOf(temp);
                    int k = (int)inttemp;
                    String newtemp = Integer.toString(k);
                    temp_amb.setText(String.valueOf(newtemp+"C°"));

                    descripcion_nub.setText(description);
                    double numpress = Double.valueOf(pressure);
                    int i = (int)numpress;
                    String newpress = Integer.toString(i);
                    presion.setText(newpress+"Pa");

                    double numhumidity = Double.valueOf(humidity);
                    int j = (int)numhumidity;
                    String newhumidity = Integer.toString(j);
                    humedad.setText(newhumidity+"%");
                    viento.setText(speed+"m/s");
                    Picasso.get().load(iconUrl).into(icon_nube);


                   /* if(description.equals(nublado)){
                        t3_description.setText("Nublado");
                    }*/

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String formatted_date = sdf.format(calendar.getTime());
                    Date date = new Date();
                    DateFormat hourdateFormat = new SimpleDateFormat("HH:mm aaa");
                    String format_hour = hourdateFormat.format(date);
                    fecha.setText(formatted_date);
                    hora_txt.setText(format_hour);
                    mDialogActualizeData.dismiss();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down);
    }
}
