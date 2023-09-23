package Controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

/**
 * Author: [Your Name]
 *
 * The KeyHandler class manages keyboard input for the Bomberman game.
 * It implements the KeyListener interface to handle key events and provides
 * methods to query the state of various keys.
 */
public class KeyHandler implements KeyListener {

    /**
     * The singleton instance of the KeyHandler class.
     */
    private static KeyHandler instance = null;

    /**
     * Get the singleton instance of the KeyHandler class.
     *
     * @return The KeyHandler instance.
     */
    public static KeyHandler getInstance() {
        if (instance == null) instance = new KeyHandler();
        return instance;
    }

    /**
     * Flag indicating if the 'W' key is pressed.
     */
    private boolean upPressed;

    /**
     * Flag indicating if the 'S' key is pressed.
     */
    private boolean downPressed;

    /**
     * Flag indicating if the 'A' key is pressed.
     */
    private boolean leftPressed;

    /**
     * Flag indicating if the 'D' key is pressed.
     */
    private boolean rightPressed;

    /**
     * Flag indicating if any movement key ('W', 'A', 'S', 'D') is pressed.
     */
    private boolean aMovementKeyPressed;

    /**
     * Flag indicating if the 'B' key (for bomb drop) is pressed.
     */
    private boolean bombDropAsked;

    /**
     * Flag indicating if the 'P' key (for pausing the game) is pressed.
     */
    private boolean stopPlaying;

    /**
     * Private constructor to ensure a single instance of KeyHandler.
     * Use {@link #getInstance()} to obtain the instance.
     */
    private KeyHandler() {
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
        // Not used in this implementation.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if (upPressed || downPressed || leftPressed || rightPressed) {
            aMovementKeyPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_B) {
            bombDropAsked = true;
        }
        if (code == KeyEvent.VK_P) {
            stopPlaying = true;
        }

        if (!upPressed && !downPressed && !rightPressed && !leftPressed) {
            aMovementKeyPressed = false;
        }
    }

    /**
     * Check if the 'W' key is pressed.
     *
     * @return True if the 'W' key is pressed, false otherwise.
     */
    public boolean isUpPressed() {
        return upPressed;
    }

    /**
     * Check if the 'S' key is pressed.
     *
     * @return True if the 'S' key is pressed, false otherwise.
     */
    public boolean isDownPressed() {
        return downPressed;
    }

    /**
     * Check if the 'A' key is pressed.
     *
     * @return True if the 'A' key is pressed, false otherwise.
     */
    public boolean isLeftPressed() {
        return leftPressed;
    }

    /**
     * Check if the 'D' key is pressed.
     *
     * @return True if the 'D' key is pressed, false otherwise.
     */
    public boolean isRightPressed() {
        return rightPressed;
    }

    /**
     * Check if any movement key ('W', 'A', 'S', 'D') is pressed.
     *
     * @return True if any movement key is pressed, false otherwise.
     */
    public boolean isAMovementKeyPressed() {
        return aMovementKeyPressed;
    }

    /**
     * Check if the 'B' key (for bomb drop) is pressed.
     *
     * @return True if the 'B' key is pressed, false otherwise.
     */
    public boolean isABombDropAsked() {
        return bombDropAsked;
    }

    /**
     * Deactivate the bomb drop request.
     */
    public void deactivateBombAsked() {
        bombDropAsked = false;
    }

    /**
     * Deactivate the stop playing request.
     */
    public void deactivateStopPlaying() {
        stopPlaying = false;
    }

    /**
     * Check if the stop playing request is active.
     *
     * @return True if the stop playing request is active, false otherwise.
     */
    public boolean stopPlayingAsked() {
        return stopPlaying;
    }

    /**
     * Remove the singleton instance of KeyHandler.
     * Use with caution.
     */
    public static void removeInstance() {
        instance = null;
    }
}

