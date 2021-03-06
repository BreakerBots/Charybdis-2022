// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DashboardControl;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.FlywheelState;

/** Spins the shooter's flywheel */
public class ChargeFlywheel extends CommandBase {
  private Shooter shooter;
  private XboxController xbox;
  private double cycleCount;
  private double prevOverShoot;

  /**
   * Creates a new ChargeFlywheel.
   * 
   * @param shooterArg    Shooter subsystem from RobotContainer.
   * @param controllerArg Xbox controller from RobotContainer.
   */
  public ChargeFlywheel(Shooter shooterArg, XboxController controllerArg) {
    shooter = shooterArg;
    xbox = controllerArg;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    cycleCount = 0;
    prevOverShoot = 0;
    shooter.resetFlywheelPID();
    DashboardControl.log("CHARGING FLYWHEEL");
    if (shooter.getFlywheelState() == FlywheelState.IDLE || shooter.getFlywheelState() == FlywheelState.OFF) {
      shooter.chargeFlywheel();
    }
  //  if (shooter.flywheelPIDAtSetpoint()) {
  //     System.out.println("Charge time: " + (cycleCount / 50) + " Highest Overshoot amt: " + prevOverShoot);
  //  }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cycleCount ++;
    if (shooter.flywheelPID.getPositionError() < prevOverShoot) {
      prevOverShoot = shooter.flywheelPID.getPositionError();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Charge time: " + (cycleCount / 50) + " Highest Overshoot amt: " + prevOverShoot);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (shooter.flywheelPIDAtSetpoint()) {
      DashboardControl.log("FLYWHEEL CHARGED!");
      shooter.setFlywheelState(FlywheelState.CHARGED);
      return true;
    } else if (xbox.getStartButtonPressed()) {
      shooter.setOff();
      shooter.resetFlywheelPID();
      DashboardControl.log("FLYWHEEL MANUALLY STOPPED!");
      return true;
    } else if (cycleCount > 9999999) { //500
      shooter.setOff();
      shooter.resetFlywheelPID();
      DashboardControl.log("FLYWHEEL CHARGING TIMED OUT!");
      return true;
    } else {
      return false;
    }
    //return false;
  }
}
