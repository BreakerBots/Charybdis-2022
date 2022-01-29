// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int FILLER = 99; // filler id for unused can devices
    // Time constants
    public static final double MS_PER_CYCLE = 20;
    public static final double CYCLES_PER_SECOND = 200;
    // Drivetrain constants
    public static final double GEAR_RATIO = 11.97; // Assume (value) to 1. Alpha = 16.24
    public static final double WHEEL_DIAMETER = 6; // Inches. Alpha = 4
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI; // Inches
    public static final double TALON_FX_TICKS = 2048.0; // Ticks per rotation
    public static final double TICKS_PER_INCH = (TALON_FX_TICKS * GEAR_RATIO) / (WHEEL_CIRCUMFERENCE);
    // IMU constants
    public static final int IMU_ID = FILLER; // Alpha = 10
    public static final double RADIANS_PER_DEGREE = Math.PI/180;
    // Drive motor IDs
    public static final int L1_ID = 11; // Alpha = 11
    public static final int L2_ID = 12; // Alpha = 12
    public static final int L3_ID = 13; // Alpha = 13
    public static final int R1_ID = 14; // Alpha = 14
    public static final int R2_ID = 15; // Alpha = 15
    public static final int R3_ID = 16; // Alpha = 16
    // AngleController PID
    public static final double KP_ANG = 0.02;
    public static final double KI_ANG = 0.1;
    public static final double KD_ANG = 0.1;
    public static final double KP_DIST = 0.05;
    public static final double KI_DIST = 0;
    public static final double KD_DIST = 0;
    public static final double ANG_POS_TOLERANCE = 1;
    public static final double ANG_VEL_TOLERANCE = 0.5;
    public static final double DIST_POS_TOLERANCE = 1;
    public static final double DIST_VEL_TOLERANCE = 0.5;
    // intake motor speeds
    public static final double INTAKESPEED = 1;
    public static final double R_SORTESPEED = 1;
    public static final double L_SORTESPEED = 0.8;
    // intake solonoid IDs
    public static final int L_INTAKESOL_FWD = FILLER;
    public static final int L_INTAKESOL_REV = FILLER;
    public static final int R_INTAKESOL_FWD = FILLER;
    public static final int R_INTAKESOL_REV = FILLER;
    // intake motor IDs
    public static final int INTAKEMAIN_ID = FILLER;
    public static final int INTAKE_L_ID = FILLER;
    public static final int INTAKE_R_ID = FILLER;
    public static final int PWR_DISTR = FILLER;
    // shooter motor IDs
    public static final int SHOOTER_1_ID = FILLER;
    public static final int SHOOTER_2_ID = FILLER;
    // shooter solonoid IDs
    public static final int SHOOTERSOL_FWD = FILLER;
    public static final int SHOOTERSOL_REV = FILLER;

}
