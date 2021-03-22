package controllers;

import org.json.JSONArray;

import java.util.concurrent.CompletableFuture;

public interface OnFormOptions {

    public boolean isArray = true;
    public Object [] getObjects();
    public JSONArray getArrays();
    public CompletableFuture get();
}
