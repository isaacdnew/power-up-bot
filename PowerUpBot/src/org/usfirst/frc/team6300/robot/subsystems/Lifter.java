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
	// TODO replace these angles with lengths on the linear actuator
	public final double startAngle = 14;
	public final double minVertAngle = 6.52;
	public final double switchAngle = 40;
	public final double topIllegalAngle = 100;
	public final double scaleMaxAngle = 160;
	public final double verticalAngle = 180;

	private SpeedController liftMotor = new VictorSP(RobotMap.lLiftMotor);

	private final double actuatorRange = 12; // inches
	private final double potOffset = 0; // inches
	private Potentiometer pot = new AnalogPotentiometer(0, actuatorRange, potOffset);

	private static final double p = 0.1;
	private static final double i = 0;
	private static final double d = 0;
	private static final double feedForward = 0;
	private static final double pidPeriod = 0.005;

	public Lifter() {
		super("lifter", p, i, d, feedForward, pidPeriod);
		liftMotor.setInverted(RobotMap.liftInverted);

		setInputRange(0, actuatorRange);
		getPIDController().setContinuous(false);
		setAbsoluteTolerance(1.0);
		setOutputRange(0.0, 1.0);
	}

	public void initDefaultCommand() {
	}

	protected double returnPIDInput() {
		return pot.pidGet() + potOffset;
	}

	protected void usePIDOutput(double output) {
		if (1 < getPosition() && getPosition() < actuatorRange - 1) {
			liftMotor.set(output);
		} else {
			liftMotor.set(0);
		}
	}

	public void setMotor(double power) {
		if (!getPIDController().isEnabled()) {
			liftMotor.set(power);
		} else {
			System.out.println("Lifter PID enabled; not setting motor.");
		}
	}
}
