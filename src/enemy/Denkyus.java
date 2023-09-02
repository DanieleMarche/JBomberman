package enemy;

import Animation.ReversedLoopAnimation;
import Controllers.AssetManager;
import Controllers.CollisionDetector;
import Controllers.EnemyController;
import Utils.ImageUtils;
import entityGerarchy.MovingAliveEntityState;
import main.GameView;
import Tile.Map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Observable;

public class Denkyus extends Enemy{

    private static final String animationPath = "res/Enemies/denkyu";



    public Denkyus(int row, int col) {
        super(col * GameView.tileSize, row * GameView.tileSize + GameView.tileSize / 2, 1, GameView.tileSize, GameView.tileSize + GameView.tileSize / 2, 0, 24, animationPath, 0, 10, 2, EnemyController.getInstance());
        frontLoopAnimation = new ReversedLoopAnimation(frontLoopAnimation, this);
        backLoopAnimation = new ReversedLoopAnimation(backLoopAnimation, this);
        leftSideLoopAnimation = new ReversedLoopAnimation(leftSideLoopAnimation, this);
        rightSideLoopAnimation = new ReversedLoopAnimation(rightSideLoopAnimation, this);
        movingInstructions = () -> {
            Map map = AssetManager.getInstance().getMap();
            if(getState() == MovingAliveEntityState.ALIVE || getState() == MovingAliveEntityState.INVULNERABILITY) {

                if(getState() == MovingAliveEntityState.INVULNERABILITY) {
                    invulnerabilityTimeCounter++;
                    if(invulnerabilityTimeCounter == invulnerabilityTime) {
                        state = MovingAliveEntityState.ALIVE;
                        invulnerabilityTimeCounter = 0;
                    }
                }

                if(!isMoving()) {



                    CollisionDetector.checkCollisionWithTiles(this, map);

                    if(isCollision()) {

                        changeRandomlyDirection(map);
                        deActivateCollision();

                    }


                    if(CollisionDetector.getFreeSurroundings(this, map).length > 2) {
                        changeRandomlyDirection(map);
                    }


                    setMoving();


                }else {

                    collisionDetector.checkCollisionWhenMoving(this);

                    if(isCollision()) {
                        stopMoving();
                        changeRandomlyDirection(map);
                    } else moveOneBlock();

                }
            }
            animate.updateAnimation();

        };

    }

    @Override
    public void die() {
        if (state != MovingAliveEntityState.INVULNERABILITY) {
            if(lives == 0) {
                state = MovingAliveEntityState.DYING;
                currentLoopAnimation = damageLoopAnimation;
            } else {
                lives--;
                state = MovingAliveEntityState.INVULNERABILITY;
            }

        }

    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    @Override
    public int getPoints() {
        return 200;
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = currentLoopAnimation.getCurrentImage();

        if (Objects.requireNonNull(state) == MovingAliveEntityState.INVULNERABILITY && invulnerabilityTime % 5 == 0) {
            image = ImageUtils.convertNonTransparentToWhite(image);
        }

        g2.drawImage(image, worldPositionX, worldPositionY, spritesWidth * GameView.tileScale, spritesHeight * GameView.tileScale, null);
    }
}
