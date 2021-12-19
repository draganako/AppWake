package com.example.appwake.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appwake.Fragments.FragmentDeleteGroupAdmin;
import com.example.appwake.Fragments.FragmentDeleteUserAdmin;
import com.example.appwake.R;
import com.example.appwake.SectionPageAdapter;

public class AdministratorActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btnNavDeleteUser;
    private Button btnNavDeleteGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.containerAdmin);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutAdministrator);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentDeleteUserAdmin(), "Delete user");
        adapter.addFragment(new FragmentDeleteGroupAdmin(), "Delete group");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }
}
