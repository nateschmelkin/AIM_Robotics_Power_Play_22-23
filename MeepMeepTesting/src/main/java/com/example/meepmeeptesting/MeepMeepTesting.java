package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {

    public static double startX = 35;
    public static double startY = -62.75;
    private static double startAngle = Math.toRadians(180);

    public static double coneLifterOffset = 6.457;
    public static double coneLifterPythagoreanOffset = 6.4;

    private static double parkingY = -14.5;
    private static double parkingRedX = 12;
    private static double parkingGreenX = 35;
    private static double parkingBlueX = 66.5;

    public static double stackFrontLiftPickupAngle = Math.toRadians(170);
    public static double stackBackLiftPickupAngle = Math.toRadians(-10);
    public static double stackAdjustment = Math.toRadians(4);

    public static double stackX = 71.3;
    public static double stackY = -14.8;

    public static double scorePoleX = 24;
    public static double scorePoleY = 0;
    public static double scorePoleFrontLiftAngle = Math.toRadians(315);
    public static double scorePoleBackLiftAngle = Math.toRadians(135);
    public static double scoreAdjustment = Math.toRadians(6);
    public static double scorePoleWait = 0.2;

    public static double scoreDropDistance = 2.4;

    public static double coneStack1Height = 9;
    public static double coneStack2Height = 7.75;
    public static double coneStack3Height = 6.5;

    public static double prepSpotX = 33.5;
    public static double prepSpotY = -15;

    public static double pickupTime = 0.42;
    public static double dropTime = 0.55;

    public static Pose2d startPose = new Pose2d(startX, startY, startAngle);
    public static Pose2d leftStartPose = new Pose2d(-startX, startY, startAngle);

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);


        RoadRunnerBotEntity coneCyclePathRight = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeBlueDark())
                .setDimensions(16.06, 15.67)
                .setConstraints(46, 46, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(startPose)
                                .strafeTo(new Vector2d(startX, scorePoleY))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(2.5)
                                .forward(2)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(dropTime)
                                .UNSTABLE_addTemporalMarkerOffset(.5, () -> {

                                })
                                .back(2)
                                .splineToConstantHeading(new Vector2d(startX, stackY + 1), Math.toRadians(90))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .strafeTo(new Vector2d(stackX - coneLifterOffset - .75, stackY))
                                //
                                // STACK #1
                                //
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(pickupTime)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .strafeTo(new Vector2d(prepSpotX, prepSpotY))
                                .turn(Math.toRadians(127))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .strafeTo(new Vector2d(scorePoleX + coneLifterPythagoreanOffset + .5, scorePoleY - coneLifterPythagoreanOffset))
                                .waitSeconds(scorePoleWait)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(dropTime)
                                .UNSTABLE_addTemporalMarkerOffset(.5, () -> {

                                })
                                .lineToSplineHeading(new Pose2d(prepSpotX, prepSpotY, stackBackLiftPickupAngle)) // ADDED ADJUSTMENT HERE
                                .strafeTo(new Vector2d(stackX - coneLifterOffset, stackY))
                                //
                                // STACK #2
                                //
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(pickupTime)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .strafeTo(new Vector2d(prepSpotX, prepSpotY))
                                .turn(Math.toRadians(127))
                                .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {

                                })
                                .strafeTo(new Vector2d(scorePoleX + coneLifterPythagoreanOffset + .5, scorePoleY - coneLifterPythagoreanOffset))
                                .waitSeconds(scorePoleWait)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(dropTime)
                                .UNSTABLE_addTemporalMarkerOffset(0.8, () -> {

                                })
                                .lineToSplineHeading(new Pose2d(prepSpotX, prepSpotY, stackFrontLiftPickupAngle))
                                .build()
                );

        RoadRunnerBotEntity coneCyclePathLeft = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeRedDark())
                .setDimensions(16.06, 15.67)
                .setConstraints(46, 46, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(leftStartPose)
                                .strafeTo(new Vector2d(-startX, scorePoleY))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(2.5)
                                .back(2)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(dropTime)
                                .UNSTABLE_addTemporalMarkerOffset(.5, () -> {

                                })
                                .forward(2)
                                .splineToConstantHeading(new Vector2d(-startX, stackY + 1), Math.toRadians(-90))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .strafeTo(new Vector2d(-stackX + coneLifterOffset - .75, stackY))
                                //
                                // STACK #1
                                //
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(pickupTime)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .strafeTo(new Vector2d(-prepSpotX, prepSpotY))
                                .turn(-Math.toRadians(127))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .strafeTo(new Vector2d(-scorePoleX - coneLifterPythagoreanOffset - .5, scorePoleY - coneLifterPythagoreanOffset))
                                .waitSeconds(scorePoleWait)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(dropTime)
                                .UNSTABLE_addTemporalMarkerOffset(.5, () -> {

                                })
                                .lineToSplineHeading(new Pose2d(-prepSpotX, prepSpotY, stackBackLiftPickupAngle)) // ADDED ADJUSTMENT HERE
                                .strafeTo(new Vector2d(-stackX + coneLifterOffset, stackY))
                                //
                                // STACK #2
                                //
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(pickupTime)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .strafeTo(new Vector2d(-prepSpotX, prepSpotY))
                                .turn(-Math.toRadians(127))
                                .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {

                                })
                                .strafeTo(new Vector2d(-scorePoleX - coneLifterPythagoreanOffset - .5, scorePoleY - coneLifterPythagoreanOffset))
                                .waitSeconds(scorePoleWait)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                                })
                                .waitSeconds(dropTime)
                                .UNSTABLE_addTemporalMarkerOffset(0.8, () -> {

                                })
                                .lineToSplineHeading(new Pose2d(-prepSpotX, prepSpotY, stackFrontLiftPickupAngle))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(coneCyclePathRight)
                .addEntity(coneCyclePathLeft)
                .start();
    }
}