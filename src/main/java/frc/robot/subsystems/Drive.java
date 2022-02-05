// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
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
  /** Creates a new Drive. */
  public PIDController anglePID;
  public PIDController distPID;
  public SimpleMotorFeedforward driveFF;
  public double prevNet;
  private PowerDistribution pdp;

  private final double artLinearFeedForward = 0.3;
  private final double artAngleFeedForward = 0.2;

  /** Creates a new Drive. */
  public Drive(PowerDistribution pdpArg) {
    pdp = pdpArg;
    anglePID = new PIDController(Constants.KP_ANG, Constants.KI_ANG, Constants.KD_ANG);
    anglePID.setTolerance(Constants.ANG_POS_TOLERANCE, Constants.ANG_VEL_TOLERANCE);
    distPID = new PIDController(Constants.KP_DIST, Constants.KI_DIST, Constants.KD_DIST);
    distPID.setTolerance(Constants.DIST_POS_TOLERANCE, Constants.DIST_VEL_TOLERANCE);
    driveFF = new SimpleMotorFeedforward(Constants.KS_DRIVE, Constants.KV_DRIVE, Constants.KA_DRIVE);
    // Left motors
    l1 = new WPI_TalonFX(Constants.L1_ID);
    l2 = new WPI_TalonFX(Constants.L2_ID);
    l3 = new WPI_TalonFX(Constants.L3_ID);
    driveL = new MotorControllerGroup(l1, l2, l3);
    // Right motors
    r1 = new WPI_TalonFX(Constants.R1_ID);
    r2 = new WPI_TalonFX(Constants.R2_ID);
    r3 = new WPI_TalonFX(Constants.R3_ID);
    driveR = new MotorControllerGroup(r1, r2, r3);
    driveR.setInverted(true);
    // Drivetrain
    driveTrainDiff = new DifferentialDrive(driveL, driveR);
  }

  /** Wraps around arcadeDrive to allow for movement */
  public void move(double netSpd, double turnSpd) {
    if (pdp.getVoltage() < 8.5) {
      netSpd *= 0.85;
      turnSpd *= 0.85;
    }
    driveTrainDiff.arcadeDrive(netSpd, turnSpd); // Calculates speed and turn outputs
  }

  public void autoMove(double netSpd, double turnSpd) {
    if (pdp.getVoltage() < 8.5) {
      netSpd *= 0.85;
      turnSpd *= 0.85;
    }
    if (netSpd > 0) {
      netSpd += artLinearFeedForward;
    }
    else {
      netSpd -= artLinearFeedForward;
    }
    if (turnSpd > 0) {
      turnSpd += artAngleFeedForward;
    }
    else {
      turnSpd -= artAngleFeedForward;
    }
    driveTrainDiff.arcadeDrive(netSpd, turnSpd); // Calculates speed and turn outputs
  }

  /** Wraps around tankDrive to allow for tank-like movement */
  public void tankMove(double spdL, double spdR) {
    driveTrainDiff.tankDrive(spdL, spdR);
  }

  @Override
  public void periodic() {
  }

  /** Returns number of ticks on left motors */
  public double getLeftTicks() {
    return l1.getSelectedSensorPosition();
  }

  /** Returns number of ticks on right motors */
  public double getRightTicks() {
    return r1.getSelectedSensorPosition();
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
    return distPID.atSetpoint();
  }

}
