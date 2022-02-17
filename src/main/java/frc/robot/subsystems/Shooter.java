// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  public enum FlywheelState {
    CHARGED,
    CHARGING,
    IDLE,
    OFF
  }

  public enum ShooterMode {
    UP,
    LOW,
    LAUNCH
  }

  // States for shooter
  private FlywheelState flywheelState = FlywheelState.OFF;
  private ShooterMode shooterMode = ShooterMode.UP;
  private boolean shooterIsUp = false;
  public boolean isShooting = false;
  // Controllers for shooter
  private SimpleMotorFeedforward flywheelFF; // Currently unused
  private PIDController flywheelPID;
  // Flywheel motors
  private WPI_TalonFX shooterL;
  private WPI_TalonFX shooterR;
  private MotorControllerGroup flywheel;
  // Flywheel target speed
  private double flyTgtSpd = Constants.HUB_SHOOT_SPD;
  private double flyIdleSpd = 0.8 * flyTgtSpd;
  // Hood piston for changing position
  private DoubleSolenoid shooterSol;
  // Subsystem pass-in
  private Hopper hopper;

  public Shooter(Hopper hopperArg) {
    // flywheelFF = new SimpleMotorFeedforward(Constants.FLYWHEEL_KS,
    // Constants.FLYWHEEL_KV);
    flywheelPID = new PIDController(Constants.FLYWHEEL_KP, Constants.FLYWHEEL_KI, Constants.FLYWHEEL_KD);
    hopper = hopperArg;
    flywheelPID.setTolerance(Constants.FLYWHEEL_SPD_TOL);
    shooterL = new WPI_TalonFX(Constants.SHOOTER_L_ID);
    shooterR = new WPI_TalonFX(Constants.SHOOTER_R_ID);
    shooterL.setInverted(true);
    flywheel = new MotorControllerGroup(shooterL, shooterR);
    shooterSol = new DoubleSolenoid(Constants.PCM_ID, PneumaticsModuleType.CTREPCM,
        Constants.SHOOTERSOL_FWD, Constants.SHOOTERSOL_REV);
  }

  /** Brings shooter to higher firing angle */
  public void raiseShooter() {
    if (!shooterIsUp) {
      shooterSol.set(Value.kForward);
      shooterIsUp = true;
    }
  }

  /** Brings shooter to lower firing angle */
  public void lowerShooter() {
    if (shooterIsUp) {
      shooterSol.set(Value.kReverse);
      shooterIsUp = false;
    }
  }

  /** Sets target and idle flywheel speed based on shooter position. */
  public void setFlywheelSpds() {
    switch (shooterMode) {
      case UP:
        flyTgtSpd = Constants.HUB_SHOOT_SPD;
      case LOW:
        flyTgtSpd = Constants.LOW_SHOOT_SPD;
      case LAUNCH:
        flyTgtSpd = Constants.LAUNCH_SHOOT_SPD;
    }
    flyIdleSpd = 0.8 * flyTgtSpd;
  }

  /** Sets flywheel to charging state. */
  public void setCharging() {
    double flySpd = (flywheelPID.calculate(getFlywheelTPS(), getFlywheelTargetRPM()));
    flywheel.set(flySpd);
    flywheelState = FlywheelState.CHARGING;
  }

  /** Turns off flywheel. */
  public void setOff() {
    flywheel.set(0);
    flywheelState = FlywheelState.OFF;
  }

  /** Sets flywheel to idle spinning. */
  public void setIdle() {
    flywheel.set(flyIdleSpd);
    flywheelState = FlywheelState.IDLE;
  }

  /** Returns the RPM of the flywheel's motors */
  public double getFlywheelTPS() {
    return Math.abs((shooterL.getSelectedSensorVelocity() / 10));
  }

  public double getLFlywheelSta() {
    return shooterL.getStatorCurrent();
  }

  public double getLFlywheelSup() {
    return shooterL.getSupplyCurrent();
  }

  public double getRFlywheelSta() {
    return shooterR.getStatorCurrent();
  }

  public double getRFlywheelSup() {
    return shooterR.getSupplyCurrent();
  }

  public double getFlywheelTargetRPM() {
    return Constants.FLYWHEEL_MAX_RPM * flyTgtSpd;
  }

  public double getFlywheelIdleSpeed() {
    return flyIdleSpd;
  }

  public ShooterMode getShootMode() {
    return shooterMode;
  }

  public void setShootMode(ShooterMode mode) {
    shooterMode = mode;
  }

  public FlywheelState getFlywheelState() {
    return flywheelState;
  }

  public void setFlywheelState(FlywheelState state) {
    flywheelState = state;
  }

  public void resetFlywheelPID() {
    flywheelPID.reset();
  }

  public boolean flywheelPIDAtSetpoint() {
    return flywheelPID.atSetpoint();
  }

  public void setManualFlywheelSpeed(double speed) {
    flywheel.set(speed);
  }

  public void shooterLogicLoop() {
    switch (shooterMode) { // Aims shooter according to position
      case UP:
        lowerShooter();
        break;
      case LOW:
      case LAUNCH:
        raiseShooter();
        break;
    }
    setFlywheelSpds();
    switch (flywheelState) { // Sets flywheel speed according to its state.
      case CHARGING:
      case CHARGED:
        setCharging();
      case IDLE:
      case OFF:
        if (hopper.bothSlotsAreFull()) { // Shooter idles if hopper is full.
          setIdle();
        } else { // Shooter shuts off if no balls are present.
          setOff();
        }

    }
    // if (flywheelState == FlywheelState.CHARGING || flywheelState == FlywheelState.CHARGED) {
    //   double flySpd;
    //   flySpd = (flywheelPID.calculate(getFlywheelTPS(), getFlywheelTargetRPM()));
    //   flywheel.set(flySpd);
    // } else if ((hopper.slot1IsFull() && hopper.slot2IsFull())
    //     && (flywheelState == FlywheelState.OFF || flywheelState == FlywheelState.IDLE)) {
    //   flywheel.set(getFlywheelIdleSpeed());
    //   flywheelState = FlywheelState.IDLE;
    // } else if ((!hopper.slot1IsFull() || !hopper.slot2IsFull())
    //     && (flywheelState == FlywheelState.OFF || flywheelState == FlywheelState.IDLE)) {
    //   setOff();
    // }
  }

  public void periodic() {
    shooterLogicLoop();
  }

}