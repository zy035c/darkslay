package com.woodburn.darkslay.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
// import org.apache.logging.log4j.Logger;

public class ImageMaster {

    // private static final Logger logger = LogManager.getLogger(ImageMaster.class.getName());


    public static TextureAtlas vfxAtlas;

    public static TextureAtlas.AtlasRegion ATK_SLASH_H;
    public static TextureAtlas.AtlasRegion ATK_SLASH_V;
    public static TextureAtlas.AtlasRegion ATK_SLASH_D;

    public static Texture WHITE_SQUARE_IMG;




    /**
     * Add on May 10, for main screen panels
     */
    public static Texture P_STANDARD;
    public static Texture P_LOOP;
    public static Texture P_DAILY;
    public static Texture P_LOCK;

    public static Texture PANEL_BG_GRAY;
    public static Texture PANEL_BG_BLUE;
    public static Texture PANEL_BG_RED;
    public static Texture PANEL_BG_BEIGE;
    public static Texture PANEL_FRAME;

    /**************************************** INITIALIZE STARTS ****************************************************/
    public static void initialize() {

        long startTime = System.currentTimeMillis();

        vfxAtlas = new TextureAtlas(Gdx.files.internal("slay_spire/battle_ui/vfx/vfx.atlas"));

        ATK_SLASH_H = vfxAtlas.findRegion("attack/slash_horizontal");
        ATK_SLASH_V = vfxAtlas.findRegion("attack/slash_vertical");


//        INTENT_ATK_1 = loadImage("images/ui/intent/attack/attack_intent_1.png");
//        INTENT_ATK_2 = loadImage("images/ui/intent/attack/attack_intent_2.png");
//        INTENT_ATK_3 = loadImage("images/ui/intent/attack/attack_intent_3.png");
//        INTENT_ATK_4 = loadImage("images/ui/intent/attack/attack_intent_4.png");
//        INTENT_ATK_5 = loadImage("images/ui/intent/attack/attack_intent_5.png");
//        INTENT_ATK_6 = loadImage("images/ui/intent/attack/attack_intent_6.png");
//        INTENT_ATK_7 = loadImage("images/ui/intent/attack/attack_intent_7.png");

        // DEBUG HITBOX

        WHITE_SQUARE_IMG = loadImage("slay_spire/other/whiteSquare32.png", false);

        /**
         * Add on May 10, for main screen panels
         */
        P_STANDARD = loadImage("slay_spire/menu_ui/play_panel_ui/standard.jpg");
        P_LOOP = loadImage("slay_spire/menu_ui/play_panel_ui/loop.jpg");
        P_LOCK = loadImage("slay_spire/menu_ui/play_panel_ui/lock.png");
        P_DAILY = loadImage("slay_spire/menu_ui/play_panel_ui/daily.jpg");

        PANEL_BG_BLUE = loadImage("slay_spire/menu_ui/play_panel_ui/menuPanelBlue.png");
        PANEL_BG_BEIGE = loadImage("slay_spire/menu_ui/play_panel_ui/menuPanelBeige.png");
        PANEL_BG_RED = loadImage("slay_spire/menu_ui/play_panel_ui/menuPanelRed.png");
        PANEL_BG_GRAY = loadImage("slay_spire/menu_ui/play_panel_ui/menuPanelGray.png");
        PANEL_FRAME = loadImage("slay_spire/menu_ui/play_panel_ui/menuPanelFrame.png");
    }
    /**************************************** INITIALIZE ENDS ****************************************************/


    public static Texture loadImage(String imgUrl) {

        assert imgUrl != null : "DO NOT CALL LOAD IMAGE WITH NULL";

        try {
            Texture retVal = new Texture(imgUrl);
            retVal.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            return retVal;
        } catch (Exception e) {
            // Logger.info("[WARNING] No image at " + imgUrl);
            System.out.println("[WARNING] No image at " + imgUrl);
            return null;
        }
    }

    public static Texture loadImage(String imgUrl, boolean linearFiltering) {

        assert imgUrl != null : "DO NOT CALL LOAD IMAGE WITH NULL";

        try {
            Texture retVal = new Texture(imgUrl);
            if (linearFiltering) retVal.setFilter(
                    Texture.TextureFilter.Linear,
                    Texture.TextureFilter.Linear
            );
            else retVal.setFilter(
                    Texture.TextureFilter.Nearest,
                    Texture.TextureFilter.Nearest
            );
            return retVal;
        } catch (Exception e) {
            // logger.info("[WARNING] No image at " + imgUrl);
            System.out.println("[WARNING] No image at " + imgUrl);
            return null;
        }
    }

}
