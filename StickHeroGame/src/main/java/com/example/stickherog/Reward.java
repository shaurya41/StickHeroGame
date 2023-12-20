package com.example.stickherog;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
//class Reward {
//    private double x;
//    private double y;
//
//    public void setX(double x) {
//        this.x = x;
//    }
//
//    public void setY(double y) {
//        this.y = y;
//    }
//
//    public Reward(double x, double y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public void render(GraphicsContext gc, double cameraOffset) {
//        // Implement rendering logic for the reward
//        gc.setFill(Color.RED);
//        gc.fillOval(x-cameraOffset, y, 10, 10);  // Example rendering; customize as needed
//    }
//
//
//    public double getX() {
//        return x;
//    }
//
//    public double getY() {
//        return y;
//    }
//}
class Reward implements renderable {
    private double x;
    private double y;

    public Reward(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void render(GraphicsContext gc,double cameraOffset) {
        gc.setFill(Color.RED);
        gc.fillOval(x-cameraOffset,y, 10.0, 10.0);
    }

}
