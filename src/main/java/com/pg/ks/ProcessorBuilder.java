package com.pg.ks;

import java.io.File;

public class ProcessorBuilder {

    private final Config config;

    public ProcessorBuilder(Config config) {
        this.config = config;
    }

    public Processor build() {
        return Processor.builder()
                .imageDirectory(imageDir())
                .csv(csvFile())
                .outputDirectory(outputDirectory())
                .bigFileExtension(bigFileExtension())
                .bigFileSuffix(bigFileSuffix())
                .smallFileExtension(smallFileExtension())
                .smallFileSuffix(smallFileSuffix())
                .headers(headers())
                .build();

    }


    private File csvFile() {
        return new File(config.getCsvFilePath());
    }

    private File imageDir() {
        return new File(config.getImageDirPath());
    }

    private File outputDirectory() {
        return new File(config.getOutputDirPath());
    }


    private Headers headers() {
        return Headers.builder()
                .imageNameColumn(config.getImageNameColumn())
                .columnOrder(config.getColumnOrder())
                .build();
    }

    private String bigFileExtension() {
        return config.getBigFileExtension();
    }

    private String bigFileSuffix() {
        return config.getBigFileSuffix();
    }

    private String smallFileExtension() {
        return config.getSmallFileExtension();
    }

    private String smallFileSuffix() {
        return config.getSmallFileSuffix();
    }


}
