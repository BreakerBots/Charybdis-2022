// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;


public class DriveWithJoystick extends CommandBase {

  private final Drive drive;
  private final XboxController xbox; 
  private final PowerDistribution pdp;
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
  public void initialize() {}

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
    if (pdp.getVoltage() < 8.5) {
      net *= 0.85;
    }
    prevNet = net;
    
    drive.teleopMove(net, turn); // Calculates speed and turn outputs
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
