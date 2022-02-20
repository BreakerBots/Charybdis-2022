// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.devices.IMU;

public class DrivePivot extends CommandBase {
  Drive drive;
  IMU imu;
  double target;
  double speedClamp;
  double lastAngle;

  public DrivePivot(Drive driveArg, IMU imuArg, double targetDegrees, double speedLimit) {
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
      if (Math.abs(curAngle - lastAngle)>90)
        curAngle *= -1;
      lastAngle = curAngle;
      double turnPercent = drive.anglePID.calculate(curAngle, target);
      turnPercent += (turnPercent>=0 ? Constants.ANG_FEEDFWD : -Constants.ANG_FEEDFWD);
      turnPercent = MathUtil.clamp(turnPercent, -speedClamp, speedClamp); // Restricts motor speed

      System.out.println("CurrAng: " + curAngle + " TgtAng: " + target + " AngErr: " + drive.anglePID.getPositionError() + " Turn %: " + turnPercent);
      // drive.move(0, turnPercent); // Turns in place
      drive.autoMove(0, turnPercent); // Turns in place
      
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
