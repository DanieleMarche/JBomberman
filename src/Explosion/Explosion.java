package Explosion;

import Controllers.AssetManager;
import Controllers.AudioManager;
import Flames.Flame;
import Flames.FlameType;
import main.GameView;
import Tile.DestructibleBlock;
import Tile.Map;
import Tile.tileGerarchy.Tile;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Explosion extends Observable implements Observer {
    private final ArrayList<Flame> flames;

    public Explosion(int bombCol, int bombRow, int explosionRadius, Observer observer, Observer flameObserver) {
        addObserver(observer);

        Map map = AssetManager.getInstance().getMap();

        flames = new ArrayList<>();
        Flame centralFlame = new Flame(bombCol * GameView.tileSize, bombRow * GameView.tileSize, FlameType.BOMB_FLAME, flameObserver);
        AssetManager.getInstance().addFlame(centralFlame);
        flames.add(centralFlame);

        flames.addAll(createFlameList(bombRow, bombCol, -1, 0, explosionRadius, map, FlameType.VERTICAL_FLAME, flameObserver));

        flames.addAll(createFlameList(bombRow, bombCol, 1, 0, explosionRadius, map, FlameType.VERTICAL_FLAME, flameObserver));

        flames.addAll(createFlameList(bombRow, bombCol, 0, 1, explosionRadius, map, FlameType.HORIZONTHAL_FLAME, flameObserver));
        flames.addAll(createFlameList(bombRow, bombCol, 0, -1, explosionRadius, map, FlameType.HORIZONTHAL_FLAME, flameObserver));

        AudioManager audioManager = AudioManager.getInstance();
        audioManager.play("res/sounds/Super_Bomberman_Sound_Effects/bomb-explodes.wav");

    }

    private ArrayList<Flame> createFlameList(int row, int col, int rowIncrement, int colIncrement, int explosionRadius, Map map, FlameType flameType, Observer observer) {
        ArrayList<Flame> flameList = new ArrayList<>();
        Tile tile;

        for (int i = 1; i <= explosionRadius; i++) {

            row += rowIncrement;
            col += colIncrement;

            tile = map.getTile(row, col);


            if (!tile.getTileType().isGetFire()){
                break;
            }

            if(tile.getTileType().isExplodable() && tile instanceof DestructibleBlock db) {
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

            Flame f = new Flame(col * GameView.tileSize, row * GameView.tileSize, flameType, observer);
            flameList.add(f);
            AssetManager.getInstance().addFlame(f);

        }

        return flameList;
    }

    public ArrayList<Flame> getExplosionFlames() {
        return flames;
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
