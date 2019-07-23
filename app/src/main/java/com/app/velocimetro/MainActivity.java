package com.app.velocimetro;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "buscandoLocation";
    private TextView currentSpeed;
    private TextView maximumSpeed;
    private LocationManager locationManager;
    private boolean isGPSEnabled = false;
    private Location location;
    private float maximum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentSpeed = (TextView) this.findViewById(R.id.currentSpeed);
        maximumSpeed = (TextView) this.findViewById(R.id.maximumSpeed);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //verificando ou solicitando permissões
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else

        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGPSEnabled) {
            activeGPS(this, "Ative o GPS para usar o velocímetro!");
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0, this);

            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        //button close
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClose(MainActivity.this, "Deseja fechar?");
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        try {
            float nCurrentSpeed = (location.getSpeed() * 3600 / 1000);
            String currentSpeed = String.format("%.2f", nCurrentSpeed);
            this.currentSpeed.setText(currentSpeed + "");
            Log.d(TAG, "km " + nCurrentSpeed);
            if (nCurrentSpeed > maximum) {
                maximum = nCurrentSpeed;
                maximumSpeed.setText(currentSpeed + " ");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 102) {
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGPSEnabled) {
                activeGPS(this, "Ative o GPS para usar o velocímetro!");
            } else {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        0, this);
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        }
    }
    //method with dialog active GPS
    public void activeGPS(Context context, String mensagem) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.dialog_gps);
        dialog.setCancelable(false);
        TextView texto = (TextView) dialog.findViewById(R.id.texto);
        texto.setText(mensagem);
        LinearLayout ll_ok_close = (LinearLayout) dialog.findViewById(R.id.ok_btn);
        ImageView ll_ok_cancel = (ImageView) dialog.findViewById(R.id.voltar);
        ll_ok_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 102);
                dialog.dismiss();
            }
        });
        ll_ok_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //method with dialog exit application
    public void dialogClose(Context context, String mensagem) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.dialog_close);
        dialog.setCancelable(false);
        TextView texto = (TextView) dialog.findViewById(R.id.texto);
        texto.setText(mensagem);
        LinearLayout ll_ok_close = (LinearLayout) dialog.findViewById(R.id.ok_btn);
        ImageView ll_ok_cancel = (ImageView) dialog.findViewById(R.id.voltar);
        ll_ok_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });
        ll_ok_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
