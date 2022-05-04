package com.example.androiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;


public class TuotteenTallennus extends AppCompatActivity {
    /**
     * Tehdään lista tallennetuille tuotteille
     */
    ArrayList<TallennettuTuote> tallennetut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuotteen_tallennus);


    }

    /**
     * Metodi, joka lukee käyttäjän syötteet ja antaa syötteiden arvot kofeiininlisäys aktiiviteetille tallennetuttuotteen tallentamista varten
     * @param view view on se view jota painettiin
     */
    public void palaaToiseenNakymaan(View view){
        Intent intent = new Intent(this, kofeiininLisays.class);
        EditText nimi = findViewById(R.id.tuotteenNimi);
        EditText kofeiini = findViewById(R.id.tuotteenKofeiini);
        EditText hinta = findViewById(R.id.tuotteenHinta);
        String syotettyKofeiiniString = kofeiini.getText().toString();
        String syotettyHintaString = hinta.getText().toString();
        /**
         * Tarkistetaan onko syötteet tyhjiä, ovatko numerot doubleja ja onko nimi käytetty jo
         * @author Perttu Harvala
         */
        if(!nimi.getText().toString().equals("") && !kofeiini.getText().toString().equals("") && !hinta.getText().toString().equals("")){
            Boolean onkoDouble = true;

            if(syotettyHintaString.indexOf(",") != -1){
                syotettyHintaString = syotettyHintaString.replace(",", ".");
            }
            if(syotettyKofeiiniString.indexOf(",") != -1){
                syotettyKofeiiniString = syotettyKofeiiniString.replace(",", ".");
            }
            try {
                Double num = Double.parseDouble(syotettyKofeiiniString);
                Double num2 = Double.parseDouble(syotettyHintaString);
            } catch (NumberFormatException e) {
                onkoDouble = false;
            }
            if(onkoDouble){
                SharedPreferences sharedPreferences2 = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson2 = new Gson();
                String json2 = sharedPreferences2.getString("Tallennetut tuotteet", null);
                Type type = new TypeToken<ArrayList<TallennettuTuote>>() {}.getType();
                tallennetut = gson2.fromJson(json2, type);
                if(tallennetut == null){
                    tallennetut = new ArrayList<>();
                }
                TallennettuTuote testiTuote = new TallennettuTuote(nimi.getText().toString(), Double.parseDouble(syotettyKofeiiniString),Double.parseDouble(syotettyHintaString));
                if(!tallennetut.contains(testiTuote)){
                    Bundle extras = new Bundle();
                    extras.putString("EXTRA_NIMI", nimi.getText().toString());
                    extras.putDouble("EXTRA_KOFEIINI", Double.parseDouble(syotettyKofeiiniString));
                    extras.putDouble("EXTRA_HINTA", Double.parseDouble(syotettyHintaString));
                    intent.putExtras(extras);
                    startActivity(intent);
                }
                else{
                    TextView varoitus = findViewById(R.id.varoitusTeksti);
                    varoitus.setText(nimi.getText().toString() + " on jo tallennettu!");
                }
            }
            else {
                TextView varoitus = findViewById(R.id.varoitusTeksti);
                varoitus.setText("Luvuissa saa olla vain 1 piste/pilkku!");
            }



        }
        else{
            TextView varoitus = findViewById(R.id.varoitusTeksti);
            varoitus.setText("Täytä kaikki kentät!");
        }

    }
}