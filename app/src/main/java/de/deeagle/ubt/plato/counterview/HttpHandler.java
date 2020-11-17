package de.deeagle.ubt.plato.counterview;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();

    private InputStream getInputStream(String getUrl) {
        try {
            URL url = new URL(getUrl);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            return connection.getInputStream();
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedExecption: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOExecption: " + e.getMessage());
        }

        return null;
    }

    public String doServiceCall(String url) {
        String response = null;

        InputStream is = getInputStream(url);
        if (null != is) {
            InputStream iss = new BufferedInputStream(is);
            response = convertStreamToString(iss);
        }

        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
