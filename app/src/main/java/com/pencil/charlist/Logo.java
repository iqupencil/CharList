package com.pencil.charlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

public class Logo extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Logo.this, Choose_Hero.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}
