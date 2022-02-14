// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.hal.DIOJNI;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Hopper subsystem. */
public class Hopper extends SubsystemBase {
  private long pauseCountA;
  public boolean hopperState;
  private WPI_TalonSRX hopperMotor;
  private DigitalInput slot1;
  private DigitalInput slot2;
  Intake intake;
  public Hopper(Intake intakeArg) {
    hopperMotor = new WPI_TalonSRX(Constants.HOPPER_ID);
    intake = intakeArg;
    slot1 = new DigitalInput(9);
    slot2 = new DigitalInput(8);
  }

  public void hopperOn() {
    hopperMotor.set(Constants.HOPPERSPEED);
    hopperState = true;
    //return hopperState = true;
  }

  public void hopperOff() {
    hopperMotor.set(0);
    hopperState = false;
  }

  public boolean getHopperPos1() {
    return slot1.get();
  }

  public boolean getHopperPos2() {
    return slot2.get();
  }



  @Override
  public void periodic() {
    // System.out.println("hop 1: " + getHopperPos1());
    // System.out.println("hop 2: " + getHopperPos2());
    if (intake.intakeState) {
      // if (getHopperPos1() && !getHopperPos2()) { 
      //   hopperOn();
      // }
      // } else if (!getHopperPos1() && getHopperPos2()) {
      //   pauseCountA++;
      //   if (pauseCountA >= Constants.HOPPER_DELAY_CYCLES) {
      //     hopperOff();
      //     pauseCountA = 0;
      //   }
      // } else if (!getHopperPos1() && !getHopperPos2()) {
      //   hopperOn();
      // } else if (getHopperPos1() && getHopperPos2()) {
      //   intake.intakeOffMethod();
      //   hopperOff();
      if (!getHopperPos1()) {
        hopperOn();
      }
      else {
        hopperOff();
      }
    
    }
    }
  }

 