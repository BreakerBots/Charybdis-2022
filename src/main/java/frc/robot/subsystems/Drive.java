// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerMath;
import frc.robot.Constants;
import frc.robot.RobotConfig;

public class Drive extends SubsystemBase {
  // Left motors (falcons)
  private WPI_TalonFX l1;
  private WPI_TalonFX l2;
  private WPI_TalonFX l3;
  private MotorControllerGroup driveL;
  // Right motors (falcons)
  private WPI_TalonFX r1;
  private WPI_TalonFX r2;
  private WPI_TalonFX r3;
  private MotorControllerGroup driveR;
  // Drivetrian
  private DifferentialDrive driveTrainDiff;
  // PIDs and stuff
  public PIDController anglePID;
  public PIDController distPID;
  public SimpleMotorFeedforward driveFF;
  public double prevNet;
  private boolean slowModeOn = false;
  private PowerDistribution pdp;

  /** Creates a new Drive. */
  public Drive(PowerDistribution pdpArg) {
    setName("Drive");
    pdp = pdpArg;
    anglePID = new PIDController(Constants.KP_ANG, Constants.KI_ANG, Constants.KD_ANG);
    anglePID.setTolerance(Constants.ANG_POS_TOL, Constants.ANG_VEL_TOL);
    distPID = new PIDController(Constants.KP_DIST, Constants.KI_DIST, Constants.KD_DIST);
    distPID.setTolerance(Constants.DIST_POS_TOL, Constants.DIST_VEL_TOL);
    driveFF = new SimpleMotorFeedforward(Constants.KS_DRIVE, Constants.KV_DRIVE, Constants.KA_DRIVE);
    // Left motors
    l1 = new WPI_TalonFX(Constants.L1_ID);
    l1.setNeutralMode(NeutralMode.Brake);
    l2 = new WPI_TalonFX(Constants.L2_ID);
    l2.setNeutralMode(NeutralMode.Brake);
    l3 = new WPI_TalonFX(Constants.L3_ID);
    l3.setNeutralMode(NeutralMode.Brake);
    driveL = new MotorControllerGroup(l1, l2, l3);

    // Right motors
    r1 = new WPI_TalonFX(Constants.R1_ID);
    r1.setNeutralMode(NeutralMode.Brake);
    r2 = new WPI_TalonFX(Constants.R2_ID);
    r2.setNeutralMode(NeutralMode.Brake);
    r3 = new WPI_TalonFX(Constants.R3_ID);
    r3.setNeutralMode(NeutralMode.Brake);
    driveR = new MotorControllerGroup(r1, r2, r3);
    driveR.setInverted(true);
    // Drivetrain
    driveTrainDiff = new DifferentialDrive(driveL, driveR);
  }

  /** Wraps around arcadeDrive to allow for movement */
  public void move(double netSpd, double turnSpd) {
    driveTrainDiff.arcadeDrive(netSpd, turnSpd); // Calculates speed and turn outputs
  }

  /** Wraps around tankDrive to allow for tank-like movement */
  public void tankMove(double spdL, double spdR) {
    driveTrainDiff.tankDrive(spdL, spdR);
  }

    /** Returns number of ticks on left motors */
    public double getLeftTicks() {
      return l1.getSelectedSensorPosition();
    }
  
    /** Returns number of ticks on right motors */
    public double getRightTicks() {
      return r1.getSelectedSensorPosition();
    }

  @Override
  public void periodic() {
    addChild("Drivetrain", driveTrainDiff);
    addChild("Angle PID", anglePID);
    addChild("Distance PID", distPID);
   // System.out.println("drive dist: " + BreakerMath.ticksToInches(getLeftTicks()));
  }

  public void toggleSlowMode() {
    slowModeOn = !slowModeOn;
  }

  public void setSlowMode(boolean val) {
    slowModeOn = val;
  }

  public boolean getSlowMode() {
    return slowModeOn;
  }

  /*** Sets encoders of all drive motors to 0 */
  public void resetEncoders() {
    l1.setSelectedSensorPosition(0);
    l2.setSelectedSensorPosition(0);
    l3.setSelectedSensorPosition(0);
    r1.setSelectedSensorPosition(0);
    r2.setSelectedSensorPosition(0);
    r3.setSelectedSensorPosition(0);
  }

  /**
   * Sets brake mode for drivetrain motors.
   * 
   * @param modeArg true for brake mode, false for default/coast mode.
   */
  public void setBrakeMode(boolean modeArg) {
    RobotConfig.setBrakeMode(modeArg, l1, l2, l3, r1, r2, r3);
  }

  /** Access feedForward .calculate() method */
  public double feedForwardCalc(double vel, double accel) {
    return driveFF.calculate(vel, accel);
  }

  /** Access distance PID .calculate() method */
  public double distPIDCalc(double distance, double target) {
    return distPID.calculate(distance, target);
  }

  /** Access distance PID .atSetpoint() method */
  public boolean atSetpointDist() {
    return (distPID.atSetpoint()) /** && (distPID.getVelocityError() != 0)) */;
  }

  public boolean atSetpointAng() {
    return (anglePID.atSetpoint()) /** && (anglePID.getVelocityError() != 0)) */;
  }

  public double getStatorCurrent(int motorID) {
    switch (motorID) {
      case Constants.L1_ID:
        return l1.getStatorCurrent();
      case Constants.L2_ID:
        return l2.getStatorCurrent();
      case Constants.L3_ID:
        return l3.getStatorCurrent();
      case Constants.R1_ID:
        return r1.getStatorCurrent();
      case Constants.R2_ID:
        return r2.getStatorCurrent();
      case Constants.R3_ID:
        return r3.getStatorCurrent();
      default:
        break;
    }
    return 0;
  }

  public double getSupplyCurrent(int motorID) {
    switch (motorID) {
      case Constants.L1_ID:
        return l1.getSupplyCurrent();
      case Constants.L2_ID:
        return l2.getSupplyCurrent();
      case Constants.L3_ID:
        return l3.getSupplyCurrent();
      case Constants.R1_ID:
        return r1.getSupplyCurrent();
      case Constants.R2_ID:
        return r2.getSupplyCurrent();
      case Constants.R3_ID:
        return r3.getSupplyCurrent();
      default:
        break;
    }
    return 0;
  }

}
