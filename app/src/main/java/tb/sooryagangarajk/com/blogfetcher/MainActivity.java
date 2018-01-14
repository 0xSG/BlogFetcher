package tb.sooryagangarajk.com.blogfetcher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public static ListView listView;
    public static CustomArrayAdapter customArrayAdapter;
    public static List<DataFish> data;
    public static String pageUrl = "https://www.googleapis.com/blogger/v3/blogs/8565561961995222193/posts?key=AIzaSyAtIK6gZmB_kGyb54aGLKcVbJlMAXlPeAg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listid);


        final SwipeRefreshLayout mySwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        new AsyncLogin().execute();
                        mySwipeRefreshLayout.setRefreshing(false);

                    }
                }
        );





        new AsyncLogin().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataFish obj = data.get(position);


                Intent i = new Intent(MainActivity.this, PostContents.class);
                i.putExtra("url", obj.postUrl);
                i.putExtra("content", obj.dcontent);
                i.putExtra("title", obj.dtitle);
                startActivity(i);
            }
        });

    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {


                    //// S G K ////
                    String pageCont="";
            URL url = null;
            try {
                url = new URL(pageUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String str;
            try {
                while ((str = in.readLine()) != null)
                {
                   pageCont+=str;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return pageCont;


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            data = new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONObject tmp = new JSONObject(result);
                JSONArray js = tmp.getJSONArray("items");

                for (int i = 0; i < js.length(); i++) {
                    JSONObject json_data = js.getJSONObject(i);
                    DataFish fishData = new DataFish();
                    fishData.dtitle = json_data.getString("title");
                    fishData.dcontent = json_data.getString("content");
                    fishData.postUrl = json_data.getString("url");
                    data.add(fishData);
                }

                customArrayAdapter = new CustomArrayAdapter(getApplicationContext(), data);
                listView.setAdapter(customArrayAdapter);


            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }

        }

    }
}
