package Thread;

import EntityModelGerarchy.MovingInstructions;

public class MovingThread extends EntityThread{

    private MovingInstructions movingInstructions;
    public MovingThread() {
        super();
        speed = 60;
    }

    public void setMovingInstructions(MovingInstructions movingInstructions) {
        this.movingInstructions = movingInstructions;
    }

    @Override
    void perform() {
        movingInstructions.followMovingInstruction();
    }
}
