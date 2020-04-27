package com.example.myapplication;

import android.os.Bundle;


import com.example.myapplication.ui.donnees.DonneesFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.saisie.SaisieFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    // Initialise l'activité.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar toolbar = findViewById(R.id.toolbar); // Recherche l'objet avec l'id et initialise la variable toolbar avec
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view); // Recherche l'objet avec l'id et initialise la variable navigationView avec
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment); // Recherche l'objet avec l'id et initialise la variable navController avec.
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    // Méthode onCreateOptionsMenu qui créer le menu des options lorsque l'utilisateur ouvre le menu pour la première fois.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Ajoute des éléments à la barre d’action si elle est présente.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // méthode onSupportNavigateUp qui est appelée chaque fois que l'utilisateur choisit de naviguer vers le haut dans la hiérarchie d'activité de l'application à partir de la barre d'actions.

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
