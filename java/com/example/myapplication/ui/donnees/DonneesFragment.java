package com.example.myapplication.ui.donnees;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.bdd.BdAdapter;
import com.example.myapplication.ui.bdd.CreateBDReleve;
import com.example.myapplication.ui.bdd.Table.Releve;
import com.google.android.material.snackbar.Snackbar;

public class DonneesFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_donnees, container, false);

        final ListView listViewReleves = (ListView) v.findViewById(R.id.listViewMoyenne);


        // Menu déroulant pour sélectionner le jour

        final Spinner spinnerJour = (Spinner) v.findViewById(R.id.menuJour);
        ArrayAdapter<CharSequence> adapterJour = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.nom_jour, android.R.layout.simple_spinner_item);
        adapterJour.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerJour.setAdapter(adapterJour);

        // Menu déroulant pour sélectionner le mois dans la moyenne

        final Spinner spinnerMois = (Spinner) v.findViewById(R.id.menuMois);
        ArrayAdapter<CharSequence> adapterMois = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.nom_mois, android.R.layout.simple_spinner_item);
        adapterMois.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerMois.setAdapter(adapterMois);


        // Menu déroulant pour sélectionner le type de température

        final Spinner spinnerTemp = (Spinner) v.findViewById(R.id.menuTemp);
        ArrayAdapter<CharSequence> adapterTemp = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.nom_temp, android.R.layout.simple_spinner_item);
        adapterTemp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerTemp.setAdapter(adapterTemp);

        //Bouton recherche de la moyenne dans la bdd

        Button btnSearch = (Button) v.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BdAdapter relevesBdd = new BdAdapter(DonneesFragment.this.getActivity());

                relevesBdd = new BdAdapter(DonneesFragment.this.getActivity());
                //On ouvre la base de données pour écrire dedans
                relevesBdd.open();
                String mois = spinnerMois.getSelectedItem().toString();
                String jour = spinnerJour.getSelectedItem().toString();
                Cursor c = relevesBdd.getReleveWithMoisAndJour(mois, jour);
                Toast.makeText(getActivity(), "il y a " + (c.getCount()) + " releves pour le " + jour+"/"+mois, Toast.LENGTH_LONG).show();
                if(spinnerTemp.getSelectedItem().toString().equals("Celsius")){
                    // colonnes à afficher
                    String[] columns = new String[]{BdAdapter.COL_NUM, BdAdapter.COL_JOUR, BdAdapter.COL_MOIS, BdAdapter.COL_HEURE, BdAdapter.COL_TEMP};
                    // champs dans lesquelles afficher les colonnes
                    int[] to = new int[]{R.id.textViewNum, R.id.textViewJour, R.id.textViewMois, R.id.textViewHeure, R.id.textViewTemp};
                    SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(DonneesFragment.this.getActivity(), R.layout.activity_releve, c, columns, to,0);
                    // Assign adapter to ListView
                    listViewReleves.setAdapter(dataAdapter);
                    relevesBdd.close();
                }else{
                    // colonnes à afficher
                    String[] columns = new String[]{BdAdapter.COL_NUM, BdAdapter.COL_JOUR, BdAdapter.COL_MOIS, BdAdapter.COL_HEURE, BdAdapter.COL_FAR};
                    // champs dans lesquelles afficher les colonnes
                    int[] to = new int[]{R.id.textViewNum, R.id.textViewJour, R.id.textViewMois, R.id.textViewHeure, R.id.textViewTemp};
                    SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(DonneesFragment.this.getActivity(), R.layout.activity_releve, c, columns, to,0);
                    // Assign adapter to ListView
                    listViewReleves.setAdapter(dataAdapter);
                    relevesBdd.close();
                }

            }
        });

        Button btnDrop = (Button) v.findViewById(R.id.btn_drop);
        btnDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BdAdapter relevesBdd = new BdAdapter(DonneesFragment.this.getActivity());
                //On ouvre la base de données pour écrire dedans
                relevesBdd.open();
                relevesBdd.dropReleve();
                Snackbar.make(v, "Table supprimée", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                relevesBdd.close();
            }
        });

        Button btnAdd = (Button) v.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BdAdapter relevesBdd = new BdAdapter(DonneesFragment.this.getActivity());
                //On ouvre la base de données pour écrire dedans
                relevesBdd.open();
                relevesBdd.CreateReleve();
                relevesBdd.close();
                Snackbar.make(v, "Table Ajoutée", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return v;
    }
}

