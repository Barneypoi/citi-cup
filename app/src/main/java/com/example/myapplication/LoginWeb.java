package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

//用于显示花旗登录界面的WebView界面
public class LoginWeb extends AppCompatActivity {
    private WebView webView;
//    private String client_id = "3906dd6d-534b-4d20-81d7-0e78848013a3";
//    private String client_secret = "";
//    private String redirect_uri = "https://sandbox.developerhub.citi.com/api-authorize";
        private String client_id = "5bc9344e-8478-4a5f-a16d-b473328b114d";
    private String client_secret = "eU2eS4nA4cK0wV1tQ4wJ7nW2pB5mJ8wU5pB6hF6jQ2eH0cN3fO";
    private String redirect_uri = "http://47.100.120.235:8081/feedback";
//    "http://47.100.120.235:8081/runo"
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        WebView webView = new WebView(this);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        webView.loadUrl("https://sandbox.apihub.citi.com/gcb/api/authCode/oauth2/authorize?response_type=code&client_id=872eaa1f-85ce-4ef7-b216-6f001893bfac&scope=accounts_details_transactions customers_profiles&countryCode=US&businessCode=GCB&locale=en_US&state=12093&redirect_uri=https://sandbox.developerhub.citi.com/playground/default.html\n");
//webView

        setContentView(R.layout.loginweb);
        webView = (WebView) findViewById(R.id.loginWeb);
        webView.getSettings().setJavaScriptEnabled(true);
        final Map<String, String> header = new HashMap<>();
        header.put("accept", "application/json");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                view.loadUrl(url, header);
                //正确登录
                if (url.startsWith("http://47.100.120.235:8081/index?uuid=")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    String userid = url.substring(url.indexOf("uuid=")+5);
//                    UserInformation.shared.setUserId(userid);
                    UserInformation.shared.setUserId("4cdfcb8a-a3b9-11e9-b20e-f42cb030f26f");
                    intent.putExtra("userid", userid);
                    startActivity(intent);
                }else if (url.startsWith("http://47.100.120.235:8081/feedback?error=")) {
                    UserInformation.shared.setUserId("4cdfcb8a-a3b9-11e9-b20e-f42cb030f26f");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });
        webView.loadUrl("https://sandbox.apihub.citi.com/gcb/api/authCode/oauth2/authorize?response_type=code" +
                "&client_id=" + client_id +
                "&scope=pay_with_points accounts_details_transactions customers_profiles " +
                "payees personal_domestic_transfers internal_domestic_transfers external_domestic_transfers bill_payments " +
                "cards onboarding reference_data meta_data insurance_purchase" +
                "&countryCode=HK" +
                "&businessCode=GCB" +
                "&locale=en_US" +
                "&state=c18d6005-8ae0-4ca0-3e27-35f780bd0d21" +
                "&redirect_uri=" + redirect_uri, header);
    }
}