package tile;

public class SolidBlock extends NotAnimatedSingleSpriteTile {

    private static final String imagePath = "/Blocks/solid_block.png";
    public static SolidBlock solidBlock = null;

    public static SolidBlock getIstance () {

        if(solidBlock == null) { solidBlock = new SolidBlock();}

        return solidBlock;

    }

    private SolidBlock() {
        super(imagePath, true, false, false);
    }
}
