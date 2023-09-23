package Portal;

import EntityModelGerarchy.NotMovingEntity;
import main.GameView;

/**
 * The Portal class represents a portal in the game that allows the player to progress to the next level when it becomes
 * visible. Portals are typically hidden until certain conditions are met.
 *
 * @Author Daniele Marchetilli
 */
public class PortalModel extends NotMovingEntity {

    private static PortalModel instance = null;

    private static final String imagePath = "res/Blocks/portal.png";

    /**
     * Retrieves the singleton instance of the Portal class. If the instance doesn't exist, it's created.
     *
     * @return The singleton instance of the Portal class.
     */
    public static PortalModel getInstance() {
        return instance;
    }

    /**
     * Retrieves or creates the singleton instance of the Portal class and sets its initial position.
     *
     * @param row The row where the portal is located.
     * @param col The column where the portal is located.
     * @return The singleton instance of the Portal class.
     */
    public static PortalModel getInstance(int row, int col) {
        if (instance == null) {
            instance = new PortalModel(row, col);
        }
        return instance;
    }

    /**
     * Constructs a Portal object at the specified row and column.
     *
     * @param row The row where the portal is located.
     * @param col The column where the portal is located.
     */
    private PortalModel(int row, int col) {
        super(col * GameView.tileSize, row * GameView.tileSize, imagePath);
        visible = false;

        addObserver(PortalController.getInstance());
    }

    /**
     * Removes the instance of the portal, effectively resetting it to null.
     */
    public static void removeInstance() {
        instance = null;
    }
}

