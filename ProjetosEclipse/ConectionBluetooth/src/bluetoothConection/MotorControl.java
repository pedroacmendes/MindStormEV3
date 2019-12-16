package bluetoothConection;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import java.lang.Math;

public class MotorControl {
	private UnregulatedMotor leftMotor = null;
	private UnregulatedMotor rightMotor = null;
	private static int MAX_POWER = 50; // maximum engine power
	
	public MotorControl() {
		leftMotor = new UnregulatedMotor(MotorPort.A);
		rightMotor = new UnregulatedMotor(MotorPort.D);

		freeMotors();
	}

	void freeMotors() {
		leftMotor.flt();
		rightMotor.flt();
	}

	public void executeCommand(int command) // command -- degrees, in which the robot should move (in a coordinate
											// system ; unit circle)
	{
		System.out.println("cmd recv: " + command);
		// received command to shut down engines ?
	
		if ( command == 825307441 ) {
			System.out.println("anda pra frente");
		} else if(command == 2) {
			System.out.println("anda pra a direita");
		} else if(command == 3) {
			System.out.println("anda pra a esquerda");
		} 
		else if(command == 4) {
			System.out.println("anda pra tras");
		} 
	}

}
