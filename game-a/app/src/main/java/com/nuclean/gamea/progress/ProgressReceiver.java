package com.nuclean.gamea.progress;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nuclean.gamea.MainActivity;
import com.nuclean.gamea.R;

import org.json.JSONObject;

public class ProgressReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Only react if action is the expected one
        if (!"com.crossquest.nexus.ACTION_PROGRESS".equals(intent.getAction())) {
            return;
        }

        String payload = intent.getStringExtra("payload");
        if (payload == null) return;

        try {
            JSONObject obj = new JSONObject(payload);
            String type = obj.optString("type", "");

            if ("KEY_ACQUIRED".equals(type)) {
                String keyId = obj.optString("keyId", "default");

                // Save to SharedPreferences
                context.getSharedPreferences("progress", Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean("key:" + keyId, true)
                        .apply();

                Log.i("NEXUS", "Key acquired: " + keyId);

                // Update UI: show the key if MainActivity is running
                if (MainActivity.instance != null) {
                    ImageView keyView = MainActivity.instance.findViewById(R.id.key);
                    if (keyView != null) {
                        keyView.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("NEXUS", "Invalid payload", e);
        }
    }
}
