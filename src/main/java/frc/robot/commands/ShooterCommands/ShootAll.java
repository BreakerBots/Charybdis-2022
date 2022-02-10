// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooterCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class ShootAll extends CommandBase {
  Shooter shooter;
  XboxController xbox;
  Hopper hopper;
  private long cycleCount;
  private long timedStopCount;
  public ShootAll(Shooter shooterArg, Hopper hopperArg, XboxController controllerArg) {
    shooter = shooterArg;
    xbox = controllerArg;
    hopper = hopperArg;
    addRequirements(shooter);
    addRequirements(hopper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cycleCount ++;
    if (cycleCount > 450) {
      xbox.setRumble(RumbleType.kLeftRumble, 0);
      xbox.setRumble(RumbleType.kRightRumble, 0);
    }
    if (cycleCount % 400 == 0) {
      System.out.println("PLEASE PRESS B BUTTON TO SHOOT (IF IN TELEOP)");
    }
    if (xbox.getBButtonPressed() && shooter.flyweelState) {
      hopper.hopperOn();
      System.out.println("SHOOTER STARTED!");
    }
    else if (shooter.autoShoot == true && shooter.flyweelState) {
      hopper.hopperOn();
      shooter.autoShoot = false;
      System.out.println("SHOOTER STARTED IN AUTO!");
    }
    if (hopper.getHopperPos1() == false && hopper.getHopperPos2() == false) {
       timedStopCount ++;
    }
    if (hopper.getHopperPos1() == false && hopper.getHopperPos2() == false && timedStopCount == 250) {
      hopper.hopperOff();
      shooter.flyweelOff();
      timedStopCount = 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (shooter.flyweelState == false) {
      System.out.println("HOPPER DEPLETED - SHOOTER STOPED!");
      return true;
    } else if (xbox.getRightBumperPressed()) {
      System.out.println("SHOOTER MANUALY STOPED!");
      return true;
    }
    else {
      return false;
    }
  }
}
