package team5427.frc.robot.commands.cascade;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import team5427.frc.robot.subsystems.Cascade.CascadeConstants;
import team5427.frc.robot.subsystems.Cascade.CascadeSubsystem;

public class MoveCascadeToPosition extends Command {
  private CascadeSubsystem cascade;
  private Distance distance;

  public MoveCascadeToPosition(Distance distance) {
    cascade = CascadeSubsystem.getInstance();
    addRequirements(cascade);

    this.distance = distance;
  }

  @Override
  public void initialize() {
    cascade.setCascadeSetpoint(distance);
    cascade.setPivotSetpoint(CascadeConstants.kStowRotation);
  }

  @Override
  public boolean isFinished() {
    return cascade.cascadeAtGoal();
  }
  
  @Override
  public void end(boolean interrupted) {
    // TODO Auto-generated method stub
    super.end(interrupted);
  }
}
