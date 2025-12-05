// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.grabber;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Grabber extends SubsystemBase {
  /** Creates a new Grabber. */
  private TalonFX grabberMotorOne = new TalonFX(1); // insert actual can id here

  private TalonFX grabberMotorTwo = new TalonFX(2); // insert actual can id here

  public Grabber() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setIntakeSpeed(int speed) {
    grabberMotorOne.set(speed);
    grabberMotorTwo.set(-speed); // may have to reverse
  }
  ;

  public Command setIntakeSpeedCommand(int speed) {
    return new RunCommand(
        () -> {
          setIntakeSpeed(speed);
        },
        this);
  }
}
