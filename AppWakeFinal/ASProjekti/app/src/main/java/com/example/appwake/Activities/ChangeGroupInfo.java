package com.example.appwake.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.Models.Grupa;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.R;
import com.example.appwake.SettingsActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChangeGroupInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_group_info);
        Button btnApply = findViewById(R.id.applyinfochangebtn);
        final int position = getIntent().getIntExtra("position", 0);
        final Grupa g = Korisnik.getInstance().getGrupe().get(position);


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePicker timePicker=findViewById(R.id.timePicker);


                Calendar cal = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
                final Date d = new Date();

                final int year = cal.get(Calendar.YEAR);
                final int month = cal.get(Calendar.MONTH);
                final int day = cal.get(Calendar.DAY_OF_MONTH);
                final int hour, minute;


                if(Build.VERSION.SDK_INT>=23)
                {
                    hour=timePicker.getHour();
                    minute=timePicker.getMinute();

                }
                else
                {
                    hour=timePicker.getCurrentHour();
                    minute=timePicker.getCurrentMinute();
                }

                cal.set(year,month,day,hour,minute,0);
                if(d.getTime() > cal.getTimeInMillis())
                {
                    d.setTime(cal.getTimeInMillis() + 1000*60*60*24);
                }
                else
                    d.setTime(cal.getTimeInMillis());




                try {
                    String url = "http://appwake.dacha204.com/api/Grupa/" + g.getId();
                    JSONObject obj = new JSONObject();
                    obj.put("Id", g.getId());
                    obj.put("Naziv", g.getNaziv());
                    obj.put("DatumKreiranja", "");
                    obj.put("BrojClanova", g.getBrojClanova());
                    obj.put("ZeljenoVremeBudjenja", mdformat.format(d));
                    obj.put("IdAdmina", g.getIdAdmina());

                    JsonObjectRequest request1 = new JsonObjectRequest
                            (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getInt("response") == 0) {
                                            Toast.makeText(ChangeGroupInfo.this, "Wake up time set!", Toast.LENGTH_LONG).show();
                                            Korisnik.getInstance().getGrupe().get(position).setZeljenoVremeBudjenja(d);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(ChangeGroupInfo.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(ChangeGroupInfo.this, "An error occurred! Message:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ChangeGroupInfo.this, String.format("Error! Message: %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                }
                            });

                    RequestQueue queue1 = Volley.newRequestQueue(ChangeGroupInfo.this);
                    queue1.add(request1);

                } catch (Exception ex) {
                    Toast.makeText(ChangeGroupInfo.this, String.format("Error! Message: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), ManageGroupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
