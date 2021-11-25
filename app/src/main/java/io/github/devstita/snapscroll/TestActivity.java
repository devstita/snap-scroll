package io.github.devstita.snapscroll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    ListView listView;
    TextView xTextView, yTextView, zTextView;

    SimpleAdapter adapter;

    private SensorManager sensorManager = null;
    private Sensor gyroSensor = null;
    private SensorEventListener gyroSensorEventListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        listView = findViewById(R.id.test_list_view);
        xTextView = findViewById(R.id.test_x_text_view);
        yTextView = findViewById(R.id.test_y_text_view);
        zTextView = findViewById(R.id.test_z_text_view);

        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("key", String.valueOf(i));
            item.put("value", String.valueOf(i * 2));
            data.add(item);
        }

        adapter = new SimpleAdapter(this.getApplicationContext(),
                data,
                android.R.layout.simple_list_item_2,
                new String[]{"key", "value"},
                new int[]{android.R.id.text1, android.R.id.text2});

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyroSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                double x = event.values[0];
                double y = event.values[1];
                double z = event.values[2];

                xTextView.setText(String.valueOf(x));
                yTextView.setText(String.valueOf(y));
                zTextView.setText(String.valueOf(z));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        listView.setAdapter(adapter);
        sensorManager.registerListener(gyroSensorEventListener, gyroSensor, SensorManager.SENSOR_DELAY_UI);

        new Thread(() -> {
            try {
                Thread.sleep(1500);
                scrollTo(1);
            } catch (InterruptedException e) {
                Log.d("Debugging", "Sleep Error");
            }
        }).start();
    }

    private void scrollTo(int w) {
        switch (w) {
            case 0: // Top
                Log.d("Debugging", "Scroll to Top !");
                listView.smoothScrollToPosition(0);
                break;
            case 1: // Bottom
                Log.d("Debugging", "Scroll to Bottom !");
                listView.smoothScrollToPosition(adapter.getCount() - 1);
                break;
            default:
                throw new UnsupportedOperationException("Scroll to Where?");
        }

    }
}