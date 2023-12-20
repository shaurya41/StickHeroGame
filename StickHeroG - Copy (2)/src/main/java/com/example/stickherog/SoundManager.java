//package com.example.stickherog;
//
//
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import java.io.BufferedInputStream;
//import java.io.InputStream;
//
//
//
//
//
//
//    public void resumeBackground(){
//        background.setMicrosecondPosition(clipTime);
//        background.start();
//    }
//
//    public void pauseBackground(){
//        clipTime = background.getMicrosecondPosition();
//        background.stop();
//    }
//
//    public void restartBackground() {
//        clipTime = 0;
//        resumeBackground();
//    }
//
//    public static void playcoin() {
//        Clip clip = getClip(loadAudio("C:\\Users\\shaur\\Downloads\\coin-collect-retro-8-bit-sound-effect-145251.mp3"));
//        clip.start();
//
//    }
//
//    public void playCoin() {
//        Clip clip = getClip(loadAudio("coin"));
//        clip.start();
//
//    }
//
//    public void playFireball() {
//        Clip clip = getClip(loadAudio("fireball"));
//        clip.start();
//
//    }
//
//    public void playGameOver() {
//        Clip clip = getClip(loadAudio("gameOver"));
//        clip.start();
//
//    }
//
//    public void playStomp() {
//        Clip clip = getClip(loadAudio("stomp"));
//        clip.start();
//
//    }
//
//    public void playOneUp() {
//        Clip clip = getClip(loadAudio("oneUp"));
//        clip.start();
//
//    }
//
//    public void playSuperMushroom() {
//
//        Clip clip = getClip(loadAudio("superMushroom"));
//        clip.start();
//
//    }
//
//    public void playMarioDies() {
//
//        Clip clip = getClip(loadAudio("marioDies"));
//        clip.start();
//
//    }
//
//    public void playFireFlower() {
//
//    }
//}