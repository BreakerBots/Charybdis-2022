// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.commands.climb.sequenceManagement.SequenceWatchdog;
import frc.robot.commands.climb.sequences.HighbarClimbSequence;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.devices.IMU;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class RunClimbSequence extends ParallelRaceGroup {
  /** Creates a new RunClimbSequence. */
  public RunClimbSequence(Climber climbArg, IMU imuArg, XboxController controllerArg) {
    addRequirements(climbArg);
    addRequirements(imuArg);
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new HighbarClimbSequence(climbArg, imuArg, controllerArg),    
      new SequenceWatchdog(controllerArg)
      );
  }
}
