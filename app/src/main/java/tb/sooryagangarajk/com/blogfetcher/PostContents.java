package tb.sooryagangarajk.com.blogfetcher;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class PostContents extends AppCompatActivity {
    public static TextView tView;
    public static TextView contView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_contents);
        Intent intent=getIntent();
        String url = intent.getExtras().getString("url");
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

    }
}
