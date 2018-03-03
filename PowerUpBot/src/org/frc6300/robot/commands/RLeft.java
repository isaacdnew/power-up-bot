package org.frc6300.robot.commands;

import org.frc6300.robot.Robot;
import org.frc6300.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RLeft extends CommandGroup {
	public RLeft(Robot robot) {
		// Hold onto the cube
		addParallel(new CloseClaw(robot.claw));

		// Flop and lift simultaneously
		addParallel(new AutoWrist(robot.wrist, 0.5, 0.2));
		addParallel(new AutoLift(robot.lifter, 1.0, 1.0));

		// Drive to switch
		addSequential(new AutoDrive(robot.drivetrain, 0.5, 2.3));
		addSequential(new Rotate(robot.drivetrain, 90));
		addSequential(new AutoDrive(robot.drivetrain, 0.5, 5.0));
		addSequential(new Rotate(robot.drivetrain, 90));
		addSequential(new AutoDrive(robot.drivetrain, 0.5, 1.5));

		// Drop cube on switch
		addSequential(new OpenClaw(robot.claw));
	}
}
