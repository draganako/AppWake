package com.example.appwake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.appwake.Activities.AdministratorActivity;

public class DeleteUserAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user_admin);
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), AdministratorActivity.class);
        startActivity(intent);
        finish();

    }
}
