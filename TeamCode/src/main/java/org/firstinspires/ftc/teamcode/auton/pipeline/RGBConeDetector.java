package org.firstinspires.ftc.teamcode.auton.pipeline;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class RGBConeDetector extends OpenCvPipeline
{
    /*
     * A variable to define the positions
     */
    public int face = 3;

    /*
     * Some color constants
     */
    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar GREEN = new Scalar(0, 255, 0);
    static final Scalar RED = new Scalar(255, 0, 0);
    static final Scalar BLACK = new Scalar(0, 0, 0);

    /*
     * The core values which define the location and size of the sample regions
     */
    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(158,15);
    static final int REGION_WIDTH = 36; // TODO Find actual size of box
    static final int REGION_HEIGHT = 70; // TODO Find actual size of box

    Point region1_pointA = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x,
            REGION1_TOPLEFT_ANCHOR_POINT.y);
    Point region1_pointB = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

    /*
     * Working variables
     */
    Mat targetRegionR, targetRegionG, targetRegionB;
    int avgR, avgG, avgB;

    @Override
    public void init(Mat firstFrame)
    {
        /*
         * Submats are a persistent reference to a region of the parent
         * buffer. Any changes to the child affect the parent, and the
         * reverse also holds true.
         */
        targetRegionR = firstFrame.submat(new Rect(region1_pointA, region1_pointB));
        targetRegionG = firstFrame.submat(new Rect(region1_pointA, region1_pointB));
        targetRegionB = firstFrame.submat(new Rect(region1_pointA, region1_pointB));
    }

    @Override
    public Mat processFrame(Mat input)
    {

        // TODO EXPLAIN HOW WE PROCESS FRAME
        // TODO CLEAN UP COMMENTS AND SPACING TO CONDENSE THIS FILE

        avgR = (int) Core.mean(targetRegionR).val[0];
        avgG = (int) Core.mean(targetRegionG).val[1];
        avgB = (int) Core.mean(targetRegionB).val[2];
        Imgproc.putText(input, "RAVG " + avgR + ", GAVG " + avgG + ", BAVG " + avgB, new Point(5, 230), 0, 0.55, new Scalar(255, 255, 255), 2);

        /*
         * Draw a rectangle showing sample region on the screen.
         * Simply a visual aid. Serves no functional purpose.
         */
        Imgproc.rectangle(
                input, // Buffer to draw on
                region1_pointA, // First point which defines the rectangle
                region1_pointB, // Second point which defines the rectangle
                BLACK, // The color the rectangle is drawn in
                2); // Thickness of the rectangle lines
        /*
         * Find the max of the 3 averages
         */
        int minOneTwo = Math.max(avgR, avgG);
        int max = Math.max(minOneTwo, avgB);

        /*
         * Now that we found the max, we actually need to go and
         * figure out which sample region that value was from
         */
        if(max == avgR) // Was it from region 1?
        {
            face = 1; // Record our analysis
            /*
             * Draw a rectangular frame on top of the chosen region.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    RED, // The color the rectangle is drawn in
                    2);
        }
        else if(max == avgG) // Was it from region 2?
        {
            face = 2; // Record our analysis
            /*
             * Draw a rectangular frame on top of the chosen region.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    GREEN, // The color the rectangle is drawn in
                    2);
        }
        else if(max == avgB) // Was it from region 3?
        {
            face = 3; // Record our analysis

            /*
             * Draw a rectangular frame on top of the chosen region.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2);
        }
        return input;
    }
}