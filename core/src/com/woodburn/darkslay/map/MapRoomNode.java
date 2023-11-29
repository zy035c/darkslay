package com.woodburn.darkslay.map;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.HitBox;
import com.woodburn.darkslay.rooms.AbstractRoom;

public class MapRoomNode {
    
    public AbstractRoom room;
    public ArrayList<MapRoomNode> parents = new ArrayList<>();

    

    public int x;
    public int y;
    public HitBox hb;

    public MapRoomNode(int x, int y) {
        this.x = x;
        this.y = y;

        float hitbox_w = 64.0F * DisplayConfig.scale;
        this.hb = new HitBox(hitbox_w, hitbox_w);
    }

    public AbstractRoom getRoom() {
        return this.room;
    }


    /* Graph DS methods */
    public ArrayList<MapRoomNode> getParents() {
        return this.parents;
    }

    public void addParent(MapRoomNode parent) {
        this.parents.add(parent);
    }

    private float animWaitTimer = 0.0F;
    private static final float ANIM_WAIT_TIME = 0.25F;

    public void update() {
        if (this.animWaitTimer != 0.0F) {
            this.animWaitTimer -= Gdx.graphics.getDeltaTime();

            if (this.animWaitTimer < 0.0F) {
                /* Set the node to be chosen */
            }

        }
    }

}
