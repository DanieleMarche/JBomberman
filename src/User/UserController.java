package User;

import Utils.MethodUtils;
import main.GameLevel;
import org.json.JSONObject;
import java.util.Map;

public class UserController {
    private static UserController instance = null;

    private static final String userDataPath = "res/user-data.json";

    public static UserController getInstance() {
        if(instance == null) {
            instance = new UserController();
        }
        return instance;
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
        } else {
            System.out.println("Errore nella lettura del file JSON.");
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


}
