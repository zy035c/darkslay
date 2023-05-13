package com.woodburn.darkslay.creatures.characters;

import com.woodburn.darkslay.cards.Deck;
import com.woodburn.darkslay.creatures.AbstractPlayer.PlayerClass;
import com.woodburn.darkslay.helper.ImageMaster;

public class Dragonslayer extends Character {
    private static String[] TEXT = {
        "Dragonslayer",
        "Desc"
    };

    private static IroncladTest singleton;
    
    public static IroncladTest create() {
        if (singleton == null) {
            singleton = new IroncladTest();

            singleton.playerClass = PlayerClass.Dragonslayer;
            singleton.nameString = TEXT[0];
            singleton.description = TEXT[1];
            singleton.startingDeck = initDeck();

            singleton.portraitImg = ImageMaster.IRONCLAD_FULL;
            singleton.selectBtnImg = ImageMaster.WATCHER_BTN;

        }

        return singleton;
    }

    private static Deck initDeck() {
        return null;
    }
}
