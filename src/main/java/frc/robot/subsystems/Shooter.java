// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private static double prevTicks = 0;
  public boolean flyweelState;
  public boolean shooterPos;
  private WPI_TalonFX shooterL;
  private WPI_TalonFX shooterR;
  private MotorControllerGroup flywheel;
  public boolean autoShoot;
  DoubleSolenoid shooterSol;
  public Shooter() {
    shooterL = new WPI_TalonFX(Constants.SHOOTER_L_ID);
    shooterR = new WPI_TalonFX(Constants.SHOOTER_R_ID);
    flywheel = new MotorControllerGroup(shooterL, shooterR);
    shooterSol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 
                Constants.SHOOTERSOL_FWD, Constants.SHOOTERSOL_REV);
  }

  @Override
  public void periodic() {
    
  }
  /** Turns Flywheel On */
  public boolean flyweelOn() {
    flywheel.set(Constants.SHOOTERSPEED);
    return flyweelState = true;
  }
   /** Turns Flywheel Off */
  public boolean flyweelOff() {
    flywheel.set(0);
    return flyweelState = false;
  }
  /** Returns the RPM of the flywheel's Motors */
  public double getFlywheelRPM() {
    double curTicks;
    double tickDiff;
    double propOfRotation;
    double rpm;
    curTicks = shooterL.getSelectedSensorPosition();
    tickDiff = curTicks - prevTicks;
    propOfRotation = tickDiff / 2048;
    rpm = (propOfRotation * 50) * 60;
    prevTicks = curTicks;
    return rpm;
  }
  /** Brings shooter to higher fireing angle */
  public boolean shooterUp() {
    shooterSol.set(Value.kForward);
    return shooterPos = true;
  }
  /** Brings shooter to lower fireing angle */
  public boolean shooterDown() {
    shooterSol.set(Value.kReverse);
    return shooterPos = false;
  }

}
