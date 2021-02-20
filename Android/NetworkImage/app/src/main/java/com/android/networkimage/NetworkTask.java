package com.android.networkimage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// background 의 intager/ pre string   / postExcute(끝날때)ㅇㅡㅣ integer
public class NetworkTask extends AsyncTask<Integer, String, Integer> {

    final static String TAG = "NetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    String devicePath; //디바이스의 위치를 나타냄
    ImageView imageView;

    public NetworkTask(Context context, String mAddr, ImageView imageView) {
        this.context = context;
        this.mAddr = mAddr;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialogue");
        progressDialog.setMessage("down ....");
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Log.v(TAG, "onProgressUpdate");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        Log.v(TAG, "onPostExecute()");
        Bitmap bitmap = BitmapFactory.decodeFile(devicePath);
        imageView.setImageBitmap(bitmap);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG, "onCanceled()");
        super.onCancelled();
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground()");

        int index = mAddr.lastIndexOf("/"); // 마지막 / 뒤의 내용을 불러온다는 의미
        String imgName = mAddr.substring(index + 1);
        Log.v(TAG, "urlAddress :" + mAddr);
        Log.v(TAG, "index : " + index);
        Log.v(TAG, "image name : "+ imgName);

        devicePath = Environment.getDataDirectory().getAbsolutePath() + "/data/com.android.networkimage/files/" + imgName;
        Log.v(TAG, "device path :" + devicePath);

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);   // 숫자의 의미는 10초의 의미이다.

            int len = httpURLConnection.getContentLength();
            byte[] bs = new byte[len];

            //http로 연결이 잘 되었다는 의미
            if (httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                fileOutputStream = context.openFileOutput(imgName, 0);
                //input 과 output 의 연결이 끝남..

                //
                while (true){
                    int i = inputStream.read(bs);
                    if (i < 0) break;
                    fileOutputStream.write(bs, 0,i);    // 처음부터 i 까지 는 데이터이다.
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) inputStream.close();
                if (fileOutputStream != null) fileOutputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
