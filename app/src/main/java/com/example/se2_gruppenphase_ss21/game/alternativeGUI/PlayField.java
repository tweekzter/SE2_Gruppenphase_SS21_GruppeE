package com.example.se2_gruppenphase_ss21.game.alternativeGUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.game.CheatingDialogFragment;
import com.example.se2_gruppenphase_ss21.game.TimerListener;
import com.example.se2_gruppenphase_ss21.game.TimerView;
import com.example.se2_gruppenphase_ss21.logic.tetris.Box;
import com.example.se2_gruppenphase_ss21.logic.tetris.Map;
import com.example.se2_gruppenphase_ss21.logic.tetris.Position;
import com.example.se2_gruppenphase_ss21.logic.tetris.Tile;
import com.example.se2_gruppenphase_ss21.menu.LeaderboardActivity;
import com.example.se2_gruppenphase_ss21.menu.MainActivity;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.client.PlayerPlacement;
import com.example.se2_gruppenphase_ss21.networking.client.listeners.InRoundListener;

import java.io.IOException;
import java.util.ArrayList;

/**
 * GUI representation of the Puzzle.
 *
 * This representation allows moving the TILES by touch inputs.
 *
 * It utilizes the MAP & TILES logic from the logic.tetris package for TILE placement
 * and MAP computations (eg. if the puzzle is solved correctly, keeping track
 * where tiles are positioned, etc).
 *
 * The Puzzle consists of a MAP, three TILES which are initially positioned in the
 * top tray bar and can be selected to be positioned inside the MAP.
 * TILEs can be moved via touch inputs and be rotated or mirrored with respective buttons.
 *
 * A timer will run out, indicating the remaining time to solve the Puzzle.
 *
 * @author Manuel Simon #00326348
 */
