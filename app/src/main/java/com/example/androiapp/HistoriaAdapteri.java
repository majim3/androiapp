package com.example.androiapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HistoriaAdapteri extends ArrayAdapter<LisattyTuote> {
    /**
     * Tehdään muuttujat kontekstille ja resurssille, ja tehdään lista lisätyt tuotteet joka on aluksi null
     */
    Context context;
    int resurource;
    ArrayList<LisattyTuote> lisatyt = null;


    /**
     * Tehdään konstruktori historia adapterille, jolle pitää syöttää context, resource ja lisattytuote lista
     * @param context Contextin muuttuja
     * @param resurource resuourcen muuttuja
     * @param lisatyt lisatyt listan muuttuja
     */
    public HistoriaAdapteri(Context context, int resurource, ArrayList<LisattyTuote> lisatyt){
        super(context, resurource, lisatyt);
        this.context = context;
        this.resurource = resurource;
        this.lisatyt = lisatyt;
    }


    /**
     * Määritellään mitä adapterin tekemissä näkymissä näkyy
     * @param position syötetty position
     * @param convertView syötetty näkymä
     * @param parent syötetty Viewgroup
     * @return
     * @author Perttu Harvala
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        /**
         * Tehdään lisattytuote, jonka avulla haetaan arvot näkymien tekstinäkymille
         */
        LisattyTuote lisatty = lisatyt.get(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.historia_view, parent, false);
        }
        String paikkaTeksti = String.valueOf(position + 1);
        TextView lisatynTeksti = (TextView)convertView.findViewById(R.id.lisaysTeksti);
        TextView pvmTeksti = (TextView)convertView.findViewById(R.id.pvmTeksti);
        String kofeiiniTeksti = String.format("%.2f", lisatty.getKofeiini());
        String hintaTeksti = String.format("%.2f", lisatty.getHinta());
        kofeiiniTeksti = kofeiiniTeksti.replace(".", ",");
        hintaTeksti = hintaTeksti.replace(".", ",");
        lisatynTeksti.setText("Lisäys " + paikkaTeksti + ", Kofeiini: " + kofeiiniTeksti + " mg," + " Hinta: " + hintaTeksti + " €");
        pvmTeksti.setText("Lisätty klo: " + lisatty.getKlo() + " | " + lisatty.getPvm());

        return convertView;
    }
}
