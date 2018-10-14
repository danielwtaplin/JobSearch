package com.activities.dwtaplin.jobsearchfinal.components;

import android.net.Uri;

import java.io.File;

public class Document {
    private String fileName;
    private Uri fileUri;
    private File file;
    private Object tag;
    public Document(Uri uri, String fileName, Object tag){
        fileUri = uri;
        this.fileName = fileName;
        this.tag = tag;
    }
    public Document(File file, String fileName, Object tag){
        this.file = file;
        this.fileName = fileName;
        this.tag = tag;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public File getFile() {
        return file;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
