package com.woodburn.darkslay.creatures.characters;

import com.badlogic.gdx.graphics.Texture;
import com.woodburn.darkslay.cards.Deck;
import com.woodburn.darkslay.creatures.AbstractPlayer;
import com.woodburn.darkslay.creatures.AbstractPlayer.PlayerClass;
import com.woodburn.darkslay.localization.UIStrings;


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

        Character character;

        switch (p) {
            case Ironclad:
                character = IroncladTest.create();
                initText(character, "IroncladTest");
                break;

            case Seeker:
                character = Seeker.create();
                initText(character, "Seeker");
                break;

            case Silent:
                character = Silent.create();
                initText(character, "Silent");
                break;

            case Dragonslayer:
                character = Dragonslayer.create();
                initText(character, "Dragonslayer");
                break;

            default:
                System.out.println("[WARNING] Unimplemented character is requested");
                return null;
        }
        return character;
    }

    /**
     * init text by localization
     * @param c
     * @param playerClassName
     */
    private static void initText(Character c, String playerClassName) {

        c.nameString = UIStrings.getStringByPath("characters/" + playerClassName + "/nameString");
        c.description = UIStrings.getStringByPath("characters/" + playerClassName + "/description");
    }



}
