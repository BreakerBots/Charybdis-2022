// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoActionCommands;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class MotorTest extends CommandBase {
  /** Creates a new MotorRotationTest. */
  Drive drive;
  double speed;
  double rotate;
  public MotorTest(Drive driveArg, double rotationsArg, double speedArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    drive = driveArg;
    speed = speedArg;
    rotate = rotationsArg;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("MOTOR TESTING BEGIN!");
    drive.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.move(speed, 0);
    System.out.println(
    "sta L1: " + drive.l1.getStatorCurrent() + "sta R1: " + drive.r1.getStatorCurrent() + "\n\n" +
    "sup L1: " + drive.l1.getSupplyCurrent() + "sup R1: " +drive.r1.getSupplyCurrent() + "\n\n" + 
    "sta L2: " + drive.l2.getStatorCurrent() + "sta R2: " + drive.r2.getStatorCurrent() + "\n\n" +
    "sup L2: " + drive.l2.getSupplyCurrent() + "sup R2: " +drive.r2.getSupplyCurrent() + "\n\n" +
    "sta L3: " + drive.l3.getStatorCurrent() + "sta R3: " + drive.r3.getStatorCurrent() + "\n\n" +
    "sup L3: " + drive.l3.getSupplyCurrent() + "sup R3: " +drive.r3.getSupplyCurrent() + "\n\n\n"
    );
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("MOTOR TESTING END!");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drive.getLeftTicks() >= (rotate * Constants.TICKS_PER_ROTATION);
  }
}
