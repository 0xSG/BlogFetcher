package tb.sooryagangarajk.com.blogfetcher;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

public class PostContents extends AppCompatActivity {
    public static TextView tView;
    public static TextView contView;
    public static String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_post_contents);
        Intent intent=getIntent();
        url = intent.getExtras().getString("url");
        String content = intent.getExtras().getString("content");
        String title = intent.getExtras().getString("title");
        tView=(TextView)findViewById(R.id.tid);
        contView=(TextView)findViewById(R.id.contid);
        tView.setText(title);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            contView.setText(Html.fromHtml(content));
        } else {
            contView.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));

        }
        contView.setMovementMethod(new ScrollingMovementMethod());

        tView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {/*
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser)*/;
                Intent i =new Intent((PostContents.this),web_activity.class);
                i.putExtra("url",url);
                startActivity(i);
            }
        });

    }
}
