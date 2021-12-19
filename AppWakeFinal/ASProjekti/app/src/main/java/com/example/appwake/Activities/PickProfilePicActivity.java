package com.example.appwake.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.GlobalVariables;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.R;
import com.example.appwake.SaveSharedPreference;

import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PickProfilePicActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_profile_pic);

        final ImageView t1=findViewById(R.id.boy);
        final ImageView t2=findViewById(R.id.boy_1);
        final ImageView t3=findViewById(R.id.girl);
        final ImageView t4=findViewById(R.id.girl_1);
        final ImageView t5=findViewById(R.id.man_1);
        final ImageView t6=findViewById(R.id.man_4);
        final boolean[] proslaSekundaa = {false};
        final int color = Color.GRAY;
        final PorterDuff.Mode mode = PorterDuff.Mode.DST_OVER;

        intent = new Intent(PickProfilePicActivity.this, MainActivity.class);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GlobalVariables)getApplication()).setprofilePictureName("boy");

                Korisnik.getInstance().setSlika("boy");

                View overlay = (View) findViewById(R.id.overlay1);
                int opacity = 100; // from 0 to 255
                overlay.setBackgroundColor(opacity * 0x1000000);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.FILL;
                overlay.setLayoutParams(params);
                overlay.invalidate();


                UpdateSlika();



            }
        });


        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GlobalVariables)getApplication()).setprofilePictureName("boy_1");

                //setProfPicName("boy_1");
                Korisnik.getInstance().setSlika("boy_1");

                View overlay = (View) findViewById(R.id.overlay2);
                int opacity = 100; // from 0 to 255
                overlay.setBackgroundColor(opacity * 0x1000000);
                FrameLayout.LayoutParams params =
                        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.FILL;
                overlay.setLayoutParams(params);
                overlay.invalidate();

                UpdateSlika();
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GlobalVariables)getApplication()).setprofilePictureName("girl");
                //setProfPicName("girl");
                Korisnik.getInstance().setSlika("girl");
                View overlay = (View) findViewById(R.id.overlay3);
                int opacity = 100; // from 0 to 255
                overlay.setBackgroundColor(opacity * 0x1000000);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.FILL;
                overlay.setLayoutParams(params);
                overlay.invalidate();

                UpdateSlika();

            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GlobalVariables)getApplication()).setprofilePictureName("girl_1");
                Korisnik.getInstance().setSlika("girl_1");

                View overlay = (View) findViewById(R.id.overlay4);
                int opacity = 100; // from 0 to 255
                overlay.setBackgroundColor(opacity * 0x1000000);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.FILL;
                overlay.setLayoutParams(params);
                overlay.invalidate();

                UpdateSlika();
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GlobalVariables)getApplication()).setprofilePictureName("man_1");
                Korisnik.getInstance().setSlika("man_1");
                View overlay = (View) findViewById(R.id.overlay5);
                int opacity = 100;
                overlay.setBackgroundColor(opacity * 0x1000000);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.FILL;
                overlay.setLayoutParams(params);
                overlay.invalidate();

                UpdateSlika();
            }
        });

        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GlobalVariables)getApplication()).setprofilePictureName("man_4");
                //setProfPicName("man_4");
                Korisnik.getInstance().setSlika("man_4");
                View overlay = (View) findViewById(R.id.overlay6);
                int opacity = 100;
                overlay.setBackgroundColor(opacity * 0x1000000);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.FILL;
                overlay.setLayoutParams(params);
                overlay.invalidate();

                UpdateSlika();
            }
        });
    }



public void UpdateSlika(){


    try {
        String url = "http://appwake.dacha204.com/api/Korisnik/" + Korisnik.getInstance().getId();
        JSONObject obj = new JSONObject();
        obj.put("Ime", Korisnik.getInstance().getIme());
        obj.put("Prezime", Korisnik.getInstance().getPrezime());

        if (Korisnik.getInstance().getDatumRodjenja() == null){
            obj.put("DatumRodjenja", null);
        }
        else{
            Calendar calend = Calendar.getInstance();
            calend.setTime(Korisnik.getInstance().getDatumRodjenja());
            obj.put("DatumRodjenja", String.format("%d-%d-%d", calend.get(Calendar.YEAR),
                    calend.get(Calendar.MONTH) + 1, calend.get(Calendar.DAY_OF_MONTH)));
        }
        obj.put("Username", Korisnik.getInstance().getUsername());
        obj.put("Slika", Korisnik.getInstance().getSlika()); //bice nova slika
        obj.put("Status", Korisnik.getInstance().getStatus());

        JsonObjectRequest request1 = new JsonObjectRequest
                (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("response") == 0) {
                                Toast.makeText(PickProfilePicActivity.this, "Profile picture changed!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(PickProfilePicActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(PickProfilePicActivity.this, "An error occurred! Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PickProfilePicActivity.this, String.format("Error! Message: %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue1 = Volley.newRequestQueue(PickProfilePicActivity.this);
        queue1.add(request1);

    } catch (Exception ex) {
        Toast.makeText(PickProfilePicActivity.this, String.format("Error! Message: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
    }
    startActivity(intent);
    finish();
}

public void setProfPicName(final String slikaNova)
{
    final String email= SaveSharedPreference.getLoggedEmail(getApplicationContext());
    try {

        String urllog = "http://appwake.dacha204.com/api/Korisnik/Email/" + email +"/";
        final JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, urllog, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String email = response.getString("Email");

                            if (email.compareTo("null") != 0){



                                String slika=response.getString("Slika");

                                response.put("Slika",slikaNova);
                            }
                            else{
                                Toast.makeText(PickProfilePicActivity.this, "No user with given id! Register to continue!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(PickProfilePicActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PickProfilePicActivity.this, "No user with given id! Register to continue!", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(PickProfilePicActivity.this);
        queue.add(request);

    } catch (Exception e) {
        Toast.makeText(PickProfilePicActivity.this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();

    }


}
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }

}

