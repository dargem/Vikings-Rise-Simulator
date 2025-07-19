package test;

public class CombatantInfoHolder {
    private int troopCount;
    private double attack;
    private double defense;
    private double health;
    private boolean isRally;
    private int combatantId;

    public CombatantInfoHolder(int troopCount, double attack, double defense, double health, boolean isRally, int combatantId) {
        this.troopCount = troopCount;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.isRally = isRally;
        this.combatantId = combatantId;
    }

    public CombatantInfo generateNewCombatantInfo() {
        return new CombatantInfo(troopCount, attack, defense, health, isRally, combatantId);
    }

}
