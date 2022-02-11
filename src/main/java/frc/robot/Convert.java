package frc.robot;

//easily accessible conversion equations
public class Convert {

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
}
