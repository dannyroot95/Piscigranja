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
import android.widget.Toast;

import com.example.piscigranja.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dmax.dialog.SpotsDialog;

public class authLogin extends AppCompatActivity {
    private Button btnLoguearse;
    private TextInputLayout email,clave;
    private String Semail = "";
    private String Sclave = "";
    private FirebaseAuth mAuth;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme4);
        setContentView(R.layout.activity_auth_login);
        getSupportActionBar().hide();

        email = (TextInputLayout) findViewById(R.id.etLoginEmail);
        clave = (TextInputLayout) findViewById(R.id.etLoginClave);
        btnLoguearse = (Button) findViewById(R.id.btnlogin);
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
                    Toast.makeText(authLogin.this , "Debes completar los campos" ,Toast.LENGTH_SHORT).show();
                }



            }
        });


    }

    private void loginUser(){

        mAuth.signInWithEmailAndPassword(Semail,Sclave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(task.isSuccessful()){
                    if(user.isEmailVerified()){
                        startActivity(new Intent(authLogin.this, Succesfull.class));
                        finish();
                    }
                    else {
                        mDialog.dismiss();
                        alertaCorreo();
                    }
                }
                else
                {
                    mDialog.dismiss();
                    Toast.makeText(authLogin.this , "No se pudo iniciar sesión , Compruebe sus datos" ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void alertaCorreo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(authLogin.this,R.style.DialogCustomStyle);
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

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
}

}