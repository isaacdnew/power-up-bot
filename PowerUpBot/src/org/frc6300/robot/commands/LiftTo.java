package org.frc6300.robot.commands;

import org.frc6300.robot.Robot;
import org.frc6300.robot.subsystems.Lifter;
import org.frc6300.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Lifts the arm to a specified height, given by the desired length of the
 * linear actuator
 */
public class LiftTo extends Command {
	private Lifter lifter;
	private Wrist wrist;
	private double length;

	public LiftTo(Robot robot, double actuatorLength) {
		this.lifter = robot.lifter;
		this.wrist = robot.wrist;
		this.length = actuatorLength;
		requires(lifter);
		requires(wrist);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		lifter.enable();
		lifter.setSetpoint(length);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (wrist.getPIDController().isEnabled()) {
			if (lifter.startLength <= lifter.getPosition() && lifter.getPosition() < lifter.minVertLength) {
				wrist.setSetpoint(wrist.foldedAngle);
			}
			if (lifter.minVertLength <= lifter.getPosition() && lifter.getPosition() < lifter.topIllegalLength) {
				wrist.setSetpoint(wristWRTGround(-90));
			}
			if (lifter.topIllegalLength <= lifter.getPosition() && lifter.getPosition() < lifter.scaleMaxLength) {
				wrist.setSetpoint(wristWRTGround(0));
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (wrist.getPIDController().isEnabled()) {
			return lifter.onTarget() && wrist.onTarget();
		}
		else {
			return lifter.onTarget();
		}
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

	private double wristWRTGround(double angle) {
		return angle - lifter.getPosition();
	}
}
