package bluetoothConection;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

import java.lang.Math;

public class MotorControl {
	private UnregulatedMotor leftMotor = null;
	private UnregulatedMotor rightMotor = null;
	
	public MotorControl() {
		leftMotor = new UnregulatedMotor(MotorPort.A);
		rightMotor = new UnregulatedMotor(MotorPort.B);

	}

	public void executeCommand(int command) 
	{
		//System.out.println("cmd recv: " + command);
	
		if ( command == 943208504 ) {
			//System.out.println("anda pra frente");
			leftMotor.setPower(50);
			rightMotor.setPower(50);
		} else if(command == 825241904) {
			//System.out.println("anda pra a direita");
			leftMotor.setPower(0);
			rightMotor.setPower(50);
		} else if(command == 825504052) {
			//System.out.println("anda pra a esquerda");
			leftMotor.setPower(50);
			rightMotor.setPower(0);
		} else if(command == 842281524) {
			//System.out.println("Parar");
			leftMotor.setPower(0);
			rightMotor.setPower(0);
		} else if(command == 892351792) {
			//System.out.println("Para traz");
			leftMotor.setPower(-50);
			rightMotor.setPower(-50);
		}
	}

}
