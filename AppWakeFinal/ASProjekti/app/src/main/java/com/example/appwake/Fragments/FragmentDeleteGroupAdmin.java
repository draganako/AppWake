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
import com.example.appwake.Activities.DeleteGroupMemberActivity;
import com.example.appwake.Activities.MainActivity;
import com.example.appwake.DeleteGrupaListAdapter;
import com.example.appwake.Models.Grupa;
import com.example.appwake.R;
import com.example.appwake.SettingsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.security.AccessController.getContext;

public class FragmentDeleteGroupAdmin extends Fragment {

    private DeleteGrupaListAdapter groupListAdapter;
    private ArrayList<Grupa> grupe;
    private ListView grupeListView;
    private Button finish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_group_admin_layout, container, false);

        grupeListView = view.findViewById(R.id.listViewDeleteGroupAdmin);
        SearchView grupeSearchFilter = view.findViewById(R.id.searchViewDeleteGroupAdmin);

        finish = view.findViewById(R.id.btnFinishDeleteGroupAdmin);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        grupe = new ArrayList<>();

        try {
            String url = "http://appwake.dacha204.com/api/Grupa";
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
                                zeljenoVremeBudjenja = new SimpleDateFormat("yyyy-MM-dd").parse(responseObject.getString("ZeljenoVremeBudjenja"));
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

                            grupe.add(grupa);
                        }
                        groupListAdapter = new DeleteGrupaListAdapter(getContext(), R.layout.administrator_list_view_adapter, grupe);
                        grupeListView.setAdapter(groupListAdapter);

                    } catch (Exception e){
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

        grupeSearchFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                groupListAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return  view;
    }

}
