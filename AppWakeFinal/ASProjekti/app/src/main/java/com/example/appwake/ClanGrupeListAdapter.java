package com.example.appwake;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.Activities.MainActivity;
import com.example.appwake.KorisnikListAdapter;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class ClanGrupeListAdapter extends ArrayAdapter<Korisnik> implements View.OnClickListener {


    private static final String TAG = "ClanGrupeListAdapter";

    private Context context;
    private int resource;
    Button buttooon;

    private ArrayList<Korisnik> originalData;

    public ClanGrupeListAdapter(Context context, int resource, ArrayList<Korisnik> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.originalData = objects;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Korisnik korisnik = originalData.get(position);
        //String username = getItem(position).getUsername();
        String username = korisnik.getUsername();

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        TextView txtViewUsername= (TextView)convertView.findViewById(R.id.username);
        txtViewUsername.setText(username);

        buttooon = (Button)convertView.findViewById(R.id.btnAddUser);
        buttooon.setTag(korisnik);


        if(korisnik.getStatus() == 0) //znaci da nije budan
        {
            buttooon.setText("Wake up!");

            buttooon.setBackgroundColor(0xffff4444);
        }
        else  if(korisnik.getStatus() == 1) //znaci da treba da se potvrdi
        {
            buttooon.setText("Confirm");
            buttooon.setBackgroundColor(0xffffbb33);
        }
        else if(korisnik.getStatus() == 2) //znaci da je budan
        {
            buttooon.setText("Awake");
            buttooon.setBackgroundColor(0xff99cc00);
        }
        else if(korisnik.getStatus() == 3)
        {
            buttooon.setText("Sent");

            buttooon.setBackgroundColor(0xffff4444);
        }

        if(korisnik.getId() == SaveSharedPreference.getLoggedId(getContext()))
        {
            buttooon.setActivated(false);
            buttooon.setEnabled(false);
            buttooon.setBackgroundColor(0xaa555555);
            txtViewUsername.setText("You: " + username);
        }


        buttooon.setOnClickListener(this);

        ImageView im = (ImageView) convertView.findViewById(R.id.profilnaUListi);


        String slik = korisnik.getSlika();
        switch (slik){
            case "boy": im.setImageResource(R.drawable.boy); break;
            case "girl": im.setImageResource(R.drawable.girl); break;
            case "girl_1": im.setImageResource(R.drawable.girl_1); break;
            case "boy_1": im.setImageResource(R.drawable.boy_1); break;
            case "man": im.setImageResource(R.drawable.man); break;
            case "man_1": im.setImageResource(R.drawable.man_1); break;
            case "man_2": im.setImageResource(R.drawable.man_2); break;
            case "man_3": im.setImageResource(R.drawable.man_3); break;
            default: im.setImageResource(R.drawable.man_4); break;
            }



        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddUser) {
            View parent = (View) v.getParent();
            Button btn = (Button) v;

            int newStatus = 2;

            final Korisnik kor = (Korisnik) btn.getTag();


            if(kor.getStatus() == 2)
            {
                Toast.makeText(getContext(), "User " + kor.getUsername() + "is awake" , Toast.LENGTH_SHORT).show();
                return;
            }
            else if(kor.getStatus() == 0)
            {
                newStatus = 3;
                btn.setText("Sent");
                kor.setStatus(3);
                btn.setTag(kor);

            }
            else if(kor.getStatus() == 1)
            {
                newStatus = 2;
                btn.setText("Awake");
                btn.setBackgroundColor(0xff99cc00);
                kor.setStatus(2);
                btn.setTag(kor);
                //Toast.makeText(getContext(), "User confirmed" + kor.getUsername(), Toast.LENGTH_SHORT).show();
            }
            else
            {
                return;
            }



            try {
                String url = "http://appwake.dacha204.com/api/Korisnik/" + kor.getId();
                JSONObject obj = new JSONObject();
                obj.put("Id", kor.getId());
                obj.put("Ime", kor.getIme());
                obj.put("Prezime", kor.getPrezime());
                obj.put("DatumRodjenja",  kor.getDatumRodjenja());
                obj.put("Username",  kor.getUsername());
                obj.put("Password",  kor.getPassword());
                obj.put("Email",  kor.getEmail());
                obj.put("Slika",  kor.getSlika());
                obj.put("Status",  newStatus);
                obj.put("JeAdministrator",kor.getJeAdmin());

                JsonObjectRequest request1 = new JsonObjectRequest
                        (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getInt("response") == 0) {
                                        Toast.makeText(getContext(), "User updated!", Toast.LENGTH_LONG).show();

                                    }
                                    else{
                                        Toast.makeText(getContext(), "Error updating user!", Toast.LENGTH_SHORT).show();
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
    }
}
