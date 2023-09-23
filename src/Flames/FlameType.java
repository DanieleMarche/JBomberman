package Flames;

/**
 * This enum class defines the different types of flames present in this game and
 * every instance contains the path to its animation.
 */
public enum FlameType {

    BOMB_FLAME("res/Bomb/bomb_explosion"),
    DESTRUCTABLE_BLOCK_FLAME("res/Explosion/Destructible_block_explosion"),
    HORIZONTHAL_FLAME("res/Explosion/horizonthal_flames"),
    VERTICAL_FLAME("res/Explosion/vertical_flames"),
    UPPER_FLAME_TIP("res/Explosion/upper_flame_tips"),
    LOWER_FLAME_TIP("res/Explosion/lower_flame_tips"),
    RIGHT_FLAME_TIP("res/Explosion/right_flame_tips"),
    LEFT_FLAME_TIP("res/Explosion/left_flame_tips");

    private final String animationPath;

    FlameType(String animationPath) {
        this.animationPath = animationPath;
    }

    public String getAnimationPath() {
        return animationPath;
    }
}
