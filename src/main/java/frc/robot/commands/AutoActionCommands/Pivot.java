// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoActionCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Devices.IMU;

public class Pivot extends CommandBase {
  Drive drive;
  IMU imu;
  double target;
  double speedClamp;

  public Pivot(Drive driveArg, IMU imuArg, double targetDegrees, double speedLimit) {
    drive = driveArg;
    addRequirements(drive);
    imu = imuArg;
    target = targetDegrees;
    speedClamp = speedLimit;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    imu.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      double curAngle = imu.getYaw();
      double turnPercent = drive.anglePID.calculate(curAngle, target);
      turnPercent = MathUtil.clamp(turnPercent, -speedClamp, speedClamp); // Restricts motor speed

      // drive.move(0, turnPercent); // Turns in place
      drive.autoMove(0, turnPercent); // Turns in place
      System.out.println("Angle error: " + drive.anglePID.getPositionError());
      System.out.println("Turn percent:" + turnPercent);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drive.anglePID.atSetpoint();
  }
}
