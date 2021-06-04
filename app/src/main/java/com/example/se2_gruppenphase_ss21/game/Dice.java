package com.example.se2_gruppenphase_ss21.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se2_gruppenphase_ss21.R;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

public class Dice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imagesAnimation.stop();
                imagesAnimationtiles.stop();
                imagesAnimationtiles2.stop();
                imagesAnimationtiles3.stop();
                try {
                    test();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        }, 3000);

    }



    private void test() throws IOException, SAXException, ParserConfigurationException {
        TextView ergebnis = findViewById(R.id.Ergebnis);
        ImageView bildergebnis = findViewById(R.id.diceresult);

        int zahl = (int) (Math.random() * 6 + 1);
        switch (zahl) {
            case 1:
                ergebnis.setText("Löwe");
                Toast.makeText(this, "Lion", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.lion);
                tiles("Lion",0);
                break;
            case 2:
                ergebnis.setText("Hand");
                Toast.makeText(this, "Hand", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.hand);
                tiles("Hand",0);
                break;
            case 3:
                ergebnis.setText("Antilope");
                Toast.makeText(this, "Antilope", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.antilope);
                tiles("Antilope",0);
                break;
            case 4:
                ergebnis.setText("Schlange");
                Toast.makeText(this, "Schlange", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.snake);
                tiles("Snake",0);
                break;
            case 5:
                ergebnis.setText("Elefant");
                Toast.makeText(this, "Elefant", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.elefant);
                tiles("Elefant",0);
                break;
            case 6:
                ergebnis.setText("Käfer");
                Toast.makeText(this, "Käfer", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.bug);
                tiles("Bug",0);
                break;

        }
    }
    private void tiles(String value, int cardnumber) throws ParserConfigurationException, IOException, SAXException {
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

            String[] result = processParsing(parser, value);

            int test1 = getpicturetotilenumber(result[0],tileone);
            int test2 = getpicturetotilenumber(result[1],tiletwo);
            int test3 = getpicturetotilenumber(result[2],tilethree);
            tileone.setBackgroundResource(test1);
            tiletwo.setBackgroundResource(test2);
            tilethree.setBackgroundResource(test3);
            int[] pictures = new int[6];
            pictures[0]= test1;
            pictures[1]=test2;
            pictures[2]=test3;
            pictures[3] = Integer.parseInt(result[0]);
            pictures[4] = Integer.parseInt(result[1]);
            pictures[5] = Integer.parseInt(result[2]);
            opentiles(pictures);
        } catch (XmlPullParserException e) {


        } catch (IOException e) {

        } finally {
            if(is != null)
                is.close();
        }

    }

    private int getpicturetotilenumber (String tilenumber, ImageView tilepicture) {

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

        }

        return 0;
    }

    private String[] processParsing(XmlPullParser parser, String dice) throws XmlPullParserException, IOException {

        int eventType = parser.getEventType();
        boolean card = false;
        boolean dicetype = false;
        String[] tiles = new String[3];


        while(eventType != XmlPullParser.END_DOCUMENT){


            String eltName = null;
            switch(eventType){
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if("two".equals(eltName)) {
                        System.out.println(eltName);
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


                        card = false;
                        dicetype=false;
                        return tiles;
                    }


                    break;
            }
            eventType = parser.next();
        }
        return null;
    }
    public void opentiles(int[] pictures) {
        Intent intent = new Intent(this,Tiles.class);
        Bundle a = new Bundle();
        a.putIntArray("key" , pictures);
        intent.putExtras(a);
        startActivity(intent);
    }

}
