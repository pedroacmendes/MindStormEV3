package com.example.mindstormev3;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.UUID;

public class Example extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private BluetoothAdapter mBluetoothAdapter;
    private static BluetoothDevice mDevice;
    private Button mSendBN;

    private final static String MY_UUID = "00001101-0000-1000-8000-00805f9b34fb";
    private static BluetoothSocket mSocket = null;
    private static String mMessage = "Stop";
    private static PrintStream sender;

    private void findBrick() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                .getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals("EV3"))
                this.mDevice = device;
        }
    }

    private void initBluetooth() {
        Log.d(TAG, "Checking Bluetooth...");
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "Device does not support Bluetooth");
            mSendBN.setClickable(false);
        } else {
            Log.d(TAG, "Bluetooth supported");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            mSendBN.setClickable(false);
            Log.d(TAG, "Bluetooth not enabled");
        } else {
            Log.d(TAG, "Bluetooth enabled");
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "SpeechRecognizer gestartet", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);

        mSendBN = (Button) findViewById(R.id.button);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        initBluetooth();
        findBrick();

        if (mDevice == null) {
            mSendBN.setClickable(false);
            Toast.makeText(this, "No Devices found or BT disabled", Toast.LENGTH_SHORT).show();
            Log.d("onC", "Connected to " + mDevice);
        }

        try {
            createSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startService();
    }

    private void startService() {
        if (PermissionHandler.checkPermission(this, PermissionHandler.RECORD_AUDIO)) {
            Intent i = new Intent(this, BackgroundRecognizerService.class);
            startService(i);
        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionHandler.RECORD_AUDIO && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startService();
            }
        }
    }

    public static void onSend(View view) throws IOException {
        try {
            OutputStream os = mSocket.getOutputStream();
            sender = new PrintStream(os);
            Log.d("onSend", "Message = " + mMessage);
            sender.println(mMessage);
            sender.flush();
            Log.d("onSend", "Message sent");
            mSocket.close();
            Log.d("onSend", "Socket closed");
        } catch (IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void createSocket() throws IOException {
        try {
            UUID uuid = UUID.fromString(MY_UUID);
            mSocket = mDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("createSocket", "Adapter");

        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        mSocket.connect();
        OutputStream os = mSocket.getOutputStream();
        sender = new PrintStream(os);

        Log.d("createSocket", "Fertig, " + "Socket: " + mSocket + " Sender: " + sender + " OutputStream: " + os + " mDevice: " + mDevice.getName());
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "App beendet");
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("onDestroy", "App vollständig beendet");
    }
}
