package com.example.piscigranja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class resetPassword extends AppCompatActivity {

    private Button btnBackLogin,btnResetPass;
    private String email="";
    private TextInputLayout etEmail_reset;
    private FirebaseAuth mAuth;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme4);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().hide();

        btnBackLogin = (Button) findViewById(R.id.btnatrasrecp);
        btnResetPass = (Button) findViewById(R.id.btnResetPass);
        etEmail_reset = (TextInputLayout) findViewById(R.id.etRecPassword);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Recuperando contraseña")
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();

        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(resetPassword.this,login.class));
                finish();
            }
        });

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail_reset.getEditText().getText().toString();

                if(!email.isEmpty()){
                    mDialog.show();
                    resetPassword();
                }

                else {
                    Toast.makeText(resetPassword.this , "Debes ingresar tu correo" ,Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public void resetPassword(){

        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(resetPassword.this , "Se envio un mensaje a su correo para restablecer su contraseña." ,Toast.LENGTH_LONG).show();
                }

                else {

                    Toast.makeText(resetPassword.this , "Error! , verifique su correo electrónico." ,Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });

    }

}
