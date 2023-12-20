package com.example.stickherog;
public class Camera {
    private double x;

    public void tick(StickHeroCharacter player, double offset) {
        // Adjust the camera's X position based on the player's X position
        x = player.getX() - offset;

        // Ensure the camera stays within the screen's boundaries
        if (x < 0) {
            x = 0;
        }
    }

    public double getX() {
        return x;
    }
}






