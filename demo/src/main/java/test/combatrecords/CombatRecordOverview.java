package test.combatrecords;
import java.util.HashMap;

public class CombatRecordOverview {
    private HashMap<Integer,CombatRecord> combatRecordsById = new HashMap<>();

    public CombatRecordOverview() {
        // default constructor for now
    }

    public void addFriendly(int combatantId) {
        combatRecordsById.get(combatantId).setFriendly(true);
    }

    public void addEnemy(int combatantId) {
        combatRecordsById.get(combatantId).setFriendly(false);
    }

    public void addEnemyTroopsKilled(int attackerCombatantId, int victimCombatantId, double enemyLost) {
        combatRecordsById.putIfAbsent(attackerCombatantId, new CombatRecord());
        combatRecordsById.get(attackerCombatantId).addEnemyLost(enemyLost);
        combatRecordsById.putIfAbsent(victimCombatantId, new CombatRecord());
        combatRecordsById.get(victimCombatantId).addFriendlyLost(enemyLost);
    }

    public void addTroopsHealed(int combatantId, double troopsHealed) {
        combatRecordsById.get(combatantId).addFriendlyHealed(troopsHealed);
    }

    public void combineCombatRecordOverview(CombatRecordOverview combatRecordOverview) {
        for (HashMap.Entry<Integer, CombatRecord> en : combatRecordOverview.combatRecordsById.entrySet()) {
            Integer combatantId = en.getKey();
            CombatRecord combatRecord = en.getValue();
            combineCombatRecord(combatantId, combatRecord);
        }
    }
    private void combineCombatRecord(int combatantId, CombatRecord combatRecord) {
        combatRecordsById.putIfAbsent(combatantId, new CombatRecord());
        combatRecordsById.get(combatantId).combineCombatRecord(combatRecord);
    } // for merging when doing groups of rounds or multithreading

    public double getTradesPostHeal() {
        double enemyLost = 0;
        double friendlyLost = 0;
        for (CombatRecord combatRecord : combatRecordsById.values()) {
            if (combatRecord.isFriendly()) {
                friendlyLost += combatRecord.getFriendlyLost();
                friendlyLost -= combatRecord.getFriendlyHealed();
            } else { // enemy
                enemyLost += combatRecord.getFriendlyLost();
                enemyLost -= combatRecord.getFriendlyHealed();
            }
        }
        return enemyLost/friendlyLost;
    }
    
    public double getTradesPreHeal() {
        double enemyLost = 0;
        double friendlyLost = 0;
        for (CombatRecord combatRecord : combatRecordsById.values()) {
            if (combatRecord.isFriendly()) {
                friendlyLost += combatRecord.getFriendlyLost();
            } else { // enemy
                enemyLost += combatRecord.getFriendlyLost();
            }
        }
        return enemyLost/friendlyLost;
    }

    public double getEnemyLostPostHeal(int rounds) {
        int totalLost = 0;
        for (CombatRecord combatRecord : combatRecordsById.values()) {
            if (!combatRecord.isFriendly()) {
                totalLost += combatRecord.getFriendlyLost();
                totalLost -= combatRecord.getFriendlyHealed();
            }
        }
        return totalLost/(double)rounds;
    }
}
