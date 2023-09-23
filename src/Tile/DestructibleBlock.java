package Tile;

import Animation.AnimationMessages;
import Animation.CycledAnimation;
import Controllers.AssetManager;
import Controllers.DestructibleBlocksController;
import Portal.PortalModel;
import PowerUp.PowerUpModel;
import Tile.tileGerarchy.TileType;
import main.Scored;
import Tile.tileGerarchy.AnimatedTile;

import java.util.Observable;
import java.util.Optional;


/**
 * The DestructibleBlock class represents a destructible block on the game map.
 * Destructible blocks can explode and potentially reveal power-ups or portals when destroyed.
 *
 * @Author Daniele Marchetilli
 */
public class DestructibleBlock extends AnimatedTile implements Scored {
    private PowerUpModel powerUpModel;
    private PortalModel portalModel;

    /**
     * Constructs a DestructibleBlock object at the specified row and column on the game map.
     *
     * @param row       The row index of the destructible block.
     * @param col       The column index of the destructible block.
     * @param type      The type of destructible block.
     */
    public DestructibleBlock(int row, int col, DestructibleBlockType type) {
        super(TileType.DESTRUCTIBLE_BLOCK, row, col, type.imagePath);

        addObserver(DestructibleBlocksController.getInstance());

        AssetManager.getInstance().addDestructibleBlock(this);
    }

    public void addPortal(PortalModel portalModel) {
        if(portalModel.getCol() == this.getCol() && portalModel.getRow() == this.getRow()) this.portalModel = portalModel;
    }

    public Optional<PortalModel> getPortal() {
        return Optional.ofNullable(portalModel);
    }

    public Optional<PowerUpModel> getPowerUp() {
        return Optional.ofNullable(powerUpModel);
    }

    public void addPowerUp(PowerUpModel powerUpModel) {
        if(this.powerUpModel == null) {
            this.powerUpModel = powerUpModel;
        }
    }

    private void setPowerUpVisibleIfPresent() {
        if(powerUpModel != null) {
            powerUpModel.setVisiblility(true);
        }
    }

    private void setPortalVisibleIfPresent() {
        if(portalModel != null) {
            portalModel.setVisiblility(true);
        }
    }

    public void explode() {
        loopAnimation = new CycledAnimation("res/Explosion/Destructible_block_explosion", 5, this, 1);
        animatedEntityThread.setAnimation(loopAnimation);
    }

    public static DestructibleBlock getDestructibleBlock(Map map, int row, int col) {

        DestructibleBlock destructibleBlock;

        if(map.getMapTileType(row - 1, col) == TileType.SOLID_BLOCK || map.getMapTileType(row - 1, col) == TileType.LIMIT_BLOCK) {

            destructibleBlock = new DestructibleBlock(row, col, DestructibleBlockType.DESTRUCTIBLE_BLOCK_WITH_SHADOW);

        }
        else {

            destructibleBlock = new DestructibleBlock(row, col, DestructibleBlockType.DESTRUCTIBLE_BLOCK);

        }

        return destructibleBlock;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg == AnimationMessages.REMOVE_ELEMENT) {
            setPowerUpVisibleIfPresent();
            setPortalVisibleIfPresent();
        }
        setChanged();
        notifyObservers(arg);
    }

    @Override
    public int getPoints() {
        return 10;
    }

    public enum DestructibleBlockType {

        DESTRUCTIBLE_BLOCK("res/Blocks/destructable_block"),
        DESTRUCTIBLE_BLOCK_WITH_SHADOW("res/Blocks/destructable_block/with_shadow");

        private final String imagePath;

        DestructibleBlockType(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getImagePath() {
            return imagePath;
        }
    }

}
