package com.example.appwake.Fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.GrupaListAdapter;
import com.example.appwake.ClanGrupeListAdapter;
import com.example.appwake.Models.Grupa;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.R;
import com.example.appwake.SettingsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link members.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link members#newInstance} factory method to
 * create an instance of this fragment.
 */
public class members extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn1;
    ClanGrupeListAdapter adapter;
    ArrayList<Korisnik> korisnici = new ArrayList<>();
    ListView groupListView;


    private OnFragmentInteractionListener mListener;

    public members() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment members.
     */
    // TODO: Rename and change types and number of parameters
    public static members newInstance(String param1, String param2) {
        members fragment = new members();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_members, container, false);




        groupListView = view.findViewById(R.id.listviewMembers);


        final int postitionIzActivitija = getArguments().getInt("position");

        Grupa g = Korisnik.getInstance().getGrupe().get(postitionIzActivitija); //ovo vec imamo ucitano

        try {
            String url = "http://appwake.dacha204.com/api/JeClan/Clanovi/" + g.getId();
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

                            korisnici.add(korisnik);


                        }


                        adapter = new ClanGrupeListAdapter(getContext(), R.layout.user_list_view_layout, korisnici);
                        groupListView.setAdapter(adapter);

                    }
                    catch (Exception e){
                        Toast.makeText(getContext(), "An error occurred! Message: " + e.getMessage(), Toast.LENGTH_LONG);
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error reading database!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Error: " + ex.toString(), Toast.LENGTH_LONG).show();
        }






        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
