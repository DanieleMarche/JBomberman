package Tile;

import Controllers.AssetManager;
import Controllers.MapController;
import entityGerarchy.Entity;
import main.GameLevel;
import main.GameModel;
import main.GameView;
import Tile.tileGerarchy.Tile;

import java.awt.*;
import java.util.*;

import static javax.imageio.ImageIO.read;

public class Map extends Observable{

    private static String plainMapPath = "res/Maps/plain-map.txt";

    private final Tile[][] map;

    public Map(GameLevel gameLevel) {
        addObserver(new MapController());

        map = new Tile[GameView.maxWorldRow][GameView.maxWorldCol];
        loadMap(gameLevel);
    }

    private void printMap() {
        for (Tile[] tiles : map) {
            for (int x = 0; x < map[0].length; x++) {
                if(tiles[x] instanceof LimitBlock) {
                    if(((LimitBlock) tiles[x]).getImage() != null) {
                        System.out.print("img" + " ");
                    }
                }

            }
            System.out.println();
        }
    }

    public Tile[][] getMap() {
        return map;
    }

    public TileType getMapTileType(int y, int x) {
        return map[y][x].getTileType();
    }

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


    public void draw(Graphics2D g2) {
        Arrays.stream(map).toList()
                .forEach(tiles -> Arrays.stream(tiles)
                                .forEach(tile -> tile.draw(g2)));
    }

    public Tile getTile(int worldRow, int worldCol) {
        return map[worldRow][worldCol];
    }

}


