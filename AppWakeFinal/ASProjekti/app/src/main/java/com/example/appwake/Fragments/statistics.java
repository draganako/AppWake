package com.example.appwake.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.Activities.AddUserActivity;
import com.example.appwake.Activities.MainActivity;
import com.example.appwake.Activities.ManageGroupActivity;
import com.example.appwake.Activities.ResignLeadershipActivity;
import com.example.appwake.ClanGrupeListAdapter;
import com.example.appwake.Models.Grupa;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.Models.StatistikaGrupa;
import com.example.appwake.R;
import com.example.appwake.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link statistics.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link statistics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class statistics extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btn1;
    Grupa g;

    TextView txtViewProsek;
    TextView txtViewNajcescePrvi;
    TextView txtViewKolikoPutaPrvi;
    TextView txtViewNajviseClanova;
    TextView txtViewKada;

    private OnFragmentInteractionListener mListener;

    public statistics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment statistics.
     */
    // TODO: Rename and change types and number of parameters
    public static statistics newInstance(String param1, String param2) {
        statistics fragment = new statistics();
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
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);


        final int postitionIzActivitija = getArguments().getInt("position");

        txtViewProsek = view.findViewById(R.id.txtProsekProbudjenih);
        txtViewNajcescePrvi = view.findViewById(R.id.txtNajcescePrvi);
        txtViewKolikoPutaPrvi = view.findViewById(R.id.txtBrojPrvih);
        txtViewNajviseClanova = view.findViewById(R.id.txtNajviseClanova);
        txtViewKada = view.findViewById(R.id.txtNajviseClanovaDatum);
        btn1 =  view.findViewById(R.id.btnLeaveGroup);

        g = (Grupa) Korisnik.getInstance().getGrupe().toArray()[postitionIzActivitija];


        btn1.setOnClickListener(new View.OnClickListener() {





            @Override
            public void onClick(View view) {


                if (g.getIdAdmina() == Korisnik.getInstance().getId()) {
                    if(g.getBrojClanova()>1)
                    {
                        AlertDialog dialog = new AlertDialog.Builder(getContext())
                                .setTitle("You are still administrating this group")
                                .setMessage("In order to leave the group, you must resign your leadership.\nChoose the new leader now?")
                                .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getContext(), ResignLeadershipActivity.class);
                                        intent.putExtra("idGrupe", g.getId());
                                        intent.putExtra("position", postitionIzActivitija);
                                        intent.putExtra("nazivGrupe", g.getNaziv());
                                        startActivity(intent);

                                    }
                                }).setNegativeButton("Cancel", null).create();
                        dialog.show();
                    }
                    else
                    {
                        AlertDialog dialog = new AlertDialog.Builder(getContext())
                                .setTitle("This group has no member to resign your leadership to.")
                                .setMessage("Do you want to delete this group?")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        try {
                                            String url = "http://appwake.dacha204.com/api/Grupa/" + g.getId();
                                            JSONObject obj = new JSONObject();

                                            JsonObjectRequest request1 = new JsonObjectRequest
                                                    (Request.Method.DELETE, url, obj, new Response.Listener<JSONObject>() {

                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            try {
                                                                if (response.getInt("response") == 0) {
                                                                    Toast.makeText(getContext(), "Group deleted", Toast.LENGTH_LONG).show();
                                                                    Korisnik.getInstance().getGrupe().remove(g); //uklanjamo grupu i iz korisnikove liste

                                                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                                                    startActivity(intent);

                                                                } else {
                                                                    Toast.makeText(getContext(), "Error deleting the group!", Toast.LENGTH_SHORT).show();
                                                                }

                                                            } catch (Exception e) {
                                                                Toast.makeText(getContext(), "An error occurred! Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }, new Response.ErrorListener() {

                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(getContext(), String.format("Error! Message: %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                            RequestQueue queue1 = Volley.newRequestQueue(getContext());
                                            queue1.add(request1);

                                        } catch (Exception ex) {
                                            Toast.makeText(getContext(), String.format("Error! Message: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).setNegativeButton("Cancel", null).create();
                        dialog.show();
                    }
                } else {

                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Leave group " + g.getNaziv() + "?")
                            .setMessage("Are you sure you want to leave group " + g.getNaziv() + "?")
                            //.setView(parent)
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   // Toast.makeText(getContext(), "About to leave the group", Toast.LENGTH_SHORT).show();

                                    final int idUserToRemove = Korisnik.getInstance().getId(); //sam korisnik kaze da zeli da napusti grupu

                                    try {
                                        String url = "http://appwake.dacha204.com/api/JeClan/" + idUserToRemove + "/" + g.getId();
                                        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    int idToUpdate = response.getInt("response");
                                                    if (idToUpdate > 0) {
                                                        //Toast.makeText(getContext(), "Found table member! " + idToUpdate, Toast.LENGTH_SHORT).show();

                                                        try {
                                                            String url = "http://appwake.dacha204.com/api/JeClan/" + idToUpdate;
                                                            JSONObject obj = new JSONObject();
                                                            obj.put("Id", idToUpdate);

                                                            Calendar cal = Calendar.getInstance();

                                                            obj.put("DatumNapustanja", String.format("%d-%d-%d", cal.get(Calendar.YEAR),
                                                                    cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));

                                                            JsonObjectRequest request1 = new JsonObjectRequest
                                                                    (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                                                                        @Override
                                                                        public void onResponse(JSONObject response) {
                                                                            try {
                                                                                if (response.getInt("response") == 0) {
                                                                                    Toast.makeText(getContext(), "You left the group", Toast.LENGTH_SHORT).show();
                                                                                    //intent to go back to main activity!

                                                                                    Korisnik.getInstance().getGrupe().remove(g);

                                                                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                                                                    startActivity(intent);

                                                                                } else {
                                                                                    Toast.makeText(getContext(), "An error occured!", Toast.LENGTH_SHORT).show();
                                                                                }

                                                                            } catch (Exception e) {
                                                                                Toast.makeText(getContext(), "Error! Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    }, new Response.ErrorListener() {

                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                            Toast.makeText(getContext(), String.format("Error! Message: %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });

                                                            RequestQueue queue1 = Volley.newRequestQueue(getContext());
                                                            queue1.add(request1);

                                                        } catch (Exception ex) {
                                                            Toast.makeText(getContext(), String.format("Error! Message: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                                                        }

                                                    } else {
                                                        Toast.makeText(getContext(), "An error occured!", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (Exception ex) {
                                                    Toast.makeText(getContext(), "An error occurred! Message: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getContext(), "An error occurred while reading database! Message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        RequestQueue queue = Volley.newRequestQueue(getContext());
                                        queue.add(request);
                                    } catch (Exception ex) {
                                        Toast.makeText(getContext(), "An error occurred! Message: " + ex.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .create();
                    dialog.show();
                }

            }
        });

        btn1 = view.findViewById(R.id.btnAdminOptions);

        btn1.setOnClickListener(new View.OnClickListener() {





            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManageGroupActivity.class);

                intent.putExtra("position", postitionIzActivitija);

                startActivity(intent);
            }
        });


        try {
            String url = "http://appwake.dacha204.com/api/Statistika/" + g.getId(); //ili g.getId()
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        double prosekProbudjeni = response.getDouble("prosekProbudjeni");
                        int ukupnoProbudjeni = response.getInt("ukupnoProbudjeni");
                        int najcescePrviId = response.getInt("najcescePrviId");
                        String najcescePrviUsername = response.getString("najcescePrviUsername");
                        int brojPrvihPrvi = response.getInt("brojPrvihPrvi");
                        int najviseClanova = response.getInt("najviseClanova");
                        Date najviseClanovaDatum;
                        try {
                            najviseClanovaDatum = new SimpleDateFormat("yyyy-MM-dd").parse(response.getString("najviseClanovaDatum"));
                        } catch (Exception e) {
                            e.printStackTrace();
                            najviseClanovaDatum = null;
                        }

                        StatistikaGrupa sg = new StatistikaGrupa(prosekProbudjeni, ukupnoProbudjeni, najcescePrviId, najcescePrviUsername,
                                brojPrvihPrvi, najviseClanova, najviseClanovaDatum);

                        g.setZaPrikaz(sg);


                        PrikaziStatistiku();

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "An error occurred! Message: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error reading database! Message:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(request);
        } catch (Exception ex) {
                    Toast.makeText(getContext(), "An error occurred! Message: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        int idA = SaveSharedPreference.getLoggedId(getContext());
        if(g.getIdAdmina() != idA)
        {
            btn1.setVisibility(View.GONE);
        }
        else
        {
            btn1.setVisibility(View.VISIBLE);
        }

        TextView nazivGrupe =  view.findViewById(R.id.txtNazivGrupe);
        nazivGrupe.setText("Group " + g.getNaziv());

        TextView idGrupe = view.findViewById(R.id.txtIdGrupe);
        idGrupe.setText("Id: " + g.getId());

        TextView brojClanova =  view.findViewById(R.id.txtBrojClanova);
        brojClanova.setText("Members: " + g.getBrojClanova());

        TextView zeljenoVremeBudjenja =  view.findViewById(R.id.txtZeljenoVremeBudjenja);
        if (g.getZeljenoVremeBudjenja() == null)
            zeljenoVremeBudjenja.setText("Next time to wake up: not set");
        else
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(g.getZeljenoVremeBudjenja());
            String vreme = String.format("%d:%d:%d",  cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
            zeljenoVremeBudjenja.setText("Next time to wake up: " + vreme);
        }

        return view;
    }

    public void PrikaziStatistiku(){
        StatistikaGrupa zaPrikaz = g.getZaPrikaz();
        if (zaPrikaz.getNajviseClanovaDatum() != null)
        {
            txtViewProsek.setText("The average of people who woke up: " + String.valueOf(zaPrikaz.getProsekProbudjeni()));
            txtViewNajcescePrvi.setText(String.format("The person who got up first the most: %s [ID: %d]", zaPrikaz.getNajcescePrviUsername(), zaPrikaz.getNajcescePrviId())); //ne treba samo id da vraca, vec i ime i prezime
            txtViewKolikoPutaPrvi.setText("The number of times they were first: " + String.valueOf(zaPrikaz.getBrojPrvihPrvi()));
            txtViewNajviseClanova.setText("The biggest number of members: " + String.valueOf(zaPrikaz.getNajviseClanova()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(zaPrikaz.getNajviseClanovaDatum());
            String prikaz = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +cal.get(Calendar.YEAR);
            txtViewKada.setText("This number of members was seen on: " + prikaz);
        }
        else
        {
            txtViewProsek.setText("No statistics yet");
            txtViewNajcescePrvi.setText("");
            txtViewKolikoPutaPrvi.setText("");
            txtViewNajviseClanova.setText("");
            txtViewKada.setText("");
        }
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
