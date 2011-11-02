package kobe.frappe;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class PhotoTaker extends Activity implements OnClickListener{
	int FOTO_TAKEN = 1337;
	CameraPreview mPreview;
	Camera mCamera;
	int mNumberOfCameras;
	int cameraId;
	int lockedCamera;
	SurfaceView mSurfaceView;
	SurfaceHolder mSurfaceHolder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //set view for this activity to be displayed on
		setContentView(R.layout.camera_view);
		mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
		
		//when surface is tapped, take picture
		mSurfaceView.setOnClickListener(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceHolder = mSurfaceView.getHolder();
		
		try {
			mCamera.setPreviewDisplay(mSurfaceHolder);
		} catch (IOException e) {}
		
		cameraId = 0;
		mNumberOfCameras = Camera.getNumberOfCameras();
		CameraInfo cameraInfo = new CameraInfo();
		
		//open the front facing camera if phone has one. should default to cameraId 0. back camera
		for(int x=0; x < mNumberOfCameras; x++){
			Camera.getCameraInfo(x, cameraInfo);
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = x;
            }
		}
	}
	
    @Override
    protected void onResume() {
        super.onResume();

        // Open the default i.e. the first rear facing camera.
        mCamera = Camera.open(cameraId);
        lockedCamera = cameraId;
        mPreview.setCamera(mCamera);
        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        
        // Because the Camera object is a shared resource, it's very
        // important to release it when the activity is paused.
        if (mCamera != null) {
            mPreview.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] imageData, Camera c) {
	
			if (imageData != null) {
				Intent mIntent = new Intent();
	
				StoreByteImage(imageData, 50);
				mCamera.startPreview();
	
				setResult(FOTO_TAKEN, mIntent);
				finish();
	
			}
		}
	};
	
	public static boolean StoreByteImage(byte[] imageData, int quality) {

        File picdir = new File("/sdcard/FRAPPEpics");
		FileOutputStream fileOutputStream = null;
		try {

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 5;
			
			Bitmap myImage = BitmapFactory.decodeByteArray(imageData, 0, imageData.length,options);

			
			fileOutputStream = new FileOutputStream(picdir.toString()+"/exPIC.jpeg");
							
  
			BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
			myImage.compress(CompressFormat.JPEG, quality, bos);
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
    
	@Override
	public void onClick(View v) {
		mCamera.takePicture(null, mPictureCallback, mPictureCallback);
	}

}
