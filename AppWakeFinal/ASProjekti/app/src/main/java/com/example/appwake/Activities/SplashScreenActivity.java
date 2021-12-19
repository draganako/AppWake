package com.example.appwake.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.Models.Grupa;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.R;
import com.example.appwake.SaveSharedPreference;
import com.example.appwake.SettingsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())){

            try {
                String url = "http://appwake.dacha204.com/api/Korisnik/"+ SaveSharedPreference.getLoggedId(getApplicationContext());
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = response.getInt("Id");
                            String ime = response.getString("Ime");
                            String prezime = response.getString("Prezime");
                            String username = response.getString("Username");
                            Date datumRodjenja;
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

                            new Korisnik(id, ime, prezime, username, datumRodjenja, email, password, jeAdministrator, slika, status);
                            SaveSharedPreference.setLoggedStatusbudan(getApplicationContext(), status);



                            try {
                                String url = "http://appwake.dacha204.com/api/JeClan/Grupe/" + Korisnik.getInstance().getId();
                                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        try {
                                            for (int i = 0; i < response.length(); i++) {

                                                JSONObject responseObject = response.getJSONObject(i);

                                                int id = responseObject.getInt("Id");
                                                int brojClanova = responseObject.getInt("BrojClanova");
                                                String naziv = responseObject.getString("Naziv");
                                                int idAdmina = responseObject.getInt("IdAdmina");
                                                Date datumKreiranja;
                                                Date zeljenoVremeBudjenja;

                                                try {
                                                    datumKreiranja = new SimpleDateFormat("yyyy-MM-dd").parse(responseObject.getString("DatumKreiranja"));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    datumKreiranja = null;
                                                }
                                                try {
                                                    zeljenoVremeBudjenja = new SimpleDateFormat("yyyy-MM-dd'T'H:mm:ss").parse(responseObject.getString("ZeljenoVremeBudjenja"));
                                                }
                                                catch (Exception e) {
                                                    e.printStackTrace();
                                                    zeljenoVremeBudjenja = null;
                                                }

                                                Grupa grupa = new Grupa();
                                                grupa.setId(id);
                                                grupa.setNaziv(naziv);
                                                grupa.setBrojClanova(brojClanova);
                                                grupa.setDatumKreiranja(datumKreiranja);
                                                grupa.setZeljenoVremeBudjenja(zeljenoVremeBudjenja);
                                                grupa.setIdAdmina(idAdmina);

                                                Korisnik.getInstance().getGrupe().add(grupa);
                                            }


                                            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                            startActivity(intent);

                                            finish();

                                        } catch (Exception e) {
                                            Toast.makeText(SplashScreenActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                                        }
                                        Toast.makeText(SplashScreenActivity.this, response.toString(), Toast.LENGTH_LONG);

                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Error reading database! " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                RequestQueue queue = Volley.newRequestQueue(SplashScreenActivity.this);
                                queue.add(request);
                            } catch (Exception ex) {
                                Toast.makeText(SplashScreenActivity.this, "Error! Message: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "An error occurred! Message: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error reading database!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(SplashScreenActivity.this);
                queue.add(request);
            } catch (Exception ex) {
                Toast.makeText(SplashScreenActivity.this, "Error: " + ex.toString(), Toast.LENGTH_LONG).show();
            }


        }
        else{
            new Handler().postDelayed(new Runnable(){

                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);

                    finish();
                }
            }, 3000);
        }





    }
}
