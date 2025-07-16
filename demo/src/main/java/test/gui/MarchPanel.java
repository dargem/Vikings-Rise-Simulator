package test.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MarchPanel extends JPanel implements IMarchPanel {
    private JComboBox<String> primaryCommander, secondaryCommander;
    private JTextField hpField, atkField, defField, troopField;
    private JComboBox<String>[] skills = new JComboBox[8];
    private JComboBox<String> teamBox;
    private List<IMarchPanel> marchPanels;
    private JPanel parentPanel;

    public MarchPanel(JPanel parentPanel, List<IMarchPanel> marchPanels) {
        this.parentPanel = parentPanel;
        this.marchPanels = marchPanels;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Commander dropdowns
        List<String> commanderNames = new ArrayList<>();
        commanderNames.add("N/A");
        try {
            for (String s : test.SkillDatabase.commanderNamesSet) commanderNames.add(s);
        } catch (Exception e) {
            commanderNames.add("Error loading commanders");
        }
        JLabel lblPrimary = new JLabel("Primary Commander");
        lblPrimary.setAlignmentX(CENTER_ALIGNMENT);
        add(lblPrimary);
        primaryCommander = new JComboBox<>(commanderNames.toArray(new String[0]));
        add(primaryCommander);
        JLabel lblSecondary = new JLabel("Secondary Commander");
        lblSecondary.setAlignmentX(CENTER_ALIGNMENT);
        add(lblSecondary);
        secondaryCommander = new JComboBox<>(commanderNames.toArray(new String[0]));
        add(secondaryCommander);
        // Stats
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
        // Skill dropdowns
        List<String> slot1Skills = new ArrayList<>();
        List<String> slot2Skills = new ArrayList<>();
        List<String> mountSkills = new ArrayList<>();
        slot1Skills.add("N/A");
        slot2Skills.add("N/A");
        mountSkills.add("N/A");
        try {
            for (String s : test.SkillDatabase.skillNamesSet) slot1Skills.add(s);
            for (String s : test.SkillDatabase.firstSlotMountSkillNamesSet) mountSkills.add(s);
            for (String s : test.SkillDatabase.secondSlotMountSkillNamesSet) slot2Skills.add(s);
        } catch (Exception e) {
            slot1Skills.add("Error loading skills");
            slot2Skills.add("Error loading skills");
            mountSkills.add("Error loading skills");
        }
        JLabel lblSkills = new JLabel("Skills");
        lblSkills.setAlignmentX(CENTER_ALIGNMENT);
        add(lblSkills);
        for (int i = 0; i < 4; i++) {
            skills[i] = new JComboBox<>(slot1Skills.toArray(new String[0]));
            add(skills[i]);
        }
        JLabel lblMount1 = new JLabel("Slot 1 Mount Skills");
        lblMount1.setAlignmentX(CENTER_ALIGNMENT);
        add(lblMount1);
        for (int i = 4; i < 6; i++) {
            skills[i] = new JComboBox<>(mountSkills.toArray(new String[0]));
            add(skills[i]);
        }
        JLabel lblMount2 = new JLabel("Slot 2 Mount Skills");
        lblMount2.setAlignmentX(CENTER_ALIGNMENT);
        add(lblMount2);
        for (int i = 6; i < 8; i++) {
            skills[i] = new JComboBox<>(slot2Skills.toArray(new String[0]));
            add(skills[i]);
        }
        JLabel lblTeam = new JLabel("Team");
        lblTeam.setAlignmentX(CENTER_ALIGNMENT);
        add(lblTeam);
        teamBox = new JComboBox<>(new String[]{"Friendly", "Enemy"});
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
        setBorder(BorderFactory.createTitledBorder("March"));
    }

    public MarchPanel(JPanel parent, List<IMarchPanel> list, MarchPanel original) {
        this(parent, list);
        this.setFrom(original);
    }
    
    public void setFrom(MarchPanel other) {
        hpField.setText(String.valueOf(other.getHp()));
        atkField.setText(String.valueOf(other.getAtk()));
        defField.setText(String.valueOf(other.getDef()));
        troopField.setText(String.valueOf(other.getTroopSize()));
        primaryCommander.setSelectedItem(other.getPrimaryCommander());
        secondaryCommander.setSelectedItem(other.getSecondaryCommander());
        for (int i = 0; i < 8; i++) {
            skills[i].setSelectedItem(other.getSkill(i));
        }
        teamBox.setSelectedItem(other.getTeam());
    }

    @Override
    public boolean isFriendly() { return teamBox.getSelectedItem().equals("Friendly"); }
    @Override
    public boolean isValidMarch() {
        try {
            Integer.parseInt(hpField.getText());
            Integer.parseInt(atkField.getText());
            Integer.parseInt(defField.getText());
            Integer.parseInt(troopField.getText());
            return true;
        } catch (Exception e) { return false; }
    }
    @Override
    public int getHp() { return Integer.parseInt(hpField.getText()); }
    @Override
    public int getAtk() { return Integer.parseInt(atkField.getText()); }
    @Override
    public int getDef() { return Integer.parseInt(defField.getText()); }
    @Override
    public int getTroopSize() { return Integer.parseInt(troopField.getText()); }
    @Override
    public String getPrimaryCommander() { return (String) primaryCommander.getSelectedItem(); }
    @Override
    public String getSecondaryCommander() { return (String) secondaryCommander.getSelectedItem(); }
    @Override
    public String getTeam() { return (String) teamBox.getSelectedItem(); }
    @Override
    public String getSkill(int i) { return (String) skills[i].getSelectedItem(); }
}
