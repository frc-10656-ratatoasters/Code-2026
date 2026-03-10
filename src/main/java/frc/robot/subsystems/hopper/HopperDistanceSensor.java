package frc.robot.subsystems.hopper;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANrangeConfiguration;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.signals.UpdateModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HopperDistanceSensor extends SubsystemBase{

    private final CANrange rightCanrange = new CANrange(61);//change CAN ID later
    //private final CANrange leftCanrange = new CANrange(60); //change CAN ID later
    public String hopperState = "empty";

    public HopperDistanceSensor(){

        CANrangeConfiguration canrangeConfig = new CANrangeConfiguration();

        canrangeConfig.ProximityParams.MinSignalStrengthForValidMeasurement = 2000; //minimun signal strength for measurement
        canrangeConfig.ProximityParams.ProximityThreshold = 0.1; // how far away the CANrange will say stuff "is detected"

        canrangeConfig.ToFParams.UpdateMode = UpdateModeValue.ShortRange100Hz; //fastest update speed, may need to change later

        //leftCanrange.getConfigurator().apply(canrangeConfig);
        rightCanrange.getConfigurator().apply(canrangeConfig);
    }{

        Boolean isFull;
        //isFull = rightCanrange.getIsDetected().getValue() && leftCanrange.getIsDetected().getValue();
        isFull = rightCanrange.getIsDetected().getValue();

    if (isFull == true) {
     hopperState = "full";  
    } else{
        hopperState = "empty";
    }
}

  

}

