// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Climber subsystem for robot. */
public class Climber extends SubsystemBase {
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
  public boolean climbSolRetracted; // true is extended

  public Climber() {
    setName("Climber");
    lClimbPID = new PIDController(Constants.KP_CLIMB, Constants.KI_CLIMB, Constants.KD_CLIMB);
    rClimbPID = new PIDController(Constants.KP_CLIMB, Constants.KI_CLIMB, Constants.KD_CLIMB);
    climberL = new WPI_TalonFX(Constants.CLIMBER_L_ID);
    climberR = new WPI_TalonFX(Constants.CLIMBER_R_ID);
    climberR.setInverted(true);
    climberL.setInverted(true);
    climbSol = new DoubleSolenoid(Constants.PCM_ID,
    PneumaticsModuleType.CTREPCM,
    Constants.CLIMBSOL_FWD, Constants.CLIMBSOL_REV);
    resetClimbEncoders();
  }

  @Override
  public void periodic() {
    String log_str = "LClimb Ticks: " + climberL.getSelectedSensorPosition() + " RClimb Ticks: " + climberR.getSelectedSensorPosition();
    DashboardControl.log(log_str);
    // This method will be called once per scheduler run
    //addChild("Winching Motors", climbMotors);
    addChild("Left Climb PID", lClimbPID);
    addChild("Right Climb PID", rClimbPID);
    addChild("Pistons", climbSol);
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
  /**
   * Returns rounded value for the precent the climber is currently extended or
   * retracted relative to its max value
   */
  public double getClimbExtPrct() {
    return Math.round((((getLeftClimbTicks() + getRightClimbTicks()) / 2) * 100) / Constants.CLIMB_EXT_THRESH);
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
}
