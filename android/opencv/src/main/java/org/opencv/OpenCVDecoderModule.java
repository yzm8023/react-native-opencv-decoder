package org.opencv;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Base64;
import android.graphics.BitmapFactory;

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
    public void decode(final String base64Img,final Callback callback){
        if(BuildConfig.DEBUG){
            Log.d(TAG, "base64Img = " + base64Img);
        }
        new Thread(){
            @Override
            public void run() {
                try {
                    byte[] byteArray = Base64.decode(base64Img,Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse(uri));
                    if(bitmap != null){
                        String qrCodeInfo = mDecodeUtils.decode(bitmap);
                        if(BuildConfig.DEBUG){
                            Log.d(TAG, "qrCodeInfo = " + qrCodeInfo);
                        }
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
