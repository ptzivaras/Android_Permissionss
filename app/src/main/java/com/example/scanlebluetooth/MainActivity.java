package com.example.scanlebluetooth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final int BLUETOOTH_SCAN_CODE = 102;
    Button button0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // o panagiotis leei apo katw na grafw kwdika.
        button0 = findViewById(R.id.button0);
        button0.setOnClickListener(view -> {
            if (areAllPermissionsGranted()) {
                Toast.makeText(MainActivity.this, "All Permission already granted", Toast.LENGTH_SHORT).show();
            } else {
                askAllPermissions();
            }
        });
    }

    public void askAllPermissions() {
        ArrayList<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            checkAndAdd(permissionsList, Manifest.permission.BLUETOOTH_SCAN);
            checkAndAdd(permissionsList, Manifest.permission.BLUETOOTH_CONNECT);
            checkAndAdd(permissionsList, Manifest.permission.BLUETOOTH_ADVERTISE);
        }
        checkAndAdd(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION);
        checkAndAdd(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (!permissionsList.isEmpty()) {
            String[] temp = new String[permissionsList.size()];
            ActivityCompat.requestPermissions(this, permissionsList.toArray(temp), 903);
        }
    }

    public void checkAndAdd(ArrayList<String> permissionList, String permission) {
        if ((ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED)) {
            permissionList.add(permission);
        }
    }

    public boolean areAllPermissionsGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_ADVERTISE) == PackageManager.PERMISSION_GRANTED);
        }
        return (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    // This function is called when user accept or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 903) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; ++i) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, permissions[i] + " Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, permissions[i] + " Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}