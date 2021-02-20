package com.android.hybrid01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    WebView webView = null;
    Button btnPage1, btnPage2, btnPage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("💛즤니의 하이브리드 앱💛");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML과 연결
        webView = findViewById(R.id.webview);
        btnPage1 = findViewById(R.id.google);
        btnPage2 = findViewById(R.id.naver);
        btnPage3 = findViewById(R.id.cnn);

        //Listener
        addListener();


        //Web Setting
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //  Javascript 사용 가능
        webSettings.setBuiltInZoomControls(true);   // 확대 축소 가능
        webSettings.setDisplayZoomControls(false);  // 돋보기 없애기

        webView.setWebViewClient(new WebViewClient(){
            // page 처음 뜰때 (다운받으러 갈떄)
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                btnReload.setText("로딩 중 ...");
            }
            // page 다운로드 다 되었을때
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                btnReload.setText(webView.getTitle());
            }
        });

        webView.loadUrl("https://www.google.co.kr/");
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
    }

    //Listener 를 메소드로 분리
    private void addListener(){
        btnPage1.setOnClickListener(onClickListener);
        btnPage2.setOnClickListener(onClickListener);
        btnPage3.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.google:
                    btnPage1Click();
                    break;
                case R.id.naver:
                    btnPage2Click();
                    break;
                case R.id.cnn:
                    btnPage3Click();
                    break;
            }
        }
    };


    private  void btnPage1Click(){
        webView.loadUrl("https://www.google.co.kr/");
    }

    private  void btnPage2Click(){
        webView.loadUrl("http://www.naver.com/");
    }

    private void btnPage3Click(){
        webView.loadUrl("https://edition.cnn.com/");
    }



}