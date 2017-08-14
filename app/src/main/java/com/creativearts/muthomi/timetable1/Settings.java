package com.creativearts.muthomi.timetable1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MUTHOMI on 3/12/2016.
 */
public class Settings extends Fragment{
 ProgressDialog pDialog;
    SQLiteHandler db;
private static final String TAG = About.class.getSimpleName();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText saving,fee,usag,chur,miscellaneou,developmen;
        final Button submit;

        saving=(EditText)view.findViewById(R.id.savings);
        fee=(EditText)view.findViewById(R.id.fees);
        usag=(EditText)view.findViewById(R.id.development);
        chur=(EditText)view.findViewById(R.id.church);
        miscellaneou=(EditText)view.findViewById(R.id.misc);
        developmen=(EditText)view.findViewById(R.id.development);
        submit=(Button)view.findViewById(R.id.btnSubmit);
        db=new SQLiteHandler(getActivity().getApplicationContext());
        pDialog = new ProgressDialog(Settings.this.getActivity());
        pDialog.setCancelable(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String savings=(saving.getText().toString());
                String usage=(usag.getText().toString());
                String fees=(fee.getText().toString());
               String church=(chur.getText().toString());
                String miscellaneous=(miscellaneou.getText().toString());
                String development=(developmen.getText().toString());
                String token=db.getToken();

                if (!savings.isEmpty()&& !usage.isEmpty()&&!fees.isEmpty()&&!church.isEmpty()&&!miscellaneous.isEmpty()&&!development.isEmpty()){
                    float sav=Float.parseFloat(savings);
                    float usa=Float.parseFloat(usage);
                    float fe=Float.parseFloat(fees);
                    float chu=Float.parseFloat(church);
                    float mis=Float.parseFloat(miscellaneous);
                    float de=Float.parseFloat(development);
                    float sum= sav +usa+fe+chu+mis+de;
                    if (sum==100){
                        //Toast.makeText(Settings.this.getActivity(),"Settings updated successfully",Toast.LENGTH_SHORT).show();
                        WithdrawCash(savings,usage,fees,church,miscellaneous,development,token);

                    }
                    else{
                        Toast.makeText(Settings.this.getActivity(),"The sum should add up to 100",Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(Settings.this.getActivity(),"Please fill all the details",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void WithdrawCash(final String savings, final String fees,final String usage,final String church, final String development,
                              final String miscelleneous, final  String token) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Setting...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PERCENTAGES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Settings Response: " + response.toString());

                hideDialog();



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Settings Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("church", church);
                params.put("development", development);
                params.put("fees", fees);
                params.put("usage",usage);
                params.put("miscellaneous",miscelleneous);
                params.put("savings",savings);
                params.put("token",token);

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
}
