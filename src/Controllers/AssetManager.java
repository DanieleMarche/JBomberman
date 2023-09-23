package Controllers;

import Animation.Drawable;
import Controllers.ControllersGerarchy.BombController;
import Bomb.BombModel;
import Bomberman.PlayerModel;
import Explosion.Explosion;
import Flames.Flame;
import Portal.PortalController;
import Portal.PortalModel;
import PowerUp.*;
import Tile.tileGerarchy.AnimatedTile;
import TopBar.*;
import User.UserModel;
import enemy.Denkyus;
import enemy.EnemyModel;
import enemy.Puropen;
import EntityModelGerarchy.MovingEntity;
import EntityModelGerarchy.NotMovingAnimatedEntity;
import main.GameLevel;
import main.GameView;
import Tile.DestructibleBlock;
import Tile.Map;
import Tile.tileGerarchy.TileType;

import java.util.ArrayList;
import java.util.Random;

/**
 * The AssetManager class is responsible for managing assets such as the player, enemies,
 * power-ups, bombs, flames, and more. It acts as a central repository for these game objects and ensures
 * they are properly synchronized and updated throughout the game.
 *
 * @Author Daniele Marchetilli
 *
 */
public class AssetManager {

    private static AssetManager instance = null;
    private final GameLevel gameLevel;

    private ArrayList<Drawable> drawables;

    private PlayerModel playerModel;

    private TopBar topBar;
    private final ArrayList<Flame> flames;

    private final ArrayList<Explosion> explosions;

    private ArrayList<EnemyModel> enemies;

    private ArrayList<PowerUpModel> powerUpModels;

    private PortalModel portalModel;

    private Map map;

    private ArrayList<DestructibleBlock> destructibleBlocks;

    private final LevelTimer levelTimer;

    private final ArrayList<BombModel> bombModels;

