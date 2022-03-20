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
  private double lTargetTicks = 0;
  private double rTargetTicks = 0;
  private double lPrevTgtTicks = 0;
  private double rPrevTgtTicks = 0;
  private int lCycleCount = 0;
  private int rCycleCount = 0;

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
    lTargetTicks = climb.getLeftClimbTicks();
    rTargetTicks = climb.getRightClimbTicks();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double inputTicks = Math.round(Constants.WINCH_INPUT_MULTIPLIER * MathUtil.applyDeadband(xbox.getRightY(), 0.1));
    if (xbox.getLeftBumper() && !xbox.getRightBumper()) {
      lTargetTicks += inputTicks;
    } else if (!xbox.getLeftBumper() && xbox.getRightBumper()) {
      rTargetTicks += inputTicks;
    } else {
      lTargetTicks += inputTicks;
      rTargetTicks += inputTicks;
    }
    
    // if (!climb.climbisFullyExtendable) {
    //   lTargetTicks = MathUtil.clamp(lTargetTicks, -10, Constants.START_MAX_CLIMB_EXT);
    //   rTargetTicks = MathUtil.clamp(rTargetTicks, -10, Constants.START_MAX_CLIMB_EXT);
    // }

    double leftTicks = climb.getLeftClimbTicks();
    double rightTicks = climb.getRightClimbTicks();
  

    double lSpeed = climb.lClimbPID.calculate(leftTicks, lTargetTicks);
    double rSpeed = climb.rClimbPID.calculate(rightTicks, rTargetTicks);

    if (lTargetTicks == lPrevTgtTicks) {
      lCycleCount ++;
      if (lCycleCount > Constants.CLIMB_SPD_RESET_CYCLES) {
        lSpeed = 0;
      }
    } else {
      lCycleCount = 0;
    }
    if (rTargetTicks == rPrevTgtTicks) {
      rCycleCount ++;
      if (rCycleCount > Constants.CLIMB_SPD_RESET_CYCLES) {
        rSpeed = 0;
      }
    } else {
      rCycleCount = 0;
    }

    climb.moveLClimb(lSpeed);
    climb.moveRClimb(rSpeed);
    
    lPrevTgtTicks = lTargetTicks;
    rPrevTgtTicks = rTargetTicks;

    if (xbox.getYButtonPressed()) {
      climb.toggleClimbSol();
    }
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
