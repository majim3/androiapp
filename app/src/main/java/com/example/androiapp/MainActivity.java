package com.example.androiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView fakta;
    private int rand;
    private String[] faktoja;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView teksti = (TextView) findViewById(R.id.saldo);
        fakta = (TextView) findViewById(R.id.faktaText) ;
        faktoja = new String[] {"Caffein may result in hedaches.", "Dependency, so you need to take more of it to get the same results.",
                                "Did you know that caffein causes faster heart rate","Consuming leo caues dehydration",
                                "Some say that caffein causes restlessness and shakiness when consumed in large quantities","Caffein may cause in some cases of anxiety",
                                "Caffein also causes Dependency, so you need to take more of it to get the same effects",
                                "Caffein also causes your intestines to work faster"};



    }

    public void faktaNappi(View v){
        rand = (int) (Math.random() * 8);
        fakta.setText(faktoja[rand]);


    }


}