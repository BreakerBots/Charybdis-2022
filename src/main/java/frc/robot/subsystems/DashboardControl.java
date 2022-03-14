// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.devices.AirCompressor;
import frc.robot.subsystems.devices.FMS_Handler;

public class DashboardControl extends SubsystemBase{
  /** Creates a new SmartDashbord. */
  private Shooter shooter;
  private Intake intake;
  private AirCompressor compressor;
  private Climber climber;
  private PowerDistribution pdp;
  private FMS_Handler fms;

  public DashboardControl(AirCompressor compressorArg, Shooter shooterArg, Intake intakeArg, PowerDistribution pdpArg,
      FMS_Handler fmsArg, Climber climbArg) {
    intake = intakeArg;
    shooter = shooterArg;
    compressor = compressorArg;
    pdp = pdpArg;
    fms = fmsArg;
    climber = climbArg;
  }

  public static void log(String output) {
    SmartDashboard.putString("LOG", output);
  }

  // @Config.ToggleButton(tabName = "DEFAULT")
  // void BrakeMode(boolean enabled) {
  //   climber.setBrakeMode(enabled);
  // }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.updateValues();
    // Shooter widgets
    SmartDashboard.putString("SHOOTER MODE", shooter.getShootMode().toString());
    SmartDashboard.putString("FLYWHEEL", shooter.getFlywheelState().toString());
    SmartDashboard.putBoolean("SHOOTING", shooter.isShooting);
    // Intake widgets
    SmartDashboard.putBoolean("INTAKING", intake.intakeIsRunning());
   // SmartDashboard.putBoolean("ARM EXTENDED", intake.armIsExtended());
    // Compressor widgets
    SmartDashboard.putBoolean("STATE", compressor.compressorIsEnabled());
    // Electrical widgets
    SmartDashboard.putNumber("BATTERY V", pdp.getVoltage());
    SmartDashboard.putBoolean("ROBORIO", RobotController.isBrownedOut());
    // Comp widgets
    // SmartDashboard.putBoolean("ALLIANCE", fms.getAllianceBool());
    // SmartDashboard.putNumber("STATION #", DriverStation.getLocation());
    SmartDashboard.putNumber("MATCH TIME", DriverStation.getMatchTime());
    // SmartDashboard.putNumber("MATCH #", DriverStation.getMatchNumber());
    // SmartDashboard.putBoolean("FMS CONNTECTED", DriverStation.isFMSAttached());
    // climber widgets
    // SmartDashboard.putNumber("LEFT CLIMB TICKS", climber.getLeftClimbTicks());
    // SmartDashboard.putNumber("LEFT CLIMB TARGET", climber.lClimbPID.getSetpoint());
    // SmartDashboard.putNumber("RIGHT CLIMB TICKS", climber.getLeftClimbTicks());
    // SmartDashboard.putNumber("RIGHT CLIMB TARGET", climber.lClimbPID.getSetpoint());
    // SmartDashboard.putBoolean("AUTO CLIMBING", climber.isClimbing());
    // SmartDashboard.putBoolean("CLIMB ARMS UP", climber.climbSolRetracted);
    // cameras
    // UsbCamera frontCam = new UsbCamera("Front Camera", 0);
    // UsbCamera backCam = new UsbCamera("Back Camera", 1);
    // CameraServer.startAutomaticCapture(frontCam);
    // CameraServer.startAutomaticCapture(backCam);
  }
}