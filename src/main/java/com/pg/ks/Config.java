package com.pg.ks;

import lombok.Data;

import java.util.List;

@Data
public class Config {

    private String csvFilePath;
    private String imageDirPath;
    private String outputDirPath;

    private int imageNameColumn;
    private List<Integer> columnOrder;

    private String bigFileExtension;
    private String bigFileSuffix;

    private String smallFileExtension;
    private String smallFileSuffix;

}
