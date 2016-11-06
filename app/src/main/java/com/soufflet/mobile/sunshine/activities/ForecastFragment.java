package com.soufflet.mobile.sunshine.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.soufflet.mobile.sunshine.R;
import com.soufflet.mobile.sunshine.SunshineApp;
import com.soufflet.mobile.sunshine.adapter.weather.OpenWeatherMapAdapter;
import com.soufflet.mobile.sunshine.adapter.weather.OpenWeatherMapAdapter.UnitType;

import java.util.List;

import javax.inject.Inject;

import static android.content.Intent.EXTRA_TEXT;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.soufflet.mobile.sunshine.adapter.weather.OpenWeatherMapAdapter.UnitType.IMPERIAL;
import static com.soufflet.mobile.sunshine.adapter.weather.OpenWeatherMapAdapter.UnitType.METRIC;

public class ForecastFragment extends Fragment {

    private static final String LOG_TAG = ForecastFragment.class.getSimpleName();

    @Inject OpenWeatherMapAdapter openWeatherMapAdapter;

    private ArrayAdapter<String> forecastUiAdapter;

    public ForecastFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((SunshineApp) getActivity().getApplication()).getNetComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        forecastUiAdapter =
                new ArrayAdapter<String>(
                        getContext(),
                        R.layout.list_item_forecast,
                        R.id.list_item_forecast_textview);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastUiAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Intent intent =
                        new Intent(getActivity(), DetailActivity.class)
                                .putExtra(EXTRA_TEXT, forecastUiAdapter.getItem(index));

                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchAndDisplayForecastData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            fetchAndDisplayForecastData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchAndDisplayForecastData() {
        SharedPreferences prefs = getDefaultSharedPreferences(getActivity());
        String location =
                prefs.getString(
                        getString(R.string.pref_location_key),
                        getString(R.string.pref_location_default));

        String unitType =
                prefs.getString(
                        getString(R.string.pref_units_key),
                        getString(R.string.pref_units_metric));

        new GetForecastAsync().execute(location, unitType);
    }

    private class GetForecastAsync extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... strings) {
            final UnitType unitType;
            if (strings[1] == getString(R.string.pref_units_imperial)) {
                unitType = IMPERIAL;
            } else {
                unitType = METRIC;
            }

            return openWeatherMapAdapter.getWeatherForecast(strings[0], unitType, 7);
        }

        @Override
        protected void onPostExecute(List<String> result) {
            if (!result.isEmpty()) {
                forecastUiAdapter.clear();
                for (String forecastData : result) {
                    forecastUiAdapter.add(forecastData);
                }
            }
        }
    }

}
