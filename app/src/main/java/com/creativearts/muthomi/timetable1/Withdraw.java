package com.creativearts.muthomi.timetable1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by MUTHOMI on 3/10/2016.
 */
public class Withdraw extends Fragment {

    private static final String TAG = About.class.getSimpleName();
    private Button Deposit;
    private EditText amounts;
    private Spinner accounts,status;
    private EditText desc;
    private ProgressDialog pDialog;
    SQLiteHandler db;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.withdraw_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v=view.findViewById(R.id.wthdrawform);
        getSeatNumber();
        accounts = (Spinner) view.findViewById(R.id.accountm);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Withdraw.this.getActivity(), R.array.days_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        accounts.setAdapter(adapter);
        status=(Spinner)view.findViewById(R.id.spinnerstatus);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(Withdraw.this.getActivity(), R.array.satus, android.R.layout.simple_spinner_item);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapters);
        amounts = (EditText) view.findViewById(R.id.amountw);
        Deposit = (Button) view.findViewById(R.id.btnWithD);
        desc=(EditText)view.findViewById(R.id.desc);
        db=new SQLiteHandler(getActivity().getBaseContext());
        pDialog = new ProgressDialog(Withdraw.this.getActivity());
        pDialog.setCancelable(false);
        Deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount=amounts.getText().toString().trim();
                String account=(String) accounts.getAdapter().getItem(accounts.getSelectedItemPosition());
                String statu=(String) status.getAdapter().getItem(status.getSelectedItemPosition());
                String description=desc.getText().toString();
                String token=db.getToken();

                if (!amount.isEmpty()&&!description.isEmpty()){
                    withdraw(amount,account,description,token,statu);
                    //Toast.makeText(Withdraw.this.getActivity(),"Successfully withdrawn",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Withdraw.this.getActivity(),"Please fill all the details",Toast.LENGTH_SHORT).show();
                }
    }
});
    }
    private void WithdrawCash(final String amount, final String account,
                              final String description, final String token,final String status) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Withdrawing ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_WITHDRAW, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                hideDialog();



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("amount", amount);
                params.put("account", account);
                params.put("description", description);
                params.put("token",token);
                params.put("option",status);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    public void getSeatNumber(){
        //showProgress(true);
        final AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Client clientm = new Client(getActivity());

        // RequestParams params = new RequestParams();
        //String nametw="galleria";
        db = new SQLiteHandler(getActivity().getApplicationContext());
        String token=db.getToken();
        params.put("token", token);
        clientm.post("showAccounts.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Snackbar.make(v,responseString, Snackbar.LENGTH_LONG).show();
                Log.d("sly", responseString);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                //showProgress(false);
            }



            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Snackbar.make(v, "No data connectionm", Snackbar.LENGTH_LONG).show();
                Log.d("sly", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Snackbar.make(v, "No data connectiond", Snackbar.LENGTH_LONG).show();
                Log.d("sly", errorResponse.toString());
            }



            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d("Daisy", response.toString(4));
                    JSONArray jsonArray=response.getJSONArray("accounts");
                    // spinner = (Spinner)this.findViewById(R.id.dynamicSpinner);
                    //String accounts[]=response.getJSONArray("accounts");
                    //String accounts[];
                    ArrayList<String> scripts = new ArrayList<String>();

                    for (int i=0;i<jsonArray.length();i++){
                        String account= jsonArray.getString(i);


                        Log.d("Daisy",account);
                        scripts.add(account);
                        // adapter.notifyDataSetChanged ();

                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),   android.R.layout.simple_spinner_item, scripts);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    //spinner.setAdapter(spinnerArrayAdapter);
                    accounts.setAdapter(spinnerArrayAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //super.onSuccess(statusCode, headers, response);




            }
        });






        //return number;

    }
    public void withdraw( String amount, String account,
                         String description, String token,String status){
        final AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Client clientm = new Client(getActivity());

        // RequestParams params = new RequestParams();
        //String nametw="galleria";
        db = new SQLiteHandler(getActivity().getApplicationContext());
        //String token=db.getToken();
        params.put("amount", amount);
        params.put("account", account);
        params.put("description", description);
        params.put("token",token);
        params.put("option",status);
        clientm.post("withd.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Snackbar.make(v,responseString, Snackbar.LENGTH_LONG).show();
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
                Log.d("sly", responseString);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                //showProgress(false);
            }



            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Snackbar.make(v, "No data connectionm", Snackbar.LENGTH_LONG).show();
                Log.d("sly", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Snackbar.make(v, "No data connectiond", Snackbar.LENGTH_LONG).show();
                Log.d("sly", errorResponse.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Intent i=new Intent(getActivity(),MainActivity.class);
                startActivity(i);
//                try {
//                    Log.d("Daisy", response.toString(4));
//                    JSONArray jsonArray=response.getJSONArray("accounts");
//                    // spinner = (Spinner)this.findViewById(R.id.dynamicSpinner);
//                    //String accounts[]=response.getJSONArray("accounts");
//                    //String accounts[];
//                    ArrayList<String> scripts = new ArrayList<String>();
//
//                    for (int i=0;i<jsonArray.length();i++){
//                        String account= jsonArray.getString(i);
//
//
//                        Log.d("Daisy",account);
//                        scripts.add(account);
//                        // adapter.notifyDataSetChanged ();
//
//                    }
//                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),   android.R.layout.simple_spinner_item, scripts);
//                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
//                    //spinner.setAdapter(spinnerArrayAdapter);
//                    accounts.setAdapter(spinnerArrayAdapter);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                //super.onSuccess(statusCode, headers, response);




            }
        });

    }
}

