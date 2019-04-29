package drysister.itcast.cn.photohome.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class News extends BmobObject {
    private List<String> comment;
    private String title;
    private String mainpic;
    private BmobUser author;
    private Integer hots;
    private String bodytxt;
    private List<String> bodypic;
    private String types;

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public BmobUser getAuthor() {
        return author;
    }

    public void setAuthor(BmobUser author) {
        this.author = author;
    }

    public Integer getHots() {
        return hots;
    }

    public void setHots(Integer hots) {
        this.hots = hots;
    }

    public String getBodytxt() {
        return bodytxt;
    }

    public void setBodytxt(String bodytxt) {
        this.bodytxt = bodytxt;
    }


    public String getMainpic() {
        return mainpic;
    }

    public void setMainpic(String mainpic) {
        this.mainpic = mainpic;
    }

    public List<String> getBodypic() {
        return bodypic;
    }

    public void setBodypic(List<String> bodypic) {
        this.bodypic = bodypic;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
