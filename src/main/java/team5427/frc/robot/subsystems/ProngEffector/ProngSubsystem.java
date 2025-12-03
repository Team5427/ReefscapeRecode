package team5427.frc.robot.subsystems.ProngEffector;

import static edu.wpi.first.units.Units.MetersPerSecond;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lombok.Getter;
import lombok.Setter;
import team5427.frc.robot.Superstructure.ProngEffectorStates;
import team5427.frc.robot.subsystems.ProngEffector.io.ProngIO;
import team5427.frc.robot.subsystems.ProngEffector.io.ProngIOInputsAutoLogged;
import team5427.frc.robot.subsystems.ProngEffector.io.ProngIOTalon;

public class ProngSubsystem extends SubsystemBase {
  public static enum GamePieceMode {
    CORAL,
    ALGAE
  }

  @Getter @Setter private static GamePieceMode gamePieceMode = GamePieceMode.CORAL;

  private ProngIO io;
  private ProngIOInputsAutoLogged inputsAutoLogged = new ProngIOInputsAutoLogged();

  public static ProngSubsystem m_instance;

  @Getter @Setter private Rotation2d wristSetpoint;
  @Getter @Setter private LinearVelocity rollerVelocity;

  private ProngEffectorStates state;

  public static ProngSubsystem getInstance() {
    if (m_instance == null) m_instance = new ProngSubsystem();
    return m_instance;
  }

  public ProngSubsystem() {
    io = new ProngIOTalon();
    rollerVelocity = MetersPerSecond.of(-0.5);
    wristSetpoint = Rotation2d.kZero;
    state = ProngEffectorStates.IDLE;
  }

  @Override
  public void periodic() {
    io.setRollerSpeeds(rollerVelocity);
    io.setWristSetpoint(wristSetpoint);

    io.updateInputs(inputsAutoLogged);
    Logger.processInputs("ProngEffector", inputsAutoLogged);
    Logger.recordOutput("Has Object", hasObject());
  }

  public boolean hasObject() {
    return io.hasResistance();
  }
}
