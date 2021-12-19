package com.example.appwake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.Activities.AdministratorActivity;
import com.example.appwake.Activities.LoginActivity;
import com.example.appwake.Activities.ManageGroupActivity;
import com.example.appwake.Models.Grupa;
import com.example.appwake.Models.Korisnik;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Button LoginProba;
    Button ManageGroupProba;
    Button AdministratorSettings;

    //Korisnik:
    Button CitajKorisnika;
    Button CitajSveKorisnike;
    Button DodajKorisnika;
    Button AzurirajKorisnika;
    Button ObrisiKorisnika;

    //Grupa:
    Button CitajGrupu;
    Button CitajSveGrupe;
    Button DodajGrupu;
    Button AzurirajGrupu;
    Button ObrisiGrupu;

    //JeClan:
    Button CitajSveClanoveGrupe;
    Button CitajSveGrupeKorisnika;
    Button DodajJeClan;
    Button AzurirajJeClan;
    Button ObrisiJeClan;
    Button VratiIdJeClan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        LoginProba = findViewById(R.id.btnLoginProba);
        ManageGroupProba = findViewById(R.id.mngGruopProba);
        AdministratorSettings = findViewById(R.id.btnAdministratorSettings);

        LoginProba.setOnClickListener(this);
        ManageGroupProba.setOnClickListener(this);
        AdministratorSettings.setOnClickListener(this);


        //Korisnik:
        CitajKorisnika = findViewById(R.id.btnCitajJednogKorisnika);
        CitajSveKorisnike = findViewById(R.id.btnCitajSveKorisnike);
        DodajKorisnika = findViewById(R.id.btnDodajKorisnika);
        ObrisiKorisnika = findViewById(R.id.btnObrisiKorisnika);
        AzurirajKorisnika = findViewById(R.id.btnIzmeniKorisnika);

        CitajKorisnika.setOnClickListener(this);
        CitajSveKorisnike.setOnClickListener(this);
        DodajKorisnika.setOnClickListener(this);
        ObrisiKorisnika.setOnClickListener(this);
        AzurirajKorisnika.setOnClickListener(this);


        //Grupa:
        CitajGrupu = findViewById(R.id.btnCitajJednuGrupu);
        CitajSveGrupe = findViewById(R.id.btnCitajSveGrupe);
        DodajGrupu = findViewById(R.id.btnDodajGrupu);
        AzurirajGrupu = findViewById(R.id.btnIzmeniGrupu);
        ObrisiGrupu = findViewById(R.id.btnObrisiGrupu);

        CitajGrupu.setOnClickListener(this);
        CitajSveGrupe.setOnClickListener(this);
        DodajGrupu.setOnClickListener(this);
        AzurirajGrupu.setOnClickListener(this);
        ObrisiGrupu.setOnClickListener(this);


        //JeClan:
        CitajSveClanoveGrupe = findViewById(R.id.btnCitajClanoveJedneGrupe);
        CitajSveGrupeKorisnika = findViewById(R.id.btnCitajGrupeKorisnika);
        DodajJeClan = findViewById(R.id.btnDodajJeClan);
        AzurirajJeClan = findViewById(R.id.btnIzmeniJeClan);
        ObrisiJeClan = findViewById(R.id.btnObrisiJeClan);
        VratiIdJeClan = findViewById(R.id.btnVratiIdJeClan);

        CitajSveClanoveGrupe.setOnClickListener(this);
        CitajSveGrupeKorisnika.setOnClickListener(this);
        DodajJeClan.setOnClickListener(this);
        AzurirajJeClan.setOnClickListener(this);
        ObrisiJeClan.setOnClickListener(this);
        VratiIdJeClan.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLoginProba) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.mngGruopProba) {
            Intent intent = new Intent(this, ManageGroupActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btnAdministratorSettings) {
            Intent intent = new Intent(this, AdministratorActivity.class);
            startActivity(intent);
        }

        //Korisnik:
        if (v.getId() == R.id.btnCitajJednogKorisnika) {
            int chooseYourId = 2;
            CitajJednogKorisnika(chooseYourId);
        }
        if (v.getId() == R.id.btnCitajSveKorisnike) {
            CitajSveKorisnike();
        }
        if (v.getId() == R.id.btnDodajKorisnika){
            Korisnik korisnik = new Korisnik();

            korisnik = new Korisnik();
            korisnik.setId(-1); //sam inkrementira
            korisnik.setIme("noviiii");
            korisnik.setPrezime("Trysout3");
            korisnik.setUsername("Wavork");
            try {
                korisnik.setDatumRodjenja(new SimpleDateFormat("yyyy-MM-dd").parse("1998-05-01"));
            } catch (ParseException e) {
                e.printStackTrace();
                korisnik.setDatumRodjenja(null);
            }
            korisnik.setEmail("noviiii@email.com");
            korisnik.setPassword("MySpeciallyDcxxesignedPassword");
            korisnik.setJeAdmin(0);
            korisnik.setStatus(0);
            korisnik.setSlika("boy");

            DodajKorisnika(korisnik);
        }
        if (v.getId() == R.id.btnObrisiKorisnika){
            int id = 6;
            ObrisiKorisnika(id);
        }
        if (v.getId() == R.id.btnIzmeniKorisnika){
            int id = 2;
            try {
                IzmeniKorisnika(id, "Updated", "NewName", "NewLastname",
                        new SimpleDateFormat("yyyy-MM-dd").parse("1995-05-05"), "boy", 1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //Grupa:
        if (v.getId() == R.id.btnCitajJednuGrupu){
            int idToRead = 5;
            CitajJednuGrupu(idToRead);
        }
        if (v.getId() == R.id.btnCitajSveGrupe){
            CitajSveGrupe();
        }
        if (v.getId() == R.id.btnDodajGrupu){
            Grupa g = new Grupa();
            int idAdmina = 7;
            g.setId(-1);
            g.setNaziv("Nova grupa");
            g.setBrojClanova(1);
            g.setIdAdmina(idAdmina);
            g.setJeAdmin(CitajJednogKorisnika(idAdmina));
            g.setDatumKreiranja(new Date());
            g.setZeljenoVremeBudjenja(null);

            DodajGrupu(g);
        }
        if (v.getId() == R.id.btnObrisiGrupu){
            int idToDelete = 8;
            ObrisiGrupu(idToDelete);
        }
        if (v.getId() == R.id.btnIzmeniGrupu){
            int id = 9;
            int idNovogAdmina = 15;
            try {
                IzmeniGrupu(id, "Izmenjena", 1,
                        new SimpleDateFormat("yyyy-MM-dd").parse("1995-05-05"), idNovogAdmina);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //JeClan:
        if (v.getId() == R.id.btnCitajClanoveJedneGrupe){
            int idGrupe = 6;
            CitajSveClanoveGrupe(idGrupe);
        }
        if (v.getId() == R.id.btnCitajGrupeKorisnika){
            int idKorisnika = 12;
            CitajSveGrupeKorisnika(idKorisnika);
        }
        if (v.getId() == R.id.btnDodajJeClan){
            int idKorisnika = 15;
            int idGrupe = 9;
            DodajJeClan(idKorisnika, idGrupe);
        }
        if (v.getId() == R.id.btnIzmeniJeClan){
            int idToUpdate = 10;
            Date datumNapustanja = new Date();
            Date realnoVremeBudjenja = null;
            IzmeniJeClan(idToUpdate, datumNapustanja, realnoVremeBudjenja);
        }
        if (v.getId() == R.id.btnObrisiJeClan){
            int idToDelete = 7;
            ObrisiJeClan(idToDelete);
        }
        if (v.getId() == R.id.btnVratiIdJeClan){
            int idKorisnika = 12;
            int idGrupe = 6;
            VratiIdJeClan(idKorisnika, idGrupe);
        }
    }


    //Korisnik:
    public Korisnik CitajJednogKorisnika(int idToRead) {

        final Korisnik[] k = {null};
        try {
            String url = "http://appwake.dacha204.com/api/Korisnik/"+ idToRead;
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int id = response.getInt("Id");
                        String ime = response.getString("Ime");
                        String prezime = response.getString("Prezime");
                        String username = response.getString("Username");
                        Date datumRodjenja;// = new SimpleDateFormat("yyyy-MM-dd").parse(response.getString("DatumRodjenja"));
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


                        k[0] = new Korisnik();
                        k[0].setId(id);
                        k[0].setIme(ime);
                        k[0].setPrezime(prezime);
                        k[0].setUsername(username);
                        k[0].setDatumRodjenja(datumRodjenja);
                        k[0].setEmail(email);
                        k[0].setPassword(password);
                        k[0].setJeAdmin(jeAdministrator);
                        k[0].setStatus(status);
                        k[0].setSlika(slika);

                        Toast.makeText(getApplicationContext(), String.format("Hello from korisnik %s %s", k[0].getIme(), k[0].getPrezime()), Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Greska pri citanju iz baze!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, "Greska: " + ex.toString(), Toast.LENGTH_LONG).show();
        }

        return k[0];
    }

    public ArrayList<Korisnik> CitajSveKorisnike() {

        final ArrayList<Korisnik> korisnici = new ArrayList<>();

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


                            korisnici.add(korisnik);

                            Toast.makeText(SettingsActivity.this, "Hello from korisnik " + korisnik.getIme() + korisnik.getPrezime(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    catch (Exception e){
                        Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                    }
                    Toast.makeText(SettingsActivity.this, response.toString(), Toast.LENGTH_LONG);

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Greska pri citanju iz baze!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, "Greska: " + ex.toString(), Toast.LENGTH_LONG).show();
        }

        return korisnici; //vratice se prazna lista ako se desi greska
    }

    public void DodajKorisnika(final Korisnik korisnik){
        try {
            String url = "http://appwake.dacha204.com/api/Korisnik";
            JSONObject obj = new JSONObject();
            obj.put("Email", korisnik.getEmail());
            obj.put("Password", korisnik.getPassword());
            obj.put("Ime", korisnik.getIme());
            obj.put("Prezime", korisnik.getPrezime());
            obj.put("DatumRodjenja", korisnik.getDatumRodjenja());
            obj.put("Username", korisnik.getUsername());
            obj.put("JeAdministrator", korisnik.getJeAdmin());
            obj.put("Slika", korisnik.getSlika());
            obj.put("Status", korisnik.getStatus());

            JsonObjectRequest request1 = new JsonObjectRequest
                    (Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int result = response.getInt("response");
                                if (result > 0) {
                                    Toast.makeText(SettingsActivity.this, "Korisnik uspesno dodat!", Toast.LENGTH_LONG).show();
                                    korisnik.setId(result);
                                }
                                else{
                                    Toast.makeText(SettingsActivity.this, "Greska pri dodavanju korisnika!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(SettingsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SettingsActivity.this, String.format("Greska! %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                        }
                    });

            RequestQueue queue1 = Volley.newRequestQueue(SettingsActivity.this);
            queue1.add(request1);

        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, String.format("Greska! %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
        }

    }

    public void ObrisiKorisnika(int idToDelete){

        try {
            String url = "http://appwake.dacha204.com/api/Korisnik/"+ idToDelete;
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (response.getInt("response") == 0) {
                            Toast.makeText(SettingsActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SettingsActivity.this, "Deleting unsuccessful!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(SettingsActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Greska pri citanju iz baze!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, "Greska: " + ex.toString(), Toast.LENGTH_LONG).show();
        }


    }

    public void IzmeniKorisnika(int idToUpdate, String newUsername, String newIme, String newPrezime, Date newDatumRodjenja, String newSlika, int newStatus){

        try {
            String url = "http://appwake.dacha204.com/api/Korisnik/" + idToUpdate;
            JSONObject obj = new JSONObject();
            obj.put("Id", idToUpdate);
            obj.put("Ime", newIme);
            obj.put("Prezime", newPrezime);
            obj.put("DatumRodjenja", "");
            obj.put("Username", newUsername);
            obj.put("Password", "");
            obj.put("Email", "");
            obj.put("Slika", newSlika);
            obj.put("Status", newStatus);
            //obj.put("JeAdministrator", 0);

            JsonObjectRequest request1 = new JsonObjectRequest
                    (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt("response") == 0) {
                                    Toast.makeText(SettingsActivity.this, "Korisnik uspesno azuriran!", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(SettingsActivity.this, "Greska pri azuriranju korisnika!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(SettingsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SettingsActivity.this, String.format("Greska! %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                        }
                    });

            RequestQueue queue1 = Volley.newRequestQueue(SettingsActivity.this);
            queue1.add(request1);

        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, String.format("Greska! %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
        }

    }


    //Grupa:
    public Grupa CitajJednuGrupu(int idToRead){

        final Grupa[] g = {null};

        try {
            String url = "http://appwake.dacha204.com/api/Grupa/"+ idToRead;
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int id = response.getInt("Id");
                        int brojClanova = response.getInt("BrojClanova");
                        String naziv = response.getString("Naziv");
                        int idAdmina = response.getInt("IdAdmina");
                        Date datumKreiranja;
                        Date zeljenoVremeBudjenja;

                        try {
                            datumKreiranja = new SimpleDateFormat("yyyy-MM-dd").parse(response.getString("DatumKreiranja"));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            datumKreiranja = null;
                        }
                        try {
                            zeljenoVremeBudjenja = new SimpleDateFormat("yyyy-MM-dd").parse(response.getString("ZeljenoVremeBudjenja"));
                        } //vreme?? u drugacijem formatu
                        catch (Exception e){
                            e.printStackTrace();
                            zeljenoVremeBudjenja = null;
                        }

                        g[0] = new Grupa();
                        g[0].setId(id);
                        g[0].setNaziv(naziv);
                        g[0].setBrojClanova(brojClanova);
                        g[0].setDatumKreiranja(datumKreiranja);
                        g[0].setZeljenoVremeBudjenja(zeljenoVremeBudjenja);
                        g[0].setIdAdmina(idAdmina);
                        g[0].setJeAdmin(CitajJednogKorisnika(idAdmina));/////

                        Toast.makeText(getApplicationContext(), String.format("Hello from group %s", g[0].getNaziv()), Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Greska pri citanju iz baze!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, "Greska: " + ex.toString(), Toast.LENGTH_LONG).show();
        }

        return g[0];
    }

    public ArrayList<Grupa> CitajSveGrupe() {

        final ArrayList<Grupa> grupe = new ArrayList<>();

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
                            } //vreme?? u drugacijem formatu
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
                            grupa.setJeAdmin(CitajJednogKorisnika(idAdmina));/////

                           // Toast.makeText(getApplicationContext(), String.format("Hello from group %s", grupa.getNaziv()), Toast.LENGTH_SHORT).show();
                            grupe.add(grupa);
                        }
                    } catch (Exception e) {
                        Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                    }
                    Toast.makeText(SettingsActivity.this, response.toString(), Toast.LENGTH_LONG);

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error reading database!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, "Error: " + ex.toString(), Toast.LENGTH_LONG).show();
        }
        return grupe; //vratice se prazna lista ako se desi greska
    }

    public void DodajGrupu(final Grupa grupa){
        try {
            String url = "http://appwake.dacha204.com/api/Grupa";
            JSONObject obj = new JSONObject();
            obj.put("Naziv", grupa.getNaziv());
            obj.put("DatumKreiranja", grupa.getDatumKreiranja());
            obj.put("BrojClanova", grupa.getBrojClanova());
            obj.put("ZeljenoVremeBudjenja", grupa.getZeljenoVremeBudjenja());
            obj.put("IdAdmina", grupa.getIdAdmina());

            JsonObjectRequest request1 = new JsonObjectRequest
                    (Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int result = response.getInt("response");
                                if (result > 0) {
                                    Toast.makeText(SettingsActivity.this, "Grupa uspesno dodata!", Toast.LENGTH_LONG).show();
                                    grupa.setId(result); /////////
                                }
                                else{
                                    Toast.makeText(SettingsActivity.this, "Greska pri dodavanju grupe!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(SettingsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SettingsActivity.this, String.format("Greska! %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                        }
                    });

            RequestQueue queue1 = Volley.newRequestQueue(SettingsActivity.this);
            queue1.add(request1);

        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, String.format("Greska! %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    public void ObrisiGrupu(int idToDelete){

        try {
            String url = "http://appwake.dacha204.com/api/Grupa/"+ idToDelete;
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getInt("response") == 0) {
                            Toast.makeText(SettingsActivity.this, "Group deleted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SettingsActivity.this, "Deleting unsuccessful!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(SettingsActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "An error occurred while reading database! Message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
            queue.add(request);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, "An error occurred! Message: " + ex.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void IzmeniGrupu(int idToUpdate, String newNaziv, int newBrojClanova, Date newZeljenoVremeBudjenja, int newIdAdmina){

        try {
            String url = "http://appwake.dacha204.com/api/Grupa/" + idToUpdate;
            JSONObject obj = new JSONObject();
            obj.put("Id", idToUpdate);
            obj.put("Naziv", newNaziv);
            obj.put("DatumKreiranja", "");
            obj.put("BrojClanova", newBrojClanova);
            obj.put("ZeljenoVremeBudjenja", newZeljenoVremeBudjenja);
            obj.put("IdAdmina", newIdAdmina);

            JsonObjectRequest request1 = new JsonObjectRequest
                    (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt("response") == 0) {
                                    Toast.makeText(SettingsActivity.this, "Group successfully updated!", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(SettingsActivity.this, "Error updating the group!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(SettingsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SettingsActivity.this, String.format("Error! Message: %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                        }
                    });

            RequestQueue queue1 = Volley.newRequestQueue(SettingsActivity.this);
            queue1.add(request1);

        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, String.format("Error! Message: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
        }

    }


    //JeClan:
    public ArrayList<Korisnik> CitajSveClanoveGrupe(int idGrupe){

        final ArrayList<Korisnik> korisnici = new ArrayList<>();

        try {
            String url = "http://appwake.dacha204.com/api/JeClan/Clanovi/" + idGrupe;
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

                            Toast.makeText(SettingsActivity.this, "Hello from korisnik " + korisnik.getIme() + korisnik.getPrezime(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    catch (Exception e){
                        Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                    }
                    Toast.makeText(SettingsActivity.this, response.toString(), Toast.LENGTH_LONG);

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Greska pri citanju iz baze!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, "Greska: " + ex.toString(), Toast.LENGTH_LONG).show();
        }

        return korisnici;

    }

    public ArrayList<Grupa> CitajSveGrupeKorisnika(int idKorisnika){

        final ArrayList<Grupa> grupe = new ArrayList<>();

        try {
            String url = "http://appwake.dacha204.com/api/JeClan/Grupe/" + idKorisnika;
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
                            grupa.setJeAdmin(CitajJednogKorisnika(idAdmina));

                            Toast.makeText(getApplicationContext(), String.format("Hello from group %s", grupa.getNaziv()), Toast.LENGTH_SHORT).show();
                            grupe.add(grupa);
                        }
                    } catch (Exception e) {
                        Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                    }
                    Toast.makeText(SettingsActivity.this, response.toString(), Toast.LENGTH_LONG);

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Greska pri citanju iz baze!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, "Greska: " + ex.toString(), Toast.LENGTH_LONG).show();
        }
        return grupe;
    }

    public void DodajJeClan(int idKorisnika, int idGrupe){
        try {
            String url = "http://appwake.dacha204.com/api/JeClan";
            JSONObject obj = new JSONObject();


            obj.put("DatumKreiranja", new Date()); //now
            obj.put("DatumNapustanja", null);
            obj.put("RealnoVremeBudjenja", null);
            obj.put("IdKorisnika", idKorisnika);
            obj.put("IdGrupe", idGrupe);

            JsonObjectRequest request1 = new JsonObjectRequest
                    (Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int result = response.getInt("response");
                                if (result == 0) {
                                    Toast.makeText(SettingsActivity.this, "Korisnik dodat u grupu!", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(SettingsActivity.this, "Greska pri dodavanju clana u grupu!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(SettingsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SettingsActivity.this, String.format("Greska! %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                        }
                    });

            RequestQueue queue1 = Volley.newRequestQueue(SettingsActivity.this);
            queue1.add(request1);

        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, String.format("Greska! %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    public void IzmeniJeClan(int idToUpdate, Date newDatumNapustanja, Date newRealnoVremeBudjenja){

        try {
            String url = "http://appwake.dacha204.com/api/JeClan/" + idToUpdate;
            JSONObject obj = new JSONObject();
            obj.put("Id", idToUpdate);
            obj.put("DatumNapustanja", newDatumNapustanja);
            obj.put("RealnoVremeBudjenja", newRealnoVremeBudjenja);

            JsonObjectRequest request1 = new JsonObjectRequest
                    (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt("response") == 0) {
                                    Toast.makeText(SettingsActivity.this, "JeClan table successfully updated!", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(SettingsActivity.this, "Error updating!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(SettingsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SettingsActivity.this, String.format("Error! Message: %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                        }
                    });

            RequestQueue queue1 = Volley.newRequestQueue(SettingsActivity.this);
            queue1.add(request1);

        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, String.format("Error! Message: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
        }

    }

    public void ObrisiJeClan(int idToDelete){
        try {
            String url = "http://appwake.dacha204.com/api/JeClan/"+ idToDelete;
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getInt("response") == 0) {
                            Toast.makeText(SettingsActivity.this, "Table member deleted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SettingsActivity.this, "Deleting unsuccessful!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(SettingsActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "An error occurred while reading database! Message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, "An error occurred! Message: " + ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void VratiIdJeClan(int idKorisnika, int idGrupe){

        try {
            String url = "http://appwake.dacha204.com/api/JeClan/" + idKorisnika + "/" + idGrupe;
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int result = response.getInt("response");
                        if (result > 0) {
                            Toast.makeText(SettingsActivity.this, "Found table member! " + result, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SettingsActivity.this, "Id not found!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(SettingsActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "An error occurred while reading database! Message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(SettingsActivity.this, "An error occurred! Message: " + ex.toString(), Toast.LENGTH_LONG).show();
        }

    }

}

