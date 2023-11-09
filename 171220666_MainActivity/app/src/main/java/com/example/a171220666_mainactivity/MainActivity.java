package com.example.a171220666_mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;

import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}