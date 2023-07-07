package player;

import entityGerarchy.Direction;
import entityGerarchy.MovingEntity;
import main.GamePanel;
import java.awt.*;

public class Player extends MovingEntity {

    static Player istance = null;

    public static Player getInstance(int positionX, int positionY, int speed) {
        if(istance == null) {
            istance = new Player(positionX, positionY, speed, "bomberMan", "bomberman");
            return istance;
        }else {
            return istance;
        }
    }
    private int bombAtSameTime;
    private int explosionRadius;

    private boolean moving;
    private int pixelCounter;

    private Player(int positionX, int positionY, int speed, String directoryName, String fileNames) {
        super(positionX, positionY, speed,13, 13, 2, 32, directoryName, fileNames, 3, 1);

        bombAtSameTime = 1;
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

    public void draw(Graphics2D g2) {

        int x = worldPositionX;
        int y = worldPositionY;

        g2.drawImage(currentAnimation.getCurrentImage(), x, y, spritesWidth * GamePanel.tileScale, spritesHeight * GamePanel.tileScale, null);
    }


}