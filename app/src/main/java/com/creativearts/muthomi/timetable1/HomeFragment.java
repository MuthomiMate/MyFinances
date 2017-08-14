package com.creativearts.muthomi.timetable1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;


public class HomeFragment extends Fragment {
    public final static String TAG="ccl";
    static FragmentManager mFragmentManager;
    View rootView;
    SQLiteHandler db;
String bank,cash;
    Button btn1,btn2;
        // Required empty public constructor





    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn1=(Button)view.findViewById(R.id.buttonbb);
        btn2=(Button)view.findViewById(R.id.buttonbw);
        db=new SQLiteHandler(getActivity().getApplicationContext());
        final String token= String.valueOf(db.getToken());
        getStatus(token);
        final android.os.Handler handler=new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn1.setText("Cash:  "+cash);
                btn2.setText("Bank:  "+bank);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        FragmentTransaction t=getFragmentManager().beginTransaction();
//                        Fragment mfrag=new Withdraw();
//                        t.replace(R.id.containerView,mfrag);
//                        t.commit();
//                        //getFragmentManager().beginTransaction().replace(R.id.containerView,new Withdraw());
//             FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
//           xfragmentTransaction.replace(R.id.containerView, new Withdraw()).commit();
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        FragmentTransaction t=getFragmentManager().beginTransaction();
//                        Fragment mfrag=new Withdraw();
//                        t.replace(R.id.containerView,mfrag);
//                        t.commit();
//           FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
//            xfragmentTransaction.replace(R.id.containerView, new Withdraw()).commit();
                    }
                });
            }
        },1500);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }


    private void getStatus(final String token) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";



        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_STATUS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    bank=jObj.getString("bank");
                    cash= jObj.getString("cash");
                   // boolean error = jObj.getBoolean("error");

                    // Check for error node in json

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }





}
