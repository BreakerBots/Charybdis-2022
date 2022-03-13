// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Robot.RobotMode;
import frc.robot.subsystems.Drive;

public class DriveWithJoystick extends CommandBase {

  private final Drive drive;
  private final XboxController xbox;
  private final PowerDistribution pdp;
  private int throttleCycles = 0;
  private double adjust = 1;
  private double prevNet;

  /** Creates a new DriveWithJoystick. */
  public DriveWithJoystick(XboxController controllerArg, PowerDistribution pdpArg, Drive driveTrainArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    drive = driveTrainArg;
    xbox = controllerArg;
    pdp = pdpArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turn = xbox.getLeftX();
    double net = xbox.getRightTriggerAxis() - xbox.getLeftTriggerAxis();

    if (net != 0) {
      if (net > prevNet + 0.35) {
        net = prevNet + 0.35;
      } else if (net < prevNet - 0.35) {
        net = prevNet - 0.35;
      }
    }
    // if (pdp.getVoltage() < 12) {
    // throttleCycles++;
    // if (throttleCycles < 200) {
    // adjust = MathUtil.clamp(0.9 - 0.15 * (12 - pdp.getVoltage()), adjust -
    // 0.0005, adjust + 0.0005);
    // turn *= adjust;
    // net *= adjust;
    // }
    // } else {
    // throttleCycles = 0;
    // adjust = 1;
    // }
    prevNet = net;
    if (Robot.robotMode != RobotMode.TEST) {
      drive.move(net, turn); // Calculates speed and turn outputs
    } else {
      drive.move(0, 0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
