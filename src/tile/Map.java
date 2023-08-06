package tile;

import main.GamePanel;
import tile.tileGerarchy.Tile;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static javax.imageio.ImageIO.read;

public class Map extends Observable implements Observer{

    public static Map instance;

    private int[][] mapTileNum;

    private Tile[][] map;

    public static Map getInstance(GamePanel gamePanel, String mapName) {
        if(instance == null) {
            instance = new Map(gamePanel, mapName);
        }
        return instance;
    }

    private Map(GamePanel gamePanel, String mapName) {
        addObserver(gamePanel);

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

    public int getMapTileNum(int y, int x) {
        return mapTileNum[y][x];
    }

    private void loadNumMap(String mapName) {
        try{
            InputStream is = getClass().getResourceAsStream(mapName);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            mapTileNum = br.lines()
                    .map(line -> line.split(" "))
                    .map(line -> Arrays.stream(line)
                            .mapToInt(Integer::parseInt)
                            .toArray())
                    .toArray(int[][]::new);

            br.close();

        }catch(Exception e) {
            System.out.println("problema");

        }
    }

    private void loadMap() {

        for(int row = 0; row< mapTileNum.length; row++) {
            for(int col = 0; col< mapTileNum[0].length; col++) {

                int tileNum = mapTileNum[row][col];

                switch (tileNum) {
                    case 0 -> {
                        if(map[row - 1] [col] instanceof DestructibleBlock ) map[row][col] = new WalkableBlock(row, col, false);
                        else if(getMapTileNum(row - 1, col) == 1) map[row][col] = new WalkableBlock(row, col, true);
                        else map[row][col] = new WalkableBlock(row, col);
                    }
                    case 1 -> map[row][col] = new LimitBlock(row, col);

                    case 2 -> {
                        if(getMapTileNum(row - 1, col) == 3 || getMapTileNum(row - 1, col) == 1) map[row][col] = new DestructibleBlock(row, col, true);
                        else map[row][col] = new DestructibleBlock(row, col, false);
                    }

                    case 3 -> map[row][col] = new SolidBlock(row, col);
                }
            }
        }

    }

    public void setTile(int y, int x, int numTile) {
        mapTileNum[y][x] = numTile;
        setChanged();
        notifyObservers();
    }

    public void draw(Graphics2D g2) {
        Arrays.stream(map).toList().stream()
                .forEach(tiles -> Arrays.stream(tiles)
                                .forEach(tile -> tile.draw(g2)));
    }

    public Tile getTile(int worldRow, int worldCol) {
        return map[worldRow][worldCol];
    };

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }
}


