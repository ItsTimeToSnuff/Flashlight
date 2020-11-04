package com.linghtinc.flash.light;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    private AtomicBoolean isFlashOn = new AtomicBoolean(false);
    private Camera cam;
    Camera.Parameters p ;
    SurfaceTexture previewTexture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cam = Camera.open();
        p = cam.getParameters();
        final ImageView flashView = findViewById(R.id.flash_view);
        Glide.with(MainActivity.this).load(R.drawable.flash_off).into(flashView);
        flashView.setOnClickListener(v ->{
            if (!isFlashOn.get()) {
                Glide.with(MainActivity.this).load(R.drawable.flash_on).into(flashView);
                turOnOffLED();
                isFlashOn.set(true);
            } else if (isFlashOn.get()) {
                Glide.with(MainActivity.this).load(R.drawable.flash_off).into(flashView);
                turOnOffLED();
                isFlashOn.set(false);
            }
        });
    }

    private void turOnOffLED(){
        if (isFlashOn.get()) {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        } else {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        }
        cam.setParameters(p);
        previewTexture = new SurfaceTexture(0);
        try {
            cam.setPreviewTexture(previewTexture);
        } catch (IOException e){
            e.printStackTrace();
        }
        cam.startPreview();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        cam.setParameters(p);
        previewTexture = new SurfaceTexture(0);
        try {
            cam.setPreviewTexture(previewTexture);
        } catch (IOException e){
            e.printStackTrace();
        }
        cam.startPreview();
        cam.release();
    }
}