package frc.robot.subsystems.Climber;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ControlModeValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.controls.Follower;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  // Add any necessary motor controllers, sensors, or other components here
  private final TalonFX leadElevatorMotor;
  private final TalonFX followerElevatorMotor;
  private final double power = .01;//TEST ON GROUND FIRST
  public Climber() {
    // Constructor for the Climber subsystem
    // Initialize components here
    leadElevatorMotor = new TalonFX(57); //change canID
    followerElevatorMotor = new TalonFX(58); //change canID later

    followerElevatorMotor.setControl(new Follower (10,MotorAlignmentValue.Aligned));
    //may have to invert??
   //followerElevatorMotor.setControlMode(ControlModeValue.Follower, leadElevatorMotor.getDeviceID());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // Add methods to control the climber subsystem
  public void startClimber() {
    // Code to start the climber mechanism
   

  }
//prob should make command
  public void stopClimber() {
    // Code to stop the climber mechanism
    //probably does some sort of locking thing so it doeesnt fall off? idk
    leadElevatorMotor.set(0);
  }

}
