package controllers;

import org.json.JSONArray;
import services.UITreeService;

public interface OnUITreeExplorerUpdate {
    void updateTreeExplorer(String data, UITreeService.TreeDataSource source);
    void updateTreeExplorer(JSONArray data, UITreeService.TreeDataSource source);
}
