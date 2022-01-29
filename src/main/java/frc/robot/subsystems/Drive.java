// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
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
  public Drive() {
    anglePID = new PIDController(Constants.KP_ANG, Constants.KI_ANG, Constants.KD_ANG);
    distPID = new PIDController(Constants.KP_DIST, Constants.KI_DIST, Constants.KD_DIST);
    // Left motors
    l1 = new WPI_TalonFX(Constants.L1_ID); 
    setTalonLimits(l1);
    l2 = new WPI_TalonFX(Constants.L2_ID);
    setTalonLimits(l2);
    l3 = new WPI_TalonFX(Constants.L3_ID);
    setTalonLimits(l3);
    driveL = new MotorControllerGroup(l1, l2, l3);
    // Right motors
    r1 = new WPI_TalonFX(Constants.R1_ID);
    setTalonLimits(r1);
    r2 = new WPI_TalonFX(Constants.R2_ID);
    setTalonLimits(r2);
    r3 = new WPI_TalonFX(Constants.R3_ID);
    setTalonLimits(r3);
    driveR = new MotorControllerGroup(r1, r2, r3);
    driveR.setInverted(true);
    // Drivetrain
    driveTrainDiff = new DifferentialDrive(driveL, driveR);
  }

  public void driveWithJoystick(XboxController xbox){

    double back = xbox.getLeftTriggerAxis();
    double forward = xbox.getRightTriggerAxis();
    double turn = xbox.getLeftX();
    double net = forward - back;
    driveTrainDiff.arcadeDrive(net, turn); // Calculates speed and turn outputs
  }

   // Wraps around arcadeDrive to allow for movement
   public void move(double netSpd, double turnAmt) {
    driveTrainDiff.arcadeDrive(netSpd, turnAmt); // Calculates speed and turn outputs
  }

  // 1-param call for move method
  public void move(double netSpd) {
    move(netSpd, 0);
  }

  // Wraps around tankDrive to allow for tank-like movement
  public void tankMove(double spdL, double spdR) {
    driveTrainDiff.tankDrive(spdL, spdR);
  }

  @Override
  public void periodic() {
  }
  
  public double getLeftTicks() {
    return l1.getSelectedSensorPosition();
  }
  public double getRightTicks() {
    return r1.getSelectedSensorPosition();
  }

  public void resetEncoders() {
    l1.setSelectedSensorPosition(0);
    l2.setSelectedSensorPosition(0);
    l3.setSelectedSensorPosition(0);
    r1.setSelectedSensorPosition(0);
    r2.setSelectedSensorPosition(0);
    r3.setSelectedSensorPosition(0);
  }
  
  private void setTalonLimits(WPI_TalonFX motor) {
                                                                  //enabled | Limit(amp) | Trigger Threshold(amp) | Trigger Threshold Time(s) 
    motor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true,      38,                30,                  0.05));
    motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true,      38,                30,                0.05));
  }


}
