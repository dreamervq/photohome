package drysister.itcast.cn.photohome.tool;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SharedHelper {
    private Context context;

    public SharedHelper() {
    }

    public SharedHelper(Context context) {
        this.context = context;
    }
    public void save(String id,String username){
        SharedPreferences sp=context.getSharedPreferences("ifsslogininfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("id",id);
        editor.putString("username",username);
        editor.commit();
    }
    public Map<String,String> read(){
        Map<String,String> data=new HashMap<String,String>();
        SharedPreferences sp=context.getSharedPreferences("ifsslogininfo",Context.MODE_PRIVATE);
        data.put("id",sp.getString("id",""));
        data.put("username",sp.getString("username",""));
        return  data;
    }
}
