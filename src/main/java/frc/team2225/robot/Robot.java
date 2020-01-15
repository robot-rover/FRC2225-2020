/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team2225.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.team2225.robot.subsystem.ArcadeDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team2225.robot.ScaleInputs;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    private TalonSRX fl = new TalonSRX(1);
    private TalonSRX bl = new TalonSRX(2);
    private TalonSRX fr = new TalonSRX(3);
    private TalonSRX br = new TalonSRX(4);
    private TalonSRX RollerIntake = new TalonSRX(5);
    private XboxController controller1 = new XboxController(0);
    private ArcadeDrive drivetrain;
    private boolean driveSwitch;
    private boolean intakeSwitch;
    double speed;
    double turn;


    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        //drivetrain = new ArcadeDrive(fl,bl,fr,br,controller1);
    }

    @Override
    public void teleopPeriodic() {

        if (controller1.getXButtonPressed()){
            driveSwitch ^=true;
        }
        if (controller1.getYButtonPressed()){
            intakeSwitch ^=true;
        }
        if(intakeSwitch){
            RollerIntake.set(ControlMode.PercentOutput, 1);
        }
        if(driveSwitch) {
            speed = ScaleInputs.scaleInputs(-controller1.getRawAxis(4));
            turn = ScaleInputs.scaleInputs(-controller1.getRawAxis(1));

            double left = speed + turn;
            double right = speed - turn;
            fr.set(ControlMode.PercentOutput, right);
            br.set(ControlMode.PercentOutput, right);
            fl.set(ControlMode.PercentOutput, left);
            bl.set(ControlMode.PercentOutput, left);
            }
        else
        {
            speed *=-1;
            turn *=-1;
        }
        }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

}
