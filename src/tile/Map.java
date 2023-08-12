package tile;

import Animation.AnimationMessages;
import Controllers.DestructibleBlocksController;
import Controllers.MapController;
import main.GamePanel;
import tile.tileGerarchy.Tile;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static javax.imageio.ImageIO.read;

public class Map extends Observable{

    public static Map instance;

    private TileType[][] mapTileNum;

    private final Tile[][] map;
    private final DestructibleBlocksController destructibleBlocksController;

    public static Map getInstance(GamePanel gamePanel, String mapName, DestructibleBlocksController destructibleBlocksController) {
        if(instance == null) {
            instance = new Map(gamePanel, mapName, destructibleBlocksController);
        }
        return instance;
    }

    private Map(GamePanel gamePanel, String mapName, DestructibleBlocksController destructibleBlocksController) {
        addObserver(new MapController(gamePanel, this));
        this.destructibleBlocksController = destructibleBlocksController;

        loadNumMap(mapName);

        map = new Tile[GamePanel.maxWorldRow][GamePanel.maxWorldCol];

        loadMap();
        //printMap();
    }

    public void printMap() {
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

    public TileType getMapTileNum(int y, int x) {
        return mapTileNum[y][x];
    }

    private void loadNumMap(String mapName) {
        try {
            InputStream is = getClass().getResourceAsStream(mapName);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            mapTileNum = br.lines()
                    .map(line -> line.split(" "))
                    .map(line -> Arrays.stream(line)
                            .mapToInt(Integer::parseInt)
                            .mapToObj(this::getTileTypeFromNumber) // Converti i numeri in TileType
                            .toArray(TileType[]::new))
                    .toArray(TileType[][]::new);

            br.close();

        } catch (Exception e) {
            System.out.println("problema");
        }
    }

    private TileType getTileTypeFromNumber(int number) {
        return switch (number) {
            case 0 -> TileType.WALKABLE_BLOCK;
            case 1 -> TileType.LIMIT_BLOCK;
            case 2 -> TileType.DESTRUCTIBLE_BLOCK;
            case 3 -> TileType.SOLID_BLOCK;
            default -> throw new IllegalArgumentException("Numero sconosciuto: " + number);
        };
    }

    private void loadMap() {

        for(int row = 0; row< mapTileNum.length; row++) {
            for(int col = 0; col< mapTileNum[0].length; col++) {

                switch (mapTileNum[row][col]) {
                    case WALKABLE_BLOCK -> {
                        map[row][col] = getWalkableBlock(row, col);
                    }
                    case LIMIT_BLOCK -> map[row][col] = new LimitBlock(row, col);

                    case DESTRUCTIBLE_BLOCK -> map[row][col] = getDestructibleBlock(row, col);

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
                if(!checkTile(row, col, tileType)) map[row][col] = getWalkableBlock(row, col);
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
                if(!checkTile(row, col, tileType)) map[row][col] = getDestructibleBlock(row, col);
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


    public void setTile(int y, int x, TileType tileType) {
        mapTileNum[y][x] = tileType;
        setChanged();
        notifyObservers();
    }


    /**
     * This function looks at the map matrix to decide what WalkableBlock to instantiate.
     * @param row The row of the tile to replace.
     * @param col The row of the tile to replace.
     * @return The new WalkableBlock Tile.
     */
    private WalkableBlock getWalkableBlock(int row, int col) {
        WalkableBlock walkableBlock;
        if(map[row - 1] [col] instanceof DestructibleBlock ) walkableBlock = new WalkableBlock(row, col, WalkableBlock.WalkableBlockType.UNDER_DESTRUCTIBLE_BLOCK);
        else if(getMapTileNum(row - 1, col) == TileType.LIMIT_BLOCK || getMapTileNum(row - 1, col) == TileType.SOLID_BLOCK) walkableBlock = new WalkableBlock(row, col, WalkableBlock.WalkableBlockType.UNDER_LIMIT_OR_SOLID_BLOCK);
        else walkableBlock = new WalkableBlock(row, col, WalkableBlock.WalkableBlockType.UNDER_WALKABLE_BLOCK);
        return walkableBlock;
    }

    private DestructibleBlock getDestructibleBlock(int row, int col) {
        DestructibleBlock destructibleBlock;
        if(getMapTileNum(row - 1, col) == TileType.SOLID_BLOCK || getMapTileNum(row - 1, col) == TileType.LIMIT_BLOCK) destructibleBlock = new DestructibleBlock(row, col, true, destructibleBlocksController );
        else destructibleBlock = new DestructibleBlock(row, col, false, destructibleBlocksController);
        return destructibleBlock;
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


