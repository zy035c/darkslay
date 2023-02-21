package com.woodburn.darkslay.helper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HitBox {

    public float x;
    public float y;

    public float width;
    public float height;

    public boolean hovered = false;
    public boolean justHovered = false;
    public boolean clickStarted = false;
    public boolean clicked = false;


    // center
    public float cX;
    public float cY;

    public HitBox(float width, float height) {
        this.width = width;
        this.height = height;

        cX = x + this.width / 2.0F;
        cY = y + this.height / 2.0F;
    }


    public void update() {
        update(this.x, this.y); // do location update
        // below is mouse input update

        if (this.clickStarted && InputHelper.justReleasedClickLeft) {
            if (this.hovered) {
                this.clicked = true;
            }
            // this.clickStarted = true;
        }


    }

    public void update(float x, float y) {

        this.x = x;
        this.y = y;

        if (justHovered) justHovered = false;

        if (!this.hovered) {
            this.hovered = (InputHelper.mX > x &&
                    InputHelper.mX < x + this.width &&
                    InputHelper.mY > y &&
                    InputHelper.mY < y + this.height);

            if (this.hovered) this.justHovered = true;

        } else {
            this.hovered = (InputHelper.mX > x &&
                    InputHelper.mX < x + this.width &&
                    InputHelper.mY > y &&
                    InputHelper.mY < y + this.height);
        }

    }

    public void move(float x, float y) {
        this.cX = x;
        this.cY = y;
        this.x = cX - this.width / 2.0F;
        this.y = cY - this.height / 2.0F;
    }

    public void render(SpriteBatch sb) {
        // debug mode
        // sb.draw(ImageMaster.DEBUG_HITBOX_IMG, this.x, this.y, this.width, this.height);
    }

    public void unhover() {
        this.hovered = false;
        this.justHovered = false;
    }

}
