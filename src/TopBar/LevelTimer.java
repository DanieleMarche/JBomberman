package TopBar;

import Animation.AnimationMessages;
import Controllers.TopBarController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class LevelTimer extends Observable {
    private final int durationInSeconds = 3 * 60; // Durata totale del timer in secondi
    private int remainingTime;
    private boolean isPaused = false;

    private Timer timer;

    public LevelTimer() {
        addObserver(TopBarController.getInstance());
        start();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void pause() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            isPaused = true;
        }
    }

    public void stop() {
        timer.stop();
    }

    public void resume() {
        if (isPaused) {
            start();
            isPaused = false;
        }
    }

    private void start() {
        remainingTime = durationInSeconds;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;

                if (remainingTime >= 0) {
                    setChanged();
                    notifyObservers(AnimationMessages.REPAINT_TOPBRAID);
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
                Thread.sleep(durationInSeconds * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            timer.stop();
        });

        timerThread.start();
    }
}


