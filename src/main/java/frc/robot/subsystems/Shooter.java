// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  public boolean flyweelState;
  public boolean shooterPos;
  private WPI_TalonFX shooterL;
  private WPI_TalonFX shooterR;
  DoubleSolenoid shooterSol;
  public Shooter() {
    shooterL = new WPI_TalonFX(Constants.SHOOTER_L_ID);
    shooterR = new WPI_TalonFX(Constants.SHOOTER_R_ID);
    shooterSol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 
                Constants.SHOOTERSOL_FWD, Constants.SHOOTERSOL_REV);
  }

  @Override
  public void periodic() {
    
  }

  public boolean flyweelOn() {
    shooterL.set(Constants.SHOOTERSPEED);
    shooterR.set(Constants.SHOOTERSPEED);
    return flyweelState = true;
  }

  public boolean flyweelOff() {
    shooterL.set(0);
    shooterR.set(0);
    return flyweelState = false;
  }

  public boolean shooterUp() {
    shooterSol.set(Value.kForward);
    return shooterPos = true;
  }

  public boolean shooterDown() {
    shooterSol.set(Value.kReverse);
    return shooterPos = false;
  }

}
