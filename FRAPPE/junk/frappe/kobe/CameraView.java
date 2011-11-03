package frappe.kobe;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import frappe.kobe.R.id;
import frappe.kobe.R.layout;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;

public class CameraView extends Activity implements SurfaceHolder.Callback,	OnClickListener {
	
	int mCameraId;
	int mLockedCamera;
	static final int FOTO_MODE = 0;
	private static final String TAG = "CameraTest";
	Camera mCamera;
	boolean mPreviewRunning = false;
	private Context mContext = this;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Log.e(TAG, "onCreate");

		Bundle extras = getIntent().getExtras();

		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.camera_view);
		mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
		mSurfaceView.setOnClickListener(this);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		//set camera to front cam if one. default if none
		int numberOfCameras = Camera.getNumberOfCameras();
		CameraInfo cameraInfo = new CameraInfo();
		mCameraId = 0;
		
		for(int x=0; x < numberOfCameras; x++){
			Camera.getCameraInfo(x, cameraInfo);
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
                mCameraId = x;
            }
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		Log.e(TAG, "onResume");
		super.onResume();
		
		mCamera = Camera.open(mCameraId);
        mCamera.setDisplayOrientation(90);
        mLockedCamera = mCameraId;
	}
	
	@Override
	protected void onPause(){
		Log.e(TAG, "onPause");
		super.onPause();
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	

	@Override
	protected void onStop() {
		Log.e(TAG, "onStop");
		super.onStop();
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.e(TAG, "surfaceCreated");
		mCamera = Camera.open();

	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		Log.e(TAG, "surfaceChanged");

		// XXX stopPreview() will crash if preview is not running
		if (mPreviewRunning) {
			mCamera.stopPreview();
		}

		Camera.Parameters p = mCamera.getParameters();
		p.setPreviewSize(w, h);
		mCamera.setParameters(p);
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mCamera.startPreview();
		mPreviewRunning = true;
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e(TAG, "surfaceDestroyed");
		mCamera.stopPreview();
		mPreviewRunning = false;
		mCamera.release();
	}


	public void onClick(View arg0) {

		mCamera.takePicture(null, mPictureCallback, mPictureCallback);

	}
	
	public static boolean StoreByteImage(Context mContext, byte[] imageData,
			int quality, String expName) {

        File sdImageMainDirectory = new File("/sdcard/facePICs");
		FileOutputStream fileOutputStream = null;
		String nameFile;
		try {

			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inSampleSize = 5;
			
			Bitmap myImage = BitmapFactory.decodeByteArray(imageData, 0,
					imageData.length,options);

			
			fileOutputStream = new FileOutputStream(
					sdImageMainDirectory.toString() +"/image.png");
							
  
			BufferedOutputStream bos = new BufferedOutputStream(
					fileOutputStream);

			myImage.compress(CompressFormat.PNG, quality, bos);

			bos.flush();
			bos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] imageData, Camera c) {
	
			if (imageData != null) {
	
				Intent mIntent = new Intent();
	
				StoreByteImage(mContext, imageData, 50, "ImageName");
				mCamera.startPreview();
	
				setResult(FOTO_MODE, mIntent);
				finish();
	
			}
		}
	};

}