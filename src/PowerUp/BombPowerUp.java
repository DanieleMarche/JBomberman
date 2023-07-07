package PowerUp;

public class BombPowerUp extends PowerUp {

    public BombPowerUp( int worldPostionX, int worldPositionY) {
        super("/Powerups/BombPowerup.png", worldPostionX, worldPositionY, PowerUpType.MORE_BOMB_AT_SAME_TIME);
    }
}
