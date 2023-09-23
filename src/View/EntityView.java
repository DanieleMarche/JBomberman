package View;

import Animation.Drawable;
import Utils.ImageUtils;
import EntityModelGerarchy.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public abstract class EntityView implements Drawable {

    protected Entity entity;

    protected BufferedImage image;

    public EntityView(Entity entity) {
        this.entity = entity;
    }

    public void setImage(String path) {
        this.image = ImageUtils.loadImage(path);
    }

    @Override
    public Optional<BufferedImage> getImage() {
        return Optional.of(image);
    }

    @Override
    public Point getPosition() {
        return entity.getPosition();
    }
}
