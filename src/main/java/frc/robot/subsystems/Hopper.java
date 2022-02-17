// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Hopper subsystem. */
public class Hopper extends SubsystemBase {
  private boolean hopperIsRunning;
  private WPI_TalonSRX hopperMotor;
  private DigitalInput slot1;
  private DigitalInput slot2;
  private Intake intake;
  private long pauseCountA;
  private long pauseCountB;

  public Hopper(Intake intakeArg) {
    hopperMotor = new WPI_TalonSRX(Constants.HOPPER_ID);
    intake = intakeArg;
    slot1 = new DigitalInput(Constants.SLOT_1_CHANNEL);
    slot2 = new DigitalInput(Constants.SLOT_2_CHANNEL);
  }

  /** Turns on hopper */
  public void activateHopper() {
    hopperMotor.set(Constants.HOPPERSPEED);
    hopperIsRunning = true;
  }

  /** Turns off hopper */
  public void deactivateHopper() {
    hopperMotor.set(0);
    hopperIsRunning = false;
  }

  public boolean hopperIsRunning() {
    return hopperIsRunning;
  }

  /**
   * Top slot of hopper.
   * 
   * @return true if full, false if empty.
   */
  public boolean slot1IsFull() {
    return !(slot1.get());

  }

  /**
   * Bottom slot of hopper.
   * 
   * @return true if full, false if empty.
   */
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

  public void hopperLogicLoop() {
    if (intake.intakeIsRunning()) {
      if (slot1IsFull() && !slot2IsFull()) { // Only bottom is full
        activateHopper(); // Turns on hopper
      } else if (!slot1IsFull() && slot2IsFull()) { // Only top is full
        pauseCountA++;
        if (pauseCountA > Constants.HOPPER_DELAY_CYCLES) { // Waits to turn off hopper
          deactivateHopper();
          pauseCountA = 0;
        }
      } else if (!slot1IsFull() && !slot2IsFull()) { // Both ar empty
        deactivateHopper();
      } else if (slot1IsFull() && slot2IsFull()) { // Both are full
        pauseCountB++;
        if (pauseCountB > 25) { // Waits to turn off hopper and intake.
          intake.deactivateIntake();
          deactivateHopper();
          pauseCountB = 0;
        }
      }
    }
  }

  @Override
  public void periodic() {
    hopperLogicLoop();
  }
}
