// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.actions;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climber;

public class ArmMoveTest extends CommandBase {
  /** Creates a new ArmMoveTest. */
  private double armSpd;
  private Climber climb;
  private double time;
  private long cycleCount;
  private boolean isInGroup;
  public ArmMoveTest(double speedArg, double secArg, boolean isGroupedArg, Climber climbArg) {
    armSpd = speedArg;
    isInGroup = isGroupedArg;
    time = secArg * 50;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    cycleCount ++;
    climb.setManualArmSpd(armSpd);
  }

  @Override
  public void end(boolean interrupted) {
    if (!isInGroup) {
      climb.setManualArmSpd(0);
    }
  }

  @Override
  public boolean isFinished() {
    return cycleCount > time;
  }
}
