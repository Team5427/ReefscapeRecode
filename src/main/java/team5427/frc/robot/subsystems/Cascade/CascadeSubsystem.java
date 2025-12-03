package team5427.frc.robot.subsystems.Cascade;

import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lombok.Getter;
import lombok.Setter;

import org.littletonrobotics.junction.Logger;
import team5427.frc.robot.subsystems.Cascade.io.CascadeIO;
import team5427.frc.robot.subsystems.Cascade.io.CascadeIOInputsAutoLogged;
import team5427.frc.robot.subsystems.Cascade.io.CascadeIOMagicTalon;

public class CascadeSubsystem extends SubsystemBase {
  private CascadeIO io;
  private CascadeIOInputsAutoLogged inputs;

  private static CascadeSubsystem m_instance;

  @Getter @Setter private Distance cascadeSetpoint = CascadeConstants.kStowDistance;
  @Getter @Setter private Rotation2d pivotSetpoint = CascadeConstants.kStowRotation;

  public static CascadeSubsystem getInstance() {
    if (m_instance == null) m_instance = new CascadeSubsystem();
    return m_instance;
  }

  public CascadeSubsystem() {
    io = new CascadeIOMagicTalon();
    inputs = new CascadeIOInputsAutoLogged();
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Cascade", inputs);

    io.setCascadeSetpoint(cascadeSetpoint);
    io.setPivotSetpoint(pivotSetpoint);
  }

  public boolean cascadeAtGoal() {
    return Math.abs(inputs.cascadeHeight.minus(cascadeSetpoint).in(Meters)) < CascadeConstants.kCascadeTolerance.in(Meters);
  }
}
