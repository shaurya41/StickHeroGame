package com.example.stickherog;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
//class reward2 {
//    private double x;
//
//    public void setX(double x) {
//        this.x = x;
//    }
//
//    public void setY(double y) {
//        this.y = y;
//    }
//
//    private double y;
//
//    public reward2(double x, double y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public void render(GraphicsContext gc, double v) {
//        // Implement rendering logic for the reward
//        gc.setFill(Color.YELLOW);
//        gc.fillOval(x-v, y, 10, 10);// Example rendering; customize as needed
//    }
////    public void render(GraphicsContext gc, double cameraX) {
////        gc.setFill(Color.BLUE); // Set the desired color for the oval
////        gc.fillOval(x - cameraX, y, 10,10);
////    }
//
//    public double getX() {
//        return x;
//    }
//
//    public double getY() {
//        return y;
//    }
////    public void shiftX(double shiftAmount) {
////        x += shiftAmount;
////    }
//
//}
class reward2 implements renderable {
    public double x;
    public double y;

    public reward2(double x, double y) {
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
        gc.setFill(Color.YELLOW);
        gc.fillOval(this.x-cameraOffset, this.y, 10.0, 10.0);
    }

}

