package com.travelguide.travelguide.model;

import java.io.Serializable;

public class ItemMedia implements Serializable {

    private int type;
    //if type = 1 video
    //if type = 0 photo

    private String path;

    public ItemMedia(int type, String path) {
        this.type = type;
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
