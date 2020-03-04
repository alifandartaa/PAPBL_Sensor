package com.example.sensoraccell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView _txView;
    SensorManager _sM;
    List _listSen;
    List listProx;
    ConstraintLayout layout;
    boolean isAccelOn = true;
    boolean isProxiOnOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _sM = (SensorManager) getSystemService(SENSOR_SERVICE);
        _txView = findViewById(R.id.TextHasil);
        _listSen = _sM.getSensorList(Sensor.TYPE_ACCELEROMETER);
        listProx = _sM.getSensorList(Sensor.TYPE_PROXIMITY);
        layout = findViewById(R.id.layout);

        SensorEventListener sel = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float [] value = event.values;
                _txView.setText("X : "+value[0]+" Y : "+value[1]+" z : "+value[2]);
//                if(isAccelOn == true){
                    if(value[0] < -1){
                        layout.setBackgroundResource(R.color.colorYellow);
                    }else if(value[0] > 1){
                        layout.setBackgroundResource(R.color.colorBlue);
                    }else{
                        layout.setBackgroundResource(R.color.colorGreen);
                    }
//                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        if(_listSen.size()>0)
        {
            _sM.registerListener(sel,(Sensor)_listSen.get(0),SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {}

        SensorEventListener sensProx = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float [] value = event.values;
//                if()
                if(value[0]==0){
//                    Toast.makeText(MainActivity.this, ""+value[0], Toast.LENGTH_SHORT).show();
                    Log.d("tag","value"+value[0]);
                    layout.setBackgroundResource(R.color.colorAccent);
                }else{
                    layout.setBackgroundResource(R.color.colorGreen);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        if(listProx.size()>0)
        {
            _sM.registerListener(sensProx,(Sensor)listProx.get(0),SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {}
    }
}
