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
  public int shooterMode;
  public SimpleMotorFeedforward flywheelFF;
  public PIDController flywheelPID;
  public FlywheelState flywheelState;
  private static double prevTicks = 0;
  // public boolean flyweelState;
  public boolean shooterPos;
  private WPI_TalonFX shooterL;
  private WPI_TalonFX shooterR;
  public double flyTgtSpdPrec = Constants.UP_SHOOTERSPEED;
  public MotorControllerGroup flywheel;
  public boolean autoShoot;
  DoubleSolenoid shooterSol;
  Hopper hopper;
  public Shooter(Hopper hopperArg) {
    // flywheelFF = new SimpleMotorFeedforward(Constants.FLYWHEEL_KS, Constants.FLYWHEEL_KV);
    flywheelPID = new PIDController(Constants.FLYWHEEL_KP, Constants.FLYWHEEL_KI, Constants.FLYWHEEL_KD);
    hopper = hopperArg;
    flywheelPID.setTolerance(20);
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
    return Math.abs((shooterL.getSelectedSensorVelocity()/10));
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
    return Constants.FLYWHEEL_MAX_SPEED * flyTgtSpdPrec;
  }

  public void periodic() {
    shooterMode = shooterMode % 3;
    switch (shooterMode) {
      case 0: if (shooterPos) {shooterDown();}
              flyTgtSpdPrec = Constants.UP_SHOOTERSPEED;   
      break;
      case 1: if (!shooterPos) {shooterUp();}
              flyTgtSpdPrec = Constants.LOW_SHOOTERSPEED;
      break;
      case 2: flyTgtSpdPrec = Constants.LAUNCH_SHOOTERSPEED;
      break;
      default: shooterDown();
    }
    // System.out.println("RPM: " + getFlywheelRPM());
    // System.out.println("AT SETPOINT: " + flywheelPID.atSetpoint());
    // if ((hopper.getHopperPos1() || hopper.getHopperPos2()) && (flywheelState == FlywheelState.OFF || flywheelState == Flywheelstate.IDLE) {
    //   flywheel.set(Constants.FLYWHEEL_IDLE_SPEED);
    //    flywheelState = FlywheelState.IDLE
    // }
    if (flywheelState == FlywheelState.CHARGING || flywheelState == FlywheelState.CHARGED) {
     double flySpd;
      flySpd = (flywheelPID.calculate(getFlywheelRPM(), getFlywheelTargetSpeed()));
      flywheel.set(flySpd);
    }
  }
}