package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RRight extends CommandGroup {
	public RRight(Robot robot) {
		// Hold onto the cube
		addParallel(new CloseClaw(robot.claw));

		// Set up arms
		addSequential(new LiftTo(robot, robot.lifter.startAngle));
		addSequential(new CalibrateWrist(robot.wrist));
		addParallel(new LiftTo(robot, robot.lifter.switchAngle));

		// Drive to switch
		addSequential(new AutoDrive(robot.drivetrain, 0.5, 1.0));
		addSequential(new Rotate(robot.drivetrain, -90));
		addSequential(new AutoDrive(robot.drivetrain, 0.5, 1.0));

		// Drop cube on switch
		addSequential(new OpenClaw(robot.claw));
	}
}
