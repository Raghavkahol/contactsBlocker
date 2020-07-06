package com.example.contactsblocker;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import com.facebook.soloader.DirectorySoSource;
import com.facebook.soloader.SoLoader;
import com.facebook.soloader.SoSource;

import java.io.File;
import java.io.IOException;

class DownloadReceiver extends ResultReceiver {

    public DownloadReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        super.onReceiveResult(resultCode, resultData);
        if (resultCode == 1) {
            boolean downloadStatus = resultData.getBoolean("downloaded");
            if(downloadStatus) {
                try {
                    File soLibDIR = new File(Environment.getExternalStorageDirectory().getPath() + "/games24x7" +".cpp");
                    DirectorySoSource soSource = new DirectorySoSource(soLibDIR,
                            SoSource.LOAD_FLAG_ALLOW_IMPLICIT_PROVISION);
                    SoLoader.prependSoSource(soSource);
                    SoLoader.loadLibrary("so_cpp");
                } catch (UnsatisfiedLinkError e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}