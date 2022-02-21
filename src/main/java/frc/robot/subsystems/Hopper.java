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
  // Hopper motor
  private WPI_TalonSRX hopperMotor;
  // Sensors for checking hopper inventory
  /** Bottom slot of hopper. */
  private DigitalInput slot1;
  /** Top slot of hopper */
  private DigitalInput slot2;
  // Intake pass-in
  private Intake intake;
  // Pause times for hopper logic
  private long pauseCountA;
  private long pauseCountB;

  public Hopper(Intake intakeArg) {
    setName("Hopper");
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
   * Bottom slot of hopper.
   * 
   * @return true if full, false if empty.
   */
  public boolean hopperBottomIsFull() {
    return !(slot1.get());

  }

  /**
   * Top slot of hopper.
   * 
   * @return true if full, false if empty.
   */
  public boolean hopperTopIsFull() {
    return !(slot2.get());
  }

  public boolean bothSlotsAreFull() {
    return hopperBottomIsFull() && hopperTopIsFull();
  }

  public boolean bothSlotsAreEmpty() {
    return !hopperBottomIsFull() && !hopperTopIsFull();
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
      if (hopperBottomIsFull() && !hopperTopIsFull()) { // Only bottom is full
        activateHopper(); // Turns on hopper
      } else if (!hopperBottomIsFull() && hopperTopIsFull()) { // Only top is full
       pauseCountA++;
       if (pauseCountA > 25) { // Waits to turn off hopper
          deactivateHopper();
         pauseCountA = 0;
      }
      } else if (!hopperBottomIsFull() && !hopperTopIsFull()) { // Both ar empty
        deactivateHopper();
      } else if (bothSlotsAreFull()) { // Both are full
        pauseCountB++;
        if (pauseCountB > 25) { // Waits to turn off hopper and intake.
          intake.deactivateIntake();
          deactivateHopper();
          pauseCountB = 0;
        }
      }}
      addChild("Hopper Motor", hopperMotor);
      addChild("Bottom Sensor", slot1);
      addChild("Top Sensor", slot2);
    }
  

  @Override
  public void periodic() {
    hopperLogicLoop();
  }
}
