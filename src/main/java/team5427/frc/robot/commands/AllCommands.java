package team5427.frc.robot.commands;

import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import team5427.frc.robot.commands.cascade.MoveCascadeToPosition;

public class AllCommands {
    public static final Command resetCascade = new MoveCascadeToPosition(Meters.of(0));
    
}
