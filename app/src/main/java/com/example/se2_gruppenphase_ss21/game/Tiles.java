package com.example.se2_gruppenphase_ss21.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.os.Vibrator;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;



import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.logic.tetris.Map;
import com.example.se2_gruppenphase_ss21.logic.tetris.Position;
import com.example.se2_gruppenphase_ss21.logic.tetris.Tile;
import com.example.se2_gruppenphase_ss21.menu.LeaderboardActivity;
import com.example.se2_gruppenphase_ss21.menu.MainActivity;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.client.PlayerPlacement;
import com.example.se2_gruppenphase_ss21.networking.client.listeners.InRoundListener;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Puzzle field. GUI representation of the Puzzle.
 * @author Pia Lanker (main logic), Manuel Simon (client integration)
 */
public class Tiles extends AppCompatActivity implements InRoundListener,
        TimerListener, CheatingDialogFragment.CheatingDialogListener {
    GameClient client;
    Tile currenttile;
    int currentpositionx=0;
    int currentpositiony=0;
    Position[] tilepositions;

    boolean[][] map;
    Map currentmap;
    Button[][] buttonarray;
    int[] pictures;

    Tile[][] tilearray = new Tile[5][5];

    Button up;
    Button down;
    Button left ;
    Button right;

    Button turnright;
    Button turnleft;
    Button mirror;
    Button removetile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpPuzzle();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setUpPuzzle();
    }

    /**
     * Sets up the Puzzle field.
     */
    private void setUpPuzzle() {
        // get client instance + register in-round listener
        client = GameClient.getActiveGameClient();
        client.registerListener(this);
        Bundle b = getIntent().getExtras();
        pictures = b.getIntArray("key");
        setContentView(R.layout.activity_tiles);
        InputStream is = null;
        try {
            is = getAssets().open("maps.xml");
            //holt sich daten aus xml f??r das aussehen der map

            map= XMLParser.parsexml(Maps.cardnumbers[pictures[6]], is);

            currentmap=new Map(map);

            fillbuttonarray();
            filltylearray();
            activateonclicklisteneronmapbuttons();
            //hole die bausteine und fuege die bilder zu den bausteinen ein


            ImageView firsttile = findViewById(R.id.firsttile);
            ImageView secondtile = findViewById(R.id.secondtile);
            ImageView thirdtile = findViewById(R.id.thirdtile);

            String categroy = "standard";
            Tile tileone = new Tile(getApplicationContext().getAssets(), pictures[3], categroy);
            Tile tiletwo = new Tile(getApplicationContext().getAssets(), pictures[4], categroy);
            Tile tilethree = new Tile(getApplicationContext().getAssets(), pictures[5], categroy);

            firsttile.setBackgroundResource(pictures[0]);
            secondtile.setBackgroundResource(pictures[1]);
            thirdtile.setBackgroundResource(pictures[2]);

            //sets onclicklistener on images

            firsttile.setOnClickListener(v -> movetiles(tileone, firsttile, Color.BLUE));

            secondtile.setOnClickListener(v -> movetiles(tiletwo, secondtile, Color.GREEN));

            thirdtile.setOnClickListener(v -> movetiles(tilethree, thirdtile, Color.RED));

            up = findViewById(R.id.tileup);
            down = findViewById(R.id.tiledown);
            left = findViewById(R.id.tileleft);
            right = findViewById(R.id.tileright);

            turnright = findViewById(R.id.turnrigth);
            turnleft = findViewById(R.id.turnleft);
            mirror = findViewById(R.id.mirror);
            removetile = findViewById(R.id.removetile);

            Button ubongo = findViewById(R.id.ubongo);
            ubongo.setOnClickListener(v -> callUbongo());


            is.close();

        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    //befuellt das tile array. tylearray dient dazu zu ??berpr??fen ob an stelle x,y ein baustein liegt und wenn ja welcher
    protected void filltylearray(){
        Tile none = new Tile();
        for (Tile[] tiles : tilearray) {
            Arrays.fill(tiles, none);
        }

    }

    protected void drawmap(){

        for(int i=0; i<map.length;i++){
            for(int j=0; j<map[i].length; j++){
                if(tilearray[i][j].getShape().length<=0){
                    if(map[i][j]){
                        buttonarray[i][j].setBackgroundColor(Color.WHITE);


                    }
                    else{
                        buttonarray[i][j].setBackgroundColor(Color.BLACK);

                    }

                }
            }
        }
    }

    //befuellt das button array um sp??ter die farben der buttons ver??ndern zu k??nnen
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
        for (Button[] buttons : buttonarray) {
            for (Button button : buttons) {
                button.setOnClickListener(v -> gettilefromid(v.getId()));
            }
        }
    }

    //true=1=frei
    //false=0=belegt

    private void movetiles(Tile tile, ImageView tileimage, int color){



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
                    setvisibilityofbuttonstrue();
                    addonclicklistener();
                    currentpositionx=i;
                    currentpositiony = j;
                    break outerloop;
                }
            }
            if(i == 4){
                currenttile = null;
                tilepositions=null;
                Toast.makeText(this, "No space for placing tile", Toast.LENGTH_SHORT).show();
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(100);
            }
        }

    }

    private void placetilesintilesarray(Tile tile, int x, int y){
        Position[] positionsoftiles = tile.getShape();
        tile.setHook(new Position(x,y));
        for(Position c : positionsoftiles ){
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
        if(tilearray[i][j].getShape().length>0){
            if(currenttile!=null) {
                currenttile.attachToMap(currentmap, currentpositionx, currentpositiony);
                placetilesintilesarray(currenttile, currentpositionx, currentpositiony);
            }else{
                setvisibilityofbuttonstrue();
                addonclicklistener();
            }
            currenttile = tilearray[i][j];


            tilepositions= currenttile.getShape();

            currentpositionx = currenttile.getHook().getX();
            currentpositiony = currenttile.getHook().getY();
            detatchfromtilearray();

        }
    }


    protected void colorbuttons(int x, int y, Position[] tiles){
        for (Position tile : tiles) {
            buttonarray[y + tile.getY()][x + tile.getX()].setBackgroundColor(currenttile.getColor());
        }
    }

    //colors the buttons in the button array


    private boolean checkifplacable(int x, int y, Position[] tilepositions){
        currenttile.setMap(currentmap);
        for (Position tileposition : tilepositions) {
            if (x + tileposition.getX() < 0 || y + tileposition.getY() < 0 || x + tileposition.getX() >= 5 || y + tileposition.getY() >= 5) {
                return false;
            } else if (tilearray[y + tileposition.getY()][x + tileposition.getX()].getShape().length > 0) {
                return false;
            }
        }
        return true;
    }

    private void addonclicklistener(){

        Button ubongo = findViewById(R.id.ubongo);

        up.setOnClickListener(v -> movetileup());
        down.setOnClickListener(v -> movetiledown());
        left.setOnClickListener(v -> movetileleft());
        right.setOnClickListener(v -> movetileright());
        turnleft.setOnClickListener(v -> turntileleft());
        turnright.setOnClickListener(v -> turntileright());
        mirror.setOnClickListener(v -> mirror());
        removetile.setOnClickListener(v -> removetile());
        ubongo.setOnClickListener(v -> callUbongo());

    }
    public void setvisibilityofbuttonstrue(){
        up.setVisibility(View.VISIBLE);
        down.setVisibility(View.VISIBLE);
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);
        mirror.setVisibility(View.VISIBLE);
        turnleft.setVisibility(View.VISIBLE);
        turnright.setVisibility(View.VISIBLE);
        removetile.setVisibility(View.VISIBLE);
    }
    public void removeonclicklistener(){
        up.setOnClickListener(null);
        down.setOnClickListener(null);
        left.setOnClickListener(null);
        right.setOnClickListener(null);
        turnleft.setOnClickListener(null);
        turnright.setOnClickListener(null);
        mirror.setOnClickListener(null);
        removetile.setOnClickListener(null);
    }

    public void setvisibilityofbuttonsfalse(){
        up.setVisibility(View.INVISIBLE);
        down.setVisibility(View.INVISIBLE);
        left.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);
        mirror.setVisibility(View.INVISIBLE);
        turnleft.setVisibility(View.INVISIBLE);
        turnright.setVisibility(View.INVISIBLE);
        removetile.setVisibility(View.INVISIBLE);

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
        }else{
            currenttile.rotateLeft();
            tilepositions = currenttile.getShape();
        }


    }

    private void turntileleft(){
        currenttile.rotateLeft();
        tilepositions=currenttile.getShape();
        if(checkifplacable(currentpositionx,currentpositiony,tilepositions)){
            drawmap();
            colorbuttons(currentpositionx,currentpositiony,tilepositions);
        }else{
            currenttile.rotateRight();
            tilepositions = currenttile.getShape();
        }

    }

    private void mirror(){
        currenttile.mirrorHorizontally();
        tilepositions=currenttile.getShape();
        if(checkifplacable(currentpositionx,currentpositiony,tilepositions)){
            drawmap();
            colorbuttons(currentpositionx,currentpositiony,tilepositions);
        }else{
            currenttile.mirrorHorizontally();
            tilepositions= currenttile.getShape();
        }
    }

    private void removetile(){
        int color = currenttile.getColor();
        if(color == Color.BLUE){
            ImageView firsttile = findViewById(R.id.firsttile);
            firsttile.setBackgroundResource(pictures[0]);
            Tile tileone = new Tile(getApplicationContext().getAssets(), pictures[3], "standard");
            firsttile.setOnClickListener(v -> movetiles(tileone,firsttile, Color.BLUE));
        } else if(color == Color.GREEN){
            ImageView secondtile = findViewById(R.id.secondtile);
            secondtile.setBackgroundResource(pictures[1]);
            Tile tiletwo = new Tile(getApplicationContext().getAssets(), pictures[4], "standard");
            secondtile.setOnClickListener(v-> movetiles(tiletwo,secondtile,Color.GREEN));
        } else if(color == Color.RED){
            ImageView thirdtile = findViewById(R.id.thirdtile);
            thirdtile.setBackgroundResource(pictures[2]);
            Tile tilethree = new Tile(getApplicationContext().getAssets(), pictures[5], "standard");
            thirdtile.setOnClickListener(v->movetiles(tilethree,thirdtile,Color.RED));
        }
        currenttile.detachFromMap();
        detatchfromtilearray();
        removeonclicklistener();
        setvisibilityofbuttonsfalse();
        currenttile=null;
        currentpositionx =0;
        currentpositiony=0;
        drawmap();
    }



    public void showCheatingDialog() {
        DialogFragment newFragment = new CheatingDialogFragment();
        newFragment.show(getSupportFragmentManager(), "CheatingDialogFragment");
    }

    @Override
    public void onCheatingPositiveClick(DialogFragment dialog) {
        try {
            client.puzzleDone(true);
        } catch(IOException ex) {
            Log.e("tiles", ex.toString());
            Toast.makeText(this, "Connection to the server failed", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCheatingCancelClick(DialogFragment dialog) {
        //USELESS?
    }


    private boolean checkSolved() {
        for(int y=0; y < tilearray.length; y++) {
            for(int x=0; x < tilearray[0].length; x++) {
                if(map[y][x] && tilearray[y][x].getShape().length == 0)
                    return false;
            }
        }
        return true;
    }

    private void callUbongo() {
        try {
            if(currenttile!=null) {
                placetilesintilesarray(currenttile, currentpositionx, currentpositiony);
            }
            if(checkSolved()) {
                Log.d("tiles", "you're done mate");
                client.puzzleDone(false);
                
            } else {
                showCheatingDialog();

            }
        } catch(IOException ex) {
            Log.e("tiles", ex.toString());
            Toast.makeText(this, "Connection to the server failed", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void detatchfromtilearray(){
        Tile empty = new Tile();
        for(Position positions:currenttile.getShape()){
            tilearray[currentpositiony+positions.getY()][currentpositionx+positions.getX()] = empty;
        }
    }

    /**
     * Called by the server when the puzzle starts
     * @param finishUntil the time until the puzzle should be finished
     * @author Manuel Simon #00326348
     */
    public void beginPuzzle(long finishUntil) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            findViewById(R.id.waitForServer).setVisibility(View.INVISIBLE);

            // reveal map and tiles
            drawmap();
            findViewById(R.id.firsttile).setVisibility(View.VISIBLE);
            findViewById(R.id.secondtile).setVisibility(View.VISIBLE);
            findViewById(R.id.thirdtile).setVisibility(View.VISIBLE);

            // start timer
            TimerView timer = findViewById(R.id.timer);
            timer.setListener(this);
            timer.start(finishUntil);
        });
    }

    /**
     * Is called when the placements are received after the Puzzle is finished, marks the end of a round.
     * Next roll request is received in approx. 10 seconds.
     * @param placements
     */
    public void placementsReceived(ArrayList<PlayerPlacement> placements) {
        TimerView timer = findViewById(R.id.timer);
        timer.abort();

        Intent intent = new Intent(this, LeaderboardActivity.class);
        intent.putExtra("placements", placements);
        startActivity(intent);
    }

    @Override
    public void userDisconnect(String nickname) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() ->
                Toast.makeText(this, "Player "+nickname+" disconnected!", Toast.LENGTH_LONG).show()
        );
    }

    @Override
    public void unknownMessage(String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() ->
                Toast.makeText(this, "Network error: "+message, Toast.LENGTH_LONG).show()
        );
    }

    /**
     * Listener method called when timer runs out.
     */
    public void timeIsUp(TimerView timer) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {

            Button button = findViewById(R.id.ubongo);
            button.setClickable(false);

            findViewById(R.id.firsttile).setVisibility(View.INVISIBLE);
            findViewById(R.id.secondtile).setVisibility(View.INVISIBLE);
            findViewById(R.id.thirdtile).setVisibility(View.INVISIBLE);
            findViewById(R.id.time_is_up).setVisibility(View.VISIBLE);

            // reset normal state color
            timer.setColor(Color.BLUE);
        });
    }

    /**
     * Prepares views for usage in next round.
     */
    @Override
    protected void onStop() {
        super.onStop();
        findViewById(R.id.waitForServer).setVisibility(View.VISIBLE);
        findViewById(R.id.time_is_up).setVisibility(View.INVISIBLE);
    }
}