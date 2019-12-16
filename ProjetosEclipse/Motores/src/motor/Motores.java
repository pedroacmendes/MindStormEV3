package motor;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.utility.Delay;

public class Motores {

	public static void main(String[] args) {

		System.out.println("Drive Forward\n and Stop\n");
		System.out.println("Press any key to start");

		// Button.LEDPattern(4); // flash green led and
		Sound.beepSequenceUp(); // make sound when ready.

		Button.waitForAnyPress();

		// create two motor objects to control the motors.
		UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.A);
		UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);

		motorA.setPower(50);
		motorB.setPower(50);
		
		Delay.msDelay(2000);

		motorA.setPower(0);
		Delay.msDelay(3000);
		motorA.setPower(50);

		Delay.msDelay(2000);

		// stop motors with brakes on.
		motorA.stop();
		motorB.stop();

		// free up motor resources.
		motorA.close();
		motorB.close();

		Sound.beepSequence(); // we are done.

	}

}
