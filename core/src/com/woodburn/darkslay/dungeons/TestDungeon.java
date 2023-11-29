package com.woodburn.darkslay.dungeons;

import java.util.ArrayList;

import com.woodburn.darkslay.WoodBurnGame;
import com.woodburn.darkslay.map.MapGenerator;
import com.woodburn.darkslay.map.MapRoomNode;
import com.woodburn.darkslay.rooms.AbstractRoom;

public class TestDungeon extends AbstractDungeon {
    
    /**
     * Set WoodBurnGame.dungeon to itself
     */
    public TestDungeon() {

        WoodBurnGame.dungeon = this;
    }


    public static ArrayList<ArrayList<MapRoomNode>> map;

    protected static void generateMap() {
        ArrayList<AbstractRoom> roomList = new ArrayList<>();
        map = MapGenerator.generateDungeon();


    }

}
