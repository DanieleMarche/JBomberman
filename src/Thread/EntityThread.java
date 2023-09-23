package Thread;

import main.Pausable;

public abstract class EntityThread implements Runnable, Pausable {

    protected Thread thread;

    protected int speed;

    public EntityThread() {
        thread = new Thread(this);
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / speed;
        double nextDrawTime = System.nanoTime() + drawInterval;
        try {

            while (thread != null) {

                perform();

                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            }

        } catch (InterruptedException ignored) {

        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    abstract void perform();

    public void stopThread() {
        thread.interrupt();
    }

    public void startThread() {
        thread.start();
    }

    @Override
    public void pause() {
        thread.interrupt();
    }

    @Override
    public void resume() {
        thread = new Thread(this);
        thread.start();
    }
}
