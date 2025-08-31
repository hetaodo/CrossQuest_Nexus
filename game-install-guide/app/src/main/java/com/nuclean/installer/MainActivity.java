package com.nuclean.installer;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(layout);
        setContentView(scrollView);

        AssetManager assetManager = getAssets();
        try {
            // List APKs inside assets/apks/
            String[] apks = assetManager.list("apks");
            if (apks != null && apks.length > 0) {
                for (String apkName : apks) {
                    if (apkName.endsWith(".apk")) {
                        TextView textView = new TextView(this);
                        textView.setText(apkName);
                        textView.setTextSize(18);
                        textView.setPadding(20, 20, 20, 20);
                        layout.addView(textView);

                        Button installButton = new Button(this);
                        installButton.setText("Install");
                        installButton.setOnClickListener(v -> {
                            try {
                                File outFile = new File(getCacheDir(), apkName);
                                InputStream is = assetManager.open("apks/" + apkName);
                                FileOutputStream fos = new FileOutputStream(outFile);
                                byte[] buffer = new byte[1024];
                                int read;
                                while ((read = is.read(buffer)) != -1) {
                                    fos.write(buffer, 0, read);
                                }
                                fos.close();
                                is.close();

                                Uri apkUri = FileProvider.getUriForFile(
                                        MainActivity.this,
                                        getPackageName() + ".fileprovider",
                                        outFile
                                );

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        layout.addView(installButton);
                    }
                }
            } else {
                TextView emptyView = new TextView(this);
                emptyView.setText("No APKs found in assets/apks/");
                layout.addView(emptyView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
