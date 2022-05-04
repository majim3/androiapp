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
    /**
     * Tehdään muuttujat klikattutuote ja pitkaanklikattu, jotka kertoo mitä osaa listviewissä klikattiin tai pitkään klikattiin
     */
    private int klikattuTuote;
    private int pitkaanKlikattu;

    /**
     * Tehdään listat tallennetuille tuotteille ja lisätyille tuotteille
     */
    ArrayList<TallennettuTuote> tallennetut;
    ArrayList<LisattyTuote> lisatyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kofeiinin_lisays);

        /**
         * Haetaan sharedpreferenceistä tallennettujen tuotteiden lista ja tehdään uusi lista jos sitä ei ole
         * @author Perttu Harvala
         */
        SharedPreferences sharedPreferences2 = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = sharedPreferences2.getString("Tallennetut tuotteet", null);
        Type type = new TypeToken<ArrayList<TallennettuTuote>>() {}.getType();
        tallennetut = gson2.fromJson(json2, type);
        if(tallennetut == null){
            tallennetut = new ArrayList<>();
        }


        /**
         * Katsotaan onko intentiä, ja jos on niin luodaan tuotteentallennuksesta tulleitten extrojen avulla uusi tallennettu tuote
         * Tehdään myös ilmoitus, että kyseisen tuotteen tallennus onnistui
         */
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
                Context context = getApplicationContext();
                CharSequence text = "Tuotteen: " + tallennettavanNimi + " tallennus onnistui!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }

        /**
         * Tehdään adapteri listviewille, joka käyttää simple list item 1 layoutia ja tallennetut listaa
         */
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tallennetut);
        ListView lv = (ListView)findViewById(R.id.tallennetutTuotteetLista);
        lv.setAdapter(arrayAdapter);

        /**
         * Tehdään klikkauksen kuuntelijat listviewin osille ja niitä klikatessa kohtaa vastaavan tallennuksen nimi menee valituntuotteen tekstiksi
         * ja klikattutuote saa arvokseen sen kohdan eli i
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView valittuTuote = findViewById(R.id.valitunTuotteenTeksti);
                valittuTuote.setText(tallennetut.get(i).getNimi());
                klikattuTuote = i;
            }
        });


        /**
         * Tehdään pitkänklikkauksen kuuntelija, jossa annetaan pitkaanklikatulle arvoksi klikatun sijainti eli i
         * sitten tehdään varoitusnäkymä jossa kysytään halutaanko kyseinen tallennettu tuote poistaa
         * ja jos painaa kyllä niin se poistetaan
         */
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


    /**
     * Tehdään metodi lisaaSyotettytuote joka lisää lisättyjen listaan kofeiininkäyttökerran, joka saa arvoikseen edittexteistä hinnan ja kofeiinimäärän
     * @param view view on klikattu näkymö
     */
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
        String syotettyHintaString = syotettyHinta.getText().toString();
        String syotettyKofeiiniString = syotettyKofeiini.getText().toString();
        /**
         * katsotaan onko editteksteissä tekstiä, ja onko ne luvut doubleja
         */
        if(!syotettyKofeiini.getText().toString().equals("") && !syotettyHinta.getText().toString().equals("")) {
            Boolean onkoDouble = true;

            if(syotettyHintaString.indexOf(",") != -1){
                syotettyHintaString = syotettyHintaString.replace(",", ".");
            }
            if(syotettyKofeiiniString.indexOf(",") != -1){
                syotettyKofeiiniString = syotettyKofeiiniString.replace(",", ".");
            }
            try {
                Double num = Double.parseDouble(syotettyHintaString);
                Double num2 = Double.parseDouble(syotettyKofeiiniString);
            } catch (NumberFormatException e) {
                onkoDouble = false;
            }
            if(onkoDouble){
                /**
                 * Jos syöte on kelvollinen, niin vielä ennen sharedpreferencen lisätyt listaan tallentamista määritellään lisätylle sen lisäysaika
                 */
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat aika = new SimpleDateFormat("hh:mm");
                SimpleDateFormat pvm = new SimpleDateFormat("dd-MMM-yyyy");
                String lisaamisAika = aika.format(calendar.getTime());
                String lisaamisPvm = pvm.format(calendar.getTime());
                lisatyt.add(new LisattyTuote(Double.parseDouble(syotettyKofeiiniString), Double.parseDouble(syotettyHintaString), lisaamisPvm, lisaamisAika));
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(lisatyt);
                editor.putString("Lisatyt tuotteet", json);
                editor.apply();

                /**
                 * Sitten viedään käyttäjä takaisin mainactivityyn ja annetaan intent, joka kertoo että lisäys onnistui
                 * jonka avulla mainactivityssä tulee ilmoitus onnistuneesta lisäyksestä
                 */
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("LisaysOnnistui", true);
                startActivity(intent);
            }
            else {
                Context context = getApplicationContext();
                CharSequence text = "Pisteitä/Pilkkuja ei saa olla kuin 1!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Täytä kaikki kentät!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * Aikalailla sama metodi, kuin äsken, mutta nyt lisätty saa arvooikseen tallennetun tuotteen arvot
     * @param view view on painettu näkymä
     */
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
            TextView valittuTuote = findViewById(R.id.valitunTuotteenTeksti);

            if(klikattuTuote > -1 && tallennetut.size() > 0 && !valittuTuote.getText().equals("")) {
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
                Intent intent = new Intent(this, MainActivity.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_LISAYS", "Lisätty");
                intent.putExtras(extras);
                startActivity(intent);
            }

    }

    /**
     * Metodi joka vie tuotteentallennus aktiviteettiin
     * @param view view on näkymä jota klikattiin
     */
    public void siirryTallentamaan(View view){
        Intent intent = new Intent(this, TuotteenTallennus.class);
        startActivity(intent);
    }
}