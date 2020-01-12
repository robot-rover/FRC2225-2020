package frc.team2225.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class ArcadeDrive {

    public ArcadeDrive(TalonSRX fl, TalonSRX bl, TalonSRX fr, TalonSRX br, Joystick controller1) {
        double speed = -controller1.getRawAxis(1) * 0.8;
        double turn = controller1.getRawAxis(4) * 0.4;

        double left = speed + turn;
        double right = speed - turn;
        fr.set(ControlMode.PercentOutput, right);
        br.set(ControlMode.PercentOutput, right);
        fl.set(ControlMode.PercentOutput, left);
        bl.set(ControlMode.PercentOutput, left);
    }
}
