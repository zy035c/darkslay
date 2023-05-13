package com.woodburn.darkslay.creatures;

import javax.swing.plaf.basic.BasicSliderUI.ScrollListener;

import com.woodburn.darkslay.scaling.StatusObject;

public class AbstractPlayer {

    /**
     * Classes, like character-type, of the player
     */
    public enum PlayerClass {
        Ironclad, Silent, Defect, Watcher, Dragonslayer, Seeker;
    }

    /**
     * Status object, status for scaling, new feature for future implementation
     */
    public StatusObject statusObj;


    public int energy;
}
