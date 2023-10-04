package com.vc.rotationtrackinglibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.vc.rotationtracklibrary.RotationTrackActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * you can call RotationTrackActivity and test result
         */
        startActivity(new Intent(this, RotationTrackActivity.class));

    }
}