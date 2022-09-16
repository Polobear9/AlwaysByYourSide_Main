package com.github.Polobear9.alwaysbyyourside_main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.Polobear9.alwaysbyyourside_main.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_ENABLE_BT = 1;
    private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        //check the bluetooth is connect.. ?
        Button navigation_home_bt = findViewById(R.id.bt_home);
        navigation_home_bt.setOnClickListener(this);
        navigation_home_bt.getAccessibilityClassName();

    }
    //bluetooth need to receiver and bluetoothAdapter for connect the bluetooth.
    BluetoothHeadset bluetoothHeadset;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra((BluetoothDevice.EXTRA_DEVICE));
                @SuppressLint("MissingPermission") String deviceName = device.getName();
                String deviceHardWareAddress = device.getAddress(); //
            }
        }
    };

    @Override
    //it is not work in phone and amul... need to check how to display the bluetooth on intent.
    public void onClick(View view) {
        //Get the Default Adapter
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        BluetoothProfile.ServiceListener profileListener = new BluetoothProfile.ServiceListener() {
            @Override
            public void onServiceConnected(int profile, BluetoothProfile proxy) {
                if(profile == BluetoothProfile.HEADSET){
                    bluetoothHeadset = (BluetoothHeadset) proxy;
                }
            }

            @Override
            public void onServiceDisconnected(int profile) {
                if(profile == BluetoothProfile.HEADSET){
                    bluetoothHeadset = null;
                }

            }
        };
        if (bluetoothAdapter == null) {
            System.out.println("this device can not use a bluetooth check again.");
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
        bluetoothAdapter.startDiscovery();
        Intent startBt = new Intent();
        Toast.makeText(this, "start the internet", Toast.LENGTH_SHORT).show();
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

    }
}