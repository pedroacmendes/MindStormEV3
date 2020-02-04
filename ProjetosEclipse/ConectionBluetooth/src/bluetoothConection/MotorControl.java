package bluetoothConection;

import lejos.hardware.Button;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.remote.ev3.RemoteRequestPort;
import lejos.ev3.*;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import java.lang.Math;

public class MotorControl {
	private UnregulatedMotor leftMotor = null;
	private UnregulatedMotor rightMotor = null;

	EV3ColorSensor colorsensor = new EV3ColorSensor(SensorPort.S3);

	public MotorControl() {
		leftMotor = new UnregulatedMotor(MotorPort.A);
		rightMotor = new UnregulatedMotor(MotorPort.B);
	}

	public void executeCommand(int command) {
		
		//System.out.println("cmd recv: " + command);

		if (command == 943208504) {
			// System.out.println("anda pra frente");
			leftMotor.setPower(50);
			rightMotor.setPower(50);
		} else if (command == 825241904) {
			// System.out.println("anda pra a direita");
			leftMotor.setPower(0);
			rightMotor.setPower(50);
		} else if (command == 825504052) {
			// System.out.println("anda pra a esquerda");
			leftMotor.setPower(50);
			rightMotor.setPower(0);
		} else if (command == 842281524) {
			// System.out.println("Parar");
			leftMotor.setPower(0);
			rightMotor.setPower(0);
		} else if (command == 892351792) {
			// System.out.println("Para tras");
			leftMotor.setPower(-50);
			rightMotor.setPower(-50);
		} else if (command == 858927922) {
			// System.out.println("Para andar sozinho");
			leftMotor.setPower(50);
			rightMotor.setPower(50);
			Delay.msDelay(3000);
			leftMotor.setPower(0);
			rightMotor.setPower(50);
			Delay.msDelay(3000);
			leftMotor.setPower(50);
			rightMotor.setPower(0);
			Delay.msDelay(3000);
			leftMotor.setPower(50);
			rightMotor.setPower(50);
			Delay.msDelay(3000);
			leftMotor.setPower(0);
			rightMotor.setPower(0);
		} else if (command == 825241649) {
			cores();
		} else if ( command == 909129264) {
			MainClass.sair();
		}

	}

	public void cores() {
		int a = colorsensor.getColorID();
		if (a == 0) {
			System.out.println("Detetei Vermelho");
			Button.LEDPattern(2); // vermelho
		} else if (a == 1) {
			System.out.println("Detetei Verde");
			Button.LEDPattern(1); // verde
		} else if (a == 2) {
			System.out.println("Detetei Azul");
			Button.LEDPattern(4); // verde a piscar
		} else if (a == 3) {
			System.out.println("Detetei Amarelo");
			Button.LEDPattern(3); // laranja
		} else {
			System.out.println("Nao conheco esta cor :(");
		}
	}

}
