package com.example.se2_gruppenphase_ss21.game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.icu.text.Transliterator;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.se2_gruppenphase_ss21.R;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Tiles extends AppCompatActivity {
    int [] drawables = new int[25];
    boolean[][] map;
    Map currentmap;
    Button[][] buttonarray;
    Tile currenttile;
    int currentpositionx=0;
    int currentpositiony=0;
    Transliterator.Position[] tilepositions;
    boolean [][] coloredbuttons = new boolean[5][5];
    Tile[][] buttontilearray = new Tile[5][5];
    Tile empty = new Tile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiles);
        try {
            InputStream is = getAssets().open("maps.xml");
            //holt sich daten aus xml für das aussehen der map
            map= parsexml("two","cardnumber", is);
            currentmap=new Map(map);
            fillbuttonarray();
            setupcolormap();
            filltylebuttonarray();
            activateonclicklisteneronmapbuttons();
            //hole die bausteine und fuege die bilder zu den bausteinen ein
            Bundle b = getIntent().getExtras();
            int[] pictures = b.getIntArray("key");
            ImageView firsttile = findViewById(R.id.firsttile);
            ImageView secondtile = findViewById(R.id.secondtile);
            ImageView thirdtile = findViewById(R.id.thirdtile);
            Tile tileone = new Tile(getApplicationContext().getAssets(), pictures[3], "standard");
            Tile tiletwo = new Tile(getApplicationContext().getAssets(), pictures[4], "standard");
            Tile tilethree = new Tile(getApplicationContext().getAssets(), pictures[5], "standard");

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
                    movetiles(tileone, firsttile);
                }
            });

            secondtile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movetiles(tiletwo, secondtile);
                }
            });

            thirdtile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movetiles(tilethree, thirdtile);
                }
            });




            fill();
            //setzt die mapbuttons in die richtige farbe
            drawmap();


        } catch (IOException e) {

        }


    }

    protected void filltylebuttonarray(){
        for(int i = 0; i<buttontilearray.length; i++){
            for(int j =0; j<buttontilearray[i].length; j++){
                buttontilearray[i][j]=empty;
            }
        }

    }



    protected void setupcolormap(){
        for(int i = 0; i<coloredbuttons.length; i++){
            for(int j =0; j<coloredbuttons[i].length; j++){
                coloredbuttons[i][j]=false;
            }
        }
    }
    protected void drawmap(){
        int count =0;
        Button tochange;
        for(int i=0; i<map.length;i++){
            for(int j=0; j<map[i].length; j++){
                if(coloredbuttons[i][j]==false){
                    if(map[i][j]==true){
                        tochange=findViewById(drawables[count]);
                        tochange.setBackgroundColor(Color.WHITE);

                    }
                    else{
                        tochange=findViewById(drawables[count]);
                        tochange.setBackgroundColor(Color.BLACK);

                    }

                }
                count++;
            }
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
    private void activateonclicklisteneronmapbuttons(){
        for(int i = 0; i<buttonarray.length; i++){
            for(int j = 0; j<buttonarray[i].length; j++){
                buttonarray[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gettilefromid(v.getId());
                    }
                });
            }
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
    public static boolean[][] parsexml(String value, String cardnumber, InputStream is){
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            boolean[][] result = processParsing(parser, value);
            return result;

        } catch (XmlPullParserException e) {


        } catch (IOException e) {

        }
        return null;
    }
    //true=1=frei
    //false=0=belegt
    protected static boolean[][] processParsing(XmlPullParser parser, String value) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        boolean card = false;
        boolean dicetype = false;
        boolean[][] map = new boolean[5][5];


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
                        for(boolean[] x : map){
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
    private View.OnClickListener movetiles(Tile tile, ImageView tileimage){
        addonclicklistener();

        tileimage.setBackgroundResource(0);
        if(currenttile!=null){


            currenttile.attachToMap(currentmap, currentpositionx, currentpositiony);
            checkifbuttoniscolored(currenttile, currentpositionx, currentpositiony);
            placetilesintilesarray(currenttile, currentpositionx, currentpositiony);
            currentpositiony=0;
            currentpositionx=0;
        }
        currenttile = tile;
        tilepositions=currenttile.getShape();
        outerloop:
        for(int i =0; i<5; i++){
            for(int j= 0; j<5; j++){
                if(checkifplacable(i,j, tilepositions)){

                    colorbuttons(i,j, tilepositions);
                    currentpositionx=i;
                    currentpositiony = j;
                    break outerloop;
                }
            }
        }



        return null;
    }

    private void placetilesintilesarray(Tile tile, int x, int y){
        Position[] tilepositions = tile.getShape();
        for(Position c : tilepositions){
            buttontilearray[y+c.getY()][x+c.getX()]=tile;
        }
    }
    private void gettilefromid(int id){

        for(int i = 0; i<buttonarray.length; i++){
            for(int j = 0; j<buttonarray[i].length; j++){
                if(buttonarray[i][j].getId()==id){
                    checkifthereisatile( i,  j);
                }
            }
        }
    }
    private void checkifthereisatile(int i, int j){
        if(buttontilearray[i][j].getShape().length<=0){
            System.out.println("kein baustein");
        }else {
            currenttile.attachToMap(currentmap, currentpositionx, currentpositiony);
            checkifbuttoniscolored(currenttile, currentpositionx, currentpositiony);
            placetilesintilesarray(currenttile, currentpositionx, currentpositiony);
            currenttile = buttontilearray[i][j];
            tilepositions= currenttile.getShape();
            currentpositionx = currenttile.getAbsolutex();
            currentpositiony = currenttile.getAbsolutey();
            deletecolorbuttons(currenttile, currentpositionx, currentpositiony);
            System.out.println(currentpositionx+currentpositiony);

        }
    }


    protected void colorbuttons(int x, int y, Position[] tiles){
        for(int i =0; i<tiles.length; i++){


            buttonarray[y+tiles[i].getY()][x+tiles[i].getX()].setBackgroundColor(Color.BLUE);
        }
    }


    protected boolean checkifbuttoniscolored(Tile tiletofix, int currentpositionx, int currentpositiony){
        Position[] positionstocolor = tiletofix.getShape();
        for(Position a : positionstocolor){
            coloredbuttons[currentpositiony+a.getY()][currentpositionx+a.getX()]= true;
        }
        return true;
    }
    protected void deletecolorbuttons(Tile tile, int currentpositionx, int currentpositiony){
        Position[] positionstocolor = tile.getShape();
        for(Position a : positionstocolor){
            coloredbuttons[currentpositiony+a.getY()][currentpositionx+a.getX()]= false;
        }
    }

    private boolean checkifplacable(int x, int y, Position[] tilepositions){
        currenttile.setMap(currentmap);
        for(int i = 0; i< tilepositions.length; i++) {
            if(x+tilepositions[i].getX()<0||y+tilepositions[i].getY()<0||x+tilepositions[i].getX()>=5||y+tilepositions[i].getY()>=5){
                return false;
            }
            else if (!currentmap.getBox(x+tilepositions[i].getX(), y+tilepositions[i].getY()).isAvailable()) {
                return false;
            }
        }
        return true;
    }

    private void addonclicklistener(){
        Button up = findViewById(R.id.tileup);
        Button down = findViewById(R.id.tiledown);
        Button left = findViewById(R.id.tileleft);
        Button right = findViewById(R.id.tileright);
        Button turnright = findViewById(R.id.turnrigth);
        Button turnleft = findViewById(R.id.turnleft);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetileup();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetiledown();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetileleft();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetileright();
            }
        });
        turnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turntileleft();
            }
        });

        turnright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turntileright();
            }
        });

    }
    private void movetileup(){
        if(checkifplacable(currentpositionx, currentpositiony-1, tilepositions)){
            drawmap();
            colorbuttons(currentpositionx, currentpositiony-1, tilepositions);
            currentpositiony--;

        }

    }
    private void movetiledown(){

        if(checkifplacable(currentpositionx, currentpositiony+1, tilepositions)){
            drawmap();
            colorbuttons(currentpositionx, currentpositiony+1, tilepositions);
            currentpositiony++;

        }



    }
    private void movetileleft(){

        if(checkifplacable(currentpositionx-1, currentpositiony, tilepositions)){
            drawmap();
            colorbuttons(currentpositionx-1, currentpositiony, tilepositions);
            currentpositionx--;

        }


    }
    private void movetileright(){

        if(checkifplacable(currentpositionx+1, currentpositiony, tilepositions)){
            drawmap();
            colorbuttons(currentpositionx+1, currentpositiony, tilepositions);
            currentpositionx++;

        }

    }

    private void turntileright(){
        currenttile.rotateRight();
        tilepositions=currenttile.getShape();
        if(checkifplacable(currentpositionx,currentpositiony,tilepositions)){
            drawmap();
            colorbuttons(currentpositionx,currentpositiony,tilepositions);
        }


    }
    private void turntileleft(){
        currenttile.rotateLeft();
        tilepositions=currenttile.getShape();
        if(checkifplacable(currentpositionx,currentpositiony,tilepositions)){
            drawmap();
            colorbuttons(currentpositionx,currentpositiony,tilepositions);
        }

    }
}