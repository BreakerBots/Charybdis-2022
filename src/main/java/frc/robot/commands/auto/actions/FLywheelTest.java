// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BreakerMath;
import frc.robot.subsystems.Shooter;

public class FLywheelTest extends CommandBase {
  /** Creates a new FLywheelTest. */
  private Shooter shooter;
  private double flywheelLSupAvg;
  private double flywheelLStaAvg;
  private double flywheelRSupAvg;
  private double flywheelRStaAvg;
  private double flywheelStaTotalAvg;
  private double flywheelSupTotalAvg;
  private double time;
  private long cycleCount;
  private double speed;
  public FLywheelTest(double secArg, double speedArg, Shooter shooterArg) {
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
    flywheelLStaAvg = BreakerMath.rollingAvg(flywheelLStaAvg, shooter.getLFlywheelSta());
    flywheelLSupAvg = BreakerMath.rollingAvg(flywheelLSupAvg, shooter.getLFlywheelSup());
    flywheelRStaAvg = BreakerMath.rollingAvg(flywheelRStaAvg, shooter.getRFlywheelSta());
    flywheelRSupAvg = BreakerMath.rollingAvg(flywheelRSupAvg, shooter.getRFlywheelSup());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    cycleCount = 0;
    flywheelStaTotalAvg = (flywheelLStaAvg + flywheelRStaAvg) / 2.0;
    flywheelSupTotalAvg = (flywheelLSupAvg + flywheelRSupAvg) / 2.0;
    System.out.println(
      "AVERAGES: \n\n"
      + " LEFT FLYWHEEL STATOR: " + flywheelLStaAvg + " LEFT FLYWHEEL SUPPLY: " + "\n"
      + " RIGHT FLYWHEEL STATOR:  " + flywheelRStaAvg + " RIGHT FLYWHEEL SUPPLY: " + "\n"
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
