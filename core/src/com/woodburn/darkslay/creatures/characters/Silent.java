package com.woodburn.darkslay.creatures.characters;

import com.woodburn.darkslay.cards.Deck;
import com.woodburn.darkslay.creatures.AbstractPlayer.PlayerClass;
import com.woodburn.darkslay.helper.ImageMaster;

public class Silent extends Character {

    private static String[] TEXT = {
        "Silent",
        "Desc"
    };

    private static Silent singleton;

    public static Silent create() {
        if (singleton == null) {
            singleton = new Silent();

            singleton.playerClass = PlayerClass.Silent;
            singleton.nameString = TEXT[0];
            singleton.description = TEXT[1];
            singleton.startingDeck = initDeck();

            singleton.portraitImg = ImageMaster.SILENT_FULL; // 
            singleton.selectBtnImg = ImageMaster.SILENT_BTN;

        }

        return singleton;
    }

    private static Deck initDeck() {
        return null;
    }
}