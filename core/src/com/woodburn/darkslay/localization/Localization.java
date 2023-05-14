package com.woodburn.darkslay.localization;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.woodburn.darkslay.global_config.Settings;
import com.woodburn.darkslay.global_config.Settings.lang;
import com.woodburn.darkslay.localization.UIStrings;

public class Localization {

    private static String ENG_PATH = "languages/eng/en.json"; 

    public static void initialize() {
        
    }

    public static String getGeneralString() {

        if (Settings.language == lang.ENG) {

        } else {

        }

        FileHandle f = Gdx.files.internal(ENG_PATH);

        String str = null;

        if (f.exists()) {
            str = f.readString(String.valueOf(StandardCharsets.UTF_8));
        }

        return str;
    }
    /**
     * No use.
     * @param args
     */
    public static void main(String[] args) {
        FileHandle f = Gdx.files.internal("drop.png");
        if (f.exists()) {
            System.out.println("Yes!");
        } else {
            System.out.println("NO..");
        }
    }

}
