// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.FlywheelState;

/** Hopper subsystem. */
public class Hopper extends SubsystemBase {
  public boolean hopperState;
  private WPI_TalonSRX hopperMotor;
  private DigitalInput slot1;
  private DigitalInput slot2;
  Intake intake;
  private long pauseCountA;
  private long pauseCountB;

  public Hopper(Intake intakeArg) {
    hopperMotor = new WPI_TalonSRX(Constants.HOPPER_ID);
    intake = intakeArg;
    slot1 = new DigitalInput(9);
    slot2 = new DigitalInput(8);
  }

  public void hopperOn() {
    hopperMotor.set(Constants.HOPPERSPEED);
    hopperState = true;
    // return hopperState = true;
  }

  public void hopperOff() {
    hopperMotor.set(0);
    hopperState = false;
  }

  public boolean slot1IsFull() {
    return !(slot1.get());

  }

  public boolean slot2IsFull() {
    return !(slot2.get());
  }

  /**
   * Returns hopper motor supply/input current.
   * 
   * @return Supply/input current in amps
   */
  public double getHopperSup() {
    return hopperMotor.getSupplyCurrent();
  }

  /**
   * Returns hopper motor stator/output current.
   * 
   * @return Stator/output current in amps
   */
  public double getHopperSta() {
    return hopperMotor.getStatorCurrent();
  }

  @Override
  public void periodic() {
    // System.out.println("hop 1: " + slot1IsFull());
    // System.out.println("hop 2: " + slot2IsFull());
    if (intake.intakeState) {
      if (slot1IsFull() && !slot2IsFull()) {
        hopperOn();
      } else if (!slot1IsFull() && slot2IsFull()) {
        pauseCountA++;
        if (pauseCountA > Constants.HOPPER_DELAY_CYCLES) {
          hopperOff();
          pauseCountA = 0;
        }
      } else if (!slot1IsFull() && !slot2IsFull()) {
        hopperOff();
      } else if (slot1IsFull() && slot2IsFull()) {
        pauseCountB++;
        if (pauseCountB > 25) {
          intake.deactivateIntake();
          hopperOff();
          pauseCountB = 0;
        }
      }
    }
  }
}
