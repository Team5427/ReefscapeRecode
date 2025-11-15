package team5427.frc.robot.subsystems.Cascade;

import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.Meters;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team5427.frc.robot.subsystems.Cascade.io.CascadeIO;
import team5427.frc.robot.subsystems.Cascade.io.CascadeIOInputsAutoLogged;
import team5427.frc.robot.subsystems.Cascade.io.CascadeIOMagicTalon;

public class CascadeSubsystem extends SubsystemBase {
    private CascadeIO io;
    private CascadeIOInputsAutoLogged inputs;
    
    private static CascadeSubsystem m_instance;

    private Distance setpoint;

    public static CascadeSubsystem getInstance() {
        if (m_instance==null)
            m_instance = new CascadeSubsystem();
        return m_instance;
    }

    public CascadeSubsystem() {
        io = new CascadeIOMagicTalon();
        inputs = new CascadeIOInputsAutoLogged();
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Inputs AutoLogged", inputs);
        io.setCascadeSetpoint(setpoint);
    }

    public void setSetpoint(Distance setpoint) {
        this.setpoint = setpoint;
    }

    public boolean cascadeAtGoal() {
        return inputs.cascadeMasterDistance.minus(setpoint).in(Meters)<0.1;
    }
}
