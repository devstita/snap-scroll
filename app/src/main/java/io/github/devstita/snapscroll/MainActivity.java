package io.github.devstita.snapscroll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Switch statusSwitch;
    Button nextButton;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.updateDisplaySize(this);

        startActivity(new Intent(getApplicationContext(), TestActivity.class));

        statusSwitch = findViewById(R.id.main_status_switch);
        nextButton = findViewById(R.id.main_next_button);

        sharedPreferences = getSharedPreferences("Sp", MODE_PRIVATE);
        boolean enabled = sharedPreferences.getBoolean("Enabled", false);
        statusSwitch.setChecked(enabled);

        statusSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("Enabled", isChecked).apply();
        });

        nextButton.setOnClickListener((view) -> {
            Intent nextIntent = new Intent(this.getApplicationContext(), TestActivity.class);
            startActivity(nextIntent);
        });
    }
}