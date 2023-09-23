package PowerUp;

import main.Scored;

/**
 * This enum class defines the various types of power ups present in this game.
 * every instance of this class containts the path to the directory that stores its animation
 * and the point that the power up release.
 */
public enum PowerUpType implements Scored {
    MORE_SPEED("res/Powerups/speed_power_up", 500),
    MORE_FLAME("res/Powerups/flame_power_up", 800),
    MORE_BOMB_AT_SAME_TIME("res/Powerups/bomb_power_up", 1000);

    private final String animationPath;
    private final int points;

    PowerUpType(String animationPath, int points) {

        this.animationPath = animationPath;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public String getAnimationPath() {
        return animationPath;
    }
}
