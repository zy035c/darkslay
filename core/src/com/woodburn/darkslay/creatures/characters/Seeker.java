package com.woodburn.darkslay.creatures.characters;

import com.woodburn.darkslay.cards.Deck;
import com.woodburn.darkslay.creatures.AbstractPlayer;
import com.woodburn.darkslay.creatures.AbstractPlayer.PlayerClass;
import com.woodburn.darkslay.helper.ImageMaster;

public class Seeker extends Character {

    private static String[] TEXT = {
        "Seeker",
        "Desc"
    };

    private static Seeker singleton;

    public static Seeker create() {
        if (singleton == null) {
            singleton = new Seeker();

            singleton.playerClass = PlayerClass.Seeker;
            singleton.nameString = TEXT[0];
            singleton.description = TEXT[1];
            singleton.startingDeck = initDeck();

            singleton.portraitImg = ImageMaster.WATCHER_FULL;
            singleton.selectBtnImg = ImageMaster.CROW_BTN;

        }

        return singleton;
    }

    private static Deck initDeck() {
        return null;
    }
}
