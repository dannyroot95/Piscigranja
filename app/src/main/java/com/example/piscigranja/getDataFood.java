package com.example.piscigranja;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class getDataFood extends AppCompatActivity  {

    private TextInputLayout Cp;
    private TextInputLayout  Pp;
    private Button procesar;
    private TextInputLayout  result;
    private String Spp = "";
    private String Scp = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme5);
        setContentView(R.layout.activity_get_data_food);
        getSupportActionBar().hide();
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.gota);

        Cp = (TextInputLayout)findViewById(R.id.etProm);
        Pp = (TextInputLayout)findViewById(R.id.etPeso);
        result = (TextInputLayout)findViewById(R.id.etresult);
        procesar = (Button)findViewById(R.id.btnprocecesoAlim);


        procesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Scp = Cp.getEditText().getText().toString();
                Spp = Pp.getEditText().getText().toString();

                if (Scp.isEmpty() && Spp.isEmpty() || Scp.isEmpty() || Spp.isEmpty()){
                    Toast.makeText(getDataFood.this , "Ingrese los datos" ,Toast.LENGTH_SHORT).show();

                }

                else {

                    long pp = Long.parseLong(Pp.getEditText().getText().toString());
                    long cp = Long.parseLong(Cp.getEditText().getText().toString());
                    long bm;
                    double Ra;
                    double Ra2, Ra_fin;


                    if (pp >= 1 && pp <= 14) {

                        bm = cp * pp;
                        Ra = ((bm * 10) / 100);
                        Ra2 = Ra / 1000.0;
                        Ra_fin = Ra2 / 2;

                        result.setHint(String.valueOf(Ra_fin + " Kg"));

                    } else if (pp >= 15 && pp <= 20) {
                        bm = cp * pp;
                        Ra = ((bm * 6) / 100);
                        Ra2 = Ra / 1000.0;
                        Ra_fin = Ra2 / 2;
                        result.setHint(String.valueOf(Ra_fin + " Kg"));

                    } else if (pp >= 21 && pp <= 34) {
                        bm = cp * pp;
                        Ra = ((bm * 5) / 100);
                        Ra2 = Ra / 1000.0;
                        Ra_fin = Ra2 / 2;
                        result.setHint(String.valueOf(Ra_fin + " Kg"));

                    } else if (pp >= 35 && pp <= 44) {
                        bm = cp * pp;
                        Ra = ((bm * 4) / 100);
                        Ra2 = Ra / 1000.0;
                        Ra_fin = Ra2 / 2;
                        result.setHint(String.valueOf(Ra_fin + " Kg"));

                    } else if (pp >= 45 && pp <= 54) {
                        bm = cp * pp;
                        Ra = ((bm * 3) / 100);
                        Ra2 = Ra / 1000.0;
                        Ra_fin = Ra2 / 2;
                        result.setHint(String.valueOf(Ra_fin + " Kg"));

                    } else if (pp >= 45 && pp <= 54) {
                        bm = cp * pp;
                        Ra = ((bm * 3) / 100);
                        Ra2 = Ra / 1000.0;
                        Ra_fin = Ra2 / 2;
                        result.setHint(String.valueOf(Ra_fin + " Kg"));

                    } else if (pp >= 55 && pp <= 229) {
                        bm = cp * pp;
                        Ra = ((bm * 2.5) / 100);
                        Ra2 = Ra / 1000.0;
                        Ra_fin = Ra2 / 2;
                        result.setHint(String.valueOf(Ra_fin + " Kg"));

                    } else if (pp >= 230 && pp <= 330) {
                        bm = cp * pp;
                        Ra = ((bm * 2) / 100);
                        Ra2 = Ra / 1000.0;
                        Ra_fin = Ra2 / 2;
                        result.setHint(String.valueOf(Ra_fin + " Kg"));

                    } else if (pp >= 331 && pp <= 380) {
                        bm = cp * pp;
                        Ra = ((bm * 1.9) / 100);
                        Ra2 = Ra / 1000.0;
                        Ra_fin = Ra2 / 2;
                        result.setHint(String.valueOf(Ra_fin + " Kg"));

                    } else if (pp >= 381 && pp <= 432) {
                        bm = cp * pp;
                        Ra = ((bm * 1.8) / 100);
                        Ra2 = Ra / 1000.0;
                        Ra_fin = Ra2 / 2;
                        result.setHint(String.valueOf(Ra_fin + " Kg"));

                    } else if (pp >= 433 && pp <= 516) {
                        bm = cp * pp;
                        Ra = ((bm * 1.6) / 100);
                        Ra2 = Ra / 1000.0;
                        Ra_fin = Ra2 / 2;
                        result.setHint(String.valueOf(Ra_fin + " Kg"));

                    } else if (pp == 0 && cp == 0) {
                        Toast.makeText(getDataFood.this, "Error", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getDataFood.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

   }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down);
    }

}