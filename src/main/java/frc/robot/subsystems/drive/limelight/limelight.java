package frc.robot.subsystems.drive.limelight;

import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.PoseEstimate;
import frc.robot.subsystems.drive.Drive;


public class limelight {

  Drive drive = Drive;

  public static void periodic() {
    String Ids = "";

    LimelightHelpers.RawFiducial[] fiducials = LimelightHelpers.getRawFiducials("");

    for (LimelightHelpers.RawFiducial f : fiducials) {
      Ids = Ids + " " + f.id;
    }
    SmartDashboard.putString("IdList", Ids);
  }
}
