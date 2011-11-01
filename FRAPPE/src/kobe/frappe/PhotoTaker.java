package kobe.frappe;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class PhotoTaker extends Activity {
	Preview mPreview;
	Camera mCamera;
	int mNumberOfCameras;
	int cameraId;
	int lockedCamera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // Create a RelativeLayout container that will hold a SurfaceView,
        // and set it as the content of our activity.
        mPreview = new Preview(this);
        setContentView(mPreview);
		
		cameraId = 0;
		mNumberOfCameras = Camera.getNumberOfCameras();
		CameraInfo cameraInfo = new CameraInfo();
		
		//open the front facing camera if phone has one. should default to cameraId 0. back cam
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

}
