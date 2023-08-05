package tile;

import Animation.Animation;
import main.GamePanel;

import java.awt.*;

public abstract class AnimatedTile extends Tile{

    protected Animation animation;

    public AnimatedTile(int row, int col, boolean solid, boolean explodable, boolean getFire, String animationPath, int spritesNumber) {
        super(row, col, solid, explodable, getFire);
        this.animation = new Animation(animationPath, spritesNumber, 0);
    }

    public Animation getAnimation() {
        return animation;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(animation.getCurrentImage(), getPositionXOnScreen(), getPositionYOnScreen(), GamePanel.tileSize, GamePanel.tileSize, null);
    }
}
