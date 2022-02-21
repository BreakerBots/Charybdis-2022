// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BreakerMath;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.devices.IMU;

/** Robot moves forward/back to target distance */
public class DriveStraight extends CommandBase {
  private int cyclecount;
  private Drive drive;
  private IMU imu;
  private double targetDistance;
  private double speedClamp;
  private double time;
  /** Autonomous command used to move the robot forward or backward a specified number of inches
   * 
   * @param driveArg Drive subsystem from RobotContainer
   * @param imuArg IMU device from RobotContainer
   * @param distanceInches the distance in inches you want the robot to travel (+ is forward and - is reverse) (relative to intake as front)
   * @param speedLimit the precent of max speed you wish the robot to be caped at (0.0 to 1.0) (DO NOT make argument negative) (WARNING: 7.0 or above is EXTREAMLY FAST)
   * @param secArg the time limit (in seconds) on this particular instance of this command befor it times out and cancles (safty feature to prevent accadents)
   */
  public DriveStraight(Drive driveArg, IMU imuArg, double distanceInches, double speedLimit, double secArg) {
    time = secArg * 50; 
    drive = driveArg;
    imu = imuArg;
    addRequirements(drive);
    targetDistance = distanceInches;
    speedClamp = speedLimit;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.resetEncoders();
    imu.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cyclecount ++;
    double curDist = BreakerMath.ticksToInches(drive.getLeftTicks());
    System.out.println("Ticks: " + drive.getLeftTicks());
    // System.out.println(drive.feedForwardCalc(4, 2)); // Constants for desired vel, desired acc
    double motorSpeed = drive.distPIDCalc(curDist, targetDistance);
    // double motorspeed = feedBackVal + feedForwardVal;
    motorSpeed = MathUtil.clamp(motorSpeed, -speedClamp, speedClamp);
    double turnSpeed = imu.getYaw() * -0.04;
    drive.autoMove(motorSpeed, turnSpeed);
    // 1D movement back and forth

    System.out.println("Position error: " + drive.distPID.getPositionError());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    cyclecount = 0;
    drive.autoMove(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drive.atSetpointDist() || cyclecount >= time;
  }
}
