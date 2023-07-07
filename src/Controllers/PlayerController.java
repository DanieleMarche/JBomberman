package Controllers;

import entityGerarchy.Direction;
import player.Player;
import Animation.*;

public class PlayerController {
    private final Player player;
    private final KeyHandler keyHandler;
    private PowerUpsController powerUpsController;
    private final BombController bombController;
    private ExplosionController explosionController;
    private final CollisionDetector collisionDetector;

    private Animation currentAnimation;

    private int spriteCounter;

    private int previousSpriteNum;

    public PlayerController(Player player, KeyHandler keyHandler, PowerUpsController powerUpsController, BombController bombController, CollisionDetector collisionDetector, ExplosionController explosionController) {
        this.player = player;
        currentAnimation = player.getCurrentAnimation();
        this.keyHandler = keyHandler;
        this.powerUpsController = powerUpsController;
        this.bombController = bombController;
        this.collisionDetector = collisionDetector;
        this.explosionController = explosionController;
        spriteCounter = 0;
    }

    public void update() {

        spriteCounter++;

        if(!player.isMoving()) {

            if (keyHandler.isAMovementKeyPressed()) {
                if (keyHandler.isUpPressed()) {
                    player.setDirection(Direction.UP);
                }
                if (keyHandler.isDownPressed()) {
                    player.setDirection(Direction.DOWN);
                }
                if (keyHandler.isRightPressed()) {
                    player.setDirection(Direction.RIGHT);
                }
                if (keyHandler.isLeftPressed()) {
                    player.setDirection(Direction.LEFT);
                }

                currentAnimation = player.getCurrentAnimation();

                player.move();

                player.deActivateCollision();

            }

            if(!keyHandler.isAMovementKeyPressed() && currentAnimation.getCurrSprite() != 1) {
                if(spriteCounter % 10 != 0) currentAnimation.setDefaultSprite();
                spriteCounter = 0;
            }

        }else {

            collisionDetector.checkTile(player);
            //powerUpsController.collisionChecker.checkCollision(player);
            bombController.collisionChecker.checkCollision(player);
            //explosionController.collisionChecker.checkCollision(player);

            if (!player.isCollisionOn()) {
                switch (player.getDirection()) {
                    case UP -> player.moveUp();
                    case DOWN -> player.moveDown();
                    case LEFT -> player.moveLeft();
                    case RIGHT -> player.moveRight();
                }
            }

            //if (keyHandler.isAMovementKeyPressed()) {
                if (spriteCounter % 10 == 0) {
                    switch (currentAnimation.getCurrSprite()) {
                        case 2 -> {
                            currentAnimation.setPreviousSprite();
                            previousSpriteNum = 2;
                        }
                        case 0 -> {
                            currentAnimation.setNextSprite();
                            previousSpriteNum = 0;
                        }
                        case 1 -> {
                            if (previousSpriteNum == 2) currentAnimation.setPreviousSprite();
                            if (previousSpriteNum == 0) currentAnimation.setNextSprite();
                        }
                    }

                }
            //}

            player.incresePixelCounter();
            if(player.getPixelCounter() == 48) {
                player.stopMoving();
                player.resetPixelCounter();
            }
        }


        if(keyHandler.isABombDropAsked()) {
            bombController.dropBomb();
            System.out.println("bomba sganciata");
            keyHandler.deactivateBombAsked();
        }

        /**

        spriteCounter++;
        if (keyHandler.isAMovementKeyPressed()) {
            if (keyHandler.isUpPressed()) {
                player.setDirection(Direction.UP);
            }
            if (keyHandler.isDownPressed()) {
                player.setDirection(Direction.DOWN);

            }
            if (keyHandler.isRightPressed()) {
                player.setDirection(Direction.RIGHT);

            }
            if (keyHandler.isLeftPressed()) {
                player.setDirection(Direction.LEFT);
            }

            player.deActivateCollision();
            collisionDetector.checkTile(player);
            //powerUpsController.collisionChecker.checkCollision(player);
            bombController.collisionChecker.checkCollision(player);
            //explosionController.collisionChecker.checkCollision(player);

            if (!player.isCollisionOn()) {
                switch (player.getDirection()) {
                    case UP -> player.moveUp();
                    case DOWN -> player.moveDown();
                    case LEFT -> player.moveLeft();
                    case RIGHT -> player.moveRight();
                }
            }

            if (keyHandler.isAMovementKeyPressed()) {
                if (spriteCounter % 15 == 0) {
                    switch (player.getSpriteNum()) {
                        case 2 -> {
                            player.setPreviousSpriteNum();
                            previousSpriteNum = 2;
                        }
                        case 0 -> {
                            player.setNextSpriteNum();
                            previousSpriteNum = 0;
                        }
                        case 1 -> {
                            if (previousSpriteNum == 2) player.setPreviousSpriteNum();
                            if (previousSpriteNum == 0) player.setNextSpriteNum();
                        }
                    }

                }
            }

        }else {
            if (spriteCounter % 15 == 0)  player.setDefaultSpriteNum();

        }



        if(keyHandler.isABombDropAsked()) {
            bombController.dropBomb();
            System.out.println("bomba sganciata");
            keyHandler.deactivateBombAsked();
        }

         **/
    }



}

