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

  private SimpleMotorFeedforward flywheelFF;
  private PIDController flywheelPID;
  private FlywheelState flywheelState = FlywheelState.OFF;
  private ShooterMode shooterMode = ShooterMode.UP;
  private boolean shooterIsUp;
  private WPI_TalonFX shooterL;
  private WPI_TalonFX shooterR;
  private double flyTgtSpdPrec = Constants.UP_SHOOTERSPEED;
  private MotorControllerGroup flywheel;
  // public boolean autoShoot; Remove due to being unused.
  private DoubleSolenoid shooterSol;
  private Hopper hopper;

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

  /** Turns Flywheel On */
  public void flyweelFullOn() {
    flywheelState = FlywheelState.CHARGING;
  }

  /** Turns Flywheel Off */
  public void flyweelOff() {
    flywheel.set(0);
    flywheelState = FlywheelState.OFF;
  }

  /** Returns the RPM of the flywheel's Motors */
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

  /** Brings shooter to higher fireing angle */
  public boolean shooterUp() {
    shooterSol.set(Value.kForward);
    return shooterIsUp = true;
  }

  /** Brings shooter to lower fireing angle */
  public boolean shooterDown() {
    shooterSol.set(Value.kReverse);
    return shooterIsUp = false;
  }

  public double getFlywheelTargetSpeed() {
    return Constants.FLYWHEEL_MAX_SPEED * flyTgtSpdPrec;
  }

  public double getFlywheelIdleSpeed() {
    return flyTgtSpdPrec * 0.8;
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

  public void periodic() {
    switch (shooterMode) {
      case UP:
        if (shooterIsUp) {
          shooterDown();
        }
        flyTgtSpdPrec = Constants.UP_SHOOTERSPEED;
        break;
      case LOW:
        if (!shooterIsUp) {
          shooterUp();
        }
        flyTgtSpdPrec = Constants.LOW_SHOOTERSPEED;
        break;
      case LAUNCH:
        flyTgtSpdPrec = Constants.LAUNCH_SHOOTERSPEED;
        break;
      default:
        shooterDown();
        flyTgtSpdPrec = Constants.UP_SHOOTERSPEED;
    }
    if (flywheelState == FlywheelState.CHARGING || flywheelState == FlywheelState.CHARGED) {
      double flySpd;
      flySpd = (flywheelPID.calculate(getFlywheelTPS(), getFlywheelTargetSpeed()));
      flywheel.set(flySpd);
    } else if ((hopper.slot1IsFull() && hopper.slot2IsFull())
        && (flywheelState == FlywheelState.OFF || flywheelState == FlywheelState.IDLE)) {
      flywheel.set(getFlywheelIdleSpeed());
      flywheelState = FlywheelState.IDLE;
    } else if ((!hopper.slot1IsFull() || !hopper.slot2IsFull())
        && (flywheelState == FlywheelState.OFF || flywheelState == FlywheelState.IDLE)) {
      flyweelOff();
    }
  }
}