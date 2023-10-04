package com.vc.rotationtracklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class RotationTrackActivity extends AppCompatActivity implements SensorEventListener {

    /**
     * variable initialization
     */
    private SensorManager sensorManager;
    private Sensor rotationSensor;

    /**
     * variable to check very first value of zRotation is captured or not
     */
    private boolean isGetFirstAngleValue;

    /**
     * variable to store very first value of zRotation
     */
    private int startRotationValue = 0;

    /**
     * find UI views
     */

    private TextView angleText;
    private CircleDisplay mCircleDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_track);

        initView();
        initSensor();
        registerSensor();
    }

    void initView() {

        angleText = findViewById(R.id.angleText);
        mCircleDisplay = findViewById(R.id.circle);

        //setup circle UI , you can customize according to requirement
        mCircleDisplay.setAnimDuration(1000);
        mCircleDisplay.setValueWidthPercent(25f);
        mCircleDisplay.setFormatDigits(1);
        mCircleDisplay.setDimAlpha(80);
        mCircleDisplay.setTouchEnabled(false);
        mCircleDisplay.setUnit("%");
        mCircleDisplay.setStepSize(1f);
        mCircleDisplay.setDrawText(true);
        mCircleDisplay.setColor(Color.parseColor("#FF0000"));
    }

    /**
     * call this method to initialize SensorManager and Rotation Sensor
     */
    public void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    /**
     * call this method to register rotation sensor and start receiving rotation value
     * must implement SensorEventListener and its implemented methods
     */
    public void registerSensor() {
        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * call this method when you want to unregister rotation sensor and stop receiving rotation value
     * for example - you can use this function onPause() lifecycle method of your activity
     */
    public void unRegisterSensor() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    /**
     * This override method used to get device zRotation angle value when device is rotating in 360 degree
     * You can use progress value to show your UI for ex. you can fill the circle color based on progress value
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor == rotationSensor) {
            float[] rotationMatrix = new float[9];
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

            float[] orientation = new float[3];
            SensorManager.getOrientation(rotationMatrix, orientation);

            // Calculate the angle of rotation around the Z axis
            float zRotationInRadians = orientation[0];
            float zRotationInDegrees = (float) Math.toDegrees(zRotationInRadians);

            // Convert zRotationInDegrees to a value between 0 and 360
            float zRotation = (zRotationInDegrees + 360) % 360;

            angleText.setText("Angle is: " + zRotation);

            // store zRotation first rotation value
            if (!isGetFirstAngleValue) {
                Log.e("isGetFirstAngleValue", "isGetFirstAngleValue");
                startRotationValue = (int) zRotation;
                isGetFirstAngleValue = true;
            }

            /**
             * This function gives progress value from 0 to 100, value 100 shows that device completed 360 degree rotation
             * You can use progress value to show your UI for ex. you can fill the circle color based on progress value
             */
            getProgressFromAngle((int) zRotation);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * This function gives progress value from 0 to 100, value 100 shows that device completed 360 degree rotation
     * You can use progress value to show your UI for ex. you can fill the circle color based on progress value
     */

    public void getProgressFromAngle(int currentZRotation) {

        int finalRotation = 0;
        if (currentZRotation > startRotationValue) {
            finalRotation = currentZRotation - startRotationValue;
        } else {
            int dif = 360 - startRotationValue;
            finalRotation = dif + currentZRotation;
        }

        //final progress value and show fill circle UI based on progress
        // can you use this progress value with your custom UI
        int progress = (finalRotation * 100) / 360;

        if (progress > 60)
            mCircleDisplay.showValue(0, 100f, false);
        else mCircleDisplay.showValue(progress, 100f, false);
    }
}