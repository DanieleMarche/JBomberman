package entityGerarchy;

public abstract class MovingLiveEntity extends MovingEntity implements Dieable {

    protected MovingAliveEntityState state;

    public MovingLiveEntity(int worldPositionX, int worldPositionY, int speed, int height, int width, int solidAreaDefaultX, int solidAreaDefaultY, int solidAreaHeight, int solidAreaWidth, String directoryName, int defautlSpriteNum, int animationSpeed) {
        super(worldPositionX, worldPositionY, speed, height, width, solidAreaDefaultX, solidAreaDefaultY, solidAreaHeight, solidAreaWidth, directoryName, defautlSpriteNum, animationSpeed);
    }

    public MovingAliveEntityState getState() {
        return state;
    }
}
