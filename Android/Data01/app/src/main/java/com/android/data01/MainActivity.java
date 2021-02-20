package com.android.data01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "Status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //parser();
        parser2();
    }

    private void parser(){
        Log.v(TAG, "parser()");

        InputStream inputStream = getResources().openRawResource(R.raw.jsonex); //위치 알려쥼
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);   //읽는다
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  //담는다

        StringBuffer stringBuffer = new StringBuffer(); //그릇을 만듦
        String line = null;

        try {
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);  // 한줄씩 담는다
            }
            Log.v(TAG, "String Buffer :" +stringBuffer.toString());

            JSONObject jsonObject = new JSONObject(stringBuffer.toString());    //json 의 형태로 바꾸라는 의미
            String name = jsonObject.getString("name");
            Log.v(TAG, "name : " + name);
            int age = jsonObject.getInt("age");
            Log.v(TAG, "age :" + age);


            JSONArray jsonArray = jsonObject.getJSONArray("hobbies");
            for (int i=0; i<jsonArray.length(); i++){
                String hobby = jsonArray.getString(i);
                Log.v(TAG, "hobbies[" + i + "] :" + hobby);
            }

            //json 파일 보면 {} 는 새로운 object로 불러와야한다고 이해할것
            JSONObject jsonObject1 = jsonObject.getJSONObject("info");
            int no = jsonObject1.getInt("no");
            Log.v(TAG, "no :" + no);
            String id = jsonObject1.getString("id");
            Log.v(TAG, "id :" + id);
            String pw = jsonObject1.getString("pw");
            Log.v(TAG, "pw : " + pw);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) inputStream.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (bufferedReader != null ) bufferedReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    //---------------------------------------------------

    private void parser2(){
        Log.v(TAG, "parser2()");

        InputStream inputStream = getResources().openRawResource(R.raw.jsonex2);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        try {
            //data 가져오기
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            Log.v(TAG, stringBuffer.toString());

            // json 파일에 []의 표시는 배열의 표시이다.
            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            JSONArray jsonArray = new JSONArray(jsonObject.getString("members_info"));
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String name = jsonObject1.getString("name");
                Log.v(TAG, "name :" + name);

                int age = jsonObject1.getInt("age");
                Log.v(TAG, "age :" +age);

                JSONArray jsonArray1 = jsonObject1.getJSONArray("hobbies");
                for (int j=0; j<jsonArray1.length(); j++){
                    String hobby = jsonArray1.getString(j);
                    Log.v(TAG, "hobbies[" + j + "] : " + hobby);
                }


                //json 파일 보면 {} 는 새로운 object로 불러와야한다고 이해할것
                JSONObject jsonObject2 = jsonObject1.getJSONObject("info");
                int no = jsonObject2.getInt("no");
                Log.v(TAG, "no :" + no);

                String id = jsonObject2.getString("id");
                Log.v(TAG, "id :" + id);

                String pw = jsonObject2.getString("pw");
                Log.v(TAG, "pw :" + pw);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (inputStream != null) inputStream.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (bufferedReader != null) bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





}