package com.example.stickherog;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
 import com.almasb.fxgl.audio.Sound;
 import javafx.animation.AnimationTimer;
 import javafx.animation.FadeTransition;
 import javafx.animation.SequentialTransition;
 import javafx.animation.TranslateTransition;
 import javafx.application.Application;
 import javafx.geometry.Insets;
 import javafx.geometry.Pos;
 import javafx.scene.Group;
 import javafx.scene.Scene;
 import javafx.scene.canvas.Canvas;
 import javafx.scene.canvas.GraphicsContext;
 import javafx.scene.image.Image;
 import javafx.scene.image.ImageView;
 import javafx.scene.input.KeyCode;
 import javafx.scene.layout.Pane;
 import javafx.scene.layout.VBox;
 import javafx.scene.paint.Color;
 import javafx.scene.text.Font;
 import javafx.scene.text.FontWeight;
 import javafx.scene.text.Text;
 import javafx.stage.Stage;

 import java.io.File;
 import java.util.ArrayList;
 import java.util.List;

 import javafx.fxml.FXML;
 import javafx.scene.control.Button;
 import javafx.scene.image.Image;
 import javafx.scene.image.ImageView;
 import javafx.util.Duration;

// import javax.print.attribute.standard.Media;
//import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.Test;
import org.w3c.dom.Entity;

import static org.testng.AssertJUnit.assertEquals;

//import static com.sun.webkit.graphics.GraphicsDecoder.SCALE;
public class HelloApplication extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Image backgroundImage;
    private Pane root;
    private Canvas canvas;
    private GraphicsContext gc;
    private StickHeroCharacter stickHero;
    private List<Platform> platforms;
    private List<Reward> rewards;
    private List<reward2> reward2s;
    private List<PoisonReward> poisonrewards;
//    private List<Obstacle> obstacles; // Add this line

    public static boolean isFlipping = false;
    private int score = 0;
    private int cherries = 0;
    public static boolean isStickStopped = false;
    public static boolean hasMovedToStickEnd = false;
    private boolean isGameOver = false;
    static boolean isCharacterOnPlatform = true;
    private VBox reviveBox;
    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    boolean gameoverflag=false;
//
//    public int getframwidth(){
//        return WIDTH*SCALE;
//    }
//    public int getframeheight(){
//        return HEIGHT*SCALE;
//    }
    public static Camera cam=new Camera();

    private static final double MIN_PLATFORM_GAP = 200.0;
    private static final double MAX_PLATFORM_GAP = 300.0;

