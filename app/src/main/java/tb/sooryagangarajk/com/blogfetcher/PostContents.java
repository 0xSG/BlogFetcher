package tb.sooryagangarajk.com.blogfetcher;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.util.Locale;

public class PostContents extends AppCompatActivity {

    public static Context context;
    public static TextView tView;
    public static TextView contView;
    public static String url;
    public static String sgk;
    public static String title;
    public static String content;
    public static TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_post_contents);
        Intent intent = getIntent();//// S G K ///;
        url = intent.getExtras().getString("url");
        content = intent.getExtras().getString("content");
        title = intent.getExtras().getString("title");
        tView = (TextView) findViewById(R.id.tid);
        contView = (TextView) findViewById(R.id.contid);
        tView.setText(title);//// S G K ////
        context = getApplicationContext();

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            contView.setText(Html.fromHtml(content));
        } else {
            contView.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
        }

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        sgk = "//// ";
        contView.setMovementMethod(new ScrollingMovementMethod());

        tView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent((PostContents.this), web_activity.class);
                i.putExtra("url", url);
                startActivity(i);
                return true;
            }
        });

        tView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textToSpeech.isSpeaking()) {
                    textToSpeech.stop();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            textToSpeech.speak(title, TextToSpeech.QUEUE_FLUSH, null);
                            try {
                                sgk.concat("S G K ");
                                while (textToSpeech.isSpeaking())
                                    Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            textToSpeech.speak(html2text(content), TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }).start();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (textToSpeech.isSpeaking()) {
            sgk.concat("////");
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onBackPressed();
    }
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}
