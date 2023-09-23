package Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;


public interface Drawable {

    /**
     * @return The image of the drawable element.
     */
    Optional<BufferedImage> getImage();
    Point getPosition();
}
