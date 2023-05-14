package com.woodburn.darkslay.localization;

import org.json.JSONObject;

public class UIStrings {


    public String[] TEXT;

    private UIStrings() {
        TEXT = new String[]{"", "Play", "", "", "", "", "", "Stat", "Quit", "", "", "", "Settings", ""};
    }

    /**
     * For main screen menu btn.. under construction
     */
    public static UIStrings getStr() {

        return new UIStrings();
    }



    /**
     * JsonReader. Get string from the json path
     * @param path
     */
    public static String getStringByPath(String path) {
        String[] keys = path.split("/");
        
        JSONObject cur = new JSONObject(Localization.getGeneralString());

        try {
            for (int i = 0; i < keys.length; ++i) {
                if (i == keys.length - 1) {
                    return cur.getString(keys[i]); 
                } else {
                    cur = cur.getJSONObject(keys[i]);
                }
            }
        } catch(Exception e) {
            System.out.println("[WARNING] Error when getting language string...");
            e.printStackTrace();
        }
       
        return cur.toString();
    }

    /* Test */
    public static void main(String[] args) {
        System.out.println("good");
    }

}
