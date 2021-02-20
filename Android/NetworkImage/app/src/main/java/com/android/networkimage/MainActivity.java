package com.android.networkimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    String urlAddr = "http://192.168.43.220:8080/test/img_0214.jpeg";
    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn);
        imageView = findViewById(R.id.imageview);

        button.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn:
                    NetworkTask networkTask = new NetworkTask(MainActivity.this, urlAddr, imageView);
                    networkTask.execute(100);
                    button.setText("Download Complete!");
                    break;
            }
        }
    };
}