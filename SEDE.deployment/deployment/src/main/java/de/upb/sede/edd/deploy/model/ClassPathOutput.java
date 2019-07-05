package de.upb.sede.edd.deploy.model;

import de.upb.sede.edd.deploy.AcrPath;

import java.util.ArrayList;
import java.util.List;

public class ClassPathOutput {


    public static final String OUTPUT_TYPE = "classpath";

    private List<AcrPath> files = new ArrayList<>();

    private List<AcrPath> jarDirs = new ArrayList<>();

    // TODO support file patterns
    /*
        "filePatterns" : [
          {
              "dir": "[SEDE.services:repo]/deploy/",
              "pattern" : "SEDE.*\\.jar"
          }
        ]
     */
    // private List<FilePattern> patterns = new ArrayList<>();

    public List<AcrPath> getFiles() {
        return files;
    }

    public void setFiles(List<AcrPath> files) {
        this.files = files;
    }

    public List<AcrPath> getJarDirs() {
        return jarDirs;
    }

    public void setJarDirs(List<AcrPath> jarDirs) {
        this.jarDirs = jarDirs;
    }
}

