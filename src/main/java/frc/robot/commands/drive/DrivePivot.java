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
  /** Autonomous command used to turn the robot in place a specified number of degrees 
   * relative to the direction in which it was facing when the command is first called 
   * 
   * @param driveArg Drive subsystem from RobotContainer
   * @param imuArg IMU subsystem from RobotContainer
   * @param targetDegrees the number of degrees you want the robot to turn relative to its orientation on command start (+ is right and - is left)
   * @param speedLimit the precent of max speed you wish the robot to be caped at (0.0 to 1.0) (DO NOT make argument negative) (NOTE: 3.0 or below tends to be quite slow)
   */
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
