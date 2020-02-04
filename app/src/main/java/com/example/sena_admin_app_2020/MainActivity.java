package com.example.sena_admin_app_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    private  String webUrl = "https://securityagencyssa.web.app/adminjob.html";
//    private  String webUrl = "https://eventforevery.firebaseapp.com/";

    ProgressDialog progressDialog;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    Button btnNoInternetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Please Wait...");

        btnNoInternetConnection = (Button)findViewById(R.id.btnN0Connection);

        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        webView = (WebView)findViewById(R.id.myWebView);

        webView.getSettings().setJavaScriptEnabled(true);
        checkConnection();

//        webView.loadUrl(webUrl);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return  true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                setTitle("Loading...");
                progressDialog.show();

                if (newProgress == 100){

                    progressBar.setVisibility(View.GONE);
                    setTitle(view.getTitle());
                    progressDialog.dismiss();
                }
                super.onProgressChanged(view, newProgress);
            }
        });


        btnNoInternetConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });
    }

    private void checkConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobilenetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected()) {
            webView.loadUrl(webUrl);
            webView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        } else if (mobilenetwork.isConnected()) {
            webView.loadUrl(webUrl);
            webView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        } else {
            webView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }
}