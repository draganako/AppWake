package com.example.appwake.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;




import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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
import android.widget.Toast;

import com.example.appwake.Models.Grupa;
import com.example.appwake.Models.JeClan;
import com.example.appwake.Models.Korisnik;
import com.example.appwake.Models.Statistika;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import com.example.appwake.R;
import com.example.appwake.SHA512.Sha512;
import com.example.appwake.SaveSharedPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginDataActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    //private View mLoginFormView;
    boolean prom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_data);
        // Set up the login form.

        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        mEmailView = (EditText) findViewById(R.id.emailText);
        mPasswordView = (EditText) findViewById(R.id.passwordText);

        Button mEmailSignInButton = (Button) findViewById(R.id.submitBtn);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isEmailValid(email)) {
            mEmailView.setError("Invalid email");
            focusView = mEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Field required");
            focusView = mEmailView;
            cancel = true;
        }

        if (!isPasswordValid(password)) {
            mPasswordView.setError("Invalid password");
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Field required");
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        if (email == null)
            return false;
        else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private Korisnik korisnik;


        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {


                String urllog = "http://appwake.dacha204.com/api/Korisnik/Email/" + mEmail + "/";
                JsonObjectRequest request = new JsonObjectRequest
                        (Request.Method.GET, urllog, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String email = response.getString("Email");
                                    if (email.compareTo("null") != 0) {

                                        String pass = response.getString("Password");
                                        String salt = response.getString("Salt");

                                        String saltedPass = Sha512.encryptThisString(mPassword, salt);

                                        if (saltedPass.compareTo(pass) == 0) {

                                            int id = response.getInt("Id");
                                            String ime = response.getString("Ime");
                                            String prezime = response.getString("Prezime");
                                            String username = response.getString("Username");
                                            Date datumRodjenja;
                                            try {
                                                datumRodjenja = new SimpleDateFormat("yyyy-MM-dd").parse(response.getString("DatumRodjenja"));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                datumRodjenja = null;
                                            }
                                            int jeAdministrator = response.getInt("JeAdministrator");
                                            int status = response.getInt("Status");
                                            String slika = response.getString("Slika");

                                            new Korisnik(id, ime, prezime, username, datumRodjenja, email, pass, jeAdministrator, slika, status);

                                            SaveSharedPreference.setLoggedIn(getApplicationContext(), true, id, email, jeAdministrator);

                                            try {
                                                String url = "http://appwake.dacha204.com/api/JeClan/Grupe/" + Korisnik.getInstance().getId();
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
                                                                } catch (Exception e) {
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

                                                            Intent intent = new Intent(LoginDataActivity.this, MainActivity.class);
                                                            startActivity(intent);

                                                            finish();

                                                        } catch (Exception e) {
                                                            Toast.makeText(LoginDataActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                                                            recreate();

                                                        }
                                                        Toast.makeText(LoginDataActivity.this, response.toString(), Toast.LENGTH_LONG);

                                                    }
                                                }, new Response.ErrorListener() {

                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(getApplicationContext(), "Error reading database!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                        recreate();

                                                    }
                                                });

                                                RequestQueue queue = Volley.newRequestQueue(LoginDataActivity.this);
                                                queue.add(request);
                                            } catch (Exception ex) {
                                                Toast.makeText(LoginDataActivity.this, "Error: " + ex.toString(), Toast.LENGTH_LONG).show();
                                                recreate();

                                            }

                                        } else {
                                            Toast.makeText(LoginDataActivity.this, "Password not valid", Toast.LENGTH_LONG).show();
                                            recreate();
                                        }

                                    } else {
                                        Toast.makeText(LoginDataActivity.this, "No user with given email! Register to continue!", Toast.LENGTH_LONG).show();
                                        recreate();

                                    }
                                } catch (Exception e) {
                                    Toast.makeText(LoginDataActivity.this, "An error occurred! Message:" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    recreate();

                                }

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginDataActivity.this, "No user with given email! Register to continue!", Toast.LENGTH_LONG).show();
                                recreate();
                            }
                        });

                RequestQueue queue = Volley.newRequestQueue(LoginDataActivity.this);
                queue.add(request);
                return true;

            } catch (Exception e) {
                Toast.makeText(LoginDataActivity.this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();

                return false;

            }
        }

    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(LoginDataActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}






