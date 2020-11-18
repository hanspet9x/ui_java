package controllers;

import views.ExplorerIcon;

import java.nio.file.Path;
import java.util.Map;

public interface OnFilesPicked {

    void picked(Map<Path, ExplorerIcon>files);

    void picked(Path path);
}
