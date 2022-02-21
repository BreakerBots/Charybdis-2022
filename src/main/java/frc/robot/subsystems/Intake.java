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
    private boolean intakeIsRunning = false;
    private boolean intakeIsExtended = false;
    private boolean indexerLIsRunning = false;
    private boolean indexerRIsRunning = false;
    private WPI_TalonSRX indexerL;
    private WPI_TalonSRX indexerR;
    private DoubleSolenoid intakeSol;
    private WPI_TalonSRX intakeMotor;

    /** Creates a new Intake. */
    public Intake() {
        setName("Intake");
        intakeMotor = new WPI_TalonSRX(Constants.INTAKE_ID);
        intakeMotor.setInverted(true); // Spins backwards
        indexerL = new WPI_TalonSRX(Constants.L_INDEX_ID);
        indexerR = new WPI_TalonSRX(Constants.R_INDEX_ID);
        intakeSol = new DoubleSolenoid(Constants.PCM_ID, PneumaticsModuleType.CTREPCM,
                Constants.INTAKESOL_FWD, Constants.INTAKESOL_REV);
        // intakeIsRunning = false;
        // indexerIsRunning = false;
    }

    public void extendIntakeArm() {
        intakeSol.set(Value.kForward);
        intakeIsExtended = true;
    }

    public void retractIntakeArm() {
        intakeSol.set(Value.kReverse);
        intakeIsExtended = false;
    }

    public boolean armIsExtended() {
        return intakeIsExtended;
    }

    public void startIntakeMotor() {
        intakeMotor.set(Constants.INTAKE_SPD);
        intakeIsRunning = true;
    }

    public void stopIntakeMotor() {
        intakeMotor.set(0);
        intakeIsRunning = false;
    }

    public boolean intakeIsRunning() {
        return intakeIsRunning;
    }

    public void startLeftIndexer() {
        indexerL.set(Constants.L_INDEX_SPD);
        indexerLIsRunning = true;
    }

    public void stopLeftIndexer() {
        indexerL.set(0);
        indexerLIsRunning = false;
    }

    public boolean leftIndexerIsOn() {
        return indexerLIsRunning;
    }

    public void startRightIndexer() {
        indexerR.set(Constants.R_INDEX_SPD);
        indexerRIsRunning = true;
    }

    public void stopRightIndexer() {
        indexerR.set(0);
        indexerRIsRunning = false;
    }

    public boolean rightIndexerIsOn() {
        return indexerRIsRunning;
    }

    /** Extends intake arm and spins the intake and indexer */
    public void activateIntake() {
        startIntakeMotor();
        startLeftIndexer();
        startRightIndexer();
        if (!intakeIsExtended) {
            extendIntakeArm();
        }
    }

    /** Turns off the intake and indexer */
    public void deactivateIntake() {
        stopIntakeMotor();
        stopLeftIndexer();
        stopRightIndexer();
    }

    /**
     * Toggles left indexer on/off when intake is off to feed in balls to the
     * hopper. MIGHT BE DEPRECATED!!!
     */
    public void toggleHopperFeed() {
        if (!intakeIsRunning) {
            if (indexerLIsRunning) {
                stopLeftIndexer();
            } else {
                startLeftIndexer();
            }
        }
    }

    /**
     * Returns intake arm motor supply/input current.
     * 
     * @return Supply/input current in amps
     */
    public double getIntakeSup() {
        return intakeMotor.getSupplyCurrent();
    }

    /**
     * Returns intake arm motor stator/output current.
     * 
     * @return Stator/output current in amps
     */
    public double getIntakeSta() {
        return intakeMotor.getStatorCurrent();
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
        addChild("Arm", intakeSol);
        addChild("Intake Motor", intakeMotor);
        addChild("Left Indexer", indexerL);
        addChild("Right Indexer", indexerR);
        // This method will be called once per scheduler run
    }
}
