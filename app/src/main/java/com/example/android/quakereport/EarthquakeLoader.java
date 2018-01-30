package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.media.audiofx.Equalizer;

import java.util.List;

/**
 * Created by zhenghao on 2018-01-29.
 */




public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);

        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    // This is on a background Thread
    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquake
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);

        return earthquakes;
    }



}
