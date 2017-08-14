package com.creativearts.muthomi.timetable1;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by MUTHOMI on 1/6/2016.
 */
public class SentFragment extends Fragment {
    SQLiteHandler db;
    View v;
    LinearLayout lm;
    public static String MyPreferences="myPrefs";
    public static String keychannel="tokenkey";
    SharedPreferences sharedPreferences;
    String account_name;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sent_layout, null);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v=view.findViewById(R.id.accountsform);
        lm= (LinearLayout) view.findViewById(R.id.lma);
        getSeatNumber();

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
                    Log.d("awesome", response.toString(4));
                    JSONArray jsonArray=response.getJSONArray("accounts");
                    final Button mu=new Button(getActivity());
                    mu.setText("Add account");
                    mu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent nnn=new Intent(getActivity(),Addaccount.class);
                            startActivity(nnn);

                        }
                    });
                    AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, AppBarLayout.LayoutParams.WRAP_CONTENT);
                    lm.addView(mu, lp);



                    for (int i=0;i<jsonArray.length();i++){
                        String account= jsonArray.getString(i);
                        final Button am=new Button(getActivity());
                        am.setText(account);
                        am.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                createDialog();
                                account_name=am.getText().toString();
                            }
                        });
                        //AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, AppBarLayout.LayoutParams.WRAP_CONTENT);
                        lm.addView(am, lp);


                        Log.d("awesome",account);

                        // adapter.notifyDataSetChanged ();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //super.onSuccess(statusCode, headers, response);




            }
        });






        //return number;

    }
    public void createDialog(){


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog ad = builder.show();
        builder.setTitle("CONFIRM");
        builder.setMessage("Select the status you want to see");
         //1 is for vertical orientation



        builder.setPositiveButton("Withdrawals", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                //Toast.makeText(MapsActivity.this,"the Space is full",Toast.LENGTH_SHORT).show();
                // getSeatNumber();

                sharedPreferences=getActivity().getSharedPreferences("IDValue",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(keychannel,account_name);
                Intent intentn=new Intent(getActivity(),DepositActivty.class);
                intentn.putExtra("account_name",account_name);
                startActivity(intentn);



            }
        });
        builder.setNeutralButton("Deposits", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dialog.dismiss();
                Intent intentn=new Intent(getActivity(),AccountsActivity.class);
                intentn.putExtra("account_name",account_name);
                startActivity(intentn);
                sharedPreferences=getActivity().getSharedPreferences("IDValue",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(keychannel,account_name);
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }



}
