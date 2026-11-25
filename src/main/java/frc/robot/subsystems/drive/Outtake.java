package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.hardware.TalonFX;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Outtake extends SubsystemBase {

/*     
    final TalonFX ShootMotor;
    public boolean shooting;
    double shootSpd;
        */  
    public Outtake() {
    Shooter Skooter = new Shooter();
    Indexer indie = new Indexer(Skooter);
    }

    public Command doOuttake() {
    Shooter Skooter = new Shooter();
    Indexer indie = new Indexer(Skooter);      
    Skooter.toggle();
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e)
    {
        Thread.currentThread().interrupt();
    }
    indie.toggle();
    try {
        Thread.sleep(5000);
    } catch (InterruptedException e)
    {
        Thread.currentThread().interrupt();
    }
    indie.toggle();
    Skooter.toggle();


       // ShootMotor = new TalonFX(61);
        //shooting = false;
        //shootSpd = 0.3;
    }
  /*       
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
*/
}
/* 

    }

} */
