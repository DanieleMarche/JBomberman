package User;

import Utils.MethodUtils;
import main.GameLevel;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

/**
 * This class defines the user of this game and its data such as their name and anvatar.
 */
public class UserModel {

    private static UserModel instance = null;

    private static final String userDataPath = "res/user-data.json";

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
        String oldName = this.username;
        this.username = username;
        propertyChangeSupport.firePropertyChange("username", oldName, this.username);
    }

    public ColoreAvatar getAvatar() {
        return avatar;
    }

    public void setAvatar(ColoreAvatar avatar) {
        ColoreAvatar oldColor = this.avatar;
        this.avatar = avatar;
        propertyChangeSupport.firePropertyChange("avatar", oldColor, avatar);
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        int oldScore = this.totalScore;
        this.totalScore = totalScore;
        propertyChangeSupport.firePropertyChange("totalScore", oldScore, totalScore);
    }

    public GameLevel getLivelloRaggiunto() {
        return livelloRaggiunto;
    }

    public void setLivelloRaggiunto(GameLevel livelloRaggiunto) {
        GameLevel oldLiv = this.livelloRaggiunto;
        this.livelloRaggiunto = livelloRaggiunto;
        propertyChangeSupport.firePropertyChange("livelloRaggiungo", oldLiv, livelloRaggiunto);
    }

    public int getMaxScoreLiv1() {
        return maxScoreLiv1;
    }

    public void setMaxScoreLiv1(int maxScoreLiv1) {
        int oldScore = this.maxScoreLiv1;
        this.maxScoreLiv1 = maxScoreLiv1;
        setTotalScore(maxScoreLiv1 + maxScoreLiv2);;
        propertyChangeSupport.firePropertyChange("maxScoreLiv1", oldScore, maxScoreLiv1);
    }

    public int getMaxScoreLiv2() {
        return maxScoreLiv2;
    }

    public void setMaxScoreLiv2(int maxScoreLiv2) {
        int oldScore = this.maxScoreLiv2;
        this.maxScoreLiv2 = maxScoreLiv2;
        setTotalScore(maxScoreLiv1 + maxScoreLiv2);;
        propertyChangeSupport.firePropertyChange("maxScoreLiv2", oldScore, maxScoreLiv2);
    }

    public int getLivelliVinti() {
        return livelliVinti;
    }

    public void setLivelliVinti(int livelliVinti) {
        int oldLiv = this.livelliVinti;
        this.livelliVinti = livelliVinti;
        propertyChangeSupport.firePropertyChange("livelliVinti", oldLiv, livelliVinti);
    }

    public int getLivelliPersi() {
        return livelliPersi;
    }

    public void setLivelliPersi(int livelliPersi) {
        int oldLiv = this.livelliPersi;
        this.livelliPersi = livelliPersi;
        propertyChangeSupport.firePropertyChange("livelliPersi", oldLiv, livelliPersi);
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

    /**This method takes the username and the avatar color and updates the user class and then saves the updates on
     * the user-data file for future run.
     * @param username The name of the new user.
     * @param coloreAvatar The avatar's color of the new user.
     */
    public void addAndSaveNewUser(String username, UserModel.ColoreAvatar coloreAvatar) {
        UserModel user = new UserModelBuilder()
                .setUsername(username)
                .setAvatar(coloreAvatar)
                .build();

        saveData(user);
    }


    /**
     * This method loads the user data stored inside the json file passed as a parameter and loads the data inside the
     * user insatnce.
     *
     */
    public void loadUserData() {

        Map<String, Object> userDataMap = MethodUtils.readJsonMapFromFile(userDataPath);

        if (userDataMap != null) {
            String username = (String) userDataMap.get("username");
            String avatarString = (String) userDataMap.get("avatar");
            UserModel.ColoreAvatar avatar = UserModel.ColoreAvatar.valueOf(avatarString);
            int totalScore = (int) userDataMap.get("totalScore");
            GameLevel livelloRaggiunto =  GameLevel.fromString((String) userDataMap.get("livelloRaggiunto"));
            int maxScoreLiv1 = (int) userDataMap.get("maxScoreLiv1");
            int maxScoreLiv2 = (int) userDataMap.get("maxScoreLiv2");
            int livelliVinti = (int) userDataMap.get("livelliVinti");
            int livelliPersi = (int) userDataMap.get("livelliPersi");

            UserModel user = new UserModelBuilder()
                    .setUsername(username)
                    .setAvatar(avatar)
                    .setTotalScore(totalScore)
                    .setLivelloRaggiunto(livelloRaggiunto)
                    .setMaxScoreLiv1(maxScoreLiv1)
                    .setMaxScoreLiv2(maxScoreLiv2)
                    .setLivelliVinti(livelliVinti)
                    .setLivelliPersi(livelliPersi)
                    .build();
        }
    }

    public void saveData(UserModel user) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUsername());
        jsonObject.put("avatar", user.getAvatar().toString());
        jsonObject.put("totalScore", user.getTotalScore());
        jsonObject.put("livelloRaggiunto", user.getLivelloRaggiunto());
        jsonObject.put("livelloSuperato", user.isLivelloSuperato());
        jsonObject.put("maxScoreLiv1", user.getMaxScoreLiv1());
        jsonObject.put("maxScoreLiv2", user.getMaxScoreLiv2());
        jsonObject.put("livelliVinti", user.getLivelliVinti());
        jsonObject.put("livelliPersi", user.getLivelliPersi());

        MethodUtils.writeJsonObjectToFile(jsonObject, userDataPath);
    }

    public void removeAllData() {

        JSONObject jsonObject = new JSONObject();

        MethodUtils.writeJsonObjectToFile(jsonObject, userDataPath);
    }


    public enum ColoreAvatar{
        red,
        white,
        black,
    }
}
