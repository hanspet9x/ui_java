package controllers;

import containers.Card;
import containers.Flex;
import views.ImageView;

import java.io.IOException;

public interface PDFViewerImpl {

    void next() throws IOException;

    void previous() throws IOException;

    Flex createFlexThumbNail(int index) throws IOException;

    ImageView createThumbNail(int index) throws IOException;

    void showImage(int index) throws IOException;

    void scrollThumbNail(int index);

    void download();

    void delete();

    void share();

    void forward();


}
