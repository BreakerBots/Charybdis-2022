// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.actions;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class ManuallyMoveClimb extends CommandBase {
  /** Creates a new ManualExtendClimb. */
  private Climber climb;
  private XboxController xbox;
  public ManuallyMoveClimb(Climber climbArg, XboxController controllerArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    climb = climbArg;
    addRequirements(climb);
    xbox = controllerArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  private double driveClimb (double speedArg, double ticks) {
    boolean maxRetract = ticks <= 10;
    boolean maxExtend = ticks >= Constants.CLIMB_FULL_EXT_DIST;
    boolean inTransit = !maxExtend && !maxRetract;
    boolean extending = speedArg >= 0;
    if ((maxRetract && extending) || (maxExtend && !extending) || inTransit) {
      return speedArg;
    } else {
      return 0;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = xbox.getRightY();
    double leftTicks = climb.getLeftClimbTicks();
    double rightTicks = climb.getRightClimbTicks();
    double catchup = Constants.kCatchup * (leftTicks - rightTicks) * (speed > 0 ? 1 : -1);
    climb.moveLClimb(driveClimb(speed, climb.getLeftClimbTicks()));
    climb.moveRClimb(driveClimb(speed+catchup, climb.getRightClimbTicks()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climb.moveClimb(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return xbox.getStartButtonPressed();
  }
}