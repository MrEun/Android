package com.example.myapplication.ui.home;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText; // Initialisation de la variable du texte

    public HomeViewModel() { // Stock et gére les données liées à l'interface utilisateur
        mText = new MutableLiveData<>();
        mText.setValue("Bienvenue sur la page d'accueil de l'association LAC TEMPERATURE");
    }

    public LiveData<String> getText() {
        return mText;
    }
}