package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Wrist extends PIDSubsystem {
	private SpeedController motor = new VictorSP(RobotMap.wristMotor);
	private Encoder enc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	private double encRevsPerPulse = 1.0 / 40;
	private double stage1Ratio = 1.0 / 10;
	private double stage2Ratio = 1.0 / 5;
	private double beltRatio = 24.0 / 36;
	private final double encOffset = 0;

	public final double foldedAngle = 160;

	private static final double p = 0.02;
	private static final double i = 0.0;
	private static final double d = 0.0;
	private static final double feedForward = 0.0;
	private static final double pidPeriod = 0.005;

	public Wrist() {
		super("wrist", p, i, d, feedForward, pidPeriod);
		setInputRange(0.0, 360.0);
		getPIDController().setContinuous(false);
		setOutputRange(0.0, 1.0);
		
		enc.setDistancePerPulse(encRevsPerPulse * stage1Ratio * stage2Ratio * beltRatio * 360.0 /* degrees */);
		enc.setMinRate(2);
	}

	public void initDefaultCommand() {
	}

	protected double returnPIDInput() {
		return getTrueAngle();
	}

	protected void usePIDOutput(double output) {
		motor.set(output);
	}

	private double getTrueAngle() {
		return enc.getDistance() + encOffset;
	}

	public boolean getEncStopped() {
		return enc.getStopped();
	}

	public void setMotor(double output) {
		if (!getPIDController().isEnabled()) {
			motor.set(output);
		} else {
			System.out.println("Wrist PID enabled; not setting motor.");
		}
	}

	public void reset() {
		disable();
		enc.reset();
		setSetpoint(0.0);
		enable();
	}
}