public void tick(){
    cam.tick(stickHero, WIDTH / 4);

}
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Stick Hero Game");
        root = new Pane();
        scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        ((Pane) root).getChildren().add(canvas);

        initializeGame(); // Move this line here
        handleInput(scene);
        playBackgroundMusic();
        scene.setOnKeyPressed(event -> {
              if (event.getCode() == KeyCode.ENTER&&StickHeroCharacter.isJumping) {
                  StickHeroCharacter.isJumping=false;
                  stickHero.setY(350);
            }
            if (event.getCode() == KeyCode.SHIFT&&!StickHeroCharacter.isJumping) {
                isCharacterFlipped = !isCharacterFlipped;
                stickHero.setFlipped(isCharacterFlipped);
            }
            if (event.getCode() == KeyCode.SPACE && !isFlipping&&!stickHero.ismoving&& !isCharacterFlipped) {
                stickHero.extend();
                isStickStopped = false; // Reset the flag when the stick is flipping
            }
            if (event.getCode() == KeyCode.ENTER&& !stickHero.ismoving) {
                stickHero.stopStick();
                isStickStopped = true;
            }
            if (event.getCode() == KeyCode.SPACE && stickHero.ismoving&&!isCharacterFlipped) {
                // Check if the character is on a platform before allowing it to jump
                stickHero.jump();
            }
            isFlipping = false;
        });


        new AnimationTimer() {
            long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) / 1e9;
                cam.tick(stickHero, WIDTH / 4);
                update(deltaTime);
                render();
                lastTime = now;

            }
        }.start();

        primaryStage.show();
    }
    private void initializeUI() {
        // Create a button to start the game
        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> startGame());

        // Add the button to the root pane
        root.getChildren().add(startButton);
    }
    private int getframeheight() {
        return 0;
    }

    private void startGame() {
//        playBackgroundMusic();
        new AnimationTimer() {
            long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) / 1e9;
                update(deltaTime);
                render();
                lastTime = now;
            }
        }.start();
    }

    private MediaPlayer bgmPlayer;
    private MediaPlayer rewardplayer;
    private MediaPlayer deadplayer;

    private void playBackgroundMusic() {
        String musicFile = "C:\\Users\\shaur\\Desktop\\game-music-7408.mp3"; // Replace this with the actual path to your music file
        try {
            Path path = Paths.get(musicFile);
            String uri = path.toUri().toString();
            Media media = new Media(uri);
            bgmPlayer = new MediaPlayer(media);
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgmPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void playrewardMusic() {
        String musicFile ="C:\\Users\\shaur\\Desktop\\coin-upaif-14631.mp3"; // Replace this with the actual path to your music file
        try {
            Path path = Paths.get(musicFile);
            String uri = path.toUri().toString();
            Media media = new Media(uri);
            rewardplayer = new MediaPlayer(media);
            rewardplayer.setCycleCount(1);
            rewardplayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void playerdeadMusic() {
        String musicFile ="C:\\Users\\shaur\\Desktop\\male-scream-in-fear-123079.mp3"; // Replace this with the actual path to your music file
        try {
            Path path = Paths.get(musicFile);
            String uri = path.toUri().toString();
            Media media = new Media(uri);
            deadplayer = new MediaPlayer(media);
            deadplayer.setCycleCount(1);
            deadplayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Platform createNewPlatform(double startX, double width) {
        return new Platform(startX, 385, width, 2000);
    }

    private void initializeGame() {
        playBackgroundMusic();
        stickHero = new StickHeroCharacter(0, 350);
        platforms = new ArrayList<>();
        rewards = new ArrayList<>();
        reward2s=new ArrayList<>();
        poisonrewards=new ArrayList<>();


        try {
            backgroundImage = new Image("file:/C:/Users/shaur/Desktop/bgimage.png");

        } catch (Exception e) {
            e.printStackTrace();
        }

        double platformX = 0;
        double rewardX = 25;
        double platformWidth = 50;
        double platformWidthmin = 70;
        double platformWidthmax = 150; // Default width
        double rewardX2 = 75;
        double poisonrewardX=75;


        for (int i = 0; i < 500; i++) {
            // Add platform
            Platform platform = new Platform(platformX, HEIGHT - 200, platformWidth, 2000);
            platforms.add(platform);
            // Add reward
            Reward reward = new Reward(rewardX, 385);
            rewards.add(reward);

            // Calculate the position for rewardX2 between two platforms
            if (i < 499) { // Ensure there is a next platform
                Platform nextPlatform = new Platform(platformX + platformWidth, HEIGHT - 200, 0, 2000);
                double rewardX2Gap = Math.random() * (MAX_PLATFORM_GAP - MIN_PLATFORM_GAP) + MIN_PLATFORM_GAP;
                double poisonrewardgap = Math.random() * (MAX_PLATFORM_GAP - MIN_PLATFORM_GAP) + MIN_PLATFORM_GAP;
                rewardX2 = platformX + platformWidth + rewardX2Gap + 30+(nextPlatform.getX() - (platformX + platformWidth + rewardX2Gap));
                poisonrewardX = platformX + platformWidth + poisonrewardgap + 80+(nextPlatform.getX() - (platformX + platformWidth + poisonrewardgap));

            }


            reward2 reward2 = new reward2(rewardX2, 420);
            reward2s.add(reward2);

            PoisonReward poisonrew = new PoisonReward(poisonrewardX, 385);
            poisonrewards.add(poisonrew);
            // Adjust X positions and width for the next iteration with random gap
            double platformGap = Math.random() * (MAX_PLATFORM_GAP - MIN_PLATFORM_GAP) + MIN_PLATFORM_GAP;
            platformWidth = Math.random() * (platformWidthmax - platformWidthmin) + platformWidthmin;

            platformX += platformWidth + platformGap;
            rewardX += platformWidth + platformGap;
            poisonrewardX+=platformWidth + platformGap;
        }



//        platforms.add(new Platform(0, HEIGHT-200, 150, 2000));
//        platforms.add(new Platform(300, HEIGHT-200, 100, 2000));
//        platforms.add(new Platform(550, HEIGHT-200, 50, 2000));
//        platforms.add(new Platform(700, HEIGHT-200, 150, 2000));
//
////        platforms.add(createNewPlatform(0, 150));
//
//        rewards.add(new Reward(100, 385));
//        rewards.add(new Reward(350, 385));
//        rewards.add(new Reward(570, 385));
//        rewards.add(new Reward(740, 385));




    }
    private int getframwidth() {
        return 0;
    }
        private void update(double deltaTime) {
        stickHero.update(deltaTime, platforms.get(0),gc);

            List<Reward> rewardsToRemove = new ArrayList<>();
            List<reward2> reward2toremove=new ArrayList<>();
            List<PoisonReward> poisonrewardtoremove=new ArrayList<>();


            if(hasMovedToStickEnd&&!isCharacterOnPlatform){
                stickHero.fallCharacterTo(stickHero.getX(),500,gc);
            }

            for (Reward reward : rewards) {
                if (stickHero.intersects(reward) || stickHero.getX() > reward.getX()) {
                    playrewardMusic();
                    stickHero.collectReward(reward);
                    rewardsToRemove.add(reward);

                    score += 10; // Increase the score when a reward is collected
                    cherries++; // Increase the cherries for the reviving feature

                    Text perfectText = new Text("PERFECT!");
                    perfectText.setX(reward.getX()-20-cam.getX());
                    perfectText.setY(reward.getY() - 50); // Adjust the Y position as needed
                    perfectText.setFill(Color.GOLD); // Set text color to golden
                    perfectText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    // Apply fade-in and fade-out animation
                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5 ), perfectText);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1);

                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), perfectText);
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);

                    SequentialTransition fade = new SequentialTransition(fadeIn, fadeOut);
                    fade.play();

                    root.getChildren().add(perfectText); // Add text to the scene graph
                }
            }
            for (reward2 reward2s : reward2s) {
                if ( stickHero.intersects(reward2s) || stickHero.getX() < reward2s.getX()+5&& stickHero.getX() > reward2s.getX()-5 && isCharacterFlipped) {
                    playrewardMusic();
                    stickHero.collectreward2(reward2s);
                    reward2toremove.add(reward2s);

                    score += 10; // Increase the score when a reward is collected
                    cherries++; // Increase the cherries for the reviving feature
                }
            }
            for (PoisonReward poisonReward : poisonrewards) {
                if ( stickHero.intersects(poisonReward) || stickHero.getX() < poisonReward.getX()+5&& stickHero.getX() > poisonReward.getX()-5 && !StickHeroCharacter.isJumping&&!isCharacterFlipped) {
                    playerdeadMusic();
                    stickHero.collectreward2(poisonReward);
                    poisonrewardtoremove.add(poisonReward);
                    displayGameOverMessage();
                    playerdeadMusic();
                    bgmPlayer.stop();
                    isGameOver = true;
                    displayReviveBox();


                }
            }
//            if(isCharacterFlipped&&isCharacterOnPlatform){
//                isCharacterFlipped=false;
//                displayGameOverMessage();
//                playerdeadMusic();
//                bgmPlayer.stop();
//                isGameOver = true;
//                displayReviveBox();
//
//                stickHero.land();
//            }

// Remove the collected rewards outside the iteration
            rewards.removeAll(rewardsToRemove);
            reward2s.removeAll(reward2toremove);
            poisonrewards.removeAll(poisonrewardtoremove);

            if (!isFlipping && isStickStopped && !hasMovedToStickEnd) {
                double stickEnd = stickHero.getX() + stickHero.getStickLength();
                double characterWidth = stickHero.getCharacterImage().getWidth();
                double characterEnd = stickEnd + characterWidth; // Calculate character's end position

            // Ensure the character stays within the screen's boundaries
            if (characterEnd > 1000) {
//                StickHeroCharacter.x+=20*deltaTime;
                stickHero.moveCharacterTo(stickEnd,350,gc);
                hasMovedToStickEnd = true;
            }
            else {
//                StickHeroCharacter.x+=20*deltaTime;
                stickHero.moveCharacterTo(stickEnd,350,gc);
                hasMovedToStickEnd = true;

            }
                double characterSpeed = 20; // Adjust speed as needed

                for (Platform platform : platforms) {
                double platformStartX = platform.getX();
                double platformEndX = platformStartX + platform.getWidth();

                if (stickHero.getX() >= platformStartX-10 && stickHero.getX() <= platformEndX-10) {
                    isCharacterOnPlatform = true;
                    break;
                }
                else{
                    isCharacterOnPlatform=false;
                }
            }

            // If character's new position is not within any platform, it falls
            if ((!isCharacterOnPlatform &&hasMovedToStickEnd&&!stickHero.ismoving)) {
                stickHero.fallCharacterTo(stickHero.getX(),500,gc);// Set character back to its original x-coordinate
                displayGameOverMessage();
                playerdeadMusic();
                bgmPlayer.stop();


                isGameOver = true;
                    displayReviveBox();

                stickHero.land(); // Handle falling logic
            }
//
        }
            if (isStickStopped && hasMovedToStickEnd&&!isCharacterOnPlatform) {
                stickHero.fallCharacterTo(stickHero.getX(),500,gc);// Set character back to its original x-coordinate
            }

        if (isFlipping) {
            for (Reward reward : rewards) {
                if (stickHero.intersects(reward)) {
                    stickHero.collectReward(reward);
                    rewards.remove(reward);
                    score += 10; // Increase the score when a reward is collected
                    cherries++; // Increase the cherries for the reviving feature
                    break;
                }
            }
        }


        // Check if Stick Hero falls off the screen
        if (stickHero.getY() > HEIGHT) {
            if (cherries > 0) {
                cherries--; // Deduct cherries for reviving
                stickHero.revive();
            } else {
                // Game over logic (reset the game or show a game over screen)
                initializeGame();
                score = 0;
            }
        }


// Check if the character has moved to the end of the stick and reset the stick-related flags
        if (hasMovedToStickEnd && stickHero.getStickLength() == 0) {
            stickHero.resetStick(); // Reset the stick length to zero
            hasMovedToStickEnd = false;
            isStickStopped = false;
            isFlipping = false;
        }
    }


    private void displayReviveBox() {
        reviveBox = new VBox();
        reviveBox.setAlignment(Pos.CENTER);
        reviveBox.setSpacing(20);
        reviveBox.setPadding(new Insets(180,400,300,320));

        Text reviveText = new Text("Revive?");
        reviveText.setFill(Color.BLACK);
        reviveText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        reviveText.setLayoutX(300);
        reviveText.setLayoutY(300);



        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        reviveBox.getChildren().addAll(reviveText, yesButton, noButton);
        root.getChildren().add(reviveBox);

        yesButton.setOnAction(e -> {

            root.getChildren().remove(reviveBox);
            reviveBox.getChildren().removeAll(reviveText, yesButton, noButton);
            if (cherries > 1) {
                cherries-=2;
                score-=20;// Remove one cherry
                removeGameOverMessage();
                gameoverflag=false;
                reviveBox.getChildren().removeAll(reviveText, yesButton, noButton);
                root.getChildren().remove(reviveBox);
                restartGame();
            }
            else {
                Text notEnoughCandyText = new Text("Not enough candy for reviving");
                notEnoughCandyText.setFill(Color.RED);
                notEnoughCandyText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

                // Position the text message
                notEnoughCandyText.setLayoutX(300);
                notEnoughCandyText.setLayoutY(250);

                root.getChildren().add(notEnoughCandyText);

                // Fade out the message after a certain duration
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), notEnoughCandyText);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(event1 -> root.getChildren().remove(notEnoughCandyText));
                fadeOut.play();
                cherries=0;
                score=0;
                removeGameOverMessage();
                restartGame();
            }
        });

        noButton.setOnAction(e -> {
            removeGameOverMessage();
            cherries=0;
            score=0;
            reviveBox.getChildren().removeAll(reviveText, yesButton, noButton);
            root.getChildren().remove(reviveBox);
            restartGame();
        });

        // Display reviveBox in the root or scene
    }
    private void removeReviveBox() {
        root.getChildren().remove(reviveBox);
        reviveBox = null; // Set to null to indicate it's removed
    }
    public static boolean isCharacterFlipped = false;
    private void handleInput(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && StickHeroCharacter.isJumping) {
                StickHeroCharacter.isJumping=false;
                stickHero.setY(350);
            }
            if (event.getCode() == KeyCode.SHIFT&&!StickHeroCharacter.isJumping) {
                isCharacterFlipped = !isCharacterFlipped;
                stickHero.setFlipped(isCharacterFlipped);
            }
            if (event.getCode() == KeyCode.SPACE && !isFlipping&&!stickHero.ismoving&&!isCharacterFlipped) {
                stickHero.extend();
                isStickStopped = false; // Reset the flag when the stick is flipping
            }
            if (event.getCode() == KeyCode.ENTER&&!stickHero.ismoving) {
                stickHero.stopStick();
                isStickStopped = true;
            }
            if (event.getCode() == KeyCode.SPACE && stickHero.ismoving&&!isCharacterFlipped) {
                // Check if the character is on a platform before allowing it to jump
                stickHero.jump();
            }
            isFlipping = false;
        });
    }
    private void displayGameOverMessage() {
        // Code to display "Game Over" message on the screen
        Text gameOverText = new Text("Game Over!");
        gameOverText.setX(300);
        gameOverText.setY(100);
        gameOverText.setFill(Color.RED); // Set color as needed
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set font and size as needed

        root.getChildren().add(gameOverText);
    }
    private void restartGame() {
        // Code to reset the game state to its initial state
//        bgmPlayer.play();
        isCharacterFlipped = false;
        isGameOver = false;
        initializeGame();
        handleInput(scene);


    }
    private void removeGameOverMessage() {
        root.getChildren().removeIf(node -> node instanceof Text && ((Text) node).getText().equals("Game Over!"));
        gameoverflag=false;
    }
    private void render() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Render the background using the adjusted camera position
        gc.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT);

        // Render other game elements
        stickHero.renderCharacter(gc);
        stickHero.renderStick(gc);

        for (Platform platform : platforms) {
            platform.render(gc,- cam.getX());
        }

        for (Reward reward : rewards) {
            reward.render(gc, cam.getX());
        }

        for (reward2 reward2 : reward2s) {
            reward2.render(gc, cam.getX());
        }
        for (PoisonReward poisonrew : poisonrewards) {
            poisonrew.render(gc, cam.getX());
        }
        // Display score and cherries on the screen
        Font font = Font.font("Arial", FontWeight.BOLD, 14);
        gc.setFont(font);
        gc.setFill(Color.GOLD);
        gc.fillText("Score: " + score, 700, 30);
        Image cherriesImage = new Image("file:/C:/Users/shaur/Desktop/cherry.png"); // Replace "/path/to/cherries.png" with the actual path

        gc.drawImage(cherriesImage, 690, 30,50,50);
        gc.fillText(": " + cherries, 740, 60);

    }

