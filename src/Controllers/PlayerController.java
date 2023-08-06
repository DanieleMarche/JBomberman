package Controllers;

import entityGerarchy.Direction;
import player.Player;
import Animation.*;

public class PlayerController {
    private final Player player;
    private final KeyHandler keyHandler;
    private final BombController bombController;
    private final CollisionDetector collisionDetector;

    private Animation currentAnimation;
    private int spriteCounter;
    private int previousSpriteNum;

    public PlayerController(Player player, KeyHandler keyHandler, BombController bombController, CollisionDetector collisionDetector) {
        this.player = player;
        currentAnimation = player.getCurrentAnimation();
        this.keyHandler = keyHandler;

        this.bombController = bombController;
        this.collisionDetector = collisionDetector;
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

                collisionDetector.checkCollisionWithTiles(player);

            }

            if(!keyHandler.isAMovementKeyPressed() && currentAnimation.getCurrentSprite() != 1) {
                if(spriteCounter % 10 != 0) currentAnimation.setDefaultSprite();
                spriteCounter = 0;
            }

        }else {

            collisionDetector.checkCollisionWithNotMovingEntities(player);

            if (!player.isCollisionOn()) {
                switch (player.getDirection()) {
                    case UP -> player.moveUp();
                    case DOWN -> player.moveDown();
                    case LEFT -> player.moveLeft();
                    case RIGHT -> player.moveRight();
                }
            }


            if (spriteCounter % 10 == 0) {
                switch (currentAnimation.getCurrentSprite()) {
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


            player.incresePixelCounter();
            if(player.getPixelCounter() == 48) {
                player.stopMoving();
                player.resetPixelCounter();
            }
        }


        if(keyHandler.isABombDropAsked()) {
            bombController.dropBomb();
            keyHandler.deactivateBombAsked();
        }

    }



}

