package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleLift extends Command {
	private Lifter lifter;
    public TeleLift(Lifter lifter) {
        this.lifter = lifter;
        requires(lifter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (lifter.getPIDController().isEnabled()) {
    		lifter.setSetpointRelative(OI.deadZone(-OI.cubeJoy.getRawAxis(OI.rightY), 0.2));
    	}
    	else {
    		lifter.setMotors(OI.deadZone(-OI.cubeJoy.getRawAxis(OI.rightY), 0.2));
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
