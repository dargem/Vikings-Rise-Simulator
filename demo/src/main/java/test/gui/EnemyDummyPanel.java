package test.gui;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EnemyDummyPanel extends JPanel implements IMarchPanel {
    private JTextField hpField, atkField, defField, troopField;
    private final JComboBox<String> teamBox;
    private List<IMarchPanel> marchPanels;
    private JPanel parentPanel;

    public EnemyDummyPanel(JPanel parentPanel, List<IMarchPanel> marchPanels) {
        this.parentPanel = parentPanel;
        this.marchPanels = marchPanels;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //test
        JLabel lblAtk = new JLabel("Attack");
        lblAtk.setAlignmentX(CENTER_ALIGNMENT);
        add(lblAtk);
        atkField = new JTextField("830"); //830
        add(atkField);
        
        JLabel lblDef = new JLabel("Defense");
        lblDef.setAlignmentX(CENTER_ALIGNMENT);
        add(lblDef);
        defField = new JTextField("696"); //696
        add(defField);

        JLabel lblHp = new JLabel("Health");
        lblHp.setAlignmentX(CENTER_ALIGNMENT);
        add(lblHp);
        hpField = new JTextField("396"); // 396
        add(hpField);

        JLabel lblTroops = new JLabel("Troops");
        lblTroops.setAlignmentX(CENTER_ALIGNMENT);
        add(lblTroops);
        troopField = new JTextField("300000");
        add(troopField);

        JLabel lblTeam = new JLabel("Team");
        lblTeam.setAlignmentX(CENTER_ALIGNMENT);
        add(lblTeam);
        teamBox = new JComboBox<>(new String[]{"Enemy"});
        teamBox.setEnabled(false);
        add(teamBox);

        JButton removeButton = new JButton("Remove");
        removeButton.setAlignmentX(CENTER_ALIGNMENT);
        removeButton.addActionListener(e -> {
            marchPanels.remove(this);
            parentPanel.remove(this);
            parentPanel.revalidate();
            parentPanel.repaint();
        });
        add(removeButton);

        setBorder(BorderFactory.createTitledBorder("Enemy Dummy"));
    }

    public EnemyDummyPanel(JPanel parent, List<IMarchPanel> list, EnemyDummyPanel original) {
        this(parent, list);
        this.setFrom(original);
    }

    public void setFrom(EnemyDummyPanel other) {
        hpField.setText(String.valueOf(other.getHp()));
        atkField.setText(String.valueOf(other.getAtk()));
        defField.setText(String.valueOf(other.getDef()));
        troopField.setText(String.valueOf(other.getTroopSize()));
    }

    @Override
    public boolean isFriendly() {
        return false;
    }

    @Override
    public boolean isValidMarch() {
        try {
            Integer.parseInt(hpField.getText());
            Integer.parseInt(atkField.getText());
            Integer.parseInt(defField.getText());
            Integer.parseInt(troopField.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int getHp() {
        return Integer.parseInt(hpField.getText());
    }

    @Override
    public int getAtk() {
        return Integer.parseInt(atkField.getText());
    }

    @Override
    public int getDef() {
        return Integer.parseInt(defField.getText());
    }

    @Override
    public int getTroopSize() {
        return Integer.parseInt(troopField.getText());
    }

    @Override
    public String getPrimaryCommander() {
        return "N/A";
    }

    @Override
    public String getSecondaryCommander() {
        return "N/A";
    }

    @Override
    public String getSkill(int i) {
        return "N/A";
    }
    @Override
    public String getTeam() {
        return teamBox.getSelectedItem().toString();
    }
}
