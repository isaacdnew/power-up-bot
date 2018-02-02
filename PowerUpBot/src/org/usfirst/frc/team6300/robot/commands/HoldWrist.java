package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.subsystems.Lifter;
import org.usfirst.frc.team6300.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HoldWrist extends Command {
	private Wrist wrist;
	private Lifter lifter;
	private double angleWRTGround = 0;
	private double requiredWristAngle;
	private double lifterAngle;
	
    public HoldWrist(Wrist wrist, Lifter lifter) {
        this.wrist = wrist;
        this.lifter = lifter;
        requires(wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	wrist.holdToAngleIfLegal(0, lifter);
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
