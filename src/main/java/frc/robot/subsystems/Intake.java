// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.fasterxml.jackson.databind.ser.impl.IndexedStringListSerializer;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    public boolean intakeState;
    private WPI_TalonSRX indexerL;
    private WPI_TalonSRX indexerR;
    private WPI_TalonSRX armMTalonSRX;
    private DoubleSolenoid intakeSol_L;
    private DoubleSolenoid intakeSol_R;
    private static WPI_TalonSRX intakeMain;

    /** Creates a new Intake. */
    public Intake() {
        intakeMain = new WPI_TalonSRX(Constants.INTAKEMAIN_ID);
        indexerL = new WPI_TalonSRX(Constants.INTAKE_L_ID);
        indexerR = new WPI_TalonSRX(Constants.INTAKE_R_ID);
        intakeSol_L = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 
                Constants.L_INTAKESOL_FWD, Constants.L_INTAKESOL_REV);
        intakeSol_R = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
                Constants.R_INTAKESOL_FWD, Constants.R_INTAKESOL_REV);
    }

    public boolean intakeOnMethod() {
        intakeSol_L.set(Value.kForward);
        intakeSol_R.set(Value.kForward);
        intakeMain.set(Constants.INTAKESPEED);
        indexerL.set(Constants.L_SORTESPEED);
        indexerR.set(Constants.R_SORTESPEED);
        return intakeState = true;
    }

    public boolean intakeOffMethod() {
        intakeSol_L.set(Value.kReverse);
        intakeSol_R.set(Value.kReverse);
        intakeMain.set(0);
        indexerL.set(0);
        indexerR.set(0);
        return intakeState = false;
    }


    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
