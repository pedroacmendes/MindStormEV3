package bluetoothConection;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

import java.lang.Math;

public class MotorControl {
	private UnregulatedMotor leftMotor = null;
	private UnregulatedMotor rightMotor = null;
	//private static int MAX_POWER = 50; // maximum engine power
	
	public MotorControl() {
		leftMotor = new UnregulatedMotor(MotorPort.A);
		rightMotor = new UnregulatedMotor(MotorPort.B);

		//freeMotors();
	}
/*
	void freeMotors() {
		leftMotor.flt();
		rightMotor.flt();
	}
*/
	public void executeCommand(int command) // command -- degrees, in which the robot should move (in a coordinate
											// system ; unit circle)
	{
		//System.out.println("cmd recv: " + command);
		// received command to shut down engines ?
	
		if ( command == 825307441 ) {
			System.out.println("anda pra frente");
			leftMotor.setPower(50);
			rightMotor.setPower(50);
		} else if(command == 875836468) {
			System.out.println("anda pra a direita");
			leftMotor.setPower(0);
			rightMotor.setPower(50);
		} else if(command == 875836211) {
			System.out.println("anda pra a esquerda");
			leftMotor.setPower(50);
			rightMotor.setPower(0);
		} 
		else if(command == 842150450) {
			System.out.println("Parar");
			leftMotor.setPower(0);
			rightMotor.setPower(0);
		} 
	}

}
