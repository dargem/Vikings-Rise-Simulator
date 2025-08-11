package test;

public class StatusEffect {
    private String name;
    private String type; // e.g. "buff", "debuff", "dot", etc.
    private int duration; // remaining duration in rounds
    public int maxDuration; // the duration if it was just applied
    private double magnitude; // effect strength
    private boolean removable;

    public StatusEffect(String name, String type, int duration, double magnitude, boolean removable) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.magnitude = magnitude;
        this.maxDuration = duration;
        this.removable = removable;
    }

    public boolean getRemovable() {
        return (!(maxDuration == duration) && removable);
    }

    public boolean justAdded() {
        return (maxDuration == duration);
    }

    public boolean getEngrained() {
        return !removable;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
    
    public void addMagnitude(double magnitude) {
        this.magnitude += magnitude;
    } // just so I can reuse this class for absorption

    public void tick() {
        duration--;
    }

    public boolean isExpired() {
        return duration < 0 || magnitude < 0.0001; //decremented immediately on round application
    } // magnitude check is for absorption

    // Getters
    public String getName() { return name; }
    public double getMagnitude() { return magnitude; }
    public String getType() { return type; }
    public int getDuration() { return duration; }
}
