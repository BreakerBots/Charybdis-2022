// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.actions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DashboardControl;
import frc.robot.subsystems.devices.IMU;

public class ClimbStablityCheck extends CommandBase {
  /** Creates a new ClimbStablityCheck. */
  IMU imu;
  Climber climber;
  private long stableTimeCount;
  /** Checks if robot is stable enough to continue climbing. 
   * Uses rate of gyro change. Rate of change must return as 
   * below passing threshhold for a cirtan amount of time before it can continue.*/
  public ClimbStablityCheck(Climber climberArg, IMU imuArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    imu = imuArg;
    climber = climberArg;
    addRequirements(imu);
    addRequirements(climberArg);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DashboardControl.log("CHECKING CLIMB STABLITY");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Math.abs(imu.getPitchRate()) < Constants.CLIMB_PITCH_TOL && Math.abs(imu.getYawRate()) < Constants.CLIMB_YAW_TOL && Math.abs(imu.getRollRate()) < Constants.CLIMB_ROLL_TOL) {
      stableTimeCount ++;
    } else {
      stableTimeCount = 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DashboardControl.log("CLIMB STABLITY CHECK PASSED!");
    climber.climbSequenceProgress ++;
    System.out.println("CLIMB SEQUENCE PROGRESS: " + climber.climbSequenceProgress + " of " + climber.climbSequenceTotal);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(imu.getPitchRate()) < Constants.CLIMB_PITCH_TOL && Math.abs(imu.getYawRate()) < Constants.CLIMB_YAW_TOL && Math.abs(imu.getRollRate()) < Constants.CLIMB_ROLL_TOL && stableTimeCount > Constants.CLIMB_MIN_STABLE_TIME) {
      return true;
    }
    else {
      return false;
    }
  }
}
