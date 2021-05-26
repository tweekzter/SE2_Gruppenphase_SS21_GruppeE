package com.example.se2_gruppenphase_ss21.game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.logic.tetris.Map;
import com.example.se2_gruppenphase_ss21.logic.tetris.Position;
import com.example.se2_gruppenphase_ss21.logic.tetris.Tile;


import java.io.IOException;
import java.io.InputStream;


public class Tiles extends AppCompatActivity {
    Tile currenttile;
    int currentpositionx=0;
    int currentpositiony=0;
    Position[] tilepositions;

    boolean[][] map;
    Map currentmap;
    Button[][] buttonarray;

    Tile[][] tilearray = new Tile[5][5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiles);
        try {
            InputStream is = getAssets().open("maps.xml");
            //holt sich daten aus xml für das aussehen der map
            map= XMLParser.parsexml("two","cardnumber", is);
            currentmap=new Map(map);

            fillbuttonarray();
            filltylearray();
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

            //sets onclicklistener on images

            firsttile.setOnClickListener(v -> movetiles(tileone, firsttile, Color.BLUE));

            secondtile.setOnClickListener(v -> movetiles(tiletwo, secondtile, Color.GREEN));

            thirdtile.setOnClickListener(v -> movetiles(tilethree, thirdtile, Color.RED));

            //zeichnet die map
            drawmap();


        } catch (IOException e) {

        }


    }

    //befuellt das tile array. tylearray dient dazu zu überprüfen ob an stelle x,y ein baustein liegt und wenn ja welcher
    protected void filltylearray(){
        Tile empty = new Tile();
        for(int i = 0; i< tilearray.length; i++){
            for(int j = 0; j< tilearray[i].length; j++){
                tilearray[i][j]=empty;
            }
        }

    }

    protected void drawmap(){

        for(int i=0; i<map.length;i++){
            for(int j=0; j<map[i].length; j++){
                // hier eventuell ein oder einfügen wenn die farbe eines gewissen tiles entspricht
                //durch buttonarray ersetzten
                if(tilearray[i][j].getShape().length<=0){
                    if(map[i][j]==true){
                        buttonarray[i][j].setBackgroundColor(Color.WHITE);


                    }
                    else{
                        buttonarray[i][j].setBackgroundColor(Color.BLACK);

                    }

                }
            }
        }
    }

    //befuellt das button array um später die farben der buttons verändern zu können
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
    //onclicklistener when user clicks on a button checks if there is a tile on this coordination
    private void activateonclicklisteneronmapbuttons(){
        for(int i = 0; i<buttonarray.length; i++){
            for(int j = 0; j<buttonarray[i].length; j++){
                buttonarray[i][j].setOnClickListener(v -> gettilefromid(v.getId()));
            }
        }
    }

    //true=1=frei
    //false=0=belegt

    private View.OnClickListener movetiles(Tile tile, ImageView tileimage, int color){
        addonclicklistener();


        if(currenttile!=null){

            currenttile.attachToMap(currentmap, currentpositionx, currentpositiony);
            placetilesintilesarray(currenttile, currentpositionx, currentpositiony);
            currentpositiony=0;
            currentpositionx=0;
        }

        tile.setColor(color);
        currenttile = tile;
        tilepositions=currenttile.getShape();
        outerloop:
        for(int i =0; i<5; i++){
            for(int j= 0; j<5; j++){
                if(checkifplacable(i,j, tilepositions)){
                    tileimage.setBackgroundResource(0);
                    tileimage.setOnClickListener(null);
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
            tilearray[y+c.getY()][x+c.getX()]=tile;
        }
    }

    //gets the coordinates from the button that was clicked an calls method checkiftereisatile
    private void gettilefromid(int id){

        for(int i = 0; i<buttonarray.length; i++){
            for(int j = 0; j<buttonarray[i].length; j++){
                if(buttonarray[i][j].getId()==id){
                    checkifthereisatile( i,  j);
                }
            }
        }
    }

    //if on this coordinates there is a tile then the currenttile is attatched, sets all fields in de colorarray true
    //and the tiles in the tilearray true
    // sets the currenttile, the positions and the coordinates

    private void checkifthereisatile(int i, int j){
        if(tilearray[i][j].getShape().length<=0){
            System.out.println("On this place there is no tile");
        }else {
            currenttile.attachToMap(currentmap, currentpositionx, currentpositiony);

            placetilesintilesarray(currenttile, currentpositionx, currentpositiony);
            currenttile = tilearray[i][j];
            tilepositions= currenttile.getShape();
            currentpositionx = currenttile.getHook().getX();
            currentpositiony = currenttile.getHook().getY();

            System.out.println(currentpositionx+currentpositiony);

        }
    }


    protected void colorbuttons(int x, int y, Position[] tiles){
        for(int i =0; i<tiles.length; i++){
            buttonarray[y+tiles[i].getY()][x+tiles[i].getX()].setBackgroundColor(currenttile.getColor());
        }
    }

    //colors the buttons in the button array


    private boolean checkifplacable(int x, int y, Position[] tilepositions){
        currenttile.setMap(currentmap);
        for(int i = 0; i< tilepositions.length; i++) {
            if(x+tilepositions[i].getX()<0||y+tilepositions[i].getY()<0||x+tilepositions[i].getX()>=5||y+tilepositions[i].getY()>=5){
                return false;
            }
            else if (tilearray[y+tilepositions[i].getY()][x+tilepositions[i].getX()].getShape().length>0) {
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
        Button mirror  = findViewById(R.id.mirror);

        up.setOnClickListener(v -> movetileup());
        down.setOnClickListener(v -> movetiledown());
        left.setOnClickListener(v -> movetileleft());
        right.setOnClickListener(v -> movetileright());
        turnleft.setOnClickListener(v -> turntileleft());
        turnright.setOnClickListener(v -> turntileright());
        mirror.setOnClickListener(v -> mirror());

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

    private void mirror(){
        currenttile.mirrorHorizontally();
        tilepositions=currenttile.getShape();
        if(checkifplacable(currentpositionx,currentpositiony,tilepositions)){
            drawmap();
            colorbuttons(currentpositionx,currentpositiony,tilepositions);
        }
    }
}