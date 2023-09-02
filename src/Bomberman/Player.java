package Bomberman;

import Animation.AnimationMessages;
import Animation.CycledAnimation;
import Bomb.Bomb;
import Controllers.*;
import User.UserModel;
import Utils.ImageUtils;
import entityGerarchy.Direction;
import entityGerarchy.MovingAliveEntityState;
import entityGerarchy.MovingLiveEntity;
import main.GameView;
import main.Scored;
import Tile.Map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Observable;

public class Player extends MovingLiveEntity {

    static Player instance = null;

    /**
     * The cohordinates where the BomberMan will autotically spawn when he dies.
     * The first index indicates the row and the second index indicates the column on the map.
     */
    private static final int[] spawnCohordinates = new int[] {1, 2};;

    private static final int originalSpeed = 2;

    private static int totalScore = 0;

    private int bombAtSameTime;
    private int remainingBombsAtSameTime;
    private static int explosionRadius;
    private int invulnerabityTime;

    private int previousSpriteNum;

    private static int lifes = 5;

    private final UserModel.ColoreAvatar color;

    public static Player getInstance() {
        if(instance == null) {
            instance = new Player();
        }
        return instance;
    }

    private Player() {
        super(spawnCohordinates[1] * GameView.tileSize, (spawnCohordinates[0] * GameView.tileSize) - (GameView.tileSize / 2), originalSpeed, 48 + 24, GameView.tileSize, 0, 24, GameView.tileSize, GameView.tileSize, "res/bomberMan/"+ UserModel.getInstance().getAvatar().toString(), 1, 10);
        addObserver(PlayerController.getInstance());
        PlayerController.getInstance().setEntity(this);
        addObserver(TopBarController.getInstance());

        color = UserModel.getInstance().getAvatar();
        bombAtSameTime = remainingBombsAtSameTime = 1;
        explosionRadius = 1;
        previousSpriteNum = 0;
        invulnerabityTime = 240;
        state = MovingAliveEntityState.INVULNERABILITY;
        damageLoopAnimation = new CycledAnimation("res/bomberMan/" + color.toString() + "/death", 0, 20, this, 1);
        stopMoving();

        collisionDetector.addCollisionCheckerWhenMoving(BombController.getInstance());
        collisionDetector.addCollisionCheckerWhenNotMoving(PowerUpsController.getInstance());
        collisionDetector.addCollisionCheckerWhenMoving(EnemyController.getInstance());
        collisionDetector.addCollisionCheckerWhenNotMoving(FlameController.getInstance());
        collisionDetector.addCollisionCheckerWhenMoving(PortalController.getInstance());

        movingInstructions = () -> {
            KeyHandler keyHandler = KeyHandler.getInstance();

            switch (getState()) {

                case ALIVE, INVULNERABILITY-> {

                    if(getState() == MovingAliveEntityState.INVULNERABILITY) {
                        decreaseInvulnerabilityTime();
                        if(invulnerabityTime == 0) {
                            state = MovingAliveEntityState.ALIVE;
                        }
                    }

                    if(!isMoving()) {

                        if (keyHandler.isAMovementKeyPressed()) {
                            if (keyHandler.isUpPressed()) {
                                setDirection(Direction.UP);
                            }
                            if (keyHandler.isDownPressed()) {
                                setDirection(Direction.DOWN);
                            }
                            if (keyHandler.isRightPressed()) {
                                setDirection(Direction.RIGHT);
                            }
                            if (keyHandler.isLeftPressed()) {
                                setDirection(Direction.LEFT);
                            }

                            setMoving();
                            AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/walking-1.wav");

                            CollisionDetector.checkCollisionWithTiles(this, AssetManager.getInstance().getMap());

                        }

                    }else {

                        collisionDetector.checkCollisionWhenMoving(this);

                        int originalSpeed = getSpeed();

                        while(getPixelCounter() + getSpeed() > GameView.tileSize) {
                            decreaseSpeed();
                        }

                        moveOneBlock();

                        while(getSpeed() < originalSpeed) {
                            increaseSpeed();
                        }

                    }

                    collisionDetector.checkCollisionWhenNotMoving(this);


                    if(keyHandler.isABombDropAsked()) {
                        dropBomb();
                        keyHandler.deactivateBombAsked();
                    }

                }
            }

            animate.updateAnimation();
        };

                setChanged();
        notifyObservers(AnimationMessages.REPAINT_TOPBRAID);
    }

