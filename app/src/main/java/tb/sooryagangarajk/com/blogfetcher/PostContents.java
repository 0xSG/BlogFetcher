package tb.sooryagangarajk.com.blogfetcher;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.util.Locale;

public class PostContents extends AppCompatActivity {
    public static TextView tView;
    public static TextView contView;
    public static String url;
    public static String title;
    public static String content;
    public static TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_post_contents);
        Intent intent=getIntent();
        Toast.makeText(getApplicationContext(),"Tap title to speak",Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"Hold title to open post",Toast.LENGTH_LONG).show();
        url = intent.getExtras().getString("url");
        content = intent.getExtras().getString("content");
        title = intent.getExtras().getString("title");
        tView=(TextView)findViewById(R.id.tid);
        contView=(TextView)findViewById(R.id.contid);
        tView.setText(title);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            contView.setText(Html.fromHtml(content));
        } else {
            contView.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));

        }
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        contView.setMovementMethod(new ScrollingMovementMethod());
        tView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent((PostContents.this),web_activity.class);
                i.putExtra("url",url);
                startActivity(i);

                return true;
            }
        });
        tView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textToSpeech.isSpeaking()){
                    Toast.makeText(getApplicationContext(),"Off",Toast.LENGTH_LONG).show();
                    textToSpeech.stop();

                }else{
                    Toast.makeText(getApplicationContext(),"Speaking",Toast.LENGTH_LONG).show();
                    textToSpeech.speak(title, TextToSpeech.QUEUE_FLUSH, null);
                    //delay 500ms
                    try {
                        while(textToSpeech.isSpeaking())
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // say contents
                    textToSpeech.speak(html2text(content), TextToSpeech.QUEUE_FLUSH, null);

                }
                ///SPEEK CODE HERE _SGK_

                // say titile



            }
        });

    }
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}
