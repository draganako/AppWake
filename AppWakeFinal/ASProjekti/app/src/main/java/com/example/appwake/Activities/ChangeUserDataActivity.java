package com.example.appwake.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.CustomRequest;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.R;
import com.example.appwake.SaveSharedPreference;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChangeUserDataActivity extends AppCompatActivity {

    TextView showDate;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    private int yearr, monthh, dayy;
    String username, ime, prezime, datumRodjenja;

    Date currentDate;

    EditText txtUsername;
    EditText txtBirthDate;
    EditText txtIme;
    EditText txtPrezime;
    //String currentPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_data);


        ActionBar actionBar = (ActionBar) getSupportActionBar();//!!!
        actionBar.setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        txtBirthDate = findViewById(R.id.editTextBirthDate);
        txtUsername = findViewById(R.id.editTextUsername);
        txtIme = findViewById(R.id.editTextFirstName);
        txtPrezime = findViewById(R.id.editTextLastName);

        Calendar calendar = Calendar.getInstance();
        if (Korisnik.getInstance().getDatumRodjenja() != null) {

            calendar.setTime(Korisnik.getInstance().getDatumRodjenja());
            yearr = calendar.get(Calendar.YEAR);
            monthh = calendar.get(Calendar.MONTH) + 1;
            dayy = calendar.get(Calendar.DAY_OF_MONTH);

            txtBirthDate.setText(String.format("%d/%d/%d", dayy, monthh, yearr));

            String currentDateStr = String.format("%d-%d-%d", yearr, monthh, dayy);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                currentDate = format.parse(currentDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                currentDate = null;
            }

        }
        else{
            yearr = calendar.get(Calendar.YEAR);
            monthh = calendar.get(Calendar.MONTH) + 1; //meseci ovde krecu od 0
            dayy = calendar.get(Calendar.DAY_OF_MONTH);
        }

        txtUsername.setText(Korisnik.getInstance().getUsername());
        txtIme.setText(Korisnik.getInstance().getIme());
        txtPrezime.setText(Korisnik.getInstance().getPrezime());

        txtBirthDate.setFocusable(false);
        txtBirthDate.setKeyListener(null);
        txtBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dpDialog = new DatePickerDialog(ChangeUserDataActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener, yearr, monthh - 1, dayy);
                dpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                monthh = month + 1;
                yearr = year;
                dayy = dayOfMonth;

                txtBirthDate.setText(String.format("%d/%d/%d", dayy, monthh, yearr));

                String updatedDate = String.format("%d-%d-%d", yearr, monthh, dayy);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    currentDate = format.parse(updatedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    currentDate = null;
                }
            }
        };

        Button buttonApply=findViewById(R.id.buttonUpdate);
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtUsername.getText().toString().equals("")) {
                    txtUsername.setHintTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Username required!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Korisnik.getInstance().setUsername(txtUsername.getText().toString());
                    Korisnik.getInstance().setDatumRodjenja(currentDate);
                    Korisnik.getInstance().setIme(txtIme.getText().toString());
                    Korisnik.getInstance().setPrezime(txtPrezime.getText().toString());

                    try {
                        String url = "http://appwake.dacha204.com/api/Korisnik/" + Korisnik.getInstance().getId();
                        JSONObject obj = new JSONObject();
                        obj.put("Ime", Korisnik.getInstance().getIme());
                        obj.put("Prezime", Korisnik.getInstance().getPrezime());

                        if (Korisnik.getInstance().getDatumRodjenja() == null){
                            obj.put("DatumRodjenja", null);
                        }
                        else{
                            Calendar calend = Calendar.getInstance();
                            calend.setTime(Korisnik.getInstance().getDatumRodjenja());
                            obj.put("DatumRodjenja", String.format("%d-%d-%d", calend.get(Calendar.YEAR),
                                    calend.get(Calendar.MONTH) + 1, calend.get(Calendar.DAY_OF_MONTH)));

                        }
                        obj.put("Username", Korisnik.getInstance().getUsername());
                        obj.put("Slika", Korisnik.getInstance().getSlika());
                        obj.put("Status", Korisnik.getInstance().getStatus());

                        JsonObjectRequest request1 = new JsonObjectRequest
                                (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if (response.getInt("response") == 0) {
                                                Toast.makeText(ChangeUserDataActivity.this, "User successfully updated!", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(ChangeUserDataActivity.this, "Error updating user!", Toast.LENGTH_SHORT).show();
                                            }

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();


                                        } catch (Exception e) {
                                            Toast.makeText(ChangeUserDataActivity.this, "An error occurred! Message:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(ChangeUserDataActivity.this, String.format("Error! %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        RequestQueue queue1 = Volley.newRequestQueue(ChangeUserDataActivity.this);
                        queue1.add(request1);

                    } catch (Exception ex) {
                        Toast.makeText(ChangeUserDataActivity.this, String.format("Error! %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                    }
                }
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
