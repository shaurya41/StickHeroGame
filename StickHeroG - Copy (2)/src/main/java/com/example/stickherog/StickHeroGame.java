//package com.example.stickherog;
//
//import javafx.animation.AnimationTimer;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.input.KeyCode;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class StickHeroGame extends Application {
//
//
//
//    public static final double WIDTH = 5000;
//    public static final double HEIGHT = 5000;
//
//    private Pane root;
//    private Canvas canvas;
//    private GraphicsContext gc;
//
//    private StickHeroCharacter stickHero;
//    private List<Platform> platforms;
//    private List<Reward> rewards;
//    public ArrayList<Obstacle> obstacles;
//
//
//    private boolean isFlipping = false;
//    private int score = 0;
//    private int cherries = 0;
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Stick Hero Game");
//
//        root = new Pane();
//        Scene scene = new Scene(root, WIDTH, HEIGHT);
//        primaryStage.setScene(scene);
//
//        canvas = new Canvas(WIDTH, HEIGHT);
//        gc = canvas.getGraphicsContext2D();
//        root.getChildren().add(canvas);
//
//        initializeGame();
//
//        scene.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.SPACE && !isFlipping) {
//                isFlipping = true;
//                stickHero.flip();
//            }
//        });
//
//        new AnimationTimer() {
//            long lastTime = System.nanoTime();
//
//            @Override
//            public void handle(long now) {
//                double deltaTime = (now - lastTime) / 1e9;
//                update(deltaTime);
//                render();
//                lastTime = now;
//            }
//        }.start();
//
//        primaryStage.show();
//    }
//
//    // ... (previous code)
//
//    private void initializeGame() {
//        stickHero = new StickHeroCharacter(WIDTH / 2, HEIGHT - 50);
//        platforms = new ArrayList<>();
//        rewards = new ArrayList<>();
//
//        // Add initial platform
//        platforms.add(new Platform(0, HEIGHT - 20, WIDTH, 100));
//
//        // Add pillars
//        double pillarWidth = 50;
//        double pillarGap = 200;
//
//        for (double x = pillarGap; x < WIDTH; x += pillarGap) {
//            double randomPillarWidth = Math.random() * 50 + 50;
//            platforms.add(new Pillar(x, HEIGHT - 20, randomPillarWidth, 10000));
//        }
//
//        // Add initial reward
//        rewards.add(new Reward(WIDTH / 2, HEIGHT - 50));
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//    private void update(double deltaTime) {
//        stickHero.update(deltaTime, platforms.get(0));
//
//        if (isFlipping) {
//            for (Reward reward : rewards) {
//                if (stickHero.intersects(reward)) {
//                    stickHero.collectReward(reward);
//                    rewards.remove(reward);
//                    score += 10; // Increase the score when a reward is collected
//                    cherries++; // Increase the cherries for the reviving feature
//                    break;
//                }
//            }
//        }
//
//        // Check if Stick Hero lands on a platform
//        for (Platform platform : platforms) {
//            if (stickHero.isLanding(platform)) {
//                stickHero.land();
//                isFlipping = false;
//                break;
//            }
//        }
//
//        // Check if Stick Hero falls off the screen
//        if (stickHero.getY() > HEIGHT) {
//            if (cherries > 0) {
//                cherries--; // Deduct cherries for reviving
//                stickHero.revive();
//            } else {
//                // Game over logic (reset the game or show a game over screen)
//                initializeGame();
//                score = 0;
//            }
//        }
//    }
//
//    private void render() {
//        gc.clearRect(0, 0, WIDTH, HEIGHT);
//
//        for (Platform platform : platforms) {
//            platform.render(gc, cameraX); // Pass cameraX to adjust the platform's rendering
//        }
//
//        for (Reward reward : rewards) {
//            reward.render(gc, cameraX); // Pass cameraX to adjust the reward's rendering
//        }
//
//
//
//        // Display score and cherries on the screen
//        gc.setFill(Color.BLACK);
//        gc.fillText("Score: " + score, 10, 20);
//        gc.fillText("Cherries: " + cherries, 10, 40);
//    }
//
//}
//
