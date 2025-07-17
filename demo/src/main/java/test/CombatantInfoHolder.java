package test;

public class CombatantInfoHolder {
    private int troopCount;
    private double attack;
    private double defense;
    private double health;
    private boolean isRally;

    public CombatantInfoHolder(int troopCount, double attack, double defense, double health, boolean isRally) {
        this.troopCount = troopCount;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.isRally = isRally;
    }

    public CombatantInfo generateNewCombatantInfo() {
        return new CombatantInfo(troopCount, attack, defense, health, isRally);
    }

}
