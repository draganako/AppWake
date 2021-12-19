package com.example.appwake.Activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.appwake.GlobalVariables;
import com.example.appwake.GrupaListAdapter;
import com.example.appwake.Models.Grupa;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.R;
import com.example.appwake.SaveSharedPreference;
import com.example.appwake.SettingsActivity;
import com.example.appwake.TheAlarm;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.acl.Group;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


//koristi retrofit client za api call !!!
/*import com.anuragdhunna.www.sessionmangementsharedpref.utils.LoginServices;
import com.anuragdhunna.www.sessionmangementsharedpref.R;
import com.anuragdhunna.www.sessionmangementsharedpref.utils.RetrofitClient;
import com.anuragdhunna.www.sessionmangementsharedpref.utils.SaveSharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;*/
//


/*import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Fakultet;
import com.ab.ssd.ab_arhivablanketa.Klase.GodinaStudija;
import com.ab.ssd.ab_arhivablanketa.Klase.Korisnik;
import com.ab.ssd.ab_arhivablanketa.Klase.Predmet;
import com.ab.ssd.ab_arhivablanketa.Klase.Rok;
import com.ab.ssd.ab_arhivablanketa.Klase.Smer;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
*/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    String items[] = new String[]{"apple", "orange"};

    Toolbar toolbar;
    Button logoutBT;
    private GrupaListAdapter adapterGrupe;

    private DrawerLayout drawerLayout;
    // ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View drawerouter = (View) findViewById(R.id.drawer_layout);

        final NavigationView nv = (NavigationView) drawerouter.findViewById(R.id.nav_view);
        final View headerLayout = nv.getHeaderView(0);
        final ImageView profImage = (ImageView) headerLayout.findViewById(R.id.profile_image);

        profImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PickProfilePicActivity.class);
                startActivity(intent);
                finish();


            }
        });

        final TextView userInfo = headerLayout.findViewById(R.id.userInfo);

        if(Korisnik.getInstance().getIme().equals("")&&
                Korisnik.getInstance().getPrezime().equals(""))
        {
            userInfo.setText("No name yet" + "\nUsername: "
                    + Korisnik.getInstance().getUsername());
        }
        else{
            userInfo.setText(Korisnik.getInstance().getIme() + " " + Korisnik.getInstance().getPrezime() + "\nUsername: "
                    + Korisnik.getInstance().getUsername());
        }

        final Menu menu = nv.getMenu();
        final MenuItem adminSettinge = menu.findItem(R.id.nav_settingsAdmin);


        if (SaveSharedPreference.getJeAdmin(getApplicationContext()) == 1) {
            adminSettinge.setVisible(true);
        }

        String imageName = Korisnik.getInstance().getSlika();
        Resources resources = getResources();
        Drawable d;

        if (imageName.equals("") || imageName == null) {
            String slikaDefault = (String) GlobalVariables.getprofilePictureName();
            int resourceId = resources.getIdentifier(slikaDefault, "drawable", getApplicationContext().getPackageName());
            d = resources.getDrawable(resourceId);
        } else {
            int resourceId = resources.getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());
            d = resources.getDrawable(resourceId);
        }
        profImage.setImageDrawable(d);





        NavigationView navig = (NavigationView) findViewById(R.id.nav_view);

        navig.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (SaveSharedPreference.getLoggedStatusbudan(MainActivity.this) == 0 || SaveSharedPreference.getLoggedStatusbudan(MainActivity.this) == 3) {

                    android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("You are awake!")
                            .setMessage("Please let other group members know by pressing the switch button")
                            //.setView(parent)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create();

                    dialog.show();

                } else {
                    int id = menuItem.getItemId();

                    if (id == R.id.nav_changeinfo) {
                        Intent intent = new Intent(getApplicationContext(), ChangeUserDataActivity.class);
                        startActivity(intent);
                        finish();

                        return true;
                    } else if (id == R.id.nav_logout) {
                        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Logging out")
                                .setMessage("Are you sure you want to log out?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        logout();
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .create();
                        dialog.show();
                        return true;
                    } else if (id == R.id.nav_groupcreate) {
                        Intent intent = new Intent(getApplicationContext(), CreateGroupActivity.class);
                        startActivity(intent);
                        finish();

                        return true;

                    } else if (id == R.id.nav_settingsAdmin) {

                        Intent intent = new Intent(getApplicationContext(), AdministratorActivity.class);
                        startActivity(intent);
                        finish();

                        return true;
                    }
                    return false;

                }
                return false;
            }

        });


        FrameLayout fragmentContainer=findViewById(R.id.fragment_container);
        TextView nogroups=fragmentContainer.findViewById(R.id.nogroups);
        ListView groupListView = findViewById(R.id.listaGrupa);

        if(Korisnik.getInstance().getGrupe().isEmpty())
        {
            groupListView.setVisibility(View.INVISIBLE);
            nogroups.setVisibility(View.VISIBLE);


        }
        else
        {
            nogroups.setVisibility(View.INVISIBLE);
            groupListView.setVisibility(View.VISIBLE);



        }

        View flactbtn=fragmentContainer.findViewById(R.id.floating_action_button);
        flactbtn.setVisibility(View.VISIBLE);
        flactbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (SaveSharedPreference.getLoggedStatusbudan(MainActivity.this) == 0 || SaveSharedPreference.getLoggedStatusbudan(MainActivity.this) == 3) {

                    android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("You are awake!")
                            .setMessage("Please let other group members know by pressing the switch button")
                            //.setView(parent)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create();

                    dialog.show();
                }

                Intent intent = new Intent(MainActivity.this, CreateGroupActivity.class);
                startActivity(intent);
                finish();
            }
        });



        adapterGrupe = new GrupaListAdapter(this, R.layout.fragment_item_grupa, Korisnik.getInstance().getGrupe());
        groupListView.setAdapter(adapterGrupe);

        createNotificationChannel("131317");
        createNotificationChannel("131318");
        createNotificationChannel("131319");

       Switch sw= headerLayout.findViewById(R.id.switch1);

       if(SaveSharedPreference.getLoggedStatusbudan(MainActivity.this) == 0 || SaveSharedPreference.getLoggedStatusbudan(MainActivity.this) == 3 )
       {
           sw.setChecked(false);
       }
       else
           sw.setChecked(true);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    try {
                        String url = "http://appwake.dacha204.com/api/Korisnik/" + SaveSharedPreference.getLoggedId(getApplicationContext());
                        JSONObject obj = new JSONObject();

                        final Date pom = new Date();
                        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        obj.put("Id", SaveSharedPreference.getLoggedId(getApplicationContext()));
                        obj.put("Ime", Korisnik.getInstance().getIme());
                        obj.put("Prezime", Korisnik.getInstance().getPrezime());
                        obj.put("DatumRodjenja",  Korisnik.getInstance().getDatumRodjenja());
                        obj.put("Username",  Korisnik.getInstance().getUsername());
                        obj.put("Password",  Korisnik.getInstance().getPassword());
                        obj.put("Email",  Korisnik.getInstance().getEmail());
                        obj.put("Slika",  Korisnik.getInstance().getSlika());
                        obj.put("Status",  1);
                        obj.put("JeAdministrator",SaveSharedPreference.getJeAdmin(getApplicationContext()));
                        obj.put("RealnoVremeBudjenja",  mdformat.format(pom));

                        JsonObjectRequest request1 = new JsonObjectRequest
                                (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if (response.getInt("response") == 0) {
                                                Toast.makeText(MainActivity.this, "Good morning!", Toast.LENGTH_LONG).show();
                                                SaveSharedPreference.setLoggedStatusbudan(getApplicationContext(), 1);
                                                stopAlarm();
                                            }
                                            else{
                                                Toast.makeText(MainActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (Exception e) {
                                            Toast.makeText(MainActivity.this, "An error occurred! Message:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity.this, String.format("Error! %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
                        queue1.add(request1);

                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, String.format("Error! %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                    }


                } else {

                    Korisnik.getInstance().setGrupe(new ArrayList<Grupa>());
                    try {
                        String url = "http://appwake.dacha204.com/api/JeClan/Grupe/" + Korisnik.getInstance().getId();
                        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    Korisnik.getInstance().setGrupe(new ArrayList<Grupa>());

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

                                    final Calendar cal = Calendar.getInstance();
                                    final Date pom = new Date();
                                    for ( Grupa x: Korisnik.getInstance().getGrupe()
                                    ) {
                                        Date dat = x.getZeljenoVremeBudjenja();
                                        if(dat != null
                                                && cal.getTimeInMillis() < dat.getTime()
                                                && (pom.getTime() <cal.getTimeInMillis() + 20  || pom.getTime() > dat.getTime())) { // ako je najmanje do sada


                                            pom.setTime(dat.getTime());
                                        }
                                    }
                                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
                                    try {

                                        String url = "http://appwake.dacha204.com/api/Korisnik/" + SaveSharedPreference.getLoggedId(getApplicationContext());
                                        JSONObject obj = new JSONObject();
                                        obj.put("Id", SaveSharedPreference.getLoggedId(getApplicationContext()));
                                        obj.put("Ime", Korisnik.getInstance().getIme());
                                        obj.put("Prezime", Korisnik.getInstance().getPrezime());
                                        obj.put("DatumRodjenja",  Korisnik.getInstance().getDatumRodjenja());
                                        obj.put("Username",  Korisnik.getInstance().getUsername());
                                        obj.put("Password",  Korisnik.getInstance().getPassword());
                                        obj.put("Email",  Korisnik.getInstance().getEmail());
                                        obj.put("Slika",  Korisnik.getInstance().getSlika());
                                        obj.put("Status",  0);//statuus!!!!!
                                        obj.put("JeAdministrator",SaveSharedPreference.getJeAdmin(getApplicationContext()));

                                        JsonObjectRequest request1 = new JsonObjectRequest
                                                (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        try {
                                                            if (response.getInt("response") == 0) {

                                                                final Calendar mcurrentDate = Calendar.getInstance();
                                                                //setAlarm(mcurrentDate.getTimeInMillis() + 1000*10);
                                                                SaveSharedPreference.setLoggedStatusbudan(getApplicationContext(), 0);


                                                                if(pom.getTime() > mcurrentDate.getTimeInMillis()+20)
                                                                {
                                                                    setAlarm(pom.getTime());
                                                                    Toast.makeText(MainActivity.this, "Alarm set! Good night!", Toast.LENGTH_LONG).show();
                                                                }
                                                                else {
                                                                    Toast.makeText(MainActivity.this, "No alarm set, good night!", Toast.LENGTH_LONG).show();
                                                                }

                                                            }
                                                            else{
                                                                Toast.makeText(MainActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                                                            }

                                                        } catch (Exception e) {
                                                            Toast.makeText(MainActivity.this, "An error occurred! Message:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }, new Response.ErrorListener() {

                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(MainActivity.this, String.format("Error! %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                        RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
                                        queue1.add(request1);

                                    } catch (Exception ex) {
                                        Toast.makeText(MainActivity.this, String.format("Error! %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                                    }


                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                                }
                                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG);

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error reading database!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(request);
                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, "Error: " + ex.toString(), Toast.LENGTH_LONG).show();
                    }







                }



            }
        });



    }

    @Override
    public void onBackPressed()
    {
        getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else {
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Leaving AppWake")
                    .setMessage("Are you sure you want to leave the app?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAndRemoveTask();
                            System.exit(0);
                        }
                    })
                    .create();
            dialog.show();
        }

    }






    @Override
    public void onClick(View v) {


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void logout() {

        SaveSharedPreference.setLoggedIn(getApplicationContext(),false, SaveSharedPreference.getLoggedId(getApplicationContext()),
                SaveSharedPreference.getLoggedEmail(getApplicationContext() ),SaveSharedPreference.getJeAdmin(getApplicationContext()) );
        Toast.makeText(getApplicationContext(), "You logged out of application", Toast.LENGTH_SHORT).show();



    }

    private void setAlarm(long timeInMillis)
    {
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(MainActivity.this,TheAlarm.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    private void stopAlarm()
    {
        TheAlarm.getInstance().Stop();
    }

    private void createNotificationChannel(String chanel) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "nameeee";
            String description = "descriptiooon";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(chanel, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}




