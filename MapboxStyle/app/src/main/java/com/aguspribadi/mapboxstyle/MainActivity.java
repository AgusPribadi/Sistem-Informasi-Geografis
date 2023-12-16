package com.boygautama.mapboxstyle;

import static android.graphics.Color.rgb;
import static com.mapbox.mapboxsdk.style.expressions.Expression.division;
import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.aguspribadi.mapboxstyle.R;
import com.mapbox.geojson.GeoJson;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private MapboxMap mapboxMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(
                this, getString(R.string.mapbox_access_token)
        );
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.map_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.street_item) {
            mapboxMap.setStyle(Style.MAPBOX_STREETS);
        }
        if (item.getItemId() == R.id.satellite_item) {
            mapboxMap.setStyle(Style.SATELLITE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        this.mapboxMap.setStyle(Style.MAPBOX_STREETS);
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(-0.05911561716705017, 109.35169368350095)).title("UM PONTIANAK"));
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(-0.0641086911073775, 109.35630572227261)).title("SPBU Pertamina 61.781.01"));
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(-0.055396188095609866, 109.36269515934192)).title("SPBU Pertamina 64.781.01"));
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(-0.04654352170416006, 109.35448090993182)).title("SPBU Pertamina Imbon"));
        //MapboxPolyline
        ArrayList<LatLng> point = new ArrayList<>();
        point.add(new LatLng(-0.05911271446936939, 109.35239661059109));
        point.add(new LatLng(-0.05563812888156143, 109.34960480136144));
        point.add(new LatLng(-0.05313764253890711, 109.3475346915023));
        mapboxMap.addPolyline(new PolylineOptions().addAll(point).color(Color.parseColor("#3bb3d0")).width(2));
        //SPBU Pertamina 61.781.01 -> UM
        ArrayList<LatLng> ruteSpbuSatu = new ArrayList<>();
        ruteSpbuSatu.add(new LatLng(-0.06387948196056076, 109.35648096284942));
        ruteSpbuSatu.add(new LatLng(-0.06146153243182018, 109.35447939882954));
        ruteSpbuSatu.add(new LatLng(-0.05884716608421859, 109.35221331361508));
        mapboxMap.addPolyline(new PolylineOptions().addAll(ruteSpbuSatu).color(Color.parseColor("red")).width(4));
        //SPBU Pertamina 64.781.01 -> UM
        ArrayList<LatLng> ruteSpbudua = new ArrayList<>();
        ruteSpbudua.add(new LatLng(-0.055396188095609866, 109.36269515934192));
        ruteSpbudua.add(new LatLng(-0.05545503461514099, 109.36210445854887));
        ruteSpbudua.add(new LatLng(-0.05616715292807354, 109.3613120983795));
        ruteSpbudua.add(new LatLng(-0.057450967663077716, 109.36037431005164));
        ruteSpbudua.add(new LatLng(-0.059497047157340735, 109.35890995762097));
        ruteSpbudua.add(new LatLng(-0.06349704169415839, 109.35632031592195));
        ruteSpbudua.add(new LatLng(-0.06146153243182018, 109.35447939882954));
        ruteSpbudua.add(new LatLng(-0.05884716608421869, 109.35221331361508));
        mapboxMap.addPolyline(new PolylineOptions().addAll(ruteSpbudua).color(Color.parseColor("yellow")).width(2));
        //SPBU Pertamina imbon -> UM
        ArrayList<LatLng> ruteSpbuTiga = new ArrayList<>();
        ruteSpbuTiga.add(new LatLng(-0.04667643624255162, 109.35421925635151));
        ruteSpbuTiga.add(new LatLng(-0.046963459776592706, 109.35440793937859));
        ruteSpbuTiga.add(new LatLng(-0.04784795641636172, 109.35516235805486));
        ruteSpbuTiga.add(new LatLng(-0.055109003600960604, 109.34965728553433));
        ruteSpbuTiga.add(new LatLng(-0.05884716608421869, 109.35221331361508));
        mapboxMap.addPolyline(new PolylineOptions().addAll(ruteSpbuTiga).color(Color.parseColor("black")).width(2));


        //Polygon
        ArrayList<LatLng> polygon = new ArrayList<>();
        polygon.add(new LatLng(-0.06136623765988779, 109.3514599464661));
        polygon.add(new LatLng(-0.06283554925199297, 109.3526152115618));
        polygon.add(new LatLng(-0.06152327078291037, 109.35435371618169));
        polygon.add(new LatLng(-0.06025584946933533, 109.3532433087468));
        mapboxMap.addPolygon(new PolygonOptions().addAll(polygon).fillColor(Color.parseColor("#3bb2d0")));

        //SPBU 1
        ArrayList<LatLng> polygonSpbu1 = new ArrayList<>();
        polygonSpbu1.add(new LatLng(-0.0638681152655647, 109.35625870170803));
        polygonSpbu1.add(new LatLng(-0.0644260488267801, 109.35676784313166));
        polygonSpbu1.add(new LatLng(-0.06474638331389515, 109.35636901571736));
        polygonSpbu1.add(new LatLng(-0.0643199776945989, 109.35598079553174));
        mapboxMap.addPolygon(new PolygonOptions().addAll(polygonSpbu1).fillColor(Color.parseColor("red")));

        //SPBU 2
        ArrayList<LatLng> polygonSpbu2 = new ArrayList<>();
        polygonSpbu2.add(new LatLng(-0.055238975477437315, 109.3626926479895));
        polygonSpbu2.add(new LatLng(-0.05552805857757617, 109.36287899900739));
        polygonSpbu2.add(new LatLng(-0.055590175723571326, 109.36264725456903));
        polygonSpbu2.add(new LatLng(-0.0553512641599222, 109.36243462328643));
        mapboxMap.addPolygon(new PolygonOptions().addAll(polygonSpbu2).fillColor(Color.parseColor("yellow")));

        //SPBU 2
        ArrayList<LatLng> polygonSpbu3 = new ArrayList<>();
        polygonSpbu3.add(new LatLng(-0.04660130026830798, 109.35424472751801));
        polygonSpbu3.add(new LatLng(-0.04637478770650116, 109.35463589020925));
        polygonSpbu3.add(new LatLng(-0.046651741983481425, 109.35459211021163));
        polygonSpbu3.add(new LatLng(-0.04678879141644832, 109.35433419037595));
        mapboxMap.addPolygon(new PolygonOptions().addAll(polygonSpbu3).fillColor(Color.parseColor("black")));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private void addClusterEarthquakes(@NonNull Style mapStyle) {
        try {
            mapStyle.addSource(new GeoJsonSource("earthquakes",
                    new URI("https://www.mapbox.com-gl-js/assets/earthquake.geojson"),
                    new GeoJsonOptions()
                            .withCluster(true)
                            .withClusterMaxZoom(14)
                            .withClusterRadius(50)
            ));
        } catch (URISyntaxException uriSyntaxException) {
            Log.e("Mapbox Cluster", uriSyntaxException.getMessage());
        }
        SymbolLayer unclusterLayer = new SymbolLayer("Eerthquake_points",
                "earthquakes");

        unclusterLayer.setProperties(
                iconImage("cross-icon-id"),
                iconSize(division(get("mag"), literal(4.0f))),
                iconColor(interpolate(exponential(1), get("mag"),
                        stop(2.0, rgb(0, 255, 0),
                        stop(4.5, rgb(0, 0, 255),
                        stop(7, rgb(255, 0, 0)),
                        ))
        );
                unclusterLayer.setFilter(has("mag"));
                mapStyle.addLayer(unclusterLayer);

                int[][] layers = new int[][] {
                        new int[] {150, ContextCompat.getColor(this, R.color.mapboxRed)},
                        new int[] {20, ContextCompat.getColor(this, R.color.mapboxGreen)},
                        new int[] {0, ContextCompat.getColor(this, R.color.mapboxBlue)},
                };

                for (int i = 0; i < layers.length; i++)
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}