package team5427.frc.robot.subsystems.Cascade.io;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.ParentDevice;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
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

  private StatusSignal<Angle> cascadeMasterMotorPosition;
  private StatusSignal<AngularVelocity> cascadeAngularVelocity;
  private StatusSignal<AngularAcceleration> cascadeAngularAcceleration;

  private StatusSignal<Current> cascadeMasterMotorCurrent;
  private StatusSignal<Voltage> cascadeMasterMotorVoltage;

  private StatusSignal<Current> cascadeSlaveMotorCurrent;
  private StatusSignal<Voltage> cascadeSlaveMotorVoltage;

  private StatusSignal<Angle> pivotPosition;
  private StatusSignal<Angle> pivotAbsolutePosition;
  private StatusSignal<AngularVelocity> pivotAngularVelocity;
  private StatusSignal<AngularAcceleration> pivotAngularAcceleration;

  private StatusSignal<Current> pivotMasterMotorCurrent;
  private StatusSignal<Voltage> pivotMasterMotorVoltage;

  private StatusSignal<Current> pivotSlaveMotorCurrent;
  private StatusSignal<Voltage> pivotSlaveMotorVoltage;

  public CascadeIOMagicTalon() {
    cascadeMotorMaster = new MagicSteelTalonFX(CascadeConstants.kCascadeMasterId);
    cascadeMotorSlave = new MagicSteelTalonFX(CascadeConstants.kCascadeSlaveId);

    pivotMotorMaster = new MagicSteelTalonFX(CascadeConstants.kPivotMasterId);
    pivotMotorSlave = new MagicSteelTalonFX(CascadeConstants.kPivotSlaveId);

    cascadeMotorMaster.useTorqueCurrentFOC(false);
    cascadeMotorSlave.useTorqueCurrentFOC(false);

    pivotMotorMaster.useTorqueCurrentFOC(false);
    pivotMotorSlave.useTorqueCurrentFOC(false);

    cascadeMotorMaster.apply(CascadeConstants.kCascadeDriverConfiguration);
    cascadeMotorMaster.talonConfig.ClosedLoopGeneral.ContinuousWrap = false;
    cascadeMotorMaster.getTalonFX().getConfigurator().apply(cascadeMotorMaster.talonConfig);

    cascadeMotorSlave.apply(new MotorConfiguration(CascadeConstants.kCascadeDriverConfiguration));
    cascadeMotorSlave.talonConfig.ClosedLoopGeneral.ContinuousWrap = false;
    cascadeMotorSlave.getTalonFX().getConfigurator().apply(cascadeMotorSlave.talonConfig);

    cascadeMotorSlave.getTalonFX().setControl(
      new Follower(cascadeMotorMaster.getTalonFX().getDeviceID(), true));

    pivotMotorMaster.apply(CascadeConstants.kPivotConfiguration);
    pivotMotorMaster.talonConfig.ClosedLoopGeneral.ContinuousWrap = false;
    pivotMotorMaster.getTalonFX().getConfigurator().apply(pivotMotorMaster.talonConfig);

    pivotMotorSlave.apply(new MotorConfiguration(CascadeConstants.kPivotConfiguration));
    pivotMotorSlave.talonConfig.ClosedLoopGeneral.ContinuousWrap = false;
    pivotMotorSlave.getTalonFX().getConfigurator().apply(pivotMotorSlave.talonConfig);

    pivotMotorSlave.getTalonFX().setControl(
      new Follower(pivotMotorMaster.getTalonFX().getDeviceID(), false));

    pivotCANcoder =
        new CANcoder(
            CascadeConstants.kPivotCANcoderId.getDeviceNumber(),
            CascadeConstants.kPivotCANcoderId.getBus());
    pivotCANcoder.getConfigurator().apply(CascadeConstants.pivotEncoderConfig);

    cascadeMasterMotorPosition = cascadeMotorMaster.getTalonFX().getPosition();
    cascadeAngularVelocity = cascadeMotorMaster.getTalonFX().getVelocity();
    cascadeAngularAcceleration = cascadeMotorMaster.getTalonFX().getAcceleration();

    cascadeMasterMotorCurrent = cascadeMotorMaster.getTalonFX().getStatorCurrent();
    cascadeMasterMotorVoltage = cascadeMotorMaster.getTalonFX().getMotorVoltage();

    cascadeSlaveMotorCurrent = cascadeMotorSlave.getTalonFX().getStatorCurrent();
    cascadeSlaveMotorVoltage = cascadeMotorSlave.getTalonFX().getMotorVoltage();

    pivotPosition = pivotMotorMaster.getTalonFX().getPosition();
    pivotAbsolutePosition = pivotCANcoder.getAbsolutePosition();
    pivotAngularVelocity = pivotMotorMaster.getTalonFX().getVelocity();
    pivotAngularAcceleration = pivotMotorMaster.getTalonFX().getAcceleration();

    pivotMasterMotorCurrent = pivotMotorMaster.getTalonFX().getStatorCurrent();
    pivotMasterMotorVoltage = pivotMotorMaster.getTalonFX().getMotorVoltage();

    pivotSlaveMotorCurrent = pivotMotorSlave.getTalonFX().getStatorCurrent();
    pivotSlaveMotorVoltage = pivotMotorSlave.getTalonFX().getMotorVoltage();

    BaseStatusSignal.setUpdateFrequencyForAll(
      50,
      cascadeMasterMotorPosition,
      cascadeAngularVelocity,
      cascadeAngularAcceleration,
      cascadeMasterMotorCurrent,
      cascadeMasterMotorVoltage,
      cascadeSlaveMotorCurrent,
      cascadeSlaveMotorVoltage
    );

    BaseStatusSignal.setUpdateFrequencyForAll(
      50,
      pivotPosition,
      pivotAngularVelocity,
      pivotAngularAcceleration,
      pivotMasterMotorCurrent,
      pivotMasterMotorVoltage,
      pivotSlaveMotorCurrent,
      pivotSlaveMotorVoltage
    );

    BaseStatusSignal.waitForAll(
      0.02,
      cascadeAngularVelocity,
      cascadeAngularAcceleration,
      cascadeMasterMotorCurrent,
      cascadeMasterMotorVoltage,
      cascadeSlaveMotorCurrent,
      cascadeSlaveMotorVoltage
    );
        
    BaseStatusSignal.waitForAll(
      0.02,
      pivotPosition,
      pivotAngularVelocity,
      pivotAngularAcceleration,
      pivotMasterMotorCurrent,
      pivotMasterMotorVoltage,
      pivotSlaveMotorCurrent,
      pivotSlaveMotorVoltage,
      pivotAbsolutePosition
    );

    ParentDevice.optimizeBusUtilizationForAll(cascadeMotorMaster.getTalonFX(), cascadeMotorSlave.getTalonFX());
    ParentDevice.optimizeBusUtilizationForAll(pivotMotorMaster.getTalonFX(), pivotMotorSlave.getTalonFX());

    pivotMotorMaster.setEncoderPosition(pivotCANcoder.getPosition().getValueAsDouble());
  }

  @Override
  public void updateInputs(CascadeIOInputs inputs) {
    BaseStatusSignal.refreshAll(
      cascadeAngularVelocity,
      cascadeAngularAcceleration,
      cascadeMasterMotorCurrent,
      cascadeMasterMotorVoltage,
      cascadeSlaveMotorCurrent,
      cascadeSlaveMotorVoltage
    );
        
    BaseStatusSignal.refreshAll(
      pivotPosition,
      pivotAngularVelocity,
      pivotAngularAcceleration,
      pivotMasterMotorCurrent,
      pivotMasterMotorVoltage,
      pivotSlaveMotorCurrent,
      pivotSlaveMotorVoltage,
      pivotAbsolutePosition
    );

    inputs.cascadeHeight = Meters.of(cascadeMotorMaster.getEncoderPosition(cascadeMasterMotorPosition));
    inputs.cascadeAngularVelocity = cascadeAngularVelocity.getValue();
    inputs.cascadeLinearVelocity = MetersPerSecond.of(cascadeMotorMaster.getEncoderVelocity(cascadeAngularVelocity));
    inputs.cascadeLinearAcceleration = MetersPerSecondPerSecond.of(cascadeMotorMaster.getEncoderAcceleration(cascadeAngularAcceleration));
    
    inputs.cascadeMasterMotorCurrent = cascadeMasterMotorCurrent.getValue();
    inputs.cascadeMasterMotorVoltage = cascadeMasterMotorVoltage.getValue();

    inputs.cascadeSlaveMotorCurrent = cascadeSlaveMotorCurrent.getValue();
    inputs.cascadeSlaveMotorVoltage = cascadeSlaveMotorVoltage.getValue();

    inputs.pivotAngle = Rotation2d.fromRotations(pivotPosition.getValue().in(Rotations));
    inputs.pivotAbsoluteAngle = Rotation2d.fromRotations(pivotAbsolutePosition.getValue().in(Rotations));
    inputs.pivotVelocity = pivotAngularVelocity.getValue();
    inputs.pivotAcceleration = pivotAngularAcceleration.getValue();

    inputs.pivotMasterMotorCurrent = pivotMasterMotorCurrent.getValue();
    inputs.pivotSlaveMotorVoltage = pivotSlaveMotorVoltage.getValue();

    inputs.pivotSlaveMotorCurrent = pivotSlaveMotorCurrent.getValue();
    inputs.pivotSlaveMotorVoltage = pivotSlaveMotorVoltage.getValue();
  }

  @Override
  public void setCascadeSetpoint(Distance setpoint) {
    cascadeMotorMaster.setSetpoint(setpoint);
  }

  @Override
  public void setCascadeEncoderPosition(Distance setpoint) {
    cascadeMotorMaster.setEncoderPosition(setpoint.in(Meters));
  }

  @Override
  public void setPivotSetpoint(Rotation2d setpoint) {
    pivotMotorMaster.setSetpoint(setpoint);
  }

  @Override
  public void setCANCoderPosition(Rotation2d angle) {
    pivotCANcoder.setPosition(angle.getMeasure());
  }

  @Override
  public void stopCascadeMotors(boolean stopped) {
    cascadeMotorMaster.setSetpoint(
      stopped ? RotationsPerSecond.of(0) : pivotMotorMaster.getTalonFX().getVelocity().getValue());
  }

  @Override
  public void stopPivotMotors(boolean stopped) {
    pivotMotorMaster.setSetpoint(
      stopped ? RotationsPerSecond.of(0) : pivotMotorMaster.getTalonFX().getVelocity().getValue());
  }
}
