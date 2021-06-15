package com.example.se2_gruppenphase_ss21.game.alternativeGUI;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.core.graphics.ColorUtils;
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

    private static final int TILE1_COLOR = Color.RED;
    private static final int TILE2_COLOR = Color.BLUE;
    private static final int TILE3_COLOR = Color.YELLOW;

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

        assignTrayTiles();
        addTileToTray(tile1, trayTile1);
        addTileToTray(tile2, trayTile2);
        addTileToTray(tile3, trayTile3);
        registerTrayTileListener(trayTile1, tile1);
        registerTrayTileListener(trayTile2, tile2);
        registerTrayTileListener(trayTile3, tile3);

        setUpButtons();

    }

    private void setUpButtons() {
        getView().findViewById(R.id.rotate_right).setOnClickListener(v -> {
            if(active == null) return;
            if(active.detachFromMap())
                setOriginalColor(active);
            active.rotateRightAndPlace(map);
            attachTile(active);
            drawMap();
        });

        getView().findViewById(R.id.rotate_left).setOnClickListener(v -> {
            if(active == null) return;
            if(active.detachFromMap())
                setOriginalColor(active);
            active.rotateLeftAndPlace(map);
            attachTile(active);
            drawMap();
        });

        getView().findViewById(R.id.mirror_horizontal).setOnClickListener(v -> {
            if(active == null) return;
            if(active.detachFromMap())
                setOriginalColor(active);
            active.mirrorHorizontallyAndPlace(map);
            attachTile(active);
            drawMap();
        });

        getView().findViewById(R.id.mirror_vertical).setOnClickListener(v -> {
            if(active == null) return;
            if(active.detachFromMap())
                setOriginalColor(active);
            active.mirrorVerticallyAndPlace(map);
            attachTile(active);
            drawMap();
        });

        getView().findViewById(R.id.remove).setOnClickListener(v -> {
            if(active == null) return;

            if(active == tile1)
                addTileToTray(active, trayTile1);
            else if(active == tile2)
                addTileToTray(active, trayTile2);
            else
                addTileToTray(active, trayTile3);

            detachTile(active);
            active.removeTempFromMap();
            drawMap();
            active = null;
        });
    }

    private void setUpTiles() {
        tile1 = new Tile(getContext().getAssets(), tileIDs[0], "standard");
        setOriginalColor(tile1);

        tile2 = new Tile(getContext().getAssets(), tileIDs[1], "standard");
        setOriginalColor(tile2);

        tile3 = new Tile(getContext().getAssets(), tileIDs[2], "standard");
        setOriginalColor(tile3);
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

    private void assignTrayTiles() {
        trayTile1 = getView().findViewById(R.id.table_tile1);
        trayTile2 = getView().findViewById(R.id.table_tile2);
        trayTile3 = getView().findViewById(R.id.table_tile3);
    }

    private void addTileToTray(Tile tile, TableLayout trayTile) {

        boolean[][] matrix = tile.getShapeMatrix();
        setOriginalColor(tile);
        drawTrayTile(trayTile, matrix, tile.getColor());
        trayTile.setClickable(true);
        trayTile.setVisibility(View.VISIBLE);
    }

    private void drawTrayTile(TableLayout table, boolean[][] matrix, int color) {
        // offset to place it more centric
        int offsetX = (4 - matrix[0].length) / 2;
        int offsetY = (4 - matrix.length) / 2;

        clearTrayTileMap(table);
        for(int y=0; y < matrix.length; y++)
            for(int x=0; x < matrix[0].length; x++)
                if(matrix[y][x] && (x+offsetX) < 4 && (x+offsetX) >= 0
                        && (y+offsetY) < 4 && (y+offsetY) >=0) {

                    View box = getBox(table, x + offsetX, y + offsetY);
                    box.setAlpha(1);
                    box.setBackgroundColor(color);
                }
    }

    private void clearTrayTileMap(TableLayout table) {
        for(int y=0; y < 4; y++)
            for(int x=0; x < 4; x++)
                getBox(table, x, y).setAlpha(0);
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
                detachTile(active);
                cursorOffsetX = active.getHook().getX() - x;
                cursorOffsetY = active.getHook().getY() - y;
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            if(active != null && attachTile(active))
                drawMap();
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE) {
            if(active != null && (active.getHook().getX() != x || active.getHook().getY() != y)) {
                active.placeTempOnMap(map, new Position(x + cursorOffsetX,y + cursorOffsetY));
                drawMap();
            }
        }

        return true;
    }

    private boolean attachTile(Tile tile) {
        boolean attached = tile.attachToMap(map, tile.getHook());
        if(attached) {
            int attachedColor = ColorUtils.blendARGB(
                    tile.getColor(), Color.BLACK, 0.3f);
            tile.setColor(attachedColor);
            MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.click);
            mp.start();
        }
        return attached;
    }

    private boolean detachTile(Tile tile) {
        boolean detached = tile.detachFromMap();
        if(detached)
            setOriginalColor(tile);
        return detached;
    }

    private void setOriginalColor(Tile tile) {
        if(tile == tile1)
            tile.setColor(TILE1_COLOR);
        else if(tile == tile2)
            tile.setColor(TILE2_COLOR);
        else
            tile.setColor(TILE3_COLOR);
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
