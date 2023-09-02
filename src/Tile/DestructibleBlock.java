package Tile;

import Animation.CycledAnimation;
import Controllers.AssetManager;
import Controllers.DestructibleBlocksController;
import Portal.Portal;
import main.Scored;
import Tile.tileGerarchy.AnimatedTile;
import PowerUp.PowerUp;

import java.util.Observable;


public class DestructibleBlock extends AnimatedTile implements Scored {

    private boolean exploded;
    private PowerUp powerUp;
    private Portal portal;

    public DestructibleBlock(int row, int col, boolean imageType) {
        super(TileType.DESTRUCTIBLE_BLOCK, row, col, findPath(imageType));

        exploded = false;

        addObserver(DestructibleBlocksController.getInstance());

        animate = () -> {
            updateCallCounter++;
            if(updateCallCounter % loopAnimation.getAnimationSpeed() == 0) {
                loopAnimation.setNextSprite();
            }
        };

        AssetManager.getInstance().addDestructibleBlock(this);

    }

    public static String findPath(boolean type) {

        if(type) return "res/Blocks/destructable_block/with_shadow";

        return "res/Blocks/destructable_block";

    }

    public boolean hasPoweUp() {
        return powerUp != null;
    }

    public boolean hasPortal() {
        return portal != null;
    }

    public void addPortal(Portal portal) {
        if(portal.getCol() == this.col && portal.getRow() == this.row) this.portal = portal;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public void addPowerUp(PowerUp powerUp) {
        if(this.powerUp == null) {
            this.powerUp = powerUp;
        }
    }

    public void setPowerUpVisibleIfPresent() {
        if(powerUp != null) {
            powerUp.setVisible();
        }
    }

    public boolean isExploded() {
        return exploded;
    }

    public void explode() {
        exploded = true;
        loopAnimation = new CycledAnimation("res/Explosion/Destructible_block_explosion", 10, this, 1);
        System.out.println(countObservers());
    }

    public static DestructibleBlock getDestructibleBlock(Map map, int row, int col) {
        DestructibleBlock destructibleBlock;
        if(map.getMapTileType(row - 1, col) == TileType.SOLID_BLOCK || map.getMapTileType(row - 1, col) == TileType.LIMIT_BLOCK) destructibleBlock = new DestructibleBlock(row, col, true);
        else destructibleBlock = new DestructibleBlock(row, col, false);
        return destructibleBlock;
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    @Override
    public int getPoints() {
        return 10;
    }

}
