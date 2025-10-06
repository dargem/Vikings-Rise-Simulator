
package test;
import java.util.ArrayList;
import java.util.List;

import test.combatrecords.CombatRecordOverview;
public class Simulator {
    private List<Combatant> combatantList = new ArrayList<>();
    private List<Combatant> enemyCombatantList = new ArrayList<>();
    private int idCount = 0;

    public void setNewCombatant(double attack, double defense, double health, 
                                int troopCount, boolean isRally,
                                String commander1Name, String commander2Name, 
                                String skill1Name, String skill2Name,
                                String skill3Name, String skill4Name, 
                                String mountFirstSlot1Name, String mountFirstSlot2Name, 
                                String mountSecondSlot1Name, String mountSecondSlot2Name){
        Combatant combatant = new Combatant(attack, defense, health, 
                                            troopCount, isRally, idCount, 
                                            commander1Name, commander2Name, 
                                            skill1Name, skill2Name, 
                                            skill3Name, skill4Name, 
                                            mountFirstSlot1Name, mountFirstSlot2Name, 
                                            mountSecondSlot1Name, mountSecondSlot2Name);
        combatantList.add(combatant);
        idCount++;
    }

    public void setNewEnemyCombatant(double attack, double defense, double health, 
                                     int troopCount, boolean isRally,
                                     String commander1Name, String commander2Name, 
                                     String skill1Name, String skill2Name, 
                                    String skill3Name, String skill4Name, 
                                     String mountFirstSlot1Name, String mountFirstSlot2Name, 
                                     String mountSecondSlot1Name, String mountSecondSlot2Name){
        Combatant combatant = new Combatant(attack, defense, health, 
                                            troopCount, isRally, idCount,
                                            commander1Name, commander2Name, 
                                            skill1Name, skill2Name, 
                                            skill3Name, skill4Name, 
                                            mountFirstSlot1Name, mountFirstSlot2Name, 
                                            mountSecondSlot1Name, mountSecondSlot2Name);
        enemyCombatantList.add(combatant);
        idCount++;
    }

    public void addNewEnemyDummy(double attack, double defense, double health, int troopCount, boolean isRally) {
        enemyCombatantList.add(createDummy(attack,defense,health,troopCount,isRally));
        idCount++;
    }

    public Combatant createDummy(double attack, double defense, double health, int troopCount, boolean isRally) {
        return new Combatant(attack, defense, health, troopCount, isRally, idCount,
                             "N/A", "N/A", 
                             "N/A", "N/A", "N/A", "N/A", 
                             "N/A", "N/A", 
                             "N/A", "N/A");
    }

    public String getAllSkills() {
        String output = "";
        for (Combatant combatant : combatantList){
            output+=combatant.getAllSkills() + " ";
        }
        return output;
    }

    public double getFactorPerSecond(){
        double total = 0;
        for (Combatant combatant : combatantList){
            total+=combatant.getFactorPerSecond();
        }
        return total;
    }

    public void runVerseRound(int rounds){
        setup();
        for (int i = 0; i < rounds; i++){
            roundCombat(combatantList, enemyCombatantList);
            for (Combatant combatant : combatantList) {
                combatant.setTroopCount();
            }
            for (Combatant combatant : enemyCombatantList) {
                combatant.setTroopCount();
            }
        }
    }

    public CombatRecordOverview findTrades(int rounds, boolean printCheck) {
        CombatRecordOverview combatRecordOverview = new CombatRecordOverview();
        if (combatantList.isEmpty() || enemyCombatantList.isEmpty()) {
            throw new IllegalStateException("Combatant lists must be initialized before running fights.");
        }

        setup();
        for (int i = 0; i < rounds; i++) {
            roundCombat(combatantList, enemyCombatantList);
            for (Combatant combatant : combatantList) {
                combatant.setTroopCount();
            }
            for (Combatant combatant : enemyCombatantList) {
                combatant.setTroopCount();
            }
        }

        for (Combatant combatant : combatantList) {
            combatRecordOverview.combineCombatRecordOverview(combatant.getCombatRecordOverview());
        }

        for (Combatant combatant : enemyCombatantList) {
            combatRecordOverview.combineCombatRecordOverview(combatant.getCombatRecordOverview());
        }


        if (printCheck) {
            //System.out.println("Enemies Killed Per Troop Lost Pre Heal: " + combatRecord.getTradesPreHeal());
            //System.out.println("Enemies Killed Per Troop Lost Post Heal: " + combatRecord.getTradesPostHeal());
            //System.out.println("does not consider heavy wounded conversion");
        }
        addCombatRecordOverviewTeams(combatRecordOverview);
        return combatRecordOverview;
    }

