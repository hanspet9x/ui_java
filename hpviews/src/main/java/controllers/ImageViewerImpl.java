package controllers;

import java.io.IOException;
import java.util.List;

public interface ImageViewerImpl {

  /*  *//**
     * It displays the selected Images after being scaled.
     *//*
    void show();*/

    /**
     * It zooms the selected file.
     */
    void zoom();

    /**
     * It deletes the selected file.
     */
    void delete();

    /**
     * It shares and forward documents to email(s) and to System users.
     * It does this by showing a dialog to select if email or user. UI should be implement accordingly.
     */
    void share();

    void forward();
    /**
     * It shows the next file or image in the directory when invoked.
     */
    void next() throws IOException;

    /**
     * It shows the previous file or image in the directory when invoked.
     */
    void previous() throws IOException;

    /**
     * It detached the file's directory from the selected filename and scans the directory for more images.
     * It returns a list of image filenames found in this directory.
     * @return List<String>
     */
    List<String> listDirImages() throws IOException;

    /**
     * It resamples the file to make it fit in the image Viewer size.
     */
    void resample() throws IOException;

    /**
     * It rotates the Image in a clockwise direction.
     */
    void rotate();

    void download();
}
