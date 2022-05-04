package com.example.androiapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Historia extends AppCompatActivity {
    ArrayList<LisattyTuote> lisatyt;
    private int klikattu;
    HistoriaAdapteri adapteri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);

        ListView listView = (ListView)findViewById(R.id.historiaListaView);

        SharedPreferences sharedPreferences2 = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = sharedPreferences2.getString("Lisatyt tuotteet", null);
        Type type = new TypeToken<ArrayList<LisattyTuote>>() {}.getType();
        lisatyt = gson2.fromJson(json2, type);
        if(lisatyt == null){
            lisatyt = new ArrayList<>();
        }
        adapteri = new HistoriaAdapteri(getApplicationContext(), R.layout.historia_view, lisatyt);

        listView.setAdapter(adapteri);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                klikattu = i;
                int klikattuTeksti = i + 1;
                AlertDialog.Builder altdial = new AlertDialog.Builder(Historia.this);
                altdial.setMessage("Poistetaanko Lisäys " + klikattuTeksti + "?").setCancelable(false)
                        .setPositiveButton("Kyllä", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                lisatyt.remove(klikattu);
                                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(lisatyt);
                                editor.putString("Lisatyt tuotteet", json);
                                editor.apply();
                                adapteri.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Ei", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = altdial.create();
                alert.setTitle("Lisätyn kofeiininkäyttökerran poistaminen");
                alert.show();
            }
        });
    }


}