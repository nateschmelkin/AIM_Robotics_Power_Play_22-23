package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {

    public static double startXBlue = -36.0;
    public static double startYBlue = 62.9;
    public static double startAngleBlue = Math.toRadians(0);

    public static double coneLifterOffset = 9;
    public static double approachDistanceOffset = 2.3;

    public static double parkingYBlue = 35;
    public static double parkingRedXBlue = -12;
    public static double parkingBlueXBlue = -36;
    public static double parkingGreenXBlue = -60;

    public static double stackXBlue = -70;
    public static double stackYBlue = 12;

    public static double firstScorePoleXBlue = -48;
    public static double firstScorePoleYBlue = 24;

    public static double secondScorePoleXBlue = -24;
    public static double secondScorePoleYBlue = 0;

    public static double thirdScorePoleXBlue = -24;
    public static double thirdScorePoleYBlue = 24;

    public static Pose2d startPoseBlue = new Pose2d(startXBlue, startYBlue, startAngleBlue);



    public static double startXRed = 36.0;
    public static double startYRed = -62.9;
    public static double startAngleRed = Math.toRadians(180);

    public static double parkingYRed = -35;
    public static double parkingRedXRed = 12;
    public static double parkingBlueXRed = 36;
    public static double parkingGreenXRed = 60;

    public static double stackXRed = 70;
    public static double stackYRed = -12;

    public static double firstScorePoleXRed = 48;
    public static double firstScorePoleYRed = -24;

    public static double secondScorePoleXRed = 24;
    public static double secondScorePoleYRed = 0;

    public static double thirdScorePoleXRed = 24;
    public static double thirdScorePoleYRed = -24;

    public static Pose2d startPoseRed = new Pose2d(startXRed, startYRed, startAngleRed);

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        // Declare our first bot
        RoadRunnerBotEntity complexPathBlue = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeBlueDark())
                .setDimensions(15.3, 14.2)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(startPoseBlue)
                                .strafeTo(new Vector2d(firstScorePoleXBlue + coneLifterOffset + approachDistanceOffset, firstScorePoleYBlue))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // LIFT UP CODE
                                })
                                .waitSeconds(2)
                                .strafeTo(new Vector2d(firstScorePoleXBlue + coneLifterOffset, firstScorePoleYBlue))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // DROP CONE CODE
                                })
                                .waitSeconds(1)
                                .strafeTo(new Vector2d(startXBlue, stackYBlue))
                                .strafeTo(new Vector2d(stackXBlue + coneLifterOffset, stackYBlue))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // PICK UP CONE CODE
                                })
                                .waitSeconds(1.25)
                                .splineToConstantHeading(new Vector2d(secondScorePoleXBlue + coneLifterOffset + approachDistanceOffset, secondScorePoleYBlue), Math.toRadians(270))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // LIFT UP CODE
                                })
                                .waitSeconds(2)
                                .strafeTo(new Vector2d(secondScorePoleXBlue + coneLifterOffset, secondScorePoleYBlue))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // DROP CONE CODE
                                })
                                .waitSeconds(1)
                                .splineToConstantHeading(new Vector2d(secondScorePoleXBlue + coneLifterOffset + approachDistanceOffset, stackYBlue), Math.toRadians(90))
                                .strafeTo(new Vector2d(stackXBlue + coneLifterOffset, stackYBlue))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // PICK UP CONE CODE
                                })
                                .waitSeconds(1.25)
                                .splineToConstantHeading(new Vector2d(thirdScorePoleXBlue + coneLifterOffset + approachDistanceOffset, thirdScorePoleYBlue), Math.toRadians(-270))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // LIFT UP CODE
                                })
                                .waitSeconds(2)
                                .strafeTo(new Vector2d(thirdScorePoleXBlue + coneLifterOffset, thirdScorePoleYBlue))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // DROP CONE CODE
                                })
                                .waitSeconds(1)
                                .splineToConstantHeading(new Vector2d(parkingRedXBlue, parkingYBlue), Math.toRadians(90))
                                .build()
                );

        RoadRunnerBotEntity complexPathRed = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeRedDark())
                .setDimensions(15.3, 14.2)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(startPoseRed)
                                .strafeTo(new Vector2d(firstScorePoleXRed - coneLifterOffset - approachDistanceOffset, firstScorePoleYRed))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // LIFT UP CODE
                                })
                                .waitSeconds(2)
                                .strafeTo(new Vector2d(firstScorePoleXRed - coneLifterOffset, firstScorePoleYRed))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // DROP CONE CODE
                                })
                                .waitSeconds(1)
                                .strafeTo(new Vector2d(startXRed, stackYRed))
                                .strafeTo(new Vector2d(stackXRed - coneLifterOffset, stackYRed))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // PICK UP CONE CODE
                                })
                                .waitSeconds(1.25)
                                .splineToConstantHeading(new Vector2d(secondScorePoleXRed - coneLifterOffset - approachDistanceOffset, secondScorePoleYRed), Math.toRadians(-270))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // LIFT UP CODE
                                })
                                .waitSeconds(2)
                                .strafeTo(new Vector2d(secondScorePoleXRed - coneLifterOffset, secondScorePoleYRed))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // DROP CONE CODE
                                })
                                .waitSeconds(1)
                                .splineToConstantHeading(new Vector2d(secondScorePoleXRed - coneLifterOffset - approachDistanceOffset, stackYRed), Math.toRadians(-90))
                                .strafeTo(new Vector2d(stackXRed - coneLifterOffset, stackYRed))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // PICK UP CONE CODE
                                })
                                .waitSeconds(1.25)
                                .splineToConstantHeading(new Vector2d(thirdScorePoleXRed - coneLifterOffset - approachDistanceOffset, thirdScorePoleYRed), Math.toRadians(270))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // LIFT UP CODE
                                })
                                .waitSeconds(2)
                                .strafeTo(new Vector2d(thirdScorePoleXRed - coneLifterOffset, thirdScorePoleYRed))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    // DROP CONE CODE
                                })
                                .waitSeconds(1)
                                .splineToConstantHeading(new Vector2d(parkingRedXRed, parkingYRed), Math.toRadians(-90))
                                .build()
                );

        RoadRunnerBotEntity redSleevePath = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeRedDark())
                .setDimensions(15.3, 14.2)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.8)
                .followTrajectorySequence(drive ->
                    drive.trajectorySequenceBuilder(new Pose2d(36, 62.9, Math.toRadians(270)))
                            .strafeTo(new Vector2d(36, 35))
                            .strafeTo(new Vector2d(61, 35))
                            .build()
                );

        RoadRunnerBotEntity blueSleevePath = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeBlueDark())
                .setDimensions(15.3, 14.2)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.8)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(36, 62.9, Math.toRadians(270)))
                                .strafeTo(new Vector2d(36, 35))
                                .build()
                );

        RoadRunnerBotEntity greenSleevePath = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeBlueLight())
                .setDimensions(15.3, 14.2)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.8)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(36, 62.9, Math.toRadians(270)))
                                .strafeTo(new Vector2d(36, 35))
                                .strafeTo(new Vector2d(13, 35))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)

                // Add both of our declared bot entities
//                .addEntity(redSleevePath)
//                .addEntity(blueSleevePath)
//                .addEntity(greenSleevePath)
                .addEntity(complexPathBlue)
                .addEntity(complexPathRed)
                .start();
    }
}