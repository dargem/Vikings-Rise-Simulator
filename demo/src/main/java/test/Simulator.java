
package test;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

public class Simulator {
    private List<Combatant> combatantList = new ArrayList<>();
    private List<Combatant> enemyCombatantList = new ArrayList<>();
    private int idCount = 0;

    public void setNewCombatant(double attack, double defense, double health, int troopCount, boolean isRally,
                                String commander1Name, String commander2Name, 
                                String skill1Name, String skill2Name, String skill3Name, String skill4Name, 
                                String mountFirstSlot1Name, String mountFirstSlot2Name, 
                                String mountSecondSlot1Name, String mountSecondSlot2Name){
        Combatant combatant = new Combatant(attack, defense, health, troopCount, isRally,
                                            commander1Name, commander2Name, 
                                            skill1Name, skill2Name, skill3Name, skill4Name, 
                                            mountFirstSlot1Name, mountFirstSlot2Name, 
                                            mountSecondSlot1Name, mountSecondSlot2Name);
        combatant.setCombatantId(idCount);
        combatantList.add(combatant);
        idCount++;
    }

    public void setNewEnemyCombatant(double attack, double defense, double health, int troopCount, boolean isRally,
                                     String commander1Name, String commander2Name, 
                                     String skill1Name, String skill2Name, String skill3Name, String skill4Name, 
                                     String mountFirstSlot1Name, String mountFirstSlot2Name, 
                                     String mountSecondSlot1Name, String mountSecondSlot2Name){
        Combatant combatant = new Combatant(attack, defense, health, troopCount, isRally,
                                            commander1Name, commander2Name, 
                                            skill1Name, skill2Name, skill3Name, skill4Name, 
                                            mountFirstSlot1Name, mountFirstSlot2Name, 
                                            mountSecondSlot1Name, mountSecondSlot2Name);
        combatant.setCombatantId(idCount);
        enemyCombatantList.add(combatant);
        idCount++;
    }

    public void addNewEnemyDummy(double attack, double defense, double health, int troopCount, boolean isRally) {
        enemyCombatantList.add(createDummy(attack,defense,health,troopCount,isRally));
    }

    public Combatant createDummy(double attack, double defense, double health, int troopCount, boolean isRally) {
        return new Combatant(attack, defense, health, troopCount, isRally,
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

    public CombatRecord findTrades(int rounds, boolean printCheck) {
        if (combatantList.isEmpty() || enemyCombatantList.isEmpty()) {
            throw new IllegalStateException("Combatant lists must be initialized before running fights.");
        }

        setup();
        CombatRecord combatRecord = new CombatRecord();
        for (int i = 0; i < rounds; i++) {
            roundCombat(combatantList, enemyCombatantList);
            for (Combatant combatant : combatantList) {
                combatRecord.addFriendlyLost(combatant.getInitialTroopCount() - combatant.getCombatantInfo().getTroopCount());
                combatRecord.addFriendlyHealed(combatant.getCombatantInfo().getTroopHealed());
                combatant.getCombatantInfo().setTroopHealed(0);
                combatant.setTroopCount();
            }
            for (Combatant combatant : enemyCombatantList) {
                combatRecord.addEnemyLost(combatant.getInitialTroopCount() - combatant.getCombatantInfo().getTroopCount());
                combatRecord.addEnemyHealed(combatant.getCombatantInfo().getTroopHealed());
                combatant.getCombatantInfo().setTroopHealed(0);
                combatant.setTroopCount();
            }
        }
        if (printCheck) {
            System.out.println("Enemies Killed Per Troop Lost Pre Heal: " + combatRecord.getTradesPreHeal());
            System.out.println("Enemies Killed Per Troop Lost Post Heal: " + combatRecord.getTradesPostHeal());
            System.out.println("does not consider heavy wounded conversion");
        }
        return combatRecord;
    }

    public void runFights(int fights) {
        if (combatantList.isEmpty() || enemyCombatantList.isEmpty()) {
            throw new IllegalStateException("Combatant lists must be initialized before running fights.");
        }
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < fights; i++) {
            setup();

            List<Combatant> currentFriendlyCombatants = new ArrayList<>(combatantList);
            List<Combatant> currentEnemyCombatants = new ArrayList<>(enemyCombatantList);

            int val = singleFight(currentFriendlyCombatants, currentEnemyCombatants);
            results.add(val);
        }
        plotHistogram(results);
    }

    // simulates a random fight and gets you who wins, should return troop size of winner in future
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
                return currentEnemyCombatants.get(0).getCombatantInfo().getTroopCount() *-1 ;
            } // *-1 for enemy victory

            if (currentEnemyCombatants.isEmpty()) {
                return currentFriendlyCombatants.get(0).getCombatantInfo().getTroopCount(); // Friendly victory
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

    /**
     * Runs group round simulations for 1 to maxRounds, each with totalTradeSamples total rounds distributed evenly.
     * Returns a List of CombatRecord, one for each round count.
     */
    public List<CombatRecord> groupRoundSimulator(int totalTradeSamples, int maxRounds) {
        List<CombatRecord> results = new ArrayList<>();
        for (int rounds = 1; rounds <= maxRounds; rounds++) {
            CombatRecord combatRecord = new CombatRecord();
            int numSimulations = totalTradeSamples / rounds;
            System.out.println(rounds);
            for (int i = 0; i < numSimulations; i++) {
                CombatRecord holder = findTrades(rounds, false);
                combatRecord.combineCombatRecord(holder);
            }
            results.add(combatRecord);
        }
        return results;
    }

    public void plotHistogram(List<Integer> values) {
        // Convert List<Integer> to double[]
        double[] data = values.stream().mapToDouble(Integer::doubleValue).toArray();

        // Create dataset
        double binWidth = 5000; // Adjust based on expected troop ranges
        double min = -200000;    // Smallest expected troop count (e.g., enemy win by 1000)
        double max = 200000;     // Largest expected troop count (e.g., friendly win by 1000)
        int binCount = (int)((max - min) / binWidth )+1;

        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("Troops Left", data, binCount, min, max);


        // Create chart
        JFreeChart histogram = ChartFactory.createHistogram(
            "Troop Survival Distribution",
            "Troops Alive After Fight",
            "Frequency",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        // Display chart
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Fight Result Histogram");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ChartPanel(histogram));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
