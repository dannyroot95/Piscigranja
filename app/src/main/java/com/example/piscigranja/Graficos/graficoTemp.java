package com.example.piscigranja.Graficos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.piscigranja.Graficos.Barras.barrasTemp;
import com.example.piscigranja.Slider.MySingleton;
import com.example.piscigranja.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class graficoTemp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mDialog;
    private FloatingActionButton btnfloat;

    ArrayList<Entry> x;
    ArrayList<String> y;
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_temp);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDialog = new ProgressDialog(this);
        btnfloat = (FloatingActionButton) findViewById(R.id.barras);
        x = new ArrayList<Entry>();
        y = new ArrayList<String>();
        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setScaleMinima(2f, 1f);
        mChart.fitScreen();
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.gota);

        btnfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(graficoTemp.this, barrasTemp.class));
            }
        });

        getDataUser();
    }

    private void getDataUser(){

        mDialog.show();
        mDialog.setMessage("Cargando datos");

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // String creado debe ser al de la base de datos
                if(dataSnapshot.exists()){
                    String serie = dataSnapshot.child("Dispositivo").getValue().toString();

                    String url = "http://piscigranja.org/datachip/"+serie;

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray jsonArray = response.getJSONArray("data");

                                for(int i = 0 ; i<jsonArray.length() ; i++){
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    String temperatura = data.getString("temperatura");
                                    int temp_int = data.getInt("temperatura");
                                    x.add(new Entry(temp_int, i));
                                    y.add(temperatura+"C°");
                                    mDialog.dismiss();
                                }

                            } catch (JSONException e) {

                                e.printStackTrace();
                                Toast.makeText(graficoTemp.this,"Error de conexión",Toast.LENGTH_LONG).show();
                                mDialog.dismiss();

                            }

                            LineDataSet set1 = new LineDataSet(x, "Temperatura");
                            set1.setColor(Color.rgb(0, 82, 159));
                            set1.setLineWidth(1.5f);
                            set1.setCircleColor(Color.rgb(0, 82, 159));
                            LineData data = new LineData(y, set1);
                            mChart.setData(data);
                            mChart.invalidate();
                            Legend l = mChart.getLegend();
                            l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            mDialog.dismiss();
                            finish();
                            Toast.makeText(graficoTemp.this , "Error , añada su dispositivo" ,Toast.LENGTH_LONG).show();
                        }
                    });

                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                mDialog.dismiss();
                Toast.makeText(graficoTemp.this,"Error de base de datos",Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down);
    }

}
