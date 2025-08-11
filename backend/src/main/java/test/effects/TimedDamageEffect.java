package test.effects;

public class TimedDamageEffect extends TimedEffect {
    private double magnitude;

    public TimedDamageEffect(String name, String type, int duration, boolean removable, double magnitude) {
        super(name,type,duration,removable);
        this.magnitude = magnitude;
    }
    
}
