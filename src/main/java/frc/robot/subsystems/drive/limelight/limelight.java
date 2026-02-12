package frc.robot.subsystems.drive.limelight;

import org.littletonrobotics.junction.AutoLogOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LimelightHelpers;

public class Limelight {
  @AutoLogOutput
  private static String IdsOne = "";
  @AutoLogOutput
  private static String IdsTwo = "";
  public static void periodic() {
    
    LimelightHelpers.RawFiducial[] fiducialsOne = LimelightHelpers.getRawFiducials("limelight-one");
    LimelightHelpers.RawFiducial[] fiducialsTwo = LimelightHelpers.getRawFiducials("limelight-two");
     for (LimelightHelpers.RawFiducial f : fiducialsOne) {
    Limelight.IdsOne = IdsOne + " " + f.id;
    }
     for (LimelightHelpers.RawFiducial f : fiducialsTwo) {
      IdsTwo = IdsTwo + " " + f.id;
    }

    
  }
   
}
 