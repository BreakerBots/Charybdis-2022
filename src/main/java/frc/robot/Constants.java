// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.swing.Box.Filler;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    /** General filler constant */
    public static final int FILLER = 99;
    // Time constants
    public static final double MS_PER_CYCLE = 20;
    public static final double CYCLES_PER_SECOND = 200;
    // Talon FX constants
    /** Ticks per rotation for Talon FX motors */
    public static final double TALON_FX_TICKS = 2048.0;
    public static final double MAX_SPD_VOLTS_FX = FILLER;

    // Xbox button constants
    public static final int A = 1;
    public static final int B = 2;
    public static final int X = 3;
    public static final int Y = 4;
    public static final int L_BUMP = 5;
    public static final int R_BUMP = 6;
    public static final int BACK = 7;
    public static final int START = 8;
    public static final int L_STICK_PRESS = 9;
    public static final int R_STICK_PRESS = 10;
    // Xbox axis constants
    public static final int LEFT_X = 0;
    public static final int LEFT_Y = 1;
    public static final int L_TRIGGER = 2;
    public static final int R_TRIGGER = 3;
    public static final int RIGHT_X = 4;
    public static final int RIGHT_Y = 5;
    // Xbox D-Pad angle constants
    public static final int D_UP = 0;
    public static final int D_RIGHT = 90;
    public static final int D_DOWN = 180;
    public static final int D_LEFT = 270;

    // PDP constants
    // Power Distribution IDs
    /** CAN ID for robot's Power Distribution HUB (PDH) */
    public static final int PDH_ID = 1;

    // Compressor constants
    /** CAN ID for robot's Pneumatics Control Module (PCM) */
    public static final int PCM_ID = 5;
    public static final double MIN_PSI = 70;
    public static final double MAX_PSI = 120;
    public static final double COMPRESSOR_TIMEOUT_CYCLES = 7000;

    // Drivetrain constants
    // Angle and distance PID
    public static final double KP_ANG = 0.025;
    public static final double KI_ANG = 0.0;
    public static final double KD_ANG = 0.0;
    public static final double KP_DIST = 0.05;
    public static final double KI_DIST = 0;
    public static final double KD_DIST = 0;
    public static final double ANG_POS_TOL = 2.5;
    public static final double ANG_VEL_TOL = 1;
    public static final double DIST_POS_TOL = 1;
    public static final double DIST_VEL_TOL = 0.1;
    // Drive feedForward values
    public static final double KS_DRIVE = 4.0089;
    public static final double KV_DRIVE = 0.029497;
    public static final double KA_DRIVE = -0.71256;
    public static final double ANG_FEEDFWD = 0.23;
    // Drive motor IDs
    public static final int L1_ID = 11;
    public static final int L2_ID = 12;
    public static final int L3_ID = 13;
    public static final int R1_ID = 14;
    public static final int R2_ID = 15;
    public static final int R3_ID = 16;
    // Slow mode
    public static final double SLOW_MODE_MULTIPLIER = 0.5;
    // Drive specs 
    /** Gear ratio for drivetrain (rotation of motors to rotation of wheel) */
    public static final double DRIVE_GEAR_RATIO = 8.49; // Assume (value) to 1. Alpha = 8.49 scyllia = 8.49
    /** Diameter of Colson wheel, in inches */
    public static final double COLSON_DIAMETER = 3.88;
    /** Circumference of Colson wheel, in inches */
    public static final double COLSON_CIRCUMFERENCE = COLSON_DIAMETER * Math.PI;
    /** Ticks per rotation of Colson wheels on drivetrain */
    public static final double DRIVE_TICKS_PER_ROTATION = (TALON_FX_TICKS * DRIVE_GEAR_RATIO);
    /** Number of Talon FX ticks per inch driven */
    public static final double DRIVE_TICKS_PER_INCH = DRIVE_TICKS_PER_ROTATION / (COLSON_CIRCUMFERENCE);

    // IMU constants
    public static final int IMU_ID = 10;
    public static boolean IMU_INVERTED = true;
    public static final double kCatchup = 0.01;

    // Intake constants
    // Intake motor speeds
    public static final double INTAKE_SPD = 1; // 1
    public static final double R_INDEX_SPD = 0.8; // 0.8
    public static final double L_INDEX_SPD = 0.8; // 0.8
    // Intake motor IDs
    public static final int INTAKE_ID = 21;
    public static final int L_INDEX_ID = 22;
    public static final int R_INDEX_ID = 23;
    public static final int PWR_DISTR = 0; // Unused?
    // Intake Solonoid IDs
    public static final int INTAKESOL_FWD = 2;
    public static final int INTAKESOL_REV = 0;

    // Hopper constants
    // Hopper motor ID
    public static final int HOPPER_ID = 31;
    // Hopper motor speeds
    public static final double HOPPER_SPD = 0.8;
    public static final double SHOOTER_HOPPER_SPD = 0.3;
    // Hopper ball position sensor channels
    public static final int SLOT_1_CHANNEL = 1;
    public static final int SLOT_2_CHANNEL = 0;
    // Amount of delay on hopper
    public static final long HOPPER_DELAY_CYCLES = 0;
    // Hopper specs
    public static final double HOPPER_WHEEL_DIAMETER = 1; // Unused?
    public static final double HOPPER_WHEEL_CIRCUMFERENCE = HOPPER_WHEEL_DIAMETER * Math.PI; // Unused?
    public static final double HOPPER_GEAR_RATIO = FILLER; // Unused?
    public static final double HOPPER_TICKS_PER_INCH = (TALON_FX_TICKS * HOPPER_GEAR_RATIO) // Unused?
            / (HOPPER_WHEEL_CIRCUMFERENCE);

    // Shooter constants
    // Shooter motor speeds
    public static final double HUB_SHOOT_SPD = 0.45; // Upper is 0.45
    public static final double LOW_SHOOT_SPD = 0.20; // Hood must be up
    public static final double LAUNCH_SHOOT_SPD = 0.50; // Hood up
    public static final double FLYWHEEL_CANCEL_RPM = 0; // Change to TPS?
    // Flywheel speed in TPS
    public static final int FLYWHEEL_MAX_TPS = 1780;
    // Shooter motor IDs
    public static final int SHOOTER_L_ID = 41;
    public static final int SHOOTER_R_ID = 42;
    // Shooter Solonoid IDs
    public static final int SHOOTERSOL_FWD = 4;
    public static final int SHOOTERSOL_REV = 1;
    // Flywheel PID
    public static final double KP_FLYWHEEL = 0.0004;
    public static final double KI_FLYWHEEL = 0;
    public static final double KD_FLYWHEEL = 0.000015;
    public static final double FLYWHEEL_VEL_TOL = 25; // Used as position tolerance.
    public static final double FLYWHEEL_ACCEL_TOL = 25; // Unused. Would be used for velocity tolerance.
    
    // Climber constants
    // Climber motor IDs
    public static final int CLIMBER_L_ID = 51;
    public static final int CLIMBER_R_ID = 52;
    // Climber Solonoid IDs
    public static final int CLIMBSOL_FWD = 1;
    public static final int CLIMBSOL_REV = 3;
    // Trigger thresholds for climber states (in ticks)
    public static final double CLIMB_RET_THRESH = 0;
    public static final double CLIMB_EXT_THRESH = 0;
    // PID values for climber
    public static final double KP_CLIMB = 0.00002;
    public static final double KI_CLIMB = 0.0000001;
    public static final double KD_CLIMB = 0.000001;
    // Climber stablity tolerences
    public static final double CLIMB_PITCH_TOL = 5;
    public static final double CLIMB_YAW_TOL = 5;
    public static final double CLIMB_ROLL_TOL = 5;
    public static final long CLIMB_MIN_STABLE_TIME = 150; // If in cycles, rename to "CLIMB_MIN_STABLE_CYCLES"?
    // Climber extension values (ticks)
    public static final double CLIMB_FULL_EXT_DIST = 2000000000;
    public static final double CLIMB_FULL_RET_DIST = FILLER;
    public static final double CLIMB_MIRACLE_GRAB_EXT_DIST = FILLER;
    public static final double CLIMB_LIFT_OF_MID_DIST = FILLER;
    public static final double LIFT_ONTO_HIGH_DIST = FILLER;
    public static final double SECOND_MIRACLE_GRAB_EXT_DIST = FILLER;
    public static final double WINCH_INPUT_MULTIPLIER = 1400; //2100

    public static final double CLIMB_SPD_RESET_CYCLES = 150;

    public static final double ART_DRIVE_FF = 0.20;
    public static final double HUB_HEIGHT_INS = 104;
    public static final double SHOOT_CAM_HEIGHT = FILLER;
    public static final double SHOOT_CAM_ANG = FILLER;
    public static final double HOOD_UP_DIST = FILLER;
    public static final int START_MAX_CLIMB_EXT = Integer.MAX_VALUE;

  
    

}
