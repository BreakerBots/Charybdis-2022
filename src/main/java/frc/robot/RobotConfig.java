package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

/**
 * Planning to use this class to store a list of constants we can use to
 * activate/deactivate any given subsystem.
 */
public class RobotConfig {

    public static final boolean useIMU = true;
    public static final boolean usePDP = true;
    public static final boolean useCompressor = true;

    public static final boolean useDrive = true;
    public static final boolean useIntake = true;
    public static final boolean useHopper = true;
    public static final boolean useShooter = true;
    public static final boolean useClimb = true;

    public static void setBrakeMode(boolean modeArg, BaseMotorController... motors) {
        for (int i = 0; i < motors.length; i++) {
            if (modeArg) {
                motors[i].setNeutralMode(NeutralMode.Brake);
            } else {
                motors[i].setNeutralMode(NeutralMode.Coast);
            }
        }
    }
}
