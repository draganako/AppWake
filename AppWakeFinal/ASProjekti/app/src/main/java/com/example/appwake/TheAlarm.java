package com.example.appwake;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.Models.Korisnik;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TheAlarm extends BroadcastReceiver{

    //MediaPlayer mediaPlayer;
    private  static TheAlarm instance;
    private  static boolean snooze;
    private  static int numSnooze= 1;
    private  static int status =0;
    public static TheAlarm getInstance()
    {

        if(instance==null)
            instance=new TheAlarm();
        return instance;
    }
    @Override
    public void onReceive(final Context context, Intent intent)
    {




        status = SaveSharedPreference.getLoggedStatusbudan(context);
        final NotificationCompat.Builder builder;



        if(numSnooze < 20 && status == 0) //
        {


            final Calendar mcurrentDate = Calendar.getInstance();
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intee=new Intent(context,TheAlarm.class);
            PendingIntent pendingIntent2=PendingIntent.getBroadcast(context,0,intee,0);
            alarmManager.set(AlarmManager.RTC,mcurrentDate.getTimeInMillis() + 1000*60,pendingIntent2);



            try {
                String url = "http://appwake.dacha204.com/api/Korisnik/"+ SaveSharedPreference.getLoggedId(context);
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = response.getInt("Id");
                            String ime = response.getString("Ime");
                            String prezime = response.getString("Prezime");
                            String username = response.getString("Username");
                            Date datumRodjenja;// = new SimpleDateFormat("yyyy-MM-dd").parse(response.getString("DatumRodjenja"));
                            try {
                                datumRodjenja = new SimpleDateFormat("yyyy-MM-dd").parse(response.getString("DatumRodjenja"));
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                datumRodjenja = null;
                            }
                            String email = response.getString("Email");
                            String password = response.getString("Password");
                            int jeAdministrator = response.getInt("JeAdministrator");
                            int status = response.getInt("Status");
                            String slika = response.getString("Slika");


                            SaveSharedPreference.setLoggedStatusbudan(context, status);
                            //Toast.makeText(context, String.format("Hello from korisnik " + status), Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(context, "An error occured! Message: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error reading database! " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);
            } catch (Exception ex) {
                Toast.makeText(context, "An error occured! Message: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }


        }
        else if(status  == 3 && numSnooze < 20)  //ako mu je neko poslao nitifikaciju
        {

            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(context, notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }

            final Calendar mcurrentDate = Calendar.getInstance();
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intee=new Intent(context,TheAlarm.class);
            PendingIntent pendingIntent2=PendingIntent.getBroadcast(context,0,intee,0);
            alarmManager.set(AlarmManager.RTC,mcurrentDate.getTimeInMillis() + 1000*60*3,pendingIntent2);
            //Toast.makeText(context, "snoozed for 8s" , Toast.LENGTH_LONG).show();

            builder = new NotificationCompat.Builder(context, "131318")
                    .setSmallIcon(R.drawable.appwakelogo)
                    .setContentTitle("WAKE UP!")
                    .setContentText("Your group needs you to wake up!" )


                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    .setAutoCancel(true);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(52, builder.build());
        }
        else //if(status == 2 || status == 1)
        {

            AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent1=new Intent(context,TheAlarm.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,intent1,0);
            alarmManager.cancel(pendingIntent);



            snooze = true;
            numSnooze = 1;
            status = 0;
        }



        numSnooze++;


    }
    public void Stop()
    {
        //mediaPlayer.stop();


        status = 2;
        numSnooze =1 ;


    }


    private void setSnooze(long timeInMillis)
    {

    }



}


