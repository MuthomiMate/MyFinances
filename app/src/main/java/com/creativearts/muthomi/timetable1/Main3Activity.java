package com.creativearts.muthomi.timetable1;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }


    public void download(View v) {
        new com.tranetech.openspace.pdfdownloaddemo.MainActivity2.DownloadFile().execute("http://www.muthomimate.mazingiraproject.com/Mmu_pfa/w9.pdf", "w9.pdf");
        Log.d("Download complete", "----------");
    }

    public void view(View v) {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/mmupfa/" + "w9.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Main3Activity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }
}









