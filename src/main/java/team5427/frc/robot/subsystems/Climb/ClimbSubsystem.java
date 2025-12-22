package team5427.frc.robot.subsystems.Climb;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lombok.Setter;
import team5427.frc.robot.subsystems.Climb.io.ClimbIO;
import team5427.frc.robot.subsystems.Climb.io.ClimbIOInputsAutoLogged;
import team5427.frc.robot.subsystems.Climb.io.ClimbIOTalon;

public class ClimbSubsystem extends SubsystemBase {
  private ClimbIO io;
  private ClimbIOInputsAutoLogged inputsAutoLogged;

  @Setter private Rotation2d hookSetpoint = new Rotation2d();

  private static ClimbSubsystem m_instance;

  public static ClimbSubsystem getInstance() {
    return m_instance == null ? new ClimbSubsystem() : m_instance;
  }

  private ClimbSubsystem() {
    io = new ClimbIOTalon();
    inputsAutoLogged = new ClimbIOInputsAutoLogged();
  }

  @Override
  public void periodic() {
    io.setHookSetpoint(hookSetpoint);
    io.updateInputs(inputsAutoLogged);
  }

  public void setPosition(Rotation2d angle) {
    io.setHookPosition(angle);
  }
}
