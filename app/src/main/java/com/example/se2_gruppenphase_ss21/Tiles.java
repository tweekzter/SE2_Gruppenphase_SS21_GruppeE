package com.example.se2_gruppenphase_ss21;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.se2_gruppenphase_ss21.logic.tetris.Map;
import com.example.se2_gruppenphase_ss21.logic.tetris.Tile;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

public class Tiles extends AppCompatActivity {
    int [] drawables = new int[25];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiles);
        try {
            InputStream is = getAssets().open("maps.xml");
            Boolean[][] map = parsexml("two","tet", is);
            Map maps = new Map();
            Tile tile = new Tile();

            //
            //attachtomap
            Bundle b = getIntent().getExtras();
            int[] pictures = b.getIntArray("key");
            ImageView firsttile = findViewById(R.id.firsttile);
            ImageView secondtile = findViewById(R.id.secondtile);
            ImageView thirdtile = findViewById(R.id.thirdtile);
            firsttile.setBackgroundResource(pictures[0]);
            secondtile.setBackgroundResource(pictures[1]);
            thirdtile.setBackgroundResource(pictures[2]);


            fill();
            int count =0;
            Button tochange;
            for(int i=0; i<map.length;i++){
                for(int j=0; j<map[i].length; j++){
                    if(map[i][j]==true){
                        tochange=findViewById(drawables[count]);
                        tochange.setBackgroundColor(Color.WHITE);
                        //tochange.
                    }
                    count++;
                }
            }


        } catch (IOException e) {

        }


    }
    private void fill(){
        drawables[0]=R.id.one;
        drawables[1]=R.id.two;
        drawables[2] = R.id.three;
        drawables[3]=R.id.four;
        drawables[4] = R.id.five;
        drawables[5] = R.id.six;
        drawables[6] = R.id.seven;
        drawables[7] = R.id.eigth;
        drawables[8] = R.id.nine;
        drawables[9] = R.id.ten;
        drawables[10] = R.id.eleven;
        drawables[11] = R.id.twelve;
        drawables[12] = R.id.thirteen;
        drawables[13] = R.id.fourteen;
        drawables[14] = R.id.fifteen;
        drawables[15] = R.id.sixteen;
        drawables[16] = R.id.seventeen;
        drawables[17]= R.id.eighteen;
        drawables[18] = R.id.nineteen;
        drawables[19] = R.id.twenty;
        drawables[20] = R.id.twentyone;
        drawables[21] = R.id.twentytwo;
        drawables[22] = R.id.twentythree;
        drawables[23] = R.id.twentyfour;
        drawables[24] = R.id.twentyfive;

    }
    public static Boolean[][] parsexml(String value, String cardnumber, InputStream is){
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            Boolean[][] result = processParsing(parser, value);
            return result;

        } catch (XmlPullParserException e) {


        } catch (IOException e) {

        }
        return null;
    }
    //true=1=frei
    //false=0=belegt
    protected static Boolean[][] processParsing(XmlPullParser parser, String value) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        boolean card = false;
        boolean dicetype = false;
        Boolean[][] map = new Boolean[5][5];


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

                    if("firstr".equals(eltName)&&card) {
                        card = false;

                        for(int j =0; j<5; j++) {

                            for (int i = 0; i < 5; i++) {
                                if ("0".equals(parser.getAttributeValue(i))) {
                                    map[j][i]=false;
                                }
                                else {
                                    map[j][i]=true;
                                }
                            }
                            eventType = parser.next();
                            eventType=parser.next();
                            eventType=parser.next();

                            eltName = parser.getName();

                        }
                        for(Boolean[] x : map){
                            for(Boolean y: x){
                                System.out.print(y);
                            }
                            System.out.println();
                        }

                        return map;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return null;

    }
}