// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;


public class DriveWithJoystick extends CommandBase {

  private final Drive drive;
  private final XboxController xbox; 
  private double prevNet;
  /** Creates a new DriveWithJoystick. */
  public DriveWithJoystick(Drive driveTrainArg, XboxController controlerArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    drive = driveTrainArg;
    xbox = controlerArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double back = xbox.getLeftTriggerAxis();
    double forward = xbox.getRightTriggerAxis();
    double turn = xbox.getLeftX();
    double net = forward - back;
    if (net != 0) {
      if (net > prevNet + 0.35) {
        net = prevNet + 0.35;
      } else if (net < prevNet - 0.35) {
        net = prevNet - 0.35;
      }
    }
    if (drive.pdp.getVoltage() < 8.5) {
      net *= 0.85;
    }
    prevNet = net;
    
    drive.driveTrainDiff.arcadeDrive(net, turn); // Calculates speed and turn outputs
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
