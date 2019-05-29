package drysister.itcast.cn.photohome.bean;

import cn.bmob.v3.BmobObject;

import java.io.Serializable;

public class PicList extends BmobObject implements Serializable {
    private String name;
    private String Title;
    private String Picurl;
    private User Author;
    private Integer Views;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPicurl() {
        return Picurl;
    }

    public void setPicurl(String picurl) {
        Picurl = picurl;
    }

    public User getAuthor() {
        return Author;
    }

    public void setAuthor(User author) {
        Author = author;
    }

    public Integer getViews() {
        return Views;
    }

    public void setViews(Integer views) {
        Views = views;
    }
}
