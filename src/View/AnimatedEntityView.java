package View;

import Animation.Animate;
import Animation.LoopAnimation;
import EntityModelGerarchy.Entity;

public class AnimatedEntityView extends EntityView{

    private LoopAnimation animation;

    private Animate animate;

    public AnimatedEntityView(Entity entity) {
        super(entity);
    }

    public void setAnimation(LoopAnimation animation) {
        this.animation = animation;
    }

    public void setAnimate(Animate animate) {
        this.animate = animate;
    }
}
