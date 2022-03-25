// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;


import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DashboardControl;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.FlywheelState;

/**
 * Shoots all the balls in the hopper
 */
public class ShootAll extends CommandBase {
  Shooter shooter;
  XboxController xbox;
  Hopper hopper;
  Intake intake;
  private long cycleCount;
  private long timedStopCount;
  private long timedStartCount;
  private boolean startWithTwoCargo;

  /**
   * Creates a new ShootAll.
   * 
   * @param shooterArg    Shooter subsystem from RobotContainer.
   * @param hopperArg     Hopper subsystem from RobotContainer.
   * @param controllerArg Xbox controller from RobotContainer.
   */
  public ShootAll(Shooter shooterArg, Hopper hopperArg, XboxController controllerArg, Intake intakeArg) {
    shooter = shooterArg;
    xbox = controllerArg;
    hopper = hopperArg;
    intake = intakeArg;
    addRequirements(shooter);
    addRequirements(hopper);
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timedStopCount = 0;
    DashboardControl.log("SHOOTALL INITIALIZED");
    if (hopper.bothSlotsAreFull()) {
      startWithTwoCargo = true;
    } else {
      startWithTwoCargo = false;
    } 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // GOTTA REWRITE THIS STUFF!!!
    cycleCount++;
    if (shooter.getFlywheelState() == FlywheelState.CHARGED && shooter.flywheelPIDAtSetpoint() /* && !startWithTwoCargo */) {
      shooter.isShooting = true;
      hopper.activateShooterHopper();
      intake.toggleHopperFeed();
      DashboardControl.log("SHOOTER STARTED!");
    }
    if (hopper.bothSlotsAreEmpty()) {
      if (timedStopCount > 75) {
        hopper.deactivateHopper();
        intake.deactivateIntake();
        timedStopCount = 0;
        shooter.setOff();
        shooter.setFlywheelState(FlywheelState.OFF);
      } else {
        timedStopCount++;
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.resetFlywheelPID();
    cycleCount = 0;
    shooter.isShooting = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (shooter.getFlywheelState() == FlywheelState.OFF) {
      DashboardControl.log("HOPPER DEPLETED - SHOOTER STOPPED!");
      return true;
    } else if (xbox.getStartButtonPressed()) {
      DashboardControl.log("SHOOTER MANUALLY STOPPED!");
      hopper.deactivateHopper();
      shooter.setOff();
      intake.deactivateIntake();
      return true;
    } else if (cycleCount > 400) {
      DashboardControl.log("SHOOTER TIMED OUT!");
      hopper.deactivateHopper();
      shooter.setOff();
      intake.deactivateIntake();
      return true;
    } else {
      return false;
    }
  }
}
