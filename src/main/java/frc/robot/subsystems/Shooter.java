// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

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
    HUB,
    LOW,
    LAUNCH
  }

  private SimpleMotorFeedforward flywheelFF;
  private PIDController flywheelPID;
  private FlywheelState flywheelState = FlywheelState.OFF;
  private ShooterMode shooterMode = ShooterMode.HUB;
  private boolean shooterIsUp;
  private WPI_TalonFX shooterL;
  private WPI_TalonFX shooterR;
  private double flyTgtSpdPrct = Constants.HUB_SHOOT_SPD;
  private double flyIdleSpd = flyTgtSpdPrct * 0.8;
  private MotorControllerGroup flywheel;
  // public boolean autoShoot; Remove due to being unused.
  private DoubleSolenoid shooterSol;
  private Hopper hopper;
  public boolean isShooting = false;

  public Shooter(Hopper hopperArg) {
    // flywheelFF = new SimpleMotorFeedforward(Constants.FLYWHEEL_KS,
    // Constants.FLYWHEEL_KV);
    flywheelPID = new PIDController(Constants.FLYWHEEL_KP, Constants.FLYWHEEL_KI, Constants.FLYWHEEL_KD);
    hopper = hopperArg;
    flywheelPID.setTolerance(20);
    shooterL = new WPI_TalonFX(Constants.SHOOTER_L_ID);
    shooterR = new WPI_TalonFX(Constants.SHOOTER_R_ID);
    shooterL.setInverted(true);
    flywheel = new MotorControllerGroup(shooterL, shooterR);
    shooterSol = new DoubleSolenoid(Constants.PCM_ID, PneumaticsModuleType.CTREPCM,
        Constants.SHOOTERSOL_FWD, Constants.SHOOTERSOL_REV);
  }

  /** Flywheel is set to charging state. */
  public void chargeFlywheel() {
    flywheelState = FlywheelState.CHARGING;
  }

  /** Turns flywheel off */
  public void setOff() {
    flywheel.set(0);
    flywheelState = FlywheelState.OFF;
  }
  
  /** Sets flywheel into idling state. */
  public void setIdle() {
    flywheel.set(flyIdleSpd);
    flywheelState = FlywheelState.IDLE;
  }

  /** Makes flywheel charge to desired speed. */
  public void runFlywheel() {
    double flySpd = (flywheelPID.calculate(getFlywheelTPS(), getFlywheelTargetSpeed()));
    flywheel.set(flySpd);
  }

  /** Based on shoot mode, sets idle speed, target speed, and shoot position. */
  public void setShooter() {
    switch (shooterMode) {
      case LOW:
        raiseShooter();
        flyTgtSpdPrct = Constants.LOW_SHOOT_SPD;
        break;
      case LAUNCH:
        raiseShooter();
        flyTgtSpdPrct = Constants.LAUNCH_SHOOT_SPD;
        break;
      case HUB:
      default:
        lowerShooter();
        flyTgtSpdPrct = Constants.HUB_SHOOT_SPD;
        break;
    }
    flyIdleSpd = flyTgtSpdPrct * 0.8;
  }

  /** Returns the TPS of the flywheel's Motors */
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

  public double getFlywheelTargetSpeed() {
    return Constants.FLYWHEEL_MAX_TPS * flyTgtSpdPrct;
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

  public void periodic() {
    setShooter();
    switch (flywheelState) {
      case CHARGED:
      case CHARGING:
        runFlywheel();
        break;
      case OFF:
      case IDLE:
        if (hopper.bothSlotsAreFull()) {
          setIdle();
        } else {
          setOff();
        }
    }
  }

  public void setflywheelManualSpeed(double speed) {
    flywheel.set(speed);
  }
}