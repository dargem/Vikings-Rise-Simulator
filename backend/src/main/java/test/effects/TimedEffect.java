package test.effects;

public class TimedEffect extends Effect {
    private final int max_duration;
    private int duration;
    private final boolean removable;

    public TimedEffect(String name, String type, int duration, boolean removable) {
        super(name,type);
        this.max_duration = duration;
        this.duration = duration;
        this.removable = removable;
    }

    public int getDuration() { return duration; }
    public boolean getRemovable() { return removable; }
    public boolean justAdded() { return duration == max_duration; }

    public void tick() {
        duration--;
    }

    public boolean isExpired() {
        return duration < 0;
    }
}
