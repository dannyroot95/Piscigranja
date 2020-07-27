package com.example.piscigranja.Servicios;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.piscigranja.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class serviceNotificacion extends Service {
    MyTask mitarea;
    private DatabaseReference mDatabaseService, mDatabaseSerieService;
    private FirebaseAuth mAuthService;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    private final static String CHANNEL_ID2 = "NOTIFICACION2";
    private final static int NOTIFICACION_ID2 = 1;
    private final static String CHANNEL_ID3 = "NOTIFICACION3";
    private final static int NOTIFICACION_ID3 = 2;
    private final static String CHANNEL_ID4 = "NOTIFICACION4";
    private final static int NOTIFICACION_ID4 = 3;

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabaseService = FirebaseDatabase.getInstance().getReference();
        mDatabaseSerieService = FirebaseDatabase.getInstance().getReference();
        mAuthService = FirebaseAuth.getInstance();
        /*Toast.makeText(this,"Servicio creado",Toast.LENGTH_LONG).show();*/
        mitarea = new MyTask();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*Toast.makeText(this, "Servicio", Toast.LENGTH_LONG).show();*/
        monitoreo();
        mitarea.execute();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*Toast.makeText(this,"Servicio detenido",Toast.LENGTH_LONG).show();*/
        mitarea.cancel(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void monitoreo(){
        String NOTIFICATION_CHANNEL_ID = "com.example.piscigranja";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Monitoreo activo")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(101, notification);

    }

    public class MyTask extends AsyncTask<String,String,String>
    {

        private boolean aux;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            aux = true;
        }

        private void createNotificationChannel(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                CharSequence name = "Noticacion";
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        private void createNotificationChannel2(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                CharSequence name = "Noticacion2";
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID2, name, NotificationManager.IMPORTANCE_HIGH);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        private void createNotificationChannel3(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                CharSequence name = "Noticacion3";
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID3, name, NotificationManager.IMPORTANCE_HIGH);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        private void createNotificationChannel4(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                CharSequence name = "Noticacion3";
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID4, name, NotificationManager.IMPORTANCE_HIGH);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        private void createNotification(){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
            builder.setSmallIcon(R.mipmap.ic_alerta_temp);
            builder.setContentTitle("Alerta de temperatura!");
            builder.setContentText("Revise la piscigranja");
            builder.setColor(Color.RED);
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setLights(Color.RED, 1000, 1000);
            builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
            builder.setDefaults(Notification.DEFAULT_SOUND);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
        }

        private void createNotification2(){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID2);
            builder.setSmallIcon(R.mipmap.ic_alerta_ph);
            builder.setContentTitle("Alerta de Ph !");
            builder.setContentText("Revise la piscigranja");
            builder.setColor(Color.MAGENTA);
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setLights(Color.MAGENTA, 1000, 1000);
            builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
            builder.setDefaults(Notification.DEFAULT_SOUND);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.notify(NOTIFICACION_ID2, builder.build());
        }

        private void createNotification3(){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID3);
            builder.setSmallIcon(R.mipmap.ic_alerta_turb);
            builder.setContentTitle("Alerta de transparencia del agua!");
            builder.setContentText("Revise la piscigranja");
            builder.setColor(Color.BLACK);
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setLights(Color.BLACK, 1000, 1000);
            builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
            builder.setDefaults(Notification.DEFAULT_SOUND);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.notify(NOTIFICACION_ID3, builder.build());
        }

        private void createNotification4(){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID4);
            builder.setSmallIcon(R.mipmap.ic_alerta_oxy);
            builder.setContentTitle("Alerta de oxigeno disuelto !");
            builder.setContentText("Revise la piscigranja");
            builder.setColor(Color.BLUE);
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setLights(Color.BLUE, 1000, 1000);
            builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
            builder.setDefaults(Notification.DEFAULT_SOUND);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.notify(NOTIFICACION_ID4, builder.build());
        }






        @Override
        protected void onProgressUpdate(String... values) {
            String id = mAuthService.getCurrentUser().getUid();
            mDatabaseSerieService.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        String serie_data_service = dataSnapshot.child("Dispositivo").getValue().toString();
                        mDatabaseService.child("dispositivos").child(serie_data_service).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.exists()){
                                    String temp_notify = dataSnapshot.child("temperatura").getValue().toString();
                                    String ph_notify = dataSnapshot.child("ph").getValue().toString();
                                    String turb_notify = dataSnapshot.child("ntu").getValue().toString();
                                    String oxy_notify = dataSnapshot.child("oxigeno").getValue().toString();

                                    Float f_service_temp= Float.parseFloat(temp_notify);
                                    Float f_service_ph= Float.parseFloat(ph_notify);
                                    Float f_service_turb= Float.parseFloat(turb_notify);
                                    Float f_service_oxy= Float.parseFloat(oxy_notify);

                                    if (f_service_temp>32 || f_service_temp<15)
                                    {
                                        createNotificationChannel();
                                        createNotification();

                                    }

                                    if (f_service_ph<6.5 || f_service_ph>8.5)
                                    {
                                        createNotificationChannel2();
                                        createNotification2();
                                    }

                                    if (f_service_turb<600.01 || f_service_turb>2900.99)
                                    {
                                        createNotificationChannel3();
                                        createNotification3();
                                    }

                                    if (f_service_oxy<1.0 || f_service_oxy>8.5)
                                    {
                                        createNotificationChannel4();
                                        createNotification4();
                                    }



                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            /*Toast.makeText(getApplicationContext(),"Notificacion",Toast.LENGTH_SHORT).show();*/
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            aux = false;
        }

        @Override
        protected String doInBackground(String... strings) {

            while (aux){
                try {
                    publishProgress();
                    Thread.sleep(50000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

}
