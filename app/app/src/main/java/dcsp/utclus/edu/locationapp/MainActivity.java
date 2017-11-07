package dcsp.utclus.edu.locationapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private boolean autoSend;
    private Thread updateThread;
    private Location location;
    private String device_id;
    private String serverIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupLocationService();
        device_id = Settings.Secure.getString(this.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i("deviceId", device_id);
    }

    @SuppressLint("MissingPermission")
    private void setupLocationService() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, this);
    }

    private void callApi() {
        EditText editIP = findViewById(R.id.ipText);
        serverIP = editIP.getText().toString();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("http://" + serverIP + ":8080/api/points/deviceIds");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                if (url != null) {
                    HttpURLConnection connection = null;
                    try {
                        connection = (HttpURLConnection) url.openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("e", connection.toString());
                    try {
                        Log.i("status", "status: " + connection.getResponseCode());
                        if (connection.getResponseCode() == 200) {
                            InputStream responseBody = connection.getInputStream();
                            BufferedReader reader =
                                    new BufferedReader(new InputStreamReader(responseBody, "UTF-8"));
                            Log.i("response", reader.readLine());
                        } else {
                            Log.e("error", "Response error");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void postToServer() {
        JSONObject postData = new JSONObject();
        try {
            postData.put("x", location.getLongitude());
            postData.put("y", location.getLatitude());
            postData.put("deviceId", device_id);

            new ServerApi().execute("http://" + serverIP + ":8080/api/points", postData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (updateThread != null) {
            updateThread.interrupt();
        }
        super.onDestroy();
    }

    public void onCheckboxClicked(View view) {
        Button button = findViewById(R.id.button);
        autoSend = ((CheckBox) view).isChecked();
        button.setEnabled(!autoSend);
        if (autoSend) {
            activateUpdateThread();
        }
    }

    public void onButtonClicked(View view) {
        sendCoordinates();
    }

    private void sendCoordinates() {
        callApi();
        EditText editLongitude = findViewById(R.id.editLongitude);
        EditText editLatitude = findViewById(R.id.editLatitude);
        editLongitude.setText(String.valueOf(location.getLongitude()));
        editLatitude.setText(String.valueOf(location.getLatitude()));
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
                        }
                    });

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updateThread.start();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("message", "l: " + location);
        this.location = location;
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("message", "on");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("message", "off");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
