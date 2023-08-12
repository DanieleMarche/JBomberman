package Explosion;

import Flames.Flame;
import Flames.FlameType;
import main.GamePanel;
import tile.DestructibleBlock;
import tile.Map;
import tile.tileGerarchy.Tile;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Explosion extends Observable implements Observer {
    public static ArrayList<Explosion> explosions = new ArrayList<>();
    private final ArrayList<Flame> flames;

    public Explosion(int bombCol, int bombRow, int explosionRadius, Map map, Observer observer, Observer flameObserver) {
        addObserver(observer);

        flames = new ArrayList<>();
        flames.add(new Flame(bombCol * GamePanel.tileSize, bombRow * GamePanel.tileSize, FlameType.BOMB_FLAME, flameObserver));

        flames.addAll(createFlameList(bombRow, bombCol, -1, 0, explosionRadius, map, FlameType.VERTICAL_FLAME, flameObserver));

        flames.addAll(createFlameList(bombRow, bombCol, 1, 0, explosionRadius, map, FlameType.VERTICAL_FLAME, flameObserver));

        flames.addAll(createFlameList(bombRow, bombCol, 0, 1, explosionRadius, map, FlameType.HORIZONTHAL_FLAME, flameObserver));
        flames.addAll(createFlameList(bombRow, bombCol, 0, -1, explosionRadius, map, FlameType.HORIZONTHAL_FLAME, flameObserver));

    }

    private ArrayList<Flame> createFlameList(int row, int col, int rowIncrement, int colIncrement, int explosionRadius, Map map, FlameType flameType, Observer observer) {
        ArrayList<Flame> flameList = new ArrayList<>();
        Tile tile;

        for (int i = 1; i <= explosionRadius; i++) {

            row += rowIncrement;
            col += colIncrement;

            tile = map.getTile(row, col);


            if (!tile.doesGetFire()){
                break;
            }

            if(tile.isExplodable() && tile instanceof DestructibleBlock db) {
                db.explode();
                break;
            }

            if(i == explosionRadius) {
                switch(flameType) {
                    case VERTICAL_FLAME -> {
                        if(rowIncrement > 0) flameType = FlameType.LOWER_FLAME_TIP;
                        else flameType = FlameType.UPPER_FLAME_TIP;
                    }
                    case HORIZONTHAL_FLAME -> {
                        if(colIncrement > 0) flameType = FlameType.RIGHT_FLAME_TIP;
                        else flameType = FlameType.LEFT_FLAME_TIP;
                    }
                }
            }

            flameList.add(new Flame(col * GamePanel.tileSize, row * GamePanel.tileSize, flameType, observer));

        }

        return flameList;
    }

    public ArrayList<Flame> getExplosionFlames() {
        return flames;
    }

    public void removeExplosion(Explosion explosion) {
        explosions.remove(explosion);
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
