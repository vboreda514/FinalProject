package com.boredaviraj.finalproject;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MinersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miners);
        TextView diamondstext = findViewById(R.id.diamondsText);
        diamondstext.setText(""+ MainActivity.diamonds);
        TextView minerpickaxecost =findViewById(R.id.minerPickaxeCostText);
        minerpickaxecost.setText("Cost: " + "" + (Math.pow(10,(1+MainActivity.pickaxeMiners))));
        TextView mineraxecost =findViewById(R.id.minerAxeCostText);
        mineraxecost.setText("Cost: " + "" + (Math.pow(10,(1+MainActivity.axeMiners))));
    }

    public void openMainMenu(View view) {
        finish();
        return;
    }

    public void buyPickaxeMiner(View view) {
        long cost = (long)(Math.pow(10,(1+MainActivity.pickaxeMiners)));
        if(MainActivity.diamonds>=cost){
            MainActivity.diamonds-=cost;
            MainActivity.pickaxeMiners++;
            TextView diamondstext = findViewById(R.id.diamondsText);
            diamondstext.setText("Diamonds: "+""+MainActivity.diamonds);
            //update cost
            TextView minerpickaxecost =findViewById(R.id.minerPickaxeCostText);
            minerpickaxecost.setText("Cost: " + "" + (Math.pow(10,(1+MainActivity.pickaxeMiners))));
        }
    }

    public void buyAxeMiner(View view) {
        long cost = (long)(Math.pow(10,(1+MainActivity.axeMiners)));
        if(MainActivity.diamonds>=cost){
            MainActivity.diamonds-=cost;
            MainActivity.axeMiners++;
            TextView diamondstext = findViewById(R.id.diamondsText);
            diamondstext.setText("Diamonds: "+""+MainActivity.diamonds);
            //update cost
            TextView mineraxecost =findViewById(R.id.minerAxeCostText);
            mineraxecost.setText("Cost: " + "" + (Math.pow(10,(1+MainActivity.axeMiners))));
        }
    }
}