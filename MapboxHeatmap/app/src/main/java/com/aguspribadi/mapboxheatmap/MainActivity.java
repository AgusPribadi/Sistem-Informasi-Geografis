package com.aguspribadi.mapboxheatmap;

import static android.graphics.Color.rgb;
import static com.mapbox.mapboxsdk.style.expressions.Expression.all;
import static com.mapbox.mapboxsdk.style.expressions.Expression.division;
import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.heatmapDensity;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.linear;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgba;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.toNumber;
import static com.mapbox.mapboxsdk.style.expressions.Expression.zoom;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapIntensity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapWeight;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;
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
import com.aguspribadi.mapboxheatmap.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.HeatmapLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

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
        this.mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
            addHeatmapLayerToMap(style);
        });
    }

    private void addHeatmapLayerToMap(@NonNull Style style) {
        try {
            style.addSource(new GeoJsonSource("earthquakes", new URI("https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson")));
        } catch (URISyntaxException e) {
            Log.e("Heatmap", e.getMessage());
        }

        HeatmapLayer heatmapLayer = new HeatmapLayer("earthquakes-heatmap", "earthquakes");
        heatmapLayer.setMaxZoom(9);
        heatmapLayer.setSourceLayer("earthquakes");
        heatmapLayer.setProperties(
                heatmapColor(
                        interpolate(
                                linear(), heatmapDensity(),
                                literal(0), rgba(33, 102, 172, 25),
                                literal(0.2), rgba(103, 169, 207, 1),
                                literal(0.4), rgba(209, 229, 240, 1),
                                literal(0.6), rgba(253, 219, 199, 1),
                                literal(0.8), rgba(239, 138, 98, 1),
                                literal(1), rgba(178, 24, 43, 1)
                        )
                ),
                heatmapWeight(
                        interpolate(
                                linear(), get("mag"),
                                stop(0, 0), stop(6, 1)
                        )
                ),
                heatmapIntensity(
                        interpolate(
                                linear(), zoom(),
                                stop(0, 1), stop(9, 3)
                        )
                ),
                heatmapRadius(
                        interpolate(
                                linear(), zoom(),
                                stop(0, 2), stop(9, 20)
                        )
                ),
                heatmapOpacity(
                        interpolate(
                                linear(), zoom(),
                                stop(7, 1), stop(9, 0)
                        )
                )
        );
        style.addLayerAbove(heatmapLayer, "waterway-label");
    }


//    private void addClusterEarthquake(@NonNull Style mapStyle) {
//        try {
//            mapStyle.addSource(new GeoJsonSource("earthquakes", new URI("https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson"),
//                    new GeoJsonOptions().withCluster(true).withClusterMaxZoom(14).withClusterRadius(50)));
//        } catch (URISyntaxException uriSyntaxException) {
//            Log.e("MapBox Cluster", uriSyntaxException.getMessage());
//        }
//        SymbolLayer unclusterLayer = new SymbolLayer("earthquake_points", "earthquakes");
//
//        unclusterLayer.setProperties(
//                iconImage("cross-icon-id"),
//                iconSize(division(get("mag"), literal(4.0f))),
//                iconColor(
//                        interpolate(exponential(1), get("mag"),
//                                stop(2.0, rgb(0, 255, 0)),
//                                stop(4.5, rgb(0, 0, 255)),
//                                stop(7.0, rgb(255, 0, 0))
//                        )
//                )
//        );
//        unclusterLayer.setFilter(has("mag"));
//        mapStyle.addLayer(unclusterLayer);
//
//        int[][] layers = new int[][]{
//                new int[]{150, ContextCompat.getColor(this, R.color.mapboxRed)},
//                new int[]{20, ContextCompat.getColor(this, R.color.mapboxGreen)},
//                new int[]{0, ContextCompat.getColor(this, R.color.mapboxBlue)}
//        };
//
//        for (int i = 0; i < layers.length; i++) {
//            CircleLayer circles = new CircleLayer("cluster-" + i, "earthquakes");
//            circles.setProperties(
//                    circleColor(layers[i][1]),
//                    circleRadius(18f)
//            );
//
//            Expression expression = toNumber(get("point_count"));
//            circles.setFilter(
//                    i == 0 ? all(has("point_count"), gte(expression, literal(layers[i][0])))
//                            :
//                            all(has("point_count", lt(expression, literal(layers[i - 1][0]))))
//            );
//
//            mapStyle.addLayer(circles);
//        }
//
//        SymbolLayer count = new SymbolLayer("count", "earthquakes");
//        count.setProperties(
//                textField(Expression.toString(get("point_count"))),
//                textSize(12f),
//                textColor(Color.WHITE),
//                textIgnorePlacement(true),
//                textAllowOverlap(true)
//        );
//        mapStyle.addLayer(count);
//    }

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


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}