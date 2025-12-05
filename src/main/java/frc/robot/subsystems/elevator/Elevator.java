// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  /** Creates a new Elevator. */
  private TalonFX elevatorMotor1 = new TalonFX(3); // replace with correct CAN ID

  private TalonFX elevatorMotor2 = new TalonFX(4); // replace with correct CAN ID

  public Elevator() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setElevatorSpeed(int speed) {
    elevatorMotor1.set(speed);
    elevatorMotor2.set(speed);
  }

  public Command setElevatorSpeedCommand(int speed) {
    return new RunCommand(
        () -> {
          setElevatorSpeed(speed);
        },
        this);
  }
}
