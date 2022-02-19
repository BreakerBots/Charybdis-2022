// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BreakerMath;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;

public class IntakeHopperIndexerTest extends CommandBase {
  private Hopper hopper;
  private Intake intake;
  private double time;
  private long cycleCount;
  private double intakeSupAvg;
  private double indexLSupAvg;
  private double indexRSupAvg;
  private double hopperSupAvg;
  private double intakeStaAvg;
  private double indexLStaAvg;
  private double indexRStaAvg;
  private double hopperStaAvg;
  private double hopperSupMax = -1;
  private double hopperStaMax = -1;

  /** Creates a new IntakeHopperIndexerTest. */
  // Put time first?
  public IntakeHopperIndexerTest(double secArg, Hopper hopperArg, Intake intakeArg) {
    hopper = hopperArg;
    intake = intakeArg;
    time = secArg * 50;
    addRequirements(hopper, intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.print("HOPPER AND INTAKE SYSTEM TESTING START!\n");
    intakeSupAvg = intake.getIntakeSup();
    indexLSupAvg = intake.getIndexerLSup();
    indexRSupAvg = intake.getIndexerRSup();
    hopperSupAvg = hopper.getHopperSup();
    intakeStaAvg = intake.getIntakeSta();
    indexLStaAvg = intake.getIndexerLSup();
    indexRStaAvg = intake.getIndexerRSup();
    hopperStaAvg = hopper.getHopperSta();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cycleCount++;
    hopper.activateHopper();
    intake.activateIntake();
    
    double hopperSup = hopper.getHopperSup();
    double hopperSta = hopper.getHopperSta();

    SmartDashboard.putNumber("HOPPER SUP", hopperSup);
    SmartDashboard.putNumber("HOPPER STA", hopperSup);

    if (hopperSup > hopperSupMax) {
      hopperSupMax = hopperSup;
    }
    if (hopperSta > hopperStaMax) {
      hopperStaMax = hopperSta;
    }

    hopperStaAvg = BreakerMath.rollingAvg(hopperStaAvg, hopperSta);
    hopperSupAvg = BreakerMath.rollingAvg(hopperSupAvg, hopperSup);
    indexLStaAvg = BreakerMath.rollingAvg(indexLStaAvg, intake.getIndexerLSta());
    indexLSupAvg = BreakerMath.rollingAvg(indexLSupAvg, intake.getIndexerLSup());
    indexRStaAvg = BreakerMath.rollingAvg(indexRStaAvg, intake.getIndexerRSta());
    indexRSupAvg = BreakerMath.rollingAvg(indexRSupAvg, intake.getIndexerRSup());
    intakeStaAvg = BreakerMath.rollingAvg(intakeStaAvg, intake.getIntakeSta());
    intakeSupAvg = BreakerMath.rollingAvg(intakeSupAvg, intake.getIntakeSup());

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println(
      "AVERAGES: \n\n" 
      + " INTAKE STATOR: " + intakeStaAvg + " INTAKE SUPPLY: " + intakeSupAvg + "\n"
      + " LEFT INDEXER STATOR: " + indexLStaAvg + " LEFT INDEXER SUPPLY: " + indexLSupAvg + "\n"
      + " RIGHT INDEXER STATOR: " + indexRStaAvg + " RIGHT INDEXER SUPPLY: " + indexRSupAvg + "\n"
      + " HOPPER STATOR: " + hopperStaAvg + " HOPPER SUPPLY: " + hopperSupAvg + "\n\n"
    );
    System.out.println("HOPPER STA MAX: " + hopperStaMax + "\n");
    System.out.println("HOPPER SUP MAX: " + hopperSupMax + "\n\n");
    hopper.deactivateHopper();
    intake.deactivateIntake();
    cycleCount = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return cycleCount > time;
  }
}
