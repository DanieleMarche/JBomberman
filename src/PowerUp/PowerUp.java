package PowerUp;

import entityGerarchy.NotMovingEntity;
import player.Player;
import main.GamePanel;

import java.awt.*;

public class PowerUp extends NotMovingEntity {
    protected PowerUpType type;

    public PowerUp(String imagePath, int worldPositionX, int worldPositionY, PowerUpType type) {
        super(worldPositionX + 16, worldPositionY + 16, 32, 32, 0,0, imagePath);
        this.type = type;
    }

    public PowerUpType getType() {
        return type;
    }

    public void draw(Graphics2D g2, Player player) {
        int screenX = worldPositionX;
        int screenY = worldPositionY;


        g2.drawImage(image, screenX, screenY, GamePanel.tileSize /2, GamePanel.tileSize /2, null);

    }


}
