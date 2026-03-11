package frc.robot.subsystems.drive;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANrangeConfiguration;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.signals.UpdateModeValue;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlignmentSensors extends SubsystemBase{

    private final CANrange rightCanrange = new CANrange(61);//change CAN ID later
    private final CANrange leftCanrange = new CANrange(60); //change CAN ID later
    private Pose2d currentPose;
    private Pose2d goalPose;
    Boolean isAligned;
    public AlignmentSensors(){

        CANrangeConfiguration canrangeConfig = new CANrangeConfiguration();

        canrangeConfig.ProximityParams.MinSignalStrengthForValidMeasurement = 2000; //minimun signal strength for measurement
        canrangeConfig.ProximityParams.ProximityThreshold = Units.feetToMeters(2); // how far away the CANrange will say stuff "is detected"

        canrangeConfig.ToFParams.UpdateMode = UpdateModeValue.ShortRange100Hz; //fastest update speed, may need to change later

        leftCanrange.getConfigurator().apply(canrangeConfig);
        rightCanrange.getConfigurator().apply(canrangeConfig);
    }
    public Boolean IsAligned(){ 
        isAligned = rightCanrange.getIsDetected().getValue() && leftCanrange.getIsDetected().getValue();
        return isAligned;
    }


  

}

