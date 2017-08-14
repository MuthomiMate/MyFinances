package com.tranetech.openspace.pdfdownloaddemo.MainActivity2;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Maryanne Mwai on 6/13/2017.
 */

public class DownloadFile extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
        String fileName = strings[1];  // -> maven.pdf
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "mmupfa");
        folder.mkdir();

        File pdfFile = new File(folder, fileName);

        try{
            pdfFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        com.tranetech.openspace.pdfdownloaddemo.FileDownloader.downloadFile(fileUrl, pdfFile);
        return null;
    }
}