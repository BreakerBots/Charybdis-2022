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
    public boolean StopIntakeAuto = false;
    public boolean intakeState = false;
    public boolean intakeSolState = false;
    public boolean indexerHopperState;
    private WPI_TalonSRX indexerL;
    private WPI_TalonSRX indexerR;
    private DoubleSolenoid intakeSol;
    private WPI_TalonSRX intakeMain;

    /** Creates a new Intake. */
    public Intake() {
        intakeMain = new WPI_TalonSRX(Constants.INTAKEMAIN_ID);
        indexerL = new WPI_TalonSRX(Constants.INTAKE_L_ID);
        indexerR = new WPI_TalonSRX(Constants.INTAKE_R_ID);
        intakeSol = new DoubleSolenoid(Constants.PCM_ID, PneumaticsModuleType.CTREPCM,
                Constants.INTAKESOL_FWD, Constants.INTAKESOL_REV);
        intakeState = false;
        indexerHopperState = false;
    }

    /** Extends intake arm and spins the intake and indexer */
    public void activateIntake() {
        intakeMain.set(Constants.INTAKESPEED);
        indexerL.set(Constants.L_SORTESPEED);
        indexerR.set(Constants.R_SORTESPEED);
        intakeState = true;
        if (!intakeSolState) {
            intakeSol.set(Value.kForward);
            intakeSolState = true;
        }
    }

    /** Retracts intake arm and turns off the intake and indexer */
    public void deactivateIntake() {
        //intakeSol.set(Value.kReverse);
        intakeMain.set(0);
        indexerL.set(0);
        indexerR.set(0);
        indexerHopperState = false;
        intakeState = false;
        System.out.println("INTAKE OFF CALLED!");
    }

    public void extendIntakeArm() {
        intakeSol.set(Value.kForward);
        intakeSolState = true;
    }

    public void retractIntakeArm() {
        intakeSol.set(Value.kReverse);
        intakeSolState = false;
    }

    public void lIndexerHopper() {
        if (intakeState == false && indexerHopperState == false) {
            indexerL.set(Constants.L_SORTESPEED);
            indexerHopperState = true;
        } else if (intakeState == false && indexerHopperState) {
            indexerL.set(0);
            indexerHopperState = false;
        }
    }

    public void runHopperFeed() {
        indexerL.set(Constants.L_SORTESPEED);
        indexerHopperState = true;
    }

    public void stopHopperFeed() {
        indexerL.set(0);
        indexerHopperState = false;
    }

    /**
     * Returns intake arm motor supply/input current.
     * 
     * @return Supply/input current in amps
     */
    public double getIntakeSup() {
        return intakeMain.getSupplyCurrent();
    }

    /**
     * Returns intake arm motor stator/output current.
     * 
     * @return Stator/output current in amps
     */
    public double getIntakeSta() {
        return intakeMain.getStatorCurrent();
    }

    /**
     * Returns right indexer motor supply/input current.
     * 
     * @return Supply/input current in amps
     */
    public double getIndexerRSup() {
        return indexerR.getSupplyCurrent();
    }

    /**
     * Returns left indexer motor supply/input current.
     * 
     * @return Supply/input current in amps
     */
    public double getIndexerLSup() {
        return indexerL.getSupplyCurrent();
    }

    /**
     * Returns right indexer motor stator/output current.
     * 
     * @return Stator/output current in amps
     */
    public double getIndexerRSta() {
        return indexerR.getStatorCurrent();
    }

    /**
     * Returns left indexer motor stator/output current.
     * 
     * @return Stator/output current in amps
     */
    public double getIndexerLSta() {
        return indexerL.getStatorCurrent();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
