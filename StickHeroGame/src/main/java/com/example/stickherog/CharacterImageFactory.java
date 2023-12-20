package com.example.stickherog;
import javafx.scene.image.Image;

public class CharacterImageFactory {
    private static final String NORMAL_IMAGE_PATH = "file:/C:/Users/shaur/Desktop/sticknewest.png";
    private static final String FLIPPED_IMAGE_PATH = "C:\\Users\\shaur\\Downloads\\sticknewest_f1_prev_ui.png";

    public static Image getImage(boolean isFlipped) {
        return isFlipped ? new Image(FLIPPED_IMAGE_PATH) : new Image(NORMAL_IMAGE_PATH);
    }
}
