package team5427.frc.robot.subsystems.Climb.io;

import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.ParentDevice;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import team5427.frc.robot.subsystems.Climb.ClimbConstants;
import team5427.lib.motors.SteelTalonFX;

public class ClimbIOTalon implements ClimbIO {
  private SteelTalonFX hook;

  private StatusSignal<Angle> hookPosition;
  private StatusSignal<AngularVelocity> hookVelocity;
  private StatusSignal<AngularAcceleration> hookAcceleration;

  private StatusSignal<Current> hookCurrent;
  private StatusSignal<Voltage> hookVoltage;

  public ClimbIOTalon() {
    hook = new SteelTalonFX(ClimbConstants.kHookServoId);

    hook.apply(ClimbConstants.kServoConfiguration);
    hook.talonConfig.ClosedLoopGeneral.ContinuousWrap = false;
    hook.getTalonFX().getConfigurator().apply(hook.talonConfig);

    hookPosition = hook.getTalonFX().getPosition();
    hookVelocity = hook.getTalonFX().getVelocity();
    hookAcceleration = hook.getTalonFX().getAcceleration();

    hookCurrent = hook.getTalonFX().getStatorCurrent();
    hookVoltage = hook.getTalonFX().getMotorVoltage();

    hook.setEncoderPosition(Rotation2d.kZero);

    BaseStatusSignal.setUpdateFrequencyForAll(
        50, hookPosition, hookVelocity, hookAcceleration, hookCurrent, hookVoltage);

    BaseStatusSignal.waitForAll(
        0.02, hookPosition, hookVelocity, hookAcceleration, hookCurrent, hookVoltage);

    ParentDevice.optimizeBusUtilizationForAll(hook.getTalonFX());
  }

  @Override
  public void updateInputs(ClimbIOInputs inputs) {
    BaseStatusSignal.refreshAll(
        hookPosition, hookVelocity, hookAcceleration, hookCurrent, hookVoltage);

    inputs.hookPosition = Rotation2d.fromRotations(hookPosition.getValue().in(Rotations));
    inputs.hookVelocity = hookVelocity.getValue();
    inputs.hookAcceleration = hookAcceleration.getValue();
    inputs.hookCurrent = hookCurrent.getValue();
    inputs.hookVoltage = hookVoltage.getValue();
  }

  @Override
  public void setHookSetpoint(Rotation2d setpoint) {
    hook.setSetpoint(setpoint);
  }

  @Override
  public void setHookPosition(Rotation2d angle) {
    hook.setEncoderPosition(angle);
  }
}
