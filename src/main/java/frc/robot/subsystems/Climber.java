// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotConfig;

/** Climber subsystem for robot. */
public class Climber extends SubsystemBase{
  // Extend/retracts climbing arms.
  private WPI_TalonFX climberL;
  private WPI_TalonFX climberR;
  // Rotates the climb arms.
  private DoubleSolenoid climbSol;
  // Makes sure we get to desired position
  public PIDController lClimbPID;
  public PIDController rClimbPID;
  private boolean climbing;
  // 0 = retracted, 1 = extending/retracting, 2 = extended
  public boolean climbSolRetracted = true; // true is extended
  public boolean climbisFullyExtendable = false;
  private int fullyExtendableLoopCheckState = 0;

  public Climber() {
    setName("Climber");
    lClimbPID = new PIDController(Constants.KP_CLIMB, Constants.KI_CLIMB, Constants.KD_CLIMB);
    rClimbPID = new PIDController(Constants.KP_CLIMB, Constants.KI_CLIMB, Constants.KD_CLIMB);
    climberL = new WPI_TalonFX(Constants.CLIMBER_L_ID);
    climberR = new WPI_TalonFX(Constants.CLIMBER_R_ID);
    climberR.setInverted(TalonFXInvertType.Clockwise);
    climberL.setInverted(TalonFXInvertType.CounterClockwise);
    climbSol = new DoubleSolenoid(Constants.PCM_ID,
    PneumaticsModuleType.CTREPCM,
    Constants.CLIMBSOL_FWD, Constants.CLIMBSOL_REV);
    climberL.setSelectedSensorPosition(0);
    climberR.setSelectedSensorPosition(0);
    resetClimbEncoders();
  }

  @Override
  public void periodic() {
    //System.out.println("LClimb Ticks: " + climberL.getSelectedSensorPosition() + " RClimb Ticks: " + climberR.getSelectedSensorPosition());
    //DashboardControl.log(log_str);
    // This method will be called once per scheduler run
    //addChild("Winching Motors", climbMotors);
    addChild("Left Climb PID", lClimbPID);
    addChild("Right Climb PID", rClimbPID);
    addChild("Pistons", climbSol);
    climbisFullyExtendableCheckLoop();
  }

  public void moveClimb(double climbSpeedArg) {
    climberL.set(climbSpeedArg);
    climberR.set(climbSpeedArg);
    // climbMotors.set(climbSpeedArg + artClimbFeedForward);
  }

  public void moveLClimb(double climbSpeedArg) {
    climberL.set(climbSpeedArg);
  }

  public void moveRClimb(double climbSpeedArg) {
    climberR.set(climbSpeedArg);
  }

  public void setBrakeMode(boolean mode) {
    RobotConfig.setBrakeMode(mode, climberL, climberR);
  }

  public double getLeftClimbTicks() {
    return climberL.getSelectedSensorPosition();
  }

  public double getRightClimbTicks() {
    return climberR.getSelectedSensorPosition();
  }

  public void toggleClimbSol() {
    climbSol.set(climbSolRetracted ? Value.kForward : Value.kReverse);
    climbSolRetracted = !climbSolRetracted;
  }

  public void resetClimbEncoders() {
    climberL.setSelectedSensorPosition(0);
    climberR.setSelectedSensorPosition(0);
  }

  public boolean isClimbing() {
    return climbing;
  }

  public void setIsClimbing(boolean val) {
    climbing = val;
  }

  private void climbisFullyExtendableCheckLoop() {
    switch (fullyExtendableLoopCheckState) {
      default:
      case 0: 
        if (!climbSolRetracted) {
          fullyExtendableLoopCheckState ++;
        }
        break;
      case 1:
        if (climbSolRetracted) {
          fullyExtendableLoopCheckState ++;
        }
        break;
      case 2:
        climbisFullyExtendable = true;
        break;
    }
  }
}
