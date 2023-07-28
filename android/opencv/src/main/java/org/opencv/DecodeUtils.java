package org.opencv;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.wechat_qrcode.WeChatQRCode;
import org.opencv.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class DecodeUtils extends Handler {

    private static final String TAG = DecodeUtils.class.getSimpleName();
    private WeChatQRCode mWeChatQRCode;
    private final Context mContext;
    private File mDetectorPrototxtFile = null;
    private File mDetectorCaffeModelFile = null;
    private File mSuperResolutionPrototxtFile = null;
    private File mSuperResolutionCaffeModelFile = null;

    DecodeUtils(Context context) {
        this.mContext = context;
        initModelFile();
    }

    /**
     * 解码
     */
    public String decode(Bitmap bitmap) {
        String result = "";
        try {
            Mat mat = new Mat();
            Utils.bitmapToMat(bitmap, mat);
            List<String> results = mWeChatQRCode.detectAndDecode(mat);
            if (results != null && results.size() > 0) {
                Log.i(TAG, results.toString());
                result = results.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private void initModelFile() {
        try {
            // detect.prototxt
            InputStream detectorIs = mContext.getResources().openRawResource(R.raw.detect_prototxt);
            File qrcodeDir = mContext.getDir("qrcode", Context.MODE_PRIVATE);
            mDetectorPrototxtFile = new File(qrcodeDir, "detect.prototxt");
            if(!mDetectorPrototxtFile.exists()){
                FileOutputStream os = new FileOutputStream(mDetectorPrototxtFile);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while (-1 != (bytesRead = detectorIs.read(buffer))) {
                    os.write(buffer, 0, bytesRead);
                }
                detectorIs.close();
                os.close();
            }

            // detect.caffemodel
            InputStream detectorCaffeIs = mContext.getResources().openRawResource(R.raw.detect_caffemodel);
            mDetectorCaffeModelFile = new File(qrcodeDir, "detect.caffemodel");
            if(!mDetectorCaffeModelFile.exists()){
                FileOutputStream ios = new FileOutputStream(mDetectorCaffeModelFile);
                byte[] ibuffer = new byte[4096];
                int ibytesRead;
                while ((ibytesRead = detectorCaffeIs.read(ibuffer)) != -1) {
                    ios.write(ibuffer, 0, ibytesRead);
                }
                detectorCaffeIs.close();
                ios.close();
            }

            // sr.prototxt
            InputStream srPrototxtIs = mContext.getResources().openRawResource(R.raw.sr_prototxt);
            mSuperResolutionPrototxtFile = new File(qrcodeDir, "sr.prototxt");
            if(!mSuperResolutionPrototxtFile.exists()){
                FileOutputStream jos = new FileOutputStream(mSuperResolutionPrototxtFile);
                byte[] jbuffer = new byte[4096];
                int jbytesRead;
                while ((jbytesRead = srPrototxtIs.read(jbuffer)) != -1) {
                    jos.write(jbuffer, 0, jbytesRead);
                }
                srPrototxtIs.close();
                jos.close();
            }

            // sr.caffemodel
            InputStream srCaffeIs = mContext.getResources().openRawResource(R.raw.sr_caffemodel);
            mSuperResolutionCaffeModelFile = new File(qrcodeDir, "sr.caffemodel");
            if(!mSuperResolutionCaffeModelFile.exists()){
                FileOutputStream kos = new FileOutputStream(mSuperResolutionCaffeModelFile);
                byte[] kbuffer = new byte[4096];
                int kbytesRead;
                while ((kbytesRead = srCaffeIs.read(kbuffer)) != -1) {
                    kos.write(kbuffer, 0, kbytesRead);
                }
                srCaffeIs.close();
                kos.close();
            }

            mWeChatQRCode = new WeChatQRCode(
                    mDetectorPrototxtFile.getAbsolutePath(),
                    mDetectorCaffeModelFile.getAbsolutePath(),
                    mSuperResolutionPrototxtFile.getAbsolutePath(),
                    mSuperResolutionCaffeModelFile.getAbsolutePath()
            );
        } catch (Exception e) {
            Log.i("qwe", "----------" + e.toString());
        }
    }

}
