package org.opencv;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.facebook.react.bridge.*;

public class OpenCVDecoderModule  extends ReactContextBaseJavaModule {

    private static final String TAG = "OpencvQRDecoder";
    private Context mContext;
    private DecodeUtils mDecodeUtils;

    public OpenCVDecoderModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    /**
     * @return the name of this module. This will be the name used to {@code require()} this module
     * from javascript.
     */
    @Override
    public String getName() {
        return "OpencvQRDecoder";
    }

    @ReactMethod
    public void init(){
        mDecodeUtils = new DecodeUtils(mContext);
    }

    @ReactMethod
    public void decode(final String uri,final Callback callback){
        Log.e(TAG, "decode uri = " + uri);
        new Thread(){
            @Override
            public void run() {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse(uri));
                    if(bitmap != null){
                        String qrCodeInfo = mDecodeUtils.decode(bitmap);
                        Log.e(TAG, "qrCodeInfo = " + qrCodeInfo);
                        callback.invoke(qrCodeInfo);
                    }
                }catch (Exception e){
                    Log.e(TAG, Log.getStackTraceString(e));
                    callback.invoke("");
                }
            }
        }.start();
    }
}
