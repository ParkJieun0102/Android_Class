package com.android.dbcrud01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    final static String TAG = "InsertActivity";
    String urlAddr = null;
    String scode, sname, sdept, sphone, macIP;
    EditText Ecode, Ename, Edept, Ephone;
    Button btnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        // intent 를 받아온다.
        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        urlAddr = "http://" + macIP + ":8080/test/studentInsert.jsp?";  // ?(물음표) 주의

        Ecode = findViewById(R.id.insert_code);
        Ename = findViewById(R.id.insert_name);
        Edept = findViewById(R.id.insert_dept);
        Ephone = findViewById(R.id.insert_phone);

        // 입력시 자릿수 제한 (onCreate 안에 넣어줘야 한다.)
        Ecode.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4)});
        Ename.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10)});
        Edept.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12)});
        Ephone.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12)});

        btnInsert = findViewById(R.id.insert_btn);
        btnInsert.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 가져옴
            scode = Ecode.getText().toString();
            sname = Ename.getText().toString();
            sdept = Edept.getText().toString();
            sphone = Ephone.getText().toString();

            // get 방식
            urlAddr = urlAddr + "code=" + scode + "&name="+ sname+ "&dept="+ sdept+ "&phone="+ sphone;
            connectInsertData();
            Toast.makeText(InsertActivity.this, scode+" 가 입력 되었습니다.", Toast.LENGTH_SHORT).show();

        }
    };

    private void connectInsertData(){
        try {
            CUDNetworkTask insnetworkTask = new CUDNetworkTask(InsertActivity.this, urlAddr);
            insnetworkTask.execute().get();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}