package asd;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class af {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LCD.drawString("Kick", 0, 4);
        Delay.msDelay(2000);
        Sound.beep();
	}

}
