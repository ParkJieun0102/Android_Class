package com.android.networkjson02;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

// Object 는 제일 큰 틀..?
public class NetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "NetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<JsonStudent> students;

    public NetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.students = new ArrayList<JsonStudent>();
    }

    //다이어로그 를 띄우기 위한
    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialogue");
        progressDialog.setMessage("down...");
        progressDialog.show();
    }

    //
    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground()");

        InputStream inputStream = null;     //
        InputStreamReader inputStreamReader = null; //
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer(); //json 파일을 담아놓는 공간 StringBuffer

        try {
            URL url = new URL(mAddr);   //위에 mAddr 선언한 이유 (url을 받기 위함 )
            Log.v(TAG, "Address :" + mAddr);

            //
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000); //10초 ...
            Log.v(TAG, "Accept :" + httpURLConnection.getResponseCode());

            //만약 연결이 되었다면
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true){
                    String strline = bufferedReader.readLine();
                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");
                }
                Log.v(TAG, "StringBuffer :" + stringBuffer);

                parser(stringBuffer.toString());    //parser 라는 메소드에 stringBuffer 을 string으로 바꾼값을 준다는 의미

            }

        }catch (Exception e){
            Log.v(TAG, "Error");
            e.printStackTrace();
        }
        // finally 일때 살아있다면 죽인다는 의미 (정리한다)
        finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStream != null) inputStream.close();
                if (inputStreamReader != null) inputStreamReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return students;    //return 값을 students 를 주는 이유는
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Log.v(TAG, "onProgressUpdate()");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG, "onPostExecute");
        super.onPostExecute(o);
        progressDialog.dismiss();   //dismiss 는 종료의 의미
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG,"onCancelled()");
        super.onCancelled();
    }

    // 위에서 parser(stringBuffer.toString());  로 선언 해줬기 때문에 String
    //parser 의 메소드
    private void parser(String s){
        Log.v(TAG, "parser()");

        try {
            JSONObject jsonObject = new JSONObject(s);  //(s) 인 이유는 위에 parser(String s)를 줘서
            JSONArray jsonArray = new JSONArray(jsonObject.getString("students_info")); //students_info(테이블의 이름?
            students.clear();

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String code = jsonObject1.getString("code");
                String name = jsonObject1.getString("name");
                String dept = jsonObject1.getString("dept");
                String phone = jsonObject1.getString("phone");

                JsonStudent student = new JsonStudent(code, name, dept, phone);
                students.add(student);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
