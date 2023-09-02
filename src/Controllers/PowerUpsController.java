package Controllers;

import Animation.AnimationMessages;
import Controllers.ControllersGerarchy.CollectionOfEntitiesController;
import PowerUp.PowerUp;
import entityGerarchy.MovingEntity;
import Bomberman.Player;

import java.util.ArrayList;
import java.util.Observable;

public class PowerUpsController extends CollectionOfEntitiesController<PowerUp> {

    private static PowerUpsController instance = null;

    public static PowerUpsController getInstance() {
        if(instance == null) {
            instance = new PowerUpsController();
        }
        return instance;
    }

    private PowerUpsController() {
        super(AssetManager.getInstance().getPowerUps());

        collisionChecker = (MovingEntity player) -> {

            if(player instanceof  Player) {
                ArrayList<PowerUp> powerUpsCopy = new ArrayList<>(entities);
                powerUpsCopy.forEach(powerUp -> {
                    if(CollisionDetector.checkcollisionBetweenMovingentityAndOtherEntity(player, powerUp) && powerUp.isVisible()) {
                        AudioManager.getInstance().play("res/sounds/Super_Bomberman_Sound_Effects/item-get.wav");
                        powerUp.powerUpActivate((Player) player);
                        update(powerUp, AnimationMessages.REMOVE_ELEMENT);
                    }
                });
            }


        };

    }


    @Override
    public void update(Observable o, Object arg) {
        PowerUp powerUp;
        switch((AnimationMessages) arg) {
            case REPAINT_GAME -> {
                powerUp = (PowerUp) o;
                repaint(powerUp);
            }
            case REMOVE_ELEMENT -> {
                powerUp = (PowerUp) o;
                entities.remove(powerUp);
                repaint(powerUp);
            }
            case GAME_WON, GAME_LOST -> entities.clear();
        }
    }
}
