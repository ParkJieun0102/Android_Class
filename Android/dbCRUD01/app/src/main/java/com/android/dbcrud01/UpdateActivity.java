package com.android.dbcrud01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    final static String TAG = "UpdateActivity";
    String urlAddr = null;
    String scode, sname, sdept, sphone, macIP;
    TextView Ucode;
    EditText Uname, Udept, Uphone;
    Button btnUpadate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // intent 를 받아온다.
        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        urlAddr = "http://" + macIP + ":8080/test/studentUpdate.jsp?";  // ?(물음표) 주의

        Ucode = findViewById(R.id.update_code);
        Uname = findViewById(R.id.update_name);
        Udept = findViewById(R.id.update_dept);
        Uphone = findViewById(R.id.update_phone);

        btnUpadate = findViewById(R.id.update_Btn);


        Ucode.setText(intent.getStringExtra("code"));
        Uname.setText(intent.getStringExtra("name"));
        Udept.setText(intent.getStringExtra("dept"));
        Uphone.setText(intent.getStringExtra("phone"));


        btnUpadate.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            scode = Ucode.getText().toString();
            sname = Uname.getText().toString();
            sdept = Udept.getText().toString();
            sphone = Uphone.getText().toString();
            // get 방식
            urlAddr = urlAddr + "code=" + scode + "&name="+ sname+ "&dept="+ sdept+ "&phone="+ sphone;
            connectUpdateData();
            Toast.makeText(UpdateActivity.this, scode+" 가 수정 되었습니다.", Toast.LENGTH_SHORT).show();
        }
    };

    private void connectUpdateData(){
        try {
            CUDNetworkTask updateworkTask = new CUDNetworkTask(UpdateActivity.this, urlAddr);
            updateworkTask.execute().get();

        }catch (Exception e){
            e.printStackTrace();
        }

        finish();
    }

}