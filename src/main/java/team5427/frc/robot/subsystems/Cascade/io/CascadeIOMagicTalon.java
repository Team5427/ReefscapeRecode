package team5427.frc.robot.subsystems.Cascade.io;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.ParentDevice;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import team5427.frc.robot.subsystems.Cascade.CascadeConstants;
import team5427.lib.motors.MagicSteelTalonFX;
import team5427.lib.motors.MotorConfiguration;

public class CascadeIOMagicTalon implements CascadeIO {
    private MagicSteelTalonFX cascadeMotorMaster;
    private MagicSteelTalonFX cascadeMotorSlave;
    private MagicSteelTalonFX pivotMotorMaster;
    private MagicSteelTalonFX pivotMotorSlave;
    
    private CANcoder pivotCANcoder;

    private StatusSignal<Distance> cascadeMasterDistance;
    private StatusSignal<LinearVelocity> cascadeMasterVelocity;
    private StatusSignal<LinearAcceleration> cascadeMasterAcceleration;
    private StatusSignal<Voltage> cascadeMasterVoltage;
    private StatusSignal<Current> cascadeMasterCurrent;
    private StatusSignal<Temperature> cascadeMasterTemperature;

    private StatusSignal<Distance> cascadeSlaveDistance;
    private StatusSignal<LinearVelocity> cascadeSlaveVelocity;
    private StatusSignal<LinearAcceleration> cascadeSlaveAcceleration;
    private StatusSignal<Voltage> cascadeSlaveVoltage;
    private StatusSignal<Current> cascadeSlaveCurrent;
    private StatusSignal<Temperature> cascadeSlaveTemperature;

    private StatusSignal<Rotation2d> pivotMasterDistance;
    private StatusSignal<AngularVelocity> pivotMasterVelocity;
    private StatusSignal<AngularAcceleration> pivotMasterAcceleration;
    private StatusSignal<Voltage> pivotMasterVoltage;
    private StatusSignal<Current> pivotMasterCurrent;
    private StatusSignal<Temperature> pivotMasterTemperature;

    private StatusSignal<Rotation2d> pivotSlaveDistance;
    private StatusSignal<AngularVelocity> pivotSlaveVelocity;
    private StatusSignal<AngularAcceleration> pivotSlaveAcceleration;
    private StatusSignal<Voltage> pivotSlaveVoltage;
    private StatusSignal<Current> pivotSlaveCurrent;
    private StatusSignal<Temperature> pivotSlaveTemperature;

    public CascadeIOMagicTalon() {
        cascadeMotorMaster = new MagicSteelTalonFX(CascadeConstants.kCascadeMasterId);
        cascadeMotorSlave = new MagicSteelTalonFX(CascadeConstants.kCascadeSlaveId);
        pivotMotorMaster = new MagicSteelTalonFX(CascadeConstants.kPivotMasterId);
        pivotMotorSlave = new MagicSteelTalonFX(CascadeConstants.kPivotSlaveId);

        cascadeMotorMaster.apply(CascadeConstants.kCascadeDriverConfiguration);
        cascadeMotorSlave.apply(new MotorConfiguration(CascadeConstants.kCascadeDriverConfiguration));
        pivotMotorMaster.apply(CascadeConstants.kPivotConfiguration);
        pivotMotorSlave.apply(new MotorConfiguration(CascadeConstants.kPivotConfiguration));

        pivotCANcoder =
        new CANcoder(
            CascadeConstants.kPivotCANcoderId.getDeviceNumber(),
            CascadeConstants.kPivotCANcoderId.getBus());
        pivotCANcoder.getConfigurator().apply(CascadeConstants.pivotEncoderConfig);

        cascadeMasterCurrent = cascadeMotorMaster.getTalonFX().getStatorCurrent();

        BaseStatusSignal.setUpdateFrequencyForAll(
            50.0,
            cascadeMasterCurrent
        );

        ParentDevice.optimizeBusUtilizationForAll(cascadeMotorMaster.getTalonFX());
    }

    @Override
    public void updateInputs(CascadeIOInputs inputs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInputs'");
    }

    @Override
    public void setCascadeSetpoint(Distance setpoint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCascadeSetpoint'");
    }

    @Override
    public void setCascadeEncoderPosition(Distance setpoint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCascadeEncoderPosition'");
    }

    @Override
    public void setPivotSetpoint(Rotation2d setpoint) {
        pivotMotorMaster.setSetpoint(setpoint.minus(CascadeConstants.kCascadePivotBuffer).getDegrees()
            > 0 ? CascadeConstants.kCascadePivotBuffer : setpoint);
    }

    @Override
    public void setCANCoderPosition(Rotation2d angle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCANCoderPosition'");
    }

    @Override
    public void stopCascadeMotors(boolean stopped) {
        cascadeMotorMaster.setSetpoint(Meters.of(cascadeMotorMaster.getSetpoint()));
    }

    @Override
    public void stopPivotMotors(boolean stopped) {
        pivotMotorMaster.setRawVoltage(stopped ? Volts.of(0) : pivotMotorMaster.getTalonFX().getMotorVoltage().getValue());
    }
    
}
