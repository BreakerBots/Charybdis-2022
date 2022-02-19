// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.sequences;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.climb.actions.ClimbStablityCheck;
import frc.robot.commands.climb.actions.MoveClimb;
import frc.robot.commands.climb.actions.PivotClimb;
import frc.robot.commands.climb.sequenceManagement.SetSequenceTotal;
import frc.robot.commands.climb.sequenceManagement.WaitForDownButton;
import frc.robot.commands.climb.sequenceManagement.endClimbSequence;
import frc.robot.commands.climb.sequenceManagement.startClimbSequence;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.devices.IMU;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class HighbarClimbSequence extends SequentialCommandGroup {
  /** Command group that runs sequence of actions nessary to automaticly climb to high bar, 
   * for actuon to continue after first extension user mus press D-PAD down button*/
  public HighbarClimbSequence(Climber climbArg, IMU imuArg, XboxController controllerArg) {
    addRequirements(climbArg);
    addRequirements(imuArg);
    
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new startClimbSequence(climbArg),
      new SetSequenceTotal(climbArg, 15),
      new MoveClimb(climbArg, Constants.CLIMB_FULL_EXT_DIST),
      new WaitForDownButton(controllerArg, climbArg),
      new MoveClimb(climbArg, Constants.CLIMB_FULL_RET_DIST),
      new MoveClimb(climbArg, Constants.CLIMB_MIRACLE_GRAB_EXT_DIST),
      new PivotClimb(climbArg),
      new ClimbStablityCheck(climbArg, imuArg),
      new MoveClimb(climbArg, Constants.CLIMB_FULL_EXT_DIST),
      new ClimbStablityCheck(climbArg, imuArg),
      new PivotClimb(climbArg),
      new MoveClimb(climbArg, Constants.CLIMB_LIFT_OF_MID_DIST),
      new ClimbStablityCheck(climbArg, imuArg),
      new PivotClimb(climbArg),
      new ClimbStablityCheck(climbArg, imuArg),
      new MoveClimb(climbArg, Constants.LIFT_ONTO_HIGH_DIST),
      new MoveClimb(climbArg, Constants.SECOND_MIRACLE_GRAB_EXT_DIST),
      new endClimbSequence(climbArg)
    );
  }
}
