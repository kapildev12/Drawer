package qazi.tooba.com.drawer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import qazi.tooba.com.drawer.Common.Common;
import qazi.tooba.com.drawer.Model.FCMResponse;
import qazi.tooba.com.drawer.Model.Notification;
import qazi.tooba.com.drawer.Model.Sender;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Marker driverMarker;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals("Started"))
                findViewById(R.id.btCancel).setVisibility(View.GONE);
            else if (intent.getAction() != null && intent.getAction().equals("Ended"))
                finish();
            else {
                String lat = intent.getStringExtra("lat");
                String lng = intent.getStringExtra("lng");
                if (lat != null && lng != null) {
                    LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                    checkMarker(latLng);
                    driverMarker.setPosition(latLng);
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        setCancelListener();
        setDriverListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("Location");
        filter.addAction("Started");
        filter.addAction("Ended");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        String lat = getIntent().getStringExtra("lat");
        String lng = getIntent().getStringExtra("lng");
        if (lat != null && lng != null && driverMarker == null) {
            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            checkMarker(latLng);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
        }
    }

    private void checkMarker(@NonNull LatLng latLng) {
        if (driverMarker == null) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title("Driver")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ambulance_foreground));
            this.driverMarker = map.addMarker(options);
        }
    }

    private void setCancelListener() {
        findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String driver = getIntent().getStringExtra("driver");
                Notification notification = new Notification("Cancel", "", "");
                Sender send = new Sender(driver, notification);
                Common.getFCMService().sendMessage(send).enqueue(new Callback<FCMResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<FCMResponse> call, @NonNull Response<FCMResponse> response) {
                        if (response.body() != null && response.body().success == 1) {
                            Toast.makeText(DriverActivity.this, "Booking Cancelled", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<FCMResponse> call, @NonNull Throwable t) {

                    }
                });
            }
        });
    }

    private void setDriverListener() {
        findViewById(R.id.btDriver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = getIntent().getStringExtra("name");
                String number = getIntent().getStringExtra("number");
                BottomSheetRiderFragment sheet = BottomSheetRiderFragment.newInstance(name, number);
                sheet.show(getSupportFragmentManager(), "sheet");
            }
        });
    }
}