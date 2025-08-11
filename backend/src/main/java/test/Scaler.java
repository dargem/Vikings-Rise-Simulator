package test;

public class Scaler {
    
    public static double scale(double factor, double attack, double troopCount) {
        return factor * attack * findSizeScalar(troopCount);
    }

    private static double findSizeScalar(double troopCount) {
        // Add on the size scalar constant later, this works for ratios tho
        if (troopCount < 10000) {
            return 2.81*Math.pow(troopCount,0.48)/964.6125*450;
        } else if (troopCount < 100000) {
            return (0.2423 + (troopCount - 10000) * 0.08419321071 / 10000) * 450;
        } else {
            return (1 + (troopCount - 100000) * 0.0230724861 / 100000) * 450;
        }
    }
}
