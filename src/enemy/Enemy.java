package enemy;

import entityGerarchy.MovingEntity;

import java.awt.*;

public class Enemy extends MovingEntity {
    private int harm;
    public Enemy(int worldPositionX, int worldPositionY, int speed, int width, int height, int solidAreaDefaultX, int solidAreaDefaultY, String directoryName, String filesName, int sriteNum) {
        super(worldPositionX, worldPositionY, speed, width, height, solidAreaDefaultX, solidAreaDefaultY, directoryName, filesName, sriteNum, 0);
    }

    @Override
    public void draw(Graphics2D g2) {

    }

}
