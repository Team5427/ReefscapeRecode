package team5427.frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.Command;
import team5427.frc.robot.Superstructure;
import team5427.frc.robot.Superstructure.ClimbStep;
import team5427.frc.robot.subsystems.Cascade.CascadeConstants;
import team5427.frc.robot.subsystems.Cascade.CascadeSubsystem;
import team5427.frc.robot.subsystems.Climb.ClimbConstants;
import team5427.frc.robot.subsystems.Climb.ClimbSubsystem;
import team5427.frc.robot.subsystems.ProngEffector.ProngConstants;
import team5427.frc.robot.subsystems.ProngEffector.ProngSubsystem;

public class Climb extends Command {
  private CascadeSubsystem cascadeSubsystem;
  private ProngSubsystem prongSubsystem;
  private ClimbSubsystem climbSubsystem;

  public Climb() {
    cascadeSubsystem = CascadeSubsystem.getInstance();
    prongSubsystem = ProngSubsystem.getInstance();
    climbSubsystem = ClimbSubsystem.getInstance();
    addRequirements(cascadeSubsystem, prongSubsystem, climbSubsystem);
  }

  @Override
  public void initialize() {
    switch (Superstructure.KSelectedClimbStep) {
      case RESET -> {
        Superstructure.KSelectedClimbStep = ClimbStep.PREP;
      }
      case PREP -> {
        cascadeSubsystem.setCascadeSetpoint(CascadeConstants.kStowDistance);
        cascadeSubsystem.setPivotSetpoint(CascadeConstants.kClimbPrepRotation);
        prongSubsystem.setWristSetpoint(ProngConstants.kStowPosition);
        climbSubsystem.setHookSetpoint(ClimbConstants.kPrepPosition);
        Superstructure.KSelectedClimbStep = ClimbStep.ACTIVATE;
        break;
      }
      case ACTIVATE -> {
        cascadeSubsystem.setCascadeSetpoint(CascadeConstants.kStowDistance);
        cascadeSubsystem.setPivotSetpoint(CascadeConstants.kClimbPrepRotation);
        prongSubsystem.setWristSetpoint(ProngConstants.kStowPosition);
        climbSubsystem.setHookSetpoint(ClimbConstants.kActivePosition);
        Superstructure.KSelectedClimbStep = ClimbStep.CLIMB;
        break;
      }
      case CLIMB -> {
        cascadeSubsystem.setCascadeSetpoint(CascadeConstants.kZeroPosition);
        cascadeSubsystem.setPivotSetpoint(CascadeConstants.kTempClimbRotation);
        prongSubsystem.setWristSetpoint(ProngConstants.kClimbRotation);
        climbSubsystem.setHookSetpoint(ClimbConstants.kActivePosition);
        break;
      }
    }
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    super.execute();
  }

  @Override
  public boolean isFinished() {
    // TODO Auto-generated method stub
    return super.isFinished();
  }

  @Override
  public void end(boolean interrupted) {
    // TODO Auto-generated method stub
    super.end(interrupted);
  }
}
