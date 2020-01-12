/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team2225.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.team2225.robot.subsystem.ArcadeDrive;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private TalonSRX fl = new TalonSRX(1);
    private TalonSRX bl = new TalonSRX(2);
    private TalonSRX fr = new TalonSRX(3);
    private TalonSRX br = new TalonSRX(4);
    private XboxController controller1 = new XboxController(0);
    private ArcadeDrive drivetrain;

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
        double speed = -controller1.getRawAxis(4) * 0.4;
        double turn = controller1.getRawAxis(1) * 0.8;

        double left = speed + turn;
        double right = speed - turn;
        fr.set(ControlMode.PercentOutput, right);
        br.set(ControlMode.PercentOutput, right);
        fl.set(ControlMode.PercentOutput, left);
        bl.set(ControlMode.PercentOutput, left);
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

}
