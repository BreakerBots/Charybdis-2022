// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.devices.IMU;

public class Turn extends CommandBase {
  /** Creates a new Turn. */
  private Drive driveTrain;
  private IMU imu;
  private double tgtAngle;
  private double radiusOfCurvature;
  private double driveRatio;
  private double angleError;

  private double tgtSpeed;

  public Turn(Drive driveTrainArg, IMU imuArg, double angleArg, double radiusArg, double speedArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveTrain = driveTrainArg;
    addRequirements(driveTrain);
    imu = imuArg;
    tgtAngle = angleArg;
    tgtSpeed = speedArg;
    radiusOfCurvature = radiusArg;
    driveRatio = (radiusOfCurvature-13)/(radiusOfCurvature+13);
    // Start angleError large
    angleError = 180;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.resetEncoders();
    imu.reset();;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double innerMotorSpeed = tgtSpeed * driveRatio;
    double currAngle = imu.getYaw();
    double currAngleNew;

    if (tgtSpeed < 0) {
      currAngleNew = currAngle * -1;
    }
    else {
      currAngleNew = currAngle;
    }
    angleError = tgtAngle - currAngleNew;
    double leftMotor = (angleError>0 ? tgtSpeed : innerMotorSpeed);
    double rightMotor = (angleError>0 ? innerMotorSpeed: tgtSpeed);

    System.out.println("CurrAng: " + currAngle + " TgtAng: " + tgtAngle + " AngErr: " + angleError + " LeftMtr: " + leftMotor + " RtMtr: " + rightMotor);
    driveTrain.tankMove(leftMotor,rightMotor);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(angleError)< 1;
  }
}
