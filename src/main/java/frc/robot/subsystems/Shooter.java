// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.devices.Limelight;

public class Shooter extends SubsystemBase {
  public enum FlywheelState {
    CHARGED,
    CHARGING,
    IDLE,
    OFF
  }

  public enum ShooterMode {
    HUB,
    //VISION,
    LOW,
    LAUNCH
  }

  public boolean isShooting = false;

  
  double kpSpd = 0;
  double currInput = 0;

  private FlywheelState flywheelState = FlywheelState.OFF;
  private ShooterMode shooterMode = ShooterMode.HUB;
  public PIDController flywheelPID;
  private boolean shooterIsUp;
  private WPI_TalonFX shooterL;
  private WPI_TalonFX shooterR;
  private double flyTgtSpdPrct = Constants.HUB_SHOOT_SPD;
  private double flyIdleSpd = flyTgtSpdPrct * 0.8;
  private MotorControllerGroup flywheel;
  // private DoubleSolenoid shooterSol;
  private Hopper hopper;
  private Limelight limelight;
  private double prevInput;
  public int counter = 0;

  public Shooter(Hopper hopperArg, Limelight limelightArg) {
    setName("Shooter");
    // flywheelFF = new SimpleMotorFeedforward(Constants.FLYWHEEL_KS,
    // Constants.FLYWHEEL_KV);
    hopper = hopperArg;
    limelight = limelightArg;
    flywheelPID = new PIDController(Constants.KP_FLYWHEEL, Constants.KI_FLYWHEEL, Constants.KD_FLYWHEEL);
    flywheelPID.setTolerance(Constants.FLYWHEEL_VEL_TOL, Constants.FLYWHEEL_ACCEL_TOL);
    shooterL = new WPI_TalonFX(Constants.SHOOTER_L_ID);
    shooterR = new WPI_TalonFX(Constants.SHOOTER_R_ID);
    shooterL.setInverted(true);
    shooterR.setInverted(false);
    flywheel = new MotorControllerGroup(shooterL, shooterR);
    // shooterSol = new DoubleSolenoid(Constants.PCM_ID,
    // PneumaticsModuleType.CTREPCM,
    // Constants.SHOOTERSOL_FWD, Constants.SHOOTERSOL_REV);
  }

  /** Returns the TPS of the flywheel's Motors */
  public double getFlywheelTPS() {
    return Math.abs((shooterL.getSelectedSensorVelocity() / 10));
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

  public void runFlywheelMike() {
    double flyCor = (flywheelPID.calculate(getFlywheelTPS(), getFlywheelTargetSpeed()));
    if (counter >= 5) {
      kpSpd = 0.00002;
      double speedErr = getFlywheelTargetSpeed() - getFlywheelTPS();
      currInput = prevInput + speedErr * kpSpd;
      flywheel.set(currInput);
      prevInput = currInput;
    } else {
      kpSpd = 0;
      flywheel.set(Constants.HUB_SHOOT_SPD);
    }

    System.out.println("Tgt: " + getFlywheelTargetSpeed() + "  Flywheel TPS: " + Math.round(getFlywheelTPS()) +
        "  MtrInput:  " + currInput +
        "  Counter:  " + counter++);
  }

  /** Makes flywheel charge to desired speed. */
  public void runFlywheel() {
    double flyCor = (flywheelPID.calculate(getFlywheelTPS(), getFlywheelTargetSpeed()));
    double flySpd = flyTgtSpdPrct + flyCor; // (flywheelPID.getPositionError() > 100 ? flyCor : 0);
    flywheel.set(flySpd);
    System.out.println(" fly tgt spd: " + getFlywheelTargetSpeed() + "  Flywheel TPS: " + Math.round(getFlywheelTPS()) +
        "  Flywheel accel:  " + String.format("%.2f", flywheelPID.getVelocityError()) +
        "  Flywheel.set: " + String.format("%.2f", flySpd) +
        " at set pt: " + flywheelPID.atSetpoint());
  }

  private void visionHoodPosLoop() {
    if (limelight.getTargetDistance() >= Constants.HOOD_UP_DIST) {
      raiseShooter();
    } else {
      lowerShooter();
    }
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
      // case VISION:
      //   visionHoodPosLoop();
      //   flyTgtSpdPrct = Constants.HUB_SHOOT_SPD;
      //   break;
      case HUB:
      default:
        lowerShooter();
        flyTgtSpdPrct = Constants.HUB_SHOOT_SPD;
        break;
    }
    flyIdleSpd = flyTgtSpdPrct * 0.8;
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
      // shooterSol.set(Value.kForward);
      shooterIsUp = true;
    }
  }

  /** Brings shooter to lower firing angle */
  public void lowerShooter() {
    if (shooterIsUp) {
      // shooterSol.set(Value.kReverse);
      shooterIsUp = false;
    }
  }

  public ShooterMode getShootMode() {
    return shooterMode;
  }

  public double getFlywheelTargetSpeed() {
   // if (limelight.hasTarget() && (getShootMode() == ShooterMode.VISION)) {
   //   return 0;
  //  } else {
      return Constants.FLYWHEEL_MAX_TPS * flyTgtSpdPrct;
    //}
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
    return flywheelPID.atSetpoint() && (flywheelPID.getVelocityError() != 0);
  }

  public void setFlywheelManualSpeed(double speed) {
    flywheel.set(speed);
  }

  public void periodic() {
    DashboardControl.log("set point: " + getFlywheelTargetSpeed() + " speed: " + getFlywheelTPS());
    setShooter();
    switch (flywheelState) {
      case CHARGED:
      case CHARGING:
        runFlywheel();
        break;
      case OFF:
      case IDLE:
        if (hopper.bothSlotsAreFull()) {
          // setIdle();
          setOff();
        } else {
          setOff();
        }
        break;
    }
    addChild("Flywheel", flywheel);
    // addChild("Shooter Piston", shooterSol);
    addChild("Flywheel PID", flywheelPID);
    // setFlywheelManualSpeed(0.44);
    // System.out.println("fly spd: " + getFlywheelTPS() + "\n" + " impt V: " +
    // (getLFlywheelSup()+getRFlywheelSup()));
    // System.out.println("fly err: " + flywheelPID.getPositionError());
  }

}