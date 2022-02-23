// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.test;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BreakerMath;
import frc.robot.subsystems.DashboardControl;
import frc.robot.subsystems.Shooter;

public class FlywheelTest extends CommandBase {
  /** Creates a new FLywheelTest. */
  private Shooter shooter;
  private double flywheelLSupAvg;
  private double flywheelLStaAvg;
  private double flywheelRSupAvg;
  private double flywheelRStaAvg;
  private double flywheelStaTotalAvg;
  private double flywheelSupTotalAvg;
  private double time;
  private int cycleCount;
  private double speed;
  public FlywheelTest(double secArg, double speedArg, Shooter shooterArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    time = secArg * 50;
    speed = speedArg;
    shooter = shooterArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setManualFlywheelSpeed(speed);
    System.out.println("START FLYWHEEL TESTING!");
    flywheelLStaAvg = shooter.getLFlywheelSta();
    flywheelLSupAvg = shooter.getLFlywheelSup();
    flywheelRStaAvg = shooter.getRFlywheelSta();
    flywheelLSupAvg = shooter.getRFlywheelSup();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cycleCount ++;
    flywheelLStaAvg = BreakerMath.getAvg(flywheelLStaAvg, shooter.getLFlywheelSta(), cycleCount);
    flywheelLSupAvg = BreakerMath.getAvg(flywheelLSupAvg, shooter.getLFlywheelSup(), cycleCount);
    flywheelRStaAvg = BreakerMath.getAvg(flywheelRStaAvg, shooter.getRFlywheelSta(), cycleCount);
    flywheelRSupAvg = BreakerMath.getAvg(flywheelRSupAvg, shooter.getRFlywheelSup(), cycleCount);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    cycleCount = 0;
    flywheelStaTotalAvg = (flywheelLStaAvg + flywheelRStaAvg);
    flywheelSupTotalAvg = (flywheelLSupAvg + flywheelRSupAvg);
    DashboardControl.log(
      "AVERAGES: \n\n"
      + " LEFT FLYWHEEL STATOR: " + flywheelLStaAvg + " LEFT FLYWHEEL SUPPLY: " + flywheelLSupAvg +"\n"
      + " RIGHT FLYWHEEL STATOR:  " + flywheelRStaAvg + " RIGHT FLYWHEEL SUPPLY: " + flywheelRSupAvg + "\n"
      + " FLYWHEEL TOTAL AVERAGE STATOR: " + flywheelStaTotalAvg + " FLYWHEEL TOTAL AVERAGE SUPPLY: " + flywheelSupTotalAvg + "\n\n"
    );
    shooter.setManualFlywheelSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return cycleCount > time;
  }
}
