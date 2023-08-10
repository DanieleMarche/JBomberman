package Controllers;

import main.GamePanel;
import tile.Map;
import tile.tileGerarchy.Tile;

import java.util.Observable;
import java.util.Observer;

public class MapController implements Observer {
    private GamePanel gamePanel;
    private final Map map;


    public MapController(GamePanel gamePanel, Map map) {
        this.gamePanel = gamePanel;
        this.map = map;
    }

    @Override
    public void update(Observable o, Object arg) {
        Tile tile = (Tile) arg;
        gamePanel.repaint(tile.getPositionXOnScreen(), tile.getPositionYOnScreen(), GamePanel.tileSize, GamePanel.tileSize);
    }
}
