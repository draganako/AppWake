package com.example.appwake.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.ClanGrupeListAdapter;
import com.example.appwake.Models.Grupa;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.R;
import com.example.appwake.SettingsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ManageGroupActivity extends AppCompatActivity {


    private Button btnDeleteGroup;
    private Button btnAddAMember;
    private Button btnDeleteAMember;
    private Button btnResign;
    private Button btnChangeInfo;
    private Button btnCreateStatistics;

    int brProbudjenih;
    private Grupa g;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);


        position = getIntent().getIntExtra("position", 0);
        g = Korisnik.getInstance().getGrupe().get(position);

        final TextView nazivG = findViewById(R.id.tbxNazivGrupeMng);
        nazivG.setText(g.getNaziv());

        TextView headerView = findViewById(R.id.tbxOpisGrupeMngGruop);
        headerView.setText("Group id: " + g.getId() + "    Members: " + g.getBrojClanova());


        Switch s = findViewById(R.id.switchGroupa);

        if(g.getZeljenoVremeBudjenja() != null)
            s.setChecked(true);
        else
            s.setChecked(false);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked)
                {
                    try {
                        String url = "http://appwake.dacha204.com/api/Grupa/reset/" + g.getId();
                        JSONObject obj = new JSONObject();
                        //ne mora da se prosledjuje, sama baza ce da update-uje
                        obj.put("Id", g.getId());
                        obj.put("Naziv", g.getNaziv());
                        obj.put("DatumKreiranja", "");
                        obj.put("BrojClanova", -1);
                        obj.put("ZeljenoVremeBudjenja", new SimpleDateFormat("2010-01-01"));
                        obj.put("IdAdmina", 0);

                        JsonObjectRequest request1 = new JsonObjectRequest
                                (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if (response.getInt("response") == 0) {
                                                Toast.makeText(ManageGroupActivity.this, "Group successfully updated!", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(ManageGroupActivity.this, "Error updating the group!", Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (Exception e) {
                                            Toast.makeText(ManageGroupActivity.this, "An error occurred! Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(ManageGroupActivity.this, String.format("Error! Message: %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        RequestQueue queue1 = Volley.newRequestQueue(ManageGroupActivity.this);
                        queue1.add(request1);

                    } catch (Exception ex) {
                        Toast.makeText(ManageGroupActivity.this, String.format("Error! Message: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent = new Intent(ManageGroupActivity.this, ChangeGroupInfo.class);
                    startActivity(intent);
                    finish();

                }
            }
        });

        btnDeleteGroup =  findViewById(R.id.btnDeleteGroup);

        btnDeleteGroup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(), "Ovde ide akcija brisanja grupe", Toast.LENGTH_SHORT).show();

                AlertDialog dialog = new AlertDialog.Builder(ManageGroupActivity.this)
                        .setTitle("Delete group " + g.getNaziv() + "?")
                        .setMessage("Are you sure you want to delete group " + g.getNaziv() + "?")
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
                                                            Toast.makeText(ManageGroupActivity.this, "Group deleted", Toast.LENGTH_LONG).show();
                                                            Korisnik.getInstance().getGrupe().remove(g); //uklanjamo grupu i iz korisnikove liste

                                                            Intent intent = new Intent(ManageGroupActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                            finish();

                                                        } else {
                                                            Toast.makeText(ManageGroupActivity.this, "Error deleting the group!", Toast.LENGTH_SHORT).show();
                                                        }

                                                    } catch (Exception e) {
                                                        Toast.makeText(ManageGroupActivity.this, "An error occurred! Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {

                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(ManageGroupActivity.this, String.format("Error! Message: %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    RequestQueue queue1 = Volley.newRequestQueue(ManageGroupActivity.this);
                                    queue1.add(request1);

                                } catch (Exception ex) {
                                    Toast.makeText(ManageGroupActivity.this, String.format("Error! Message: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();

                dialog.show();

            }
        });

        btnAddAMember =  findViewById(R.id.btnAddAMember);

        btnAddAMember.setOnClickListener(new View.OnClickListener() {





            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddGroupMemberActivity.class);
                intent.putExtra("idGrupe", g.getId()); //prosledjujemo id grupe
                intent.putExtra("position", position);
                startActivity(intent);
                finish();

            }
        });

        btnDeleteAMember =  findViewById(R.id.btnDeleteAMember);



        if (g.getBrojClanova() == 1){
            btnDeleteAMember.setOnClickListener(null);
        }
        else{
            btnDeleteAMember.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), DeleteGroupMemberActivity.class);
                    intent.putExtra("idGrupe", g.getId()); //prosledjujemo id grupe
                    intent.putExtra("position", position);
                    startActivity(intent);
                    finish();

                }
            });
        }



        btnResign =  findViewById(R.id.btnResign);

        if (g.getBrojClanova() == 1){
            btnResign.setOnClickListener(null);
        }
        else {
            btnResign.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), ResignLeadershipActivity.class);
                    intent.putExtra("idGrupe", g.getId()); //prosledjujemo id grupe
                    intent.putExtra("position", position);
                    intent.putExtra("nazivGrupe", g.getNaziv());
                    startActivity(intent);
                    finish();

                }
            });
        }


        btnChangeInfo =  findViewById(R.id.btnChangeInfo);

        btnChangeInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageGroupActivity.this, ChangeGroupInfo.class);
                intent.putExtra("position", position);
                startActivity(intent);
                finish();

            }
        });

        btnCreateStatistics = findViewById(R.id.BtnCreateStatistics);

        btnCreateStatistics.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                final ArrayList<Korisnik> korisnici = new ArrayList<>();
                final int brProb = 0;
                Date vremePrvog;
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

                                sracunajProb(korisnici);

                                try {
                                    String url = "http://appwake.dacha204.com/api/Statistika";
                                    JSONObject obj = new JSONObject();
                                    final Date pom = new Date();
                                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                                    obj.put("Datum", mdformat.format(pom));
                                    obj.put("BrojProbudjenih", brProbudjenih);
                                    obj.put("BrojClanova", g.getBrojClanova());
                                    obj.put("IdPrvog", g.getIdAdmina());
                                    obj.put("IdGrupe", g.getId());


                                    JsonObjectRequest request1 = new JsonObjectRequest
                                            (Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {

                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        int result = response.getInt("response");
                                                        if (result > 0) {
                                                            Toast.makeText(getApplicationContext(), "Statistics added!", Toast.LENGTH_LONG).show();

                                                        }
                                                        else{
                                                            Toast.makeText(getApplicationContext(), "Error adding statistics!", Toast.LENGTH_SHORT).show();
                                                        }

                                                    } catch (Exception e) {
                                                        Toast.makeText(getApplicationContext(), "An error occurred! Message:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {

                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(getApplicationContext(), String.format("Error! %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
                                    queue1.add(request1);

                                } catch (Exception ex) {
                                    Toast.makeText(getApplicationContext(), String.format("Error! %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch (Exception e){
                                Toast.makeText(getApplicationContext(), "An error occurred! Message:" + e.getMessage(), Toast.LENGTH_LONG);
                            }
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error reading database!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Error: " + ex.toString(), Toast.LENGTH_LONG).show();
                }





            }
        });

    }



    void sracunajProb( ArrayList<Korisnik> korisnici)
    {
        int brProb = 0;
        for (Korisnik kor: korisnici
        ) {
            if(kor.getStatus() == 2 || kor.getStatus() == 1)
                brProb++;
        }
        brProbudjenih = brProb;
        return ;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ManageGroupActivity.this, GrupaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
