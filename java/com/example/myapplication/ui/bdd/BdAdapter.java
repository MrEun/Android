package com.example.myapplication.ui.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.ui.bdd.Table.Releve;

public class BdAdapter {

    static final int VERSION_BDD =1;
    private static final String NOM_BDD = "releve.db";
    static final String TABLE_RELEVE = "table_releve";
    static final String COL_ID = "_id";
    static final int NUM_COL_ID = 0;
    public static final String COL_NUM = "NUM";
    static final int NUM_COL_NUM = 1;
    public static final String COL_JOUR = "JOUR";
    static final int NUM_COL_JOUR = 2;
    public static final String COL_MOIS = "MOIS";
    static final int NUM_COL_MOIS = 3;
    public static final String COL_HEURE = "HEURE";
    static final int NUM_COL_HEURE = 4;
    public static final String COL_TEMP = "TEM";
    static final int NUM_COL_TEMP = 5;
    public static final String COL_FAR = "FAR";
    static final int NUM_COL_FAR = 6;
    private CreateBDReleve bdReleve;
    private Context context;
    private SQLiteDatabase db;

    //le constructeur
    public BdAdapter  (Context context){
        this.context = context;
        bdReleve = new CreateBDReleve(context, NOM_BDD, null, VERSION_BDD);
    }

    //si la base n’existe pas, l’objet SQLiteOpenHelper exécute la méthode onCreate
    // si la version de la base a changé, la méthode onUpgrade sera lancée
    // dans les 2 cas l’appel à getWritableDatabase ou getReadableDatabase renverra  la base
    // de données en cache, nouvellement ouverte, nouvellement créée ou mise à jour

    //les méthodes d'instance
    public BdAdapter  open(){
        db = bdReleve.getWritableDatabase();
        return this;
    }
    public BdAdapter  close (){
        db.close();
        return null;
    }

    public void dropReleve (){
        db.execSQL("DROP TABLE " + TABLE_RELEVE + ";");
    }

    public void CreateReleve (){
        db.execSQL("CREATE TABLE " + TABLE_RELEVE + " ("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COL_NUM + " TEXT NOT NULL, " + COL_JOUR + " TEXT NOT NULL, " + COL_MOIS + " TEXT NOT NULL, " + COL_HEURE + " TEXT NOT NULL, " + COL_TEMP + " TEXT NOT NULL, " + COL_FAR + " TEXT NOT NULL);");
    }

    public long insererReleve (Releve unReleve){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne où on veut mettre la valeur)
        values.put(COL_NUM, unReleve.getNumLac());
        values.put(COL_JOUR, unReleve.getJour());
        values.put(COL_MOIS, unReleve.getMois());
        values.put(COL_HEURE, unReleve.getHeure());
        values.put(COL_TEMP, unReleve.getTemp());
        values.put(COL_FAR, unReleve.getFar());
        //on insère l'objet dans la BDD via le ContentValues
        return db.insert(TABLE_RELEVE, null, values);
    }

    public Releve cursorToReleve(Cursor c){ //Cette méthode permet de convertir un cursor en un releve
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;
        //Sinon
        c.moveToFirst();   //on se place sur le premier élément
        Releve unReleve = new Releve(null,null,null,null,null,null);  //On créé un releve
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        unReleve.setNumLac(c.getString(NUM_COL_NUM));
        unReleve.setJour(c.getString(NUM_COL_JOUR));
        unReleve.setMois(c.getString(NUM_COL_MOIS));
        unReleve.setHeure(c.getString(NUM_COL_HEURE));
        unReleve.setTemp(c.getString(NUM_COL_TEMP));
        unReleve.setFar(c.getString(NUM_COL_FAR));
        c.close();     //On ferme le cursor
        return unReleve;  //On retourne le releve
    }

    public Cursor getReleveWithDesignation(String designation){
        //Récupère dans un Cursor les valeurs correspondant à un releve grâce à sa designation)
        Cursor c = db.query(TABLE_RELEVE, new String[] {COL_ID,COL_NUM, COL_JOUR, COL_MOIS, COL_HEURE, COL_TEMP}, COL_MOIS + " LIKE \"" + designation +"\"", null, null, null, null, null);
        return (Cursor) cursorToReleve(c);
    }

    public int updateReleve(String ref, Releve unReleve){
        //La mise à jour d'un releve dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quel releve on doit mettre à jour grâce à sa référence
        ContentValues values = new ContentValues();
        values.put(COL_NUM, unReleve.getNumLac());
        values.put(COL_JOUR, unReleve.getJour());
        values.put(COL_MOIS, unReleve.getMois());
        values.put(COL_HEURE, unReleve.getHeure());
        values.put(COL_TEMP, unReleve.getTemp());
        values.put(COL_FAR, unReleve.getTemp());
        return db.update(TABLE_RELEVE, values, COL_NUM + " = \"" +ref+"\"", null);
    }
    public int removeReleveWithRef(String ref){
        //Suppression d'un releve de la BDD grâce à sa référence
        return db.delete(TABLE_RELEVE, COL_NUM + " = \"" +ref+"\"", null);
    }

    public Cursor getData(){
        return db.rawQuery("SELECT * FROM TABLE_RELEVE", null);
    }

    public Cursor getReleveWithMoisAndJour(String mois, String jour){
        return db.query(TABLE_RELEVE, new String[] {COL_ID,COL_NUM, COL_JOUR, COL_MOIS, COL_HEURE, COL_TEMP, COL_FAR}, COL_MOIS + " LIKE \"" + mois + "\" AND " + COL_JOUR + " LIKE \"" + jour + "\"", null, null, null, null, null);

    }

    public Cursor getMoyenneWithMoisAndJour(String mois){
        return  db.rawQuery("SELECT " + COL_ID + "," + COL_NUM + "," + COL_JOUR + "," + COL_MOIS + "," + COL_HEURE + "," + " AVG(" + COL_TEMP + ")," + "AVG(" + COL_FAR + ") FROM TABLE_RELEVE WHERE " + COL_MOIS + " LIKE " + "\"" + mois + "\"",null);    }


}
