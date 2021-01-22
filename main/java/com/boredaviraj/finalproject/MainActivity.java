package com.boredaviraj.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    MediaPlayer soundeffects;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_REQUEST2 = 1889;
    public static long diamonds = 0;
    public static String pickaxe = "none";
    public static String axe = "none";
    public static int pickaxeMiners = 0;
    public static int axeMiners = 0;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //music
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.finalprojmusic);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        //permissions
        requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST);
        //shared preferences
        sp = getSharedPreferences("prefs" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(sp.contains("diamonds")){
            diamonds = sp.getLong("diamonds",0);
        }
    }

    public void updateBackground(View view) {
        if(diamonds >= 100000000000000L) {
            //open camera
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
            //get picture and set background - DONE in onactivityresult
            //deduct gold
            diamonds -= 100000000000000L;

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//sets background
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1888){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView bg = (ImageView)findViewById(R.id.backgroundimage);
            bg.setImageBitmap(photo);
        }
        else if(requestCode==1889){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView player = (ImageView)findViewById(R.id.playerimage);
            player.setImageBitmap(photo);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        TextView diamondstext = findViewById(R.id.diamondsText);
        diamondstext.setText("Diamonds: "+""+diamonds);
        final Handler handler = new Handler();
        final int delay = 10000; // 10 seconds delay

        handler.postDelayed(new Runnable() {
            public void run() {

                //if miners present, give diamonds based on pickaxe level
                if(pickaxeMiners>0){
                    switch (pickaxe){
                        case "none":
                            break;
                        case "wooden":
                            diamonds += (long)pickaxeMiners*1L;
                            break;
                        case "stone":
                            diamonds += (long)pickaxeMiners*10L;
                            break;
                        case "iron":
                            diamonds += (long)pickaxeMiners*25000L;
                            break;
                        case "diamond":
                            diamonds += (long)pickaxeMiners*2500000000L;
                            break;
                    }
                }
                if(axeMiners>0){
                    switch (axe){
                        case "none":
                            break;
                        case "wooden":
                            diamonds += (long)axeMiners*1L;
                            break;
                        case "stone":
                            diamonds += (long)axeMiners*10L;
                            break;
                        case "iron":
                            diamonds += (long)axeMiners*25000L;
                            break;
                        case "diamond":
                            diamonds += (long)axeMiners*2500000000L;
                            break;
                    }
                }
                TextView diamondstext = findViewById(R.id.diamondsText);
                diamondstext.setText("Diamonds: "+""+diamonds);

                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void openMinersMenu(View view) {
        Intent intent = new Intent(this, MinersActivity.class);
        startActivity(intent);
    }

    public void upgradePickaxe(View view) {
        if(pickaxe.equals("none")){//upgrade to wooden
            pickaxe = "wooden";
            Button b = (Button)findViewById(R.id.buttonPickaxe);
            b.setText("Buy Stone");
            ImageButton pick = (ImageButton)findViewById(R.id.buttonUsePickaxe);
            pick.setImageResource(R.drawable.wooden_pickaxe);
            TextView pickaxecosttext = findViewById(R.id.pickaxeCostText);
            pickaxecosttext.setText("Cost: 100");

        }
        else if (pickaxe.equals("wooden") && diamonds >= 100L){//upgrade to stone
            pickaxe = "stone";
            Button b = (Button)findViewById(R.id.buttonPickaxe);
            b.setText("Buy Iron");
            diamonds -= 100L;
            ImageButton pick = (ImageButton)findViewById(R.id.buttonUsePickaxe);
            pick.setImageResource(R.drawable.stone_pickaxe);
            TextView pickaxecosttext = findViewById(R.id.pickaxeCostText);
            pickaxecosttext.setText("Cost: 100000");
        }
        else if (pickaxe.equals("stone") && diamonds >= 100000L){//upgrade to iron
            pickaxe = "iron";
            Button b = (Button)findViewById(R.id.buttonPickaxe);
            b.setText("Buy Diamond");
            diamonds -= 100000L;
            ImageButton pick = (ImageButton)findViewById(R.id.buttonUsePickaxe);
            pick.setImageResource(R.drawable.iron_pickaxe);
            TextView pickaxecosttext = findViewById(R.id.pickaxeCostText);
            pickaxecosttext.setText("Cost: 10000000000");
        }
        else if (pickaxe.equals("iron") && diamonds >= 10000000000L){//upgrade to diamond
            pickaxe = "diamond";
            Button b = (Button)findViewById(R.id.buttonPickaxe);
            b.setText("Maxed Out");
            diamonds -= 10000000000L;
            ImageButton pick = (ImageButton)findViewById(R.id.buttonUsePickaxe);
            pick.setImageResource(R.drawable.diamond_pickaxe);
            TextView pickaxecosttext = findViewById(R.id.pickaxeCostText);
            pickaxecosttext.setText("MAXED OUT");
        }
        TextView diamondstext = findViewById(R.id.diamondsText);
        diamondstext.setText("Diamonds: "+""+diamonds);
    }

    public void usePickaxe(View view) {
        if(pickaxe.equals("wooden")){
            diamonds+=1L;
        }
        else if(pickaxe.equals("stone")){
            diamonds += 10L;
        }
        else if(pickaxe.equals("iron")){
            diamonds += 25000L;
        }
        else if(pickaxe.equals("diamond")){
            diamonds += 2500000000L;
        }
        TextView diamondstext = findViewById(R.id.diamondsText);
        diamondstext.setText("Diamonds: "+""+diamonds);
        //play sound effect
        if(!pickaxe.equals("none")){
            double d = Math.random();
            if(d<.5){
                soundeffects = MediaPlayer.create(getApplicationContext(), R.raw.pickaxe1);
                soundeffects.start();
            }
            else{
                soundeffects = MediaPlayer.create(getApplicationContext(), R.raw.pickaxe2);
                soundeffects.start();
            }
        }
    }

    public void upgradeAxe(View view) {
        if(axe.equals("none")){//upgrade to wooden
            axe = "wooden";
            Button b = (Button)findViewById(R.id.buttonAxe);
            b.setText("Buy Stone");
            ImageButton axe = (ImageButton)findViewById(R.id.buttonUseAxe);
            axe.setImageResource(R.drawable.wooden_axe);
            TextView axecosttext = findViewById(R.id.axeCostText);
            axecosttext.setText("Cost: 100");
        }
        else if (axe.equals("wooden") && diamonds >= 100L){//upgrade to stone
            axe = "stone";
            Button b = (Button)findViewById(R.id.buttonAxe);
            b.setText("Buy Iron");
            diamonds -= 100L;
            ImageButton axe = (ImageButton)findViewById(R.id.buttonUseAxe);
            axe.setImageResource(R.drawable.stone_axe);
            TextView pickaxecosttext = findViewById(R.id.pickaxeCostText);
            pickaxecosttext.setText("Cost: 100000");
        }
        else if (axe.equals("stone") && diamonds >= 100000L){//upgrade to iron
            axe = "iron";
            Button b = (Button)findViewById(R.id.buttonAxe);
            b.setText("Buy Diamond");
            diamonds -= 100000L;
            ImageButton axe = (ImageButton)findViewById(R.id.buttonUseAxe);
            axe.setImageResource(R.drawable.iron_axe);
            TextView pickaxecosttext = findViewById(R.id.pickaxeCostText);
            pickaxecosttext.setText("Cost: 10000000000");
        }
        else if (axe.equals("iron") && diamonds >= 10000000000L){//upgrade to diamond
            axe = "diamond";
            Button b = (Button)findViewById(R.id.buttonAxe);
            b.setText("Maxed Out");
            diamonds -= 10000000000L;
            ImageButton axe = (ImageButton)findViewById(R.id.buttonUseAxe);
            axe.setImageResource(R.drawable.diamond_axe);
            TextView pickaxecosttext = findViewById(R.id.pickaxeCostText);
            pickaxecosttext.setText("MAXED OUT");
        }
        TextView diamondstext = findViewById(R.id.diamondsText);
        diamondstext.setText("Diamonds: "+""+diamonds);
    }

    public void useAxe(View view) {
        if(axe.equals("wooden")){
            diamonds+=1L;
        }
        else if(axe.equals("stone")){
            diamonds += 10L;
        }
        else if(axe.equals("iron")){
            diamonds += 25000L;
        }
        else if(axe.equals("diamond")){
            diamonds += 2500000000L;
        }
        TextView diamondstext = findViewById(R.id.diamondsText);
        diamondstext.setText("Diamonds: "+""+diamonds);
        //play sound effect
        if(!axe.equals("none")){
            double d = Math.random();
            if(d<.5){
                soundeffects = MediaPlayer.create(getApplicationContext(), R.raw.axe1);
                soundeffects.start();
            }
            else{
                soundeffects = MediaPlayer.create(getApplicationContext(), R.raw.axe2);
                soundeffects.start();
            }
        }
    }

    public void cheat(View view) {
        diamonds += 500000000000000L;
    }

    public void buyNetherite(View view) {
        if(diamonds >= 1000000000000000L){
            diamonds -= 1000000000000000L;
            ImageView playerimg = findViewById(R.id.playerimage);
            playerimg.setImageResource(R.drawable.netheritearmor);
            TextView diamondstext = findViewById(R.id.diamondsText);
            diamondstext.setText("Diamonds: "+""+diamonds);
            soundeffects = MediaPlayer.create(getApplicationContext(), R.raw.armorequipsound);
            soundeffects.start();

        }
    }

    public void buyCustomPlayer(View view) {
        if(diamonds >= 5000000000000000L){
            diamonds -= 5000000000000000L;
            TextView diamondstext = findViewById(R.id.diamondsText);
            diamondstext.setText("Diamonds: "+""+diamonds);
            //open camera
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST2);
            //get picture and set customplayer - DONE in onactivityresult
        }
    }
}