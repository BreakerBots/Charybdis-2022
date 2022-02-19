// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.actions;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climber;

public class ArmMoveTest extends CommandBase {
  /** Creates a new ArmMoveTest. */
  private Climber climb;
  private double time;
  private long cycleCount;
  private boolean isInGroup;
  private XboxController xbox;
  public ArmMoveTest(XboxController xboxArg, double secArg, boolean isGroupedArg, Climber climbArg) {
    isInGroup = isGroupedArg;
    time = secArg * 50;
    xbox = xboxArg;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double armspd = 0.5 * xbox.getRightY();
    cycleCount ++;
    System.out.println("ArmMoveTestExecute:" + armspd);
    climb.setManualArmSpd(0.1);
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
