package com.scratch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratch.engine.GameEngine;
import com.scratch.model.Config;
import com.scratch.model.GameResult;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ScratchGameApplication {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        String configPath = null;
        Double betAmount = null;


        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--config":
                    if (i + 1 < args.length) {
                        configPath = args[++i];
                    }
                    break;
                case "--betting-amount":
                    if (i + 1 < args.length) {
                        betAmount = Double.parseDouble(args[++i]);
                    }
                    break;
                default:

            }
        }


        if (betAmount == null) {
            System.err.println("Usage: java -jar scratch-game.jar [--config <path>] --betting-amount <amount>");
            return;
        }

        try {

            Config config;
            if (configPath != null) {

                config = mapper.readValue(new File(configPath), Config.class);
            } else {

                try (InputStream in = ScratchGameApplication.class
                        .getClassLoader()
                        .getResourceAsStream("config.json")) {
                    if (in == null) {
                        System.err.println("Default config.json not found on classpath!");
                        return;
                    }
                    config = mapper.readValue(in, Config.class);
                }
            }


            GameResult result = new GameEngine(config, betAmount).play();


            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(result)
            );

        } catch (IOException e) {
            System.err.println("Error loading config or running game: " + e.getMessage());
        }
    }
}
