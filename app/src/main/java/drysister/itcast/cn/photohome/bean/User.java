package drysister.itcast.cn.photohome.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String header;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
