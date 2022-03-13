package com.example.piscigranja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dmax.dialog.SpotsDialog;

public class login extends AppCompatActivity {

    private Button btnLanzarRegistro,btnLoguearse;
    private String Semail = "";
    private String Sclave = "";
    private FirebaseAuth mAuth;
    AlertDialog mDialog;
    private TextView txtRecuperar;
    private TextInputLayout email,clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme4);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        email = (TextInputLayout) findViewById(R.id.etLoginEmail);
        clave = (TextInputLayout) findViewById(R.id.etLoginClave);
        btnLoguearse = (Button) findViewById(R.id.btnlogin);
        btnLanzarRegistro = (Button) findViewById(R.id.btnLanzarRegistro);
        txtRecuperar = (TextView) findViewById(R.id.txtRecuperarClave);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Iniciando sesión")
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();


        btnLoguearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Semail = email.getEditText().getText().toString();
                Sclave = clave.getEditText().getText().toString();

                if(!Semail.isEmpty() && !Sclave.isEmpty()){
                    mDialog.show();
                    loginUser();
                }

                else
                {
                    mDialog.dismiss();
                    Toast.makeText(login.this , "Debes completar los campos" ,Toast.LENGTH_SHORT).show();
                }



            }
        });

        btnLanzarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, registro.class));
            }
        });


        txtRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,resetPassword.class));
            }
        });


    }


    private void loginUser(){

        mAuth.signInWithEmailAndPassword(Semail,Sclave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(task.isSuccessful()){
                    startActivity(new Intent(login.this,Succesfull.class));
                    //startService(new Intent(login.this,serviceNotificacion.class));
                    finish();
                }
                else
                {
                    mDialog.dismiss();
                    Toast.makeText(login.this , "No se pudo iniciar sesión , Compruebe sus datos" ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // evitar volver a iniciar sesion cuando lo hiciste
    @Override
    protected void onStart() {
        FirebaseUser user = mAuth.getCurrentUser();
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(login.this,Succesfull.class));
            finish();
        }
    }

    private void alertaCorreo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(login.this,R.style.DialogCustomStyle);
        builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>Alerta!</font>"));
        builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>Debes confirmar tu correo</font>"))
                .setPositiveButton(Html.fromHtml("<font color='#ebebeb'>Confirmar correo</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.dismiss();
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                        if (launchIntent != null) {
                            startActivity(launchIntent);//null pointer check in case package name was not found
                        }
                    }
                })
                .setNegativeButton(Html.fromHtml("<font color='#EDEA1C'>Cerrar</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .show();
    }

}
