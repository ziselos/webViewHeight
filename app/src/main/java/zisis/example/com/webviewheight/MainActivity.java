package zisis.example.com.webviewheight;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    AppCompatTextView title;
    AppCompatTextView plainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupWebView();
    }

    private void initViews() {
      webView = findViewById(R.id.articleWebView);
      title = findViewById(R.id.titleTextView);
      plainTextView = findViewById(R.id.articlePlainTextView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:WebViewHeight.resize(document.body.getBoundingClientRect().height)");
                super.onPageFinished(view, url);
            }
        });
        webView.addJavascriptInterface(this, "WebViewHeight");
        String folderPath = "file:android_asset/";

        String fileName = "article.html";

        String file = folderPath + fileName;

        webView.loadUrl(file);
    }

    @JavascriptInterface
    public void resize(final float height) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.setLayoutParams(new RelativeLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }

}
