package com.epam.testorm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.epam.testorm.realm.ChannelHelper;
import com.epam.testorm.realm.RealmCache;
import com.epam.testorm.realm.RealmChannel;
import com.epam.testorm.realm.RealmVideoStream;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

import static com.epam.testorm.realm.ChannelHelper.getLogoUrl;


public class MainActivity extends ActionBarActivity {

    public static final String URL = "https://dl.dropboxusercontent.com/u/52289508/ORM/channels.json";

    private RealmCache realm;
    private ListView listView;
    private RealmBaseAdapter<RealmChannel> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = new RealmCache(this);
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

    private void initAdapter(RealmResults<RealmChannel> channels) {
        if (mAdapter == null) {
            mAdapter = new RealmBaseAdapter<RealmChannel>(this, channels, false) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = inflater.inflate(R.layout.item,
                                parent, false);
                    }
                    RealmChannel channel = realmResults.get(position);
                    updateView(convertView, channel);
                    return convertView;
                }
            };
            listView.setAdapter(mAdapter);
        } else {
            mAdapter.updateRealmResults(channels);
        }
    }

    private void updateView(View convertView, RealmChannel channel) {
        ImageView logo = (ImageView) convertView.findViewById(R.id.logo);
        ImageLoader.getInstance().displayImage(getLogoUrl(channel), logo);
        setText(convertView, R.id.number, String.valueOf(channel.getChannelNumber()));
        setText(convertView, R.id.desc, String.valueOf(channel.getStationSchedules().getDescription()));
        setText(convertView, R.id.title, String.valueOf(channel.getStationSchedules().getTitle()));
        setText(convertView, R.id.hd, String.valueOf(channel.getStationSchedules().isHd() ? "HD": null));
        RealmVideoStream stream = ChannelHelper.getStream(channel);
        setText(convertView, R.id.videoUrl, String.valueOf(stream == null ? null : stream.getUrl()));
        setText(convertView, R.id.videoKey, String.valueOf(stream == null ? null : stream.getProtectionKey()));
        setText(convertView, R.id.entitlements, String.valueOf(ChannelHelper.getEntitlements(channel)));

    }

    private void setText(View view, int id, String value) {
        TextView number = (TextView) view.findViewById(id);
        if (!TextUtils.isEmpty(value)) {
            number.setText(value);
            number.setVisibility(View.VISIBLE);
        } else {
            number.setVisibility(View.GONE);
        }
    }

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
        if (id == R.id.action_vip) {
            return true;
        }
        if (id == R.id.action_visible) {
            return true;
        }
        if (id == R.id.action_hd) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void fetchAll() {
        final long now = System.currentTimeMillis();
        RealmResults<RealmChannel> allChannels = realm.getAllChannels();
        final long time = System.currentTimeMillis() - now;
        log("Read all" + time);
        initAdapter(allChannels);
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
        realm.process(result);
    }

    private void log(String error) {
        TextView view = (TextView) findViewById(R.id.log);
        view.setText(error);
    }
}
