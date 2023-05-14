package com.woodburn.darkslay.creatures.characters;

import com.badlogic.gdx.graphics.Texture;
import com.woodburn.darkslay.cards.Deck;
import com.woodburn.darkslay.creatures.AbstractPlayer.PlayerClass;
import com.woodburn.darkslay.helper.ImageMaster;

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
public class IroncladTest extends Character {
    
    

    private static IroncladTest singleton;
    
    public static IroncladTest create() {
        if (singleton == null) {
            singleton = new IroncladTest();

            singleton.playerClass = PlayerClass.Ironclad;
           
            singleton.startingDeck = initDeck();

            singleton.portraitImg = ImageMaster.IRONCLAD_FULL;
            singleton.selectBtnImg = ImageMaster.IRONCLAD_BTN;

        }

        return singleton;
    }

    private static Deck initDeck() {
        return null;
    }
}
