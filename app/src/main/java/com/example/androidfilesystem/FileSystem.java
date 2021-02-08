package com.example.androidfilesystem;

import android.util.Log;

import java.io.Serializable;

public class FileSystem implements Serializable {
    private String fileName;
    private String filePath;
    private String extension;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
