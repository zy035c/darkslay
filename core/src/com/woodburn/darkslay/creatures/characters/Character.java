package com.woodburn.darkslay.creatures.characters;

import com.badlogic.gdx.graphics.Texture;
import com.woodburn.darkslay.cards.Deck;
import com.woodburn.darkslay.creatures.AbstractPlayer;
import com.woodburn.darkslay.creatures.AbstractPlayer.PlayerClass;


/**
 * Elements that are different for specified character:
 * + Playerclass
 * + nameString
 * + desc
 * + selectImg
 * + in-game texture, skeleton
 * + initMaxHealth
 * + startingDeck
 * + startingRing
 * + startingItem
 * + startingGold
 */
public abstract class Character {

    public PlayerClass playerClass;
    
    public Texture portraitImg;   
    public Texture selectBtnImg;

    public String nameString;
    public String description;
    
    public Deck startingDeck;

    public int startingMaxHealth = 100;
    public int startingGold = 100;

    /**
     * Return the singleton of the Character that is of the playerClass
     */
    public static Character instantiate(PlayerClass p) {
        switch (p) {
            case Ironclad:
                return IroncladTest.create();
            case Seeker:
                return Seeker.create();
            case Silent:
                return Silent.create();
            case Dragonslayer:
                return Dragonslayer.create();

            default:
                System.out.println("[WARNING] Unimplemented character is requested");
                return null;
        }
    }



}
