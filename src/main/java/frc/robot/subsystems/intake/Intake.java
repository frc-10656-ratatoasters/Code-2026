package frc.robot.subsystems.intake;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  // Add any necessary motor controllers, sensors, or other components here
  private String intakeState = "neutral"; 
  private final TalonFX intakeMotor; // States our motor name
  private final TalonFX armMotor;
  private final double armTopLimit = 0;//make sure zero is arm up, or adjust later
  private final double armBottomLimit = 0.125;// in rotations, ADJUST LATER
  public Intake () {
    // Constructor for the Intake subsystem
    // Initialize components here
    intakeMotor = new TalonFX(51);
    armMotor = new TalonFX(52);
  }

  @Override
  public void periodic() {
// Tells what state the intake is at
    if(intakeState == "intake"){
      intake();
    }
    else if(intakeState == "outtake"){
      outake();
    }
    else if (intakeState == "neutral"){
      stopIntake();
    }
    // This method will be called once per scheduler run
  }

  // Add methods to control the intake subsystem
  public void intake() {
    intakeMotor.set (0.5);
    // Code to start the intake mechanism
  }
 

  
  public Command extendArmCommand(){
    return new InstantCommand(()->{
      extendArm();
    }, this);
  }
  public Command retractArmCommand(){
    return new InstantCommand(()->{
      retractArm();
    }, this);
  }

  public Command IntakeCommand(){
    return new InstantCommand(
        () -> {
            intakeState = "intake";
        },
        this);
  }
  public void outake(){

    intakeMotor.set(-0.5);
  }
  public Command OuttakeCommand(){
    return new InstantCommand(
        () -> {
            intakeState = "outtake";
        },
        this);
  }
  
  public Command stopIntakeCommand(){
    return new InstantCommand(
        () -> {
            intakeState = "neutral";
        },
        this);
  }

  public void setIntakeSpeed(int speed) {// BE CAREFUL doesnt put in limits and stuff
    intakeMotor.set(-speed); //dont remember why its backwards but scared to change it
  }

  public void retractArm(){
    if(armMotor.getPosition().getValueAsDouble() > armTopLimit){
        armMotor.set(-0.3);
        } 
    else {
        armMotor.set(0);
        }
    }
    public void extendArm(){
      if(armMotor.getPosition().getValueAsDouble() < armBottomLimit){
          armMotor.set(0.3);
          } 
      else {
          armMotor.set(0);
          }
  }


  public void stopIntake() {
    // Code to stop the intake mechanism
    intakeMotor.set(0);
  }
}
