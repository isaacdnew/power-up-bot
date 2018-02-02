/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleDrive;

import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The drivetrain. It drives the robot.
 */
public class Drivetrain extends Subsystem {
	
	private final SpeedController lfMotor = new Victor(RobotMap.lfMotor);
	private final SpeedController lbMotor = new Victor(RobotMap.lfMotor);
	private final SpeedController rfMotor = new Victor(RobotMap.lfMotor);
	private final SpeedController rbMotor = new Victor(RobotMap.lfMotor);
	
	private final DoubleSolenoid lSol = new DoubleSolenoid(RobotMap.lSolPort1, RobotMap.lSolPort2);
	private final DoubleSolenoid rSol = new DoubleSolenoid(RobotMap.rSolPort1, RobotMap.rSolPort2);
	
//	private final Encoder lEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
//	private final Encoder rEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	
	public Drivetrain() {
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleDrive(this));
	}
	
	public void setForwardPower(double power) {
		lfMotor.set(power);
		lbMotor.set(power);
		rfMotor.set(power);
		rbMotor.set(power);
	}
	
	public void shiftDown() {
		lSol.set(DoubleSolenoid.Value.kForward);
		rSol.set(DoubleSolenoid.Value.kForward);
	}
	
	public void teleDrive(Joystick joy, int forwardAxis, int rotateAxis) {
		//save joystick axis values, adding deadzones, from 0 to 1
		double forwardSpeed = deadZone(joy.getRawAxis(forwardAxis), 0.2);
		double rotateSpeed = deadZone(joy.getRawAxis(rotateAxis), 0.2);
		
		//calculate desired motor speeds from 0 to 1
		double leftSpeed = forwardSpeed + rotateSpeed;
		double rightSpeed = forwardSpeed - rotateSpeed;
		
		//set motor speeds
		lfMotor.set(leftSpeed);
		lbMotor.set(leftSpeed);
		rfMotor.set(rightSpeed);
		rbMotor.set(rightSpeed);
	}
	/**
	 * Adds a deadzone to, for example, a joystick input that does not zero itself well mechanically.
	 * @param input the input value (any double between -1 and 1, inclusively).
	 * @param radius how far from zero the input can be for the output to still be zero. This must be greated than 0 and less than 1.
	 * @return the value after application of the deadzone (between -1 and 1, inclusively).
	 */
	private double deadZone(double input, double radius) {
		if (radius <= 0) {
			return input;
		}
		if (radius >= 1) {
			return 0;
		}
		
		
		double output = ((1/(1-radius)) * (input - 1)) + 1;
		if (Math.abs(output) > 1) {
			output = Math.copySign(1, output);
		}
		return output;
		
	}

	public void stop() {
		//TODO disable PID control, if it is used.
		lfMotor.stopMotor();
		lbMotor.stopMotor();
		rfMotor.stopMotor();
		rbMotor.stopMotor();
	}
}
