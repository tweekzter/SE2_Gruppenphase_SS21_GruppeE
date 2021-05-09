package com.example.se2_gruppenphase_ss21.logic.tetris;

import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

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
class StructureLoader {
    private static String json;
    // a default structure that will be used, whenever problems occur
    private static boolean[][] standardStructure = {
            { false, false, false, false },
            { false, true,  true,  false },
            { false, true,  true,  false },
            { false, false, false, false }
    };

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
        if(json == null)
            loadStructurePool(mgr);

        boolean[][] structure = null;
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject tp = obj.getJSONObject(type);
            JSONArray cat = tp.getJSONArray(category);
            for(int i=0; i < cat.length(); i++) {
                JSONObject struct = cat.getJSONObject(i);
                if (struct.getInt("id") == id) {
                    JSONArray line = struct.getJSONArray("shape");
                    for (int y = 0; y < line.length(); y++) {
                        JSONArray row = line.getJSONArray(y);
                        for (int x = 0; x < row.length(); x++) {
                            if (structure == null)
                                structure = new boolean[line.length()][row.length()];
                            structure[y][x] = row.getBoolean(x);
                        }
                    }
                }
            }

            if(structure == null || structure[0] == null)
                throw new NullPointerException();

        } catch (JSONException e) {
            e.printStackTrace();
            return standardStructure;
        } catch(NullPointerException ex) {
            Log.e("Structure", "loading failed - invalid ID");
            return standardStructure;
        }

        return structure;
    }

    /**
     * Loads the actual JSON file.
     *
     * @param mgr AssetManager needed to read JSON file.
     */
    private static void loadStructurePool(AssetManager mgr) {
        try {
            InputStream is = mgr.open("structures.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    static boolean[][] getStandardStructure() {
        return standardStructure;
    }
}
