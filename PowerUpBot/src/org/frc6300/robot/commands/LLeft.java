package org.frc6300.robot.commands;

import org.frc6300.robot.Robot;
import org.frc6300.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LLeft extends CommandGroup {
	public LLeft(Robot robot) {
		// Hold onto the cube
		addParallel(new CloseClaw(robot.claw));

		// Set up arms
		addSequential(new LiftTo(robot, Lifter.minLength));
		addSequential(new CalibrateWrist(robot.wrist));
		addParallel(new LiftTo(robot, Lifter.switchLength));

		// Drive to switch
		addSequential(new AutoDrive(robot.drivetrain, 0.5, 1.0));
		addSequential(new Rotate(robot.drivetrain, 90));
		addSequential(new AutoDrive(robot.drivetrain, 0.5, 1.0));

		// Drop cube on switch
		addSequential(new OpenClaw(robot.claw));
	}
}
