package frc.robot;

//easily accessible conversion equations
public class BreakerMath {

    // Drive logistic curve constants

    /** L constant for logistic curve */
    private static double L = 0.9;
    /** k constant for logistic curve */
    private static double k = 7.5;
    /** x0 constant for logistic curve */
    private static double x0 = 0.6;
    /** Vertical translation for logistic curve */
    private static double b = 0.15;

    /**
     * @param deg Angle value, in degrees
     * @return angle value within -360 to +360 degrees.
     */
    public static final double ANGLE_CONVERT(double deg) {
        deg = deg % 360;
        return deg;
    }

    /**
     * @param ticks Talon FX encoder ticks.
     * @return distance, in inches.
     */
    public static double ticksToInches(double ticks) {
        return ticks / (Constants.TICKS_PER_INCH);
    }

    /**
     * Returns y when x is fed into pre-determined logistic curve.
     * 
     * @param x Value between -1 and 1.
     * @return Value between -1 and 1.
     */
    public static double logisticCurve(double x) {
        double absX = Math.abs(x);
        double y = L / (1 + Math.pow(Math.E, -k * (absX - x0))) + b;
        if (x < 0) {
            y *= -1;
        }
        return y;
    }
}
