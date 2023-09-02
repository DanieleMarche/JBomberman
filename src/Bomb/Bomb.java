package Bomb;

import Controllers.*;
import Explosion.Explosion;
import enemy.Enemy;
import entityGerarchy.NotMovingAnimatedEntity;
import main.GameView;
import Animation.*;
import Bomberman.Player;

import java.awt.*;
import java.util.Observable;

import static Controllers.CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity;


public class Bomb extends NotMovingAnimatedEntity {

    private final Player player;
    private boolean passedInto;

    public Bomb(int worldX, int worldY, Player player) {
        super(worldX, worldY, "res/Bomb", 15, BombController.getInstance());

        passedInto = false;

        this.player = player;

        loopAnimation = new CycledReversedAnimation(loopAnimation, this, 4);

        collisionChecker = movingEntity -> {


                if (movingEntity instanceof Player) {
                    if (CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, this) && passedInto) {
                        movingEntity.activateCollision();

                    }

                    if (!CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, this) && !passedInto && movingEntity.equals(player)) {
                        passedInto = true;
                    }
                }

                if (movingEntity instanceof Enemy && checkcollisionBetweenMovingentityAndOtherEntity(movingEntity, this)) {
                    Rectangle intersection = getBounds().union(movingEntity.getBounds());
                    if(intersection.height * intersection.width == (GameView.tileSize*GameView.tileSize)*2) {
                        movingEntity.activateCollision();
                    }
                }




    };

    }

    public Player getPlayer() {
        return player;
    }

    public boolean isPassedInto() {
        return passedInto;
    }

    public void hasPassedInto() {
        passedInto = true;
    }

    public void explode ()  {
        if(player != null) player.increaseRemainingBombsAtSameTime();
        AssetManager.getInstance().addExplosion(new Explosion(getCol(), getRow(), Player.getExplosionRadius(), this, FlameController.getInstance()));
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(loopAnimation.getCurrentImage(), worldPositionX, worldPositionY, GameView.tileSize, GameView.tileSize, null);

    }

    @Override
    public void update(Observable o, Object arg) {

        setChanged();
        notifyObservers(arg);

    }
}