    public void dropBomb () {
        if(getRemainingBombsAtSameTime() > 0) {
            decreaseRemainingBombsAtSameTime();

            AssetManager.getInstance().addBomb(new Bomb((getCol() * GameView.tileSize), (getRow() * GameView.tileSize), this));
        }
    }

    public static int getTotalScore() {
        return totalScore;
    }

    public void addScore(Scored score) {
        totalScore += score.getPoints();
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_TOPBRAID);
    }

    public static int getLifes() {
        return lifes;
    }

    public void decrementLifes() {
        if(lifes > 1) {
            lifes--;

        } else {
            setChanged();
            notifyObservers(AnimationMessages.GAME_LOST);
        }

    }

    @Override
    public void stopMoving() {
        super.stopMoving();
        animate = () -> {
            updateCallCounter++;
            if(updateCallCounter % currentLoopAnimation.getAnimationSpeed() != 0) {
                currentLoopAnimation.setDefaultSprite();
                updateCallCounter = 0;
            }

        };
    }

    @Override
    public void setMoving() {
        super.setMoving();
        animate = () -> {
            updateCallCounter++;
            if (updateCallCounter % getCurrentAnimation().getAnimationSpeed() == 0) {
                switch (currentLoopAnimation.getCurrentSpriteIndex()) {
                    case 2 -> {
                        currentLoopAnimation.setPreviousSprite();
                        previousSpriteNum = 2;
                    }
                    case 0 -> {
                        currentLoopAnimation.setNextSprite();
                        previousSpriteNum = 0;
                    }
                    case 1 -> {
                        if (previousSpriteNum == 2) currentLoopAnimation.setPreviousSprite();
                        if (previousSpriteNum == 0) currentLoopAnimation.setNextSprite();
                    }

                }
                updateCallCounter = 0;

            }
        };
    }

    public void decreaseInvulnerabilityTime() {
        invulnerabityTime--;
        if(invulnerabityTime == 0) {
            state = MovingAliveEntityState.ALIVE;
        }
    }

    public int getBombAtSameTime() {
        return bombAtSameTime;
    }
    public static  int getExplosionRadius() {
        return explosionRadius;
    }
    public void increaseBombAtSameTime() {
        bombAtSameTime++;
        remainingBombsAtSameTime++;
    }
    public void decreaseBombAtSameTime() {
        bombAtSameTime--;
    }
    public void increaseExplosionRadius() {
        explosionRadius++;
    }
    public void increaseSpeed() {
        speed++;
    }
    public void decreaseSpeed() {
        speed--;
    }
    public void decreaseExplosionRadius() {
        explosionRadius--;
    }
    public int getRemainingBombsAtSameTime() {
        return remainingBombsAtSameTime;
    }
    public void increaseRemainingBombsAtSameTime() {
        remainingBombsAtSameTime ++;
    }
    public void decreaseRemainingBombsAtSameTime() {
        remainingBombsAtSameTime --;
    }

    public void die() {
        if(state == MovingAliveEntityState.ALIVE) {
            AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/bomberman-dies.wav");

            decrementLifes();
            state = MovingAliveEntityState.DYING;
            currentLoopAnimation = damageLoopAnimation;

            animate = () -> {
                updateCallCounter++;
                if(updateCallCounter % currentLoopAnimation.getAnimationSpeed() == 0) {
                    currentLoopAnimation.setNextSprite();
                    updateCallCounter = 0;
                }

            };



        }
    }

    public static void removeInstance() {
        instance = null;
        if(lifes == 0) lifes = 5;
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = currentLoopAnimation.getCurrentImage();

        if (Objects.requireNonNull(state) == MovingAliveEntityState.INVULNERABILITY && invulnerabityTime % 5 == 0) {
                image = ImageUtils.convertNonTransparentToWhite(image);
        }

        g2.drawImage(image, worldPositionX, worldPositionY, spritesWidth * GameView.tileScale, spritesHeight * GameView.tileScale, null);

    }


}