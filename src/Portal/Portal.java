package Portal;

import Animation.AnimationMessages;
import Bomberman.Player;
import Controllers.CollisionDetector;
import Controllers.PortalController;
import enemy.Enemy;
import entityGerarchy.NotMovingEntity;
import main.GameView;

import java.awt.*;

public class Portal extends NotMovingEntity {

    private static Portal instance = null;

    private static final String imagePath = "res/Blocks/portal.png";

    private boolean visibility;

    public static Portal getInstance() {
        return instance;
    }

    public static Portal getInstance(int row, int col) {
        if (instance == null) {
            instance = new Portal(row, col);
        }
        return instance;

    }

    private Portal(int row, int col) {
        super(col * GameView.tileSize, row * GameView.tileSize, imagePath);
        visibility = false;

        addObserver(PortalController.getInstance());
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    @Override
    public void draw(Graphics2D g2) {
        if(visibility) {
            g2.drawImage(image, worldPositionX, worldPositionY, width, height, null);
        }
    }

    public static void removeInstance() {
        instance = null;
    }

}
