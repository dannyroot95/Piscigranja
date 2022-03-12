package com.example.piscigranja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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

public class barrasTurbidez extends AppCompatActivity {

    ArrayList yAxis;
    ArrayList yValues;
    ArrayList xAxis1;
    BarEntry values ;
    BarChart chart;
    BarData data;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barras_turbidez);

        mDialog = new ProgressDialog(this);
        chart = (BarChart) findViewById(R.id.chart);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        getDataUser();

    }
    private void getDataUser(){

        mDialog.show();
        mDialog.setMessage("Cargando datos");

        xAxis1 = new ArrayList<>();
        yAxis = null;
        yValues = new ArrayList<>();

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
                                    String turbidez = data.getString("turbidez");
                                    xAxis1.add("");
                                    values = new BarEntry(Float.valueOf(turbidez),i);
                                    yValues.add(values);
                                    mDialog.dismiss();

                                }

                            } catch (JSONException e) {

                                e.printStackTrace();
                                Toast.makeText(barrasTurbidez.this,"Error de conexión",Toast.LENGTH_LONG).show();
                                mDialog.dismiss();

                            }

                            BarDataSet barDataSet1 = new BarDataSet(yValues, "Turbidez");
                            barDataSet1.setColor(Color.rgb(126, 0, 138));

                            yAxis = new ArrayList<>();
                            yAxis.add(barDataSet1);
                            String names[]= (String[]) xAxis1.toArray(new String[xAxis1.size()]);
                            data = new BarData(names,yAxis);
                            chart.setData(data);
                            chart.setDescription("");
                            chart.setTouchEnabled(true);
                            chart.setDragEnabled(true);
                            chart.setPinchZoom(true);
                            chart.setScaleEnabled(true);
                            chart.animateXY(2000, 2000);
                            chart.invalidate();
                            Legend l = chart.getLegend();
                            l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);




                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                mDialog.dismiss();
                Toast.makeText(barrasTurbidez.this,"Error de base de datos",Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down);
    }

}
