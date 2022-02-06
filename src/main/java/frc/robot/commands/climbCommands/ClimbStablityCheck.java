// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climbCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Devices.IMU;

public class ClimbStablityCheck extends CommandBase {
  /** Creates a new ClimbStablityCheck. */
  IMU imu;
  Climber climber;
  public ClimbStablityCheck(Climber climberArg, IMU imuArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    imu = imuArg;
    climber = climberArg;
    addRequirements(imu);
    addRequirements(climberArg);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("CLIMB STABLITY CHECK PASSED!");
    climber.climbSequenceProgress ++;
    System.out.println("CIMB SEQUENCE PROGRESS: " + climber.climbSequenceProgress + " of " + climber.climbSequenceTotal);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (imu.getPitchRate() < Constants.CLIMB_PITCH_TOLR && imu.getYawRate() < Constants.CLIMB_YAW_TOLR && imu.getRollRate() < Constants.CLIMB_ROLL_TOLR) {
      return true;
    }
    else {
      return false;
    }
  }
}
