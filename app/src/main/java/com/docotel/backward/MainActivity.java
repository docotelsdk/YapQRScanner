package com.docotel.backward;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;

import com.docotel.scanner.BarcodeCallback;
import com.docotel.scanner.BarcodeResult;
import com.docotel.scanner.DecoratedBarcodeView;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;

import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSION = 210;
    private DecoratedBarcodeView barcodeView;
    private String lastText;
    private BeepManager beepManager;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() == null || result.getText().equals(lastText)) {
                lastText = result.getText();
                DialogQRScanning cdd = new DialogQRScanning(MainActivity.this, lastText);
                cdd.show();
                barcodeView.pause();

                return;
            }

            lastText = result.getText();
            DialogQRScanning cdd = new DialogQRScanning(MainActivity.this, lastText);
            cdd.show();
            barcodeView.pause();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);

        if (!hasPermission()) requestPermissions();

    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    private boolean hasPermission() {
        int a = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int b = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return a == PackageManager.PERMISSION_GRANTED && b == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            boolean accessStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean accessCamera = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if ((!accessStorage || !accessCamera) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, REQUEST_PERMISSION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
