package com.example.stickherog;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Platform {
    private double x;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    private double y;
    private double width;
    private double height;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Platform(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


//    public void render(GraphicsContext gc) {
//        // Implement rendering logic for the platform
//
//        gc.fillRect(x, y, width, height);
//    }
 public void render(GraphicsContext gc, double cameraOffset) {
    gc.setFill(Color.BLACK); // Adjust color as needed
    gc.fillRect(x + cameraOffset, y, width, height);
}

//    public void shiftX(double shiftAmount) {
//        x += shiftAmount;
//    }



}
