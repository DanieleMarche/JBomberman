package Tile;

import main.GameLevel;
import main.GameView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class MapBuilder {

    private static MapBuilder instance = null;

    public  static MapBuilder getInstance() {
        if (instance == null) {
            instance = new MapBuilder();
        }
        return instance;
    }


    public TileType[][] generateMap(GameLevel level) {
        return addDestructibleBlock(
                        addSolidBlock(
                                loadNumMap("/Maps/plain-map.txt"), level)
                , level);
    }

    private TileType[][] loadNumMap(String mapName) {
        TileType[][] mapTileNum = new TileType[GameView.maxWorldRow][GameView.maxWorldCol];
        try {
            InputStream is = getClass().getResourceAsStream(mapName);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            mapTileNum = br.lines()
                    .map(line -> line.split(" "))
                    .map(line -> Arrays.stream(line)
                            .mapToInt(Integer::parseInt)
                            .mapToObj(TileType::getTileTypeFronInt) // Converti i numeri in TileType
                            .toArray(TileType[]::new))
                    .toArray(TileType[][]::new);

            br.close();

        } catch (Exception e) {
            System.out.println("problema");
        }
        return mapTileNum;
    }

    private TileType[][] addSolidBlock(TileType[][] tileTypesMap, GameLevel gameLevel) {

        int n = gameLevel.getNumberofSolidBlock();
        int i = 0;

        while(i < n) {
            Random r = new Random();
            int row = r.nextInt(GameView.maxWorldRow);
            int col = r.nextInt(GameView.maxWorldCol);
            if(!(row == 1 && col == 2) && !(row == 1 && col == 3) && !(row == 2 && col == 2)) {
                if(tileTypesMap[row][col] == TileType.WALKABLE_BLOCK) {
                    tileTypesMap[row][col] = TileType.SOLID_BLOCK;
                    i++;
                }
            }

        }

        return tileTypesMap;
    }

    private TileType[][] addDestructibleBlock(TileType[][] tileTypesMap, GameLevel gameLevel) {

        int n = gameLevel.getNumberOfDestructibleBlocks();
        int i = 0;

        while(i < n) {
            Random r = new Random();
            int row = r.nextInt(0, GameView.maxWorldRow);
            int col = r.nextInt(0, GameView.maxWorldCol);
            if(!(row == 1 && col == 2) && !(row == 1 && col == 3) && !(row == 2 && col == 2)) {
                if(tileTypesMap[row][col] == TileType.WALKABLE_BLOCK) {
                    tileTypesMap[row][col] = TileType.DESTRUCTIBLE_BLOCK;
                    i++;
                }
            }
        }

        return tileTypesMap;
    }

}
