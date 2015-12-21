package com.example.shana.androidlesson7_volley;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shana on 2015/12/21.
 */
public class MainActivity extends Activity {
    @Bind(R.id.activity_main_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.activity_main_layout)
    ViewGroup layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadData();
    }

    private void loadData() {
        StatusViewHolder.addLoadingView(this, layout);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonRequest<ArrayList<PersonalProfile>> jsonRequest = new JsonRequest<ArrayList<PersonalProfile>>(Request.Method.GET, Host.getHostName(), null,
                new Response.Listener<ArrayList<PersonalProfile>>() {
                    @Override
                    public void onResponse(ArrayList<PersonalProfile> response) {
                        StatusViewHolder.removeAllStatusView(layout);
                        recyclerView.setAdapter(new RecyclerViewAdapter(response, requestQueue));
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                StatusViewHolder.addRetryView(MainActivity.this, layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData();
                    }
                });
            }
        }) {
            @Override
            protected Response<ArrayList<PersonalProfile>> parseNetworkResponse(NetworkResponse response) {
                String result = new String(response.data, Charsets.UTF_8);
                ArrayList<PersonalProfile> list = new Gson().fromJson(result, new TypeToken<ArrayList<PersonalProfile>>() {
                }.getType());
                return Response.success(list, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        requestQueue.add(jsonRequest);
    }
}
