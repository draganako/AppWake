package com.example.appwake;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appwake.Models.Grupa;
import com.example.appwake.Models.Korisnik;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class DeleteGroupMemberListAdapter extends ArrayAdapter<Korisnik> implements View.OnClickListener, Filterable {

    private Context context;
    private int resource;
    private DeleteGroupMemberListAdapter.FilterClass filter;

    private ArrayList<Korisnik> originalData;
    private ArrayList<Korisnik> filteredData;

    private int idGrupe;
    private int arrayPosition;
    private Grupa g;

    public DeleteGroupMemberListAdapter(Context context, int resource, ArrayList<Korisnik> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.originalData = objects;
        this.filteredData = objects;


    }

    public int getCount(){
        return filteredData.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Korisnik korisnik = filteredData.get(position);
        String username = korisnik.getUsername();
        int id = korisnik.getId();
        this.g = Korisnik.getInstance().getGrupe().get(arrayPosition);
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        TextView txtViewUsername= (TextView)convertView.findViewById(R.id.username);
        txtViewUsername.setText(String.format("ID: %d\r\nUsername: %s", id, username));

        ImageView im = (ImageView) convertView.findViewById(R.id.profilnaUListi);

        String slik = korisnik.getSlika();
        switch (slik){
            case "boy": im.setImageResource(R.drawable.boy); break;
            case "girl": im.setImageResource(R.drawable.girl); break;
            case "girl_1": im.setImageResource(R.drawable.girl_1); break;
            case "boy_1": im.setImageResource(R.drawable.boy_1); break;
            case "man": im.setImageResource(R.drawable.man); break;
            case "man_1": im.setImageResource(R.drawable.man_1); break;
            case "man_2": im.setImageResource(R.drawable.man_2); break;
            case "man_3": im.setImageResource(R.drawable.man_3); break;
            default: im.setImageResource(R.drawable.man_4); break;
        }

        Button btnAdd = (Button)convertView.findViewById(R.id.btnAddUser);
        btnAdd.setText("Remove");
        btnAdd.setTag(korisnik);
        btnAdd.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnAddUser)
        {
            View parent = (View)v.getParent();
            Button btn = (Button)v;

            final Korisnik userToRemove = (Korisnik)btn.getTag();


            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("Remove user " + userToRemove.getUsername() + "?")
                    .setMessage("Are you sure you want to remove user " + userToRemove.getUsername()+ " from your group?")
                    .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final int idUserToRemove = userToRemove.getId(); //zapravo azuriramo vreme napustanja grupe

                            try {
                                String url = "http://appwake.dacha204.com/api/JeClan/" + idUserToRemove + "/" + idGrupe;
                                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            int idToUpdate = response.getInt("response");
                                            if (idToUpdate > 0) {
                                                //Toast.makeText(getContext(), "Found table member! " + idToUpdate, Toast.LENGTH_SHORT).show();

                                                try {
                                                    String url = "http://appwake.dacha204.com/api/JeClan/" + idToUpdate;
                                                    JSONObject obj = new JSONObject();
                                                    obj.put("Id", idToUpdate);

                                                    Calendar cal = Calendar.getInstance();

                                                    obj.put("DatumNapustanja", String.format("%d-%d-%d", cal.get(Calendar.YEAR),
                                                            cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));

                                                    JsonObjectRequest request1 = new JsonObjectRequest
                                                            (Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {

                                                                @Override
                                                                public void onResponse(JSONObject response) {
                                                                    try {
                                                                        if (response.getInt("response") == 0) {
                                                                            Toast.makeText(getContext(), "User " + userToRemove.getUsername() + " removed", Toast.LENGTH_SHORT).show();

                                                                            int currentBroj = g.getBrojClanova();
                                                                            g.setBrojClanova(currentBroj - 1);

                                                                            originalData.remove(userToRemove);
                                                                            filteredData.remove(userToRemove);
                                                                            notifyDataSetChanged();
                                                                        }
                                                                        else{
                                                                            Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } catch (Exception e) {
                                                                        Toast.makeText(getContext(), "Error! Message: " + e.toString(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            }, new Response.ErrorListener() {

                                                                @Override
                                                                public void onErrorResponse(VolleyError error) {
                                                                    Toast.makeText(getContext(), String.format("Error! Message: %s", error.getMessage()), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                    RequestQueue queue1 = Volley.newRequestQueue(getContext());
                                                    queue1.add(request1);

                                                } catch (Exception ex) {
                                                    Toast.makeText(getContext(), String.format("Error! Message: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                            else{
                                                Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception ex) {
                                            Toast.makeText(getContext(), "An error occurred! Message: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getContext(), "An error occurred while reading database! Message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                RequestQueue queue = Volley.newRequestQueue(getContext());
                                queue.add(request);
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), "An error occurred! Message: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                            }



                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();

            dialog.show();
        }
    }
    ////////////
    @Override
    public Filter getFilter()
    {
        if (filter == null)
            filter = new DeleteGroupMemberListAdapter.FilterClass();
        return filter;
    }

    public void setIdGrupe(int idGrupe) {
        this.idGrupe = idGrupe;
    }

    public void setArrayPosition(int arrayPosition) {
        this.arrayPosition = arrayPosition;
    }


    private class FilterClass extends Filter{

        @Override
        protected Filter.FilterResults performFiltering(CharSequence charSequence)
        {
            Filter.FilterResults results = new Filter.FilterResults();

            if(charSequence == null || charSequence.length() == 0)
            {
                results.values = originalData;
                results.count = originalData.size();
            }
            else
            {
                ArrayList<Korisnik> filterResultsData = new ArrayList<Korisnik>();

                for(Korisnik data : originalData)
                {
                    if(data.getUsername().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        filterResultsData.add(data);
                    }
                }

                results.values = filterResultsData;
                results.count = filterResultsData.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, Filter.FilterResults
                filterResults)
        {
            filteredData = (ArrayList<Korisnik>)filterResults.values;
            notifyDataSetChanged();
        }
    }

}
