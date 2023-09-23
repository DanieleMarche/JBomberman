package Tile;

import Controllers.MapController;
import Tile.tileGerarchy.TileType;
import EntityModelGerarchy.Entity;
import main.GameLevel;
import main.GameView;
import Tile.tileGerarchy.Tile;

import java.util.*;

import static javax.imageio.ImageIO.read;


/**
 * The `Map` class represents the game map, which contains various types of tiles, including walkable blocks, limit blocks,
 * destructible blocks, and solid blocks. It is responsible for loading the map, managing tiles, and providing access to
 * map-related data.
 *
 * @Author Daniele Marchetilli
 */
public class Map extends Observable{

    /**
     * The directory on the resource folder where the plain map file is stored.
     */
    private static final String plainMapPath = "res/Maps/plain-map.txt";

    /**
     * The actual map based on a matrix of tiles.
     */
    private final Tile[][] map;

    public Map(GameLevel gameLevel) {
        addObserver(MapController.getInstance());

        map = new Tile[GameView.maxWorldRow][GameView.maxWorldCol];
        loadMap(gameLevel);
    }

    public Tile[][] getMap() {
        return map;
    }

    /**
     * This function returns the type of the tile present of that position on the map.
     * @param y the row of the tile.
     * @param x the column of the tile.
     * @return The type of the tile on that cohordinates.
     */
    public TileType getMapTileType(int y, int x) {
        return map[y][x].getTileType();
    }

    /**
     * This function loads the matrix map taking a gamelevel as a parameter and using the map builder class
     * to generate randomly the pieces with a free position.
     */
    private void loadMap(GameLevel gameLevel) {

        TileType[][] mapTileNum = MapBuilder.getInstance().generateMap(gameLevel);

        for(int row = 0; row< mapTileNum.length; row++) {
            for(int col = 0; col< mapTileNum[0].length; col++) {

                switch (mapTileNum[row][col]) {
                    case WALKABLE_BLOCK -> {
                        map[row][col] = WalkableBlock.getWalkableBlock(this, row, col);
                    }
                    case LIMIT_BLOCK -> map[row][col] = new LimitBlock(row, col);

                    case DESTRUCTIBLE_BLOCK -> {
                        map[row][col] = DestructibleBlock.getDestructibleBlock(this, row, col);


                    }

                    case SOLID_BLOCK -> map[row][col] = new SolidBlock(row, col);
                }

                setChanged();
                notifyObservers(map[row][col]);
            }
        }

    }

    /**
     * This function takes as parameters a position inside the map matrix and a TileType, then it checks if the tile on
     * that position is not the same type as the one passed as parameter, and if so, it assigns a new tile of the
     * specified type in that position.
     * @param row The row of the tile to replace.
     * @param col The column of the tile to replace.
     * @param tileType The type the new tile will be.
     */
    public void replaceTile(int row, int col, TileType tileType) {
        switch (tileType) {
            case WALKABLE_BLOCK -> {
                if(!checkTile(row, col, tileType)) map[row][col] = WalkableBlock.getWalkableBlock(this, row, col);
                if(map[row + 1][col].getTileType() == TileType.WALKABLE_BLOCK) {

                    WalkableBlock.reassignType(this, (WalkableBlock) map[row + 1][col]);
                    setChanged();
                    notifyObservers(map[row + 1][col]);

                }
            }
            case LIMIT_BLOCK -> {
                if(!checkTile(row, col, tileType)) map[row][col] = new LimitBlock(row, col);
            }
            case DESTRUCTIBLE_BLOCK -> {
                if(!checkTile(row, col, tileType)) map[row][col] = DestructibleBlock.getDestructibleBlock(this, row, col);
                if(map[row + 1][col].getTileType() == TileType.WALKABLE_BLOCK) {
                    WalkableBlock.reassignType(this, (WalkableBlock) map[row + 1][col]);
                    setChanged();
                    notifyObservers(map[row + 1][col]);
                }

            }

        }
        setChanged();
        notifyObservers(map[row][col]);
    }

    public boolean checkTile(int row, int col, TileType tileType) {
        return map[row][col].getTileType() == tileType;
    }


    /**
     * This function get as a parameter an entity and return the tiles surrounding it.
     * @param entity
     * @return The tiles surrounding the entity in the order of up, down, left and right.
     */
    public Tile[] getSourroundings(Entity entity) {

        int entityRow = entity.getRow();
        int entityCol = entity.getCol();

        return new Tile[] {
                map[entityRow - 1][entityCol],
                map[entityRow + 1][entityCol],
                map[entityRow][entityCol - 1],
                map[entityRow][ entityCol + 1]};

    }

    public Tile getTile(int worldRow, int worldCol) {
        return map[worldRow][worldCol];
    }

}


