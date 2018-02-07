/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6300.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6300.robot.commands.*;
import org.usfirst.frc.team6300.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the Timed Robot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	private Compressor compressor = new Compressor();
	public final Drivetrain drivetrain = new Drivetrain();
	//public final Lifter lifter = new Lifter();
	//public final Claw claw = new Claw();
	//public final Wrist wrist = new Wrist();
	public OI oi;

	Command autonomousCommand;
	SendableChooser<String> sideChooser = new SendableChooser<>();
	
	/**
	 * Adds a deadzone to, for example, a joystick input that does not completely zero itself mechanically.
	 * @param input the input value (any double between -1 and 1, inclusively).
	 * @param radius how far from zero the input can be for the output to still be zero. This must be greated than 0 and less than 1.
	 * @return the value after application of the deadzone (between -1 and 1, inclusively).
	 */
	public static double deadZone(double input, double radius) {
		double output;
		double maxOutput = 1;
		if (radius > maxOutput) {
			output = 0;
		}
		else {
			if (input > radius) {
				output = ((maxOutput * (input - maxOutput)) / (maxOutput - radius)) + maxOutput;
			}
			else if (input < -radius) {
				output = ((maxOutput * (input + maxOutput)) / (maxOutput - radius)) - maxOutput;
			}
			else {
				output = 0;
			}
		}
		if (Math.abs(output) > maxOutput) {
			output = maxOutput;
		}
		return output;
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI(this);
		
		sideChooser.addDefault("Starting from Left", "left");
		sideChooser.addObject("Starting from Center", "center");
		sideChooser.addObject("Starting from Right", "right");
		SmartDashboard.putData("Starting Side", sideChooser);
		
		compressor.start();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		String startingSide = sideChooser.getSelected();
		if (gameData.charAt(0) == 'L') {
			switch (startingSide) {
			case "left":
//				autonomousCommand = new LLeft(this);
				break;
			case "right":
//				autonomousCommand = new LRight(drivetrain, lifter, claw);
				break;
			case "center":
//				autonomousCommand = new LCenter(drivetrain, lifter, claw);
				break;
			default:
				System.out.println("Invalid starting side string!");
				break;
			}
		}	
		else if (gameData.charAt(0) == 'R'){
			switch (startingSide) {
			case "left":
//				autonomousCommand = new RLeft(drivetrain, lifter, claw);
				break;
			case "right":
//				autonomousCommand = new RRight(drivetrain, lifter, claw);
				break;
			case "center":
//				autonomousCommand = new RCenter(drivetrain, lifter, claw);
				break;
			default:
				System.out.println("Invalid starting side string!");
				break;
			}
		}
		else {
			System.out.println("Invalid game data!");
		}
		// schedule the autonomous command
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
		else {
			System.out.println("autonomousCommand is null! Running auto line code.");
			autonomousCommand = new AutoLine(drivetrain);
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
