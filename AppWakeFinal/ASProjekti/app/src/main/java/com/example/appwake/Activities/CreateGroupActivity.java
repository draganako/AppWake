package com.example.appwake.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.Models.Grupa;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.Models.StatistikaGrupa;
import com.example.appwake.R;
import com.example.appwake.SettingsActivity;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class CreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        ActionBar actionBar = (ActionBar) getSupportActionBar();//!!!
        actionBar.setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.btnNext){
                    EditText txtNaziv = findViewById(R.id.editTextGroupName);
                    if (txtNaziv.getText().toString().equals("")) {
                        txtNaziv.setHintTextColor(Color.RED);
                        Toast.makeText(getApplicationContext(), "Group name required!", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        final Grupa g = new Grupa();

                        String naziv = txtNaziv.getText().toString();
                        final int idAdmina = Korisnik.getInstance().getId();
                        final Calendar cal = Calendar.getInstance();
                        Date datumKreiranja = cal.getTime();

                        g.setNaziv(naziv);
                        g.setIdAdmina(idAdmina);
                        g.setBrojClanova(0);
                        g.setZeljenoVremeBudjenja(null);
                        g.setDatumKreiranja(datumKreiranja);
                        g.setZaPrikaz(null);

                        try {
                            String url = "http://appwake.dacha204.com/api/Grupa";
                            JSONObject obj = new JSONObject();
                            obj.put("Naziv", naziv);
                            obj.put("DatumKreiranja", String.format("%d-%d-%d", cal.get(Calendar.YEAR),
                                    cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));
                            obj.put("BrojClanova", 0);
                            obj.put("ZeljenoVremeBudjenja", null);
                            obj.put("IdAdmina", idAdmina);

                            JsonObjectRequest request1 = new JsonObjectRequest
                                    (Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                final int result = response.getInt("response");
                                                if (result > 0) {
                                                    Toast.makeText(CreateGroupActivity.this, "Group created!", Toast.LENGTH_LONG).show();
                                                    g.setId(result);


                                                    try {
                                                        String url = "http://appwake.dacha204.com/api/JeClan";
                                                        JSONObject obj = new JSONObject();

                                                        obj.put("DatumUclanjenja", String.format("%d-%d-%d", cal.get(Calendar.YEAR),
                                                                cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));
                                                        obj.put("DatumNapustanja", null);
                                                        obj.put("RealnoVremeBudjenja", null);
                                                        obj.put("IdKorisnika", idAdmina);
                                                        obj.put("IdGrupe", result);

                                                        JsonObjectRequest request1 = new JsonObjectRequest
                                                                (Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {

                                                                    @Override
                                                                    public void onResponse(JSONObject response) {
                                                                        try {
                                                                            int result2 = response.getInt("response");
                                                                            if (result2 > 0) {
                                                                                //Toast.makeText(CreateGroupActivity.this, "Admin dodat", Toast.LENGTH_LONG).show();
                                                                                //Toast.makeText(CreateGroupActivity.this, "Id za je clan je " + result2, Toast.LENGTH_LONG).show();

                                                                                Korisnik.getInstance().getGrupe().add(g); //korisnikovoj listi se pridodaje nova grupa

                                                                                g.setBrojClanova(1);

                                                                                Intent intent = new Intent(CreateGroupActivity.this, AddUserActivity.class);
                                                                                intent.putExtra("idGrupe", result); //prosledjujemo idgrupe
                                                                                intent.putExtra("position", Korisnik.getInstance().getGrupe().indexOf(g));

                                                                                startActivity(intent);
                                                                                finish();


                                                                            } else {
                                                                                Toast.makeText(CreateGroupActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                                                                            }

                                                                        } catch (Exception e) {
                                                                            Toast.makeText(CreateGroupActivity.this, "An error occurred! Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                }, new Response.ErrorListener() {

                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {
                                                                        Toast.makeText(CreateGroupActivity.this, String.format("An error occurred! Message: %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });

                                                        RequestQueue queue1 = Volley.newRequestQueue(CreateGroupActivity.this);
                                                        queue1.add(request1);

                                                    } catch (Exception ex) {
                                                        Toast.makeText(CreateGroupActivity.this, String.format("An error occurred! Message: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                else{
                                                    Toast.makeText(CreateGroupActivity.this, "Group could not be created!", Toast.LENGTH_SHORT).show();
                                                }

                                            } catch (Exception e) {
                                                Toast.makeText(CreateGroupActivity.this, "An error occurred! Message:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(CreateGroupActivity.this, String.format("An error occurred! Message: %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            RequestQueue queue1 = Volley.newRequestQueue(CreateGroupActivity.this);
                            queue1.add(request1);

                        } catch (Exception ex) {
                            Toast.makeText(CreateGroupActivity.this, String.format("An error occurred! Message: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });

    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }
}
