// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoActionCommands;

import java.time.Month;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;

public class MotorTest extends CommandBase {
  /** Creates a new MotorRotationTest. */
  Drive drive;
  double speed;
  double rotate;
  double l1StaTotle;
  double l2StaTotle;
  double l3StaTotle;
  double r1StaTotle;
  double r2StaTotle;
  double r3StaTotle;
  
  double l1SupTotle;
  double l2SupTotle;
  double l3SupTotle;
  double r1SupTotle;
  double r2SupTotle;
  double r3SupTotle;

  double l1StaAvg;
  double l2StaAvg;
  double l3StaAvg;
  double r1StaAvg;
  double r2StaAvg;
  double r3StaAvg;

  double l1SupAvg;
  double l2SupAvg;
  double l3SupAvg;
  double r1SupAvg;
  double r2SupAvg;
  double r3SupAvg;

  private long cycleCount;
  public MotorTest(Drive driveArg, double speedArg, int rotationsArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    drive = driveArg;
    speed = speedArg;
    rotate = rotationsArg;
    addRequirements(drive);
  }

// Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("MOTOR TESTING START!");
    drive.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cycleCount ++;
    drive.move(speed, 0);
    // System.out.println(
    // "sta L1: " + drive.l1.getStatorCurrent() + "  sta R1: " + drive.r1.getStatorCurrent() + "\n" +
    // "sta L2: " + drive.l2.getStatorCurrent() + "  sta R2: " + drive.r2.getStatorCurrent() + "\n" +
    // "sta L3: " + drive.l3.getStatorCurrent() + "  sta R3: " + drive.r3.getStatorCurrent() + "\n\n" +

    // "sup L1: " + drive.l1.getSupplyCurrent() + "  sup R1: " +drive.r1.getSupplyCurrent() + "\n" +   
    // "sup L2: " + drive.l2.getSupplyCurrent() + "  sup R2: " +drive.r2.getSupplyCurrent() + "\n" +
    // "sup L3: " + drive.l3.getSupplyCurrent() + "  sup R3: " +drive.r3.getSupplyCurrent() + "\n\n\n"
    // );

    l1StaTotle = l1StaTotle + drive.l1.getStatorCurrent();
    l2StaTotle = l2StaTotle + drive.l1.getStatorCurrent();
    l3StaTotle = l3StaTotle + drive.l1.getStatorCurrent();
    r1StaTotle = r1StaTotle + drive.l1.getStatorCurrent();
    r2StaTotle = r2StaTotle + drive.l1.getStatorCurrent();
    r3StaTotle = r3StaTotle + drive.l1.getStatorCurrent();

    l1SupTotle = l1SupTotle + drive.l1.getSupplyCurrent();
    l2SupTotle = l2SupTotle + drive.l1.getSupplyCurrent();
    l3SupTotle = l3SupTotle + drive.l1.getSupplyCurrent();
    r1SupTotle = r1SupTotle + drive.l1.getSupplyCurrent();
    r2SupTotle = r2SupTotle + drive.l1.getSupplyCurrent();
    r3SupTotle = r3SupTotle + drive.l1.getSupplyCurrent();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("MOTOR TESTING END!");
    l1StaAvg = l1StaTotle / cycleCount;
    l2StaAvg = l2StaTotle / cycleCount;
    l3StaAvg = l3StaTotle / cycleCount;
    r1StaAvg = r1StaTotle / cycleCount;
    r2StaAvg = r2StaTotle / cycleCount;
    r3StaAvg = r3StaTotle / cycleCount;

    l1SupAvg = l1SupTotle / cycleCount;
    l2SupAvg = l2SupTotle / cycleCount;
    l3SupAvg = l3SupTotle / cycleCount;
    r1SupAvg = r1SupTotle / cycleCount;
    r2SupAvg = r2SupTotle / cycleCount;
    r3SupAvg = r3SupTotle / cycleCount;
    System.out.println(
      "\n" + "AVG RESULTS: " + "\n" +
    "sta avg L1: " + l1StaAvg + "  sta avg R1: " + r1StaAvg + "\n" +
    "sta avg L2: " + l2StaAvg + "  sta avg R2: " + r2StaAvg + "\n" +
    "sta avg L3: " + l3StaAvg + "  sta avg R3: " + r3StaAvg + "\n\n" +

    "sup avg L1: " + l1SupAvg + "  sup avg R1: " + r1SupAvg + "\n" +   
    "sup avg L2: " + l2SupAvg + "  sup avg R2: " + r2SupAvg + "\n" +
    "sup avg L3: " + l3SupAvg + "  sup avg R3: " + r3SupAvg + "\n\n\n"
    );



  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(drive.getLeftTicks()) >= (rotate * Constants.TICKS_PER_ROTATION);
  }
}
