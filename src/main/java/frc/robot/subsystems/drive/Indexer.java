package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXSConfiguration;
import com.ctre.phoenix6.configs.TalonFXSConfigurator;
import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.signals.MotorArrangementValue;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase{
    
    final TalonFXS IndexMotor;
    TalonFXSConfiguration IndexConfig;

    boolean indexing;
    double indexSpd;
    Shooter partShooter;
    boolean shooting;

    public Indexer(Shooter partnerShooter) {

        IndexMotor = new TalonFXS(62);
        IndexConfig = new TalonFXSConfiguration();

        IndexConfig.Commutation.MotorArrangement = MotorArrangementValue.Minion_JST;
        IndexMotor.getConfigurator().apply(IndexConfig);

        indexing = false;
        indexSpd = 0.05;

        partShooter = partnerShooter;
    }

    public InstantCommand toggle() {
        return new InstantCommand(
        () -> {indexing = !indexing;}
        );
    }

    @Override
    public void periodic() {
    
    if (indexing == true && shooting == true)
    {
        IndexMotor.set(indexSpd);
    } else if (indexing == true && shooting == false)
    {
        indexing = false;
    } else
    {
        IndexMotor.stopMotor();
    }
    
    shooting = partShooter.shooting;

    }
}
