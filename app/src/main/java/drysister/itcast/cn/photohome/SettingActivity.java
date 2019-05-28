package drysister.itcast.cn.photohome;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import drysister.itcast.cn.photohome.tool.SharedHelper;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.loginOut)
    Button loginOut;
    @BindView(R.id.settingClearTxt)
    TextView settingClearTxt;
    private SharedHelper sharedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        notifyUserIsLogin();
    }

    private void notifyUserIsLogin() {
        sharedHelper = new SharedHelper(getApplicationContext());
        Map<String, String> data = sharedHelper.read();
        if (data.get("id") != null && data.get("id") != "") {
            loginOut.setEnabled(true);
            loginOut.setTextColor(Color.RED);
        } else {

            loginOut.setEnabled(false);
            loginOut.setTextColor(Color.GREEN);
        }
    }

    @OnClick({R.id.backHome, R.id.loginOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backHome:
                finish();
                overridePendingTransition(R.anim.inact, R.anim.outact);
                break;
            case R.id.loginOut:
                clearUserSh();
                break;
        }
    }

    private void clearUserSh() {
        Map<String, String> data = sharedHelper.read();
        if (data.get("id") != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            builder.setTitle("确定退出登录？");
            builder.setMessage("退出后将不能使用vip服务！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sharedHelper.clearAllInfo();
                    Map<String, String> data = sharedHelper.read();
                    notifyUserIsLogin();
                    Toast.makeText(SettingActivity.this, "用户退出，登录享受更多服务", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.show();
        }
    }

    @OnClick(R.id.settingClear)
    public void onClearViewClicked() {
settingClearTxt.setText("0.0M");
        Toast.makeText(this, "已清理缓存", Toast.LENGTH_SHORT).show();
    }
}
