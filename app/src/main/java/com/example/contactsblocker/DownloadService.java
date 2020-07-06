package com.example.contactsblocker;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DownloadService extends IntentService {

    private File downloadFile = null;

    public  boolean isDownloaded = false;

    public static boolean isIntentServiceRunning = false;

    private ResultReceiver rec;

    private int id;


    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        isIntentServiceRunning = true;


        String URL = intent.getStringExtra("url");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String fileDownloadPath = intent.getStringExtra("path") ;
        downloadFile = new File(fileDownloadPath);

        if (TextUtils.isEmpty(URL) || TextUtils.isEmpty(fileDownloadPath)) {
            return;
        }

        InputStream is = null;
        FileOutputStream os = null;

        try {
            URL downloadURL = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) downloadURL.openConnection();
            int responseCode = conn.getResponseCode();

            if (responseCode != 200)
                throw new ConnectException("Error in connection");
            is = conn.getInputStream();
            os = new FileOutputStream(downloadFile);

            byte[] buffer = new byte[10 * 1024];
            int byteCount;


            while ((byteCount = is.read(buffer)) != -1) {
                os.write(buffer, 0, byteCount);
            }

            isDownloaded = true;
            loadSoFile(receiver);
        } catch (ConnectException e) {
            if(downloadFile!=null)
                downloadFile.delete();

            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeConnections(is,os);
        }
    }

    private void loadSoFile(ResultReceiver receiver) {
        Bundle resultData = new Bundle();
        resultData.putBoolean("downloaded" ,true);
        receiver.send(1, resultData );
    }

    private void closeConnections(InputStream is , OutputStream os){
        try {
            if(os != null)
                os.close();
            if(is != null)
                is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!isDownloaded) {
            if(downloadFile !=null)
                downloadFile.delete();
        }
        isDownloaded = false;
        rec = null;
    }

}


