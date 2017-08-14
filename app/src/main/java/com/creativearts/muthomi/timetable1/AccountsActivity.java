package com.creativearts.muthomi.timetable1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AccountsActivity extends AppCompatActivity {
    ListView mw;
    private AccountsAdapter listAdapter;
    View v;
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        v=findViewById(R.id.activity_accountsfu);

        mw= (ListView) findViewById(R.id.listn);

        // Create ArrayAdapter using the planet list.
        listAdapter = new AccountsAdapter(getBaseContext());
        final String item_id = getIntent().getStringExtra("item_id");
        Client client = new Client(AccountsActivity.this);
        RequestParams params = new RequestParams();
        db = new SQLiteHandler(AccountsActivity.this);
        SharedPreferences sharedPreferences=getSharedPreferences("IDValue",0);
        String account_name=sharedPreferences.getString("tokenkey","");
        String token=db.getToken();
        Intent intent=getIntent();
        String w=intent.getStringExtra("account_name");
        params.put("token", token);
        params.put("account",w);
        Log.d("mutugi",w);
        Log.d("mutugi",token);
        //final View v=findViewById(R.id.login_formw);

        client.post("depositAccount.php", params, new JsonHttpResponseHandler() {


            @Override
            public void onFinish() {
                super.onFinish();
                // showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //Snackbar.make(v, "mmmm", Snackbar.LENGTH_LONG).show();
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
                Log.d("mutugi",responseString);
                if (responseString.contentEquals("null")){
                    Snackbar.make(v, "No deposits in that account", Snackbar.LENGTH_LONG).show();
                }
                else{
                    Snackbar.make(v, responseString, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Snackbar.make(v, "No data connectionm", Snackbar.LENGTH_LONG).show();
                Log.d("mutugi",errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Snackbar.make(v, "No data connectiond", Snackbar.LENGTH_LONG).show();
                Log.d("mutugi",errorResponse.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    Log.d("mutugi", response.toString(4));


                            for (int x = 0; x < response.length(); x++) {
                                MainClass review = new MainClass();
                                try {
                                    review.setDate_of_deposit(response.getJSONObject(x).getString("deposit_date"));
                                    review.setAmount_deposited(response.getJSONObject(x).getString("amount_deposited"));
                                    review.setOption_deposit(response.getJSONObject(x).getString("option"));
                                    listAdapter.add(review);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

////                        review.setTotal(response.getJSONObject(x).getString("amount"));
////                        review.setPbefore(response.getJSONObject(x).getString("prepaidbefore"));
////                        review.setPafter(response.getJSONObject(x).getString("prepaidafter"));
////                        review.setReg(response.getJSONObject(x).getString("reg"));



                                //String mm="muthomi";

                            }







                    //return number;
                    mw.setAdapter(listAdapter);







                    //return number;
                  //  mw.setAdapter(listAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //super.onSuccess(statusCode, headers, response);

            }
        });


        //AsyncHttpClient client = new AsyncHttpClient();
        //RequestParams params = new RequestParams();
       // final AsyncHttpClient client = new AsyncHttpClient();


    }
//        client.get("http://192.168.43.222/memo_jim/memos.php", null, new JsonHttpResponseHandler() {
//        });

        //Set the ArrayAdapter as the ListView's adapter.






}
