package com.example.se2_gruppenphase_ss21;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.se2_gruppenphase_ss21.logic.tetris.Map;
import com.example.se2_gruppenphase_ss21.logic.tetris.Position;
import com.example.se2_gruppenphase_ss21.logic.tetris.Tile;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.Arrays;

public class Tiles extends AppCompatActivity {
    int [] drawables = new int[25];
    Boolean[][] map;
    Button[][] buttonarray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiles);
        try {
            InputStream is = getAssets().open("maps.xml");
            map = parsexml("two","cardnumber", is);
            Map maps = new Map();
            fillbuttonarray();
            
            Bundle b = getIntent().getExtras();
            int[] pictures = b.getIntArray("key");
            ImageView firsttile = findViewById(R.id.firsttile);
            ImageView secondtile = findViewById(R.id.secondtile);
            ImageView thirdtile = findViewById(R.id.thirdtile);
            Tile tileone = new Tile(getApplicationContext().getAssets(), pictures[3], "standard");
            Tile tiletwo = new Tile(getApplicationContext().getAssets(), pictures[4], "standard");
            Tile tilethree = new Tile(getApplicationContext().getAssets(), pictures[5], "standard");
            System.out.println(pictures[3]);
            firsttile.setBackgroundResource(pictures[0]);
            secondtile.setBackgroundResource(pictures[1]);
            thirdtile.setBackgroundResource(pictures[2]);

            Position[] test = tiletwo.getShape();
            for(Position testa: test){
                System.out.println("x:" + testa.getX());
                System.out.println("y:" + testa.getY());;
            }

            firsttile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movetiles(firsttile);
                }
            });

            secondtile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movetiles(secondtile);
                }
            });




            fill();
            int count =0;
            Button tochange;
            for(int i=0; i<map.length;i++){
                for(int j=0; j<map[i].length; j++){
                    if(map[i][j]==true){
                        tochange=findViewById(drawables[count]);
                        tochange.setBackgroundColor(Color.WHITE);

                    }
                    count++;
                }
            }


        } catch (IOException e) {

        }


    }
    protected void fillbuttonarray(){
        buttonarray = new Button[5][5];
        buttonarray[0][0] = findViewById(R.id.one);
        buttonarray[0][1]=findViewById(R.id.two);
        buttonarray[0][2] = findViewById(R.id.three);
        buttonarray[0][3] = findViewById(R.id.four);
        buttonarray[0][4] = findViewById(R.id.five);
        buttonarray[1][0] = findViewById(R.id.six);
        buttonarray[1][1] = findViewById(R.id.seven);
        buttonarray[1][2] = findViewById(R.id.eigth);
        buttonarray[1][3] = findViewById(R.id.nine);
        buttonarray[1][4] = findViewById(R.id.ten);
        buttonarray[2][0] = findViewById(R.id.eleven);
        buttonarray[2][1] = findViewById(R.id.twelve);
        buttonarray[2][2] = findViewById(R.id.thirteen);
        buttonarray[2][3] = findViewById(R.id.fourteen);
        buttonarray[2][4] = findViewById(R.id.fifteen);
        buttonarray[3][0] = findViewById(R.id.sixteen);
        buttonarray[3][1] = findViewById(R.id.seventeen);
        buttonarray[3][2] = findViewById(R.id.eighteen);
        buttonarray[3][3] = findViewById(R.id.nineteen);
        buttonarray[3][4] = findViewById(R.id.twenty);
        buttonarray[4][0] = findViewById(R.id.twentyone);
        buttonarray[4][1] = findViewById(R.id.twentytwo);
        buttonarray[4][2] = findViewById(R.id.twentythree);
        buttonarray[4][3] = findViewById(R.id.twentyfour);
        buttonarray[4][4] = findViewById(R.id.twentyfive);

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
    private View.OnClickListener movetiles(ImageView test){
        test.setBackgroundResource(0);


        return null;
    }
}