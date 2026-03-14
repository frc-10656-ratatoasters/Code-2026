package frc.robot.subsystems;

import com.ctre.phoenix6.configs.*;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  private final TalonFX armMotor = new TalonFX(52); // CAN ID

  // Motion Magic request. (Voltage version gives predictable behavior under load.)
  private final MotionMagicVoltage mmRequest = new MotionMagicVoltage(0);

  // Two positions (example values):
  // These are in "mechanism rotations" if you configure a rotor-to-sensor ratio.
  private static final double kDownPosition = 0.00;   // arm down
  private static final double kUpPosition   = 0.25;   // arm up (example!)
 // ---- TUNING CONSTANTS (YOU MUST TUNE THESE) ----
  // Slot0 is the main PID/FF slot used by Motion Magic.
  private static final double kP = 60.0;
  private static final double kI = 0.0;
  private static final double kD = 2.0;

  // Arm feedforward (optional but highly recommended for arms):
  // If you don't set kG, your arm may droop or fight gravity harder than necessary.
  private static final double kG = 0.25;  // start small and tune
  private static final double kS = 0.0;
  private static final double kV = 0.12;
  private static final double kA = 0.0;

  // Motion Magic profile limits (mechanism units per second)
  private static final double kCruiseVelocity = 1.0;  // rotations/sec (example)
  private static final double kAcceleration   = 2.0;  // rotations/sec^2 (example)
  private static final double kJerk           = 0.0;  // rotations/sec^3 (0 = disabled)

  // Gear ratio: motor rotations per arm rotation
  // Example: 100:1 reduction => 100 motor rot = 1 arm rot
  private static final double kRotorToMechanismRatio = 100.0;

private String state = "neutral";

  public Arm() {
    configureMotor();
  }

  private void configureMotor() {
    var cfg = new TalonFXConfiguration();

    // Feedback scaling so your position targets are in MECHANISM rotations
    cfg.Feedback.SensorToMechanismRatio = 1.0; // keep 1.0 for TalonFX internal sensor
    cfg.Feedback.RotorToSensorRatio = 1.0;     // typically 1.0 for integrated sensor
    cfg.Feedback.RotorToMechanismRatio = kRotorToMechanismRatio;

    // Neutral mode for arms is almost always Brake
    cfg.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    // (Optional) Invert if needed
    cfg.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

    // PID + FF
    cfg.Slot0.kP = kP;
    cfg.Slot0.kI = kI;
    cfg.Slot0.kD = kD;
    cfg.Slot0.kS = kS;
    cfg.Slot0.kV = kV;
    cfg.Slot0.kA = kA;

    // Gravity compensation (Phoenix 6 supports this for arms)
    cfg.Slot0.kG = kG;
    cfg.Slot0.GravityType = GravityTypeValue.Arm_Cosine;

    // Motion Magic profile
    cfg.MotionMagic.MotionMagicCruiseVelocity = kCruiseVelocity;
    cfg.MotionMagic.MotionMagicAcceleration   = kAcceleration;
    cfg.MotionMagic.MotionMagicJerk           = kJerk;

    // (Optional but recommended) Add soft limits to protect the robot
    cfg.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
    cfg.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0.35; // example
    cfg.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
    cfg.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -0.02; // example

    // Apply config
    armMotor.getConfigurator().apply(cfg);

    // If you have an absolute encoder, you’d sync your position here.
    // Otherwise you might zero on a limit switch in disabledInit/teleopInit.
  }

  /** Command the arm to a specific mechanism position (in rotations, because of RotorToMechanismRatio). */
  public void setPositionRot(double positionRot) {
    armMotor.setControl(mmRequest.withPosition(positionRot));
  }

  public void goDown() { setPositionRot(kDownPosition); }

  
  public void goUp()   { setPositionRot(kUpPosition); }

  public double getPositionRot() {
    return armMotor.getPosition().getValueAsDouble();
  }

  /** Simple “at goal” check so you can gate other actions if needed. */
  public boolean atSetpoint(double targetRot, double toleranceRot) {
    return Math.abs(getPositionRot() - targetRot) <= toleranceRot;
  }

  @Override
  public void periodic() {
    // If you want: publish position to SmartDashboard, log, etc.
    updateArmPosition();// uncomment when motor is on and set
    if (state.equals("Extend")) {
      extendArm();
    } else if (state.equals("Retract")) {
      retractArm();
    } else {
      armMotor.stopMotor();
    }
  }

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

}
