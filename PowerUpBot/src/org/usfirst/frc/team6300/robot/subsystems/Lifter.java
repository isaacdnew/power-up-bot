package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 *
 */
public class Lifter extends PIDSubsystem {
	public final double startAngle = 14;
	public final double minAngle = 6.52;
    public final double switchAngle = 40;
	public final double scaleMaxAngle = 160;
	
	private SpeedController liftMotor = new VictorSP(RobotMap.lLiftMotor);
	
	private final double actuatorRange = 10;
	private final double potOffset = 0;
	private Potentiometer liftPot = new AnalogPotentiometer(0, actuatorRange, potOffset);
	
    private static final double p = 0;
    private static final double i = 0;
    private static final double d = 0;
    private static final double feedForward = 0;
    private static final double pidPeriod = 0.005;
    public Lifter() {
    	super("lifter", p, i, d, feedForward, pidPeriod);
    	liftMotor.setInverted(RobotMap.liftInverted);
    	
    	setInputRange(0, 10);
    	getPIDController().setContinuous(false);
    	setOutputRange(0, 1);
    }
    
    public void initDefaultCommand() {
    }
    
    protected double returnPIDInput() {
        return liftPot.pidGet();
    }
    
    protected void usePIDOutput(double output) {
       setMotor(output);
    }
    
    public void setMotor(double power) {
    	liftMotor.set(power);
    }
}