//    private void render() {
//
//
//        gc.drawImage(backgroundImage ,0,0, 800, 700);
//        stickHero.renderCharacter(gc);
//        stickHero.renderStick(gc);
//
//
//        for (Platform platform : platforms) {
//            platform.render(gc);
//        }
//
//        for (Reward reward : rewards) {
//            reward.render(gc);
//        }
//        for (reward2 reward2 : reward2s) {
//            reward2.render(gc);
//        }
//        // Display score and cherries on the screen
//        Font font = Font.font("Arial", FontWeight.BOLD, 14);
//        gc.setFont(font);
//        gc.setFill(Color.GOLD);
//        gc.fillText("Score: " + score, 700, 30);
//        Image cherriesImage = new Image("file:/C:/Users/shaur/Desktop/cherry.png"); // Replace "/path/to/cherries.png" with the actual path
//
//        gc.drawImage(cherriesImage, 690, 30,50,50);
//        gc.fillText(": " + cherries, 740, 60);
//
//    }
//private void simulateKeyPress(KeyCode key) {
//    Robot robot = new Robot();
//    robot.keyPress(key);
//    robot.keyRelease(key);
//}

@Test
void testGetFrameWidth() {
    HelloApplication app = new HelloApplication();
    assertEquals(21600, app.getframwidth());
}
    @Test
    void testGetFrameHeight() {
        HelloApplication app = new HelloApplication();
        assertEquals(16200, app.getframeheight());
    }




}
