package drysister.itcast.cn.photohome;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wildma.pictureselector.PictureSelector;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import drysister.itcast.cn.photohome.bean.User;
import drysister.itcast.cn.photohome.tool.SharedHelper;

public class MeInfoActivity extends AppCompatActivity {

    @BindView(R.id.meHeadeImg)
    RoundImageView meHeadeImg;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.email)
    EditText email;
    private SharedHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_info);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        sh = new SharedHelper(getApplicationContext());
        Map<String, String> data = sh.read();
        if (data.get("id") != null && data.get("id") != "") {
            BmobQuery<User> bmobQuery = new BmobQuery<>();
            bmobQuery.getObject(data.get("id"), new QueryListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        Glide.with(getApplicationContext()).
                                load(user.getHeader())
                                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                .into(meHeadeImg);
                        username.setText(user.getUsername());
                        if (user.getMobilePhoneNumber()!=""&&user.getMobilePhoneNumber()!=null){
                        phoneNumber.setText(user.getMobilePhoneNumber());
                        }else{
                            phoneNumber.setText("无");
                        }
                        email.setText(user.getEmail());
                    }
                }
            });
        } else {
            finshThis();
        }
    }

    @OnClick(R.id.backHome)
    public void onBackViewClicked() {
        finshThis();
    }

    public void finshThis() {
        finish();
        overridePendingTransition(R.anim.inact, R.anim.outact);
    }

    public void choosePictureFromCal() {
        PictureSelector.create(MeInfoActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                .selectPicture(true, 220, 220, 1, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                Log.i("yes---", "onActivityResult: " + picturePath);
                saveImgToBmob(picturePath);

            } else {
                Toast.makeText(this, "未选择图片~", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void saveImgToBmob(String imagPath) {
        final BmobFile bmobFile = new BmobFile(new File(imagPath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob scuess", "done: " + bmobFile.getFileUrl());
                    saveImagePatToUser(bmobFile.getFileUrl());
                } else {
                    Log.i("bmob error", "done: error");
                }
            }
        });
    }

    private void saveImagePatToUser(String imgurl) {
        Glide
                .with(getApplicationContext())
                .load(imgurl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(meHeadeImg);
        sh = new SharedHelper(getApplicationContext());
        Map<String, String> data = sh.read();
        User user = new User();
        user.setHeader(imgurl);
        user.update(data.get("id"), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(MeInfoActivity.this, "头像更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MeInfoActivity.this, "头像更新出错，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @OnClick(R.id.meHeadeImg)
    public void onHeadViewClicked() {
        choosePictureFromCal();
    }

    @OnClick(R.id.dosub)
    public void onSubmitViewClicked() {

       showMyDialog();
    }

    private void showMyDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MeInfoActivity.this);
        builder.setTitle("警告");
        builder.setMessage("确定提交修改？");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        upDateNormalUserinfo();
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

    public void upDateNormalUserinfo(){
        sh=new SharedHelper(getApplicationContext());
        Map<String,String> data=sh.read();
        User user=new User();
        user.setUsername(username.getText().toString().trim());
        user.setMobilePhoneNumber(phoneNumber.getText().toString().trim());
        user.setEmail(email.getText().toString().trim());
        user.update(data.get("id"), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    Toast.makeText(MeInfoActivity.this, "基础信息更新成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MeInfoActivity.this, "连接中断，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
