package Bomberman;

import Animation.AnimationMessages;
import Animation.CycledAnimation;
import Controllers.ControllersGerarchy.BombController;
import Bomb.BombModel;
import Controllers.*;
import Portal.PortalController;
import PowerUp.PowerUpsController;
import User.UserModel;
import Utils.ImageUtils;
import EntityModelGerarchy.Direction;
import EntityModelGerarchy.MovingAliveEntityState;
import EntityModelGerarchy.MovingLiveEntity;
import main.GameView;
import main.Scored;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Optional;
import java.util.Random;

/**
 * The Player class represents the player character in the Bomberman game. It extends the MovingLiveEntity class
 * and defines the behavior of the player character, including movement, dropping bombs, and handling collisions.
 *
 * This class is designed as a singleton, allowing only one player character in the game.
 *
 * @author Daniele Marchetilli
 *
 */
public class PlayerModel extends MovingLiveEntity {

    /**
     * The only instance possible for this class.
     */
    static PlayerModel instance = null;

    /**
     * The cohordinates where the BomberMan will autotically spawn when he dies.
     * The first index indicates the row and the second index indicates the column on the map.
     */
    private static final int[] spawnCohordinates = new int[] {1, 2};;

    /**
     * The original speed represents thye number of pixels that the bomberman will move its position when it moves.
     */
    private static final int originalSpeed = 2;

    /**
     * The total score the player got during the game.
     */
    private static int totalScore = 0;

    /**
     * How many bombs at the same time can the bomberman drop.
     */
    private int bombAtSameTime;

    /**
     * How many bombs at the same time remains at the bomberman to drop.
     */
    private int remainingBombsAtSameTime;
    /**
     * The explosion radius the bombs that bomberman drop will have.
     */
    private static int explosionRadius;
    /**
     * The seconds multiplied by 3 the player will stay in the state of invulnerability when he is dropped.
     */
    private int invulnerabityTime;

    /**
     * The previous frame index of the bomberman animation.
     */
    private int previousSpriteNum;

    /*
    How many lifes remains to the bomberman befor the player loses the game.
     */
    private static int lifes;

    /**
     * The number of lifes the bomberman will have when the player start a level.
     */
    private static final int lifesResetValue = lifes = 5;

    /**
     * This method return the only instance possible of the bomberman, and if it is null, then it instance a new
     * bomberman.
     * @return The bomberman instance.
     */
    public static PlayerModel getInstance() {
        if(instance == null) {
            instance = new PlayerModel();
        }
        return instance;
    }

    /**
     * Private constructor to create a Player instance. This constructor initializes the player's attributes,
     * including speed, bombs, explosion radius, and more.
     */
    private PlayerModel() {
        super(spawnCohordinates[1] * GameView.tileSize, (spawnCohordinates[0] * GameView.tileSize) - (GameView.tileSize / 2), originalSpeed, 48 + 24, GameView.tileSize, 0, 24, GameView.tileSize, GameView.tileSize, "res/bombermanSprites/"+ UserModel.getInstance().getAvatar().toString(), 1, 5);
        // Add observers to the player
        addObserver(PlayerController.getInstance());
        PlayerController.getInstance().setEntity(this);
        addObserver(TopBarController.getInstance());
        AssetManager.getInstance().setPlayer(this);

        UserModel.ColoreAvatar color = UserModel.getInstance().getAvatar();
        bombAtSameTime = remainingBombsAtSameTime = 1;
        explosionRadius = 1;
        previousSpriteNum = 0;
        invulnerabityTime = 240;
        state = MovingAliveEntityState.INVULNERABILITY;

        // Define the damage loop animation
        damageLoopAnimation = new CycledAnimation("res/bombermanSprites/" + color.toString() + "/death", 0, 5, this, 1);
        stopMoving();

        // Add collision checkers for the entities the bomberman will collide with.
        collisionDetector.addCollisionCheckerWhenMoving(BombController.getInstance());
        collisionDetector.addCollisionCheckerWhenNotMoving(PowerUpsController.getInstance());
        collisionDetector.addCollisionCheckerWhenMoving(EnemyController.getInstance());
        collisionDetector.addCollisionCheckerWhenNotMoving(EnemyController.getInstance());
        collisionDetector.addCollisionCheckerWhenNotMoving(FlameController.getInstance());
        collisionDetector.addCollisionCheckerWhenMoving(PortalController.getInstance());

        currentDisplayingImage = currentLoopAnimation.getCurrentImage();

        // Define moving instructions for the player
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

        };

