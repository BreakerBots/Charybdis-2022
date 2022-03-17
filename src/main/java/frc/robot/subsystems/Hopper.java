// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Hopper subsystem. */
public class Hopper extends SubsystemBase {
  private boolean hopperIsRunning;
  // Hopper motor
  private WPI_TalonSRX hopperMotor;
  // Sensors for checking hopper inventory
  private DigitalInput bottomSlot;
  private DigitalInput topSlot;
  // Intake pass-in
  private Intake intake;
  // Pause times for hopper logic
  private long pauseCountA;
  private long pauseCountB;
  private boolean ballInTransit = false;

  public Hopper(Intake intakeArg) {
    setName("Hopper");
    hopperMotor = new WPI_TalonSRX(Constants.HOPPER_ID);
    hopperMotor.setInverted(true);
    intake = intakeArg;
    bottomSlot = new DigitalInput(Constants.SLOT_1_CHANNEL);
    topSlot = new DigitalInput(Constants.SLOT_2_CHANNEL);
  }

  /** Turns on hopper */
  public void activateHopper() {
    hopperMotor.set(Constants.HOPPER_SPD);
    hopperIsRunning = true;
  }

  /** Activates hopper specifically for feeding balls into the shooter. */
  public void activateShooterHopper() {
    hopperMotor.set(Constants.SHOOTER_HOPPER_SPD);
    hopperIsRunning = true;
  }

  /** Turns off hopper */
  public void deactivateHopper() {
    hopperMotor.set(0);
    hopperIsRunning = false;
  }

  /**
   * Current state of hopper.
   * 
   * @return true if the hopper is running, false if the hopper is not.
   */
  public boolean hopperIsRunning() {
    return hopperIsRunning;
  }

  /**
   * Bottom slot of hopper.
   * 
   * @return true if full, false if empty.
   */
  public boolean bottomSlotIsFull() {
    return !bottomSlot.get();
  }

  /**
   * Top slot of hopper.
   * 
   * @return true if full, false if empty.
   */
  public boolean topSlotIsFull() {
    return topSlot.get();
  }

  /**
   * Checks both hopper slots for fullness.
   * 
   * @return true if both are full, false otherwise.
   */
  public boolean bothSlotsAreFull() {
    return bottomSlotIsFull() && topSlotIsFull();
  }

  /**
   * Checks both hopper slots for emptiness.
   * 
   * @return true if both are empty, false otherwise.
   */
  public boolean bothSlotsAreEmpty() {
    return !bottomSlotIsFull() && !topSlotIsFull();
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

  public boolean getBallInTransit() {
    return ballInTransit;
  }

  public void setBallInTransit(boolean state) {
    ballInTransit = state;
  }

  /**
   * Persistent hopper logic based on other subsystems and fullness of slots.
   * <p>
   * NOTE: Top slot is filled first, then bottom
   * <p>
   * NOTE: Pauses are to assure that the balls are fully moved into proper
   * position.
   */
  public void hopperLogicLoop() {
    if (intake.intakeIsRunning()) {
      if (bottomSlotIsFull() && !topSlotIsFull()) { // Only bottom is full
        activateHopper(); // Turns on hopper
        setBallInTransit(true);
      } else if (!bottomSlotIsFull() && topSlotIsFull()) { // Only top is full
        pauseCountA++;
        setBallInTransit(false);
        if (pauseCountA > 0) { // Waits to turn off hopper
          deactivateHopper();
          pauseCountA = 0;
        }
      } else if (bothSlotsAreEmpty() && !getBallInTransit()) { // Both are empty
        deactivateHopper();
        // activateHopper();
      } else if (bothSlotsAreFull()) { // Both are full
        pauseCountB++;
        if (pauseCountB > 0) { // Waits to turn off hopper and intake.
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
    addChild("Hopper Motor", hopperMotor);
    addChild("Bottom Sensor", bottomSlot);
    addChild("Top Sensor", topSlot);
    // System.out.println("Bottom slot: " + bottomSlotIsFull());
    // System.out.println("Top slot: " + topSlot.get());
  }
}
