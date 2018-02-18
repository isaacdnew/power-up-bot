package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.subsystems.Lifter;
import org.usfirst.frc.team6300.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleLiftPID extends Command {
	private Lifter lifter;
	private Wrist wrist;
	
    public TeleLiftPID(Lifter lifter, Wrist wrist) {
        this.lifter = lifter;
        this.wrist = wrist;
        requires(lifter);
        requires(wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lifter.enable();
    	wrist.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
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
