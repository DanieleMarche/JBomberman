package Tile.tileGerarchy;

import Animation.*;
import main.Pausable;

import java.util.Observer;
import Thread.AnimatedEntityThread;

/**
 * The AnimatedTile class represents a tile in the game that has an associated animation.
 *
 * @Author Daniele Marchetilli
 */
public abstract class AnimatedTile extends Tile implements Observer, Pausable {

    protected AnimatedEntityThread animatedEntityThread;
    protected LoopAnimation loopAnimation;
    public Animate animate;

    /**
     * Constructs an AnimatedTile object with the specified tile type, row, column, and animation path.
     *
     * @param tileType      The type of the tile.
     * @param row           The row index of the tile.
     * @param col           The column index of the tile.
     * @param animationPath The path to the animation for this tile.
     */
    public AnimatedTile(TileType tileType, int row, int col, String animationPath) {
        super(tileType, row, col);
        this.loopAnimation = new LoopAnimation(animationPath, 5, this);

        animate = () -> {
            currentDisplayingImage = loopAnimation.getCurrentImage();
            loopAnimation.setNextSprite();
        };

        animatedEntityThread = new AnimatedEntityThread(loopAnimation, animate);
        animatedEntityThread.startThread();
    }

    /**
     * Retrieves the animation associated with this animated tile.
     *
     * @return The animation of the tile.
     */
    public LoopAnimation getAnimation() {
        return loopAnimation;
    }

    @Override
    public void pause() {
        animatedEntityThread.pause();
    }

    @Override
    public void resume() {
        animatedEntityThread.resume();
    }
}

