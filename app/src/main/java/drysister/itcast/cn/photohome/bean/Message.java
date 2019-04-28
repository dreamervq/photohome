package drysister.itcast.cn.photohome.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class Message extends BmobObject {
    private String title;
    private BmobUser writer;
    private BmobFile pic;
private String url;

    public String getTitle() {
        return title;
    }

    public BmobUser getWriter() {
        return writer;
    }

    public BmobFile getPic() {
        return pic;
    }

    public String getUrl() {
        return url;
    }
}
