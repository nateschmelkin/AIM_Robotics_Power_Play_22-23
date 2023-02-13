package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.auton.pipeline.RGBConeDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class CameraSubsystem {

    public HardwareMap hwMap = null; // Local access to hardware map

    private OpenCvCamera camera; // Camera

    WebcamName webcamName; // Var for webcam name

    public RGBConeDetector pipeline = null; // Pipeline for sleeve detection

    public int coneFace; // Integer to represent the face of the cone sleeve

    // initCamera initializes the camera and sets it up
    public void initCamera(HardwareMap ahwMap) {
        hwMap = ahwMap;
        // OpenCV webcam
        webcamName = hwMap.get(WebcamName.class, "Ray");
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);
    }

    // initPipeline sets active pipeline for camera
    public void initPipeline() {
        // OpenCV pipeline
        pipeline = new RGBConeDetector();

        camera.setPipeline(pipeline);
    }

    // webcamStream turns on webcam
    public void webcamStream() {
        // Webcam Streaming
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }
    // processImage retrieves the face the pipeline recognizes
    public void processImage() {
        coneFace = pipeline.face;
    }

}
