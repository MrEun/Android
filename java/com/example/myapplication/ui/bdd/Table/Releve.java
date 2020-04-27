package com.example.myapplication.ui.bdd.Table;

public class Releve {
    protected String numLac;
    protected String jour;
    protected String mois;
    protected String heure;
    protected String temp;
    protected String far;

    //constructeur paramétré
    public  Releve (String numLac, String jour, String mois, String heure, String temp, String far){
        super();
        this.numLac=numLac;
        this.jour=jour;
        this.mois=mois;
        this.heure=heure;
        this.temp=temp;
        this.far=far;
    }

    //les accesseurs
    public String getNumLac(){
        return numLac;
    }
    public String getJour(){
        return jour;
    }
    public String getMois(){
        return mois;
    }
    public String getHeure(){
        return heure;
    }
    public String getTemp(){
        return temp;
    }
    public String getFar(){
        return far;
    }


    //les mutateurs
    public void setNumLac(String numLac){
        this.numLac=numLac;
    }
    public void setJour(String jour) {
        this.jour = jour;
    }
    public void setMois(String mois){
        this.mois=mois;
    }
    public void setHeure(String heure){
        this.heure=heure;
    }
    public void setTemp(String temp){
        this.temp=temp;
    }
    public void setFar(String temp){
        this.far=far;
    }


}

