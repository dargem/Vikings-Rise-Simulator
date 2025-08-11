package test.effects;

public class Effect {
    // base stuff, attack defense health basic attack etc baked in from base skills etc
    private final String name;
    private final String type;

    public Effect(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String get_name() { return name; }
    public String get_type() { return type; }
}
