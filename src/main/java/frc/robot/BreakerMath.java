package frc.robot;

import edu.wpi.first.math.MathUtil;

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
    public static final double constrainAngle(double deg) {
        return deg % 360;
    }

    /**
     * @param ticks Talon FX encoder ticks.
     * @return distance, in inches.
     */
    public static double ticksToInches(double ticks) {
        return ticks / (Constants.DRIVE_TICKS_PER_INCH);
    }

    /**
     * Returns y when x is fed into pre-determined logistic curve.
     * 
     * @param x Value between -1 and 1.
     * @return Value between -1 and 1.
     */
    public static double driveCurve(double x) {
        double absX = MathUtil.applyDeadband(Math.abs(x), 0.05);
        double y = (Math.signum(x) * L) / (1 + Math.pow(Math.E, -k * (absX - x0))) + b;
        return y;
    }

    public static double rollingAvg(double avg, double newVal) {
        return (avg + newVal) / 2.0;
    }

    public static double getAvg(double lastAvg, double newVal, int cycleCount) {
        return (((lastAvg * (cycleCount - 1)) + newVal) / cycleCount);
    }

    public static double voltsToSpdPer(double volts) {
        return volts * Constants.MAX_SPD_VOLTS_FX;
    }

}
