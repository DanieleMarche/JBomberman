package Explosion;

import entityGerarchy.NotMovingAnimatedEntity;
import player.Player;

import java.awt.*;
import java.util.Observer;
import java.util.Random;

public class Flame extends NotMovingAnimatedEntity {

    protected FlameType flameType;

    public Flame(int worldPositionX, int worldPositionY,FlameType flameType, Observer observer) {
        super(worldPositionX, worldPositionY, GamePanel.tileSize, GamePanel.tileSize, 0, 0, flameType.getAnimationPath(), 10, observer);
        this.flameType = flameType;
        animation = new CycledReversedAnimation(flameType.getAnimationPath(), 6, this, 2);


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
