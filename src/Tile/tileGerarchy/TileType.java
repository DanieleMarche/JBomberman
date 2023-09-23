package Tile.tileGerarchy;


/**
 * The `TileType` enum represents various types of tiles that can be used in the game map grid.
 * Each tile type has specific properties, such as whether it's solid, explodable, or can catch fire.
 *
 * @Author Daniele Marchetilli
 */
public enum TileType {
    SOLID_BLOCK(3, true, false, false),
    LIMIT_BLOCK(1, true, false, false),
    WALKABLE_BLOCK(0, false, false, true),
    DESTRUCTIBLE_BLOCK(2, true, true, true),
    DESTRUCTIBLE_BLOCK_WITH_PORTAL(2, true, true, true);

    private final int tileCode;

    private boolean solid;
    private boolean explodable;
    private boolean getFire;

    TileType(int tileCode, boolean solid, boolean explodable, boolean getFire) {

        this.tileCode = tileCode;
        this.solid = solid;
        this.explodable = explodable;
        this.getFire = getFire;
    }

    public static TileType getTileTypeFronInt(int code) {
        for ( TileType type: TileType.values()) {
            if(type.tileCode == code) return type;
        }
        return null;
    }

    public int getTileCode() {
        return tileCode;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean isExplodable() {
        return explodable;
    }

    public boolean isGetFire() {
        return getFire;
    }
}
