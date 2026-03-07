package frc.robot.subsystems.Climber;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.hardware.CANcoder;

import org.littletonrobotics.junction.AutoLogOutput;

import com.ctre.phoenix6.controls.Follower;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  // Add any necessary motor controllers, sensors, or other components here
  private final TalonFX leadElevatorMotor;
  private final TalonFX followerElevatorMotor;
  private final double power = .1;//TEST ON GROUND FIRST probably waaaay to low, adjust later
  private String climberState = "neutral";
  private final CANcoder hangEncoder;
  private double hangPosition = 0;
  //private final double hangTopLimit = 0.9;
  //private final double hangBottomLimit = 0.2;
  @AutoLogOutput
  private void updateHangPosition(){
    hangPosition = hangEncoder.getPosition().getValueAsDouble();
  }
  public Climber() {
    // Constructor for the Climber subsystem
    // Initialize components here
    leadElevatorMotor = new TalonFX(57); //change canID
    followerElevatorMotor = new TalonFX(58); //change canID later
    followerElevatorMotor.setControl(new Follower (10,MotorAlignmentValue.Aligned));
    hangEncoder = new CANcoder(60);//maybe 60s can be hang stuff
    //may have to invert??
   //followerElevatorMotor.setControlMode(ControlModeValue.Follower, leadElevatorMotor.getDeviceID());
       //makes it brake when off, so it doesnt fall off
    leadElevatorMotor.setNeutralMode(com.ctre.phoenix6.signals.NeutralModeValue.Brake);
    followerElevatorMotor.setNeutralMode(com.ctre.phoenix6.signals.NeutralModeValue.Brake);
  }

  @Override
  public void periodic() {
    updateHangPosition();
    // This method will be called once per scheduler run
    if(climberState == "climb"){
      
    }
    else if(climberState == "hold"){
      stopClimber();
    }
    else if (climberState == "neutral"){
      leadElevatorMotor.set(0);
     }
     
    // This method will be called once per scheduler run
  }
  

  // Add methods to control the climber subsystem
  public void startClimber() {
    // Code to start the climber mechanism
    climberState = "climb";
    }
   

//prob should make command
  public void stopClimber() {
    // Code to stop the climber mechanism
    //probably does some sort of locking thing so it doeesnt fall off? idk
    leadElevatorMotor.stopMotor();
    climberState = "hold";
  }

  public Command ClimbCommand(){
    return new InstantCommand(
        () -> {
            climberState = "climb";
        },
        this);
  
  }

  public Command HoldClimbCommand(){
    return new InstantCommand(
        () -> {
            climberState = "hold";
        },
        this);
  }

  public void autoClimbL1(double power){
    leadElevatorMotor.set(power);
      leadElevatorMotor.stopMotor();
  }

}