    public List<Integer> runFights(int fights) {
        if (combatantList.isEmpty() || enemyCombatantList.isEmpty()) {
            throw new IllegalStateException("Combatant lists must be initialized before running fights.");
        }
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < fights; i++) {
            setup();

            List<Combatant> currentFriendlyCombatants = new ArrayList<>(combatantList);
            List<Combatant> currentEnemyCombatants = new ArrayList<>(enemyCombatantList);

            int val = singleFightRounds(currentFriendlyCombatants, currentEnemyCombatants, i < 5); // Debug first 5 fights
            results.add(val);
        }
        System.out.println("Sample results: " + results.subList(0, Math.min(10, results.size())));
        return results;
    }

    private int singleFightRounds(List<Combatant> currentFriendlyCombatants, List<Combatant> currentEnemyCombatants, boolean debug) {
        int rounds = 0;
        
        // Debug: Print initial troop counts
        if (debug && rounds == 0) {
            System.out.println("Fight start - Friendly troops: " + 
                currentFriendlyCombatants.stream().mapToInt(c -> c.getCombatantInfo().getTroopCount()).sum() +
                ", Enemy troops: " + 
                currentEnemyCombatants.stream().mapToInt(c -> c.getCombatantInfo().getTroopCount()).sum());
        }
        
        while (true) {
            // Perform one round of combat using the current friendly and enemy combatants.
            roundCombat(currentFriendlyCombatants, currentEnemyCombatants);
            rounds++;
            
            // Debug: Print troop counts after combat
            if (debug) {
                int friendlyTotal = currentFriendlyCombatants.stream().mapToInt(c -> c.getCombatantInfo().getTroopCount()).sum();
                int enemyTotal = currentEnemyCombatants.stream().mapToInt(c -> c.getCombatantInfo().getTroopCount()).sum();
                System.out.println("Round " + rounds + " - Friendly: " + friendlyTotal + ", Enemy: " + enemyTotal);
            }
            
            // Remove defeated friendly combatants from the *current* fight's list.
            // Iterate backwards to avoid ConcurrentModificationException when removing elements.
            for (int iter = currentFriendlyCombatants.size() - 1; iter >= 0; iter--) {
                Combatant combatant = currentFriendlyCombatants.get(iter);
                if (combatant.getCombatantInfo().getTroopCount() <= 0) {
                    currentFriendlyCombatants.remove(iter);
                }
            }

            // Remove defeated enemy combatants from the *current* fight's list.
            for (int iter = currentEnemyCombatants.size() - 1; iter >= 0; iter--) {
                Combatant combatant = currentEnemyCombatants.get(iter);
                if (combatant.getCombatantInfo().getTroopCount() <= 0) {
                    currentEnemyCombatants.remove(iter);
                }
            }

            if (currentEnemyCombatants.isEmpty() || currentFriendlyCombatants.isEmpty()) {
                if (debug) {
                    System.out.println("Fight ended after " + rounds + " rounds");
                }
                return rounds; // Return the number of rounds it took
            }
        }
    }

    private int singleFight(List<Combatant> currentFriendlyCombatants, List<Combatant> currentEnemyCombatants) {
        //System.out.println(currentEnemyCombatants.get(0).getCombatantInfo().getTroopCount());
        //System.out.println(currentFriendlyCombatants.get(0).getCombatantInfo().getTroopCount());
        while (true) {
            // Perform one round of combat using the current friendly and enemy combatants.
            roundCombat(currentFriendlyCombatants, currentEnemyCombatants);
            
            // Remove defeated friendly combatants from the *current* fight's list.
            // Iterate backwards to avoid ConcurrentModificationException when removing elements.
            for (int iter = currentFriendlyCombatants.size() - 1; iter >= 0; iter--) {
                Combatant combatant = currentFriendlyCombatants.get(iter);
                if (combatant.getCombatantInfo().getTroopCount() <= 0) {
                    currentFriendlyCombatants.remove(iter);
                }
            }

            // Remove defeated enemy combatants from the *current* fight's list.
            for (int iter = currentEnemyCombatants.size() - 1; iter >= 0; iter--) {
                Combatant combatant = currentEnemyCombatants.get(iter);
                if (combatant.getCombatantInfo().getTroopCount() <= 0) {
                    currentEnemyCombatants.remove(iter);
                }
            }

            if (currentEnemyCombatants.isEmpty() && currentFriendlyCombatants.isEmpty()) { return 0; }
            // both depleted num troops at same time

            if (currentFriendlyCombatants.isEmpty()) {
                int total = 0;
                for (Combatant enemyCombatant : currentEnemyCombatants) {
                    total += enemyCombatant.getCombatantInfo().getTroopCount();
                }
                return total *-1 ;
            } // *-1 for enemy victory

            if (currentEnemyCombatants.isEmpty()) {
                int total = 0;
                for (Combatant friendlyCombatant : currentFriendlyCombatants) {
                    total += friendlyCombatant.getCombatantInfo().getTroopCount();
                }
                return total; // Friendly victory
            }
        }
    }

    private void setup() {
        // Reset friendly combatants
        for (Combatant combatant : combatantList) {
            combatant.reset(); // Calls resetTroops and other state resets
        }

        // Reset enemy combatants
        for (Combatant combatant : enemyCombatantList) {
            combatant.reset(); // Calls resetTroops and other state resets
        }

    }

    // singular round of combat
    private void roundCombat(List<Combatant> friendlyCombatants, List<Combatant> enemyCombatants) {
        // Count basic attackers
        int friendlyBasics = 0;
        int enemyBasics = 0;
        //System.out.println(friendlyCombatants.get(0).getCombatantInfo().getRound());
        for (Combatant combatant : friendlyCombatants) {
            if (combatant.getCombatantInfo().getBasicAttackCheck()) {
                friendlyBasics++;
            }
        }

        for (Combatant combatant : enemyCombatants) {
            if (combatant.getCombatantInfo().getBasicAttackCheck()) {
                enemyBasics++;
            }
        }

        // Set number of attackers on the combatant info of the first combatant in each list.
        if (!friendlyCombatants.isEmpty()) {
            friendlyCombatants.get(0).setNumberEnemyAttackers(enemyBasics);
        }
        if (!enemyCombatants.isEmpty()) {
            enemyCombatants.get(0).setNumberEnemyAttackers(friendlyBasics);
        }
        //System.out.println("Start " + friendlyCombatants.get(0).getCombatantInfo().getTroopCount());
        // Round initialization for all combatants

        for (Combatant combatant : friendlyCombatants) {
            combatant.roundInitialisation();
        }
        for (Combatant combatant : enemyCombatants) {
            combatant.roundInitialisation();
        }

        if (!friendlyCombatants.isEmpty() && !enemyCombatants.isEmpty()) {
            for (Combatant enemyCombatant : enemyCombatants) {
                enemyCombatant.startPhase(friendlyCombatants.get(0).getCombatantInfo());
            }
        }
        //System.out.println("Enemy attacks friendly main " + friendlyCombatants.get(0).getCombatantInfo().getTroopCount());
        if (!friendlyCombatants.isEmpty() && !enemyCombatants.isEmpty()) {
            for (Combatant friendlyCombatant : friendlyCombatants) {
                friendlyCombatant.startPhase(enemyCombatants.get(0).getCombatantInfo());
            }
        }
        //System.out.println("Friendly attacks enemy main " + friendlyCombatants.get(0).getCombatantInfo().getTroopCount());
        // Counterattacks - friendly main counterattacking enemies
        if (!friendlyCombatants.isEmpty()) {
            for (Combatant enemyCombatant : enemyCombatants) {
                friendlyCombatants.get(0).counterattackPhase(enemyCombatant.getCombatantInfo());
            }
        }
        //System.out.println("Friendly main counterattacks enemies " + friendlyCombatants.get(0).getCombatantInfo().getTroopCount());

        // Counterattacks - enemy main counterattacking friendlies
        if (!enemyCombatants.isEmpty()) {
            for (Combatant friendlyCombatant : friendlyCombatants) {
                enemyCombatants.get(0).counterattackPhase(friendlyCombatant.getCombatantInfo());
            }
        }
        //System.out.println("Enemy main counterattacks friendly " + friendlyCombatants.get(0).getCombatantInfo().getTroopCount());

        // End phase for all combatants
        for (Combatant combatant : friendlyCombatants) {
            combatant.endPhase();
        }

        for (Combatant combatant : enemyCombatants) {
            combatant.endPhase();
        }
        //System.out.println("Phase ended " + friendlyCombatants.get(0).getCombatantInfo().getTroopCount());
    }

    public List<CombatRecordOverview> groupRoundSimulator(int totalTradeSamples, int maxRounds) {
        List<CombatRecordOverview> combatRecordOverviewListResults = new ArrayList<>();

        for (int rounds = 1; rounds <= maxRounds; rounds++) {
            CombatRecordOverview combatRecordOverview = new CombatRecordOverview();
            int numSimulations = totalTradeSamples / rounds;
            //System.out.println(rounds);
            for (int i = 0; i < numSimulations; i++) {
                //System.out.println("success");
                CombatRecordOverview holder = findTrades(rounds, false);
                combatRecordOverview.combineCombatRecordOverview(holder);
            }
            addCombatRecordOverviewTeams(combatRecordOverview);
            combatRecordOverviewListResults.add(combatRecordOverview);
        }
        return combatRecordOverviewListResults;
    }

    private void addCombatRecordOverviewTeams(CombatRecordOverview combatRecordOverview) {
        for (Combatant combatant : combatantList) {
            combatRecordOverview.addFriendly(combatant.getCombatantId());
        }
        for (Combatant combatant : enemyCombatantList) {
            combatRecordOverview.addEnemy(combatant.getCombatantId());
        }
    }
}
