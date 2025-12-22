package team5427.frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import org.littletonrobotics.junction.Logger;

public final class Superstructure {
  public static final String dashboardKey = "/Superstructure";

  public static final Trigger disabledTrigger =
      new Trigger(
          () -> {
            return DriverStation.isDisabled();
          });

  public static final Trigger autonTrigger =
      new Trigger(
          () -> {
            return DriverStation.isAutonomous();
          });

  public static enum SwerveStates {
    RAW_DRIVING,
    CONTROLLED_DRIVING,
    AUTO_ALIGN,
    INTAKE_ASSISTANCE,
    AUTON,
    DISABLED;

    public static class SwerveTriggers {
      public static final Trigger kRawDriving =
          new Trigger(
              () -> {
                return kSelectedSwerveState.equals(RAW_DRIVING);
              });
      public static final Trigger kControlledDriving =
          new Trigger(
              () -> {
                return kSelectedSwerveState.equals(CONTROLLED_DRIVING);
              });
      public static final Trigger kAuto_Align =
          new Trigger(
              () -> {
                return kSelectedSwerveState.equals(AUTO_ALIGN);
              });
      public static final Trigger kIntake_Assistance =
          new Trigger(
              () -> {
                return kSelectedSwerveState.equals(INTAKE_ASSISTANCE);
              });
      public static final Trigger kAuton =
          new Trigger(
              () -> {
                return kSelectedSwerveState.equals(AUTON);
              });
      public static final Trigger kDisabled =
          new Trigger(
              () -> {
                return kSelectedSwerveState.equals(DISABLED);
              });
    }
  }

  public static SwerveStates kSelectedSwerveState = SwerveStates.DISABLED;

  public static enum ProngStates {
    ALGAE_INTAKE,
    ALGAE_OUTAKE,
    CORAL_INTAKE,
    CORAL_OUTAKE,
    CORAL_STOWED,
    ALGAE_STOWED,
    DISABLED,
    IDLE;

    public static class ProngTriggers {
      public static final Trigger kAlgae_Intake =
          new Trigger(
              () -> {
                return kSelectedProngState.equals(ALGAE_INTAKE);
              });
      public static final Trigger kAlgae_Outake =
          new Trigger(
              () -> {
                return kSelectedProngState.equals(ALGAE_OUTAKE);
              });
      public static final Trigger kCoral_Intake =
          new Trigger(
              () -> {
                return kSelectedProngState.equals(CORAL_INTAKE);
              });
      public static final Trigger kCoral_Outake =
          new Trigger(
              () -> {
                return kSelectedProngState.equals(CORAL_OUTAKE);
              });
      public static final Trigger kCoral_Stowed =
          new Trigger(
              () -> {
                return kSelectedProngState.equals(CORAL_STOWED);
              });
      public static final Trigger kAlgae_Stowed =
          new Trigger(
              () -> {
                return kSelectedProngState.equals(ALGAE_STOWED);
              });
      public static final Trigger kDisabled =
          new Trigger(
              () -> {
                return kSelectedProngState.equals(DISABLED);
              });
      public static final Trigger kIdle =
          new Trigger(
              () -> {
                return kSelectedProngState.equals(IDLE);
              });
    }
  }

  public static ProngStates kSelectedProngState = ProngStates.DISABLED;

  public static enum GamePieceMode {
    CORAL,
    ALGAE;

    public static class GamePieceTriggers {
      public static final Trigger kCoral =
          new Trigger(
              () -> {
                return kSelectedGamePieceMode.equals(GamePieceMode.CORAL);
              });
      public static final Trigger kAlgae =
          new Trigger(
              () -> {
                return kSelectedGamePieceMode.equals(GamePieceMode.ALGAE);
              });
    }
  }

  public static GamePieceMode kSelectedGamePieceMode = GamePieceMode.CORAL;

  public static enum ClimbStep {
    RESET,
    PREP,
    ACTIVATE,
    CLIMB;

    public static class ClimbStepTriggers {
      public static final Trigger kReset =
          new Trigger(
              () -> {
                return KSelectedClimbStep.equals(ClimbStep.RESET);
              });
      public static final Trigger kPrep =
          new Trigger(
              () -> {
                return KSelectedClimbStep.equals(ClimbStep.PREP);
              });
      public static final Trigger kActivate =
          new Trigger(
              () -> {
                return KSelectedClimbStep.equals(ClimbStep.ACTIVATE);
              });
      public static final Trigger kClimb =
          new Trigger(
              () -> {
                return KSelectedClimbStep.equals(ClimbStep.CLIMB);
              });
    }
  }

  public static ClimbStep KSelectedClimbStep = ClimbStep.RESET;

  public static void logStates() {
    Logger.recordOutput(dashboardKey + "/" + "SwerveState", kSelectedSwerveState);
    Logger.recordOutput(dashboardKey + "/" + "ProngState", kSelectedProngState);
  }
}
