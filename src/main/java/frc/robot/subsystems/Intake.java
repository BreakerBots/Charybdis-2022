package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motor;

public class Intake extends SubsystemBase {
    private WPI_TalonSRX armMotor;
    private WPI_TalonSRX indexerL;
    private WPI_TalonSRX indexerR;
    private static DoubleSolenoid intakeSol_L = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
            Constants.L_INTAKESOL_FWD, Constants.L_INTAKESOL_REV);
    private static DoubleSolenoid intakeSol_R = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
            Constants.R_INTAKESOL_FWD, Constants.R_INTAKESOL_REV);

    public Intake() {
        armMotor = new WPI_TalonSRX();
    }
}
