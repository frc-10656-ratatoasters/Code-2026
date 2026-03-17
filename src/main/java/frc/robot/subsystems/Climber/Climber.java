package frc.robot.subsystems.Climber;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.hardware.CANcoder;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.AutoLogOutput;

import com.ctre.phoenix6.controls.Follower;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  // Add any necessary motor controllers, sensors, or other components here
  public final TalonFX leadClimbMotor;
  private final TalonFX followerClimbMotor;
  private final double climbSpeed = .5;// from -1 to 1, might have to reverse
  public String climberState = "hold";

  private final double climbTopLimit = 0.9;
  private final double climbBottomLimit = 0.2;

  public Climber() {
    // Constructor for the Climber subsystem
    // Initialize components here
    leadClimbMotor = new TalonFX(57); // change canID
    followerClimbMotor = new TalonFX(58); // change canID later
    followerClimbMotor.setControl(new Follower(10, MotorAlignmentValue.Aligned));
    // followerClimbMotor.setControlMode(ControlModeValue.Follower,
    // leadClimbMotor.getDeviceID());
    // makes it brake when off, so it doesnt fall off
    leadClimbMotor.setNeutralMode(com.ctre.phoenix6.signals.NeutralModeValue.Brake);
    followerClimbMotor.setNeutralMode(com.ctre.phoenix6.signals.NeutralModeValue.Brake);
  }

  @AutoLogOutput
  private String logClimberState() {// feels excessive but the only way I can figure out to log it
    return climberState;
  }

  @Override
  public void periodic() {
    //logClimberState();//uncomment once motors on
    // This method will be called once per scheduler run
    if (climberState.equals("climb") && leadClimbMotor.getPosition().getValueAsDouble() < climbTopLimit) {
      leadClimbMotor.set(climbSpeed);
    } else if (climberState.equals("hold")) {
      leadClimbMotor.stopMotor();// idk the difference from set(0)
    } else if (climberState.equals("descend") && leadClimbMotor.getPosition().getValueAsDouble() > climbBottomLimit) {
      leadClimbMotor.set(-.5);
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
    leadClimbMotor.stopMotor();
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
            climber.leadClimbMotor.set(speed.getAsDouble());
          } else {
            if (climber.climberState.equals("manual")) {
              climber.leadClimbMotor.stopMotor();
            }
          }
        }, this);
  }
}