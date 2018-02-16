/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The drivetrain. It drives the robot.
 */
public class Drivetrain extends PIDSubsystem {
	
	private final SpeedController lfMotor = new VictorSP(RobotMap.lfMotor);
	private final SpeedController lbMotor = new VictorSP(RobotMap.lbMotor);
	private final SpeedController rfMotor = new VictorSP(RobotMap.rfMotor);
	private final SpeedController rbMotor = new VictorSP(RobotMap.rbMotor);
	double leftSpeed = 0;
	double rightSpeed = 0;
	
	private final DoubleSolenoid lSol = new DoubleSolenoid(RobotMap.lSolPort1, RobotMap.lSolPort2);
	private final DoubleSolenoid rSol = new DoubleSolenoid(RobotMap.rSolPort1, RobotMap.rSolPort2);
	
	private final Encoder lEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	private final Encoder rEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	private final double encRevsPerPulse = 1/400;
	private final double lowGearRatio = 15/42;
	private final double highGearRatio = 1; //TODO count gear teeth to measure ratio
	private final double wheelDiameter = 6; //inches
	private final double pulseDistLowGear  = encRevsPerPulse * lowGearRatio  * Math.PI * wheelDiameter;
	private final double pulseDistHighGear = encRevsPerPulse * highGearRatio * Math.PI * wheelDiameter;
	
	Gyro gyro;
	static final double p = 0.04;
	static final double i = 0.0002;
	static final double d = 0.22;
	static final double feedForward = 0.06;
	static final double pidPeriod = 0.005;
	
	public Drivetrain() {
		super("drivetrain", p, i, d, feedForward, pidPeriod);
		gyro = new ADXRS450_Gyro();
		getPIDController().setAbsoluteTolerance(3);
		getPIDController().setInputRange(0, 360);
		getPIDController().setContinuous(true);
		getPIDController().setOutputRange(-1, 1);
		
		lfMotor.setInverted(RobotMap.lfInverted);
		lbMotor.setInverted(RobotMap.lbInverted);
		rfMotor.setInverted(RobotMap.rfInverted);
		rbMotor.setInverted(RobotMap.rbInverted);
		
		lEncoder.setDistancePerPulse(pulseDistLowGear);
		rEncoder.setDistancePerPulse(pulseDistLowGear);
		
		lEncoder.reset();
		rEncoder.reset();
		shiftDown();
	}
	
	
	
	
	
	//PID CONTROL
	@Override
	protected double returnPIDInput() {
		return gyro.getAngle() - (360 * Math.floor(gyro.getAngle() / 360));
	}

	@Override
	protected void usePIDOutput(double output) {
		double leftOutput = leftSpeed  - output; 
		double rightOutput = rightSpeed + output;
		if (leftOutput > 1 || rightOutput > 1) {
			double maxOutput = Math.max(leftOutput, rightOutput);
			leftOutput /= maxOutput;
			rightOutput /= maxOutput;
		}
		lfMotor.set(leftOutput);
		lbMotor.set(leftOutput);
		rfMotor.set(rightOutput);
		rbMotor.set(rightOutput);;
	}
	
	
	
	
	
	//DRIVING
	public void setSpeeds(double leftSpeed, double rightSpeed) {
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
		
		if (!getPIDController().isEnabled()) {
			lfMotor.set(leftSpeed);
			lbMotor.set(leftSpeed);
			rfMotor.set(rightSpeed);
			rbMotor.set(rightSpeed);
		}
	}
	
	public void stop() {
		disable();
		lfMotor.stopMotor();
		lbMotor.stopMotor();
		rfMotor.stopMotor();
		rbMotor.stopMotor();
	}
	
	
	
	
	//SHIFTING
	public void shiftUp() {
		lSol.set(DoubleSolenoid.Value.kReverse);
		rSol.set(DoubleSolenoid.Value.kReverse);
		rEncoder.setDistancePerPulse(pulseDistHighGear);
		lEncoder.setDistancePerPulse(pulseDistHighGear);
		rEncoder.reset();
		lEncoder.reset();
	}
	
	public void shiftDown() {
		lSol.set(DoubleSolenoid.Value.kForward);
		rSol.set(DoubleSolenoid.Value.kForward);
		rEncoder.setDistancePerPulse(pulseDistLowGear);
		lEncoder.setDistancePerPulse(pulseDistLowGear);
		rEncoder.reset();
		lEncoder.reset();
	}
	
	public void updateGear() {
		if (Math.abs((lEncoder.getRate() + rEncoder.getRate()) / 2) > 18) {
			shiftUp();
		}
		else {
			shiftDown();
		}
	}
	
	public void putEncoderData() {
		SmartDashboard.putNumber("Left Enc Count", lEncoder.get());
		SmartDashboard.putNumber("Left Enc Dist", lEncoder.getDistance());
		SmartDashboard.putNumber("Left Enc Rate", lEncoder.getRate());
		SmartDashboard.putNumber("Right Enc Count", rEncoder.get());
		SmartDashboard.putNumber("Right Enc Dist", rEncoder.getDistance());
		SmartDashboard.putNumber("Right Enc Rate", rEncoder.getRate());
	}
	
	
	
	
	
	//MISCELLANEOUS
	public void initDefaultCommand() {
		setDefaultCommand(new TeleDrive(this));
	}
}
