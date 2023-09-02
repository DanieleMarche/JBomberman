package TopBar;

import Utils.ImageUtils;
import Utils.MethodUtils;
import main.GameView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TopBar {

    public static TopBar instance = null;

    BufferedImage topBar;
    BufferedImage[] numbers;

    BufferedImage currentLifeNumber;

    ArrayList<BufferedImage> score;

    BufferedImage[] timer;

    public static TopBar getInstance() {
        if(instance == null) {
            instance = new TopBar();
        }
        return instance;
    }

    private TopBar() {

        topBar = ImageUtils.loadImage("res/top-bar/topBar.png");
        numbers = ImageUtils.loadPNGsFromDirectory("res/top-bar/top-bar-numbers").toArray(new BufferedImage[0]);
        currentLifeNumber = numbers[0];
        timer = new BufferedImage[] {numbers[0], numbers[0], numbers[0]};
        score = new ArrayList<>();
    }

    public void setScore(int score) {
        this.score = new ArrayList<>();
        Arrays.stream(MethodUtils.extractDigits(score))
                .forEach(digit -> this.score.add(numbers[digit]));
        Collections.reverse(this.score);
    }

    public void setCurrentLifeNumber(int n) {
        this.currentLifeNumber = numbers[n];
    }

    public void setTimer(int remainingSeconds) {
        int minutes = remainingSeconds / 60;
        timer[0] = numbers[minutes];

        int[] seconds = MethodUtils.extractDigits(remainingSeconds % 60);

        try{
            timer[1] = numbers[seconds[0]];
            timer[2] = numbers[seconds[1]];
        } catch (ArrayIndexOutOfBoundsException e) {
            timer[1] = numbers[0];
            timer[2] = numbers[seconds[0]];
        }
    }

    public static void removeInstance() {
        instance = null;
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(topBar, 0, 0, topBar.getWidth() * GameView.tileScale, topBar.getHeight() * GameView.tileScale, null);

        g2.drawImage(timer[0], 176 * GameView.tileScale, 9 * GameView.tileScale, timer[0].getWidth() * GameView.tileScale, timer[0].getHeight() * GameView.tileScale, null);

        g2.drawImage(timer[1], 192 * GameView.tileScale, 9 * GameView.tileScale, timer[0].getWidth() * GameView.tileScale, timer[0].getHeight() * GameView.tileScale, null);

        g2.drawImage(timer[2], 200 * GameView.tileScale, 9 * GameView.tileScale, timer[0].getWidth() * GameView.tileScale, timer[0].getHeight() * GameView.tileScale, null);

        int i = 0;

        for(BufferedImage digit: score){
            g2.drawImage(digit, ((130  - (i * digit.getWidth())) * GameView.tileScale) , 9 * GameView.tileScale, timer[0].getWidth() * GameView.tileScale, timer[0].getHeight() * GameView.tileScale, null);
            i++;
        }


        g2.drawImage(currentLifeNumber, 50 * GameView.tileScale, 9 * GameView.tileScale, currentLifeNumber.getWidth() * GameView.tileScale, currentLifeNumber.getHeight() * GameView.tileScale, null);
    }
}
