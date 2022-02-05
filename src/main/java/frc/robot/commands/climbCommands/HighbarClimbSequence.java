// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climbCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Devices.IMU;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class HighbarClimbSequence extends SequentialCommandGroup {
  /** Creates a new MidbarClimbSequence. */
  public HighbarClimbSequence(Climber climbArg, IMU imuArg) {
    addRequirements(climbArg);
    addRequirements(imuArg);
    
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new MoveClimb(climbArg, Constants.CLIMB_FULL_EXT_DIST),
      new MoveClimb(climbArg, Constants.CLIMB_FULL_RET_DIST),
      new MoveClimb(climbArg, Constants.CLIMB_MIRACLE_GRAB_EXT_DIST),
      new PivotClimb(climbArg),
      new ClimbStablityCheck(imuArg),
      new MoveClimb(climbArg, Constants.CLIMB_FULL_EXT_DIST),
      new ClimbStablityCheck(imuArg),
      new PivotClimb(climbArg),
      new MoveClimb(climbArg, Constants.CLIMB_LIFT_OF_MID_DIST),
      new ClimbStablityCheck(imuArg),
      new PivotClimb(climbArg),
      new ClimbStablityCheck(imuArg),
      new MoveClimb(climbArg, Constants.LIFT_ONTO_HIGH_DIST),
      new MoveClimb(climbArg, Constants.SECOND_MIRACLE_GRAB_EXT_DIST)
    );
  }
}