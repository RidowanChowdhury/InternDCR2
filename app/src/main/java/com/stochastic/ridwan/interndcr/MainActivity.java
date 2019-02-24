package com.stochastic.ridwan.interndcr;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String url = "https://raw.githubusercontent.com/appinion-dev/intern-dcr-data/master/data.json";
    private String TAG = MainActivity.class.getSimpleName();
    List<String> list1 = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();
    List<String> list3 = new ArrayList<String>();
    List<String> list4 = new ArrayList<String>();
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);

        list1.add("Choose");
        list2.add("Choose");
        list3.add("Choose");
        list4.add("Choose");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter3);
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list4);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter4);

        b1=(Button)findViewById(R.id.btn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Done",Toast.LENGTH_LONG).show();
            }
        });

        new getData().execute();
    }

    private class getData extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray product_group_list = jsonObj.getJSONArray("product_group_list");
                    JSONArray literature_list = jsonObj.getJSONArray("literature_list");
                    JSONArray physician_sample_list = jsonObj.getJSONArray("physician_sample_list");
                    JSONArray gift_list = jsonObj.getJSONArray("gift_list");

                    // looping through All Contacts
                    for (int i = 0; i < product_group_list.length(); i++) {
                        JSONObject c = product_group_list.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("product_group");
                        list1.add(name);
                        }
                    for (int i = 0; i < literature_list.length(); i++) {
                        JSONObject c = literature_list.getJSONObject(i);

                        String id = c.getString("id");
                        String literature = c.getString("literature");
                        list2.add(literature);
                    }
                    for (int i = 0; i < physician_sample_list.length(); i++) {
                        JSONObject c = physician_sample_list.getJSONObject(i);

                        String id = c.getString("id");
                        String sample = c.getString("sample");
                        list3.add(sample);
                    }
                    for (int i = 0; i < gift_list.length(); i++) {
                        JSONObject c = gift_list.getJSONObject(i);

                        String id = c.getString("id");
                        String gift = c.getString("gift");
                        list4.add(gift);
                    }

                } catch (final JSONException e) {
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



    }
}
