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
import com.example.se2_gruppenphase_ss21.logic.tetris.Position;
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
    private Tile active;
    private Map map;
    private int cursorOffsetX = 0;
    private int cursorOffsetY = 0;

    private TableLayout mapTable;
    private TableLayout trayTile1;
    private TableLayout trayTile2;
    private TableLayout trayTile3;



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

        return view;
    }

    public void onStart() {
        super.onStart();
        setUpPuzzle();
    }

    private void setUpPuzzle() {

        mapTable = getView().findViewById(R.id.table_playfield);
        map = new Map(getContext().getAssets(), mapID, "5x5");
        drawMap();

        setUpTiles();

        setUpTileTray();
        registerTrayTileListener(trayTile1, tile1);
        registerTrayTileListener(trayTile2, tile2);
        registerTrayTileListener(trayTile3, tile3);

        getView().findViewById(R.id.rotate_right).setOnClickListener(v -> {
            if(active == null) return;
            active.rotateRightAndPlace(map);
            drawMap();
        });

        getView().findViewById(R.id.rotate_left).setOnClickListener(v -> {
            if(active == null) return;
            active.rotateLeftAndPlace(map);
            drawMap();
        });

        getView().findViewById(R.id.mirror_horizontal).setOnClickListener(v -> {
            if(active == null) return;
            active.mirrorHorizontallyAndPlace(map);
            drawMap();
        });

        getView().findViewById(R.id.mirror_vertical).setOnClickListener(v -> {
            if(active == null) return;
            active.mirrorVerticallyAndPlace(map);
            drawMap();
        });
    }

    private void setUpTiles() {
        tile1 = new Tile(getContext().getAssets(), tileIDs[0], "standard");
        tile1.setColor(Color.RED);

        tile2 = new Tile(getContext().getAssets(), tileIDs[1], "standard");
        tile2.setColor(Color.BLUE);

        tile3 = new Tile(getContext().getAssets(), tileIDs[2], "standard");
        tile3.setColor(Color.YELLOW);
    }

    private void drawMap() {
        for(int y=0; y < map.getSizeY(); y++) {
            for(int x=0; x < map.getSizeX(); x++) {

                Box box = map.getBox(x,y);
                View tableCell = getBox(mapTable, x, y);

                if(box.isCoveredByTempTile())
                    tableCell.setBackgroundColor(box.getTempTile().getColor());

                else if(box.isCoveredByTile())
                    tableCell.setBackgroundColor(box.getTile().getColor());

                else if(box.isField())
                    tableCell.setBackgroundColor(Color.WHITE);

                else
                    tableCell.setBackgroundColor(Color.parseColor("#1C1C1C"));
            }
        }
    }

    private void setUpTileTray() {

        boolean[][] matrix = tile1.getShapeMatrix();
        trayTile1 = getView().findViewById(R.id.table_tile1);
        drawTrayTile(trayTile1, matrix, Color.RED);

        matrix = tile2.getShapeMatrix();
        trayTile2 = getView().findViewById(R.id.table_tile2);
        drawTrayTile(trayTile2, matrix, Color.BLUE);

        matrix = tile3.getShapeMatrix();
        trayTile3 = getView().findViewById(R.id.table_tile3);
        drawTrayTile(trayTile3, matrix, Color.YELLOW);
    }

    private void drawTrayTile(TableLayout table, boolean[][] matrix, int color) {
        // offset to place it more centric
        int offsetX = (4 - matrix[0].length) / 2;
        int offsetY = (5 - matrix.length) / 2;

        for(int y=0; y < matrix.length; y++)
            for(int x=0; x < matrix[0].length; x++)
                if(matrix[y][x])
                    getBox(table, x + offsetX, y + offsetY).setBackgroundColor(color);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int boxWidth = (int)(convertPixelsToDp(mapTable.getWidth()) / 5);
        int x = (int)(convertPixelsToDp(event.getX()) / boxWidth);
        int y = (int)(convertPixelsToDp(event.getY()) / boxWidth);
        // touch events are also tracked outside of element boundary
        x = Math.min(x, 4);
        y = Math.min(y, 4);
        x = Math.max(x, 0);
        y = Math.max(y, 0);


        Box box = map.getBox(x,y);
        Tile onLocation = box.getTempTile();

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(box.isCoveredByTempTile()) {
                active = onLocation;
                cursorOffsetX = active.getHook().getX() - x;
                cursorOffsetY = active.getHook().getY() - y;
            }
        }
        else {
            if(active.getHook().getX() != x || active.getHook().getY() != y) {
                active.placeTempOnMap(map, new Position(x + cursorOffsetX,y + cursorOffsetY));
                drawMap();
            }
        }

        return true;
    }

    private View getBox(TableLayout table, int x, int y) {
        TableRow row = (TableRow)table.getChildAt(y);
        return row.getChildAt(x);
    }

    private void registerTrayTileListener(View trayTile, Tile tile) {
        trayTile.setOnClickListener(v -> {
            active = tile;
            active.placeTempOnMap(map, new Position(2,2));
            drawMap();
            v.setClickable(false);
            v.setVisibility(View.INVISIBLE);
        });
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
