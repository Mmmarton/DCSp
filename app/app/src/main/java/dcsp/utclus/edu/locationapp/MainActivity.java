package dcsp.utclus.edu.locationapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private boolean autoSend;
    private Thread updateThread;
    private int updateDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        if (updateThread != null) {
            updateThread.interrupt();
        }
        super.onDestroy();
    }

    public void onCheckboxClicked(View view) {
        EditText editText = findViewById(R.id.editText2);
        Button button = findViewById(R.id.button);
        autoSend = ((CheckBox) view).isChecked();
        editText.setEnabled(autoSend);
        button.setEnabled(!autoSend);
        if (autoSend) {
            activateUpdateThread();
        }
    }

    public void onButtonClicked(View view) {
        sendCoordinates();
    }

    private void sendCoordinates() {
        EditText editLongitude = findViewById(R.id.editLongitude);
        EditText editLatitude = findViewById(R.id.editLatitude);
        editLongitude.setText(String.valueOf(Math.random()));
        editLatitude.setText(String.valueOf(Math.random()));
    }

    private void activateUpdateThread() {
        if (updateThread != null) {
            updateThread.interrupt();
        }
        final Handler handler = new Handler();
        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (autoSend) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            sendCoordinates();
                            updateDelay = Integer.valueOf(((EditText) findViewById(R.id.editText2)).getText().toString());
                        }
                    });

                    try {
                        Thread.sleep(updateDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updateThread.start();
    }
}
