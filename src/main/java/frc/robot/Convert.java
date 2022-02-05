package frc.robot;

//easily accessible conversion equations
public class Convert {

    public static final double ANGLE_CONVERT(double deg) { // Sets angle within -360 to +360 degrees
        deg = deg % 360;
        return deg;
    }

    public static double ticksToInches(double ticks) {
        return ticks / (Constants.TICKS_PER_INCH);
    }
}
