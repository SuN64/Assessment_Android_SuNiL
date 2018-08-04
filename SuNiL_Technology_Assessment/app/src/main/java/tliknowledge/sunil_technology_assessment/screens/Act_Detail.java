package tliknowledge.sunil_technology_assessment.screens;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tliknowledge.sunil_technology_assessment.R;
import tliknowledge.sunil_technology_assessment.custom.RotateLoading;
import tliknowledge.sunil_technology_assessment.utils.Utils;

public class Act_Detail extends AppCompatActivity {
    private WebView web_detail;
    private String articleURL = "";
    private RotateLoading rotateLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail);

        init();

        findXmlIds();


        setWebView();

    }

    private void init() {
        articleURL = getIntent().getExtras().getString(Utils.key_articleURL);
    }

    private void findXmlIds() {
        web_detail = (WebView) findViewById(R.id.web_detail);
        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);
    }

    private void setWebView() {
        rotateLoading.start();
        WebSettings webSettings = web_detail.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClientImpl webViewClient = new WebViewClientImpl();
        web_detail.setWebViewClient(webViewClient);
        web_detail.loadUrl(articleURL);
    }


    class WebViewClientImpl extends WebViewClient {


        public WebViewClientImpl() {
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if(rotateLoading.isStart())
            {
                rotateLoading.stop();
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            if(rotateLoading.isStart())
            {
                rotateLoading.stop();
            }
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            if(rotateLoading.isStart())
            {
                rotateLoading.stop();
            }
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {

            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(rotateLoading.isStart())
            {
                rotateLoading.stop();
            }
            super.onPageFinished(view, url);
        }
    }

}
