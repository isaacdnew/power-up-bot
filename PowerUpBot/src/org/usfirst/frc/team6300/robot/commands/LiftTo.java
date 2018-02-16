package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftArmTo extends Command {
	private Robot robot;
	private String position;
	
    public LiftArmTo(Robot robot, String position) {
    	this.robot = robot;
        this.position = position;
        requires(robot.lifter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (!robot.lifter.getPIDController().isEnabled()) {
    		robot.lifter.enable();
    	}
    	switch (position) {
    	case "scale": {
    		robot.lifter.setSetpoint(robot.lifter.scaleMaxAngle);
    		if (robot.lifter.getPosition() < 0) {
    			robot.setClawHold(true);
    		}
    		else {
    			robot.setClawHold(false);
    		}
    		break;
    	}
    	case "switch": {
    		robot.lifter.setSetpoint(robot.lifter.switchAngle);
    		if (robot.lifter.getPosition() > 0) {
    			robot.setClawHold(true);
    		}
    		else {
    			robot.setClawHold(false);
    		}
    		break;
    	}
    	case "floor": {
    		robot.lifter.setSetpoint(robot.lifter.minAngle);
    		if (robot.lifter.getPosition() > 0) {
    			robot.setClawHold(true);
    		}
    		else {
    			robot.setClawHold(false);
    		}
    		break;
    	}
    	}
    	
    	while (!robot.lifter.onTarget()) {
    		Timer.delay(0.05);
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
