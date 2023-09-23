package TopBar;

import Animation.AnimationMessages;
import Controllers.TopBarController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.logging.Level;

/**
 * The LevelTimer class is responsible for managing the timer for a level.
 *
 * It has a duration of 3 minutes, and can be paused and resumed.
 *
 * @Author Daniele Marchetilli
 */
public class LevelTimer extends Observable {

    private static LevelTimer instance = null;

    /**
     * The total duration of the timer in seconds.
     */
    private int durationInSeconds;

    /**
     * The remaining time in the timer.
     */
    private int remainingTime;

    /**
     * Whether the timer is paused.
     */
    private boolean isPaused;

    /**
     * The timer object.
     */
    private Timer timer;

    public static LevelTimer getInstance() {
        if(instance == null) instance = new LevelTimer();
        return instance;
    }

    /**
     * Constructs a new LevelTimer object.
     * It adds itself as an observer to the TopBarController object.
     */
    private LevelTimer() {
        addObserver(TopBarController.getInstance());
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    /**
     * Returns whether the timer is paused.
     *
     * @return Whether the timer is paused.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Returns the remaining time in the timer.
     *
     * @return The remaining time in the timer.
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Pauses the timer.
     */
    public void pause() {
        if (timer != null && timer.isRunning()) {
            durationInSeconds = remainingTime;
            timer.stop();
            isPaused = true;
        }
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Resumes the timer.
     */
    public void resume() {
        if (isPaused) {
            start();
            isPaused = false;
        }
    }

    /**
     * Starts the timer.
     */
    public void start() {
        remainingTime = durationInSeconds;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;

                if (remainingTime > 0) {
                    setChanged();
                    notifyObservers(AnimationMessages.REPAINT_TOPBAR);
                } else {
                    timer.stop();
                    setChanged();
                    notifyObservers(AnimationMessages.GAME_LOST);
                }
            }
        });

        Thread timerThread = new Thread(() -> {
            timer.start();

            try {
                Thread.sleep(durationInSeconds * 1000L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        });

        timerThread.start();
    }

    public static void removeInstance() {
        instance = null;
    }
}


