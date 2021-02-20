package com.android.dbcrud01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    final static String TAG = "DeleteActivity";
    String urlAddr = null;
    TextView Dcode, Dname, Ddept, Dphone;
    String scode, macIP;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        // intent 를 받아온다.
        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        urlAddr = "http://" + macIP + ":8080/test/studentDelete.jsp?";  // ?(물음표) 주의

        Dcode = findViewById(R.id.delete_code);
        Dname = findViewById(R.id.delete_name);
        Ddept = findViewById(R.id.delete_dept);
        Dphone = findViewById(R.id.delete_phone);

        btnDelete = findViewById(R.id.delete_Btn);


        Dcode.setText("학번 : "+intent.getStringExtra("code"));
        Dname.setText("이름 : "+intent.getStringExtra("name"));
        Ddept.setText("전공 : "+intent.getStringExtra("dept"));
        Dphone.setText("전화번호 : "+intent.getStringExtra("phone"));

        scode = intent.getStringExtra("code");
        btnDelete.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //scode = Dcode.getText().toString();


            // get 방식
            urlAddr = urlAddr + "code=" + scode;
            connectDeleteData();
            Toast.makeText(DeleteActivity.this, urlAddr+" 가 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
        }
    };

    private void connectDeleteData(){
        try {
            CUDNetworkTask deleteworkTask = new CUDNetworkTask(DeleteActivity.this, urlAddr);
            deleteworkTask.execute().get();

        }catch (Exception e){
            e.printStackTrace();
        }

        finish();
    }

}