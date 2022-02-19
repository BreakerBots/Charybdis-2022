// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.devices;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DashboardControl;

public class ClimbWatchdog extends SubsystemBase {
  /** Creates a new ClimbWatchdog. */
  private boolean forceEndSequence = false;
  private XboxController xbox;
  private Climber climb;
  private int cycleCount;
  public ClimbWatchdog(XboxController controllerArg, Climber climbArg) {
    xbox = controllerArg;
    climb = climbArg;
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (climb.isClimbing()) {
      cycleCount ++;
      if (xbox.getStartButtonPressed() || cycleCount > 2250) {
        forceEndSequence = true;
        cycleCount = 0;
      } else {
        forceEndSequence = false;
      }
    } else {
      forceEndSequence = false;
      cycleCount = 0;
    }
    if (forceEndSequence) {
      DashboardControl.log("CLIMB MANUALY ABORTED!");
    }
  }

  public boolean getClimbForceEnd() {
    return forceEndSequence;
  }

}
