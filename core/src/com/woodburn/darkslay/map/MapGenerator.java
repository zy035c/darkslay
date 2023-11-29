package com.woodburn.darkslay.map;

import java.util.ArrayList;

public class MapGenerator {
    
    /**
     * @since Jun 1 2023
     * @doc
     * Generate a test map, 1 campfire and 3 monster rooms in a vertical line
     * @return
     */
    public static ArrayList<ArrayList<MapRoomNode>> generateTestMap() {
        return createNodes(4, 1);
    }

    private static ArrayList<ArrayList<MapRoomNode>> createNodes(int height, int width) {
        ArrayList<ArrayList<MapRoomNode>> nodes = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            ArrayList<MapRoomNode> row = new ArrayList<>();
            for (int x = 0; x < width; x++) {
                row.add(new MapRoomNode(x, y));
            }
            nodes.add(row);
        }
        return nodes;
    }
}
