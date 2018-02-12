package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.subsystems.Claw;
import org.usfirst.frc.team6300.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftArmTo extends Command {
	private Lifter lifter;
	private Claw claw;
	private String position;
	private double angle;
	
    public LiftArmTo(Lifter lifter, Claw claw, String position) {
        this.lifter = lifter;
        requires(lifter);
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
    	lifter.setSetpoint(angle);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
