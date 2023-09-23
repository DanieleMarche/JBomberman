package EntityModelGerarchy;

/**
 * This class defines an abstract moving entity thas is alive and because of this it has a movingAliveEntityState
 */
public abstract class MovingLiveEntity extends MovingEntity implements Dieable {

    /**
     * The current state of the moving alive entity.
     */
    protected MovingAliveEntityState state;

    public MovingLiveEntity(int worldPositionX, int worldPositionY, int speed, int height, int width, int solidAreaDefaultX, int solidAreaDefaultY, int solidAreaHeight, int solidAreaWidth, String directoryName, int defautlSpriteNum, int animationSpeed) {
        super(worldPositionX, worldPositionY, speed, height, width, solidAreaDefaultX, solidAreaDefaultY, solidAreaHeight, solidAreaWidth, directoryName, defautlSpriteNum, animationSpeed);
    }

    public MovingAliveEntityState getState() {
        return state;
    }
}
