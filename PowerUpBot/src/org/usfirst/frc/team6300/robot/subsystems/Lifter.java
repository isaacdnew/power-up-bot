package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleLift;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Lifter extends PIDSubsystem {
	public final double startAngle = 14;
	public final double minAngle = 6.52;
    public final double switchAngle = 40;
	public final double scaleMaxAngle = 160;
	
	private SpeedController lLiftMotor = new VictorSP(RobotMap.lLiftMotor);
	private SpeedController rLiftMotor = new VictorSP(RobotMap.rLiftMotor);
	
	private Encoder liftEnc = new Encoder(RobotMap.liftEncPort1, RobotMap.liftEncPort2, RobotMap.liftEncInverted, Encoder.EncodingType.k4X);
	private final double sprocketRatio = 1/1;
	private final double gearboxRatio = 1/40; //TODO check for actual ratio
	private final double motorRevsPerPulse = 1/40; //TODO test this
	
    private static final double p = 0;
    private static final double i = 0;
    private static final double d = 0;
    private static final double feedForward = 0;
    private static final double pidPeriod = 0.005;
    public Lifter() {
    	super("lifter", p, i, d, feedForward, pidPeriod);
    	lLiftMotor.setInverted(RobotMap.lLiftInverted);
    	rLiftMotor.setInverted(RobotMap.rLiftInverted);
    	liftEnc.setDistancePerPulse(motorRevsPerPulse * gearboxRatio * sprocketRatio * 360 /*degrees per revolution*/);
    	setInputRange(0, 360);
    	setOutputRange(0, 1);
    }
    
    public void initDefaultCommand() {
    	setDefaultCommand(new TeleLift(this));
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return liftEnc.getDistance();
    }
    
    protected void usePIDOutput(double output) {
       setMotors(output);
    }
    
    public void setMotors(double power) {
    	lLiftMotor.set(power);
        rLiftMotor.set(power);
    }
}
