package test.gui;

public interface IMarchPanel {
    boolean isFriendly();
    boolean isValidMarch();
    int getHp();
    int getAtk();
    int getDef();
    int getTroopSize();
    String getPrimaryCommander();
    String getSecondaryCommander();
    String getSkill(int i);
    String getTeam();
    Boolean isRally();
}
