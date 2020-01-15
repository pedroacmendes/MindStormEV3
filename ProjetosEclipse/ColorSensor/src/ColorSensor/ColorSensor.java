package ColorSensor;

import lejos.remote.ev3.RemoteRequestPort;
import lejos.ev3.*;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class ColorSensor {
	public static void main(String[] args) throws Exception {

		System.out.println("Coloque-me a 1 cm de distancia");
		System.out.println("");
	
		// System.out.println(a);

		EV3ColorSensor colorsensor = new EV3ColorSensor(SensorPort.S3);
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

		System.out.println("");
		System.out.println("Pressione para sair");
		Button.waitForAnyPress();

	}
}