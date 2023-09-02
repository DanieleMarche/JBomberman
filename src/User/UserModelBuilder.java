package User;

import main.GameLevel;

public class UserModelBuilder {

    private String username = null;
    private UserModel.ColoreAvatar avatar = null;
    private int totalScore = 0;
    private GameLevel livelloRaggiunto = GameLevel.LEVEL_1;
    private boolean livelloSuperato = false;
    private int maxScoreLiv1 = 0;
    private int maxScoreLiv2 = 0;
    private int livelliVinti = 0;
    private int livelliPersi = 0;

    public UserModelBuilder setLivelloSuperato(boolean livelloSuperato) {
        this.livelloSuperato = livelloSuperato;
        return this;
    }

    public UserModelBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserModelBuilder setAvatar(UserModel.ColoreAvatar avatar) {
        this.avatar = avatar;
        return this;
    }

    public UserModelBuilder setTotalScore(int totalScore) {
        this.totalScore = totalScore;
        return this;
    }

    public UserModelBuilder setLivelloRaggiunto(GameLevel livelloRaggiunto) {
        this.livelloRaggiunto = livelloRaggiunto;
        return this;
    }

    public UserModelBuilder setMaxScoreLiv1(int maxScoreLiv1) {
        this.maxScoreLiv1 = maxScoreLiv1;
        return this;
    }

    public UserModelBuilder setMaxScoreLiv2(int maxScoreLiv2) {
        this.maxScoreLiv2 = maxScoreLiv2;
        return this;
    }

    public UserModelBuilder setLivelliVinti(int livelliVinti) {
        this.livelliVinti = livelliVinti;
        return this;
    }

    public UserModelBuilder setLivelliPersi(int livelliPersi) {
        this.livelliPersi = livelliPersi;
        return this;
    }

    public UserModel build() {
        UserModel user = UserModel.getInstance();
        user.setUsername(username);
        user.setAvatar(avatar);
        user.setTotalScore(totalScore);
        user.setLivelloRaggiunto(livelloRaggiunto);
        user.setLivelloSuperato(livelloSuperato);
        user.setMaxScoreLiv1(maxScoreLiv1);
        user.setMaxScoreLiv2(maxScoreLiv2);
        user.setLivelliVinti(livelliVinti);
        user.setLivelliPersi(livelliPersi);
        return user;
    }
}
