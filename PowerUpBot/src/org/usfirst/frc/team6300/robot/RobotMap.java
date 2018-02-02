/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6300.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Drivetrain
	public static final int lfMotor = 1;
	public static final int rfMotor = 2;
	public static final int lbMotor = 0;
	public static final int rbMotor = 3;
	public static final int lSolPort1 = 0;
	public static final int lSolPort2 = 1;
	public static final int rSolPort1 = 2;
	public static final int rSolPort2 = 3;
	
	//Lifter
	public static final int liftMotor = 4;
	public static final int armLengthInches = 44;
	
	//Wrist
	public static final int wristMotor = 5;
	public static final int wristLengthInches = 25;
	
	//Claw
	public static final int clawSolPort1 = 4;
	public static final int clawSolPort2 = 5;
}
