package User;

import main.GameLevel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UserModel {

    private static UserModel instance = null;

    private String username;
    private ColoreAvatar avatar;
    private int totalScore;
    private GameLevel livelloRaggiunto;
    private boolean livelloSuperato;
    private int maxScoreLiv1;
    private int maxScoreLiv2;

    private int livelliVinti;
    private int livelliPersi;

    private UserModel(String username, ColoreAvatar avatar, int totalScore, GameLevel livelloRaggiunto,
                      boolean livelloSuperato,
                      int maxScoreLiv1, int maxScoreLiv2, int livelliVinti, int livelliPersi) {
        this.username = username;
        this.avatar = avatar;
        this.totalScore = totalScore;
        this.livelloRaggiunto = livelloRaggiunto;
        this.livelloSuperato = livelloSuperato;
        this.maxScoreLiv1 = maxScoreLiv1;
        this.maxScoreLiv2 = maxScoreLiv2;
        this.livelliVinti = livelliVinti;
        this.livelliPersi = livelliPersi;
    }

    public static UserModel getInstance() {
        if(instance == null) {
            instance = new UserModel(null, null, 0, GameLevel.LEVEL_1, false, 0,0,0,0);
        }
        return instance;
    }

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);


    public boolean isLivelloSuperato() {
        return livelloSuperato;
    }

    public void setLivelloSuperato(boolean livelloSuperato) {
        GameLevel oldLivelloRaggiunto = livelloRaggiunto;
        if(livelloRaggiunto.getLevelCode() < GameLevel.LEVEL_2.getLevelCode() && livelloSuperato) {
            livelloRaggiunto = GameLevel.fromLevelCode(livelloRaggiunto.getLevelCode() + 1);
        }
        propertyChangeSupport.firePropertyChange("livelloRaggiunto", oldLivelloRaggiunto, livelloRaggiunto);


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ColoreAvatar getAvatar() {
        return avatar;
    }

    public void setAvatar(ColoreAvatar avatar) {
        this.avatar = avatar;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public GameLevel getLivelloRaggiunto() {
        return livelloRaggiunto;
    }

    public void setLivelloRaggiunto(GameLevel livelloRaggiunto) {
        this.livelloRaggiunto = livelloRaggiunto;
    }

    public int getMaxScoreLiv1() {
        return maxScoreLiv1;
    }

    public void setMaxScoreLiv1(int maxScoreLiv1) {
        this.maxScoreLiv1 = maxScoreLiv1;
        totalScore = maxScoreLiv1 + maxScoreLiv2;
    }

    public int getMaxScoreLiv2() {
        return maxScoreLiv2;
    }

    public void setMaxScoreLiv2(int maxScoreLiv2) {
        this.maxScoreLiv2 = maxScoreLiv2;
        totalScore = maxScoreLiv1 + maxScoreLiv2;
    }

    public int getLivelliVinti() {
        return livelliVinti;
    }

    public void setLivelliVinti(int livelliVinti) {
        this.livelliVinti = livelliVinti;
    }

    public int getLivelliPersi() {
        return livelliPersi;
    }

    public void setLivelliPersi(int livelliPersi) {
        this.livelliPersi = livelliPersi;
    }

    public void increaseLivelliVinti(){
        int oldLivelliVinti = livelliVinti;
        livelliVinti++;
        propertyChangeSupport.firePropertyChange("livelliVinti", oldLivelliVinti, livelloRaggiunto);
    }

    public void increaseLivelliPersi() {
        int oldLivelliPersi = livelliPersi;
        livelliPersi++;
        propertyChangeSupport.firePropertyChange("livelliPersi", oldLivelliPersi, livelloRaggiunto);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }


    public enum ColoreAvatar{
        red,
        white,
        black,
    }
}
