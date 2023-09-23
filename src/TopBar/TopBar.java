package TopBar;

import Animation.Drawable;
import Utils.ImageUtils;
import Utils.MethodUtils;
import main.GameView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;


/**
 * This function defines the topbar of the game.
 */
public class TopBar implements Drawable {

    BufferedImage topBar;
    BufferedImage[] numbers;

    BufferedImage currentLifeNumber;

    ArrayList<BufferedImage> score;

    BufferedImage[] timer;

    public TopBar() {
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

    @Override
    public Optional<BufferedImage> getImage() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0,0));
        points.add(new Point(176, 9));
        points.add(new Point(192, 9));
        points.add(new Point(200, 9));
        points.add(new Point(50, 9));

        ArrayList<BufferedImage> topBarImages = new ArrayList<>();
        topBarImages.add(topBar);
        topBarImages.add(timer[0]);
        topBarImages.add(timer[1]);
        topBarImages.add(timer[2]);
        topBarImages.add(currentLifeNumber);

        int i = 0;
        for(BufferedImage digit: score){
            topBarImages.add(digit);
            points.add(new Point(((130  - (i * digit.getWidth()))) , 9));
            i++;
        }

        return Optional.of(ImageUtils.combineImages(topBarImages, points, GameView.screenWidth, GameView.topBarHeight));

    }

    @Override
    public Point getPosition() {
        return new Point(0,0);
    }
}
