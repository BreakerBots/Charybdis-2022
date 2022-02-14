// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.FlywheelState;

public class Shooter extends SubsystemBase {
  public SimpleMotorFeedforward flywheelFF;
  public PIDController flywheelPID;
  public FlywheelState flywheelState;
  private static double prevTicks = 0;
  // public boolean flyweelState;
  public boolean shooterPos;
  private WPI_TalonFX shooterL;
  private WPI_TalonFX shooterR;
  public MotorControllerGroup flywheel;
  public boolean autoShoot;
  DoubleSolenoid shooterSol;
  Hopper hopper;
  public Shooter(Hopper hopperArg) {
    // flywheelFF = new SimpleMotorFeedforward(Constants.FLYWHEEL_KS, Constants.FLYWHEEL_KV);
    flywheelPID = new PIDController(Constants.FLYWHEEL_KP, Constants.FLYWHEEL_KI, Constants.FLYWHEEL_KD);
    hopper = hopperArg;
    flywheelPID.setTolerance(100);
    shooterL = new WPI_TalonFX(Constants.SHOOTER_L_ID);
    shooterR = new WPI_TalonFX(Constants.SHOOTER_R_ID);
    shooterL.setInverted(true);
    flywheel = new MotorControllerGroup(shooterL, shooterR);
    shooterSol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 
                Constants.SHOOTERSOL_FWD, Constants.SHOOTERSOL_REV);
  }

  
  /** Turns Flywheel On */
  public void flyweelFullOn() {
    flywheelState = FlywheelState.CHARGING;
  }

   /** Turns Flywheel Off */
  public void flyweelOff() {
    flywheel.set(0);
    flywheelState = FlywheelState.OFF;
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

  public double getFlywheelTargetSpeed() {
    return Constants.FLYWHEEL_MAX_SPEED * Constants.FLYWHEEL_TAR_SPEED_PREC;
  }

  public void periodic() {
    // flywheel.set(1);
    // System.out.println("RPM: " + getFlywheelRPM());
    if ((hopper.getHopperPos1() || hopper.getHopperPos2()) && flywheelState == FlywheelState.OFF) {
      flywheel.set(Constants.FLYWHEEL_IDLE_SPEED);
    }
    else if (flywheelState == FlywheelState.CHARGING || flywheelState == FlywheelState.CHARGED) {
     double flySpd;
      flySpd = (flywheelPID.calculate(getFlywheelRPM(), getFlywheelTargetSpeed()));
      flySpd = MathUtil.clamp(flySpd, 0, 1);
      flywheel.set(flySpd);
    }
  }
}