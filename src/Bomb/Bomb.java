package Bomb;

import entityGerarchy.NotMovingAnimatedEntity;
import player.Player;
import Animation.*;
import java.awt.*;
import java.util.ArrayList;


public class Bomb extends NotMovingAnimatedEntity {

    public static ArrayList<Bomb> bombs = new ArrayList<>();
    private boolean exploded;
    private boolean passedInto;
    private int seconds;
    private final int explosionTime;

    public static Bomb getInstance(int worldX, int worldY) {

        for(Bomb b: bombs) {
            if(b.getWorldPositionX() == worldX && b.getWorldPositionY() == worldY) {
                return b;
            }
        }

        Bomb b = new Bomb(worldX, worldY);
        bombs.add(b);
        return b;

    }

    private Bomb(int worldX, int worldY) {
        super(worldX, worldY, 48, 48, 0, 0, "/Bomb/Bomb_0", 3);

        exploded = false;

        passedInto = false;
        explosionTime = 240;
        //bombExplosionAnimation = new Animation("/Bomb/Bomb_Explosion/bomb_explosion_0", 5, 0);

    }

    public void explode() {
        exploded = true;
    }

    public boolean isExploded() {
        return exploded;
    }

    private void resetSecondCounter() {
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

        g2.drawImage(animation.getCurrentImage(), screenX, screenY, 48, 48, null);

    }
}
