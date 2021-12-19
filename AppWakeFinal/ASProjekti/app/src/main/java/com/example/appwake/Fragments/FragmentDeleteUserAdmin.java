package com.example.appwake.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.appwake.Activities.MainActivity;
import com.example.appwake.DeleteKorisnikListAdapter;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.R;
import com.example.appwake.SettingsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentDeleteUserAdmin extends Fragment {

    private DeleteKorisnikListAdapter korisnikListAdapter;
    private ListView korisniciListView;
    private ArrayList<Korisnik> korisnici;
    Button finish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_user_admin_layout, container, false);

        korisniciListView = (ListView)view.findViewById(R.id.listViewDeleteUserAdmin);
        SearchView korisniciSearchFilter = (SearchView)view.findViewById(R.id.searchViewDeleteUserAdmin);

        finish = view.findViewById(R.id.btnFinishDeleteUserAdmin);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
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

                            if (korisnik.getId() == Korisnik.getInstance().getId())
                                continue;
                            korisnici.add(korisnik);
                        }

                        korisnikListAdapter = new DeleteKorisnikListAdapter(getContext(), R.layout.administrator_list_view_adapter, korisnici);
                        korisniciListView.setAdapter(korisnikListAdapter);
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(), "An error occurred! Message: " + e.getMessage(), Toast.LENGTH_LONG);
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error reading database! Message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Error! Message: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        korisniciSearchFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                korisnikListAdapter.getFilter().filter(newText);
                return true;
            }
        });


        return  view;
    }

}
