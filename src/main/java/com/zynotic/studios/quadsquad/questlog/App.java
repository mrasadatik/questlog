package com.zynotic.studios.quadsquad.questlog;

import javafx.application.Application;

import com.zynotic.studios.quadsquad.questlog.scene.SplashScreenPreloader;

public class App {

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", SplashScreenPreloader.class.getCanonicalName());
        Application.launch(QuestLog.class, args);
    }

}