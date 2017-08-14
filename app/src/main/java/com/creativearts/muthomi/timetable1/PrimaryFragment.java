package com.creativearts.muthomi.timetable1;

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
import android.widget.TableLayout;
import android.widget.TextView;

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
public class  PrimaryFragment extends Fragment {

    TextView savingdeposit,feesdeposit,clothingdeposit,transportdeposit,religiousdeposit,miscellenaeousdeposit,shoppingdeposit,stationarydepo,breakfastdep,lunchdep,dinnerdep;
    TextView savingwith,feeswith,clothingwith,transportwith,religiouswith,miscellenaeouswith,shoppingwith,stationarywith,breakfastwith,lunchwith,dinnerwith;
    View rootView;
    TextView savings, fees, clothing, transport,religious,misc,shopping,stationery,breakfast,lunch,dinner;
    SQLiteHandler db;
    View v;
    Button reveal;
    TableLayout table;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView     = inflater.inflate(R.layout.primary_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        table=(TableLayout)view.findViewById(R.id.table_accounts);
        table.setVisibility(View.GONE);
        v=view.findViewById(R.id.linearstandings);
        savingdeposit=(TextView)view.findViewById(R.id.txt3);
        savingwith=(TextView)view.findViewById(R.id.txt5);
        feesdeposit=(TextView)view.findViewById(R.id.txt10);
        feeswith=(TextView)view.findViewById(R.id.txt12);
        clothingdeposit=(TextView)view.findViewById(R.id.txt17);
        clothingwith=(TextView)view.findViewById(R.id.txt19);
        transportdeposit=(TextView)view.findViewById(R.id.txt24);
        transportwith=(TextView)view.findViewById(R.id.txt26);
        religiousdeposit=(TextView)view.findViewById(R.id.txt31);
        religiouswith=(TextView)view.findViewById(R.id.txt33);
        miscellenaeousdeposit=(TextView)view.findViewById(R.id.txt38);
        miscellenaeouswith=(TextView)view.findViewById(R.id.txt40);
        shoppingdeposit=(TextView)view.findViewById(R.id.txt46);
        shoppingwith=(TextView)view.findViewById(R.id.txt48);
        stationarydepo=(TextView)view.findViewById(R.id.txt53);
        stationarywith=(TextView)view.findViewById(R.id.txt55);
        breakfastdep=(TextView)view.findViewById(R.id.txt62);
        breakfastwith=(TextView)view.findViewById(R.id.txt64);
        lunchdep=(TextView)view.findViewById(R.id.txt69);
        lunchwith=(TextView)view.findViewById(R.id.txt71);
        dinnerdep=(TextView)view.findViewById(R.id.txt76);
        dinnerwith=(TextView)view.findViewById(R.id.txt78);

        savings=(TextView)view.findViewById(R.id.txt7);
        fees=(TextView)view.findViewById(R.id.txt14);
        clothing=(TextView)view.findViewById(R.id.txt21);
        transport=(TextView)view.findViewById(R.id.txt28);
        religious=(TextView)view.findViewById(R.id.txt35);
        misc=(TextView)view.findViewById(R.id.txt42);
        shopping=(TextView)view.findViewById(R.id.txt50);
        stationery=(TextView)view.findViewById(R.id.txt57);
        breakfast=(TextView)view.findViewById(R.id.txt66);
        lunch=(TextView)view.findViewById(R.id.txt73);
        dinner=(TextView)view.findViewById(R.id.txt80);


        reveal=(Button)view.findViewById(R.id.btnReveal);
        reveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSeatNumber();
            }
        });


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
        clientm.post("ShowStandings.php", params, new JsonHttpResponseHandler() {
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
                    JSONArray jsonArray=response.getJSONArray("Deposits");
//                    "Stationery and Printing": 0,
//                            "Shopping and Personal Effect": 0,
//                            "Fees": 11500,
//                            "Savings": 15000,
//                            "Dinner": 0,
//                            "Transport": 0,
//                            "Breakfast": 0,
//                            "Lunch": 0,
//                            "Religious": 0,
//                            "Clothing": 0,
//                            "miscelleaneous": 0

                    for (int i=0;i<jsonArray.length();i++){
                        savingdeposit.setText(jsonArray.getJSONObject(i).getString("Savings"));
                        stationarydepo.setText(jsonArray.getJSONObject(i).getString("Stationery and Printing"));
                        shoppingdeposit.setText(jsonArray.getJSONObject(i).getString("Shopping and Personal Effect"));
                        feesdeposit.setText(jsonArray.getJSONObject(i).getString("Fees"));
                        dinnerdep.setText(jsonArray.getJSONObject(i).getString("Dinner"));
                        transportdeposit.setText(jsonArray.getJSONObject(i).getString("Transport"));
                        breakfastdep.setText(jsonArray.getJSONObject(i).getString("Breakfast"));
                        lunchdep.setText(jsonArray.getJSONObject(i).getString("Lunch"));
                        religiousdeposit.setText(jsonArray.getJSONObject(i).getString("Religious"));
                        clothingdeposit.setText(jsonArray.getJSONObject(i).getString("Clothing"));
                        miscellenaeousdeposit.setText(jsonArray.getJSONObject(i).getString("miscelleaneous"));
//                        String account= jsonArray.getString(i);
//                        table.setVisibility(View.VISIBLE);
//                        reveal.setVisibility(View.GONE);
                        getWith();
//
//
//                        Log.d("Daisy",account);
                       // scripts.add(account);
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
    public void getWith(){
        //showProgress(true);
        final AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Client clientm = new Client(getActivity());

        // RequestParams params = new RequestParams();
        //String nametw="galleria";
        db = new SQLiteHandler(getActivity().getApplicationContext());
        String token=db.getToken();
        params.put("token", token);
        clientm.post("showstandingsw.php", params, new JsonHttpResponseHandler() {
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
                    //JSONArray jsonArray=response.getJSONArray("Deposits");
                    JSONArray jsonArray=response.getJSONArray("withdrwals");

                    // spinner = (Spinner)this.findViewById(R.id.dynamicSpinner);
                    //String accounts[]=response.getJSONArray("accounts");
                    //String accounts[];
                    // ArrayList<String> scripts = new ArrayList<String>();

                    for (int i=0;i<jsonArray.length();i++){
                        savingwith.setText(jsonArray.getJSONObject(i).getString("Savings"));
                        stationarywith.setText(jsonArray.getJSONObject(i).getString("Stationery and Printing"));
                        shoppingwith.setText(jsonArray.getJSONObject(i).getString("Shopping and Personal Effect"));
                        feeswith.setText(jsonArray.getJSONObject(i).getString("Fees"));
                        dinnerwith.setText(jsonArray.getJSONObject(i).getString("Dinner"));
                        transportwith.setText(jsonArray.getJSONObject(i).getString("Transport"));
                        breakfastwith.setText(jsonArray.getJSONObject(i).getString("Breakfast"));
                        lunchwith.setText(jsonArray.getJSONObject(i).getString("Lunch"));
                        religiouswith.setText(jsonArray.getJSONObject(i).getString("Religious"));
                        clothingwith.setText(jsonArray.getJSONObject(i).getString("Clothing"));
                        miscellenaeouswith.setText(jsonArray.getJSONObject(i).getString("miscelleaneous"));
                        int sav,fee,clo,tra,rel,mis,shop,stat,bre,lu,din;
                        sav=Integer.parseInt(String.valueOf(Integer.parseInt(savingdeposit.getText().toString())-Integer.parseInt(savingwith.getText().toString())));
                        fee=Integer.parseInt(String.valueOf(Integer.parseInt(feesdeposit.getText().toString())-Integer.parseInt(feeswith.getText().toString())));
                       clo=Integer.parseInt(String.valueOf(Integer.parseInt(clothingdeposit.getText().toString())-Integer.parseInt(clothingwith.getText().toString())));
                        tra=Integer.parseInt(String.valueOf(Integer.parseInt(transportdeposit.getText().toString())-Integer.parseInt(transportwith.getText().toString())));
                        rel=Integer.parseInt(String.valueOf(Integer.parseInt(religiousdeposit.getText().toString())-Integer.parseInt(religiouswith.getText().toString())));
                        mis=Integer.parseInt(String.valueOf(Integer.parseInt(miscellenaeousdeposit.getText().toString())-Integer.parseInt(miscellenaeouswith.getText().toString())));
                        shop=Integer.parseInt(String.valueOf(Integer.parseInt(shoppingdeposit.getText().toString())-Integer.parseInt(shoppingwith.getText().toString())));
                        stat=Integer.parseInt(String.valueOf(Integer.parseInt(stationarydepo.getText().toString())-Integer.parseInt(stationarywith.getText().toString())));
                        bre=Integer.parseInt(String.valueOf(Integer.parseInt(breakfastdep.getText().toString())-Integer.parseInt(breakfastwith.getText().toString())));
                        lu=Integer.parseInt(String.valueOf(Integer.parseInt(lunchdep.getText().toString())-Integer.parseInt(lunchwith.getText().toString())));
                        din=Integer.parseInt(String.valueOf(Integer.parseInt(dinnerdep.getText().toString())-Integer.parseInt(dinnerwith.getText().toString())));
//                        String account= jsonArray.getString(i);
                        savings.setText(String.valueOf(sav));
                        fees.setText(String.valueOf(fee));
                        clothing.setText(String.valueOf(clo));
                        transport.setText(String.valueOf(tra));
                        religious.setText(String.valueOf(rel));
                        misc.setText(String.valueOf(mis));
                        shopping.setText(String.valueOf(shop));
                        stationery.setText(String.valueOf(stat));
                        breakfast.setText(String.valueOf(bre));
                        lunch.setText(String.valueOf(lu));
                        dinner.setText(String.valueOf(din));

                        table.setVisibility(View.VISIBLE);
                        reveal.setVisibility(View.GONE);

//
//
//                        Log.d("Daisy",account);
                        // scripts.add(account);
                        // adapter.notifyDataSetChanged ();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //super.onSuccess(statusCode, headers, response);




            }
        });
    }
}





