package com.example.se2_gruppenphase_ss21.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.client.listeners.PreRoundListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;



public class Dice extends AppCompatActivity implements PreRoundListener {

    int[] pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        GameClient client = GameClient.getActiveGameClient();
        client.registerListener(this);
        Log.d("dice", "registered");
    }

    private void startAnimation(int result) {
        Maps.setcardnumbers();
        ImageView bildergebnis = findViewById(R.id.diceresult);
        ImageView tile1 = (ImageView)findViewById(R.id.tile1);
        ImageView tile2 = (ImageView)findViewById(R.id.tile2);
        ImageView tile3 = (ImageView) findViewById(R.id.tile3);
        AnimationDrawable imagesAnimation;
        AnimationDrawable imagesAnimationtiles;
        AnimationDrawable imagesAnimationtiles2;
        AnimationDrawable imagesAnimationtiles3;
        bildergebnis.setBackgroundResource(R.drawable.dice_slideshow);
        tile1.setBackgroundResource(R.drawable.tiles_slideshow);
        tile2.setBackgroundResource(R.drawable.tiles_slideshow);
        tile3.setBackgroundResource(R.drawable.tiles_slideshow);
        imagesAnimation = (AnimationDrawable) bildergebnis.getBackground();
        imagesAnimationtiles = (AnimationDrawable) tile1.getBackground();
        imagesAnimationtiles2 = (AnimationDrawable) tile2.getBackground();
        imagesAnimationtiles3 = (AnimationDrawable) tile3.getBackground();

        imagesAnimation.start();
        imagesAnimationtiles.start();
        imagesAnimationtiles2.start();
        imagesAnimationtiles3.start();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            imagesAnimation.stop();
            imagesAnimationtiles.stop();
            imagesAnimationtiles2.stop();
            imagesAnimationtiles3.stop();
            try {
                test(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 3000);
    }



    private void test(int result) throws IOException {
        TextView ergebnis = findViewById(R.id.Ergebnis);
        ImageView bildergebnis = findViewById(R.id.diceresult);
        int cardnumber = (int)(Math.random() * 36) + 1;
        String lion = "Lion";
        String hand = "Hand";
        String antilope = "Antilope";
        String snake = "Snake";
        String elefant = "Elefant";
        String bug = "Bug";

        switch (result) {
            case 1:
                ergebnis.setText(lion);
                Toast.makeText(this, lion, Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.lion);
                tiles(lion,cardnumber);
                break;
            case 2:
                ergebnis.setText(hand);
                Toast.makeText(this, hand, Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.hand);
                tiles(hand,cardnumber);
                break;
            case 3:
                ergebnis.setText(antilope);
                Toast.makeText(this, antilope, Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.antilope);
                tiles(antilope,cardnumber);
                break;
            case 4:
                ergebnis.setText(snake);
                Toast.makeText(this, snake, Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.snake);
                tiles(snake,cardnumber);
                break;
            case 5:
                ergebnis.setText(elefant);
                Toast.makeText(this, elefant, Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.elefant);
                tiles(elefant,cardnumber);
                break;
            case 6:
                ergebnis.setText(bug);
                Toast.makeText(this, bug, Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.bug);
                tiles(bug,cardnumber);
                break;
            default:

        }
    }
    private void tiles(String value, int cardnumber) throws IOException{
        ImageView tileone= findViewById(R.id.tile1);
        ImageView tiletwo = findViewById(R.id.tile2);
        ImageView tilethree = findViewById(R.id.tile3);
        XmlPullParserFactory parserFactory;
        InputStream is = null;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            is = getAssets().open("solutions.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            String cardnumberinwords = Maps.cardnumbers[cardnumber];
            System.out.println(cardnumberinwords + value);
            String[] result = processParsing(parser, value, cardnumberinwords);
            System.out.println(result.toString());
            int test1 = getpicturetotilenumber(result[0]);
            int test2 = getpicturetotilenumber(result[1]);
            int test3 = getpicturetotilenumber(result[2]);
            tileone.setBackgroundResource(test1);
            tiletwo.setBackgroundResource(test2);
            tilethree.setBackgroundResource(test3);
            pictures = new int[7];
            pictures[0]= test1;
            pictures[1]=test2;
            pictures[2]=test3;
            pictures[3] = Integer.parseInt(result[0]);
            pictures[4] = Integer.parseInt(result[1]);
            pictures[5] = Integer.parseInt(result[2]);
            pictures[6] = cardnumber;
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();

        } finally {
            if(is != null)
                is.close();
        }

    }

    private int getpicturetotilenumber (String tilenumber) {

        switch (tilenumber) {
            case "1":
                return R.drawable.red;
            case "2":
                return R.drawable.yellow;
            case "3":
                return R.drawable.brown;
            case "4":
                return R.drawable.darkgreen;
            case "5":
                return R.drawable.greenl;
            case "6":
                return R.drawable.redl;
            case "7":
                return R.drawable.turquoise;
            case "8":
                return R.drawable.brownishyellow;
            case "9":
                return R.drawable.darkred;
            case "10":
                return R.drawable.black;
            case "11":
                return R.drawable.blue;
            case "12":
                return R.drawable.darkblue;
            default:

        }

        return 0;
    }

    private String[] processParsing(XmlPullParser parser, String dice, String cardnumber) throws XmlPullParserException, IOException {

        int eventType = parser.getEventType();
        boolean card = false;
        boolean dicetype = false;
        String[] tiles = new String[3];


        while(eventType != XmlPullParser.END_DOCUMENT){


            String eltName = null;
            switch(eventType){
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if(cardnumber.equals(eltName)) {
                        card = true;
                        eventType = parser.next();
                        eltName = parser.getName();
                    }

                    if(dice.equals(eltName)&&card) {
                        dicetype = true;
                        eventType = parser.next();
                        eltName = parser.getName();
                    }
                    if("Tile".equals(eltName)&&dicetype&&card){
                        tiles[0] = parser.getAttributeValue(0);
                        tiles[1] = parser.getAttributeValue(1);
                        tiles[2] = parser.getAttributeValue(2);


                        return tiles;
                    }


                    break;
                default:
            }
            eventType = parser.next();
        }
        return tiles;
    }
    public void opentiles() {
        Intent intent = new Intent(this,Tiles.class);
        Bundle a = new Bundle();
        a.putIntArray("key" , pictures);
        intent.putExtras(a);
        startActivity(intent);
    }

    @Override
    public void playDiceAnimation(int result) {
        runOnUiThread(() -> startAnimation(result));
    }

    @Override
    public void transitionToPuzzle() {
        opentiles();
    }

    @Override
    public void userDisconnect(String nickname) {
        runOnUiThread(() ->
                Toast.makeText(this, "Player "+nickname+" disconnected!", Toast.LENGTH_LONG).show()
        );
    }

    @Override
    public void unknownMessage(String message) {
        runOnUiThread(() ->
                Toast.makeText(this, "Network error: "+message, Toast.LENGTH_LONG).show()
        );
    }
}

