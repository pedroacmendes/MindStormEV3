package bluetoothConection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.hardware.Bluetooth;
import lejos.remote.nxt.NXTConnection;

public class MainClass {

	static private DataInputStream inputDataStream = null;
	static private DataOutputStream outputDataStream = null;
	
	static void startBlueToothConnection()
	{
		NXTConnection connection = null;
		try
		{
			System.out.println("init BT connection ...");
			connection = Bluetooth.getNXTCommConnector().waitForConnection(10000, NXTConnection.RAW);
			inputDataStream = connection.openDataInputStream();
			outputDataStream = connection.openDataOutputStream();
			System.out.println("Connected");
		} 
		catch (Exception a)
		{
			System.out.println("ERROR");
			Bluetooth.getNXTCommConnector().cancel();
			
			try {
				inputDataStream.close();
				outputDataStream.close();
				connection.close();
			} catch (IOException e) {
				System.out.println("ERROR: closing failed");
			}
			startBlueToothConnection();
		}
		getData();
	}
	
	public static void main(String[] args) 
	{
		startBlueToothConnection();
	}
	
	static void getData()
	{
		new CommandListener(inputDataStream).run();
	}

}