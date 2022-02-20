// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.actions;

import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DashboardControl;
import frc.robot.subsystems.devices.ClimbWatchdog;
import frc.robot.subsystems.devices.IMU;

public class ClimbStabilityCheck extends CommandBase {
  /** Creates a new ClimbStablityCheck. */
  private IMU imu;
  private Climber climber;
  private ClimbWatchdog dachshund;
  private long stableTimeCount;
  /** Checks if robot is stable enough to continue climbing. 
   * Uses rate of gyro change. Rate of change must return as 
   * below passing threshhold for a cirtan amount of time before it can continue.*/
  public ClimbStabilityCheck(Climber climberArg, IMU imuArg, ClimbWatchdog watchdogArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    
    imu = imuArg;
    climber = climberArg;
    dachshund = watchdogArg;
    addRequirements(imu);
    addRequirements(climber);
    addRequirements(dachshund);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climber.setIsClimbing(true);
    DashboardControl.log("CHECKING CLIMB STABLITY");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!dachshund.getClimbForceEnd()) {
      if (Math.abs(imu.getPitchRate()) < Constants.CLIMB_PITCH_TOL 
      && Math.abs(imu.getYawRate()) < Constants.CLIMB_YAW_TOL 
      && Math.abs(imu.getRollRate()) < Constants.CLIMB_ROLL_TOL) {
        stableTimeCount ++;
      } else {
        stableTimeCount = 0;
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DashboardControl.log("CLIMB STABLITY CHECK PASSED!");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(imu.getPitchRate()) < Constants.CLIMB_PITCH_TOL 
    && Math.abs(imu.getYawRate()) < Constants.CLIMB_YAW_TOL 
    && Math.abs(imu.getRollRate()) < Constants.CLIMB_ROLL_TOL 
    && stableTimeCount > Constants.CLIMB_MIN_STABLE_TIME) || dachshund.getClimbForceEnd();
  }
}
