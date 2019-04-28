package drysister.itcast.cn.photohome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class ActivityBanner extends AppCompatActivity {
private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        WebView webView=(WebView)findViewById(R.id.bannerView);
        imgBack=(ImageView)findViewById(R.id.bannerback);
        Intent it2=getIntent();
        webView.getSettings().setJavaScriptEnabled(true);//启用js
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.loadUrl(it2.getStringExtra("bannerUrl"));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}
