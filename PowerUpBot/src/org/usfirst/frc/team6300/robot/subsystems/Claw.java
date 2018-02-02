package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Claw extends Subsystem {
	private DoubleSolenoid sol = new DoubleSolenoid(RobotMap.clawSolPort1, RobotMap.clawSolPort2);
    public Claw() {
    }
    
    public void close() {
    	sol.set(DoubleSolenoid.Value.kReverse);
    }
    public void open() {
    	sol.set(DoubleSolenoid.Value.kForward);
    }
    public void turnOff() {
    	sol.set(DoubleSolenoid.Value.kOff);
    }
    
    public boolean isClosed() {
    	return sol.get() == DoubleSolenoid.Value.kReverse;
    }
    public boolean isOpen() {
    	return sol.get() == DoubleSolenoid.Value.kForward;
    }
    public boolean isOff() {
    	return sol.get() == DoubleSolenoid.Value.kOff;
    }
    
    public void initDefaultCommand() {
    }
}

