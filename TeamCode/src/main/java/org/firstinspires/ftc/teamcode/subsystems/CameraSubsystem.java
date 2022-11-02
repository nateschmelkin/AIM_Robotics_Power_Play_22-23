package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.auton.RGBConeDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class CameraSubsystem {

    private OpenCvCamera camera;
    WebcamName webcamName;

    private RGBConeDetector pipeline;

    public int coneFace;

    public void initCamera(HardwareMap hwMap) {
        // OpenCV webcam
        webcamName = hwMap.get(WebcamName.class, "Webcam 1");
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);
    }

    public void initPipeline() {
        // OpenCV pipeline
        pipeline = new RGBConeDetector();

        camera.setPipeline(pipeline);
    }

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

    public void processImage() {
        coneFace = pipeline.face;
    }

}