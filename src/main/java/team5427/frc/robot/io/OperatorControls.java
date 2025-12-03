package team5427.frc.robot.io;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import team5427.frc.robot.Constants.DriverConstants;
import team5427.frc.robot.commands.cascade.MoveCascadeToPosition;
import team5427.frc.robot.subsystems.Cascade.CascadeConstants;

public class OperatorControls {
  private CommandXboxController joy;

  public OperatorControls() {
    joy = new CommandXboxController(DriverConstants.kOperatorJoystickPort);
    initalizeTriggers();
  }

  public OperatorControls(CommandXboxController joy) {
    this.joy = joy;
    initalizeTriggers();
  }

  /** Made private to prevent multiple calls to this method */
  private void initalizeTriggers() {
    joy.rightBumper()
      .onTrue(
        new MoveCascadeToPosition(CascadeConstants.kL2Distance)
      );
    joy.leftBumper()
      .onTrue(
        new MoveCascadeToPosition(CascadeConstants.kStowDistance)
      );
  }
}
