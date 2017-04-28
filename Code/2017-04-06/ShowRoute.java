package com.example.sesh.runbbusroutes;

import android.app.Activity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by seshv on 4/6/17.
 */

public class ShowRoute extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_detail);

        // get the name and detail from bundle
        Bundle bundle = getIntent().getExtras();
        String routeName = bundle.getString(Routes.ROUTE_NAME_KEY);
        String routeDetail = bundle.getString(Routes.ROUTE_DETAIL_KEY);

        // get the name and detail view objects
        TextView routeNameView = (TextView)findViewById(R.id.route_name);
        TextView routeDetailView = (TextView)findViewById(R.id.route_detail);

        // set name and detail on the views
        routeNameView.setText(routeName);
        routeDetailView.setText(routeDetail);
    }

}
