package com.pg.ks;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.Builder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Builder
public class Processor {

    private File imageDirectory;
    private File csv;
    private File outputDirectory;
    private Headers headers;
    private String bigFileExtension;
    private String bigFileSuffix;
    private String smallFileExtension;
    private String smallFileSuffix;

    public Processor(File imageDirectory, File csv, File outputDirectory, Headers headers, String bigFileExtension, String bigFileSuffix, String smallFileExtension, String smallFileSuffix) {
        this.imageDirectory = imageDirectory;
        this.csv = csv;
        this.outputDirectory = outputDirectory;
        this.headers = headers;
        this.bigFileExtension = bigFileExtension;
        this.bigFileSuffix = bigFileSuffix;
        this.smallFileExtension = smallFileExtension;
        this.smallFileSuffix = smallFileSuffix;
    }

    public void process() {
        DirProcessor dirProcessor = new DirProcessor(imageDirectory, Arrays.asList(bigFileExtension, smallFileExtension), false);
        List<File> images = dirProcessor.getImages(bigFileSuffix, smallFileSuffix);
        if(images.isEmpty()) {
            System.out.println("No images to be processed. Exiting");
            return;
        }
        List<String[]> csvData = readCsv(csv);

        csvData.forEach(row -> {
            String imageNameColumn = row[headers.getImageNameColumn()];
            if(StringUtils.isNotEmpty(imageNameColumn)) {
                String fileName = headers.getColumnOrder().stream()
                        .map(index -> row[index].trim())
                        .collect(Collectors.joining("_"));

                createNewFiles(imageNameColumn, images, fileName);
            } else {
                System.out.println("Omitting row: " + row);
            }
        });

    }

    public void createNewFiles(String imageName, List<File> images, String fileName) {
        createNewImage(imageName, images, fileName, bigFileExtension, bigFileSuffix);
        createNewImage(imageName, images, fileName, smallFileExtension, smallFileSuffix);
    }

    private void createNewImage(String imageName, List<File> images, String newFileName, String extension, String suffix) {
        String newFileNameFull = cleanFileName(newFileName+suffix+ "." + extension);
        try {
            File file = getImage(imageName, extension, suffix, images);
            FileUtils.copyFile(file, new File(outputDirectory.getAbsolutePath()+ "/ " + newFileNameFull));
        } catch(NoSuchElementException e) {
            System.out.println("Cannot find image with name: [" + imageName + "] extension: [" + extension + "] suffix: [" + suffix + "]");
        } catch(IOException e) {
            throw new RuntimeException("Cannot create new file: [" + newFileNameFull + "]", e);
        }
    }

    private String cleanFileName(String fileName) {
        return fileName.replaceAll("[\\\\/:*?\"<>|]", "");
    }

    public File getImage(String key, String extension, String suffix, List<File> images) {
        return images.stream()
                .filter(image -> image.getName().contains(key) && image.getName().contains(extension) && image.getName().contains(suffix))
                .findFirst()
                .get();
    }

    private List<String[]> readCsv(File csvFile) {
        try {
            return new CSVReaderBuilder(new FileReader(csv))
                    .withSkipLines(1)
                    .build()
                    .readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Cannot read csv file: " + csv.getAbsolutePath(), e);
        }
    }

}
