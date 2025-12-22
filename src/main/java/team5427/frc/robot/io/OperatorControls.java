package team5427.frc.robot.io;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import team5427.frc.robot.Constants.DriverConstants;
import team5427.frc.robot.Superstructure;
import team5427.frc.robot.Superstructure.GamePieceMode;
import team5427.frc.robot.Superstructure.GamePieceMode.GamePieceTriggers;
import team5427.frc.robot.Superstructure.ProngStates;
import team5427.frc.robot.commands.ResetSubsystems;
import team5427.frc.robot.commands.cascade.MoveCascadeAll;
import team5427.frc.robot.commands.climb.Climb;
import team5427.frc.robot.commands.prong.EjectGamePiece;
import team5427.frc.robot.commands.prong.MoveProngAll;
import team5427.frc.robot.commands.prong.RollerVelocity;
import team5427.frc.robot.commands.prong.RotateWrist;
import team5427.frc.robot.subsystems.Cascade.CascadeConstants;
import team5427.frc.robot.subsystems.ProngEffector.ProngConstants;

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
    Superstructure.disabledTrigger
        .toggleOnTrue(
            new InstantCommand(
                () -> {
                  Superstructure.kSelectedProngState = ProngStates.DISABLED;
                }))
        .toggleOnFalse(
            new InstantCommand(
                () -> {
                  Superstructure.kSelectedProngState = ProngStates.IDLE;
                }));
    joy.a()
        .onTrue(
            new ConditionalCommand(
                new MoveCascadeAll(CascadeConstants.kL1Distance, CascadeConstants.kL1Rotation),
                new MoveCascadeAll(
                    CascadeConstants.kProcessorDistance, CascadeConstants.kProcessorRotation),
                GamePieceTriggers.kCoral))
        .onTrue(
            new ConditionalCommand(
                new RotateWrist(ProngConstants.kL1Rotation),
                new RotateWrist(ProngConstants.kProcessorPosition),
                GamePieceTriggers.kCoral));
    joy.x()
        .onTrue(
            new ConditionalCommand(
                new MoveCascadeAll(CascadeConstants.kL2Distance, CascadeConstants.kL2Rotation),
                new MoveCascadeAll(
                    CascadeConstants.kLowReefAlgaeDistance, CascadeConstants.kLowReefAlgaeRotation),
                GamePieceTriggers.kCoral))
        .onTrue(
            new ConditionalCommand(
                new RotateWrist(ProngConstants.kL2Rotation),
                new MoveProngAll(
                    ProngConstants.kAlgaeReefIntakeVelocity, ProngConstants.kLowReefAlgaeRotation),
                GamePieceTriggers.kCoral));
    joy.b()
        .onTrue(
            new ConditionalCommand(
                new MoveCascadeAll(CascadeConstants.kL3Distance, CascadeConstants.kL3Rotation),
                new MoveCascadeAll(
                    CascadeConstants.kHighReefAlgaeDistance,
                    CascadeConstants.kHighReefAlgaeRotation),
                GamePieceTriggers.kCoral))
        .onTrue(
            new ConditionalCommand(
                new RotateWrist(ProngConstants.kL3Rotation),
                new MoveProngAll(
                    ProngConstants.kAlgaeReefIntakeVelocity, ProngConstants.kHighReefAlgaeRotation),
                GamePieceTriggers.kCoral));
    joy.y()
        .onTrue(
            new ConditionalCommand(
                new MoveCascadeAll(CascadeConstants.kL4Distance, CascadeConstants.kL4Rotation),
                new MoveCascadeAll(
                    CascadeConstants.kBargeDistance, CascadeConstants.kBargeRotation),
                GamePieceTriggers.kCoral))
        .onTrue(
            new ConditionalCommand(
                new RotateWrist(ProngConstants.kL4Rotation),
                new RotateWrist(ProngConstants.kBargePosition),
                GamePieceTriggers.kCoral));

    joy.leftTrigger()
        .whileTrue(
            new ConditionalCommand(
                new MoveCascadeAll(
                    CascadeConstants.kRSCIntakeDistance, CascadeConstants.kIntakeRotation),
                new MoveCascadeAll(
                    CascadeConstants.kFloorIntakeDistance, CascadeConstants.kFloorIntakeRotation),
                GamePieceTriggers.kCoral))
        .whileTrue(
            new ConditionalCommand(
                new MoveCascadeAll(
                    CascadeConstants.kRSCIntakeDistance, CascadeConstants.kIntakeRotation),
                new MoveCascadeAll(
                    CascadeConstants.kFloorIntakeDistance, CascadeConstants.kFloorIntakeRotation),
                GamePieceTriggers.kCoral))
        .onFalse(new ConditionalCommand(new ResetSubsystems(), null, GamePieceTriggers.kCoral));
    joy.rightTrigger()
        .whileTrue(new EjectGamePiece())
        .onFalse(
            new ConditionalCommand(
                new RollerVelocity(ProngConstants.kStowVelocity),
                new RollerVelocity(ProngConstants.kAlgaeFloorIntakeVelocity),
                GamePieceTriggers.kCoral));

    joy.povDown().onTrue(new ResetSubsystems());

    joy.povUp().onTrue(new Climb());

    joy.povLeft()
        .onTrue(
            new InstantCommand(() -> Superstructure.kSelectedGamePieceMode = GamePieceMode.ALGAE));
    joy.povRight()
        .onTrue(
            new InstantCommand(() -> Superstructure.kSelectedGamePieceMode = GamePieceMode.CORAL));
  }
}
