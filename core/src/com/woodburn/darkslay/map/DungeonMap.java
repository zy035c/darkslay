package com.woodburn.darkslay.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.woodburn.darkslay.dungeons.AbstractDungeon;
import com.woodburn.darkslay.global_config.DisplayConfig;
import com.woodburn.darkslay.helper.ImageMaster;
import com.woodburn.darkslay.helper.MathHelper;

public class DungeonMap {

    private static Texture top;
    private static Texture mid;
    private static Texture bot;
    private static Texture blend;
    
    /**
     * A map. User can scroll up and down
     */
    public DungeonMap() {
        if (top == null) {
            top = ImageMaster.loadImage("slay_spire/map/mapTop.png");
            mid = ImageMaster.loadImage("slay_spire/map/mapMid.png");
            bot = ImageMaster.loadImage("slay_spire/map/mapBot.png");
            blend = ImageMaster.loadImage("slay_spire/map/mapBlend.png");
        }
        // this.bossHb = new Hitbox(400.0F * DisplayConfig.scale, 360.0F * DisplayConfig.scale);
    }

    public Legend legend = new Legend();

    private class Legend {
        
    }

    public void hide() { targetAlpha = 0.0F; }

    public float targetAlpha = 0.0F;

    public void update() {
        // this.legend.update(this.baseMapColor.a, (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP));
        this.baseMapColor.a = MathHelper.fadeLerpSnap(this.baseMapColor.a, this.targetAlpha);

        // this.bossHb.move(DisplayConfig.WIDTH / 2.0F, DungeonMapScreen.offsetY + mapOffsetY + BOSS_OFFSET_Y + BOSS_W / 2.0F);
        // this.bossHb.update();
        updateReticle();

        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMPLETE
                && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP && (DisplayConfig.isDebug ||
                        (AbstractDungeon.getCurrMapNode()).y == 14 || (AbstractDungeon.id
                                .equals("TheEnding") && (AbstractDungeon.getCurrMapNode()).y == 2))) {
            if (this.bossHb.hovered && (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())) {

                (AbstractDungeon.getCurrMapNode()).taken = true;
                MapRoomNode node2 = AbstractDungeon.getCurrMapNode();
                for (MapEdge e : node2.getEdges()) {
                    if (e != null) {
                        e.markAsTaken();
                    }
                }

                InputHelper.justClickedLeft = false;
                CardCrawlGame.music.fadeOutTempBGM();
                MapRoomNode node = new MapRoomNode(-1, 15);
                node.room = (AbstractRoom) new MonsterRoomBoss();
                AbstractDungeon.nextRoom = node;

                if (AbstractDungeon.pathY.size() > 1) {
                    AbstractDungeon.pathX.add(AbstractDungeon.pathX.get(AbstractDungeon.pathX.size() - 1));
                    AbstractDungeon.pathY.add(Integer.valueOf(
                            ((Integer) AbstractDungeon.pathY.get(AbstractDungeon.pathY.size() - 1)).intValue() + 1));
                } else {
                    AbstractDungeon.pathX.add(Integer.valueOf(1));
                    AbstractDungeon.pathY.add(Integer.valueOf(15));
                }

                AbstractDungeon.nextRoomTransitionStart();
                this.bossHb.hovered = false;
            }
        }

        if (this.bossHb.hovered || this.atBoss) {
            this.bossNodeColor = MapRoomNode.AVAILABLE_COLOR.cpy();
        } else {
            this.bossNodeColor.lerp(NOT_TAKEN_COLOR, Gdx.graphics.getDeltaTime() * 8.0F);
        }

        this.bossNodeColor.a = this.baseMapColor.a;
    }

    private Color baseMapColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);

    private static float mapOffsetY;
    private static final float H = 1020.0F * DisplayConfig.scale;
    private static final float BLEND_H = 512.0F * DisplayConfig.scale;

    private static float mapMidDist;

    private void renderNormalMap(SpriteBatch sb) {
        sb.setColor(this.baseMapColor);

   
        sb.draw(
            top, 
            0.0F, 
            H + DungeonMapScreen.offsetY + mapOffsetY, 
            DisplayConfig.WIDTH, 
            1080.0F * DisplayConfig.scale
        );
       
        renderMapCenters(sb);

        sb.draw(
            bot, 
            0.0F, 
            -this.mapMidDist + DungeonMapScreen.offsetY + mapOffsetY + 1.0F, 
            DisplayConfig.WIDTH,
            1080.0F * DisplayConfig.scale
        );

        renderMapBlender(sb);
        // this.legend.render(sb);
    }

    private Color reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);

    private void renderMapCenters(SpriteBatch sb) {

    }

    private void renderMapBlender(SpriteBatch sb) {

    }

    private void updateReticle() {
        if (this.bossHb.hovered) {
            this.reticleColor.a += Gdx.graphics.getDeltaTime() * 3.0F;

            if (this.reticleColor.a > 1.0F) {
                this.reticleColor.a = 1.0F;
            }
        } else {
            this.reticleColor.a = 0.0F;
        }
    }

}
