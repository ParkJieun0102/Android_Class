package com.android.database;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.database.R;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "Status";
    Button insertBtn, updateBtn, deleteBtn, selectBtn;
    TextView tvResult;
    MemberInfo memberInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memberInfo = new MemberInfo(MainActivity.this);

        findViewById(R.id.insertBtn).setOnClickListener(onClickListener);
        findViewById(R.id.updateBtn).setOnClickListener(onClickListener);
        findViewById(R.id.deleteBtn).setOnClickListener(onClickListener);
        findViewById(R.id.selectBtn).setOnClickListener(onClickListener);
        tvResult = findViewById(R.id.tv_result);
    }
    // -------------------------------------------------------------------------
    // 이 아래론 method
    View.OnClickListener onClickListener = new View.OnClickListener() {

        SQLiteDatabase DB;

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                // 입력
                case R.id.insertBtn:
                    try {
                        DB = memberInfo.getWritableDatabase(); // 입력이라 writable
                        String query = "INSERT INTO member (username, userid, passwd) VALUES ('유비', 'yb', 1111);";
                        DB.execSQL(query);

                        memberInfo.close(); // memberInfo 종료
                        Toast.makeText(MainActivity.this, "Insert OK!", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        e.printStackTrace();
                        Log.v(TAG, String.valueOf(e));
                        Toast.makeText(MainActivity.this, "Insert Error!", Toast.LENGTH_SHORT).show();
                    }
                    break;

                // 수정
                case R.id.updateBtn:
                    try{
                        DB = memberInfo.getWritableDatabase();
                        String query = "UPDATE member SET username = '관우' WHERE userid = 'yb';";
                        DB.execSQL(query);

                        memberInfo.close();
                        Toast.makeText(MainActivity.this, "Update OK!", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Update Error!", Toast.LENGTH_SHORT).show();
                    }
                    break;

                // 검색
                case R.id.selectBtn:
                    try {
                        DB = memberInfo.getReadableDatabase();
                        String query = "SELECT username, userid, passwd FROM member;";
                        Cursor cursor = DB.rawQuery(query, null);

                        StringBuffer stringBuffer = new StringBuffer();

                        while (cursor.moveToNext()){
                            String username = cursor.getString(0);
                            String userid = cursor.getString(1);
                            int passwd = cursor.getInt(2);

                            // strinbuffer에 담기
                            stringBuffer.append("username : " + username + ", userid : " + userid + ", passwd : " + passwd + "\n");
                        }

                        tvResult.setText(stringBuffer.toString());
                        cursor.close();
                        memberInfo.close();
                        Toast.makeText(MainActivity.this, "Select OK!", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Select Error!", Toast.LENGTH_SHORT).show();
                    }
                    break;

                    //삭제
                case R.id.deleteBtn:
                    try{
                        DB = memberInfo.getWritableDatabase();
                        String query = "DELETE From member;";
                        DB.execSQL(query);

                        memberInfo.close();
                        Toast.makeText(MainActivity.this, "Update OK!", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Update Error!", Toast.LENGTH_SHORT).show();
                    }
                    break;


            }
        }
    };


} //--