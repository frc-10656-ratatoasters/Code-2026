package frc.robot.subsystems.leds;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.AutoLogOutput;


import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDs extends SubsystemBase {
  // Add any necessary motor controllers, sensors, or other components here
  private String blinkinState = "neutral";

  private final SparkMax blinkin; 

  public LEDs() {
    
    // Initialize components here
    blinkin = null; // Replace 0 with the actual PWM channel

  }

  @Override
  public void periodic() {

    if (blinkinState == "red") {
      red();
    } else if (blinkinState == "blue") {
      blue();
    } else if (blinkinState == "neutral") {
      neutral();
    }
    // This method will be called once per scheduler run
  }

  // Add methods to control the intake subsystem
  public void red() {
    blinkin.set(0.5); //check the number for the color
    // Code to start the intake mechanism
  }

  public void SetStateToRed() {
    blinkinState = "red";
  }

  public void blue() {
    blinkin.set(0.98); //check the number for the color
  }

  public void setStateToBlue() {
    blinkinState = "blue";
  }

  public void neutral(){
    blinkin.set(0); //check the number for the color
  }
  public Command setStateToOff() {
    return new InstantCommand(
        () -> {
          blinkinState = "neutral";
        },
        this);
  }
}
