package drysister.itcast.cn.photohome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import drysister.itcast.cn.photohome.bean.User;
import drysister.itcast.cn.photohome.tool.MD5;

public class RegisterActivity extends AppCompatActivity {
private Handler postHandler;
    @BindView(R.id.registUsername)
    EditText registUsername;
    @BindView(R.id.registPasswd)
    EditText registPasswd;
    @BindView(R.id.registPasswdagin)
    EditText registPasswdagin;
    @BindView(R.id.registemail)
    EditText registemail;
private final String defHeadUrl="http://bmob-cdn-24966.b0.upaiyun.com/2019/05/07/839cf7f640de96e4808002527585fe7e.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.backHome, R.id.registsub, R.id.txttologin})
    public void onFunViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backHome:
                killThis();
                break;
            case R.id.registsub:
                registByAccount();
                break;
            case R.id.txttologin:
                killThis();
                break;
        }
    }

    private void registByAccount() {
        if (registUsername.getText() == null || registUsername.getText().toString().equals("")) {
            return;
        }
        if (registPasswd.getText() == null || registPasswd.getText().toString().equals("")) {
            return;
        }
        if (registPasswdagin.getText().toString() == null || registPasswdagin.getText().toString().equals("")) {
            return;
        }
        if (registemail.getText() == null || registemail.getText().toString().equals("")) {
            return;
        }
        String pattern="\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
        if (!registPasswdagin.getText().toString().equals(registPasswd.getText().toString())){
            Toast.makeText(this, "密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!registemail.getText().toString().matches(pattern)){
            Toast.makeText(this, "邮箱错误，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        final User user=new User();
        user.setUsername(registUsername.getText().toString());
        user.setPassword(MD5.getMD5(registPasswd.getText().toString()));
        user.setEmail(registemail.getText().toString());
        user.setHeader(defHeadUrl);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                    Toast.makeText(RegisterActivity.this, "注册成功，"+user.getUsername()+"  请登录吧", Toast.LENGTH_SHORT).show();
                    postHandler=new Handler();
                    postHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            killThis();
                        }
                    },1000);

                }else{
                    Toast.makeText(RegisterActivity.this, "注册失败，请重试！", Toast.LENGTH_SHORT).show();
                    clearAll();
                }
            }
        });
    }
    private void killThis(){
        finish();
        overridePendingTransition(R.anim.inact, R.anim.outact);
    }
    private void clearAll(){
        registemail.setText("");
        registPasswdagin.setText("");
        registPasswd.setText("");
    }
}
