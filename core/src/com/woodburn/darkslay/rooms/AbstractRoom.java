package com.woodburn.darkslay.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public abstract class AbstractRoom implements Disposable {

    // public MonsterGroup monsters;

    protected Texture mapImg;
    public boolean isBattleOver;

    public enum RoomType {
        MONSTER, EVENT;
    }



}
