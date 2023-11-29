package com.woodburn.darkslay.map;

import java.util.ArrayList;

import com.woodburn.darkslay.dungeons.AbstractDungeon;
import com.woodburn.darkslay.helper.HitBox;

public class DungeonMapScreen {
    


    private ArrayList<MapRoomNode> nodes = new ArrayList<>();
    public DungeonMap map = new DungeonMap();

    public DungeonMapScreen() {

    }


    public HitBox mapNodeHb;

    public void open(boolean doScrollingAnimation) {
        this.mapNodeHb = null;

        // if (!AbstractDungeon.id.equals("TheEnding")) {
        //     this.mapScrollUpperLimit = MAP_UPPER_SCROLL_DEFAULT;
        // } else {
        //     this.mapScrollUpperLimit = MAP_UPPER_SCROLL_FINAL_ACT;
        // }

        AbstractDungeon.player.releaseCard();
        
        this.map.legend.isLegendHighlighted = false;
        if (Settings.isDebug) {
            doScrollingAnimation = false;
        }

        InputHelper.justClickedLeft = false;
        this.clicked = false;
        this.clickTimer = 999.0F;
        this.grabbedScreen = false;

        AbstractDungeon.topPanel.unhoverHitboxes();
        this.map.show();
        this.dismissable = !doScrollingAnimation;
        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play("MAP_OPEN", 0.1F);
        } else {
            CardCrawlGame.sound.play("MAP_OPEN_2", 0.1F);
        }

        if (doScrollingAnimation) {
            this.mapNodeHb = null;
            AbstractDungeon.topLevelEffects
                    .add(new LevelTransitionTextOverlayEffect(AbstractDungeon.name, AbstractDungeon.levelNum));

            this.scrollWaitTimer = 4.0F;
            offsetY = this.mapScrollUpperLimit;
            this.targetOffsetY = MAP_SCROLL_LOWER;
        } else {
            this.scrollWaitTimer = 0.0F;
            AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
            if (AbstractDungeon.getCurrMapNode() == null) {
                offsetY = this.mapScrollUpperLimit;
            } else {
                offsetY = -50.0F * Settings.scale + (AbstractDungeon.getCurrMapNode()).y * -ICON_SPACING_Y;
            }
            this.targetOffsetY = offsetY;
        }

        AbstractDungeon.dynamicBanner.hide();
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.MAP;
        AbstractDungeon.isScreenUp = true;
        this.grabStartY = 0.0F;

        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.hideCombatPanels();
        AbstractDungeon.overlayMenu.endTurnButton.hide();
        AbstractDungeon.overlayMenu.showBlackScreen();
        updateImage();
    }



    /**
     * update scroller
     */
    public void update() {
        
        this.map.update();

        if (AbstractDungeon.isScreenUp) {
            for (MapRoomNode n : this.nodes) {
                n.update();
            }
        }


        
    }

}
