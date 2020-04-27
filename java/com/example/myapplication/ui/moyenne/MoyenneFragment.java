package com.example.myapplication.ui.moyenne;

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

public class MoyenneFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_moyenne, container, false);

        final ListView listViewReleves = (ListView) v.findViewById(R.id.listViewMoyenne);

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

                BdAdapter relevesBdd = new BdAdapter(MoyenneFragment.this.getActivity());

                relevesBdd = new BdAdapter(MoyenneFragment.this.getActivity());
                //On ouvre la base de données pour écrire dedans
                relevesBdd.open();
                String mois = spinnerMois.getSelectedItem().toString();
                Cursor c = relevesBdd.getMoyenneWithMoisAndJour(mois);
                if(spinnerTemp.getSelectedItem().toString().equals("Celsius")){
                    String[] columns = new String[]{BdAdapter.COL_TEMP};
                    // champs dans lesquelles afficher les colonnes
                    int[] to = new int[]{R.id.textViewTemp};
                    SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(MoyenneFragment.this.getActivity(), R.layout.activity_moyenne, c, columns, to,0);
                    // Assign adapter to ListView
                    listViewReleves.setAdapter(dataAdapter);
                    relevesBdd.close();
                }else{
                    String[] columns = new String[]{BdAdapter.COL_FAR};
                    // champs dans lesquelles afficher les colonnes
                    int[] to = new int[]{R.id.textViewTemp};
                    SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(MoyenneFragment.this.getActivity(), R.layout.activity_moyenne, c, columns, to,0);
                    // Assign adapter to ListView
                    listViewReleves.setAdapter(dataAdapter);
                    relevesBdd.close();
                }

            }
        });

        return v;
    }
}

