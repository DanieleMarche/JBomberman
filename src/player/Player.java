package player;

import entityGerarchy.Direction;
import entityGerarchy.MovingEntity;
import main.GamePanel;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Player extends MovingEntity {

    static Player istance = null;

    public static Player getInstance(int positionX, int positionY, int speed, GamePanel observer) {
        if(istance == null) {
            istance = new Player(positionX, positionY, speed);
            return istance;
        }else {
            return istance;
        }
    }
    private int bombAtSameTime;
    private int remainingBombsAtSameTime;
    private int explosionRadius;

    private boolean moving;
    private int pixelCounter;

    private Player(int positionX, int positionY, int speed) {
        super(positionX, positionY, speed, GamePanel.tileSize, GamePanel.tileSize, 0, 26, "res/bomberMan", 1, 10);

        bombAtSameTime = remainingBombsAtSameTime = 1;
        explosionRadius = 1;
        moving = false;
        pixelCounter = 0;

        direction = Direction.DOWN;
    }

    public void incresePixelCounter() {
        pixelCounter += speed;
    }

    public void resetPixelCounter() {
        pixelCounter = 0;
    }

    public void move() {
        moving = true;
    }

    public void stopMoving() {
        moving = false;
    }

    public int getPixelCounter() {
        return pixelCounter;
    }

    public boolean isMoving() {
        return moving;
    }

    public int getBombAtSameTime() {
        return bombAtSameTime;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }

    public void increaseBombAtSameTime() {
        bombAtSameTime++;
    }

    public void decreaseBombAtSameTime() {
        bombAtSameTime--;
    }

    public void increaseExplosionRadius() {
        explosionRadius++;
    }

    public void decreaseExplosionRadius() {
        explosionRadius--;
    }

    public int getRemainingBombsAtSameTime() {
        return remainingBombsAtSameTime;
    }

    public void increaseRemainingBombsAtSameTime() {
        remainingBombsAtSameTime ++;
    }

    public void decreaseRemainingBombsAtSameTime() {
        remainingBombsAtSameTime --;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(currentAnimation.getCurrentImage(), worldPositionX, worldPositionY, spritesWidth * GamePanel.tileScale, spritesHeight * GamePanel.tileScale, null);
    }


    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}