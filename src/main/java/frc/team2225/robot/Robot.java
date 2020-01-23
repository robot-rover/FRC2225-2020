/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team2225.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.team2225.robot.subsystem.ArcadeDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team2225.robot.ScaleInputs;

import static java.lang.String.valueOf;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS1);
    private TalonSRX fl = new TalonSRX(0);
    private TalonSRX bl = new TalonSRX(1);
    private TalonSRX fr = new TalonSRX(2);
    private TalonSRX br = new TalonSRX(3);
    private VictorSPX RollerIntakeL = new VictorSPX(4);
    private VictorSPX RollerIntakeR = new VictorSPX(5);
    private XboxController controller1 = new XboxController(0);
    private XboxController controller2 = new XboxController(1);
    private boolean driveSwitch;
    private boolean intakeSwitch;
    private NetworkTableEntry myBoolean;
    double speed;
    double turn;
    double gyroval;
    double left;
    double right;
    double oldval;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    private final I2C.Port i2cPort = I2C.Port.kOnboard;

    /**
     * A Rev Color Sensor V3 object is constructed with an I2C port as a
     * parameter. The device will be automatically initialized with default
     * parameters.
     */
    //private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    /**
     * A Rev Color Match object is used to register and detect known colors. This can
     * be calibrated ahead of time or during operation.
     *
     * This object uses a simple euclidian distance to estimate the closest match
     * with given confidence range.
     */
    private final ColorMatch m_colorMatcher = new ColorMatch();

    /**
     * Note: Any example colors should be calibrated as the user needs, these
     * are here as a basic example.
     */
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);



    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        gyro.calibrate();
        myBoolean = Shuffleboard.getTab("Controller Select")
                .add("Joystick Enabled?", false)
                .withWidget("Toggle Button")
                .getEntry();

        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
    }
    public void robotPeriodic(){
        Color detectedColor = m_colorSensor.getColor();
        if (myBoolean.getBoolean(false)==false)
        {driveSwitch ^= true;}
        else if (myBoolean.getBoolean(false)){
            driveSwitch ^=true;
        }
        /**
         * Run the color match algorithm on our detected color
         */
        String colorString;
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
            colorString = "Blue";
        } else if (match.color == kRedTarget) {
            colorString = "Red";
        } else if (match.color == kGreenTarget) {
            colorString = "Green";
        } else if (match.color == kYellowTarget) {
            colorString = "Yellow";
        } else {
            colorString = "Unknown";
        }

        /**
         * Open Smart Dashboard or Shuffleboard to see the color detected by the
         * sensor.
         */
        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorString);
        //Color detectedColor = m_colorSensor.getColor();

        /**
         * The sensor returns a raw IR value of the infrared light detected.
         */
        double IR = m_colorSensor.getIR();
        int proximity = m_colorSensor.getProximity();

        SmartDashboard.putNumber("Proximity", proximity);
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
        if (controller2.getYButtonPressed()){
            intakeSwitch ^=true;
        }
        if(intakeSwitch){
            //RollerIntake.set(ControlMode.PercentOutput, 1);
        }
        if(!driveSwitch) {
            speed = ScaleInputs.scaleInputs(-controller1.getRawAxis(4));
            turn = ScaleInputs.scaleInputs(-controller1.getRawAxis(1));

            left = speed + turn;
            right = speed - turn;
            fr.set(ControlMode.PercentOutput, right);
            br.set(ControlMode.PercentOutput, right);
            fl.set(ControlMode.PercentOutput, left);
            bl.set(ControlMode.PercentOutput, left);
        }
        else
        {

            speed = ScaleInputs.scaleInputs(-controller1.getRawAxis(4))*-1;
            turn = ScaleInputs.scaleInputs(-controller1.getRawAxis(1))*-1;

            left = speed + turn;
            right = speed - turn;
            fr.set(ControlMode.PercentOutput, right);
            br.set(ControlMode.PercentOutput, right);
            fl.set(ControlMode.PercentOutput, left);
            bl.set(ControlMode.PercentOutput, left);

        }

        }


    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

}
