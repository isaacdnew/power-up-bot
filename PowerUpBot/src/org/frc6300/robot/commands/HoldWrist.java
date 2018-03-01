package org.frc6300.robot.commands;

import org.frc6300.robot.RobotMap;
import org.frc6300.robot.subsystems.Lifter;
import org.frc6300.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HoldWrist extends Command {
	private Wrist wrist;
	private Lifter lifter;
	private double desiredAngle = 0;
	
	/**
     * Keeps the lifter arm within bounds, while also keeping it as close as possible to its desired angle with respect to the ground.
     */
    public HoldWrist(Wrist wrist, Lifter lifter, int desiredAngle) {
        this.wrist = wrist;
        this.lifter = lifter;
        this.desiredAngle = desiredAngle;
        requires(wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	desiredAngle -= 360 * Math.floor(desiredAngle / 360);
    	double lifterAngle = lifter.getPosition();
    	double angleWRTGround;
    	if (Math.cos(desiredAngle) <= (RobotMap.armLengthInches * (1 - Math.cos(lifterAngle))) / RobotMap.wristLengthInches) {
    		//then it's legal.
    		angleWRTGround = desiredAngle;
    	}
    	else if (desiredAngle >= 180) {
    		//then it's illegal, below the lifter arm.
    		angleWRTGround = 360 - Math.acos((RobotMap.armLengthInches * (1 - Math.cos(lifterAngle))) / RobotMap.wristLengthInches);
    	}
    	else {
    		//then it's illegal, above the lifter arm.
    		angleWRTGround = Math.acos((RobotMap.armLengthInches * (1 - Math.cos(lifterAngle))) / RobotMap.wristLengthInches);
    	}
    	double requiredWristAngle = lifterAngle + 180 - angleWRTGround;
    	wrist.setSetpoint(requiredWristAngle);
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
