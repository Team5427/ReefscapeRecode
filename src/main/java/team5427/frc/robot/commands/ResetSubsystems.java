package team5427.frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import team5427.frc.robot.Superstructure;
import team5427.frc.robot.Superstructure.ClimbStep;
import team5427.frc.robot.subsystems.Cascade.CascadeConstants;
import team5427.frc.robot.subsystems.Cascade.CascadeSubsystem;
import team5427.frc.robot.subsystems.ProngEffector.ProngConstants;
import team5427.frc.robot.subsystems.ProngEffector.ProngSubsystem;

public class ResetSubsystems extends Command {
  private CascadeSubsystem cascadeSubsystem;
  private ProngSubsystem prongSubsystem;

  public ResetSubsystems() {
    cascadeSubsystem = CascadeSubsystem.getInstance();
    prongSubsystem = ProngSubsystem.getInstance();
    addRequirements(cascadeSubsystem, prongSubsystem);
  }

  @Override
  public void initialize() {
    switch (Superstructure.kSelectedGamePieceMode) {
      case CORAL -> {
        cascadeSubsystem.setCascadeSetpoint(CascadeConstants.kStowDistance);
        cascadeSubsystem.setPivotSetpoint(CascadeConstants.kStowRotation);

        prongSubsystem.setWristSetpoint(ProngConstants.kStowPosition);
        prongSubsystem.setRollerVelocity(ProngConstants.kStowVelocity);
        break;
      }

      case ALGAE -> {
        cascadeSubsystem.setCascadeSetpoint(CascadeConstants.kStowDistance);
        cascadeSubsystem.setPivotSetpoint(CascadeConstants.kStowRotation);

        prongSubsystem.setWristSetpoint(ProngConstants.kAlgaeStowPosition);
        prongSubsystem.setRollerVelocity(ProngConstants.kAlgaeFloorIntakeVelocity);
        break;
      }
    }

    Superstructure.KSelectedClimbStep = ClimbStep.RESET;
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
