// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.commands.shooter;

// import edu.wpi.first.math.MathUtil;
// import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.Constants;
// import frc.robot.subsystems.DashboardControl;
// import frc.robot.subsystems.Drive;
// import frc.robot.subsystems.Hopper;
// import frc.robot.subsystems.Intake;
// import frc.robot.subsystems.Shooter;
// import frc.robot.subsystems.Shooter.FlywheelState;
// import frc.robot.subsystems.Shooter.ShooterMode;
// import frc.robot.subsystems.devices.Limelight;

// public class RunVisionShooter extends CommandBase {
  
//   Limelight camera;
//   Drive drive;
//   Shooter shooter;
//   Hopper hopper;
//   Intake intake;
//   XboxController xbox;
//   boolean startShooting = false;
//   boolean startWithTwoCargo;
//   private long timedStopCount;
//   private long timedStartCount;
//   /** vision capable shooting command to compleatly replace the ChargeThenShoot command group. Defaults to normal shooting
//    * if driver specifyed or if a limelight fault is detected.
//     */
//   public RunVisionShooter(Limelight limelightArg, Drive driveArg, Shooter shooterArg, Hopper hopperArg, Intake intakeArg, XboxController controllerArg) {
//     // Use addRequirements() here to declare subsystem dependencies.
//     camera = limelightArg;
//     drive = driveArg;
//     hopper = hopperArg;
//     intake = intakeArg;
//     xbox = controllerArg;
//   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {
//     startShooting = false;
//     shooter.resetFlywheelPID();
//     camera.aimPID.reset();
//     DashboardControl.log("CHARGING FLYWHEEL");
//     if (shooter.getFlywheelState() == FlywheelState.IDLE || shooter.getFlywheelState() == FlywheelState.OFF) {
//       shooter.chargeFlywheel();
//     }
//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     // Pivots robot to allign with vision target if the camera has a valid target and robot has vision targeting enabled
//     if (camera.hasTarget() /** && (shooter.getShootMode() == ShooterMode.VISION) */) {
//       double curAngle = camera.getTargetInfo("tx"); // gets position of target relative to center of cameras FOV
//       double turnPercent = camera.aimPID.calculate(curAngle, 0);  // Restricts motor speed
//       turnPercent += (turnPercent > 0 ? Constants.ANG_FEEDFWD : -Constants.ANG_FEEDFWD);
//       drive.move(0, turnPercent); // Turns in place
//     } else {
//       drive.move(0, 0);
//     }

//     // Checks superstructur conditions to see if the robot can begin shooting
//     if (shooter.flywheelPIDAtSetpoint() && (camera.shooterIsAimed() ^ !camera.hasTarget())) {
//       shooter.setFlywheelState(FlywheelState.CHARGED);
//       startShooting = true;
//     } 

//     // logic loop that handles shooting after the flywheel is charged (ball feeding)
//     if (startShooting = true) {
//       if (shooter.getFlywheelState() == FlywheelState.CHARGED && shooter.flywheelPIDAtSetpoint()) {
//         shooter.isShooting = true;
//         hopper.activateShooterHopper();
//         intake.toggleHopperFeed();
//         DashboardControl.log("SHOOTER STARTED!");
//       }
//       if (hopper.bothSlotsAreEmpty()) {
//         // delay to prevent balls from being caught in system
//         if (timedStopCount > 75) {
//           hopper.deactivateHopper();
//           intake.deactivateIntake();
//           timedStopCount = 0;
//           shooter.setOff();
//           shooter.setFlywheelState(FlywheelState.OFF);
//           shooter.resetFlywheelPID();
//           shooter.isShooting = false;
//         } else {
//           timedStopCount++;
//         }
//       }
//     }
//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {
//     drive.move(0, 0);
//   }

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     return (shooter.getFlywheelState() == FlywheelState.OFF) || xbox.getBackButtonPressed();
//   }
// }
