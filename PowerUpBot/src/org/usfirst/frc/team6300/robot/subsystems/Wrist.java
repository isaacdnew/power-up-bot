package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Wrist extends PIDSubsystem {
	private SpeedController motor = new VictorSP(RobotMap.wristMotor);
	private Encoder enc = new Encoder(RobotMap.wristEncPort1, RobotMap.wristEncPort2, false, Encoder.EncodingType.k4X);
	private double encRevsPerPulse = 1.0 / 40;
	private final double encOffset = 411;

	public final double foldedAngle = 160;

	private static final double p = 0.01;
	private static final double i = 0.0;
	private static final double d = 0.0;
	private static final double feedForward = 0.0;
	private static final double pidPeriod = 0.005;

	public Wrist() {
		super("wrist", p, i, d, feedForward, pidPeriod);
		setInputRange(0.0, 360.0);
		getPIDController().setContinuous(false);
		setOutputRange(0.0, 1.0);

		enc.setDistancePerPulse(90.0 / 215); // 215 steps = 90 degrees on this encoder and gearing setup
		enc.setDistancePerPulse(1);
		enc.setMinRate(2);
	}

	public void initDefaultCommand() {
	}

	protected double returnPIDInput() {
		return getTrueAngle();
	}

	protected void usePIDOutput(double output) {
		motor.set(output);
		SmartDashboard.putNumber("Wrist PID Output", output);
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
