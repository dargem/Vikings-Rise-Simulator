package test.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import test.combatrecords.CombatRecordOverview;


public class SimulatorGUI extends JFrame {
    private JTextArea resultArea;
    private test.Simulator simulator;
    private List<IMarchPanel> marchPanels = new java.util.ArrayList<>();

    public SimulatorGUI() {
        setTitle("Vikings Rise Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        simulator = new test.Simulator();

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new java.awt.Color(40, 53, 147));
        JLabel titleLabel = new JLabel("Vikings Rise Simulator");
        titleLabel.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 32));
        titleLabel.setForeground(java.awt.Color.WHITE);
        headerPanel.add(titleLabel);

        // Tabbed pane for simulation modes
        JTabbedPane tabbedPane = new JTabbedPane();

        // Trades Tab
        JPanel tradesPanel = new JPanel(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        JLabel tradesRoundsLabel = new JLabel("Rounds:");
        JTextField tradesRoundsField = new JTextField("1000000", 10);
        JButton tradesRunButton = new JButton("Run Trades");
        tradesRunButton.setToolTipText("Run Trades Simulation");
        gbc.gridx = 0; gbc.gridy = 0; tradesPanel.add(tradesRoundsLabel, gbc);
        gbc.gridx = 1; tradesPanel.add(tradesRoundsField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; tradesPanel.add(tradesRunButton, gbc);

        // Plot Fights Tab
        JPanel plotPanel = new JPanel(new java.awt.GridBagLayout());
        JLabel plotFightsLabel = new JLabel("Fights:");
        JTextField plotFightsField = new JTextField("30000", 10);
        JButton plotRunButton = new JButton("Run Plot Fights");
        plotRunButton.setToolTipText("Run Plot Fights Simulation");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; plotPanel.add(plotFightsLabel, gbc);
        gbc.gridx = 1; plotPanel.add(plotFightsField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; plotPanel.add(plotRunButton, gbc);

        // Group Round Sim Tab
        JPanel groupPanel = new JPanel(new java.awt.GridBagLayout());
        JLabel groupLengthLabel = new JLabel("Fight Length (intervals for 1-n rounds ran):");
        JTextField groupLengthField = new JTextField("20", 10);
        JLabel groupNumLabel = new JLabel("Rounds per interval:");
        JTextField groupNumField = new JTextField("100000", 10);
        JButton groupRunButton = new JButton("Run Group Round Sim");
        groupRunButton.setToolTipText("Run Group Round Simulation");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; groupPanel.add(groupLengthLabel, gbc);
        gbc.gridx = 1; groupPanel.add(groupLengthField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; groupPanel.add(groupNumLabel, gbc);
        gbc.gridx = 1; groupPanel.add(groupNumField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; groupPanel.add(groupRunButton, gbc);

        tabbedPane.addTab("Trades", tradesPanel);
        tabbedPane.addTab("Plot Fights", plotPanel);
        tabbedPane.addTab("Group Round Sim", groupPanel);

        // March List Panel (styled)
        JPanel marchListPanel = new JPanel();
        marchListPanel.setLayout(new BoxLayout(marchListPanel, BoxLayout.X_AXIS));
        //marchListPanel.setLayout(new BoxLayout(marchListPanel, BoxLayout.Y_AXIS));

        marchListPanel.setBackground(new java.awt.Color(232, 234, 246));
        JScrollPane marchScrollPane = new JScrollPane(marchListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        marchScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Marches"));

        JButton addMarchButton = new JButton("+ Add March");
        addMarchButton.setToolTipText("Add a new march");
        addMarchButton.setBackground(new java.awt.Color(76, 175, 80)); // Green
        addMarchButton.setForeground(java.awt.Color.WHITE);
        addMarchButton.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        addMarchButton.setFocusPainted(false);
        addMarchButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15));
        addMarchButton.setOpaque(true);
        addMarchButton.setContentAreaFilled(true);
        addMarchButton.addActionListener(e -> {
            MarchPanel mp = new MarchPanel(marchListPanel, marchPanels);
            marchPanels.add(mp);
            marchListPanel.add(mp);
            marchListPanel.revalidate();
            marchListPanel.repaint();
        });

        JButton addEnemyDummyButton = new JButton("+ Add Enemy Dummy");
        addEnemyDummyButton.setToolTipText("Add a basic enemy dummy");
        addEnemyDummyButton.setBackground(new java.awt.Color(244, 67, 54)); // Red
        addEnemyDummyButton.setForeground(java.awt.Color.WHITE);
        addEnemyDummyButton.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        addEnemyDummyButton.setFocusPainted(false);
        addEnemyDummyButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15));
        addEnemyDummyButton.setOpaque(true);
        addEnemyDummyButton.setContentAreaFilled(true);
        addEnemyDummyButton.addActionListener(e -> {
            EnemyDummyPanel dummy = new EnemyDummyPanel(marchListPanel, marchPanels);
            marchPanels.add(dummy);
            marchListPanel.add(dummy);
            marchListPanel.revalidate();
            marchListPanel.repaint();
        });

        JButton cloneMarchButton = new JButton("+ Clone March");
        cloneMarchButton.setToolTipText("Clone an existing march");
        cloneMarchButton.setBackground(new java.awt.Color(33, 150, 243)); // Blue
        cloneMarchButton.setForeground(java.awt.Color.WHITE);
        cloneMarchButton.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        cloneMarchButton.setFocusPainted(false);
        cloneMarchButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15));
        cloneMarchButton.setOpaque(true);
        cloneMarchButton.setContentAreaFilled(true);
        cloneMarchButton.addActionListener(e -> {
            if (marchPanels.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No marches to clone.", "Clone March", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Create choices for selection dialog
            String[] options = new String[marchPanels.size()];
            for (int i = 0; i < marchPanels.size(); i++) {
                options[i] = "March " + (i + 1) + (marchPanels.get(i).isFriendly() ? " (Friendly)" : " (Enemy)");
            }
            JTextField numberClones = new JTextField(); 
            numberClones.setText("1"); // defaults to 1
            Object[] message = {
                "Number of clones:", numberClones
            };
            String selected = (String) JOptionPane.showInputDialog(this,
                message,
                "Clone March",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                
                options[0]);

            if (selected != null) {
                int selectedIndex = java.util.Arrays.asList(options).indexOf(selected);
                IMarchPanel original = marchPanels.get(selectedIndex);
                IMarchPanel clone;
                int numberOfLoops = Integer.parseInt(numberClones.getText());
                for (int i = 0; i < numberOfLoops; i++) {
                    if (original instanceof MarchPanel) {
                        clone = new MarchPanel(marchListPanel, marchPanels, (MarchPanel) original);
                    } else if (original instanceof EnemyDummyPanel) {
                        clone = new EnemyDummyPanel(marchListPanel, marchPanels, (EnemyDummyPanel) original);
                    } else {
                        JOptionPane.showMessageDialog(this, "Unknown march type. Cannot clone.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    marchPanels.add(clone);
                    marchListPanel.add((JPanel) clone);
                    marchListPanel.revalidate();
                    marchListPanel.repaint();
                }
            }
        });

        // Add one march by default
        MarchPanel mp = new MarchPanel(marchListPanel, marchPanels);
        marchPanels.add(mp);
        marchListPanel.add(mp);

        JPanel marchPanelContainer = new JPanel(new BorderLayout());
        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));
        buttonRow.setBackground(new java.awt.Color(232, 234, 246));
        buttonRow.add(addMarchButton);
        buttonRow.add(addEnemyDummyButton);
        buttonRow.add(cloneMarchButton);
        marchPanelContainer.add(buttonRow, BorderLayout.NORTH);
        marchPanelContainer.add(marchScrollPane, BorderLayout.CENTER);

        // Results area
        resultArea = new JTextArea(12, 70);
        resultArea.setEditable(false);
        resultArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 16));
        resultArea.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createTitledBorder("Results"),
            javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        // Layout main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.WEST);
        mainPanel.add(marchPanelContainer, BorderLayout.CENTER);
        mainPanel.add(resultScrollPane, BorderLayout.SOUTH);
        setContentPane(mainPanel);

        // Button actions
        tradesRunButton.addActionListener(e -> {
            int rounds = 1000000;
            try { rounds = Integer.parseInt(tradesRoundsField.getText().trim()); } catch (Exception ignored) {}
            runSimulationThreaded(rounds);
        });
        plotRunButton.addActionListener(e -> {
            int fights = 30000;
            try { fights = Integer.parseInt(plotFightsField.getText().trim()); } catch (Exception ignored) {}
            runPlotFightsThreaded(fights);
        });
        groupRunButton.addActionListener(e -> {
            int fightLength = 20;
            int fightsPerLength = 100000;
            try { fightLength = Integer.parseInt(groupLengthField.getText().trim()); } catch (Exception ignored) {}
            try { fightsPerLength = Integer.parseInt(groupNumField.getText().trim()); } catch (Exception ignored) {}
            runGroupRoundSimThreaded(fightsPerLength, fightLength);
        });
    }

    // Run group round simulator in a background thread
    private void runGroupRoundSimThreaded(int totalTradeSamples, int maxRounds) {
        resultArea.setText("Running group round simulation, please wait...");
        new Thread(() -> {
            try {
                simulator = new test.Simulator();
                for (IMarchPanel mp : marchPanels) {
                    if (!mp.isValidMarch()) continue;
                    addMarchesToSimulator(mp);
                }
                List<CombatRecordOverview> results = simulator.groupRoundSimulator(totalTradeSamples, maxRounds);
                StringBuilder sb = new StringBuilder();
                sb.append("Group Round Simulation Results (Rounds: TradesPreHeal, TradesPostHeal):\n");
                for (int i = 0; i < results.size(); i++) {
                    CombatRecordOverview rec = results.get(i);
                    sb.append(String.format("%2d: %.3f, %.3f\n", (i+1), rec.getTradesPreHeal(), rec.getTradesPostHeal()));
                }
                SwingUtilities.invokeLater(() -> resultArea.setText(sb.toString()));
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> resultArea.setText("Error: " + ex.getMessage()));
            }
        }).start();
    }

    private void runSimulationThreaded(int rounds) {
        resultArea.setText("Running simulation, please wait...");
        new Thread(() -> {
            try {
                simulator = new test.Simulator();
                for (IMarchPanel mp : marchPanels) {
                    if (!mp.isValidMarch()) continue;
                    addMarchesToSimulator(mp);
                }
                CombatRecordOverview record = simulator.findTrades(rounds, false);
                String resultText = ("Simulation complete! \n" +
                    "Trades Pre-Heal: " + record.getTradesPreHeal() + "\n" +
                    "Trades Post-Heal: " + record.getTradesPostHeal() + "\n" + 
                    "Enemy Lost Per Round Pre Heal: " + record.getEnemyLostPreHeal(rounds) + "\n\n" +
                    "Per Combatant Rundown: " + "\n" + 
                    record.getStringCombatRecords(rounds)
                    );
                
                SwingUtilities.invokeLater(() -> resultArea.setText(resultText));
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> resultArea.setText("Error: " + ex.getMessage()));
            }
        }).start();
    }

    // Run histogram plotting in a background thread
    private void runPlotFightsThreaded(int fights) {
        resultArea.setText("Running fight simulations and plotting, please wait...");
        new Thread(() -> {
            try {
                simulator = new test.Simulator();
                for (IMarchPanel mp : marchPanels) {
                    if (!mp.isValidMarch()) continue;
                    addMarchesToSimulator(mp);
                }
                simulator.runFights(fights);
                SwingUtilities.invokeLater(() -> resultArea.setText("Fight histogram plotted (see new window)."));
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> resultArea.setText("Error: " + ex.getMessage()));
            }
        }).start();
    }

    private void addMarchesToSimulator(IMarchPanel mp) {
        if (mp.isFriendly()) {
            simulator.setNewCombatant(mp.getAtk(), mp.getDef(), mp.getHp(), mp.getTroopSize(), mp.isRally(),
                                      mp.getPrimaryCommander(), mp.getSecondaryCommander(),
                                      mp.getSkill(0), mp.getSkill(1), mp.getSkill(2), mp.getSkill(3),
                                      mp.getSkill(4), mp.getSkill(5), mp.getSkill(6), mp.getSkill(7));
        } else {
            simulator.setNewEnemyCombatant(mp.getAtk(), mp.getDef(), mp.getHp(), mp.getTroopSize(), mp.isRally(),
                                      mp.getPrimaryCommander(), mp.getSecondaryCommander(),
                                      mp.getSkill(0), mp.getSkill(1), mp.getSkill(2), mp.getSkill(3),
                                      mp.getSkill(4), mp.getSkill(5), mp.getSkill(6), mp.getSkill(7));  
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimulatorGUI().setVisible(true);
        });
    }
}
