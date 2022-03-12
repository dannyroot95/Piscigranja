package com.example.piscigranja;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.PopupMenu;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Succesfull extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private Button  btnDatafood,btnDataMap,btnaddSerie;
    private CircleImageView foto;
    private FirebaseAuth mAuth , exitAuth;
    private DatabaseReference mDatabase;
    private TextView nombres_txt,email_txt,serie_txt;
    private StorageReference mStorage;
    private static final int GALLERY = 1;
    private ProgressDialog mDialog,mDialogActualizeData;
    private LottieAnimationView btnDataTemp,btnDataoxy,btnDataPh,btnDataTurb;
    String txtActualizeName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succesfull);


        mDialogActualizeData = new ProgressDialog(this,R.style.MyAlertDialogData);
        mDialogActualizeData.setCancelable(false);
        mDialogActualizeData.show();
        mDialogActualizeData.setContentView(R.layout.dialog_data);
        mDialogActualizeData.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);


        mStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnDataTemp = (LottieAnimationView) findViewById(R.id.btnDataTemp);
        btnDatafood = (Button) findViewById(R.id.btnDataFood);
        btnDataMap = (Button) findViewById(R.id.btnDataMap);
        btnDataPh = (LottieAnimationView) findViewById(R.id.btnDataPh);
        btnDataTurb = (LottieAnimationView) findViewById(R.id.btnDataTurb);
        btnDataoxy = (LottieAnimationView) findViewById(R.id.btnDataOxy);
        nombres_txt = (TextView) findViewById(R.id.txtNombres);
        email_txt = (TextView) findViewById(R.id.txtEmail);
        serie_txt = (TextView)findViewById(R.id.txtSerie);
        btnaddSerie = (Button) findViewById(R.id.btnSerieData);
        foto = (CircleImageView) findViewById(R.id.fotodefault);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.gota);


        btnDatafood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(Succesfull.this, getDataFood.class));
            }
        });

        btnDataTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(Succesfull.this, getDataTemp.class));
                /* animacion hacia arriba-> overridePendingTransition(R.anim.slide_up,R.anim.no_change);*/
            }
        });


        btnDataPh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(Succesfull.this, getDataPh.class));
            }
        });

        btnDataTurb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(Succesfull.this, getDataTurbidez.class));
            }
        });

        btnDataoxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(Succesfull.this, getDataOxy.class));
            }
        });


        btnaddSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(Succesfull.this,numSerieIot.class));
            }
        });

        btnDataMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(Succesfull.this,MapsIot.class));
            }
        });


        getDataUser();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY && resultCode == RESULT_OK){
            mDialog.setMessage("Actualizando foto de perfil");
            mDialog.show();
            Uri uri = data.getData();
            final String id = mAuth.getCurrentUser().getUid();
            final StorageReference filepath = mStorage.child("fotos").child(id).child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String id = mAuth.getCurrentUser().getUid();
                            DatabaseReference imagestore = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(id);
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("foto" , String.valueOf(uri));
                            imagestore.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mDialog.dismiss();
                                    Toast.makeText(Succesfull.this,"Foto de perfil actualizada",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }
            });
        }
    }


    public void show_popup(View view){

        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

    }

    public void show_graph(View view){

        Context wrapper = new ContextThemeWrapper(this, R.style.MyPopupOtherStyle);
        PopupMenu popupMenu = new PopupMenu(wrapper,view);

        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        popupMenu.getMenuInflater().inflate(R.menu.graficos, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.gota);
        mediaPlayer.start();

        switch (item.getItemId()){
            case R.id.item1:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY);
                return true;

            case R.id.item2:

                final AlertDialog.Builder mDialogName = new AlertDialog.Builder(Succesfull.this);
                mDialogName.setTitle("Actualiza tu nombre");

                final EditText actualizar = new EditText(Succesfull.this);
                actualizar.setInputType(InputType.TYPE_CLASS_TEXT);
                mDialogName.setView(actualizar);
                mDialogName.setIcon(R.mipmap.ic_launcher);
                mDialogName.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        txtActualizeName = actualizar.getText().toString();
                        if(!txtActualizeName.isEmpty()){
                            Map<String,Object> updateName = new HashMap<>();
                            updateName.put("nombres",txtActualizeName);
                            String id = mAuth.getCurrentUser().getUid();
                            mDatabase.child("Usuarios").child(id).updateChildren(updateName);
                            Toast.makeText(Succesfull.this , "Nombre actualizado" ,Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(Succesfull.this , "Ingrese su nombre" ,Toast.LENGTH_LONG).show();

                        }


                    }
                });

                mDialogName.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                mDialogName.show();

                return true;
            case R.id.item3:
                exitAuth.signOut();
                stopService(new Intent(Succesfull.this,serviceNotificacion.class));
                startActivity(new Intent(Succesfull.this,login.class));
                finish();
                return true;
                /* -----------------------SEGUNDO MENU------------------------------- */
            case R.id.menutemp:
                startActivity(new Intent(Succesfull.this,graficoTemp.class));
                return true;
            case R.id.menuph:
                startActivity(new Intent(Succesfull.this,graficoPh.class));
                return true;
            case R.id.menuturb:
                startActivity(new Intent(Succesfull.this,graficoTurbidez.class));
                return true;
            case R.id.menuoxy:
                startActivity(new Intent(Succesfull.this,graficoOxy.class));
                return true;
            default:
                return false;

        }

    }

    private void getDataUser(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // String creado debe ser al de la base de datos
                if(dataSnapshot.exists()){
                    String nombres = dataSnapshot.child("nombres").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String serie = dataSnapshot.child("Dispositivo").getValue().toString();
                    String setFoto = dataSnapshot.child("foto").getValue().toString();
                    Glide.with(Succesfull.this).load(setFoto).into(foto);
                    nombres_txt.setText(nombres);
                    email_txt.setText(email);
                    serie_txt.setText(serie);
                    mDialogActualizeData.dismiss();
                }
                else {
                    mDialog.dismiss();
                    Toast.makeText(Succesfull.this,"Error de conexión",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDialog.dismiss();
                Toast.makeText(Succesfull.this,"Error",Toast.LENGTH_LONG).show();
            }
        });
    }

}
