package team5427.frc.robot.commands.prong;

import edu.wpi.first.wpilibj2.command.Command;
import team5427.frc.robot.Superstructure;
import team5427.frc.robot.subsystems.ProngEffector.ProngConstants;
import team5427.frc.robot.subsystems.ProngEffector.ProngSubsystem;

public class EjectGamePiece extends Command {
  private ProngSubsystem prongSubsystem;

  public EjectGamePiece() {
    prongSubsystem = ProngSubsystem.getInstance();
    addRequirements(prongSubsystem);
  }

  @Override
  public void initialize() {
    switch (Superstructure.kSelectedGamePieceMode) {
      case CORAL -> {
        prongSubsystem.setRollerVelocity(ProngConstants.kCoralScoringVelocity);
        break;
      }

      case ALGAE -> {
        prongSubsystem.setRollerVelocity(ProngConstants.kAlgaeBargeVelocity);
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
