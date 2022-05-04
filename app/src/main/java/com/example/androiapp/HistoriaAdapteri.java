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
    Context context;
    int resurource;
    ArrayList<LisattyTuote> lisatyt = null;

    public HistoriaAdapteri(Context context, int resurource, ArrayList<LisattyTuote> lisatyt){
        super(context, resurource, lisatyt);
        this.context = context;
        this.resurource = resurource;
        this.lisatyt = lisatyt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LisattyTuote lisatty = lisatyt.get(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.historia_view, parent, false);
        }
        String paikkaTeksti = String.valueOf(position + 1);
        TextView lisatynTeksti = (TextView)convertView.findViewById(R.id.lisaysTeksti);
        TextView pvmTeksti = (TextView)convertView.findViewById(R.id.pvmTeksti);
        String kofeiiniTeksti = String.valueOf(lisatty.getKofeiini());
        String hintaTeksti = String.valueOf(lisatty.getHinta());
        lisatynTeksti.setText("Lisäys " + paikkaTeksti + ", Kofeiini: " + kofeiiniTeksti + " mg," + " Hinta: " + hintaTeksti + " €");
        pvmTeksti.setText("Lisätty klo: " + lisatty.getKlo() + " | " + lisatty.getPvm());

        return convertView;
    }
}