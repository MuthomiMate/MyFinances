package com.creativearts.muthomi.timetable1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Addaccount extends AppCompatActivity {
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaccount);
        final Client client=new Client(Addaccount.this);
        final View v=findViewById(R.id.activity_addaccountmm);
        final EditText ac=(EditText)findViewById(R.id.Textaddaccount);
        Button submit=(Button)findViewById(R.id.buttonaccountadd);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ac.getText().toString().contentEquals("")){
                    Toast.makeText(Addaccount.this, "Enter the account name", Toast.LENGTH_SHORT).show();
                }else{
                    RequestParams params = new RequestParams();
                    db = new SQLiteHandler(Addaccount.this);

                    String token=db.getToken();
                    params.put("token", token);
                    params.put("account",ac.getText().toString());
                   // params.put("account",w);
                    client.post("Addaccount.php", params, new JsonHttpResponseHandler() {


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
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                Log.d("mutugi", response.toString(4));
                                Intent in=new Intent(Addaccount.this,MainActivity.class);
                                startActivity(in);








                                //return number;
                              //  mw.setAdapter(listAdapter);







                                //return number;
                                //  mw.setAdapter(listAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //super.onSuccess(statusCode, headers, response);

                        }
                    });

                }
            }
        });


    }
}