    public static AssetManager getInstance() {
        if(instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    private AssetManager() {
        drawables = new ArrayList<>();
        this.levelTimer = LevelTimer.getInstance();
        this.topBar = new TopBar();
        TopBarController.getInstance().setTopBar(topBar);
        gameLevel = UserModel.getInstance().getLivelloRaggiunto();

        flames = new ArrayList<>();
        FlameController.getInstance().setEntities(flames);

        explosions = new ArrayList<>();
        ExplosionController.getInstance().setExplosions(explosions);

        bombModels = new ArrayList<>();
        BombController.getInstance().setEntities(bombModels);

        destructibleBlocks = new ArrayList<>();
        DestructibleBlocksController.getInstance().setTiles(destructibleBlocks);

        enemies = new ArrayList<>();
        EnemyController.getInstance().setEntities(enemies);

        powerUpModels = new ArrayList<>();
        PowerUpsController.getInstance().setEntities(powerUpModels);
    }

    public LevelTimer getLevelTimer() {
        return levelTimer;
    }

    public TopBar getTopBar() {
        return topBar;
    }


    /**
     * This function takes a map as input and add it to its map property, then it generates the powerUps of the game,
     * the portal and the enemies.
     * @param map
     */
    public void setMap(Map map) {
        this.map = map;
        powerUpModels.addAll(setPowerUps());
        portalModel = addPortal(map);
        PortalController.getInstance().setEntity(portalModel);
        enemies.addAll(addEnemies(map));
    }

    /**
     * This function removes the instance of the AssetManager removing also the instances of the Player class and the
     * Portal class.
     */
    public static void removeInstance() {
        PortalModel.removeInstance();
        PlayerModel.removeInstance();
        instance = null;
    }

    public ArrayList<BombModel> getBombs() {
        return bombModels;
    }

    public GameLevel getGameLevel() {
        return gameLevel;
    }

    public PlayerModel getPlayer() {
        return playerModel;
    }

    public ArrayList<Flame> getFlames() {
        return flames;
    }

    public ArrayList<Explosion> getExplosions() {
        return explosions;
    }

    public ArrayList<EnemyModel> getEnemies() {
        return enemies;
    }

    public ArrayList<PowerUpModel> getPowerUps() {
        return powerUpModels;
    }

    public PortalModel getPortal() {
        return portalModel;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<DestructibleBlock> getDestructibleBlocks() {
        return destructibleBlocks;
    }

    /**
     * This function randomly generates the powerUp position of this lebvel.
     * @return the Arraylist with the generated PowerUps
     */
    private ArrayList<PowerUpModel> setPowerUps() {

        ArrayList<PowerUpModel> res = new ArrayList<>();

        res.addAll(setPowerUpsByTypes(gameLevel.getNumberOfBombPowerUps(), PowerUpType.MORE_BOMB_AT_SAME_TIME));
        res.addAll(setPowerUpsByTypes(gameLevel.getNumberOfFlamePowerUps(), PowerUpType.MORE_FLAME));
        res.addAll(setPowerUpsByTypes(gameLevel.getNumberOfSpeedPowerUps(), PowerUpType.MORE_SPEED));

        return res;
    }

    /**
     * This function ta
     * @param n number of PowerUps to generate
     * @param type the type the powerups will have
     * @return an Arraylist containing the generated power ups.
     */
    private ArrayList<PowerUpModel> setPowerUpsByTypes(int n, PowerUpType type) {
        ArrayList<PowerUpModel> res = new ArrayList<>();

        Random random = new Random();

        int i = 0;
        while (i != n) {
            int randomIndex = random.nextInt(destructibleBlocks.size());
            DestructibleBlock selectedBlock = destructibleBlocks.get(randomIndex);

            if (selectedBlock != null) {
                if(selectedBlock.getPowerUp().isPresent()) continue;
                PowerUpModel selectedPowerUpModel = new PowerUpModel(selectedBlock.getWorldPositionX(), selectedBlock.getWorldPositionY() - GameView.topBarHeight, type);
                selectedBlock.addPowerUp(selectedPowerUpModel);
                res.add(selectedPowerUpModel);
                i++;
            }
        }
        return res;
    }

    /**
     * This function generate the portal position for the map.
     * @param map the map of the level.
     * @return the generated portal.
     */
    private PortalModel addPortal(Map map) {

        int n = 1;
        int i = 0;

        while(i < n) {
            Random r = new Random();
            int row = r.nextInt(GameView.maxWorldRow);
            int col = r.nextInt(GameView.maxWorldCol);

            if(map.getMapTileType(row, col) == TileType.WALKABLE_BLOCK) {
                PortalModel.getInstance(row, col).setVisiblility(true);
                i++;
            }

            else if (map.getMapTileType(row, col) == TileType.DESTRUCTIBLE_BLOCK ) {
                DestructibleBlock db = (DestructibleBlock) map.getTile(row, col);
                if(db.getPowerUp().isEmpty()) db.addPortal(PortalModel.getInstance(row, col));
                i++;
            }
        }
        return PortalModel.getInstance();
    }

    /**
     * This function randomly finds the position of the level enemies.
     * @param map the level map.
     * @return an arraylist containing the generated enemies.
     */
    private ArrayList<EnemyModel> addEnemies(Map map) {

        ArrayList<EnemyModel> res = new ArrayList<>();
        int numberOfPuropens = gameLevel.getNumberOfPuropens();
        int numberOfDenkyuses = gameLevel.getNumberOfDenkyuses();

        //generates the puropens
        while(numberOfPuropens > 0) {
            Random r = new Random();
            int row = r.nextInt(1, GameView.maxWorldRow - 2);
            int col = r.nextInt(2, GameView.maxWorldCol - 3);

            if(!(row == 1 && col == 2) && !(row == 1 && col == 3) && !(row == 2 && col == 2) && map.getMapTileType(row, col) == TileType.WALKABLE_BLOCK) {
                if(!EnemyModel.checkEnemyPresence(row, col, res))  res.add(new Puropen(row, col));
                numberOfPuropens--;
            }

        }

        //generates the denkyus
        while(numberOfDenkyuses > 0) {
            Random r = new Random();
            int row = r.nextInt(1, GameView.maxWorldRow - 2);
            int col = r.nextInt(2, GameView.maxWorldCol - 3);

            if(!(row == 1 && col == 2) && !(row == 1 && col == 3) && !(row == 2 && col == 2) && map.getMapTileType(row, col) == TileType.WALKABLE_BLOCK) {
                if(!EnemyModel.checkEnemyPresence(row, col, res)) res.add(new Denkyus(row, col));
                numberOfDenkyuses--;
            }

        }

        return res;

    }

    public void setPlayer(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public void addBomb(BombModel b) {
        bombModels.add(b);
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

    public void addDrawable(Drawable d) {
        drawables.add(d);
    }

    public void removeDrawable(Drawable d) {
        drawables.remove(d);
    }

    public void pause() {
        levelTimer.pause();
        bombModels.forEach(NotMovingAnimatedEntity::pause);
        flames.forEach(NotMovingAnimatedEntity::pause);
        destructibleBlocks.forEach(AnimatedTile::pause);
        enemies.forEach(MovingEntity::pause);
        playerModel.pause();

    }

    public void resume() {
        if(levelTimer.isPaused()) levelTimer.resume();
        bombModels.forEach(NotMovingAnimatedEntity::resume);
        flames.forEach(NotMovingAnimatedEntity::resume);
        destructibleBlocks.forEach(AnimatedTile::resume);
        enemies.forEach(MovingEntity::resume);
        playerModel.resume();
    }
}
