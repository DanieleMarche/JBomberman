package Explosion;

import tile.DestructibleBlock;
import tile.TileManager;
import player.Player;
import Animation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Explosion extends Observable {

    private static Animation destructibleBlockExplosion;
    private final ArrayList<Flame>[] flameRadius;
    private static final int explosionTime = 120;
    private int secondsCount;

    public Explosion(int worldX, int worldY, int explosionRadius, TileManager tileManager, Observer gamePanel) {
        addObserver(gamePanel);
        int bombCol = (worldX + 24)/ 48;
        int bombRow = (worldY / 48);

        ArrayList<Flame> flameRadiusUp = createFlameList(bombRow, bombCol, 1, 0, explosionRadius, tileManager,  gamePanel);
        ArrayList<Flame> flameRadiusDown = createFlameList(bombRow, bombCol, -1, 0, explosionRadius, tileManager,  gamePanel);
        ArrayList<Flame> flameRadiusRight = createFlameList(bombRow, bombCol, 0, 1, explosionRadius, tileManager,  gamePanel);
        ArrayList<Flame> flameRadiusLeft = createFlameList(bombRow, bombCol, 0, -1, explosionRadius, tileManager,  gamePanel);


        flameRadius = new ArrayList[] {flameRadiusUp, flameRadiusDown, flameRadiusLeft, flameRadiusRight};

        secondsCount = 0;
    }
    private ArrayList<Flame> createFlameList(int row, int col, int rowIncrement, int colIncrement, int explosionRadius, TileManager tileManager, Observer gamePanel) {
        ArrayList<Flame> flameList = new ArrayList<>();
        int tile;


        for (int i = 1; i <= explosionRadius; i++) {
            row += rowIncrement;
            col += colIncrement;
            tile = tileManager.getMapTileNum(row, col);
            if (!tileManager.getTile(tile).doesGetOnFire()){
                break;
            }
            flameList.add(new Flame(col * 48, row * 48, gamePanel));
            if(tileManager.getTile(tile).isDestructible()) {
                DestructibleBlock db = DestructibleBlock.getIstance(col, row, 0);
                db.explode();
                break;
            }

        }


        return flameList;
    }

    public ArrayList<Flame>[] getFlameRadius() {
        return flameRadius;
    }

    public boolean update() {
        secondsCount++;
        if(secondsCount % 7 == 0) {
            for(ArrayList<Flame> flames: flameRadius) {
                flames.forEach(Flame::updateAnimation);
            }
        }
        return secondsCount == explosionTime;
    }
    public void removeDestructibleBlocks(TileManager tileManager) {


        for(ArrayList<Flame> flames: flameRadius) {
            flames.stream()
                    .filter(flame ->
                            tileManager.getTile(
                                    tileManager.getMapTileNum(
                                            flame.getWorldPositionY()/64, flame.getWorldPositionX()/64))
                                    .isDestructible())
                    .forEach(flame -> tileManager.setTile(flame.getWorldPositionY()/64, flame.getWorldPositionX()/64, 0));
        }


        setChanged();
        notifyObservers();
    }

    public void draw(Graphics2D g2, Player player, TileManager tileManager) {
        for(ArrayList<Flame> flames: flameRadius) {
            flames.forEach(f -> f.draw(g2, player));
        }
    }
}
