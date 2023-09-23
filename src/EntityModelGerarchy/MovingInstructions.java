package EntityModelGerarchy;

/**
 * The MovingInstructions interface represents instructions for controlling the movement of entities in the game.
 * Implementing classes should define the behavior for following these instructions.
 */
public interface MovingInstructions {

     /**
      * Defines the behavior for following the specified moving instruction.
      * Implementing classes should provide the logic for entity movement based on the instruction.
      */
     void followMovingInstruction();
}
