package org.frc6300.robot.commands;

import org.frc6300.robot.OI;
import org.frc6300.robot.subsystems.Lifter;
import org.frc6300.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TeleLift extends Command {
	private Lifter lifter;
	private Wrist wrist;
	private static final boolean AUTOMATIC_WRIST = true;

	private enum LifterPosition {
		STOWED, BOTTOM_LEGAL, ILLEGAL, TOP_LEGAL
	}

	private LifterPosition lifterPosition;

	public TeleLift(Lifter lifter, Wrist wrist) {
		this.lifter = lifter;
		this.wrist = wrist;
		requires(lifter);
		requires(wrist);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		lifter.disable();
		wrist.disable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (AUTOMATIC_WRIST) {
			if (!wrist.getPIDController().isEnabled()) {
				wrist.enable();
			}

			if (lifter.getPosition() <= lifter.minVertLength) {
				// Set setpoint only if it needs to change
				if (!(lifterPosition == LifterPosition.STOWED)) {
					wrist.setSetpoint(160);
				}
				lifterPosition = LifterPosition.STOWED;
			} else if (lifter.minVertLength < lifter.getPosition() && lifter.getPosition() <= lifter.topIllegalLength) {
				// Set setpoint only if it needs to change
				if (!(lifterPosition == LifterPosition.ILLEGAL)) {
					wrist.setSetpoint(-90);
				}
				lifterPosition = LifterPosition.ILLEGAL;
			} else if (lifter.topIllegalLength < lifter.getPosition()) {
				// Set setpoint only if it needs to change
				if (!(lifterPosition == LifterPosition.TOP_LEGAL)) {
					wrist.setSetpoint(0);
				}
				lifterPosition = LifterPosition.TOP_LEGAL;
			}
		} else {
			wrist.setMotor(OI.deadZone(-OI.cubeJoy.getRawAxis(OI.leftY)));
		}
		lifter.setMotor(OI.deadZone(-OI.cubeJoy.getRawAxis(OI.rightY)));

		SmartDashboard.putNumber("Lifter Length", lifter.getPosition());
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
