package team5427.frc.robot.subsystems.ProngEffector;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lombok.Getter;
import lombok.Setter;
import org.littletonrobotics.junction.Logger;
import team5427.frc.robot.Superstructure.GamePieceMode;
import team5427.frc.robot.subsystems.ProngEffector.io.ProngIO;
import team5427.frc.robot.subsystems.ProngEffector.io.ProngIOInputsAutoLogged;
import team5427.frc.robot.subsystems.ProngEffector.io.ProngIOTalon;

public class ProngSubsystem extends SubsystemBase {

  @Getter @Setter private static GamePieceMode gamePieceMode = GamePieceMode.CORAL;

  private ProngIO io;
  private ProngIOInputsAutoLogged inputsAutoLogged = new ProngIOInputsAutoLogged();

  private static ProngSubsystem m_instance;

  @Getter @Setter private Rotation2d wristSetpoint;
  @Getter @Setter private LinearVelocity rollerVelocity;

  public static ProngSubsystem getInstance() {
    if (m_instance == null) m_instance = new ProngSubsystem();
    return m_instance;
  }

  private ProngSubsystem() {
    io = new ProngIOTalon();
    rollerVelocity = ProngConstants.kStowVelocity;
    wristSetpoint = Rotation2d.kZero;
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
