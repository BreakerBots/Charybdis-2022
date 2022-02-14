// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class MotorTest extends CommandBase {
  /** Creates a new MotorRotationTest. */
  Drive drive;
  double speed;
  double rotate;
  double l1StaTotal;
  double l2StaTotal;
  double l3StaTotal;
  double r1StaTotal;
  double r2StaTotal;
  double r3StaTotal;
  
  double l1SupTotal;
  double l2SupTotal;
  double l3SupTotal;
  double r1SupTotal;
  double r2SupTotal;
  double r3SupTotal;

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
    drive.teleopMove(speed, 0);
    // System.out.println(
    // "sta L1: " + drive.getStatorCurrent(Constants.L1_ID) + "  sta R1: " + drive.getStatorCurrent(Constants.R1_ID) + "\n" +
    // "sta L2: " + drive.getStatorCurrent(Constants.L2_ID) + "  sta R2: " + drive.getStatorCurrent(Constants.R2_ID) + "\n" +
    // "sta L3: " + drive.getStatorCurrent(Constants.L3_ID) + "  sta R3: " + drive.getStatorCurrent(Constants.R3_ID) + "\n\n" +

    // "sup L1: " + drive.getSupplyCurrent(Constants.L1_ID) + "  sup R1: " +drive.getSupplyCurrent(Constants.R1_ID) + "\n" +   
    // "sup L2: " + drive.getSupplyCurrent(Constants.L2_ID) + "  sup R2: " +drive.getSupplyCurrent(Constants.R2_ID) + "\n" +
    // "sup L3: " + drive.getSupplyCurrent(Constants.L3_ID) + "  sup R3: " +drive.getSupplyCurrent(Constants.R3_ID) + "\n\n\n"
    // );

    l1StaTotal = l1StaTotal + drive.getStatorCurrent(Constants.L1_ID);
    l2StaTotal = l2StaTotal + drive.getStatorCurrent(Constants.L2_ID);
    l3StaTotal = l3StaTotal + drive.getStatorCurrent(Constants.L3_ID);
    r1StaTotal = r1StaTotal + drive.getStatorCurrent(Constants.R1_ID);
    r2StaTotal = r2StaTotal + drive.getStatorCurrent(Constants.R2_ID);
    r3StaTotal = r3StaTotal + drive.getStatorCurrent(Constants.R3_ID);

    l1SupTotal = l1SupTotal + drive.getSupplyCurrent(Constants.L1_ID);
    l2SupTotal = l2SupTotal + drive.getSupplyCurrent(Constants.L2_ID);
    l3SupTotal = l3SupTotal + drive.getSupplyCurrent(Constants.L3_ID);
    r1SupTotal = r1SupTotal + drive.getSupplyCurrent(Constants.R1_ID);
    r2SupTotal = r2SupTotal + drive.getSupplyCurrent(Constants.R2_ID);
    r3SupTotal = r3SupTotal + drive.getSupplyCurrent(Constants.R3_ID);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("MOTOR TESTING END!");
    l1StaAvg = l1StaTotal / cycleCount;
    l2StaAvg = l2StaTotal / cycleCount;
    l3StaAvg = l3StaTotal / cycleCount;
    r1StaAvg = r1StaTotal / cycleCount;
    r2StaAvg = r2StaTotal / cycleCount;
    r3StaAvg = r3StaTotal / cycleCount;

    l1SupAvg = l1SupTotal / cycleCount;
    l2SupAvg = l2SupTotal / cycleCount;
    l3SupAvg = l3SupTotal / cycleCount;
    r1SupAvg = r1SupTotal / cycleCount;
    r2SupAvg = r2SupTotal / cycleCount;
    r3SupAvg = r3SupTotal / cycleCount;
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
    return Math.abs(drive.getLeftTicks()) >= (rotate * Constants.DRIVE_TICKS_PER_ROTATION);
  }
}
