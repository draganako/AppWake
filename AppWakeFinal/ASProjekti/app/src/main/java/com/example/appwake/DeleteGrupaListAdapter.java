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

import java.security.AccessControlContext;
import java.util.ArrayList;

public class DeleteGrupaListAdapter extends ArrayAdapter<Grupa> implements View.OnClickListener, Filterable {

    private Context context;
    private int resource;
    private DeleteGrupaListAdapter.FilterClass filter;

    private ArrayList<Grupa> originalData;
    private ArrayList<Grupa> filteredData;

    public DeleteGrupaListAdapter(Context context, int resource, ArrayList<Grupa> objects) {
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

        Grupa grupa = filteredData.get(position);
        //String username = getItem(position).getUsername();
        int id = grupa.getId();
        String naziv = grupa.getNaziv();

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        TextView txtViewId= (TextView)convertView.findViewById(R.id.txtDeleteIdAdmin);
        txtViewId.setText("ID: " + String.valueOf(id));

        TextView txtViewNaziv= (TextView)convertView.findViewById(R.id.txtDeleteNameAdministrator);
        txtViewNaziv.setText(naziv);

        Button btnDelete = (Button)convertView.findViewById(R.id.btnDeleteAdministrator);
        btnDelete.setTag(grupa);
        btnDelete.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnDeleteAdministrator)
        {
            View parent = (View)v.getParent();
            Button btn = (Button)v;

            final Grupa grupaZaBrisanje = (Grupa)btn.getTag();


            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("Delete group " + grupaZaBrisanje.getNaziv() + "?")
                    .setMessage("Are you sure you want to permanently delete group " + grupaZaBrisanje.getNaziv() + "?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int idToDelete = grupaZaBrisanje.getId();

                            try {
                                String url = "http://appwake.dacha204.com/api/Grupa/"+ idToDelete;
                                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if (response.getInt("response") == 0) {
                                                int toDelete = getPosition(grupaZaBrisanje);

                                                if (Korisnik.getInstance().getGrupe().contains(grupaZaBrisanje))
                                                    Korisnik.getInstance().getGrupe().remove(grupaZaBrisanje);
                                                originalData.remove(grupaZaBrisanje);
                                                filteredData.remove(grupaZaBrisanje);
                                                Toast.makeText(getContext(), "Group " + grupaZaBrisanje.getNaziv() + " deleted" , Toast.LENGTH_SHORT).show();

                                                notifyDataSetChanged();
                                            }
                                            else{
                                                Toast.makeText(getContext(), "Deleting unsuccessful!", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception ex) {
                                            Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getContext(), "An error occurred while accessing the database! Message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                RequestQueue queue = Volley.newRequestQueue(getContext());
                                queue.add(request);
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), "An error occurred! Message: " + ex.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .create();

            dialog.show();
        }
    }

    @Override
    public Filter getFilter()
    {
        if (filter == null)
            filter = new DeleteGrupaListAdapter.FilterClass();
        return filter;
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
                ArrayList<Grupa> filterResultsData = new ArrayList<Grupa>();

                for(Grupa data : originalData)
                {
                    if(data.getNaziv().toLowerCase().contains(charSequence.toString().toLowerCase()))
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
            filteredData = (ArrayList<Grupa>)filterResults.values;
            notifyDataSetChanged();
        }
    }

}
