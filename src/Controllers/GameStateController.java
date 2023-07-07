package Controllers;

import main.GamePanel;

public class GameStateController {
    private final KeyHandler keyH;

    private final GamePanel gamePanel;

    public GameStateController(KeyHandler keyH, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.keyH = keyH;
    }

    public void checkChangeState() {
        if(keyH.stopPLayingAsked()) {
            gamePanel.setPauseState();
        }else{
            gamePanel.setPlayState();
        }
    }
}
