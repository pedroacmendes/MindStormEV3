package asd;

import lejos.hardware.Sound;
import lejos.hardware.*;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class af {

	public static void main(String[] args) {
		
		LCD.drawString("Pedro", 0, 4);
        Delay.msDelay(5000);
        Sound.beep();
        
		
		//System.out.println("Hello World!!");
        //Button.waitForAnyPress();
	}

}

