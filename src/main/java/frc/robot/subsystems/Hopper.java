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
  private DigitalInput hopPos1;
  Intake intake;
  public Hopper(Intake intakeArg) {
    hopperMotor = new WPI_TalonSRX(Constants.HOPPER_ID);
    intake = intakeArg;
    hopPos1 = new DigitalInput(9);
  }

  public boolean hopperOn() {
    hopperMotor.set(Constants.HOPPERSPEED);
    return hopperState = true;
  }

  public boolean hopperOff() {
    hopperMotor.set(0);
    return hopperState = false;
  }

  public boolean getHopperPos1() {
    return hopPos1.get();
  }

  public boolean getHopperPos2() {
    return false; //DIOJNI.getDIO(Constants.HOPPER_P2_ID);
  }



  @Override
  public void periodic() {
    if (intake.intakeState) {
      if (getHopperPos1() ) { //&& !getHopperPos2()
        hopperOn();
      }
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
      // }
      else {
        hopperOff();
        intake.intakeOffMethod();

      }
    }
  }
}

 