package tile.tileGerarchy;

import Animation.Animation;
import main.GamePanel;
import tile.TileType;
import tile.tileGerarchy.Tile;

import java.awt.*;

public abstract class AnimatedTile extends Tile {

    protected Animation animation;

    public AnimatedTile(TileType tileType, int row, int col, boolean solid, boolean explodable, boolean getFire, String animationPath, int spritesNumber) {
        super(tileType, row, col, solid, explodable, getFire);
        this.animation = new Animation(animationPath, spritesNumber, 0);
    }

    public Animation getAnimation() {
        return animation;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(animation.getCurrentImage(), getPositionXOnScreen(), getPositionYOnScreen(), GamePanel.tileSize, GamePanel.tileSize, null);
    }
}
