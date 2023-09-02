package main;

import java.util.Arrays;

public enum GameLevel {

    LEVEL_1(1, 3, 30, 2, 0, 1, 1, 0),
    LEVEL_2(2, 5, 35, 1, 2, 0, 0 ,1);

    private final int levelCode;
    private final int numberofSolidBlock;
    private final int numberOfDestructibleBlocks;

    private final int numberOfPuropens;

    private final int numberOfDenkyuses;

    private final int numberOfBombPowerUps;

    private final int numberOfFlamePowerUps;

    private final int numberOfSpeedPowerUps;

    GameLevel(int levelCode, int numberofSolidBlock, int numberOfDestructibleBlocks, int numberOfPuropens, int numberOfDenkyuses, int numberOfBombPowerUps, int numberOfFlamePowerUps, int numberOfSpeedPowerUps) {
        this.levelCode = levelCode;
        this.numberofSolidBlock = numberofSolidBlock;
        this.numberOfDestructibleBlocks = numberOfDestructibleBlocks;
        this.numberOfPuropens = numberOfPuropens;
        this.numberOfDenkyuses = numberOfDenkyuses;
        this.numberOfBombPowerUps = numberOfBombPowerUps;
        this.numberOfFlamePowerUps = numberOfFlamePowerUps;
        this.numberOfSpeedPowerUps = numberOfSpeedPowerUps;
    }

    public int getNumberOfBombPowerUps() {
        return numberOfBombPowerUps;
    }

    public int getNumberOfFlamePowerUps() {
        return numberOfFlamePowerUps;
    }

    public int getNumberOfSpeedPowerUps() {
        return numberOfSpeedPowerUps;
    }

    public int getNumberOfPuropens() {
        return numberOfPuropens;
    }

    public int getNumberOfDenkyuses() {
        return numberOfDenkyuses;
    }

    public int getLevelCode() {
        return levelCode;
    }

    public int getNumberofSolidBlock() {
        return numberofSolidBlock;
    }

    public int getNumberOfDestructibleBlocks() {
        return numberOfDestructibleBlocks;
    }

    public static GameLevel fromString(String levelString) {
        return switch (levelString) {
            case "LEVEL_1" -> LEVEL_1;
            case "LEVEL_2" -> LEVEL_2;
            default -> throw new IllegalArgumentException("Invalid level string: " + levelString);
        };
    }

    public static GameLevel fromLevelCode(int levelCode) {
        return Arrays.stream(values())
                .filter(gameLevel -> gameLevel.levelCode == levelCode)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid level code: " + levelCode));
    }




}
