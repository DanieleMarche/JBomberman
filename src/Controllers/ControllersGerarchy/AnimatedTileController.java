package Controllers.ControllersGerarchy;

import main.GamePanel;
import tile.DestructibleBlock;
import tile.tileGerarchy.AnimatedTile;

import java.util.ArrayList;
import java.util.Observer;

public abstract class AnimatedTileController<T extends AnimatedTile> implements Observer {

    protected GamePanel gamePanel;
    protected ArrayList<T> animatedTiles;

    public AnimatedTileController(GamePanel gamePanel, ArrayList<T> collection) {
        this.gamePanel = gamePanel;
        animatedTiles = collection;
    }

    public void updateAnimation() {
        ArrayList<AnimatedTile> arrayListCopy = new ArrayList<>(animatedTiles);

        arrayListCopy.forEach(element -> element.animate.updateAnimation());
    }

}
