package com.github.Polobear9.alwaysbyyourside_main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.Polobear9.alwaysbyyourside_main.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public BluetoothAdapter bluetoothAdapter;
    public Set<BluetoothDevice> mPairedDevices;
    public List<String> mListpairedDevices;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private ActivityMainBinding binding;

/*    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if ((result.getResultCode() == Activity.RESULT_OK)) {

                } else {
                    Log.d("Bt_connectFile", "Connect is Filed");
                }
            });*/

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        Button btButton = findViewById(R.id.bt_home);
        btButton.setOnClickListener(this);


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View view) {
        if(bluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "this Device is not support Bluetooth", Toast.LENGTH_SHORT).show();
        }else{
            if(bluetoothAdapter.isEnabled()){
                Toast.makeText(getApplicationContext(), "Bluetooth is already to start.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Bluetotth is not ready for start.", Toast.LENGTH_SHORT).show();
                Intent start_bt_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(start_bt_intent,BT_REQUEST_ENABLE);
            }

        }
    }
}