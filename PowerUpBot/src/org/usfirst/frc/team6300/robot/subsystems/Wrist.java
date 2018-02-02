package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleWrist;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 *
 */
public class Wrist extends PIDSubsystem {
	private SpeedController wristMotor = new Victor(RobotMap.wristMotor);
	private Potentiometer pot = new AnalogPotentiometer(0, 360, 0);
	
    private static final double p = 0;
    private static final double i = 0;
    private static final double d = 0;
    private static final double feedForward = 0;
    private static final double pidPeriod = 0.005;
    public Wrist() {
    	super("wrist", p, i, d, feedForward, pidPeriod);
    	setInputRange(0, 360);
    	setOutputRange(0, 1);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new TeleWrist(this));
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return pot.get();
    }
    
    protected void usePIDOutput(double output) {
       wristMotor.set(output);
    }
    
    public void teleLift(Joystick joy, int liftAxis) {
    	disable();
		wristMotor.set(joy.getRawAxis(liftAxis));
		enable();
		setSetpoint(returnPIDInput());
	}
    /**
     * Keeps the lifter arm within bounds, while also keeping it as close as possible to its desired angle with respect to the ground.
     * @param desiredAngle the desired angle with respect to the ground
     * @param lifter
     */
    public void holdToAngleIfLegal(double desiredAngle, Lifter lifter) {
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
		setSetpoint(requiredWristAngle);
    }
}
