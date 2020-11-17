package de.deeagle.ubt.plato.counterview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ArrayList<LocationItem> locations;
    private RecyclerView recyclerView;
    private LocationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locations = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new LocationAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public void bntUpdateStatsClicked(View view) {
        GetCounters g = new GetCounters();
        g.execute();
    }


    private class GetCounters extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String url = "https://ub-plato.uni-trier.de/api/1/counters";

            String jsonResponse = sh.doServiceCall(url);

            Log.d(TAG, "Got response url: " + url);

            if (jsonResponse != null) {
                try {
                    locations.clear();
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    JSONArray counters = jsonObject.getJSONArray("data");
                    for (int index = 0; index < counters.length(); index++) {
                        JSONObject counter = counters.getJSONObject(index);

                        int id = counter.getInt("id");
                        String name = counter.getString("name");
                        String location = counter.getString("location");
                        int currentPercent = counter.getInt("currentPercent");
                        String openState = counter.getString("openState");
                        String maintenanceMode = counter.getString("maintenanceMode");
                        int openTime = counter.getInt("openTime");
                        int closeTime = counter.getInt("closeTime");

                        LocationItem locationItem = new LocationItem(id, name, location, openState, currentPercent, openTime, closeTime);
                        Log.d(TAG, "doInBackground: " + locationItem.toString());
                        locations.add(locationItem);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            // update list
            Log.d(TAG, "onPostExecute: size of locations is " + locations.size());
            adapter.setLocations(locations);
        }
    }
}