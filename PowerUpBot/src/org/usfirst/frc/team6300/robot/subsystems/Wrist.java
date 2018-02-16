package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleWrist;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Wrist extends PIDSubsystem {
	private SpeedController wristMotor = new Victor(RobotMap.wristMotor);
	private Encoder wristEnc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	
    private static final double p = 0;
    private static final double i = 0;
    private static final double d = 0;
    private static final double feedForward = 0;
    private static final double pidPeriod = 0.005;
    
    public Wrist() {
    	super("wrist", p, i, d, feedForward, pidPeriod);
    	setInputRange(0, 360);
    	setOutputRange(0, 1);
    	
    	wristEnc.setDistancePerPulse(1);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new TeleWrist(this));
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return wristEnc.get();
    }
    
    protected void usePIDOutput(double output) {
    	wristMotor.set(output);
    }
    
    public void setOutput(double output) {
    	wristMotor.set(output);
    }
}
