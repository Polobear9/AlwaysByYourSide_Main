package com.github.Polobear9.alwaysbyyourside_main;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.Polobear9.alwaysbyyourside_main.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if ((result.getResultCode() == Activity.RESULT_OK)) {

                } else {
                    Log.d("Bt_connectFile", "Connect is Filed");
                }
            });

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    @Override
    public void onClick(View view) {
        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter != null) {
            Log.d("MainActivity", "Bluetooth is support");
            boolean btEnable = mBtAdapter.isEnabled();
            if (!btEnable) {
                Log.d("!btenable start", "btenable zone.");
                Intent btOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activityResultLauncher.launch(btOn);
                Log.d("!btemableFinish", "btEnable finish");
            }
        }

    }
}