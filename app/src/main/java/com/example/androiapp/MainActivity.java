package com.example.androiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView fakta;
    private int rand;
    private String[] faktoja;
    private Intent lisaysSivu;
    private Intent tietoSivu;
    private Intent historiaSivu;
    ArrayList<LisattyTuote> lisatyt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent2 = getIntent();
        Bundle extras = intent2.getExtras();
        if (extras != null){
            Context context = getApplicationContext();
            CharSequence text = "Käyttökerran lisääminen onnistui!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }



        SharedPreferences sharedPreferences3 = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String userName = sharedPreferences3.getString("User name: ", null);
        String userAge = sharedPreferences3.getString("User age: ", null);

        if (userName == null  ||  userAge == null) {
            Intent intent = new Intent(MainActivity.this, Alku.class);
            startActivity(intent);
        }



        TextView teksti = (TextView) findViewById(R.id.saldo);
        fakta = (TextView) findViewById(R.id.faktaText) ;
        faktoja = new String[] {"Caffeine may result in headaches.", "Dependency, so you need to take more of it to get the same results.",
                                "Did you know that caffeine causes faster heart rate","Consuming leo causes dehydration",
                                "Some say that caffeine causes restlessness and shakiness when consumed in large quantities","Caffeine may cause in some cases of anxiety",
                                "Caffeine also causes Dependency, so you need to take more of it to get the same effects",
                                "Caffeine also causes your intestines to work faster"};

    }

    public void faktaNappi(View v){
        rand = (int) (Math.random() * 8);
        fakta.setText(faktoja[rand]);
    }

   public void lisays(View v){
        lisaysSivu = new Intent(MainActivity.this, kofeiininLisays.class);
        startActivity(lisaysSivu);

   }
   public void historiaPainike(View view){
        historiaSivu = new Intent(MainActivity.this, Historia.class);
        startActivity(historiaSivu);

   }

   public void tietoNappi(View v){
        tietoSivu = new Intent(MainActivity.this, tiedot.class);
        startActivity(tietoSivu);
   }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences2 = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = sharedPreferences2.getString("Lisatyt tuotteet", null);
        Type type = new TypeToken<ArrayList<LisattyTuote>>() {}.getType();
        lisatyt = gson2.fromJson(json2, type);
        if(lisatyt == null){
            lisatyt = new ArrayList<>();
        }


        int i = 0;
        double saldo = 0;
        while(i < lisatyt.size()){
            saldo = saldo + lisatyt.get(i).getHinta();
            i++;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Saldo", String.valueOf(saldo));
        editor.apply();


        SharedPreferences sharedPreferences4 = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String ladattuSaldo = sharedPreferences4.getString("Saldo", "0");

        TextView saldoView = findViewById(R.id.saldo);
        String saldoTeksti = "Saldo: " + ladattuSaldo + " €";
        saldoView.setText(saldoTeksti);

        SharedPreferences sharedPreferences3 = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson3 = new Gson();
        String json3 = sharedPreferences3.getString("Lisatyt tuotteet", null);
        Type type2 = new TypeToken<ArrayList<LisattyTuote>>() {}.getType();
        lisatyt = gson3.fromJson(json3, type2);
        if(lisatyt == null){
            lisatyt = new ArrayList<>();
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat pvm = new SimpleDateFormat("dd-MMM-yyyy");
        String pvmTallaHetkella = pvm.format(calendar.getTime());
        int i2 = 0;
        double paivanKofeiini = 0;
        while(i2 < lisatyt.size()){
            if(lisatyt.get(i2).getPvm().equals(pvmTallaHetkella)){
                paivanKofeiini = paivanKofeiini + lisatyt.get(i2).getKofeiini();
                i2++;
            }
            else {
                i2++;
            }
        }
        SharedPreferences sharedPreferences1 = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        int paivanKofeiiniInt = (int)paivanKofeiini;
        editor1.putString("Paivan kofeiini", String.valueOf(paivanKofeiiniInt));
        editor1.apply();


        SharedPreferences sharedPreferenceskf = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String ladattuKofeiini = sharedPreferenceskf.getString("Paivan kofeiini", "0");

        TextView kofeiiniTeksti = findViewById(R.id.textView4);
        kofeiiniTeksti.setText("Tänään käytetty kofeiini: " + ladattuKofeiini + " mg");

        TextView kofeiiniStatusTeksti = findViewById(R.id.kofeiiniStatus);
        int kaytettyKofeiiniInt = Integer.parseInt(ladattuKofeiini);
        if (kaytettyKofeiiniInt > 500){
            kofeiiniStatusTeksti.setText("Olet käyttänyt yli suositellun määrän kofeiinia päivässä!");
            kofeiiniStatusTeksti.setTextColor(Color.rgb(153, 12, 12));
        }
        else if (kaytettyKofeiiniInt > 250){
            kofeiiniStatusTeksti.setText("Olet käyttänyt yli puolet suositellusta kofeiini määrästä päivässä!");
            kofeiiniStatusTeksti.setTextColor(Color.rgb(196, 175, 10));
        }
        else if (kaytettyKofeiiniInt < 250){
            kofeiiniStatusTeksti.setText("Hyvä, olet käyttänyt alle puolet suositellusta kofeiini määrästä.");
            kofeiiniStatusTeksti.setTextColor(Color.rgb(10, 196, 69));
        }
    }


}