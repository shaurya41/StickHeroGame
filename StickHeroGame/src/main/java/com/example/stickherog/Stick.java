package com.example.stickherog;

import java.awt.*;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Stick {
    private static final double MAX_LENGTH = 300; // Maximum length for the stick
    private static final double STICK_SPEED = 300.0;
    private static final double FALL_SPEED = 50.0;

    private double length;
    private boolean extending;
    private boolean falling;

    public Stick() {
        this.length = 0;
        this.extending = true;
        this.falling = false;
    }

    public double getLength() {
        return length;
    }

    public void extend(double deltaTime) {
        if (extending) {
            length += STICK_SPEED * deltaTime;

            if (length >= MAX_LENGTH) {
                extending = false;
                falling = true;
            }
        } else if (falling) {
            length += STICK_SPEED * deltaTime;
        }
    }
//    public void falldiagonally(double deltaTime){
////        Group root = new Group();
////        Scene scene = new Scene(root, 400, 400);
//        Rectangle stick =new Rectangle(100,100,20,300);
//        stick.setFill(Color.ORANGE);
////        root.getChildren().add(stick);
//        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), stick);
//        fadeOut.setFromValue(1.0);
//        fadeOut.setToValue(0.0);
//        SequentialTransition fade = new SequentialTransition(fadeOut);
//        fade.play();
//
//
//    }

    public void stopExtension() {
        extending = false;
        falling = true;
    }

    public double retrieveExtendedLength() {
        return length;
    }

    public void reset() {
        length = 0;
        extending = true;
        falling = false;
    }
}


