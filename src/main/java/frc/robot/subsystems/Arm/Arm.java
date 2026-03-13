package frc.robot.subsystems.Arm;


import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.AutoLogOutput;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  // Add any necessary motor controllers, sensors, or other components here
  private String state = "neutral";
  private final TalonFX armMotor;
  private final double armTopLimit = 0;// make sure zero is arm up, or adjust later
  private final double armBottomLimit = 0.125;// in rotations, ADJUST LATER
  private final CANcoder armCANcoder = new CANcoder(54);

  
  private double armPosition = 0;
   

    public void configureCurrentLimits(){
    TalonFXConfiguration toConfigure = new TalonFXConfiguration();
    CurrentLimitsConfigs currentLimits = new CurrentLimitsConfigs();
    //lets the current limits be controlled
    currentLimits.SupplyCurrentLimitEnable = true;
    currentLimits.SupplyCurrentLimit = 50;//Highest limit of amps
    currentLimits.SupplyCurrentLowerLimit = 40; //goes down to 40 Amps if it reaches 50 amps
    currentLimits.SupplyCurrentLowerTime = 1;//lowers limit for 1 seecond]
    
    //Stator Current Limit(motor output)
    currentLimits.StatorCurrentLimitEnable = true;
    currentLimits.StatorCurrentLimit = 120;

    toConfigure.CurrentLimits = currentLimits;
    //Apply Configuration
    armMotor.getConfigurator().apply(toConfigure);

  }
  public Arm() {
    // Constructor for the Intake subsystem
    // Initialize components here
    armMotor = new TalonFX(52);

    armMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  @AutoLogOutput
  private void updateArmPosition() {// having a method for this feels excessive but its the only way i can figure
                                    // out to log arm position
    if(armCANcoder != null){
    armPosition = armCANcoder.getPosition().getValueAsDouble();// idk what unit... figure out
    SmartDashboard.putString("Arm Encoder Position", String.valueOf(armPosition));
  }
  }
  @Override
  public void periodic(){
    updateArmPosition();// uncomment when motor is on and set
    

// This method will be called once per scheduler run

  }

  // Add methods to control the arm subsystem
  

  public Command extendArmCommand() {
    return new InstantCommand(() -> {
    state = "Extend";
    }, this);
  }

  public Command retractArmCommand() {
    return new InstantCommand(() -> {
    state = "Retract";
    }, this);
  }





  /*public void retractArm() {
    if (armCANcoder.getPosition().getValueAsDouble() > armTopLimit) {
      armMotor.set(-0.3);
    } else {
      armMotor.set(0);
    }
  }

  public void extendArm() {
    if (armCANcoder.getPosition().getValueAsDouble() < armBottomLimit) {
      armMotor.set(0.3);
    } else {
      armMotor.set(0);
    }
  }*/

 
}

