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


public class Alku extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "..............";
    private TextView teksti;
    private String date;
    private EditText nimi;
    private EditText ika;
    private DatePickerDialog.OnDateSetListener paivamaara;
    public LocalDate pvm;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nimi = (EditText) findViewById(R.id.editTextTextPersonName);
        ika = (EditText) findViewById(R.id.editTextTextPersonName2);
        ika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar kalenteri = Calendar.getInstance();
                int vuosi = kalenteri.get(Calendar.YEAR);
                int kuukausi = kalenteri.get(Calendar.MONTH);
                int paiva = kalenteri.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog date = new DatePickerDialog(Alku.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, paivamaara, vuosi, kuukausi, paiva);

                date.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                date.show();



            }
        });

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void klikattu(View view) {

        teksti = (TextView)findViewById(R.id.textView3);
        TextView teksti2 = (TextView)findViewById(R.id.textView2);
        teksti.setText("");
        teksti2.setText("");
        EditText nimi = (EditText) findViewById(R.id.editTextTextPersonName);
        String viesti = nimi.getText().toString();

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
        startIntent();
    }
    public void startIntent() {
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        String viesti = nimi.getText().toString();
        String age = ika.getText().toString();

        intent.putExtra(EXTRA_MESSAGE, viesti);
        intent.putExtra(EXTRA_MESSAGE2, age);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validateAge(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        return date.isBefore(currentDate.minusYears(15).minusMonths(1));
    }
}