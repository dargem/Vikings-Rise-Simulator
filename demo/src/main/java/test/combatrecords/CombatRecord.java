package test.combatrecords;
public class CombatRecord {
    public boolean isFriendly;
    private double enemyLost;
    private double enemyHealed;
    private double friendlyLost;
    private double friendlyHealed;

    public CombatRecord() {
        enemyHealed=0;
        enemyLost=0;
        friendlyHealed=0;
        friendlyLost=0;
    }

    public double getEnemyLost() { return enemyLost; }
    public double getEnemyLostAverage(int rounds) { return enemyLost/(double)rounds;}
    public double getEnemyHealed() { return enemyHealed; }
    public double getFriendlyLost() { return friendlyLost; }
    public double getFriendlyHealed() { return friendlyHealed; }
    public void setFriendly(boolean isFriendly) { this.isFriendly = isFriendly; }
    public boolean isFriendly() { return isFriendly; }

    //public void setEnemyLost(int enemyLost) { this.enemyLost = enemyLost; }
    //public void setFriendlyLost(int friendlyLost) { this.friendlyLost = friendlyLost; }

    public void addEnemyLost(double enemyLost) { this.enemyLost += enemyLost; }
    public void addEnemyHealed(double enemyHealed) { this.enemyHealed += enemyHealed; }
    public void addFriendlyLost(double friendlyLost) { this.friendlyLost += friendlyLost; }
    public void addFriendlyHealed(double friendlyHealed) { this.friendlyHealed += friendlyHealed; }

    public double getTradesPostHeal() { return enemyLost/friendlyLost; }
    public double getTradesPreHeal() { 
        return (enemyLost+enemyHealed)/(friendlyLost+friendlyHealed); 
    }

    public void combineCombatRecord(CombatRecord combatRecord) {
        enemyLost += combatRecord.getEnemyLost();
        enemyHealed += combatRecord.getEnemyHealed();
        friendlyLost += combatRecord.getFriendlyLost();
        friendlyHealed += combatRecord.getFriendlyHealed();
    }
}