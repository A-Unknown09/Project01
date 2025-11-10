package com.example.checkbox_projects;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CheckBox checkboxEnableDialog;
    private TextView textViewStatus;
    private ConnectivityReceiver connectivityReceiver;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkboxEnableDialog = findViewById(R.id.checkboxEnableDialog);
        Button buttonShowStatus = findViewById(R.id.buttonShowStatus);
        textViewStatus = findViewById(R.id.textViewStatus);

        buttonShowStatus.setOnClickListener(v -> {
            if (checkboxEnableDialog.isChecked()) {
                String status = getInternetStatus();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Internet Status")
                        .setMessage(status)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        // Handle checkbox toggle
        checkboxEnableDialog.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                registerConnectivityReceiver();
            } else {
                unregisterConnectivityReceiver();
                textViewStatus.setText("Internet status will appear here");
            }
        });

        // Start with receiver active
        registerConnectivityReceiver();
    }

    private String getInternetStatus() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm == null) return "No internet connection";

        Network activeNetwork = cm.getActiveNetwork();
        if (activeNetwork == null) return "No internet connection";

        NetworkCapabilities capabilities = cm.getNetworkCapabilities(activeNetwork);
        if (capabilities == null) return "No internet connection";

        boolean wifi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        boolean mobile = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);

        if (wifi) return "Connected via Wi-Fi";
        else if (mobile) return "Connected via Mobile Data";
        else return "No active internet connection";
    }

    private void registerConnectivityReceiver() {
        if (connectivityReceiver == null) {
            connectivityReceiver = new ConnectivityReceiver(status -> runOnUiThread(() -> textViewStatus.setText(status)));
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(connectivityReceiver, filter);
        }
    }

    private void runOnUiThread(Object ignoredO) {
    }

    private void registerReceiver(ConnectivityReceiver connectivityReceiver, IntentFilter ignoredFilter) {
        this.connectivityReceiver = connectivityReceiver;
    }

    private void unregisterConnectivityReceiver() {
        if (connectivityReceiver != null) {
            unregisterReceiver(connectivityReceiver);
            connectivityReceiver = null;
        }
    }

    private void unregisterReceiver(ConnectivityReceiver connectivityReceiver) {
        this.connectivityReceiver = connectivityReceiver;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterConnectivityReceiver();
    }

    // ---- BroadcastReceiver Inner Class ----
    public static class ConnectivityReceiver extends BroadcastReceiver {
        public interface ConnectivityListener {
            void onStatusChanged(String status);
        }

        private final ConnectivityListener listener;

        public ConnectivityReceiver(ConnectivityListener listener) {
            this.listener = listener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            if (cm == null) return;

            Network activeNetwork = cm.getActiveNetwork();
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(activeNetwork);

            String status;
            if (capabilities == null) {
                status = "No internet connection";
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                status = "Connected via Wi-Fi";
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                status = "Connected via Mobile Data";
            } else {
                status = "No active internet connection";
            }

            if (listener != null) listener.onStatusChanged(status);
        }
    }
}