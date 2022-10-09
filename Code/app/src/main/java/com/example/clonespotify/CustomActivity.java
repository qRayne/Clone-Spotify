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
                remplirCouleur(),R.layout.layoutcomplex,new String[]{"Numero","nomCouleurButton","nomCouleurBackground"},new int[]{R.id.numero,R.id.nomCouleurButton,R.id.nomCouleurHexa});

        listViewCouleur.setAdapter(simpleAdapter);
        listViewCouleur.setOnItemClickListener(ec);
    }

    public Vector<Hashtable<String,String>> remplirCouleur(){

        try {
            FileInputStream fis = openFileInput("couleurs.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            Scanner sc = new Scanner(isr);

            while(sc.hasNext()){
                Hashtable<String,String> hashtable= new Hashtable<>();
                sc.useDelimiter("[;]");
                hashtable.put("Numero",sc.next());
                hashtable.put("nomCouleurButton",sc.next());
                hashtable.put("nomCouleurBackground",sc.next());
                vector.add(hashtable);
            }

            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return vector;
    }

    private class EcouteurListeView implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String itemValue = adapterView.getItemAtPosition(i).toString();
            String hexaValue = "";

            // recuperer la valeur hexa
            Pattern pattern = Pattern.compile("nomCouleurBackground=(.*)");
            Matcher matcher = pattern.matcher(itemValue);
            if (matcher.find()) {
                hexaValue = matcher.group(1).replace("}","");
            }

            // le renvoie à l'activite de musique
            Intent retour = new Intent();
            retour.putExtra("hexaValue",hexaValue);
            setResult(1,retour);
            finish();
        }
    }


}