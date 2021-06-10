package com.example.se2_gruppenphase_ss21.logic.tetris;

import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Loader class for JSON data structure file.
 *
 * !! PLEASE do not make fundamental changes to the logic, without coordinating with the team !!
 *
 * The JSON file contains a pool of data structures used.
 * It is basically structured in TYPE (map or tile), CATEGORY (in case of MAP this represents
 * the dimensions, for example 5x6 or "standard" for TILES) and the specific ID of the structure.
 * TILES could be extended by an "extended" category,
 * in case you would want a larger set of TILES for a game mode.
 *
 * @author Manuel Simon #00326348
 */
public class StructureLoader {
    private static String json;
    // a default structure that will be used, whenever problems occur
    private static boolean[][] standardStructure = {
            { false, false, false, false },
            { false, true,  true,  false },
            { false, true,  true,  false },
            { false, false, false, false }
    };

    /**
     * Utility class - no instantiation intended.
     */
    private StructureLoader() {
        super();
    }

    /**
     * Searches and returns the structure by ID, type and category in form of a two dimensional
     * boolean array.
     *
     * True representing a valid field and false a non-valid field.
     *
     * @param mgr AssetManager needed to read JSON file
     * @param id the ID of the structure
     * @param type type of the structure (for example "map" or "tile")
     * @param category category of the structure (for example the dimension "5x6")
     * @return
     */
    static boolean[][] getStructure(AssetManager mgr, int id, String type, String category) {

        boolean[][] structure = null;

        try {
            if(json == null)
                loadStructurePool(mgr);

            JSONObject obj = new JSONObject(json);
            JSONObject tp = obj.getJSONObject(type);
            JSONArray cat = tp.getJSONArray(category);

            for(int i=0; i < cat.length(); i++) {
                JSONObject struct = cat.getJSONObject(i);
                if(struct.getInt("id") == id) {
                    JSONArray line = struct.getJSONArray("shape");
                    for(int y = 0; y < line.length(); y++) {
                        JSONArray row = line.getJSONArray(y);
                        for(int x = 0; x < row.length(); x++) {
                            if(structure == null)
                                structure = new boolean[line.length()][row.length()];
                            structure[y][x] = row.getBoolean(x);
                        }
                    }
                }
            }

            if(structure == null || structure[0] == null)
                throw new NullPointerException("loading structure failed");

        } catch(JSONException | NullPointerException | IOException ex) {
            Log.e("pool", ex.toString());
            return standardStructure;
        }

        return structure;
    }

    /**
     * Returns the IDs of the Tiles (stored in structures.json) for a specific dice result
     * and map.
     * @param mgr AssetManager to access structures.json
     * @param diceResult dice result
     * @param mapID ID of the map this dice result should be applied on
     * @return the IDs of the Tiles as an int-array
     */
    public static int[] getTiles(AssetManager mgr, String diceResult, int mapID) {
        int tiles[] = { 2, 3, 4 };

        try {
            if(json == null)
                loadStructurePool(mgr);

            JSONObject obj = new JSONObject(json);
            JSONObject tp = obj.getJSONObject("map");
            JSONArray cat = tp.getJSONArray("5x5");

            for(int i=0; i < cat.length(); i++) {
                JSONObject struct = cat.getJSONObject(i);
                if(struct.getInt("id") == mapID) {
                    JSONArray t = struct.getJSONArray(diceResult);
                    for(int j=0; j < 3; j++)
                        tiles[j] = t.getInt(j);
                    break;
                }
            }

        } catch(JSONException | NullPointerException | IOException ex) {
            Log.e("pool", ex.toString());
            return tiles;
        }

        return tiles;
    }

    /**
     * Loads the actual JSON file.
     *
     * @param mgr AssetManager needed to read JSON file.
     */
    private static void loadStructurePool(AssetManager mgr) throws IOException {
        InputStream is = mgr.open("structures.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        int read = is.read(buffer);
        if(size != read)
            throw new IOException("failure at reading correct file size");
        is.close();

        json = new String(buffer, StandardCharsets.UTF_8);
    }

    static boolean[][] getStandardStructure() {
        return standardStructure;
    }
}
