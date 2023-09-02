package PowerUp;

import Animation.AnimationMessages;
import Bomberman.Player;
import Controllers.AudioManager;
import Controllers.CollisionDetector;
import Controllers.PowerUpsController;
import entityGerarchy.MovingEntity;
import entityGerarchy.NotMovingAnimatedEntity;
import main.GameView;
import main.Scored;

import java.awt.*;
import java.util.Observable;

public class PowerUp extends NotMovingAnimatedEntity implements Scored {
    protected PowerUpType type;

    private boolean visible;


    public  PowerUp(int x, int y, PowerUpType type) {
        super(x, y, type.getAnimationPath(), 10, PowerUpsController.getInstance());
        this.type = type;

        collisionChecker = (MovingEntity player) -> {
            if(player instanceof Player) {
                    if(CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(player, this) && isVisible()) {
                        AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/item-get.wav");
                        powerUpActivate((Player) player);
                        notifyObservers(AnimationMessages.REMOVE_ELEMENT);
                    }
                }
            };


        visible = false;
    }

    public void powerUpActivate(Player player) {

        switch(type) {
            case MORE_SPEED -> player.increaseSpeed();
            case MORE_BOMB_AT_SAME_TIME -> player.increaseBombAtSameTime();
            case MORE_FLAME -> player.increaseExplosionRadius();
        }
        player.addScore(this);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(){
        visible = true;
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_GAME);
    }

    public PowerUpType getType() {
        return type;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(isVisible()){
            setChanged();
            notifyObservers(arg);
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        if(isVisible()) g2.drawImage(loopAnimation.getCurrentImage(), worldPositionX, worldPositionY, GameView.tileSize, GameView.tileSize, null);
    }

    @Override
    public int getPoints() {
        return type.getPoints();
    }
}
