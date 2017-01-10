package com.taeven.anew;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by vaibhav on 2/1/17.
 */
public class mentor {
    private String name;
    private Bitmap image;

    public mentor(String name, Bitmap image)
    {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
