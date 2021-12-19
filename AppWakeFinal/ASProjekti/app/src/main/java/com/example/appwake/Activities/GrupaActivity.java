package com.example.appwake.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.appwake.Fragments.members;
import com.example.appwake.Fragments.statistics;
import com.example.appwake.R;
import com.example.appwake.SectionPageAdapter;

public class GrupaActivity extends AppCompatActivity implements statistics.OnFragmentInteractionListener, members.OnFragmentInteractionListener  {

    private SectionPageAdapter mSectionsPA;
    private ViewPager mViewPager;

    String nazivGrupe;
    int idGrupee;
    int idAdminaGrupee;
    int position;
    int brClanova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupa);


        position = getIntent().getIntExtra("position", 0);



        mSectionsPA = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager =  (ViewPager) findViewById(R.id.kontejner);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablejaut);
        tabLayout.setupWithViewPager(mViewPager);


    }


    private void setupViewPager(ViewPager viewPager)
    {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        Log.println(1, "1", "Ovde ");

        Bundle bun = new Bundle();

        bun.putInt("position", position);

        statistics s = new statistics();
        s.setArguments(bun);

        members m = new members();
        m.setArguments(bun);

        adapter.addFragment(m, "Members");
        adapter.addFragment(s, "Statistics");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }
}
