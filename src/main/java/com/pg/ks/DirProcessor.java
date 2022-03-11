package com.pg.ks;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DirProcessor {

    private final File dir;
    private final List<String> fileExtensions;
    private final boolean recursive;

    public DirProcessor(File dir, List<String> fileExtensions, boolean recursive) {
        this.dir = dir;
        this.fileExtensions = fileExtensions;
        this.recursive = recursive;
    }

    public List<File> getImages(String... nameFilters) {
        return new ArrayList<>(
                FileUtils.listFiles(dir, fileExtensions.toArray(new String[]{}), recursive)).stream()
                .filter(file -> StringUtils.containsAny(file.getName(), nameFilters))
                .collect(Collectors.toList());
    }
}
