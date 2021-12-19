package com.example.appwake.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.AddGroupMemberListAdapter;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.R;
import com.example.appwake.ResignLeadershipListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResignLeadershipActivity extends AppCompatActivity {

    private ResignLeadershipListAdapter adapter;
    private ListView userListView;
    private ArrayList<Korisnik> korisnici;
    private int idGrupe;
    private String nazivGrupe;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resign_leadership);

        userListView = findViewById(R.id.usersListViewResignLeadership);
        SearchView searchFilter = findViewById(R.id.searchViewResignLeadership);

        idGrupe = getIntent().getIntExtra("idGrupe", 0);
        nazivGrupe = getIntent().getStringExtra("nazivGrupe");
        position = getIntent().getIntExtra("position", 0);

        korisnici = new ArrayList<>();

        try {
            String url = "http://appwake.dacha204.com/api/jeclan/clanovi/" + idGrupe;
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

                            //ne treba prikazivati admina
                            if (korisnik.getId() == Korisnik.getInstance().getId())
                                continue;
                            korisnici.add(korisnik);
                        }

                        adapter = new ResignLeadershipListAdapter(ResignLeadershipActivity.this, R.layout.user_list_view_layout, korisnici);
                        adapter.setIdGrupe(idGrupe);
                        adapter.setNazivGrupe(nazivGrupe);
                        adapter.setArrayPosition(position);
                        userListView.setAdapter(adapter);
                    }
                    catch (Exception e){
                        Toast.makeText(ResignLeadershipActivity.this, "An error occurred! Message: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ResignLeadershipActivity.this, "Error reading database! Message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(ResignLeadershipActivity.this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(ResignLeadershipActivity.this, "Error! Message: " + ex.getMessage(), Toast.LENGTH_LONG).show();
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

        finish();

    }
}
