package org.frc6300.robot.commands;

import org.frc6300.robot.Robot;
import org.frc6300.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LCenter extends CommandGroup {
	public LCenter(Robot robot) {
		// Hold onto the cube
		addParallel(new CloseClaw(robot.claw));

		// Set up arms
		addSequential(new LiftTo(robot, Lifter.minLength));
		addSequential(new CalibrateWrist(robot.wrist));
		addParallel(new LiftTo(robot, Lifter.switchLength));

		// Drive to auto line, but turn left first to avoid the pile of power cubes
		addSequential(new AutoDrive(robot.drivetrain, 0.5, 0.2));
		addSequential(new Rotate(robot.drivetrain, -15));
		addSequential(new AutoDrive(robot.drivetrain, 0.5, 3.0));

		// Drop cube on switch
		addSequential(new OpenClaw(robot.claw));   
	}
}
