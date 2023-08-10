package tile.tileGerarchy;

import Animation.Animation;
import main.GamePanel;
import tile.TileType;
import tile.tileGerarchy.Tile;

import java.awt.*;
import java.util.Observer;

public abstract class AnimatedTile extends Tile implements Observer {

    protected Animation animation;

    public AnimatedTile(TileType tileType, int row, int col, boolean solid, boolean explodable, boolean getFire, String animationPath) {
        super(tileType, row, col, solid, explodable, getFire);
        this.animation = new Animation(animationPath, 10, this);
    }

    public Animation getAnimation() {
        return animation;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(animation.getCurrentImage(), getPositionXOnScreen(), getPositionYOnScreen(), GamePanel.tileSize, GamePanel.tileSize, null);
    }
}
