// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.actions;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class ManuallyMoveClimb extends CommandBase {
  private Climber climb;
  private XboxController xbox;
  private double targetTicks = 0;

  public ManuallyMoveClimb(Climber climbArg, XboxController controllerArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    climb = climbArg;
    addRequirements(climb);
    xbox = controllerArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climb.lClimbPID.reset();
    climb.rClimbPID.reset();
    climb.resetClimbEncoders();
    targetTicks = 0;
  }

  private double driveClimb(double speedArg, double ticks) {
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
    double inputTicks = Math.round(-300 * MathUtil.applyDeadband(xbox.getRightY(), 0.05));
    targetTicks += inputTicks;
    targetTicks = MathUtil.clamp(targetTicks, 0, Constants.CLIMB_FULL_EXT_DIST);
    SmartDashboard.putNumber("TargetTicks", targetTicks);
    double leftTicks = climb.getLeftClimbTicks();
    double rightTicks = climb.getRightClimbTicks();
    double lSpeed = climb.lClimbPID.calculate(leftTicks, targetTicks);
    double rSpeed = climb.rClimbPID.calculate(rightTicks, targetTicks);
    climb.moveLClimb(driveClimb(lSpeed, climb.getLeftClimbTicks()));
    climb.moveRClimb(driveClimb(rSpeed, climb.getRightClimbTicks()));
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
