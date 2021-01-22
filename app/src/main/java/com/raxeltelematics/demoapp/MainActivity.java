package com.raxeltelematics.demoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raxeltelematics.v2.sdk.TrackingApi;
import com.raxeltelematics.v2.sdk.utils.permissions.PermissionsDialogFragment;
import com.raxeltelematics.v2.sdk.utils.permissions.PermissionsWizardActivity;

public class MainActivity extends AppCompatActivity {

    private static final String YOUR_TOKEN = "313af61d-0ec5-471a-81c2-772c45337bdb";
    private TrackingApi trackingApi;

    public MainActivity() {
        this.trackingApi = TrackingApi.Companion.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText deviceId = findViewById(R.id.deviceId);
        deviceId.setText(YOUR_TOKEN);
        ((Button) this.findViewById(R.id.dashboardButton)).setOnClickListener((View.OnClickListener) ((View.OnClickListener) it -> {
            if (trackingApi.isSdkEnabled()) {
                startActivity(new Intent((Context) MainActivity.this, DashboardStatisticsActivity.class));
            } else {
                if (deviceId.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Device ID is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                trackingApi.setDeviceID(deviceId.getText().toString());
                startActivity(new Intent((Context) MainActivity.this, DashboardStatisticsActivity.class));
            }
        }));

        ((Button) this.findViewById(R.id.tracksButton)).setOnClickListener((View.OnClickListener) ((View.OnClickListener) it -> {
            if (trackingApi.isSdkEnabled()) {
                startActivity(new Intent(this, TripsListActivity.class));
            } else {
                if (deviceId.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Device ID is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                trackingApi.setDeviceID(deviceId.getText().toString());
                startActivity(new Intent(this, TripsListActivity.class));
            }
        }));

        ((Button) this.findViewById(R.id.enable_manual_sdk)).setOnClickListener((View.OnClickListener) ((View.OnClickListener) it -> {
            if (deviceId.getText().toString().isEmpty()) {
                Toast.makeText(this, "Device ID is empty", Toast.LENGTH_LONG).show();
                return;
            }
            if (!trackingApi.isAllRequiredPermissionsAndSensorsGranted()) {
                Toast.makeText(this, "Please grant all required permissions", Toast.LENGTH_LONG).show();
                return;
            }
            trackingApi.setDeviceID(deviceId.getText().toString());
            trackingApi.setEnableSdk(true);
        }));

        ((Button) this.findViewById(R.id.disable_manual_sdk)).setOnClickListener((View.OnClickListener) ((View.OnClickListener) it -> {
            trackingApi.setEnableSdk(false);
        }));

        ((Button) this.findViewById(R.id.start)).setOnClickListener((View.OnClickListener) ((View.OnClickListener) it -> {
            trackingApi.startTracking();
        }));

        ((Button) this.findViewById(R.id.stop)).setOnClickListener((View.OnClickListener) ((View.OnClickListener) it -> {
            trackingApi.stopTracking();
        }));

        ((Button) this.findViewById(R.id.startPermissionsWizard)).setOnClickListener((View.OnClickListener) ((View.OnClickListener) it -> {
            if (!TrackingApi.getInstance().isAllRequiredPermissionsAndSensorsGranted()) {
                startActivityForResult(PermissionsWizardActivity.Companion.getStartWizardIntent(
                        this,
                        false,
                        true
                ), PermissionsWizardActivity.WIZARD_PERMISSIONS_CODE);
            } else
                Toast.makeText(this, "All permissions are already granted", Toast.LENGTH_SHORT).show();
        }));

        ((Button) this.findViewById(R.id.startPermissionsDialog)).setOnClickListener((View.OnClickListener) ((View.OnClickListener) it -> {

            if (!TrackingApi.getInstance().isAllRequiredPermissionsAndSensorsGranted()) {
                PermissionsDialogFragment permsFragment = PermissionsDialogFragment.Companion.newInstants(null, null, true);
                permsFragment.setPermissionsGrantedListener((PermissionsDialogFragment.PermissionsGrantedListener) (new PermissionsDialogFragment.PermissionsGrantedListener() {
                    public void onGrantedStatus(boolean allPermsGranted) {
                        Log.d("TAG", "PermissionsDialogFragment onGrantedStatus: " + allPermsGranted);
                    }
                }));
                permsFragment.show(getSupportFragmentManager(), "telematics_permission_tag");
            } else
                Toast.makeText(this, "All permissions are already granted", Toast.LENGTH_SHORT).show();
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50005) {
            switch(resultCode) {
                case -1:
                    Log.d("TAG", "onActivityResult: WIZARD_RESULT_ALL_GRANTED");
                    Toast.makeText((Context)this, (CharSequence)"All permissions was granted", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Log.d("TAG", "onActivityResult: WIZARD_RESULT_CANCELED");
                    Toast.makeText((Context)this, (CharSequence)"Wizard cancelled", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Log.d("TAG", "onActivityResult: WIZARD_RESULT_NOT_ALL_GRANTED");
                    Toast.makeText((Context)this, (CharSequence)"All permissions was not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

}