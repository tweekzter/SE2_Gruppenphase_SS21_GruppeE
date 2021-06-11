package com.example.se2_gruppenphase_ss21.game.alternativeGUI;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.fragment.app.Fragment;

import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.logic.tetris.Box;
import com.example.se2_gruppenphase_ss21.logic.tetris.Map;
import com.example.se2_gruppenphase_ss21.logic.tetris.Tile;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.client.PlayerPlacement;
import com.example.se2_gruppenphase_ss21.networking.client.listeners.InRoundListener;

import java.util.ArrayList;

public class PlayField extends Fragment implements InRoundListener, View.OnTouchListener {
    private GameClient client;
    private int[] tileIDs;
    private int mapID = 2;

    private Tile tile1;
    private Tile tile2;
    private Tile tile3;
    private Map map;

    private TableLayout mapTable;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = GameClient.getActiveGameClient();
        client.registerListener(this);
        tileIDs = getArguments().getIntArray("tiles");
        mapID = getArguments().getInt("mapID");
        Log.d("dice", "mapID "+mapID);
    }

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, ViewGroup puzzleContainer,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playfield, puzzleContainer, false);
        view.findViewById(R.id.table_playfield).setOnTouchListener(this);
        mapTable = view.findViewById(R.id.table_playfield);

        return view;
    }

    public void onStart() {
        super.onStart();
        setUpPuzzle();
    }

    private void setUpPuzzle() {
        map = new Map(getContext().getAssets(), mapID, "5x5");
        setUpMap();

        tile1 = new Tile(getContext().getAssets(), tileIDs[0], "standard");
        tile2 = new Tile(getContext().getAssets(), tileIDs[1], "standard");
        tile3 = new Tile(getContext().getAssets(), tileIDs[2], "standard");
        setUpTileTray();
    }

    private void setUpMap() {
        for(int y=0; y < map.getSizeY(); y++) {
            for(int x=0; x < map.getSizeX(); x++) {
                Box box = map.getBox(x,y);
                if(box.isField())
                    getBox(mapTable, x, y).setBackgroundColor(Color.WHITE);
            }
        }
    }

    private void setUpTileTray() {
        TableLayout table = getView().findViewById(R.id.table_tile1);
        boolean[][] matrix = tile1.getShapeMatrix();
        drawTrayTile(table, matrix, Color.RED);

        table = getView().findViewById(R.id.table_tile2);
        matrix = tile2.getShapeMatrix();
        drawTrayTile(table, matrix, Color.BLUE);

        table = getView().findViewById(R.id.table_tile3);
        matrix = tile3.getShapeMatrix();
        drawTrayTile(table, matrix, Color.YELLOW);
    }

    private void drawTrayTile(TableLayout table, boolean[][] matrix, int color) {
        for(int y=0; y < matrix.length; y++)
            for(int x=0; x < matrix[0].length; x++)
                if(matrix[y][x])
                    getBox(table,x,y).setBackgroundColor(color);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int boxWidth = (int)(convertPixelsToDp(mapTable.getWidth()) / 5);
        int x = (int)(convertPixelsToDp(event.getX()) / boxWidth);
        int y = (int)(convertPixelsToDp(event.getY()) / boxWidth);

        getBox(mapTable, x, y).setBackgroundColor(Color.RED);

        return true;
    }

    private View getBox(TableLayout table, int x, int y) {
        TableRow row = (TableRow)table.getChildAt(y);
        return row.getChildAt(x);
    }

    private float convertDpToPixels(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

    private float convertPixelsToDp(float pixels) {
        return pixels / getContext().getResources().getDisplayMetrics().density;
    }

    @Override
    public void beginPuzzle(long finishUntil) {

    }

    @Override
    public void placementsReceived(ArrayList<PlayerPlacement> placements) {

    }

    @Override
    public void userDisconnect(String nickname) {

    }

    @Override
    public void unknownMessage(String message) {

    }
}
