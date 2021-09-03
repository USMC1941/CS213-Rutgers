package com.example.sesh.runbbusroutes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Routes extends Activity {

    public static final String ROUTE_NAME_KEY = "route_name";
    public static final String ROUTE_DETAIL_KEY = "route_detail";
    private String[] routeNames;
    private String[] routeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_list);

        ListView listView = findViewById(R.id.routes_list);
        routeNames = getResources().getStringArray(R.array.routes_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.route, routeNames);
        listView.setAdapter(adapter);

        InputStream is = getResources().openRawResource(R.raw.routes);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        routeDetails = new String[routeNames.length];
        try {
            for (int i = 0; i < routeNames.length; i++) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (!line.startsWith("*")) {
                    sb.append(line).append("\n");
                    line = br.readLine();
                }
                routeDetails[i] = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener((parent, view, position, id) -> showRoute(position));
    }

    private void showRoute(int pos) {
        Bundle bundle = new Bundle();
        bundle.putString(ROUTE_NAME_KEY, routeNames[pos]);
        bundle.putString(ROUTE_DETAIL_KEY, routeDetails[pos]);
        Intent intent = new Intent(this, ShowRoute.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}