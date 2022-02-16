// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  private WPI_TalonFX climberL;
  private WPI_TalonFX climberR;
  private MotorControllerGroup climbMotors;
  private DoubleSolenoid climbSolL;
  private DoubleSolenoid climbSolR;
  public PIDController climbPID;
  private final double artClimbFeedForward = 0.3;
  enum ClimberState {
    RETRACTED,
    MOVING,
    EXTENDED
  }
  // 0 = retracted, 1 = extending/retracting, 2 = extended
  public ClimberState climbState;
  public boolean climbSolState; //true is extended
  public int climbSequenceTotal;
  public int climbSequenceProgress;
  public Climber() {
    climbPID = new PIDController(Constants.KP_CLIMB, Constants.KI_CLIMB, Constants.KD_CLIMB);
    climberL = new WPI_TalonFX(Constants.CLIMBER_L_ID);
    climberR = new WPI_TalonFX(Constants.CLIMBER_R_ID);
    climbMotors = new MotorControllerGroup(climberL, climberR);
    climbSolL = new DoubleSolenoid(Constants.PCM_ID, PneumaticsModuleType.CTREPCM,
    Constants.CLIMBSOL_L_FWD, Constants.CLIMBSOL_L_REV);
    climbSolR = new DoubleSolenoid(Constants.PCM_ID, PneumaticsModuleType.CTREPCM,
    Constants.CLIMBSOL_R_FWD, Constants.CLIMBSOL_R_REV);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
      if (getClimbTicks() < Constants.CLIMB_RET_THRESH) {
        climbState = ClimberState.RETRACTED;
      }
      else if (getClimbTicks() > Constants.CLIMB_EXT_THRESH) {
        climbState = ClimberState.EXTENDED;
      }
      else {
        climbState = ClimberState.MOVING;
      }

      if (climbSequenceProgress == climbSequenceTotal) {
        System.out.println("CLIMB SEQUENCE COMPLETE!");
        climbSequenceTotal = 0;
        climbSequenceProgress = 0;
      }
  }

  public void extendClimb(double climbSpeedArg) {
    climbMotors.set(climbSpeedArg + artClimbFeedForward);
  }

  public double getClimbTicks() {
    return climberL.getSelectedSensorPosition();
  }

  public void toggleClimbSol() {
    if (climbSolState == true) {
      climbSolL.set(Value.kForward);
      climbSolR.set(Value.kForward);
      climbSolState = false;
    }
    else {
      climbSolL.set(Value.kReverse);
      climbSolR.set(Value.kReverse);
      climbSolState = true;
    }
  }


  
}
