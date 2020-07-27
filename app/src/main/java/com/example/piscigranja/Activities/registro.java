package com.example.piscigranja.Activities;

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

import com.example.piscigranja.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import dmax.dialog.SpotsDialog;

public class registro extends AppCompatActivity {

    private TextInputLayout nombres, email, clave;
    private Button registro;
    private TextView txtYaTengo;
    private String Snombres = "";
    private String Semail = "";
    private String Sclave = "";
    public static final String URL_PHOTO_DEFAULT = "https://firebasestorage.googleapis.com/v0/b/piscigranja-137b5.appspot.com/o/fotos%2Fuser.png?alt=media&token=e2947537-60e8-4fbc-bffb-505be4158e0a";


    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    AlertDialog mDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme4);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nombres = (TextInputLayout) findViewById(R.id.etNombres);
        email = (TextInputLayout) findViewById(R.id.etEmail);
        clave = (TextInputLayout) findViewById(R.id.etClave);
        registro = (Button) findViewById(R.id.btnRegistro);
        txtYaTengo = (TextView) findViewById(R.id.txtyatengo);

        txtYaTengo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.piscigranja.Activities.registro.this, authLogin.class));
                finish();
            }
        });

        mDialog2 = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Registrando usuario")
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snombres = nombres.getEditText().getText().toString();
                Semail = email.getEditText().getText().toString();
                Sclave = clave.getEditText().getText().toString();

                if(!Snombres.isEmpty() && !Semail.isEmpty() && !Sclave.isEmpty()){
                    if(Sclave.length() >= 6){
                        mDialog2.show();
                        registrarUser();
                    }
                    else{
                        Toast.makeText(com.example.piscigranja.Activities.registro.this , "La contraseña debe tener más de 6 caracteres" ,Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(com.example.piscigranja.Activities.registro.this , "Debe llenar los campos" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    private void registrarUser(){

        mAuth.setLanguageCode("es");
        mAuth.createUserWithEmailAndPassword(Semail,Sclave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String , Object> map = new HashMap<>();
                    map.put("nombres",Snombres);
                    map.put("email",Semail);
                    map.put("clave",Sclave);
                    map.put("Dispositivo","vacio");
                    map.put("foto",URL_PHOTO_DEFAULT);
                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if(task2.isSuccessful()){
                                verficarUser();
                            }
                            else {
                                mDialog2.dismiss();
                                Toast.makeText(com.example.piscigranja.Activities.registro.this , "No se pudo crear los datos correctamente" ,Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else {
                    mDialog2.dismiss();
                    Toast.makeText(com.example.piscigranja.Activities.registro.this , "No se pudo registrar este usuario" ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void verficarUser(){
        FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification();
        alertaCorreo();
    }

    private void alertaCorreo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(registro.this,R.style.DialogCustomStyle);
        builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>Alerta!</font>"));
        builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>Debes confirmar tu correo</font>"))
                .setPositiveButton(Html.fromHtml("<font color='#ebebeb'>Confirmar correo</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog2.dismiss();
                        startActivity(new Intent(registro.this,authLogin.class));
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                        if (launchIntent != null) {
                            startActivity(launchIntent);//null pointer check in case package name was not found
                        }
                        finish();
                    }
                })
                .setNegativeButton(Html.fromHtml("<font color='#EDEA1C'>Cerrar</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(registro.this,authLogin.class));
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

}
