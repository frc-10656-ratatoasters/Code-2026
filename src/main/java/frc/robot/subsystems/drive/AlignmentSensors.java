package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.AutoLogOutput;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANrangeConfiguration;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.signals.UpdateModeValue;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlignmentSensors extends SubsystemBase{

    private final CANrange rightCanrange = new CANrange(61);//change CAN ID later
    private final CANrange leftCanrange = new CANrange(60); //change CAN ID later
//CHANGE DEPENDING ON ALLIANCE BOTS!! our middle to end of intake is 2 ft
    private final double distanceBetweenRobots = 5;//ft
    //CHANGE LATER
    private final double distanceBetweenCANranges = 2;//ft
    public boolean isAligned = false;
    private Pose2d currentPose;
    private Pose2d targetPose;
    public AlignmentSensors(){
        CANrangeConfiguration canrangeConfig = new CANrangeConfiguration();

        canrangeConfig.ProximityParams.MinSignalStrengthForValidMeasurement = 2000; //minimun signal strength for measurement
        canrangeConfig.ProximityParams.ProximityThreshold = Units.feetToMeters(2); // how far away the CANrange will say stuff "is detected"

        canrangeConfig.ToFParams.UpdateMode = UpdateModeValue.ShortRange100Hz; //fastest update speed, may need to change later

        leftCanrange.getConfigurator().apply(canrangeConfig);
        rightCanrange.getConfigurator().apply(canrangeConfig);
    }
    public boolean isAligned(){
        isAligned = rightCanrange.getIsDetected().getValue() && leftCanrange.getIsDetected().getValue();
        return isAligned;
    }
    @AutoLogOutput
    public Pose2d getAlignTargetPose(Drive drive){
        double leftDistance = leftCanrange.getDistance().getValueAsDouble();//both meters
        double rightDistance = rightCanrange.getDistance().getValueAsDouble();
        if(Units.metersToFeet(leftDistance) > 4 && Units.metersToFeet(rightDistance) > 4){
            return null;
        }
        leftDistance -= Units.feetToMeters(distanceBetweenRobots);
        rightDistance -= Units.feetToMeters(distanceBetweenRobots);
        double rotation = rightDistance - leftDistance;
        rotation = Math.atan(rotation/distanceBetweenCANranges);//RADIANS
        double x = (leftDistance + rightDistance)/2;
        double YOutput = 0;
        if(Units.metersToFeet(leftDistance) < 3 && Units.metersToFeet(rightDistance) > 5){
            YOutput = 1;//guess we might want to change? 
        }
        else if(Units.feetToMeters(rightDistance) < 3 && Units.feetToMeters(leftDistance) > 5){
            YOutput = -1;
        }
        currentPose = drive.getPose();
        Translation2d translation = new Translation2d(x, YOutput);
        Rotation2d rotation2d = new Rotation2d(rotation);
        targetPose = new Pose2d(currentPose.getTranslation().plus(translation), currentPose.getRotation().plus(rotation2d));
        return targetPose;
    }

  

}

