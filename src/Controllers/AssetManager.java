package Controllers;

import Bomb.Bomb;
import Bomberman.Player;
import Explosion.Explosion;
import Flames.Flame;
import Portal.Portal;
import PowerUp.*;
import TopBar.TopBar;
import User.UserModel;
import enemy.Denkyus;
import enemy.Enemy;
import enemy.Puropen;
import main.GameLevel;
import main.GameView;
import Tile.DestructibleBlock;
import Tile.Map;
import Tile.TileType;

import java.util.ArrayList;
import java.util.Random;

public class AssetManager {

    private static AssetManager instance = null;
    private final GameLevel gameLevel;

    private Player player;

    private final ArrayList<Flame> flames;

    private final ArrayList<Explosion> explosions;

    private ArrayList<Enemy> enemies;

    private ArrayList<PowerUp> powerUps;

    private Portal portal;

    private Map map;

    private ArrayList<DestructibleBlock> destructibleBlocks;

    private final ArrayList<Bomb> bombs;

    public static AssetManager getInstance() {
        if(instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    private AssetManager() {
        gameLevel = UserModel.getInstance().getLivelloRaggiunto();
        flames = new ArrayList<>();
        explosions = new ArrayList<>();
        bombs = new ArrayList<>();
        destructibleBlocks = new ArrayList<>();
        enemies = new ArrayList<>();
        powerUps = new ArrayList<>();
    }

    public void setMap(Map map) {
        this.map = map;
        powerUps.addAll(setPowerUps());
        portal = addPortal(map);
        PortalController.getInstance().setEntity(portal);
        //enemies.addAll(addEnemies(map));
    }

    public static void removeInstance() {
        Portal.removeInstance();
        Player.removeInstance();
        TopBar.removeInstance();
        instance = null;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public GameLevel getGameLevel() {
        return gameLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Flame> getFlames() {
        return flames;
    }

    public ArrayList<Explosion> getExplosions() {
        return explosions;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public Portal getPortal() {
        return portal;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<DestructibleBlock> getDestructibleBlocks() {
        return destructibleBlocks;
    }

    private ArrayList<PowerUp> setPowerUps() {

        ArrayList<PowerUp> res = new ArrayList<>();

        res.addAll(setPowerUpsByTypes(gameLevel.getNumberOfBombPowerUps(), PowerUpType.MORE_BOMB_AT_SAME_TIME));
        res.addAll(setPowerUpsByTypes(gameLevel.getNumberOfFlamePowerUps(), PowerUpType.MORE_FLAME));
        res.addAll(setPowerUpsByTypes(gameLevel.getNumberOfSpeedPowerUps(), PowerUpType.MORE_SPEED));

        return res;
    }

    private ArrayList<PowerUp> setPowerUpsByTypes(int n, PowerUpType type) {
        ArrayList<PowerUp> res = new ArrayList<>();

        Random random = new Random();

        int i = 0;
        while (i != n) {
            int randomIndex = random.nextInt(destructibleBlocks.size());
            DestructibleBlock selectedBlock = destructibleBlocks.get(randomIndex);

            if (selectedBlock != null) {
                if(selectedBlock.getPowerUp() != null) continue;
                PowerUp selectedPowerUp = new PowerUp(selectedBlock.getPositionXOnScreen(), selectedBlock.getPositionYOnScreen() - GameView.topBarHeight, type);
                selectedBlock.addPowerUp(selectedPowerUp);
                res.add(selectedPowerUp);
                i++;
            }
        }
        return res;
    }

    private Portal addPortal(Map map) {

        int n = 1;
        int i = 0;

        while(i < n) {
            Random r = new Random();
            int row = r.nextInt(GameView.maxWorldRow);
            int col = r.nextInt(GameView.maxWorldCol);

            if(map.getMapTileType(row, col) == TileType.WALKABLE_BLOCK) {
                Portal.getInstance(row, col).setVisibility(true);
                i++;
            }

            else if (map.getMapTileType(row, col) == TileType.DESTRUCTIBLE_BLOCK ) {
                DestructibleBlock db = (DestructibleBlock) map.getTile(row, col);
                if(!db.hasPoweUp()) db.addPortal(Portal.getInstance(row, col));
                i++;
            }
        }
        return Portal.getInstance();
    }

    private ArrayList<Enemy> addEnemies(Map map) {

        ArrayList<Enemy> res = new ArrayList<>();
        int numberOfPuropens = gameLevel.getNumberOfPuropens();
        int numberOfDenkyuses = gameLevel.getNumberOfDenkyuses();

        while(numberOfPuropens > 0) {
            Random r = new Random();
            int row = r.nextInt(1, GameView.maxWorldRow - 2);
            int col = r.nextInt(2, GameView.maxWorldCol - 3);

            if(!(row == 1 && col == 2) && !(row == 1 && col == 3) && !(row == 2 && col == 2) && map.getMapTileType(row, col) == TileType.WALKABLE_BLOCK) {
                if(!Enemy.checkEnemyPresence(row, col, res))  res.add(new Puropen(row, col));
                numberOfPuropens--;
            }

        }

        while(numberOfDenkyuses > 0) {
            Random r = new Random();
            int row = r.nextInt(1, GameView.maxWorldRow - 2);
            int col = r.nextInt(2, GameView.maxWorldCol - 3);

            if(!(row == 1 && col == 2) && !(row == 1 && col == 3) && !(row == 2 && col == 2) && map.getMapTileType(row, col) == TileType.WALKABLE_BLOCK) {
                if(!Enemy.checkEnemyPresence(row, col, res)) res.add(new Denkyus(row, col));
                numberOfDenkyuses--;
            }

        }

        return res;

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addBomb(Bomb b) {
        bombs.add(b);
    }

    public  void addExplosion(Explosion e) {
        explosions.add(e);
    }

    public void addFlame(Flame f) {
        flames.add(f);
    }

    public void addDestructibleBlock(DestructibleBlock db) {
        destructibleBlocks.add(db);
    }
}
