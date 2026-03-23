// package frc.robot.commands.autos;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import frc.robot.subsystems.intake.Intake;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// public class IntakeCommand extends InstantCommand{
//     Intake intake;
//     Boolean intakeRan = false;
    
//     public void intakeCommand(Intake intake){
//         this.intake = intake;
//         SmartDashboard.putBoolean("intakeRan", intakeRan);
//     }
//     @Override
//     public void initialize(){
//         intake.SetStateToIntake();
//         intakeRan = true;
//         SmartDashboard.putBoolean("intakeRan", intakeRan);

//     }
// }
