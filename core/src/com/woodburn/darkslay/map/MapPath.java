import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapPath implements Comparable<MapPath> {

    public final int dstX;
    public final int dstY;
    public final int srcX;
    public final int srcY;

    public MapPath(int srcX, int srcY, int dstX, int dstY) {
        this.srcX = srcX;
        this.srcY = srcY;
        this.dstX = dstX;
        this.dstY = dstY;
    }

    
    public int compareTo(MapPath e) {

        if (this.dstX > e.dstX) return 1;
        if (this.dstX < e.dstX) return -1;

        if (this.dstY > e.dstY) return 1;
        if (this.dstY < e.dstY) return -1;
        if (this.dstY == e.dstY) return 0;

        assert false; /* throw exception?! */
        return 0;
    }

    /**
     * Render line between room and room on a map?!
     */
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        for (MapDot d : this.dots) {
            d.render(sb);
        }
    }

}