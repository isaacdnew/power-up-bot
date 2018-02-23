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
	public final double startLength = 0;
	public final double minVertLength = 1.68;
	public final double switchLength = 5.1;
	public final double topIllegalLength = 7.8;
	public final double scaleMaxLength = 6.7; // TODO wrong...
	public final double verticalLength = 10;

	private SpeedController motor = new VictorSP(RobotMap.liftMotor);

	private final double actuatorRange = 10; // inches
	private final double potOffset = 0.0; // inches
	private Potentiometer pot = new AnalogPotentiometer(RobotMap.liftPot, actuatorRange, potOffset);

	private static final double p = 0.05;
	private static final double i = 0;
	private static final double d = 0;
	private static final double feedForward = 0;
	private static final double pidPeriod = 0.005;

	public Lifter() {
		super("lifter", p, i, d, feedForward, pidPeriod);
		motor.setInverted(RobotMap.liftInverted);

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
			motor.set(output);
		} else {
			motor.set(0);
		}
	}

	public void setMotor(double power) {
		if (!getPIDController().isEnabled()) {
			motor.set(power);
		} else {
			System.out.println("Lifter PID enabled; not setting motor.");
		}
	}
}
