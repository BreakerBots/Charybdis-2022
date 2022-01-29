package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake extends SubsystemBase {
    private WPI_TalonSRX armMotor;
    private WPI_TalonSRX indexerL;
    private WPI_TalonSRX indexerR;
    private static DoubleSolenoid pistonL;
    private static DoubleSolenoid pistonR;

    public Intake() {
        armMotor = new WPI_TalonSRX(Constants.ARM_MOTOR_ID);
        indexerL = new WPI_TalonSRX(Constants.L_INDEX_ID);
        indexerR = new WPI_TalonSRX(Constants.R_INDEX_ID);

        pistonL = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
        Constants.L_INTAKE_FWD, Constants.L_INTAKE_REV);
        pistonR = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
        Constants.R_INTAKE_FWD, Constants.R_INTAKE_REV);

    }
}
