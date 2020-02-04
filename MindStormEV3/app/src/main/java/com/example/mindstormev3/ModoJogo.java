package com.example.mindstormev3;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.example.mindstormev3.Conection.getFirebaseUser;

public class ModoJogo extends AppCompatActivity {

    private Button btn_acelera;
    private Button btn_esquerda;
    private Button btn_direita;
    private Button btn_trava;
    private Button btn_tras;
    private Button btn_conec;
    private Button btn_sozinho;
    private Button btn_color;

    public BluetoothAdapter mBTAdapter;
    public Set<BluetoothDevice> mPairedDevices;
    public ArrayAdapter<String> mBTArrayAdapter;
    public ListView mDevicesListView;

    String deviceName = "EV3";

    public Handler mHandler;
    public ConnectedThread mConnectedThread;
    public BluetoothSocket mBTSocket = null;

    public static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public final static int REQUEST_ENABLE_BT = 1;
    public final static int MESSAGE_READ = 2;
    public final static int CONNECTING_STATUS = 3;

    //firebase database
    DatabaseReference reff;
    Movimentos movimentos;

    Date data = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modojogo);

        btn_acelera = (Button) findViewById(R.id.btn_acelera);
        btn_direita = (Button) findViewById(R.id.btn_direita);
        btn_esquerda = (Button) findViewById(R.id.btn_esquerda);
        btn_trava = (Button) findViewById(R.id.btn_trava);
        btn_tras = (Button) findViewById(R.id.btn_tras);
        btn_sozinho = (Button) findViewById(R.id.btn_sozinho);
        btn_conec = (Button) findViewById(R.id.btn_conec);
        btn_color = (Button) findViewById(R.id.btn_color);

        mBTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();

        mDevicesListView = (ListView) findViewById(R.id.devicesListView);
        mDevicesListView.setAdapter(mBTArrayAdapter); // assign model to view
        mDevicesListView.setOnItemClickListener(mDeviceClickListener);

        //bluetoothOn();

        String auth = getFirebaseUser().getEmail();
        Toast.makeText(this, "Welcome " + auth, Toast.LENGTH_SHORT).show();

        auth = auth.replaceAll("[^a-zZ-Z1-9 ]", "");

        movimentos = new Movimentos();
        reff = FirebaseDatabase.getInstance().getReference().child("Movimentos").child(auth);


        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == MESSAGE_READ) {
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        if (mBTArrayAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth device not found!", Toast.LENGTH_SHORT).show();
        } else {
            btn_acelera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConnectedThread != null) {
                        mConnectedThread.write("88");
                        mConnectedThread.write("88");
                        movimentos.setMovimento("Andei para a frente");
                        movimentos.setData(data);
                        reff.push().setValue(movimentos);
                        Toast.makeText(ModoJogo.this, "Estou a andar para a frente", Toast.LENGTH_SHORT).show();
                    }
                    mBTArrayAdapter.clear();
                }
            });

            btn_trava.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConnectedThread != null) {
                        mConnectedThread.write("24");
                        mConnectedThread.write("24");
                        movimentos.setMovimento("Parei de andar");
                        movimentos.setData(data);
                        reff.push().setValue(movimentos);
                        Toast.makeText(ModoJogo.this, "Parei de andar", Toast.LENGTH_SHORT).show();
                    }
                    mBTArrayAdapter.clear();
                }
            });

            btn_esquerda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConnectedThread != null) {
                        mConnectedThread.write("14");
                        mConnectedThread.write("14");
                        movimentos.setMovimento("Andei para a esquerda");
                        movimentos.setData(data);
                        reff.push().setValue(movimentos);
                        Toast.makeText(ModoJogo.this, "Estou a andar para a esquerda", Toast.LENGTH_SHORT).show();
                    }
                    mBTArrayAdapter.clear();
                }
            });

            btn_direita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConnectedThread != null) {
                        mConnectedThread.write("10");
                        mConnectedThread.write("10");
                        movimentos.setMovimento("Andei para a direita");
                        movimentos.setData(data);
                        reff.push().setValue(movimentos);
                        Toast.makeText(ModoJogo.this, "Estou a andar para a direita", Toast.LENGTH_SHORT).show();
                    }
                    mBTArrayAdapter.clear();
                }
            });

            btn_tras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConnectedThread != null) {
                        mConnectedThread.write("50");
                        mConnectedThread.write("50");
                        movimentos.setMovimento("Andei para trás");
                        movimentos.setData(data);
                        reff.push().setValue(movimentos);
                        Toast.makeText(ModoJogo.this, "Estou a andar para trás", Toast.LENGTH_SHORT).show();
                    }
                    mBTArrayAdapter.clear();
                }
            });

           btn_sozinho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConnectedThread != null) {
                        mConnectedThread.write("32");
                        mConnectedThread.write("32");
                        movimentos.setMovimento("Andei sozinho");
                        movimentos.setData(data);
                        reff.push().setValue(movimentos);
                        Toast.makeText(ModoJogo.this, "Estou a andar sozinho", Toast.LENGTH_SHORT).show();
                    }
                    mBTArrayAdapter.clear();
                }
            });

            btn_conec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    discover(v);
                }
            });

            btn_color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConnectedThread != null) {
                        mConnectedThread.write("100");
                        mConnectedThread.write("100");
                        movimentos.setMovimento("Detetei uma cor");
                        movimentos.setData(data);
                        reff.push().setValue(movimentos);
                        Toast.makeText(ModoJogo.this, "Estou a detetar cores", Toast.LENGTH_SHORT).show();
                    }
                    mBTArrayAdapter.clear();
                }
            });
        }
    }

    private void bluetoothOn() {
        if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
    }

    private void bluetoothOff(View view) {
        mBTAdapter.disable();
        Toast.makeText(getApplicationContext(), "Bluetooth turned Off", Toast.LENGTH_SHORT).show();
    }

    private void discover(View view) {
        if (mBTAdapter.isDiscovering()) {
            mBTAdapter.cancelDiscovery();
            Toast.makeText(getApplicationContext(), "Discovery stopped", Toast.LENGTH_SHORT).show();
        } else {
            if (mBTAdapter.isEnabled()) {
                mBTArrayAdapter.clear(); //LIMPA O ARRAY
                mBTAdapter.startDiscovery();
                Toast.makeText(getApplicationContext(), "A procurar EV3", Toast.LENGTH_SHORT).show();
                registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            } else {
                Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
            }
        }
    }

    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                mBTArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    /*
    private void listPairedDevices(View view){
        mPairedDevices = mBTAdapter.getBondedDevices();
        if(mBTAdapter.isEnabled()) {
            for (BluetoothDevice device : mPairedDevices)
                if(deviceName.equals(device.getName())){
                    mBTArrayAdapter.add(device.getName()  + "\n" + device.getAddress());
                }
            Toast.makeText(getApplicationContext(), "Show Paired Devices", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
    }
*/

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

            if (!mBTAdapter.isEnabled()) {
                Toast.makeText(getBaseContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            final String address = info.substring(info.length() - 17);
            final String name = info.substring(0, info.length() - 17);

            // Spawn a new thread to avoid blocking the GUI one
            new Thread() {
                public void run() {
                    boolean fail = false;

                    BluetoothDevice device = mBTAdapter.getRemoteDevice(address);

                    try {
                        mBTSocket = createBluetoothSocket(device);
                    } catch (IOException e) {
                        fail = true;
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                    // Establish the Bluetooth socket connection.
                    try {
                        mBTSocket.connect();
                    } catch (IOException e) {
                        try {
                            fail = true;
                            mBTSocket.close();
                            mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                    .sendToTarget();
                        } catch (IOException e2) {
                            //insert code to deal with this
                            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (fail == false) {
                        mConnectedThread = new ConnectedThread(mBTSocket);
                        mConnectedThread.start();

                        mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                                .sendToTarget();
                    }
                }
            }.start();
        }
    };

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    public static class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private Handler mHandler;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        //SystemClock.sleep(10); //pause and wait for rest of data. Adjust this depending on your sending speed.
                        bytes = mmInStream.available(); // how many bytes are ready to be read?
                        bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget(); // Send the obtained bytes to the UI activity
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }
    
}

