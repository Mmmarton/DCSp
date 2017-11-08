package dcsp.utcluj.edu.locationapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LocationListener {

  private boolean autoSend;
  private Thread updateThread;
  private Location location;
  private String device_id;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    try {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      setupLocationService();
      device_id = Settings.Secure
          .getString(this.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
      Log.i("deviceId", device_id);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressLint("MissingPermission")
  private void setupLocationService() {
    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);
    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, this);
  }

  private void postToServer() {
    EditText editIP = findViewById(R.id.ipText);
    String serverIP = editIP.getText().toString();
    JSONObject postData = new JSONObject();
    try {
      postData.put("x", location.getLatitude());
      postData.put("y", location.getLongitude());
      postData.put("deviceId", device_id);

      new ServerApi().execute("http://" + serverIP + ":8080/api/points", postData.toString());
    }
    catch (JSONException e) {
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
    postToServer();
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
          }
          catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
    updateThread.start();
  }

  @Override
  public void onLocationChanged(Location location) {
    Button button = findViewById(R.id.button);
    CheckBox checkBox = findViewById(R.id.checkBox);
    TextView connectingText = findViewById(R.id.connectingText);
    button.setEnabled(true);
    checkBox.setEnabled(true);
    connectingText.setVisibility(View.GONE);
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
