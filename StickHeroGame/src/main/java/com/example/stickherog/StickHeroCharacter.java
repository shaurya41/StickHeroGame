package com.example.stickherog;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.example.stickherog.HelloApplication.*;

//import static com.example.stickherog.HelloApplication.hasMovedToStickEnd;

public class StickHeroCharacter {
    private static StickHeroCharacter instance;

    private static final double STICK_EXTENSION_SPEED = 200.0;
    private static final double MOVEMENT_SPEED = 20.0;
    private static final double CHARACTER_HEIGHT = 50.0;

    public static double x;

    public StickHeroCharacter() {

    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    private double y;
    private double stickLength;
    private boolean isFlipped;
    public static Image characterImage;

    private Stick stick;
    private boolean isExtending = false;
    private boolean isFalling = false;
    double retreivelength;
    double retrievex;
    boolean ismoving=false;

    double lastPositionBeforeMoving = 0;
    public StickHeroCharacter(double x, double y) {
        this.x = x;
        this.y = y;
        this.stickLength = 0;
        this.isFlipped = false;
        this.characterImage = new Image("file:/C:/Users/shaur/Desktop/sticknewest.png");
        this.stick = new Stick();
    }

    public static StickHeroCharacter getInstance() {
        if (instance == null) {
            instance = new StickHeroCharacter();
        }
        return instance;
    }
    public void update(double deltaTime, Platform platform,GraphicsContext gc) {

        if (isFlipped && isExtending) {
            stickLength += STICK_EXTENSION_SPEED * deltaTime;
            stick.extend(deltaTime);
        }

        else {
            stopStick();
            if (retreivelength > 0) {
                double targetX = retreivelength+retrievex+15;
                stopStick();
                // Calculate the stick's end X-coordinate
                if(x<targetX) {
                    ismoving=true;
                    x += 100 * deltaTime;
                    stopStick();
                }
                if(x>targetX &&x!=0&&ismoving){
                    ismoving=false;
                    stopStick();
                    HelloApplication.isStickStopped=true;
                    HelloApplication.hasMovedToStickEnd=true;

                }

            }
//            if(hasMovedToStickEnd){
//                fallCharacterTo(x,450,gc);
//                ismoving=false;
//                stopStick();
//                HelloApplication.isStickStopped=true;
//                HelloApplication.hasMovedToStickEnd=true;
//                isCharacterFlipped=false;
//
//            }
            else {
                // Move character towards the stick's end point
                if (x + MOVEMENT_SPEED < platform.getX() + platform.getWidth()) {
                    x += MOVEMENT_SPEED * deltaTime;
                }
            }
        }


    }
    public void renderCharacter(GraphicsContext gc) {
        gc.drawImage(characterImage, x - cam.getX(), y, 20, CHARACTER_HEIGHT);
    }

    public void renderStick(GraphicsContext gc) {
        if (isExtending) {
            gc.setFill(Color.BLACK);
            gc.fillRect(x + 20 - cam.getX(), y - stick.getLength() + CHARACTER_HEIGHT / 2, 5, stick.getLength());
                    retrievex=x;
                    retreivelength=stick.getLength();
        } else {
            gc.setFill(Color.GREEN);
            gc.fillRect(retrievex + 20 - cam.getX(), 395, retreivelength, 5);
        }
    }



    //    public void renderCharacter(GraphicsContext gc) {
//        gc.drawImage(characterImage, x, y, 20, CHARACTER_HEIGHT);
//    }
//
//public void renderStick(GraphicsContext gc) {
//    if (isExtending) {
//        gc.setFill(Color.BLACK);
//        gc.fillRect(x + 20, y - stick.getLength() + CHARACTER_HEIGHT / 2, 5, stick.getLength());
//        retrievex=x;
//        retreivelength=stick.getLength();
//    } else {
//
//        gc.setFill(Color.GREEN);
//        gc.fillRect(retrievex+20, 395, retreivelength, 5);
//    }
//}
    public void stopStick() {
        stick.stopExtension();
        stickLength = stick.retrieveExtendedLength();
    }

    public void extend() {
        isFlipped = true;
        isExtending = true;
    }

    public boolean intersects(Reward reward) {
        Rectangle characterBounds = new Rectangle(x, y, 20, CHARACTER_HEIGHT + stickLength);
        Rectangle rewardBounds = new Rectangle(reward.getX(), reward.getY(), 10, 10);
        return characterBounds.intersects(rewardBounds.getBoundsInLocal());
    }
    public boolean intersects(reward2 reward2s) {
        Rectangle characterBounds = new Rectangle(x, y, 20, CHARACTER_HEIGHT + stickLength);
        Rectangle reward2sBounds = new Rectangle(reward2s.getX(), reward2s.getY(), 10, 10);
        return characterBounds.intersects(reward2sBounds.getBoundsInLocal());
    }
    public boolean intersects(PoisonReward poisonReward) {
        Rectangle characterBounds = new Rectangle(x, y, 20, CHARACTER_HEIGHT + stickLength);
        Rectangle PoisonRewardBounds = new Rectangle(poisonReward.getX(), poisonReward.getY(), 10, 10);
        return characterBounds.intersects(PoisonRewardBounds.getBoundsInLocal());
    }


    public void collectReward(Reward reward) {
    }
    public void collectreward2(reward2 reward2) {
    }

    public void land() {
        isFalling=false;

    }

    public void revive() {
        // Handle revival logic
    }
    public void updateStickLength() {
        stickLength = stick.getLength();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getStickLength() {
        return stickLength;
    }

    public void moveCharacterTo(double xPosition, double yPosition, GraphicsContext gc) {

//            x = xPosition+10; // Update the character's x position
//            y = yPosition; // Update the character's y position
//            isFlipped = false;
            isExtending = false;
            stickLength = 0; // Reset the stick length
            isFalling = false;

    }
    public static boolean isJumping = false;
    private double initialY;


    public void jump() {
        if (!isJumping) {
            // Save the initial Y position before jumping
            initialY = getY();
            // Set the character's Y position to simulate a jump
            setY(getY() - 30);
            isJumping = true;
        } else {
            // Set the character's Y position back to the initial position
            setY(getY());
            isJumping = false;
        }
    }

    public void fallCharacterTo(double xPosition, double yPosition,GraphicsContext gc) {
            x = xPosition+15; // Update the character's x position
            y = yPosition; // Update the character's y position
//        isFlipped = false;
        isExtending = false;
        stickLength = 0; // Reset the stick length
        isFalling = false;

    }
    public void setFlipped(boolean flipped) {
        isFlipped = flipped;

        double yOffset = 50.0; // Set the desired downward movement

        if (flipped) {
            characterImage = new Image("C:\\Users\\shaur\\Downloads\\sticknewest_f1_prev_ui.png");
            y += yOffset; // Move character downwards
        } else {
            characterImage = new Image("file:/C:/Users/shaur/Desktop/sticknewest.png");
            y -= yOffset; // Move character upwards (assuming it moved downwards before)
        }
    }


    public Image getCharacterImage() {
        return characterImage;
    }
    public void resetStick() {
        stick.reset(); // Reset any parameters related to the stick's extension
        stickLength = 0; // Reset the stick length to zero
    }


}


