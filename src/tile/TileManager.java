package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static javax.imageio.ImageIO.read;

public class TileManager extends Observable implements Observer{

    public static TileManager instance;

    private final ArrayList<DestructibleBlock> destructibleBlocks = DestructibleBlock.destructibleBlocks;

    private final LimitBlock limitBlock = LimitBlock.getIstance();

    private final WalkableBlock walkableBlock = WalkableBlock.getIstance();

    private final NotAnimatedSingleSpriteTile solidBlock = SolidBlock.getIstance();

    private int[][] mapTileNum;

    public static TileManager getInstance(GamePanel gamePanel, String mapName) {
        if(instance == null) {
            instance = new TileManager(gamePanel, mapName);
        }
        return instance;
    }

    private TileManager (GamePanel gamePanel, String mapName) {
        addObserver(gamePanel);

        addTiles();

        loadMap(mapName);
        //printMap();
    }

    public void printMap() {
        for (int[] ints : mapTileNum) {
            for (int x = 0; x < mapTileNum[0].length; x++) {
                System.out.print(ints[x] + " ");

            }
            System.out.println();
        }
    }

    public int getMapTileNum(int y, int x) {
        return mapTileNum[y][x];
    }

    public void addTiles() {


        /**
        try {

        } catch (IOException e) {
            e.printStackTrace();
        }
         **/
    }



    public void loadMap(String mapName) {
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

    public void setTile(int y, int x, int numTile) {
        mapTileNum[y][x] = numTile;
        setChanged();
        notifyObservers();
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while (worldCol < GamePanel.maxWorldCol && worldRow < GamePanel.maxWorldRow) {

            int tileNum = mapTileNum[worldRow][worldCol];

            BufferedImage image = null;

            switch (tileNum) {
                case 0 -> {
                    if(getMapTileNum(worldRow - 1, worldCol) == 1 || getMapTileNum(worldRow - 1, worldCol) == 3) {image = walkableBlock.getImage(1);}
                    else if (getMapTileNum(worldRow - 1, worldCol) == 2) {image = walkableBlock.getImage(2);}
                    else {image = walkableBlock.getImage(0);}
                }
                case 1 -> {
                    if (worldRow == 0) {
                        switch (worldCol) {
                            case 0 -> image = limitBlock.getImage(0);
                            case 1 -> image = limitBlock.getImage(1);
                            case 14 -> image = limitBlock.getImage(3);
                            case 13 -> image = limitBlock.getImage(4);
                            default -> image = limitBlock.getImage(2);
                        }
                        break;
                    }
                    if (worldRow == 12) {
                        switch (worldCol) {
                            case 0 -> image = limitBlock.getImage(9);
                            case 1 -> image = limitBlock.getImage(10);
                            case 14 -> image = limitBlock.getImage(12);
                            case 13 -> image = limitBlock.getImage(13);
                            default -> image = limitBlock.getImage(11);
                        }
                        break;
                    }
                    switch (worldCol) {
                        case 0 -> image = limitBlock.getImage(6);
                        case 1 -> image = limitBlock.getImage(5);
                        case 14 -> image = limitBlock.getImage(8);
                        case 13 -> image = limitBlock.getImage(7);
                    }
                }
                case 2 -> {
                    if(getMapTileNum(worldRow - 1, worldCol) == 1 || getMapTileNum(worldRow - 1, worldCol) == 3) image = DestructibleBlock.getIstance(worldCol, worldRow, 1).getSprite();
                    else image = DestructibleBlock.getIstance(worldCol, worldRow, 0).getSprite();
                }
                case 3 -> image = solidBlock.getImage();
            }

            g2.drawImage(image, worldCol * GamePanel.tileSize, worldRow * GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize, null);



            worldCol++;

            if (worldCol == GamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    public Tile getTile(int mapTileNum) {
        switch(mapTileNum) {
            case 1 -> {
                return limitBlock;
            }
            case 0 -> {
                return walkableBlock;
            }
            case 2 -> {
                return destructibleBlocks.get(0);
            }
            case 3 -> {
                return solidBlock;
            }
        }
        return null;
    };

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }
}


