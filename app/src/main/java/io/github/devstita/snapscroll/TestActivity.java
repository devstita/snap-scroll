package io.github.devstita.snapscroll;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.HashMap;

public class TestActivity extends AppCompatActivity {
    float startX, startY;
    Handler uiHandler;

    Button scrollUpButton, scrollDownButton;
    ListView listView;
    SimpleAdapter adapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        uiHandler = new Handler();

        scrollUpButton = findViewById(R.id.test_scroll_up_button);
        scrollDownButton = findViewById(R.id.test_scroll_down_button);
        listView = findViewById(R.id.test_list_view);

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

        scrollUpButton.setOnClickListener((view) -> {
            scrollTo(1);
        });

        scrollDownButton.setOnClickListener((view) -> {
            scrollTo(-1);
        });

        listView.setAdapter(adapter);
        listView.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Utils.debug("DOWN");
                    startX = event.getX();
                    startY = event.getY();
                    break;

                case MotionEvent.ACTION_UP:
                    Utils.debug("UP");
                    float endX = event.getX(), endY = event.getY();
                    float deltaX = endX - startX, deltaY = endY - startY;
                    int direction = (int) Math.signum(deltaY);

                    Utils.debug("DeltaX: " + deltaX + ", DeltaY: " + deltaY + ", Direction: " + direction);

                    if (Math.abs(deltaX) > Utils.DISPLAY_WIDTH * 0.4) {
                        Utils.debug("Fast Scroll !");

                        new Thread(() -> {
                            uiHandler.post(() -> listView.setEnabled(false));

                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            uiHandler.post(() -> {
                                Utils.debug("Stop Scroll !");
                                listView.scrollBy(0, 0);
                            });
                        }).start();
                    }

                    break;
            }
            return false;
        });
    }

    private void scrollTo(int w) {
        switch (w) {
            case 1: // Top
                Utils.debug("Scroll to Top !");
                listView.scrollBy(0, 0);
                listView.smoothScrollToPosition(0);
                break;
            case -1: // Bottom
                Utils.debug("Scroll to Bottom !");
                listView.scrollBy(0, 0);
                listView.smoothScrollToPosition(adapter.getCount() - 1);
                break;
            default:
                throw new UnsupportedOperationException("Scroll to Where?");
        }

    }
}