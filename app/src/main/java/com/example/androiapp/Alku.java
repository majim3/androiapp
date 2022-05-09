package com.example.androiapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * Tässä on tapahtuma missä käyttäjä syöttää tietonsa ja sovellus ottaa ne talteen.
 * @author Kaarle Häyhä
 */

public class Alku extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "..............";
    private TextView teksti;
    private String date;
    private EditText nimi;
    private EditText ika;
    private DatePickerDialog.OnDateSetListener paivamaara;
    public  LocalDate pvm;

    /**
     * OnCreate metodi
     * @param savedInstanceState savedInstanceState
     */





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         *Alhaalla asetetaan onClick tapahtuma "ika" kohtaan missä avautuu kalenteri, josta käyttäjä voi valita oman syntymäpäivänsä..
         */


        setContentView(R.layout.activity_alku);
        nimi = (EditText) findViewById(R.id.editTextTextPersonName);
        ika = (EditText) findViewById(R.id.editTextTextPersonName2);
        ika.setOnClickListener(new View.OnClickListener() {
            /*
             * onClick tapahtuma missä avautuu kalenteri
             */
            @Override
            public void onClick(View view) {
                Calendar kalenteri = Calendar.getInstance();
                int vuosi = kalenteri.get(Calendar.YEAR);
                int kuukausi = kalenteri.get(Calendar.MONTH);
                int paiva = kalenteri.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog date = new DatePickerDialog(Alku.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, paivamaara, vuosi, kuukausi, paiva);

                date.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                date.show();

                /*
                 * Ylempänä määritetään ja luodaan kalenterinäkymä
                 */



            }
        });

        /**
         * Alempana määritetaan ja luodaan päivämäärä date "String" ja pvm "Date" joita voidaan käyttää myöhemmin.
         */

        paivamaara = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                pvm = LocalDate.of(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                date = month + "/" + day + "/" + year;
                ika.setText(date);
            }
        };

    }


    /**
     * Tässä tehdään onClick tapahtuma "valmis" napille minkä jälkeen sovellus ottaa tiedot talteen ja vie käyttäjän toiseen aktiviteettiin.
     * @param view on mikä ottaa vastaan onClickeventin
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void klikattu(View view) {

        teksti = (TextView)findViewById(R.id.textView3);
        TextView teksti2 = (TextView)findViewById(R.id.textView2);
        teksti.setText("");
        teksti2.setText("");
        EditText nimi = (EditText) findViewById(R.id.editTextTextPersonName);
        String viesti = nimi.getText().toString();

        /*
         * Alempana ehtolausekkeet minkä avulla voidaan varmistaa ettei käyttäjä pääse läpi jos hän syöttää virheelliset tiedot.
         */

        if(viesti.equals("")) {
            teksti2.setText("Kentät eivät voi olla tyhjiä!");
            return;
        }
        if (pvm == null) {
            teksti.setText("Ikä ei voi olla tyhjä!");
            return;
        }
        if (!validateAge(pvm)) {
            teksti.setText("Ikä ei voi olla alle 15!");
            return;
        }
        /*
         Kun päästy läpi, sovellus aloittaa toisen aktiviteetin.
         */
        startIntent();
    }

    /**
     * Metodi jossa aktiviteetti käynnistyy ja käytetään SharedPreference toimintoa että saadaan käyttäjän tiedot talteen ja että niitä voidaan käyttää muualla.
     */
    public void startIntent() {
        String name = nimi.getText().toString();
        String age = ika.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("User name: ", name);
        editor.putString("User age: ", age);
        editor.apply();
        Intent intent = new Intent(Alku.this, MainActivity.class);
        startActivity(intent);

    }

    /**
     * Metodi jolla voidaan tarkistaa käyttäjän ikä vuosien erolla kalenteripäivämäärästä.
     * Huom! Metodissa käytetään myös "minusMonths(1)" koska Androidstudio aloittaa kuukaudet 0 jonka takia Tammikuu = 0 ja Helmikuu = 1 jne.
     * @param date mikä on päivämäärä
     * @return Lähetetään takaisin päivämäärä
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validateAge(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        return date.isBefore(currentDate.minusYears(15).minusMonths(1));
    }

    /**
     * Alempana luodaan ehto, missä katsotaan onko käyttäjä syöttänyt tietoja sovellukselle vai ovatko ne tyhjinä.
     * Jos tietoja ei löydy tai ole, sovellus heittää käyttäjän takaisin kohtaan missä syötetään tiedot.
     */

    protected void onStart() {
        super.onStart();



        SharedPreferences sharedPreferencesUser = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String userName = sharedPreferencesUser.getString("User name: ", null);
        String userAge = sharedPreferencesUser.getString("User age: ", null);

        if (userName != null || userAge != null) {
            Intent intent = new Intent(Alku.this, MainActivity.class);
            startActivity(intent);
        }
    }
}