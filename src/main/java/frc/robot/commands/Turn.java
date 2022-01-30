// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class Turn extends CommandBase {
  /** Creates a new Turn. */
  Drive driveTrain;
  double tgtAngle;
  double radiusOfCurvature;
  double outerTickTgt;
  double tgtTickRatio;
  final double tgtSpeed = 0.5;

  public Turn(Drive driveTrainArg, double angleArg, double radiusArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveTrain = driveTrainArg;
    tgtAngle = angleArg;
    radiusOfCurvature = radiusArg;
    outerTickTgt = (tgtAngle/360)*(radiusOfCurvature*2*Math.PI);
    tgtTickRatio = (radiusOfCurvature-13)/(radiusOfCurvature+13);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double innerTicks = tgtAngle >= 0 ? driveTrain.getRightTicks() : driveTrain.getLeftTicks();
    double outerTicks =  tgtAngle >= 0 ? driveTrain.getLeftTicks() : driveTrain.getRightTicks();
    double curTickRatio = innerTicks/outerTicks;
    double tickRatioError = tgtTickRatio - curTickRatio;
    double innerMotorSpeed = tgtSpeed * tickRatioError;

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
