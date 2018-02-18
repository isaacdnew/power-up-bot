package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LLeft extends CommandGroup {
	public LLeft(Robot robot) {
		//Calibrate Arms
		addParallel(new LiftTo(robot, robot.lifter.startAngle));
		addSequential(new CalibrateWrist(robot.wrist));
		
		addParallel(new LiftTo(robot, robot.lifter.switchAngle));
		addParallel(new CloseClaw(robot.claw));
		addSequential(new AutoDrive(robot.drivetrain, 0.5, 1.0));
		addSequential(new Rotate(robot.drivetrain, 90));
		addSequential(new AutoDrive(robot.drivetrain, 0.5, 1.0));
		addSequential(new OpenClaw(robot.claw));

		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}
