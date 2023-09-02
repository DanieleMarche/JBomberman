package Tile.tileGerarchy;

import Animation.*;
import main.GameView;
import Tile.TileType;

import java.awt.*;
import java.util.Observer;

public abstract class AnimatedTile extends Tile implements Observer {

    protected LoopAnimation loopAnimation;
    protected int updateCallCounter;
    public Animate animate;

    public AnimatedTile(TileType tileType, int row, int col, String animationPath) {
        super(tileType, row, col);
        this.loopAnimation = new LoopAnimation(animationPath, 10, this);
        updateCallCounter = 0;
    }

    public LoopAnimation getAnimation() {
        return loopAnimation;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(loopAnimation.getCurrentImage(), getPositionXOnScreen(), getPositionYOnScreen(), GameView.tileSize, GameView.tileSize, null);
    }
}
