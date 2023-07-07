package Explosion;

import entityGerarchy.NotMovingAnimatedEntity;
import player.Player;

import java.awt.*;
import java.util.Observer;
import java.util.Random;

public class Flame extends NotMovingAnimatedEntity {

    public Flame(int worldPositionX, int worldPositionY, Observer o) {
        super(worldPositionX + 6, worldPositionY + 3, 48, 48, 0, 0, "/Explosion/Flame_F0", 5);
        //currentSpriteNum = r.nextInt(0, numSprites);
        addObserver(o);
    }

    public void updateAnimation() {
         //currentSpriteNum = r.nextInt(0, numSprites);
         setChanged();
         notifyObservers();
    }

    public void draw(Graphics2D g2, Player player) {

        int screenX = worldPositionX;
        int screenY = worldPositionY;

        //g2.drawImage(images.get(currentSpriteNum), screenX, screenY, 48, 48, null);

    }
}
