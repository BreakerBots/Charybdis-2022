package frc.robot;

import edu.wpi.first.math.MathUtil;

//easily accessible conversion equations
public class Convert {
    public static final double IN_TO_FT(double in) {
        return in / 12.0;
    }

    public static final double FT_TO_IN(double ft) {
        return 12.0 * ft;
    }

    public static final double TICK_TO_IN(double ticks) {
        return ticks / (Constants.TICKS_PER_INCH);
    }

    public static final double IN_TO_M(double in) {
        return in / Constants.IN_PER_M;
    }

    public static final double M_TO_IN(double m) {
        return m * Constants.IN_PER_M;
    }

    public static final double IN_TO_TICK(double in) {
        return in * (Constants.TICKS_PER_INCH);
    }

    public static final double DEG_TO_RAD(double deg) {
        return deg * Constants.RADIANS_PER_DEGREE;
    }

    public static final double RAD_TO_DEG(double rad) {
        return rad / Constants.RADIANS_PER_DEGREE;
    }

    public static final double CYCLES_TO_MS(int cycles) {
        return cycles * Constants.MS_PER_CYCLE;
    }

    public static final double MS_TO_SEC(double ms) {
        return 0.001 * ms;
    }

    public static final double CYCLES_TO_SEC(int cycles) {
        return 0.001 * (cycles * 20);
    }

    public static final double ANGLE_CONVERT(double deg) { // Sets angle within -180 to +180 degrees
        deg = deg % 360;
        return deg;
    }

}
