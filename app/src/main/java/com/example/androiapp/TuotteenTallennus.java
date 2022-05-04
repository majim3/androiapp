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
    ArrayList<TallennettuTuote> tallennetut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuotteen_tallennus);


    }

    public void palaaToiseenNakymaan(View view){
        Intent intent = new Intent(this, kofeiininLisays.class);
        EditText nimi = findViewById(R.id.tuotteenNimi);
        EditText kofeiini = findViewById(R.id.tuotteenKofeiini);
        EditText hinta = findViewById(R.id.tuotteenHinta);
        if(!nimi.getText().toString().equals("") && !kofeiini.getText().toString().equals("") && !hinta.getText().toString().equals("")){
            Boolean onkoDouble = true;
            try {
                Double num = Double.parseDouble(kofeiini.getText().toString());
                Double num2 = Double.parseDouble(hinta.getText().toString());
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
                TallennettuTuote testiTuote = new TallennettuTuote(nimi.getText().toString(), Double.parseDouble(kofeiini.getText().toString()),Double.parseDouble(hinta.getText().toString()));
                if(!tallennetut.contains(testiTuote)){
                    Bundle extras = new Bundle();
                    extras.putString("EXTRA_NIMI", nimi.getText().toString());
                    extras.putDouble("EXTRA_KOFEIINI", Double.parseDouble(kofeiini.getText().toString()));
                    extras.putDouble("EXTRA_HINTA", Double.parseDouble(hinta.getText().toString()));
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
                varoitus.setText("Luvuissa saa olla vain 1 piste!");
            }



        }
        else{
            TextView varoitus = findViewById(R.id.varoitusTeksti);
            varoitus.setText("Täytä kaikki kentät!");
        }

    }
}