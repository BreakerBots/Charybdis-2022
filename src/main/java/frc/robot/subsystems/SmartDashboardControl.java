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
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.devices.AirCompressor;

public class SmartDashboardControl extends SubsystemBase {
  /** Creates a new SmartDashbord. */
  private Shooter shooter;
  private Intake intake;
  private AirCompressor compressor;
  private Climber climber;
  private PowerDistribution pdp;
  // private double[] climbProg = new double[1];

  public SmartDashboardControl(AirCompressor compressorArg, Shooter shooterArg, Intake intakeArg, PowerDistribution pdpArg) { // Climber climbArg
    intake = intakeArg;
    shooter = shooterArg;
    compressor = compressorArg;
    pdp = pdpArg;
    // climber = climbArg;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.updateValues();
    SmartDashboard.putString("SHOOTER MODE", shooter.getShootMode().toString());
    SmartDashboard.putBoolean("INTAKE", intake.intakeState);
    SmartDashboard.putBoolean("ARM EXTENDED", intake.intakeSolState);
    SmartDashboard.putBoolean("COMPRESSOR", compressor.getCompressorState());
    SmartDashboard.putNumber("BATTERY V", pdp.getVoltage());
    SmartDashboard.putBoolean("BROWNOUT", RobotController.isBrownedOut());
    SmartDashboard.putString("FLYWHEEL", shooter.getFlywheelState().toString());
    // }
    // climbProg[0] = climber.climbSequenceTotal;
    // climbProg[1] = climber.climbSequenceProgress;
    // SmartDashboard.putNumberArray("CLIMB PROGRESS - A of B:", climbProg);
    // UsbCamera usbCamera = new UsbCamera("USB Camera 0", 0);
    // CameraServer.startAutomaticCapture(usbCamera);

    // // Creates the CvSink and connects it to the UsbCamera
    // CvSink cvSink = CameraServer.getVideo();

    // // Creates the CvSource and MjpegServer [2] and connects them
    // CvSource outputStream = CameraServer.putVideo("frontvid", 640, 480);
  }
}