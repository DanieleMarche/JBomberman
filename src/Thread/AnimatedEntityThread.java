package Thread;

import Animation.Animate;
import Animation.LoopAnimation;

public class AnimatedEntityThread extends EntityThread {

    private LoopAnimation animation;

    protected Animate animate;

    public AnimatedEntityThread(LoopAnimation animation, Animate animate) {
        super();
        this.animation = animation;
        this.animate = animate;
        speed = animation.getAnimationSpeed();
        thread = new Thread(this);
    }

    public LoopAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(LoopAnimation animation) {
        this.animation = animation;
    }

    public Animate getAnimate() {
        return animate;
    }

    public void setAnimate(Animate animate) {
        this.animate = animate;
    }



    @Override
    void perform() {
        animate.updateAnimation();
    }


}
