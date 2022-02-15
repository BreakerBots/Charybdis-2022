// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SmartDashboardControl extends SubsystemBase {
  /** Creates a new SmartDashbord. */
  Shooter shooter;
  Intake intake;
  Climber climber;
  //private double[] climbProg = new double[1];
  
  public SmartDashboardControl(Shooter shooterArg, Intake intakeArg) { //Climber climbArg
    intake = intakeArg;
    shooter = shooterArg;
    // climber = climbArg;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.updateValues();
    switch (shooter.shooterMode) {
      case UP: SmartDashboard.putString("SHOOTER MODE: ", "HIGH");
      break;
      case LOW: SmartDashboard.putString("SHOOTER MODE: ", "LOW");
      break;
      case LAUNCH: SmartDashboard.putString("SHOOTER MODE: ", "LAUNCH");
      break;
      default: SmartDashboard.putString("SHOOTER MODE: ", "ERROR!");
    }
    SmartDashboard.putBoolean("INTAKEING: ", intake.intakeState);
    // climbProg[0] = climber.climbSequenceTotal;
    // climbProg[1] = climber.climbSequenceProgress;
    // SmartDashboard.putNumberArray("CLIMB PROGRESS - A of B:", climbProg);
    // UsbCamera usbCamera = new UsbCamera("USB Camera 0", 0);
    //     CameraServer.startAutomaticCapture(usbCamera);

    //     // Creates the CvSink and connects it to the UsbCamera
    //     CvSink cvSink = CameraServer.getVideo();
        
    //     // Creates the CvSource and MjpegServer [2] and connects them
    //     CvSource outputStream = CameraServer.putVideo("Blur", 640, 480);
  }
}