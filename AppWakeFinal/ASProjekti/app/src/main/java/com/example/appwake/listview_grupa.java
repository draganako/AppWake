package com.example.appwake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appwake.Activities.LoginActivity;

public class listview_grupa extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_grupa);

        Button btn = findViewById(R.id.btnGrupa);
        btn.setOnClickListener(this);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "HELLO ", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getApplicationContext(), proba2.class);
                //startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnGrupa) {
           // Intent intent = new Intent(this, LoginActivity.class);
           // startActivity(intent);
            //Toast.makeText(getApplicationContext(), "HELLO ", Toast.LENGTH_SHORT).show();
        }
    }
}
