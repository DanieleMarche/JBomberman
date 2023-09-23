package EntityModelGerarchy;

/**
 * The Dieable interface represents entities that can be "killed" or "destroyed" in the game.
 * Implementing classes should define the behavior that occurs when an entity dies.
 */
public interface Dieable {

    /**
     * Defines the action to be taken when the implementing entity "dies" in the game.
     */
    void die();
}

