// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.devices;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Climber;

public class ClimbWatchdog extends SubsystemBase {
  /** Creates a new ClimbWatchdog. */
  private boolean forceEndSequence = false;
  private int cycleCount;
  Climber climb;
  XboxController xbox;
  public ClimbWatchdog(Climber climbArg, XboxController controllerArg) {
    climb = climbArg;
    xbox = controllerArg;
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(climb.isClimbing()) {
      cycleCount ++;
      if (xbox.getStartButtonPressed()) {
        forceEndSequence = true;
        cycleCount = 0;
      } else if (cycleCount > 2250) {
        forceEndSequence = true;
        cycleCount = 0;
      } else {
        forceEndSequence = false;
      }
    } else {
      forceEndSequence = false;
    }
  }

  public boolean getClimbForceEnd() {
    return forceEndSequence;
  }

}
