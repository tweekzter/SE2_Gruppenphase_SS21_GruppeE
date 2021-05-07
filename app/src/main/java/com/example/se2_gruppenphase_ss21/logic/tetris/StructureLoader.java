package com.example.se2_gruppenphase_ss21.logic.tetris;

import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

class StructureLoader {
    private ArrayList<ArrayList<ArrayList<Boolean>>> pool = new ArrayList<>();
    private AssetManager mgr;
    // @TODO replace this prototype map with an existing map
    private static boolean[][] standardStructure = {
            { false, false, false, false },
            { false, true,  true,  false },
            { false, true,  true,  false },
            { false, false, false, false }
    };

    StructureLoader(AssetManager mgr) {
        this.mgr = mgr;
        load();
    }

    void load() {
        if(!pool.isEmpty())
            clearPool();

        try {
            InputStream is = mgr.open("mapPool.cfg");
            Scanner scanner = new Scanner(is);
            int poolCount = 0;
            int y = 0;
            pool.add(new ArrayList<>());

            while (scanner.hasNext()) {
                String l = scanner.nextLine();
                l = l.trim();

                if (l.length() == 0)
                    continue;
                if (l.charAt(0) == '#') {
                    poolCount++;
                    y = 0;
                    pool.add(new ArrayList<>());
                    continue;
                }

                pool.get(poolCount).add(new ArrayList<>());
                char[] line = l.toCharArray();
                for (char c : line) {
                    if (c == 'O' || c == 'o')
                        pool.get(poolCount).get(y).add(true);
                    else
                        pool.get(poolCount).get(y).add(false);
                }
                y++;
            }

            is.close();
            scanner.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            // in case of IO Exception - fill mapPool with standard map
            pool.add(new ArrayList<>());
            for(int y=0; y < standardStructure.length; y++) {
                pool.get(0).add(new ArrayList<>());
                for(int x=0; x < standardStructure[y].length; x++) {
                    pool.get(0).get(y).add(standardStructure[y][x]);
                }
            }
        }
    }

    boolean[][] getStructureByID(int id) {
        if(pool.size() == 0 || id >= pool.size())
            return standardStructure;

        int ySize = pool.get(id).size();
        int xSize = pool.get(id).get(0).size();
        boolean[][] map = new boolean[ySize][xSize];

        for(int y=0; y < ySize; y++) {
            for(int x=0; (x < xSize) && (x < pool.get(id).get(y).size()); x++) {
                map[y][x] = pool.get(id).get(y).get(x);
            }
        }
        return map;
    }

    void clearPool() {
        pool = new ArrayList<>();
    }
}
