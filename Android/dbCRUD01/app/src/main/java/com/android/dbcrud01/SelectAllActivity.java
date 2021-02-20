package com.android.dbcrud01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectAllActivity extends AppCompatActivity {

    final static String TAG = "SelectAllActivity";
    String urlAddr = null;
    ArrayList<Student> members;
    StudentAdapter adapter;
    ListView listView;
    String macIP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_all);

        listView = findViewById(R.id.lv_student);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");

        urlAddr = "http://" + macIP + ":8080/test/student_query_all.jsp";
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
        Log.v(TAG, "onResume()");

    }

    private void connectGetData(){
        try {
            NetworkTask networkTask = new NetworkTask(SelectAllActivity.this, urlAddr);
            Object obj = networkTask.execute().get();

            members = (ArrayList<Student>) obj; // obj 는 암호화가 된다

            adapter = new StudentAdapter(SelectAllActivity.this, R.layout.student_layout, members);
            listView.setAdapter(adapter);
            // 클릭 짧게
            listView.setOnItemClickListener(onItemClickListener);
            // 클릭 길게
            listView.setOnItemLongClickListener(onItemLongClickListener);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 리스트 클릭하 수정페이지
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
            intent.putExtra("code", members.get(position).getCode());
            intent.putExtra("name", members.get(position).getName());
            intent.putExtra("dept", members.get(position).getDept());
            intent.putExtra("phone", members.get(position).getPhone());


            intent.putExtra("macIP", macIP); // ip를 보내준다.
            startActivity(intent);


        }
    };

    // 리스트 길게 클릭하면 삭제페이
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(SelectAllActivity.this, DeleteActivity.class);
            intent.putExtra("code", members.get(position).getCode());
            intent.putExtra("name", members.get(position).getName());
            intent.putExtra("dept", members.get(position).getDept());
            intent.putExtra("phone", members.get(position).getPhone());

            intent.putExtra("macIP", macIP); // ip를 보내준다.
            startActivity(intent);

            Toast.makeText(SelectAllActivity.this, "LongClick", Toast.LENGTH_SHORT).show();

            return false;
        }
    };


} // ----