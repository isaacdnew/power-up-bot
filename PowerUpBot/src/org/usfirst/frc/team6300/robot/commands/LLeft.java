package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LLeft extends CommandGroup {
    public LLeft(Robot robot) {
    	addParallel(new HoldWrist(robot.wrist, robot.lifter));
    	addParallel(new LiftArmTo(robot.lifter, robot.lifter.switchAngle));
        addParallel(new CloseClaw(robot.claw));
        addSequential(new AutoDrive(robot.drivetrain, 0.5, true, 1.0));
        addSequential(new OpenClaw(robot.claw));
        addSequential(new LiftArmTo(robot.lifter, robot.lifter.switchAngle + 10));
    	// Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
