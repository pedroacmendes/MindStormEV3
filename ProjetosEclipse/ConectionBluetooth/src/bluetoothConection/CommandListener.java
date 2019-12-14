package bluetoothConection;

import java.io.DataInputStream;

public class CommandListener {
	
	DataInputStream inputDataStream = null;
	MotorControl motorControl = null;
	
	public CommandListener(DataInputStream inputDataStream)
	{
		this.inputDataStream = inputDataStream;
		motorControl = new MotorControl();
	}
	
	public void run() {
		System.out.println("Recv:");
		int input = 1;
		while(input > -2)
		{
			try 
			{
				input = inputDataStream.readInt();
				motorControl.executeCommand(input);
			}
			catch (Exception e) {
				System.out.println("ERROR: BT server crashed!");
				MainClass.startBlueToothConnection();
			}
		}
	}

}
