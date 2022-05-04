package com.example.androiapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class kofeiininLisays extends AppCompatActivity {
    private int klikattuTuote;
    private int pitkaanKlikattu;

    ArrayList<TallennettuTuote> tallennetut;
    ArrayList<LisattyTuote> lisatyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kofeiinin_lisays);

        SharedPreferences sharedPreferences2 = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = sharedPreferences2.getString("Tallennetut tuotteet", null);
        Type type = new TypeToken<ArrayList<TallennettuTuote>>() {}.getType();
        tallennetut = gson2.fromJson(json2, type);
        if(tallennetut == null){
            tallennetut = new ArrayList<>();
        }


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            if(extras.containsKey("EXTRA_NIMI") && extras.containsKey("EXTRA_KOFEIINI") && extras.containsKey("EXTRA_HINTA")){
                String tallennettavanNimi = extras.getString("EXTRA_NIMI", "");
                double tallennettavanKofeiini = extras.getDouble("EXTRA_KOFEIINI", 0);
                double tallennettavanHinta = extras.getDouble("EXTRA_HINTA", 0);
                tallennetut.add(new TallennettuTuote(tallennettavanNimi, tallennettavanKofeiini, tallennettavanHinta));
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(tallennetut);
                editor.putString("Tallennetut tuotteet", json);
                editor.apply();

            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tallennetut);
        ListView lv = (ListView)findViewById(R.id.tallennetutTuotteetLista);

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView valittuTuote = findViewById(R.id.valitunTuotteenTeksti);
                valittuTuote.setText(tallennetut.get(i).getNimi());
                klikattuTuote = i;
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                pitkaanKlikattu = i;
                AlertDialog.Builder altdial = new AlertDialog.Builder(kofeiininLisays.this);
                altdial.setMessage("Poistetaanko tuote " + tallennetut.get(pitkaanKlikattu).getNimi() + "?").setCancelable(false)
                        .setPositiveButton("Kyllä", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tallennetut.remove(pitkaanKlikattu);
                                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(tallennetut);
                                editor.putString("Tallennetut tuotteet", json);
                                editor.apply();
                                klikattuTuote = -1;
                                TextView valittuTuote = findViewById(R.id.valitunTuotteenTeksti);
                                valittuTuote.setText("");
                                arrayAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Ei", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = altdial.create();
                alert.setTitle("Tallennetun tuotteen poisto");
                alert.show();
                return true;
            }
        });
    }
    public void lisaaSyotettyTuote(View view){
        SharedPreferences sharedPreferences2 = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = sharedPreferences2.getString("Lisatyt tuotteet", null);
        Type type = new TypeToken<ArrayList<LisattyTuote>>() {}.getType();
        lisatyt = gson2.fromJson(json2, type);
        if(lisatyt == null){
            lisatyt = new ArrayList<>();
        }
        EditText syotettyKofeiini = findViewById(R.id.syotettyKofeiini);
        EditText syotettyHinta = findViewById(R.id.syotettyHinta);
        if(!syotettyKofeiini.getText().toString().equals("") && !syotettyHinta.getText().toString().equals("")) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat aika = new SimpleDateFormat("hh:mm");
            SimpleDateFormat pvm = new SimpleDateFormat("dd-MMM-yyyy");
            String lisaamisAika = aika.format(calendar.getTime());
            String lisaamisPvm = pvm.format(calendar.getTime());
            lisatyt.add(new LisattyTuote(Double.parseDouble(syotettyKofeiini.getText().toString()), Double.parseDouble(syotettyHinta.getText().toString()), lisaamisPvm, lisaamisAika));
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(lisatyt);
            editor.putString("Lisatyt tuotteet", json);
            editor.apply();
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Täytä kaikki kentät!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
    public void lisaaValittuTuote(View view){
            SharedPreferences sharedPreferences2 = getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson2 = new Gson();
            String json2 = sharedPreferences2.getString("Lisatyt tuotteet", null);
            Type type = new TypeToken<ArrayList<LisattyTuote>>() {
            }.getType();
            lisatyt = gson2.fromJson(json2, type);
            if (lisatyt == null) {
                lisatyt = new ArrayList<>();
            }
            if(klikattuTuote > -1 ) {
                Double kofeiini = tallennetut.get(klikattuTuote).getKofeiini();
                Double hinta = tallennetut.get(klikattuTuote).getHinta();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat aika = new SimpleDateFormat("hh:mm");
                SimpleDateFormat pvm = new SimpleDateFormat("dd-MMM-yyyy");
                String lisaamisAika = aika.format(calendar.getTime());
                String lisaamisPvm = pvm.format(calendar.getTime());
                lisatyt.add(new LisattyTuote(kofeiini, hinta, lisaamisPvm, lisaamisAika));
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(lisatyt);
                editor.putString("Lisatyt tuotteet", json);
                editor.apply();
            }

    }
    public void siirryTallentamaan(View view){
        Intent intent = new Intent(this, TuotteenTallennus.class);
        startActivity(intent);
    }
}