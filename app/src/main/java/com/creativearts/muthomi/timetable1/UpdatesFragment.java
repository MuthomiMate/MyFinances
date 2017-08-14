package com.creativearts.muthomi.timetable1;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by MUTHOMI on 1/6/2016.
 */
public class UpdatesFragment extends Fragment {
    SQLiteHandler db;
    ProgressDialog pDialog;
    TextView txt1;
    View v;
    private static final String TAG = About.class.getSimpleName();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_layout,null);
    }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Button download=(Button)view.findViewById(R.id.button35);
            v=view.findViewById(R.id.muthomi);
            Button show=(Button)view.findViewById(R.id.button36);
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSeatNumber();
                }
            });
            show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view();
                }
            });






//        txt1  =(TextView)view.findViewById(R.id.textView);
//        final Button show=(Button)view.findViewById(R.id.btnShowcase);
//        db=new SQLiteHandler(getActivity().getApplicationContext());
//        pDialog = new ProgressDialog(UpdatesFragment.this.getActivity());
//        pDialog.setCancelable(false);
//
//        final String token= String.valueOf(db.getToken());
//        show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //isConnected();
//               //if( isConnected()==true){
//                getDeposits(token);
//                //txt1.setText(token);
//                show.setVisibility(view.GONE);
//           //    }
//            }
//        });


    }
    public void download() {
        db = new SQLiteHandler(getActivity().getApplicationContext());
        String token=db.getToken();
        String link= "http://www.muthomimate.mazingiraproject.com/Mmu_pfa/"+token+"withdraw"+".pdf";
        Log.d("lin",link);

            new com.tranetech.openspace.pdfdownloaddemo.MainActivity2.DownloadFile().execute(link, "withdraw.pdf");
            Log.d("Download complete", "----------");
            Snackbar.make(v, "Download complete", Snackbar.LENGTH_LONG).show();


    }

    public void view() {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/mmupfa/" + "withdraw.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
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
       clientm.post("withdrawpdf.php", params, new JsonHttpResponseHandler() {
           @Override
           public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
               Snackbar.make(v,responseString, Snackbar.LENGTH_LONG).show();
               Log.d("Failed: ", ""+statusCode);
               Log.d("Error : ", "" + throwable);

               Log.d("sly", responseString);
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
               download();




           }
       });






       //return number;

   }



}
