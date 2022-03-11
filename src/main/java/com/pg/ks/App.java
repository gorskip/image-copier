package com.pg.ks;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class App {

    public static void main(String[] args) throws IOException {

        Instant start = Instant.now();

        Config config = createConfig(args[0]);

        Processor processor = new ProcessorBuilder(config).build();

        processor.process();

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Process finished in: " + timeElapsed.getSeconds() + " seconds");
    }

    private static Config createConfig(String configPath) throws IOException {
        File configFile = new File(configPath);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(FileUtils.readFileToString(configFile, "UTF-8"), Config.class);
    }
}
