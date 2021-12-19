package com.example.appwake.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.DeleteKorisnikListAdapter;
import com.example.appwake.KorisnikListAdapter;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddUserActivity extends AppCompatActivity {

    private KorisnikListAdapter adapter;
    private ListView userListView;
    private ArrayList<Korisnik> korisnici;
    private int idGrupe;
    private int arrayPosition;
    private Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        ActionBar actionBar = (ActionBar) getSupportActionBar();//!!!
        actionBar.setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        userListView = findViewById(R.id.usersListView);
        SearchView searchFilter = findViewById(R.id.searchView);
        idGrupe = getIntent().getIntExtra("idGrupe", 0);
        arrayPosition = getIntent().getIntExtra("position", 0);

        finish = findViewById(R.id.btnFinishAddUser);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        korisnici = new ArrayList<>();

        try {
            String url = "http://appwake.dacha204.com/api/Korisnik/";
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    try {
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject responseObject = response.getJSONObject(i);

                            int id = responseObject.getInt("Id");
                            String ime = responseObject.getString("Ime");
                            String prezime = responseObject.getString("Prezime");
                            String username = responseObject.getString("Username");
                            Date datumRodjenja;
                            try {
                                datumRodjenja = new SimpleDateFormat("yyyy-MM-dd").parse(responseObject.getString("DatumRodjenja"));
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                datumRodjenja = null;
                            }
                            String email = responseObject.getString("Email");
                            String password = responseObject.getString("Password");
                            int jeAdministrator = responseObject.getInt("JeAdministrator");
                            int status = responseObject.getInt("Status");
                            String slika = responseObject.getString("Slika");

                            Korisnik korisnik = new Korisnik();
                            korisnik.setId(id);
                            korisnik.setIme(ime);
                            korisnik.setPrezime(prezime);
                            korisnik.setUsername(username);
                            korisnik.setDatumRodjenja(datumRodjenja);
                            korisnik.setEmail(email);
                            korisnik.setPassword(password);
                            korisnik.setJeAdmin(jeAdministrator);
                            korisnik.setStatus(status);
                            korisnik.setSlika(slika);

                            if (korisnik.getId() == Korisnik.getInstance().getId()) //konkretnog korisnika ne prikazujemo
                                continue; //preskacemo jedan loop
                            korisnici.add(korisnik);
                        }
                        adapter = new KorisnikListAdapter(AddUserActivity.this, R.layout.user_list_view_layout, korisnici);
                        adapter.setIdGrupe(idGrupe);
                        adapter.setArrayPosition(arrayPosition);
                        userListView.setAdapter(adapter);
                    }
                    catch (Exception e){
                        Toast.makeText(AddUserActivity.this, "An error occurred! Message:" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddUserActivity.this, "Error reading database! Message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(AddUserActivity.this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(AddUserActivity.this, "Error! Message: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        searchFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);    ////ne radi
                return true;
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
