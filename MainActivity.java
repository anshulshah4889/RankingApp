package com.example.leena.blindwalkapp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import java.net.URL;
import org.opencv.videoio.VideoCapture;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    URL videoUrl;
    JavaCameraView javaCameraView;
    private static String TAG="MainActivity";
    static{ System.loadLibrary("openCv_java"); }
    BaseLoaderCallback loaderCallback=new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch(status){
                case BaseLoaderCallback.SUCCESS:{
                    javaCameraView.enableView();
                    break;
                }
                default:{
                    super.onManagerConnected(status);
                    break;
                }

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        javaCameraView=(JavaCameraView)findViewById(R.id.camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(javaCameraView!=null){
            javaCameraView.disableView();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(javaCameraView!=null){
            javaCameraView.disableView();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
            if(OpenCVLoader.initDebug()){
                Log.i(TAG, "OpenCV Loaded Succesfully");
                loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            }
            else{
                Log.i(TAG, "OpenCV Failure to Load");
                OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, loaderCallback);
            }

    }

    @Override
    public void onCameraViewStarted(int width, int height) {


    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        //Do  individual frame-by-frame processing here
        return inputFrame.rgba();
    }
  /*another way to stream the camera: check which is more efficient later
    private void dispatchTakeVideoIntent(){
        Intent takeVideoIntent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(takeVideoIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takeVideoIntent,REQUEST_VIDEO_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode==REQUEST_VIDEO_CAPTURE && resultCode==RESULT_OK){
            Uri videoUri=intent.getData();
            try {
                videoUrl=new URL(videoUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }*/
}

