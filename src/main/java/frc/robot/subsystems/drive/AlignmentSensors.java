package frc.robot.subsystems.drive;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANrangeConfiguration;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.signals.UpdateModeValue;

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
    public ChassisSpeeds getAlignOutputs(){
        double leftDistance = leftCanrange.getDistance().getValueAsDouble();//both meters
        double rightDistance = rightCanrange.getDistance().getValueAsDouble();
        leftDistance -= Units.feetToMeters(distanceBetweenRobots);
        rightDistance -= Units.feetToMeters(distanceBetweenRobots);
        double rotation = rightDistance - leftDistance;
        rotation = Units.radiansToDegrees(Math.atan(rotation/distanceBetweenCANranges));
        double x = (leftDistance + rightDistance)/2;
        
    }

  

}

