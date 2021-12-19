package com.example.appwake;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appwake.Activities.GrupaActivity;
import com.example.appwake.Activities.MainActivity;
import com.example.appwake.Models.Grupa;
import com.example.appwake.Models.Korisnik;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GrupaListAdapter  extends ArrayAdapter<Grupa> implements View.OnClickListener {

    private Context context;
    private int resource;
    private ArrayList<Grupa> dataA;

    public GrupaListAdapter(Context context, int resource, ArrayList<Grupa> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.dataA = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        Grupa grupaa = dataA.get(position);
        String username = grupaa.getNaziv();

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        TextView txtViewUsername = (TextView) convertView.findViewById(R.id.infoGrupa);

        String prikaz = "";
        if (grupaa.getZeljenoVremeBudjenja() != null) {


            Calendar cal = Calendar.getInstance();
            Date pom = new Date();
            cal.setTime(grupaa.getZeljenoVremeBudjenja());
            if(pom.getTime() + 20 < cal.getTimeInMillis())
            {
                prikaz = String.format("%d:%d:%d",  cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
            }
            else
            {
                if(grupaa.getIdAdmina() == SaveSharedPreference.getLoggedId(getContext()))
                {
                    prikaz = "Set up next alarm";
                }
                else
                {
                    prikaz = "No alarm set";
                }
            }

        }
        else
        {
            if(grupaa.getIdAdmina() == SaveSharedPreference.getLoggedId(getContext()))
            {
                prikaz = "Set up next alarm";
            }
            else
            {
                prikaz = "No alarm set";
            }
        }
        txtViewUsername.setText("Name: " + grupaa.getNaziv() + " \nMembers: " + grupaa.getBrojClanova() + "\n" + prikaz);

        Button btnAdd = (Button) convertView.findViewById(R.id.btnGrupa);
        btnAdd.setTag(grupaa);
        btnAdd.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {

        if(SaveSharedPreference.getLoggedStatusbudan(getContext()) == 0 || SaveSharedPreference.getLoggedStatusbudan(getContext()) == 3 ) {

            android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(getContext())
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
            return;
        }

        if (v.getId()==R.id.btnGrupa)
        {
            View parent = (View)v.getParent();
            Button btn = (Button)v;

            final Grupa pokazii = (Grupa)btn.getTag();

            //Toast.makeText(getContext(), "Pokazi grupu " + pokazii.getNaziv(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), GrupaActivity.class);
            //intent.putExtra("nazivGrupe", pokazii.getNaziv());
           // intent.putExtra("idGrupe", pokazii.getId());
           // intent.putExtra("idAdminaGrupe", pokazii.getIdAdmina());
            //intent.putExtra("brojClanova", pokazii.getBrojClanova());
            intent.putExtra("position", getPosition(pokazii));
            getContext().startActivity(intent);

        }
    }





}
