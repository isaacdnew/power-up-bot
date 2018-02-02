package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleLift;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 *
 */
public class Lifter extends PIDSubsystem {
	private SpeedController liftMotor = new Victor(RobotMap.liftMotor);
	private Potentiometer pot = new AnalogPotentiometer(0, 360, 0);
    public final double minLiftAngle = 6.52;
    public final double switchAngle = 40;
	public final double scaleMaxAngle = 180;
	public final double scaleMidAngle = 40;
	public final double scaleMinAngle = 40;
	
	private double minWristAngle = 90;
	
    private static final double p = 0;
    private static final double i = 0;
    private static final double d = 0;
    private static final double feedForward = 0;
    private static final double pidPeriod = 0.005;
    public Lifter() {
    	super("lifter", p, i, d, feedForward, pidPeriod);
    	setInputRange(0, 360);
    	setOutputRange(0, 1);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new TeleLift(this, OI.cubeJoy, OI.leftY));
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return 0.0;
    }
    
    protected void usePIDOutput(double output) {
       liftMotor.set(output);
    }
    
    public void teleLift(Joystick joy, int liftAxis) {
    	disable();
		liftMotor.set(joy.getRawAxis(liftAxis));
		enable();
		setSetpoint(returnPIDInput());
	}
    
    public double getMinWristAngle() {
    	return minWristAngle;
    }
}
