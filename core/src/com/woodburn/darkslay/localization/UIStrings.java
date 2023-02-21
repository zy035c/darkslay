package com.woodburn.darkslay.localization;

public class UIStrings {


    public String[] TEXT;

    private UIStrings() {
        TEXT = new String[]{"", "Play", "", "", "", "", "", "Stat", "Quit", "", "", "", "Settings", ""};
    }


    public static UIStrings getUIString(String str) {

        return new UIStrings();
    }
}
