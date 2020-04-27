package com.example.myapplication.ui.saisie;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.ui.bdd.BdAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ui.bdd.Table.Releve;
import com.example.myapplication.ui.donnees.DonneesFragment;
import com.google.android.material.snackbar.Snackbar;


public class SaisieFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_saisie, container, false);
        Button buttonSave;
        TextView laDate;
        final EditText temp, nom;

        temp = (EditText) v.findViewById(R.id.InputTemp);
        nom = (EditText) v.findViewById(R.id.InputNom);
        buttonSave = (Button) v.findViewById(R.id.buttonSave);
        final Spinner spinnerHeure = (Spinner) v.findViewById(R.id.menuHeure);
        final BdAdapter relevesBdd = new BdAdapter(getActivity());

        // Menu déroulant pour sélectionner le jour

        final Spinner spinnerJour = (Spinner) v.findViewById(R.id.menuJourSaisie);
        ArrayAdapter<CharSequence> adapterJour = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.nom_jour, android.R.layout.simple_spinner_item);
        adapterJour.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerJour.setAdapter(adapterJour);

        // Menu déroulant pour sélectionner le mois

        final Spinner spinnerMois = (Spinner) v.findViewById(R.id.menuMoisSaisie);
        ArrayAdapter<CharSequence> adapterMois = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.nom_mois, android.R.layout.simple_spinner_item);
        adapterMois.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerMois.setAdapter(adapterMois);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float far = Float.parseFloat(temp.getText().toString())* 9/5 + 32;

                Releve AjoutReleve = new Releve(nom.getText().toString(), spinnerJour.getSelectedItem().toString(), spinnerMois.getSelectedItem().toString(), spinnerHeure.getSelectedItem().toString(),temp.getText().toString(), Float.toString(far));

                if (nom.getText().toString().equals("") || temp.getText().toString().equals("")) {

                    Snackbar.make(view, "Veuillez remplir le formulaire", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else {

                    relevesBdd.open();
                    //On insère le releve que l'on vient de créer
                    relevesBdd.insererReleve(AjoutReleve);
                    relevesBdd.close();

                    Snackbar.make(view, "Données enregistrée", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        // Menu déroulant pour sélectionner le mois dans la moyenne

        ArrayAdapter<CharSequence> adapterHeure = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.heure, android.R.layout.simple_spinner_item);
        adapterHeure.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerHeure.setAdapter(adapterHeure);

        return v;
    }



}



