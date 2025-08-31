package com.nuclean.gameb;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String ACTION_PROGRESS = "com.crossquest.nexus.ACTION_PROGRESS";
    private static final String PERMISSION = "com.crossquest.nexus.permission.PROGRESS";
    private static final String TARGET_PKG = "com.nuclean.gamea"; // GameA package

    private ImageButton btnGetKey;
    private TextView tvGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetKey = findViewById(R.id.btn_get_key);
        tvGuide = findViewById(R.id.tv_guide);

        btnGetKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onKeyPicked();
            }
        });
    }

    private void onKeyPicked() {
        // 1. Hide key
        btnGetKey.setVisibility(View.GONE);

        // 2. Change text
        tvGuide.setText("You have picked up the key");

        // 3. Toast
        Toast.makeText(this, "Key picked!", Toast.LENGTH_SHORT).show();

        // 4. Send broadcast
        sendKeyBroadcast("p1", "gold");
    }

    private void sendKeyBroadcast(String playerId, String keyId) {
        try {
            // Build JSON payload
            JSONObject payload = new JSONObject();
            payload.put("type", "KEY_ACQUIRED");
            payload.put("playerId", playerId);
            payload.put("keyId", keyId);
            payload.put("ts", System.currentTimeMillis());

            // Create broadcast intent
            Intent intent = new Intent(ACTION_PROGRESS);
            intent.setPackage(TARGET_PKG); // explicitly target GameA
            intent.putExtra("payload", payload.toString());

            // Send broadcast with permission
            sendBroadcast(intent, PERMISSION);

            // Debug toast to confirm send
            Toast.makeText(this, "Broadcast sent to GameA", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Broadcast error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
