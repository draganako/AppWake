package com.example.appwake.Activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.appwake.R;


import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;


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
import android.widget.TextView;
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
import com.example.appwake.SHA512.Sha512;
import com.example.appwake.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class RegisterDataActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private UserRegisterTask mRegTask = null;

    private EditText mEmailView;
    private EditText mUsernameView;
    private EditText mPasswordView;

    void registracija(View view) {
        attemptRegister();
    }


    void skip(View view) {
        Korisnik.setInstance(null);
        Toast.makeText(RegisterDataActivity.this, "Unsigned user!", Toast.LENGTH_LONG).show();

    }


    private void attemptRegister() {

        if (mRegTask != null) {
            return;
        }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mUsernameView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String username = mUsernameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            mPasswordView.setError("Invalid password! Must be at least 5 characters long and contain a digit");
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Field required");
            focusView = mPasswordView;
            cancel = true;
        }


        // Check for a valid email address.
        if (!isEmailValid(email)) {
            mEmailView.setError("Invalid email");
            focusView = mEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Field required");
            focusView = mEmailView;
            cancel = true;
        }


        if (!isUsernameValid(username)) {
            mUsernameView.setError("Invalid username");
            focusView = mUsernameView;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {
            mUsernameView.setError("Field required");
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // kick off a background task to
            // perform the user login attempt.

            mRegTask = new UserRegisterTask(email, password, username);
            mRegTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {//zameniti pravom proverom
        //TODO: Replace this with your own logic
        if( password.length() >= 5 && containsNo(password))
            return true;
        else
            return false;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        if (email == null)
            return false;
        else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

    }

    private boolean containsNo(String s)
    {
        if(s.contains("0")||s.contains("1")||s.contains("2")||s.contains("3")||s.contains("4")||s.contains("5")
                ||s.contains("6")||s.contains("7") ||s.contains("8")||s.contains("9"))
            return true;
        else return false;
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);

        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.emailTb);
        mUsernameView = (EditText) findViewById(R.id.usernameTb);
        mPasswordView = (EditText) findViewById(R.id.passwordTb);


        Button mEmailSignInButton = (Button) findViewById(R.id.registerBtn);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });


    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
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
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
     //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
     ArrayAdapter<String> adapter =
     new ArrayAdapter<>(LoginActivity.this,
     android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

     mEmailView.setAdapter(adapter);
     }*/
    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mUsername;
        private Korisnik korisnik;


        UserRegisterTask(String email, String password, String username) {
            mEmail = email;
            mPassword = password;
            mUsername = username;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                String urlFind = "http://appwake.dacha204.com/api/Korisnik/email/" + mEmail + "/";

                final JsonObjectRequest request = new JsonObjectRequest
                        (Request.Method.GET, urlFind, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("Email") != "null"){
                                        Toast.makeText(RegisterDataActivity.this, "Database already contains a user with the given email!", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        try {
                                            final Korisnik k = new Korisnik();
                                            k.setEmail(mEmail);
                                            k.setUsername(mUsername);
                                            k.setSlika("boy");
                                            String salt = new String(Sha512.Salt(), StandardCharsets.ISO_8859_1);
                                            String saltedPass= Sha512.encryptThisString(mPassword, salt);
                                            k.setPassword(saltedPass);

                                            String urllog = "http://appwake.dacha204.com/api/Korisnik";
                                            JSONObject obj = new JSONObject();
                                            obj.put("Email", mEmail);
                                            obj.put("Password", saltedPass);
                                            obj.put("Ime", "");
                                            obj.put("Prezime", "");
                                            obj.put("DatumRodjenja", null);
                                            obj.put("Username", mUsername);
                                            obj.put("JeAdministrator", 0);
                                            obj.put("Status", 0);
                                            obj.put("Slika", "boy"); //default slika
                                            obj.put("Salt", salt); ///////

                                            JsonObjectRequest request1 = new JsonObjectRequest
                                                    (Request.Method.POST, urllog, obj, new Response.Listener<JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            try {
                                                                int idKorisnika = response.getInt("response");
                                                                if (idKorisnika > 0) {


                                                                    k.setId(idKorisnika);

                                                                    Korisnik.setInstance(k);

                                                                    SaveSharedPreference.setLoggedIn(getApplicationContext() ,true ,idKorisnika,mEmail, 0); //admina necemo da registrujemo odavde!

                                                                    Toast.makeText(RegisterDataActivity.this, "Register successful! Welcome to AppWake!", Toast.LENGTH_LONG).show();

                                                                    Intent intent = new Intent(RegisterDataActivity.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }

                                                                else {
                                                                    Toast.makeText(RegisterDataActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                                                                    recreate();

                                                                }

                                                            } catch (Exception e) {
                                                                Toast.makeText(RegisterDataActivity.this, "Error! Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                recreate();

                                                            }
                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(RegisterDataActivity.this, "Error! Message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            recreate();

                                                        }
                                                    });

                                            RequestQueue queue1 = Volley.newRequestQueue(RegisterDataActivity.this);
                                            queue1.add(request1);

                                        } catch (Exception e) {
                                            Toast.makeText(RegisterDataActivity.this, "Error! Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            // showProgress(false);
                                            recreate();

                                        }

                                    }
                                }catch (Exception e){
                                    Toast.makeText(RegisterDataActivity.this, "Error! Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    recreate();

                                }

                            }}, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(RegisterDataActivity.this,"Error! Message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                recreate();

                            }

                        });

                RequestQueue queue = Volley.newRequestQueue(RegisterDataActivity.this);
                queue.add(request);


                return true;
            } catch (Exception e) {
                Toast.makeText(RegisterDataActivity.this, "Error! Message: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return false;
            }
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mRegTask = null;
            //showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mRegTask = null;
        }



    }


        @Override
        public void onBackPressed() {

            Intent intent = new Intent(RegisterDataActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


}