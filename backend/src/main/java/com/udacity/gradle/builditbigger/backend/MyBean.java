package com.udacity.gradle.builditbigger.backend;

import com.example.jokegenerator.JokeGenerator;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    public String getData() {
        JokeGenerator jokeGenerator = new JokeGenerator();
        return jokeGenerator.getJoke();
    }
}