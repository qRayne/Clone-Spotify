package com.example.clonespotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomActivity extends AppCompatActivity {

    ListView listViewCouleur;
    SimpleAdapter simpleAdapter;
    Vector<Hashtable<String,String>> vector;
    EcouteurListeView ec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        listViewCouleur = findViewById(R.id.listViewCouleur);
        vector = new Vector<>();
        ec = new EcouteurListeView();
        simpleAdapter = new SimpleAdapter(CustomActivity.this,
                remplirCouleur(),R.layout.layoutcomplex,new String[]{"Numero","nomCouleurButton","nomCouleurHexa"},new int[]{R.id.numero,R.id.nomCouleurButton,R.id.nomCouleurHexa});

        listViewCouleur.setAdapter(simpleAdapter);
        listViewCouleur.setOnItemClickListener(ec);
    }

    public Vector<Hashtable<String,String>> remplirCouleur(){

        try {
            // Ici on lit le fichier couleurs.txt et on recupère toutes les infos : numero, coul
            FileInputStream fis = openFileInput("couleurs.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            Scanner sc = new Scanner(isr);

            while(sc.hasNext()){
                Hashtable<String,String> hashtable= new Hashtable<>();
                sc.useDelimiter("[;]"); // ici le delimiter est ; et a chaque fin de ligne, il a un ; pour separer les couleurs et leurs infos
                hashtable.put("Numero",sc.next());
                hashtable.put("nomCouleurButton",sc.next());
                hashtable.put("nomCouleurHexa",sc.next());
                vector.add(hashtable);
            }

            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // on retourne alors un vector rempli de couleurs qui sera mis dans un listView
        return vector;
    }

    private class EcouteurListeView implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // On recupère l'item clicker
            String itemValue = adapterView.getItemAtPosition(i).toString();
            String hexaValue = "";

            // recuperer la valeur hexa
            // on recupère la chaine de caractère au complet et on veut retrouver la couleur en hexa
            Pattern pattern = Pattern.compile("nomCouleurHexa=(.*)");
            Matcher matcher = pattern.matcher(itemValue);
            if (matcher.find()) {
                hexaValue = matcher.group(1).replace("}",""); // on enlève le } parce que ca ne fait pas partie des caracètres d'un hexa
            }

            // le renvoie à l'activite de musique
            Intent retour = new Intent();
            retour.putExtra("hexaValue",hexaValue);
            setResult(1,retour);
            finish();
        }
    }


}