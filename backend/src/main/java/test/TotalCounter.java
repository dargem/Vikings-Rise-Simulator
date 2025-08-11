package test;

public class TotalCounter {
    private double accumulatedFactorTotal;
    private double damageFactorTotal; //includes damage from everything, inc status and post-equivalence retribution
    private double healFactorTotal;
    private double absorptionFactorTotal;
    private double reductionFactorTotal; //from equivalence using nullification/eva
    private double statusFactorTotal; // sub part of damage factor total, from status damage
    private double counterAttackDamageTotal;
    private int evasionConstant = 3000;
    private int retributionConstant = 3500;
    private int nullificationContant = 3000;
    private int brokenBladeConstant = 1000; // add a change when flanking?

    // issue with counting status effects fix later doesn't effect combat just FPS averages
    
    public TotalCounter() {
        accumulatedFactorTotal=0;
        damageFactorTotal=0;
        healFactorTotal=0;
        absorptionFactorTotal=0;
        reductionFactorTotal=0;
        statusFactorTotal=0;
        counterAttackDamageTotal=0;
        // constants

    }

    public double getAccumulatedFactorTotal(){
        accumulatedFactorTotal = damageFactorTotal + healFactorTotal + absorptionFactorTotal + reductionFactorTotal + counterAttackDamageTotal;
        return accumulatedFactorTotal; // status factor total is included in damage factor total
    }

    public double getDamageFactorTotal(){return damageFactorTotal;}
    public double getHealFactorTotal(){return healFactorTotal;}
    public double getAbsorptionFactorTotal(){return absorptionFactorTotal;}
    public double getReductionFactorTotal(){return reductionFactorTotal;}
    public double getStatusFactorTotal(){return statusFactorTotal;}
    public double getCounterAttackDamageTotal(){return counterAttackDamageTotal;}
    public void addDamageFactor(double val){damageFactorTotal+=val;}
    public void addHealFactor(double val){healFactorTotal+=val;}
    public void addAbsorptionFactor(double val){absorptionFactorTotal+=val;}
    public void addStatusFactor(double val){statusFactorTotal+=val; damageFactorTotal+=val;} // status factor is included in damage factor total
    public void addCounterAttackDamage(double val){counterAttackDamageTotal+=val;}
    public void addNullification(double val){reductionFactorTotal+=val*nullificationContant;}
    public void addEvasion(double val){reductionFactorTotal+=val*evasionConstant;}
    public void addRetribution(double val){damageFactorTotal+=val*retributionConstant;}
    public void addBrokenBlade(double val){reductionFactorTotal+=val*brokenBladeConstant;}

    public void reset() {
        accumulatedFactorTotal=0;
        damageFactorTotal=0;
        healFactorTotal=0;
        absorptionFactorTotal=0;
        reductionFactorTotal=0;
        statusFactorTotal=0;
        counterAttackDamageTotal=0;
    }
}
