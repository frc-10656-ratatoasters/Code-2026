package frc.robot.subsystems.Climber;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.hardware.CANcoder;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.AutoLogOutput;

import com.ctre.phoenix6.controls.Follower;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  // Add any necessary motor controllers, sensors, or other components here
  public final TalonFX climbMotor;
  private double climbSpeed = .1;// from -1 to 1, might have to reverse
  public String climberState = "neutral";
  private final CANcoder climbCANcoder;
  private double climbTopLimit = 2;
  private double climbBottomLimit = .5;
  private double descendSpeed = -.5;// from -1 to 1, might have to reverse
  public Climber() {
    // Constructor for the Climber subsystem
    // Initialize components here
    climbMotor = new TalonFX(57); 
    climbCANcoder = new CANcoder(58);
    // climbMotor.getDeviceID());
    // makes it brake when off, so it doesnt fall off
    climbMotor.setNeutralMode(com.ctre.phoenix6.signals.NeutralModeValue.Coast);
  }

  @AutoLogOutput
  private String logClimberState() {// feels excessive but the only way I can figure out to log it
    return climberState;
  }

  @Override
  public void periodic() {
    climbTopLimit = SmartDashboard.getNumber("input Climber Top limit, so higher rotations", 1);
    climbBottomLimit = SmartDashboard.getNumber("input Climber Bottom limit, so lower rotations", .5);
    climbSpeed = SmartDashboard.getNumber("input Climb Speed, from -1 to 1", .1);
    descendSpeed = SmartDashboard.getNumber("input Descend Speed, from -1 to 1", -.5);
    SmartDashboard.putString("Climber State", climberState);
    System.out.println("Climber Position" + climbMotor.getPosition().getValueAsDouble());
  
    SmartDashboard.putNumber("Climber motor Position", climbMotor.getPosition().getValueAsDouble());
    SmartDashboard.putNumber("Climb position from encoder", climbCANcoder.getAbsolutePosition().getValueAsDouble());

    //logClimberState();//uncomment once motors on
    if (climberState.equals("neutral")){//sets it to coast when neutral mode
      climbMotor.setNeutralMode(com.ctre.phoenix6.signals.NeutralModeValue.Coast);
      climbMotor.stopMotor();
    }
    if (climberState.equals("climb") && climbMotor.getPosition().getValueAsDouble() < climbTopLimit) {
      climbMotor.set(climbSpeed);
      if (climbMotor.getPosition().getValueAsDouble() >= climbTopLimit) {
        climberState = "hold";
      }
    } else if (climberState.equals("hold")) {
      climbMotor.setNeutralMode(com.ctre.phoenix6.signals.NeutralModeValue.Brake);
      climbMotor.stopMotor();
    } else if (climberState.equals("descend")) {
        if(climbMotor.getPosition().getValueAsDouble() > climbBottomLimit)
          climbMotor.set(descendSpeed);//this is confusing cause descend means climb actually goes up...
        else{
          climbMotor.stopMotor();
          climberState = "neutral";
        }
    }

    // This method will be called once per scheduler run
  }

  // Add methods to control the climber subsystem
  public void startClimber() {
    // Code to start the climber mechanism
    climberState = "climb";
  }

  // prob should make command
  public void stopClimber() {
    // Code to stop the climber mechanism
    // probably does some sort of locking thing so it doeesnt fall off? idk
    climbMotor.stopMotor();
    climberState = "hold";
  }

  public Command ClimbCommand() {// so theoretically this works as climbL1 command cause it stops in periodic at
                                 // the limit... TEST
    return new InstantCommand(
        () -> {
          climberState = "climb";
        },
        this);

  }

  public Command HoldClimbCommand() {
    return new InstantCommand(
        () -> {
          climberState = "hold";
        },
        this);
  }

  public Command descendClimbCommand() {
    return new InstantCommand(
        () -> {
          climberState = "descend";
        },
        this);
  }

  public Command joystickClimbCommand(DoubleSupplier speed, Climber climber){
    return new InstantCommand(
        () -> {
          if (speed.getAsDouble() != 0) {
            climber.climberState = "manual";
            climber.climbMotor.set(speed.getAsDouble());
            SmartDashboard.putString("Climber Output", Double.toString(speed.getAsDouble()));
          } else {
            climber.climberState = "hold";
            climber.climbMotor.stopMotor();
          }
        }, this);
  }
}