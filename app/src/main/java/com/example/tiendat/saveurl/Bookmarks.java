package com.example.tiendat.saveurl;

/**
 * Created by Tiendat on 1/12/2017.
 */

public class Bookmarks {
    private String Url;
    private String Name;
    private String Image;

    public Bookmarks(){

    }

    public Bookmarks(String url, String name, String image) {
        Url = url;
        Name = name;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