        movingThread.setMovingInstructions(movingInstructions);
        movingThread.startThread();
        animatedEntityThread.startThread();

        setChanged();
        notifyObservers(AnimationMessages.REPAINT_TOPBAR);

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
    public static int getTotalScore() {
        return totalScore;
    }
    public static int getLifes() {
        return lifes;
    }
    public int getBombAtSameTime() {
        return bombAtSameTime;
    }
    public static  int getExplosionRadius() {
        return explosionRadius;
    }
    public static void resetLifes() {
        lifes = lifesResetValue;
    }

    /**
     * This funcition adds at the bombs array in the AssetManager a new bomb if the number of bomb at the same time
     * the player can drop is more than zero.
     */
    public void dropBomb () {
        if(getRemainingBombsAtSameTime() > 0) {
            decreaseRemainingBombsAtSameTime();
            AssetManager.getInstance().addBomb(new BombModel((getCol() * GameView.tileSize), (getRow() * GameView.tileSize), this));
        }
    }

    /**
     * This functions takes as parameter a Scored instance and uses its contract to add the score to its total score.
     * @param score The instance that implements scored
     */
    public void addScore(Scored score) {
        totalScore += score.getPoints();
        setChanged();
        notifyObservers(AnimationMessages.REPAINT_TOPBAR);
    }

    /**
     * This function decremetns the life of the bomberman, updating the top bar and. if the remaining lifes are 1,
     * then it sends a message saying that the game is lost.
     */
    public void decrementLifes() {
        if(lifes > 1) {
            lifes--;
            setChanged();
            notifyObservers(AnimationMessages.REPAINT_TOPBAR);
        } else {
            setChanged();
            notifyObservers(AnimationMessages.GAME_LOST);
        }

    }

    /*
    This function makes the bomberman stop moving and updates its animation instructions.
     */
    @Override
    public void stopMoving() {
        super.stopMoving();
        animate = () -> {
            currentDisplayingImage = currentLoopAnimation.getCurrentImage();
            currentLoopAnimation.setDefaultSprite();
        };
        animatedEntityThread.setAnimate(animate);
    }

    /**
     * This function set the moving flag to true and updates the animation instructions.
     */
    @Override
    public void setMoving() {
        super.setMoving();
        animate = () -> {
            currentDisplayingImage = currentLoopAnimation.getCurrentImage();
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

        };

        animatedEntityThread.setAnimate(animate);
    }

    /**
     * This function decreases the invulnerabilityTime by one and if it is zero, it changes the bomberman status to alive.
     */
    public void decreaseInvulnerabilityTime() {
        invulnerabityTime--;
        if(invulnerabityTime == 0) {
            state = MovingAliveEntityState.ALIVE;
        }
    }


    public void increaseBombAtSameTime() {
        bombAtSameTime++;
        remainingBombsAtSameTime++;
    }


    /**
     * This functions changes the status of the bomberman, changes its current animation to damage animation and updates
     * the animation instructions if the bomberman state is alive.
     */
    public void die() {
        if(state == MovingAliveEntityState.ALIVE) {
            AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/bomberman-dies.wav");

            decrementLifes();
            state = MovingAliveEntityState.DYING;
            currentLoopAnimation = damageLoopAnimation;

            animate = () -> {
                currentDisplayingImage = currentLoopAnimation.getCurrentImage();
                currentLoopAnimation.setNextSprite();
            };
            animatedEntityThread.setAnimate(animate);

        }
    }

    @Override
    public Optional<BufferedImage> getImage() {
        if(state == MovingAliveEntityState.INVULNERABILITY) {
            Random r = new Random();
            if(r.nextInt() % 2 == 0)  return Optional.of(ImageUtils.convertNonTransparentToWhite(currentDisplayingImage));
        }
        return Optional.of(currentDisplayingImage);
    }

    /**
     * This funcion removes sets the instance of the player to null.
     */
    public static void removeInstance() {
        instance = null;
    }


    /**
     * This functions updates the observers of this class with the same arguments of its obervables sent him.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(arg == AnimationMessages.REMOVE_ELEMENT) animatedEntityThread.stopThread();
        setChanged();
        notifyObservers(arg);
    }


}