// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.FlywheelState;

/**
 * Shoots all the balls in the hopper
 */
public class ShootAll extends CommandBase {
  /** Shooter alias for RobotContainer instance. */
  Shooter shooter;
  /** Xbox controller alias for RobotContainer instance. */
  XboxController xbox;
  /** Hopper alias for RobotContainer instance. */
  Hopper hopper;
  /** Intake alias for RobotContainer instance. */
  Intake intake;
  private long cycleCount;
  private long timedStopCount;

  /** Creates a new ShootAll. */
  public ShootAll() {
    shooter = Robot.m_robotContainer.shooterSys;
    xbox = Robot.m_robotContainer.xbox;
    hopper = Robot.m_robotContainer.hopperSys;
    intake = Robot.m_robotContainer.intakeSys;
    addRequirements(shooter);
    addRequirements(hopper);
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("STARTED SHOOTING");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cycleCount++;
  
    // if (cycleCount % 400 == 0) {
    //   System.out.println("PLEASE PRESS B BUTTON TO SHOOT (IF IN TELEOP)");
    // }
    if (shooter.flywheelState == FlywheelState.CHARGED) {
      hopper.hopperOn();
      intake.indexerHopperL();
      System.out.println("SHOOTER STARTED!");

    }
    System.out.println(shooter.getFlywheelRPM());
    if (hopper.getHopperPos2()) { //&& hopper.getHopperPos1() == false
      timedStopCount++;
    }
    if (hopper.getHopperPos2() && timedStopCount > 100) { // && hopper.getHopperPos1() == false 
      hopper.hopperOff();
      intake.intakeOffMethod();
      timedStopCount = 0;
      shooter.flyweelOff();
      shooter.flywheelState = FlywheelState.OFF;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (shooter.flywheelState == FlywheelState.OFF) {
      System.out.println("HOPPER DEPLETED - SHOOTER STOPED!");
      return true;
    } else if (xbox.getLeftBumperPressed()) {
      System.out.println("SHOOTER MANUALY STOPED!");
      hopper.hopperOff();
      shooter.flyweelOff();
      intake.intakeOffMethod();
      return true;
    } else {
      return false;
    }
  }
}
