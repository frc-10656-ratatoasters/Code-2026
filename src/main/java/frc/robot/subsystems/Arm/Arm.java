package frc.robot.subsystems.Arm;


import java.util.Set;
import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//for magic controller PID
import com.ctre.phoenix6.controls.MotionMagicVoltage;

public class Arm extends SubsystemBase {
  // Add any necessary motor controllers, sensors, or other components here
  private final TalonFX armMotor;
  private final double armTopLimit = .05;// make sure zero is arm up, or adjust later
  private final double armBottomLimit = 0.22;// in rotations, ADJUST LATER
  private final CANcoder armCANcoder = new CANcoder(53);
  
  private static final double kP = 0.8; // adjust later
  private static final double kI = 0.0; // adjust later
  private static final double kD = 0.0; // adjust later
  //arm feedforward values?
  //makes a new PID controller
  PIDController armPidController = new PIDController(kP, kI, kD);
  
  
  private static double armPosition = 0;
   
  
  public Arm() {
    // Constructor for the Arm subsystem
    // Initialize components here
    armMotor = new TalonFX(100);
    armCANcoder.setPosition(0); //hopefully callibrates encoder to zero when robot turns on, adjust if not
    armMotor.setNeutralMode(NeutralModeValue.Brake);
    configureCurrentLimits();
    
  }
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
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateArmPosition();
  }

  @AutoLogOutput
  private void updateArmPosition() {// having a method for this feels excessive but its the only way i can figure
                                    // out to log arm position
    if(armCANcoder != null){
    armPosition = armCANcoder.getPosition().getValueAsDouble();// idk what unit... figure out
    SmartDashboard.putString("Arm Encoder Position", String.valueOf(armPosition));
  }
  }

  // Add methods to control the arm subsystem


  public void extendArm() {
    double output;
    if(armCANcoder != null && armCANcoder.getPosition().getValueAsDouble() < armBottomLimit){
      output = .65;
    }
    else{
      output = 0;
    }
    armMotor.set(output);
    SmartDashboard.putString("arm output", Double.toString(output));
  }

    public void retractArm() { 
      double output;
      if(armCANcoder != null && armCANcoder.getPosition().getValueAsDouble() > armTopLimit){
        output = -.65;
      }
      else{
        output = 0;
      }
      SmartDashboard.putString("arm output", Double.toString(output));
      armMotor.set(output);
 }
  
  public Command extendArmCommand() {
    return new InstantCommand(() -> {
    extendArm();
    }, this);}

  public Command retractArmCommand() {
    return new InstantCommand(() -> {
      retractArm();
    }, this);
  }

  public void manualArm (DoubleSupplier speed, Arm arm){
    if (armCANcoder.getPosition().getValueAsDouble() > armTopLimit && speed.getAsDouble() < 0){
    armMotor.set(speed.getAsDouble());
      } else if (armCANcoder.getPosition().getValueAsDouble() < armBottomLimit && speed.getAsDouble() > 0){
      armMotor.set(speed.getAsDouble());
    } else {
      armMotor.set(0);
    }
  }

  public Command manualArmCommand(DoubleSupplier speed, Arm arm) {
    return new InstantCommand(() -> {
      manualArm(speed, arm);
    }, this);

  }


}

 


