package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.subsystems.Lifter;
import org.usfirst.frc.team6300.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TeleLift extends Command {
	private Lifter lifter;
	private Wrist wrist;
	
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
    	if (lifter.minVertLength < lifter.getPosition() && lifter.getPosition() <= lifter.topIllegalLength) {
    		wrist.setSetpoint(wristWRTGround(-90));
    	}
    	else if (lifter.getPosition() <= lifter.minVertLength) {
    		wrist.setSetpoint(160);
    	}
    	else if (lifter.topIllegalLength > lifter.getPosition()) {
    		wrist.setSetpoint(0);
    	}
    	wrist.setMotor(OI.deadZone(-OI.cubeJoy.getRawAxis(OI.leftY)));
    	lifter.setMotor(OI.deadZone(-OI.cubeJoy.getRawAxis(OI.rightY)));
    	SmartDashboard.putNumber("Lifter Length", lifter.getPosition());
    }
    
    private double wristWRTGround(double angle) {
    	return angle - lifter.getPosition();
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
