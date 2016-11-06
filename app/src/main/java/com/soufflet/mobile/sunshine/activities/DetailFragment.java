package com.soufflet.mobile.sunshine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soufflet.mobile.sunshine.R;
import com.soufflet.mobile.sunshine.SettingsActivity;

import static android.content.Intent.EXTRA_TEXT;
import static android.support.v4.view.MenuItemCompat.getActionProvider;

public class DetailFragment extends Fragment {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    private ShareActionProvider shareActionProvider;
    private String forecastData;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra(EXTRA_TEXT)) {
            forecastData = intent.getStringExtra(EXTRA_TEXT);
            TextView textView = (TextView) rootView.findViewById(R.id.forecast_details);
            textView.setText(forecastData);

            setShareIntent();
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        shareActionProvider = (ShareActionProvider) getActionProvider(item);
        setShareIntent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent() {
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(createShareForecastIntent(forecastData));
        }
    }

    private Intent createShareForecastIntent(String data) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");

        shareIntent.putExtra(Intent.EXTRA_TEXT, data + FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }
}
