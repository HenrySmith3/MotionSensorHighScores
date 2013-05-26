package com.example.MotionSensorHighScores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Accelerometer extends Activity implements SensorEventListener {
    boolean started = false;
    SensorManager mSensorManager;
    Sensor mAccelerometer;
    float maxAccelX = 0;
    float maxAccelY = 0;
    float maxAccelZ = 0;
    Button button;
    TextView textX;
    TextView textY;
    TextView textZ;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometer);

        button = (Button) findViewById(R.id.startButton);
        textX = (TextView) findViewById(R.id.textViewX);
        textY = (TextView) findViewById(R.id.textViewY);
        textZ = (TextView) findViewById(R.id.textViewZ);

        mSensorManager =  (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        button.setOnClickListener(startListener);
    }


    public View.OnClickListener startListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            started = true;
            button.setText("Stop");
            button.setOnClickListener(stopListener);
            maxAccelX = 0;
            maxAccelY = 0;
            maxAccelZ = 0;
            textX.setText("X: 0");
            textY.setText("Y: 0");
            textZ.setText("Z: 0");
        }
    };

    public View.OnClickListener stopListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            started = false;
            button.setText("Start");
            button.setOnClickListener(startListener);
        }
    };

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER || started == false)
            return;
        float mSensorX = 0, mSensorY = 0, mSensorZ = 0;
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        mSensorX = event.values[0];
        mSensorY = event.values[1];
        mSensorZ = event.values[2];
        if (Math.abs(mSensorX) > maxAccelX) {
            textX.setText("X: " + Math.abs(mSensorX));
            maxAccelX = Math.abs(mSensorX);
        }
        if (Math.abs(mSensorY) > maxAccelY) {
            textY.setText("Y: " + Math.abs(mSensorY));
            maxAccelY = Math.abs(mSensorY);
        }
        if (Math.abs(mSensorZ) > maxAccelZ) {
            textZ.setText("Z: " + Math.abs(mSensorZ));
            maxAccelZ = Math.abs(mSensorZ);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
