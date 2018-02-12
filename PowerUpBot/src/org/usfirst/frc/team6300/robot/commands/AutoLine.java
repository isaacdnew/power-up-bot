package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLine extends CommandGroup {
	
	public AutoLine(Drivetrain drivetrain) {
		addSequential(new AutoDrive(drivetrain, 0.5, 1.0));
	}
}
