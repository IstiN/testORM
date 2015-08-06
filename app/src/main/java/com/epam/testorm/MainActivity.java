package com.epam.testorm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    public static final String URL = "https://dl.dropboxusercontent.com/u/52289508/ORM/testOrm.json";

    private ICacheManager cacheManager;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cacheManager = CacheFactory.getManager(this);
        listView = (ListView) findViewById(R.id.list);
        final ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(options);

        ImageLoader.getInstance().init(builder.build());

    }

    private static final DisplayImageOptions options = new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder()
            .showImageForEmptyUri(android.R.color.transparent)
            .showImageOnLoading(android.R.color.transparent)
            .showImageOnFail(android.R.color.transparent)
            .resetViewBeforeLoading(true)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .displayer(new SimpleBitmapDisplayer()) // default
            .build();




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_load) {
            loadData();
            return true;
        }
        if (id == R.id.action_all) {
            fetchAll();
            return true;
        }
        if (id == R.id.action_filtered) {
            fetchFiltered();
            return true;
        }
        if (id == R.id.action_images) {
            fetchImages();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchFiltered() {
        final long now = System.currentTimeMillis();
        ListAdapter fullAdapter = cacheManager.getFilteredAdapter();
        final long time = System.currentTimeMillis() - now;
        log("Read filtered " + time + "ms, count = " + (fullAdapter == null ? 0 : fullAdapter.getCount()));
        listView.setAdapter(fullAdapter);
    }

    private void fetchImages() {
        final long now = System.currentTimeMillis();
        ListAdapter fullAdapter = cacheManager.getImagesOnlyAdapter();
        final long time = System.currentTimeMillis() - now;
        log("Read images " + time + "ms, count = " + (fullAdapter == null ? 0 : fullAdapter.getCount()));
        listView.setAdapter(fullAdapter);
    }

    private void fetchAll() {
        final long now = System.currentTimeMillis();
        ListAdapter fullAdapter = cacheManager.getFullAdapter();
        final long time = System.currentTimeMillis() - now;
        log("Read all " + time + "ms, count = " + (fullAdapter == null ? 0 : fullAdapter.getCount()));
        listView.setAdapter(fullAdapter);
    }

    ProgressDialog dialog;

    private void loadData() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL(URL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    String result = InputStreamUtils.readString(inputStream);
                    final long now = System.currentTimeMillis();
                    processFeed(result);
                    final long time = System.currentTimeMillis() - now;
                    inputStream.close();
                    urlConnection.disconnect();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            log("Parsed and stored " + time);
                            if (dialog != null && dialog.isShowing()) {
                                dialog.hide();
                            }
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            log("Load error " + e.getMessage());
                            if (dialog != null && dialog.isShowing()) {
                                dialog.hide();
                            }
                        }
                    });
                }
            }
        }).start();

    }

    private void processFeed(String result) {
        cacheManager.processFeed(result);
    }

    private void log(String error) {
        TextView view = (TextView) findViewById(R.id.log);
        view.setText(error + " (" + BuildConfig.ORM + ")" );
    }
}
