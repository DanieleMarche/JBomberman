package Bomb;

import entityGerarchy.NotMovingAnimatedEntity;
import player.Player;
import Animation.*;
import java.awt.*;


public class Bomb extends NotMovingAnimatedEntity {

    private Animation currentAnimation;
    private final Animation bombExplosionAnimation;
    private boolean exploded;
    private boolean passedInto;
    private int seconds;
    private final int explosionTime;

    public Bomb(int worldX, int worldY) {
        super(worldX, worldY, 45, 40, 0, 0, "/Bomb/Bomb_0", 3);

        exploded = false;

        passedInto = false;
        explosionTime = 120;
        currentAnimation = animation;
        bombExplosionAnimation = new Animation("/Bomb/Bomb_Explosion/bomb_explosion_0", 5, 0);

    }



    public void explode() {
        exploded = true;
        currentAnimation = bombExplosionAnimation;
        resetSecondCounter();
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void resetSecondCounter() {
        seconds = 0;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getExplosionTime() {
        return explosionTime;
    }

    public void increaseSeconds() {
        seconds++;
    }

    public boolean isPassedInto() {
        return passedInto;
    }
    public void hasPassedInto() {
        passedInto = true;
    }

    public void draw(Graphics2D g2, Player player) {
        int screenX = worldPositionX;
        int screenY = worldPositionY;

        g2.drawImage(currentAnimation.getCurrentImage(), screenX, screenY, 48, 48, null);

    }
}
