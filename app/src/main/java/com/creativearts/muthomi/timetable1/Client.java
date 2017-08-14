package com.creativearts.muthomi.timetable1;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by Eduh_mik on 6/18/2017.
 */

public class Client {
    private AsyncHttpClient client;
    private static Context mContext;


    public static String URL = "http://muthomimate.mazingiraproject.com/Mmu_pfa/";



    public Client(Context context) {


        if (client == null) {
            client = new AsyncHttpClient();
            //client.setSSLSocketFactory(ssl);
            mContext = context;
            init();
        }
    }

    private void init() {


    }

    public void setSync() {
        client = new SyncHttpClient();
        init();
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler handler) {

        if (params == null) {
            params = new RequestParams();
        }

        String api_token = "";
        api_token = PreferenceManager.getDefaultSharedPreferences(mContext).getString("api_token", null);
//TESTING FOR ERRORS
        Log.d("WebClient", "Binding api_key: " + api_token);

        params.add("token", api_token);

        Log.d("WebClient", "Binding app_id: " + api_token);

        Log.d("WebClient", "Sending HTTP GET " + URL + url);

        client.get(mContext, URL + url, params, handler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler handler) {

        if (params == null) {
            params = new RequestParams();
        }

        String api_token = "";
        api_token = PreferenceManager.getDefaultSharedPreferences(mContext).getString("api_token", null);

        Log.d("WebClient", "Binding api_key: " + api_token);

        params.add("token", api_token);


        Log.d("WebClient", "Sending HTTP POST " + URL + url);
        Log.d("Webclient", params.toString());

        client.post(mContext, URL + url, params, handler);
    }
}