public class PlayField extends Fragment implements InRoundListener,
        View.OnTouchListener, TimerListener, CheatingDialogFragment.CheatingDialogListener {

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

    private static final boolean BLUFF = true;
    private static final boolean NO_BLUFF = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = GameClient.getActiveGameClient();
        client.registerListener(this);

        tileIDs = getArguments().getIntArray("tiles");
        mapID = getArguments().getInt("mapID");
        Log.d("puzzle", "mapID "+mapID);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup puzzleContainer,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playfield, puzzleContainer, false);
        view.findViewById(R.id.table_playfield).setOnTouchListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpPuzzle();
    }

    /**
     * Does the main initialization work for the Puzzle.
     * Method names should be self-explanatory.
     *
     * For detailed information about the concept of the Puzzle,
     * refer to the class description.
     */
    private void setUpPuzzle() {

        mapTable = getView().findViewById(R.id.table_playfield);
        map = new Map(getContext().getAssets(), mapID, "5x5");
        // map will get drawn once server signals to start round

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

        getView().findViewById(R.id.rotate_right).setOnClickListener(v -> rotateRight());
        getView().findViewById(R.id.rotate_left).setOnClickListener(v -> rotateLeft());
        getView().findViewById(R.id.mirror_horizontal).setOnClickListener(v -> mirrorHorizontally());
        getView().findViewById(R.id.mirror_vertical).setOnClickListener(v -> mirrorVertically());

        getView().findViewById(R.id.remove).setOnClickListener(v -> removeTileFromMap());
        getView().findViewById(R.id.ubongo_button).setOnClickListener(v -> callUbongo(v));
        getView().findViewById(R.id.ubongo_button).setClickable(false);
    }

    private void rotateRight() {
        if(active == null) return;
        if(active.detachFromMap())
            setOriginalColor(active);
        active.rotateRightAndPlace(map);
        attachTile(active);
        drawMap();
    }

    private void rotateLeft() {
        if(active == null) return;
        if(active.detachFromMap())
            setOriginalColor(active);
        active.rotateLeftAndPlace(map);
        attachTile(active);
        drawMap();
    }

    private void mirrorHorizontally() {
        if(active == null) return;
        if(active.detachFromMap())
            setOriginalColor(active);
        active.mirrorHorizontallyAndPlace(map);
        attachTile(active);
        drawMap();
    }

    private void mirrorVertically() {
        if(active == null) return;
        if(active.detachFromMap())
            setOriginalColor(active);
        active.mirrorVerticallyAndPlace(map);
        attachTile(active);
        drawMap();
    }

    private void setUpTiles() {
        tile1 = new Tile(getContext().getAssets(), tileIDs[0], "standard");
        tile1.setMap(map);
        setOriginalColor(tile1);

        tile2 = new Tile(getContext().getAssets(), tileIDs[1], "standard");
        tile2.setMap(map);
        setOriginalColor(tile2);

        tile3 = new Tile(getContext().getAssets(), tileIDs[2], "standard");
        tile3.setMap(map);
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
    }

    private void drawTrayTile(TableLayout table, boolean[][] matrix, int color) {
        // offset to place it more centric
        int offsetX = (4 - matrix[0].length) / 2;
        int offsetY = (4 - matrix.length) / 2;

        clearTrayTileMap(table);

        for(int y=0; y < matrix.length; y++)
            for(int x=0; x < matrix[0].length; x++) {
                boolean validPos = (x + offsetX) < 4 && (x + offsetX) >= 0
                        && (y + offsetY) < 4 && (y + offsetY) >= 0;

                if(matrix[y][x] && validPos) {
                    View box = getBox(table, x + offsetX, y + offsetY);
                    box.setBackgroundColor(color);
                }
            }
    }

    private void clearTrayTileMap(TableLayout table) {
        int yLength = table.getChildCount();
        int xLength = ((TableRow)table.getChildAt(0)).getChildCount();

        for(int y=0; y < yLength; y++)
            for(int x=0; x < xLength; x++)
                getBox(table, x, y).setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * Handles touch events to position the TILEs.
     *
     * TILEs can be selected by touch and be dragged around. If a TILE comes to a stop on
     * a valid position, it will automatically snap to the puzzle. This is displayed by
     * a darker color tone. It will automatically detach by touch again.
     *
     * This implementation uses TempTiles for presentation only. This allows us to always show
     * the TILE on top that has been moved recently. Although it might sound better to
     * place an attached TILE at the bottom (as it sticks to the puzzle), it has turned out
     * to be more ergonomic, to have the recently used TILE on top - even if attached.
     * An attached TILE will always have a TempTile representation at the same position.
     * So it will also be displayed accurately.
     *
     * @param v The view this touch event is called upon.
     * @param event The touch event.
     * @return true
     */
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


        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Box box = map.getBox(x,y);
            if(box.isCoveredByTempTile()) {
                active = box.getTempTile();
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
            if(active == null)
                return true;
            int prevX = active.getHook().getX() - cursorOffsetX;
            int prevY = active.getHook().getY() - cursorOffsetY;
            boolean posChanged = prevX != x || prevY != y;

            if(posChanged) {
                active.placeTempOnMap(new Position(x + cursorOffsetX,y + cursorOffsetY));
                drawMap();
            }
        }

        return true;
    }

    private void callUbongo(View v) {
        if(map.checkSolved()) {
            v.setClickable(false);

            setTrayBarVisibility(View.INVISIBLE);
            TextView infobox = getView().findViewById(R.id.infobox);
            infobox.setVisibility(View.VISIBLE);
            infobox.setText(R.string.puzzle_solved);

            try {
                client.puzzleDone(NO_BLUFF);

            } catch(IOException ex) {
                Log.e("puzzle", "error while trying to send puzzleDone message to client");
                Log.e("puzzle", ex.toString());
                Toast.makeText(getActivity(), "Connection to the server failed", Toast.LENGTH_LONG).show();

                v.setClickable(true);
                infobox.setVisibility(View.INVISIBLE);
                setTrayBarVisibility(View.VISIBLE);
            }
        }
        else
            showCheatingDialog();
    }

    private void showCheatingDialog() {
        DialogFragment newFragment = new CheatingDialogFragment();
        newFragment.show(getChildFragmentManager(), "CheatingDialogFragment");
    }

    @Override
    public void onCheatingPositiveClick(DialogFragment dialog) {
        getView().findViewById(R.id.ubongo_button).setClickable(false);

        setTrayBarVisibility(View.INVISIBLE);
        TextView infobox = getView().findViewById(R.id.infobox);
        infobox.setVisibility(View.VISIBLE);
        infobox.setText(R.string.solved_cheating);

        try {
            client.puzzleDone(BLUFF);

        } catch(IOException ex) {
            Log.e("tiles", ex.toString());
            Toast.makeText(getActivity(), "Connection to the server failed", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCheatingCancelClick(DialogFragment dialog) {
        // do nix
    }

    private void setTrayBarVisibility(int visibility) {
        trayTile1.setVisibility(visibility);
        trayTile2.setVisibility(visibility);
        trayTile3.setVisibility(visibility);
    }

    private void removeTileFromMap() {
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
    }

    private boolean attachTile(Tile tile) {
        boolean attached = tile.attachToMap();
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

    private void registerTrayTileListener(TableLayout trayTile, Tile tile) {
        trayTile.setOnClickListener(v -> {
            active = tile;
            active.placeTempOnMap(new Position(2,2));
            drawMap();
            v.setClickable(false);
            clearTrayTileMap(trayTile);
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
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            // show tray icons
            getView().findViewById(R.id.infobox).setVisibility(View.INVISIBLE);
            setTrayBarVisibility(View.VISIBLE);

            // reveal map and activate Ubongo button
            drawMap();
            getView().findViewById(R.id.ubongo_button).setClickable(true);

            //start timer
            TimerView timer = getView().findViewById(R.id.timerView2);
            timer.setListener(this);
            timer.start(finishUntil);
        });
    }

    @Override
    public void placementsReceived(ArrayList<PlayerPlacement> placements) {
        TimerView timer = getView().findViewById(R.id.timerView2);
        timer.abort();

        Intent intent = new Intent(getActivity(), LeaderboardActivity.class);
        intent.putExtra("placements", placements);
        getActivity().startActivity(intent);
    }

    @Override
    public void userDisconnect(String nickname) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() ->
                Toast.makeText(getActivity(),
                        "Player "+nickname+" disconnected!", Toast.LENGTH_LONG).show()
        );
    }

    @Override
    public void unknownMessage(String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() ->
                Toast.makeText(getActivity(),
                        "Network error: "+message, Toast.LENGTH_LONG).show()
        );
    }

    @Override
    public void timeIsUp(TimerView timer) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            getView().findViewById(R.id.ubongo_button).setClickable(false);

            setTrayBarVisibility(View.INVISIBLE);

            TextView infobox = getView().findViewById(R.id.infobox);
            infobox.setVisibility(View.VISIBLE);
            infobox.setText(R.string.time_is_up);

            // reset timer color for next round
            timer.setColor(Color.BLUE);
        });
    }
}