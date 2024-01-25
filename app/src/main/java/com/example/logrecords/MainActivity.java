package com.example.logrecords;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private boolean isCallLogDisplayed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkPermission()) {
            // Proceed to display fragments if permission is granted
            displayFragments();
        } else {
            requestPermission();
        }

        Button switchLogsButton = findViewById(R.id.switchLogsButton);
        switchLogsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle between Call Logs and Message Logs
                toggleFragments();
            }
        });
    }

    private void toggleFragments() {
        // Toggle between displaying Call Logs and Message Logs
        Fragment newFragment;
        if (isCallLogDisplayed) {
            newFragment = new MessageLogFragment();
        } else {
            newFragment = new CallLogFragment();
        }

        // Replace the current fragment with the new one
        replaceFragment(newFragment);

        // Update the flag
        isCallLogDisplayed = !isCallLogDisplayed;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) ==
                        PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS},
                PERMISSION_REQUEST_CODE);
    }

    private void displayFragments() {
        // Create instances of the fragments
        CallLogFragment callLogFragment = new CallLogFragment();

        // Begin the transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace the content frame with the call log fragment by default
        transaction.replace(R.id.fragmentContainer, callLogFragment);

        // Commit the transaction
        transaction.commit();
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to display fragments
                displayFragments();
            } else {
                // Permission denied, show a toast or take appropriate action
                Toast.makeText(this, "Permission denied. App cannot access call and message logs.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
