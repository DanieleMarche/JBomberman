package PowerUp;

import main.Scored;

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
