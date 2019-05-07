package drysister.itcast.cn.photohome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import drysister.itcast.cn.photohome.bean.User;
import drysister.itcast.cn.photohome.fragment.MyFragment3;
import drysister.itcast.cn.photohome.tool.MD5;
import drysister.itcast.cn.photohome.tool.SharedHelper;

public class LoginActivity extends AppCompatActivity {
    SharedHelper sh;
    @BindView(R.id.loginName)
    EditText loginName;
    @BindView(R.id.loginPwd)
    EditText loginPwd;
    private Intent registIntent;
    @BindView(R.id.defheadImg)
    RoundImageView defheadImg;
private Handler postHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_login_defhead);
        defheadImg.setBitmap(bitmap);
        registIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        sh = new SharedHelper(getApplicationContext());
    }

    @OnClick(R.id.backHome)
    public void onBackViewClicked() {
        finish();
        overridePendingTransition(R.anim.inact, R.anim.outact);
    }

    private void gotoRegist() {
        startActivity(registIntent);
        overridePendingTransition(R.anim.inact, R.anim.outact);
    }

    @OnClick({R.id.linkToRegist, R.id.txtToRegist})
    public void onRegistViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linkToRegist:
                gotoRegist();
                break;
            case R.id.txtToRegist:
                gotoRegist();
                break;
        }
    }

    @OnClick(R.id.loginSub)
    public void onSubViewClicked() {
        if (loginName.getText() == null || loginName.getText().equals("")) {
            Toast.makeText(this, "用户名为空，请输入", Toast.LENGTH_SHORT).show();
            return;
        }
        if (loginPwd.getText() == null || loginPwd.getText().equals("")) {
            return;
        }
        BmobUser.loginByAccount(loginName.getText().toString(), MD5.getMD5(loginPwd.getText().toString()), new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "登录成功！welcome： "+user.getUsername(), Toast.LENGTH_SHORT).show();
                    clearAll();
                    sh.save(user.getObjectId(), user.getUsername());
                    postHandler=new Handler();
                    postHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Map<String,String> data=sh.read();
                            Log.i("username in sh", "run: "+data.get("username"));
                            Log.i("id in sh", "run: "+data.get("id"));
                            finish();
                        }
                    },1000);
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clearAll() {
        loginPwd.setText("");
        loginName.setText("");
    }
}
