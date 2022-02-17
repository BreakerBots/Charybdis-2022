// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climber;

public class ArmMoveTest extends InstantCommand {
  /** Creates a new ArmMoveTest. */
  private double armSpd;
  private WPI_TalonFX climbMotor;
  public ArmMoveTest(double value, WPI_TalonFX motorArg) {
    armSpd = value;
    climbMotor = motorArg;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climbMotor.set(armSpd);
  }
}
