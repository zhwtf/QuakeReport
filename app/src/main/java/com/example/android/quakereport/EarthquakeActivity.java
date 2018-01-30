/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;


public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    private static final int EARTHQUAKE_LOADER_ID = 1;


    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        // Get the list of earthquakes from QueryUtils
       // ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();



        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes the list of earthquakes as input
        //final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);
        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
//
//        // Create a new {@link ArrayAdapter} of earthquakes
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
         earthquakeListView.setAdapter(mAdapter);

        // Set an item click listener on the listview, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object to pass into the Intent constructor
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websitIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websitIntent);

            }
        });





        // Start the AsyncTask to fetch the earthquake data
        //EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        //task.execute(USGS_REQUEST_URL);


        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);


    }


    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the givin URL
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }


    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data
        mAdapter.clear();
    }



//    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {
//        @Override
//        protected List<Earthquake> doInBackground(String... urls) {
//            if (urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//
//            List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
//            return result;
//
//        }
//
//        @Override
//        protected void onPostExecute(List<Earthquake> data) {
//            mAdapter.clear();
//
//            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//            // data set. This will trigger the ListView to update.
//            if (data != null && !data.isEmpty()) {
//                mAdapter.addAll(data);
//            }
//
//        }
//    }




}
