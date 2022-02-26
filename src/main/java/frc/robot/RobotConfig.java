package frc.robot;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

/**
 * Planning to use this class to store a list of constants we can use to
 * activate/deactivate any given subsystem.
 */
public class RobotConfig {

    private static final boolean useIMU = true;
    private static final boolean usePDP = true;
    private static final boolean useCompressor = true;
    private static final boolean useDrive = true;
    private static final boolean useIntake = true;
    private static final boolean useHopper = true;
    private static final boolean useShooter = true;
    private static final boolean useClimb = true;

    public static HashMap<String, Boolean> getSubsystems() {
        HashMap<String, Boolean> subsystems = new HashMap<String, Boolean>();
        subsystems.put("IMU", useIMU);
        subsystems.put("PDP", usePDP);
        subsystems.put("Compressor", useCompressor);
        subsystems.put("Drive", useDrive);
        subsystems.put("Intake", useIntake);
        subsystems.put("Hopper", useHopper);
        subsystems.put("Shooter", useShooter);
        subsystems.put("Climb", useClimb);
        return subsystems;
    }

    /**
     * Sets brake mode for given CTRE motor controllers.
     * 
     * @param modeArg true for brake mode, false for default/coast mode.
     * @param motors  CTRE motor controllers (Talon FX, Talon SRX, etc.).
     */
    public static void setBrakeMode(boolean modeArg, BaseMotorController... motors) {
        for (BaseMotorController motor : motors) {
            if (modeArg) {
                motor.setNeutralMode(NeutralMode.Brake);
            } else {
                motor.setNeutralMode(NeutralMode.Coast);
            }
        }
    }

}


