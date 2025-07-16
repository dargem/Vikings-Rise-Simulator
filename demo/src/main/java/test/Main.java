package test;
// should add something for graphing
//import java.io.File;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //need some kind of ui later, should add validation
        Simulator simulate = new Simulator();

        for (int i=0; i < 1; i++) {

            simulate.setNewCombatant(166*(5), 174*(4), 198*(2), 200000,
            "Gunnar", "Hilda",
            "Disarmament", "Battle Role Shift", "Green Chant",
            "Berserk Killing Machine", "Bloodthirst Gaze", "Strangled Death", 
            "Agonizing Frost", "Divine Awe");
            //(166, 174, 198, 200000,

            simulate.setNewEnemyCombatant(172*(5), 196*(4), 169*(2), 200000,
            "Sephina", "Vali",
            "Disarmament", "Silencer", "Silent Invasion",
            "Einherjar's Oath", "Soul of Fury", "Strangled Death", 
            "Divine Awe", "Divine Awe");
            //(166, 174, 198, 200000,
            //(166, 174, 198, 200000,

            /*
            simulate.setNewEnemyCombatant(166*(4), 174*(3.5+0.2), 198*(2.1), 200000,
            "Sephina", "Vali",
            "First Strike", "Odin's Asylum", "Berserk Killing Machine",
            "Einherjar's Oath", "Bloodthirst Gaze", "Strangled Death", 
            "N/A", "N/A");
            */

        }   
        //(166, 174, 198, 200000,

        //(166, 174, 198, 200000,


        /*
        simulate.setNewCombatant(166*(5), 174*(4), 198*(2), 200000,
        "Gunnar", "Hilda",
        "Disarmament", "Battle Role Shift", "Green Chant",
        "Berserk Killing Machine", "Soul of Fury", "Strangled Death", 
        "Divine Awe", "Divine Awe");
        //(166, 174, 198, 200000,

        simulate.setNewEnemyCombatant(172*(5), 196*(4), 169*(2), 200000,
        "Sephina", "Vali",
        "Disarmament", "Silencer", "Silent Invasion",
        "Einherjar's Oath", "Soul of Fury", "Strangled Death", 
        "Agonizing Frost", "Divine Awe");
        
        simulate.setNewEnemyCombatant(172*(4.6), 196*(4), 169*(2), 200000,
        "Alf", "N/A",
        "N/A", "N/A", "N/A",
        "N/A", "N/A", "Strangled Death", 
        "N/A", "N/A");


        simulate.setNewEnemyCombatant(172*(5), 196*(4), 169*(2), 200000,
        "Heidrun", "Charlton",
        "Devastating Charge", "Divine Blessing", "First Strike",
        "Odin's Asylum", "Pain N Fury", "Bloodthirst Gaze", 
        "N/A", "N/A");

        "Sephina", "Vali",
        "First Strike", "Odin's Asylum", "Berserk Killing Machine",
        "Rage Leech", "Bloodthirst Gaze", "Strangled Death", 
        "N/A", "N/A");

        simulate.setNewCombatant(166*(5), 174*(4), 198*(2), 100000,
        "Sephina", "Vali",
        "Disarmament", "Silencer", "Silent Invasion",
        "Einherjar's Oath", "Soul of Fury", "Strangled Death", 
        "Divine Awe", "Divine Awe");
        //(166, 174, 198, 200000,

        simulate.setNewEnemyCombatant(166*(5), 174*(4), 198*(2), 100000,
        "Sephina", "Vali",
        "Disarmament", "Silencer", "Silent Invasion",
        "Einherjar's Oath", "Bloodthirst Gaze", "Strangled Death", 
        "Divine Awe", "Icicle Armor");

        simulate.setNewCombatant(172*(4.6), 196*(4), 169*(2), 100000,
        "Gunnar", "Hilda",
        "Disarmament", "Silencer", "Silent Invasion",
        "Einherjar's Oath", "Soul of Fury", "Strangled Death", 
        "Divine Awe", "Divine Awe");
        //(166, 174, 198, 200000,


        simulate.setNewEnemyCombatant(166*(5), 174*(4), 198*(2), 100000,
        "Sephina", "Vali",
        "Disarmament", "Silencer", "Silent Invasion",
        "Einherjar's Oath", "Bloodthirst Gaze", "Strangled Death", 
        "Divine Awe", "Icicle Armor");

        simulate.setNewEnemyCombatant(196*(5), 205*(4), 231*(2), 200000,
        "Sephina", "Vali",
        "Silencer", "Silent Invasion", "Einherjar's Oath",
        "Disarmament", "Bloodthirst Gaze", "Strangled Death", 
        "Agonizing Frost", "Fatal Chomp");

        simulate.setNewEnemyCombatant(196*(5), 205*(4), 231*(2), 100000,
        "Vali", "Sephina",
        "Silencer", "Silent Invasion", "Einherjar's Oath",
        "Disarmament", "Bloodthirst Gaze", "Stinging Tongue", 
        "Divine Awe", "Divine Awe");

        simulate.setNewCombatant(196*(5), 205*(4), 231*(2), 200000,
        "Alf", "Olena", 
        "Einherjar's Oath", "Silent Invasion", "Berserk Killing Machine",
        "Disarmament", "Bloodthirst Gaze", "N/A", 
        "N/A", "N/A");  

        simulate.setNewEnemyCombatant(196*(5), 205*(4), 231*(2), 200000,
        "Vali", "Sephina",
        "Einherjar's Oath", "Silent Invasion", "Berserk Killing Machine",
        "Enrage", "Bloodthirst Gaze", "N/A", 
        "N/A", "N/A"); 
        */
        simulate.findTrades(300000, true);
        //groupRoundSimulator(simulate);
        //simulate.findTrades(500000,false);
        //simulate.runFights(100000);
        //System.out.println("done");
        //simulate.runVerseRound(100000);
        //simulate.getFactorPerSecond(); // fix fps counter for status effects
        /*
        simulate.setNewEnemyCombatant(300, 200, 100, 100000,
        "Sephina", "N/A",
        "First Strike", "Poison Arrow", "N/A",
        "N/A", "N/A", "N/A", 
        "N/A", "N/A");
        */
        
        
        
        int round = 5;
        double total = 0;
        int repeats = 1500000;
        //simulate.runRound(1);
        //System.out.println(simulate.getFactorPerSecond());
        //groupRoundSimulator(30,simulate);
        //simulate.runVerseRound(1000000);
        //System.out.println(simulate.getFactorPerSecond());

        //System.out.println(simulate.getAllSkills());
    }

    public static void groupRoundSimulator(Simulator simulate) {
        int totalTradeSamples = 100000;
        int maxRounds = 30;

        for (int rounds = 1; rounds <= maxRounds; rounds++) {
            CombatRecord combatRecord = new CombatRecord();
            int numSimulations = totalTradeSamples / rounds;

            for (int i = 0; i < numSimulations; i++) {
                CombatRecord holder = simulate.findTrades(rounds, false);
                combatRecord.combineCombatRecord(holder);
            }
            System.out.println(combatRecord.getTradesPreHeal());

            //System.out.printf(
            //    "Rounds: %2d | Trades Pre-Heal: %.3f | Trades Post-Heal: %.3f%n",
            //    rounds,
            //    combatRecord.getTradesPreHeal(),
            //    combatRecord.getTradesPostHeal()
            //);
        }
    }

}
