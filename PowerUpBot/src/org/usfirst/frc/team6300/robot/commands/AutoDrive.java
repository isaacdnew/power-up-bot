package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDrive extends Command {
	private Drivetrain drivetrain;
	private double seconds;
	private double power;
	
	/**
	 * Drives the robot straight forward over a specified distance.
	 * @param drivetrain the drivetrain to use
	 * @param power the power (between 0 and 1) to send to the motors
	 * @param lowGear if true, robot will use low gear. Otherwise, it will use high gear.
	 * @param seconds the time to drive forward.
	 */
    public AutoDrive(Drivetrain drivetrain, double power, boolean lowGear, double seconds) {
    	this.drivetrain = drivetrain;
    	this.seconds = seconds;
    	this.power = power;
        requires(drivetrain);
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
    	drivetrain.shiftDown();
    	drivetrain.setForwardPower(power);
    	Timer.delay(seconds);
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
