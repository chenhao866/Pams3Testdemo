package zzept.com.pams3testdemo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Method;
import java.util.List;

public class CameraSurfacePreview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;

	public CameraSurfacePreview(Context context) {
		super(context);
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
    @Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("Dennis", "surfaceCreated() is called");
		try {
			mCamera = Camera.open();
			if (mCamera == null) {
				return;
			}
			mCamera.setPreviewDisplay(holder);
			initCamera();
		} catch (Exception e) {
			Log.e("Dennis", "Error setting camera preview: " + e.getMessage());
		}
	}
    @Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		Log.d("Dennis", "surfaceChanged() is called");
		if (mCamera == null) {
			Log.d("Dennis", "cancelAutoFocus() faild");
			return;
		}
		mCamera.autoFocus(new AutoFocusCallback() {
			@Override
			public void onAutoFocus(boolean success, Camera camera) {
				if (success) {
					initCamera();
					camera.cancelAutoFocus();
				}
			}

		});
		try {
			mCamera.startPreview();
		} catch (Exception e) {
			Log.d("Dennis", "Error starting camera preview: " + e.getMessage());
		}
	}
    @Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}

		Log.d("Dennis", "surfaceDestroyed() is called");
	}

	public void takePicture(PictureCallback imageCallback) {
		if (mCamera == null) {
			return;
		}
		mCamera.takePicture(null, null, imageCallback);
	}


	public void initCamera() {
        Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPictureFormat(PixelFormat.JPEG);
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		mCamera.setParameters(parameters);
		mCamera.startPreview();
		mCamera.cancelAutoFocus();

	}


	private void setDispaly(Camera.Parameters parameters, Camera camera) {
		if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
			setDisplayOrientation(camera, 90);
		} else {
			parameters.setRotation(90);
		}

	}


	private void setDisplayOrientation(Camera camera, int i) {
		Method downPolymorphic;
		try {
			downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[] { int.class });
			if (downPolymorphic != null) {
				downPolymorphic.invoke(camera, new Object[] { i });
			}
		} catch (Exception e) {
			Log.e("Came_e", "图像出错");
		}
	}

	private Point getBestCameraResolution(Camera.Parameters parameters, Point screenResolution) {
		float tmp = 0f;
		float mindiff = 100f;
		float x_d_y = (float) screenResolution.x / (float) screenResolution.y;
		Size best = null;
		List<Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
		for (Size s : supportedPreviewSizes) {
			tmp = Math.abs(((float) s.height / (float) s.width) - x_d_y);
			if (tmp < mindiff) {
				mindiff = tmp;
				best = s;
			}
		}
		return new Point(best.width, best.height);
	}
}