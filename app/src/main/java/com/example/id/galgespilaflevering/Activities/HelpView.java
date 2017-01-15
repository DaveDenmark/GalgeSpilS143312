package com.example.id.galgespilaflevering.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.id.galgespilaflevering.R;

public class HelpView extends Activity {
    TextView textView;
    WebView MywebView, w1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_view);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Wikipedia kan være din hjælp. Forslag til strategi står nederst på siden");

        MywebView = (WebView) findViewById(R.id.webviewer);
        MywebView.loadUrl("https://da.wikipedia.org/wiki/Galgespil");

        w1 = (WebView) findViewById(R.id.webviewer);
        w1.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView w1, String url) {
                w1.loadUrl(url);
                return false;
            }
        });


    }



}
