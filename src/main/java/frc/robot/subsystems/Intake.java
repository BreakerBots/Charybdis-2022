// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    public boolean intakeState;
    public boolean indexerHopperState;
    private WPI_TalonSRX indexerL;
    private WPI_TalonSRX indexerR;
    private DoubleSolenoid intakeSol;
    private static WPI_TalonSRX intakeMain;

    /** Creates a new Intake. */
    public Intake() {
        intakeMain = new WPI_TalonSRX(Constants.INTAKEMAIN_ID);
        indexerL = new WPI_TalonSRX(Constants.INTAKE_L_ID);
        indexerR = new WPI_TalonSRX(Constants.INTAKE_R_ID);
        intakeSol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
                Constants.INTAKESOL_FWD, Constants.INTAKESOL_REV);
    }

    /** Extends intake arm and spins the intake and indexer */
    public boolean intakeOnMethod() {
        intakeSol.set(Value.kForward);
        intakeMain.set(Constants.INTAKESPEED);
        indexerL.set(Constants.L_SORTESPEED);
        indexerR.set(Constants.R_SORTESPEED);
        return intakeState = true;
    }

    /** Retracts intake arm and turns off the intake and indexer */
    public boolean intakeOffMethod() {
        intakeSol.set(Value.kReverse);
        intakeMain.set(0);
        indexerL.set(0);
        indexerR.set(0);
        indexerHopperState = false;
        return intakeState = false;
    }

    public void lIndexerHopper() {
        if (intakeState == false && indexerHopperState == false) {
            indexerL.set(Constants.L_SORTESPEED);
            indexerHopperState = true;
        }
        else if (intakeState == false && indexerHopperState) {
            indexerL.set(0);
            indexerHopperState = false;
        }
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
