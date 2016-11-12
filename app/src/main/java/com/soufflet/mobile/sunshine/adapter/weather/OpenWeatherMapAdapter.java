package com.soufflet.mobile.sunshine.adapter.weather;

import android.net.Uri;
import android.util.Log;

import com.google.common.collect.ImmutableList;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.soufflet.mobile.sunshine.adapter.weather.WeatherDataParser.getWeatherDataFromJson;

public class OpenWeatherMapAdapter {

    private static final String LOG_TAG = OpenWeatherMapAdapter.class.getSimpleName();

    /**
     * @see https://home.openweathermap.org/api_keys
     */
    private static final String API_KEY = "key";
    
    private static final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private static final String QUERY_PARAM = "q";
    private static final String FORMAT_PARAM = "mode";
    private static final String UNITS_PARAM = "units";
    private static final String DAYS_PARAM = "cnt";
    private static final String APPID_PARAM = "APPID";

    public enum UnitType {
        METRIC,
        IMPERIAL
    }

    public ImmutableList<String> getWeatherForecast(String zipcode, UnitType unitType, int numberOfDays) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast

            Uri apiUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, zipcode)
                    .appendQueryParameter(FORMAT_PARAM, "json")
                    .appendQueryParameter(UNITS_PARAM, "metric")
                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numberOfDays))
                    .appendQueryParameter(APPID_PARAM, API_KEY)
                    .build();

            URL apiUrl = new URL(apiUri.toString());

            Log.v(LOG_TAG, apiUrl.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) apiUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return ImmutableList.of();
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return ImmutableList.of();
            }

            return getWeatherDataFromJson(buffer.toString(), unitType);
        } catch (IOException e) {
            throw new RuntimeException("Unable to retrieve weather forecast", e);
        } catch (JSONException e) {
            throw new RuntimeException("Unable to parse weather forecast", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    throw new RuntimeException("Unable to retrieve weather forecast", e);
                }
            }
        }
    }
}
