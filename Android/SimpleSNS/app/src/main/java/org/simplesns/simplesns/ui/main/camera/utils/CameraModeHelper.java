package org.simplesns.simplesns.ui.main.camera.utils;

import android.annotation.TargetApi;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.os.Handler;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CameraModeHelper {

    private CaptureRequest mRequest;
    CaptureRequest.Builder mRequestBuilder;
    CameraCaptureSession mCaptureSession;
    CameraCaptureSession.CaptureCallback mCaptureCallback;
    Handler mBackgroundHandler;

    public CameraModeHelper(CaptureRequest.Builder mRequestBuilder,
                            CameraCaptureSession mCaptureSession,
                            CameraCaptureSession.CaptureCallback mCaptureCallback,
                            Handler mBackgroundHandler) {
        this.mRequestBuilder = mRequestBuilder;
        this.mCaptureSession = mCaptureSession;
        this.mCaptureCallback = mCaptureCallback;
        this.mBackgroundHandler = mBackgroundHandler;
    }

    boolean checkSessionAndBuilder(CameraCaptureSession session, CaptureRequest.Builder builder) {
        return session != null && builder != null;
    }

    public CameraModeHelper setOnAutoFlash() {
        mRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
        return this;
    }
    public CameraModeHelper setOnAlwaysFlash() {
        mRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                CaptureRequest.CONTROL_AE_MODE_ON_ALWAYS_FLASH);
        return this;
    }

    public CameraModeHelper resetAutoExposure() {
        mRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_OFF);
        setModeRequest ();
        return this;
    }

    public CameraModeHelper setAutoExposure (boolean isAEMode) {
        if (isAEMode) {
            mRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON);
        } else {
            mRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_OFF);
        }
        return this;
    }

    public CameraModeHelper setTorchFlash(boolean isTorch) {
        try {
            if (isTorch) {
                mRequestBuilder.set(CaptureRequest.FLASH_MODE,
                        CaptureRequest.FLASH_MODE_TORCH);
            } else {
                mRequestBuilder.set(CaptureRequest.FLASH_MODE,
                        CaptureRequest.FLASH_MODE_OFF);
            }
        } catch (Exception e) {
            // todo
        }
        return this;
    }

    public void setModeRequest () {
        if (!checkSessionAndBuilder(mCaptureSession, mRequestBuilder)) {
            return;
        }
        mRequest = mRequestBuilder.build();

        try {
            mCaptureSession.setRepeatingRequest(mRequest,
                    mCaptureCallback, mBackgroundHandler);
        }catch (Exception e) {
            // todo
        }
    }
}
