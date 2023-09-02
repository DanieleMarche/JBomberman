package Controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class KeyHandler implements KeyListener {

    private static KeyHandler instance = null;

    public static KeyHandler getInstance() {
        if(instance == null) instance = new KeyHandler();
        return instance;
    }

    private boolean aMovementKeyPressed, upPressed, downPressed, leftPressed, rightPressed, bombDropAsked, stopPlaying;

    public KeyHandler() {
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        aMovementKeyPressed = false;
        bombDropAsked = false;
        stopPlaying = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }


        if(upPressed || downPressed || leftPressed || rightPressed) aMovementKeyPressed = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;

        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;

        }
        if(code == VK_B) {
            bombDropAsked = true;
        }
        if (code == VK_P) {
            stopPlaying = true;
        }

        if(!upPressed && !downPressed &&!rightPressed && !leftPressed) {aMovementKeyPressed = false;}


    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isAMovementKeyPressed() {
        return aMovementKeyPressed;
    }

    public boolean isABombDropAsked() {
        return bombDropAsked;
    }

    public void deactivateBombAsked() {
        bombDropAsked = false;
    }

    public void deactivateStopPlaying() {
        stopPlaying = false;
    }

    public boolean stopPLayingAsked() {
        return stopPlaying;
    }

    public static void removeInstance() {
        instance = null;
    }

}
