package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    
    final TalonFX ShootMotor;
    boolean shooting;
    double shootSpd;

    public Shooter() {

        ShootMotor = new TalonFX(61);

        shooting = false;
        shootSpd = 0.3;
    }
//Spamton mining sim -Snow
//... -Roman
    public InstantCommand toggle() {
        return new InstantCommand(
        () -> {shooting = !shooting;}
        );
    }

    @Override
    public void periodic() {
    Logger.recordOutput("Shooting Variable: ", shooting);  

    if (shooting == true)
    {
        ShootMotor.set(shootSpd);
    } else
    {
        ShootMotor.stopMotor();
    }

    }
}
