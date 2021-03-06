package drysister.itcast.cn.photohome.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import drysister.itcast.cn.photohome.LoginActivity;
import drysister.itcast.cn.photohome.MeInfoActivity;
import drysister.itcast.cn.photohome.MyPicListActivity;
import drysister.itcast.cn.photohome.MyPostActivity;
import drysister.itcast.cn.photohome.R;
import drysister.itcast.cn.photohome.RoundImageView;
import drysister.itcast.cn.photohome.SettingActivity;
import drysister.itcast.cn.photohome.bean.User;
import drysister.itcast.cn.photohome.tool.SharedHelper;


public class MyFragment3 extends Fragment {
    @BindView(R.id.meEmail)
    TextView meEmail;
    @BindView(R.id.myRoundImg)
    RoundImageView myRoundImg;
    private User meUser;
    Unbinder unbinder;
    @BindView(R.id.meUsername)
    TextView meUsername;

    private Intent loginIntent;

    public MyFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
//        RoundImageView imageView = (RoundImageView) view.findViewById(R.id.myRoundImg);
        //     Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.defhead);
        //   myRoundImg.setBitmap(bitmap);
        initData();
        loginIntent = new Intent(getActivity(), LoginActivity.class);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initData() {

    }


    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.me_login)
    public void onLoginViewClicked() {
        SharedHelper sh = new SharedHelper(getContext());
        Map<String, String> data = sh.read();
        if (data.get("id") != null && data.get("id") != "") {
            getUserInfo();
            startActivity(new Intent(getActivity(), MeInfoActivity.class));
        } else {
            startActivity(loginIntent);
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    public void getUserInfo() {
        SharedHelper sh = new SharedHelper(getContext());
        Map<String, String> data = sh.read();
        if (data.get("id") != "") {
            if (meUser == null) {
                Log.i("userinfo is null", "getUserInfo: ");
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject(data.get("id"), new QueryListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            meUsername.setText(user.getUsername());
                            meEmail.setText(user.getEmail());
                            //设置图片圆角角度
                            Glide.with(getContext()).
                                    load(user.getHeader())
                                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                    .into(myRoundImg);
                            Log.i("hi", "done: " + user.getUsername());
                        } else {
                            Log.i("error in bmob", "done: " + e.getMessage());
                        }
                    }
                });
            } else {

            }
        } else {
            meUser = null;
            meUsername.setText("请立即登录");
            meEmail.setText("登录后享受更多服务");
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.img_login_defhead);
            myRoundImg.setBitmap(bitmap);
        }
    }

    @OnClick({R.id.meFunPic, R.id.meFunMypos, R.id.meFunLike, R.id.meFunSetting})
    public void onFuncViewClicked(View view) {
        switch (view.getId()) {
            case R.id.meFunPic:
                openMyPicList();
                break;
            case R.id.meFunMypos:
                openMyPost();
                break;
            case R.id.meFunLike:
                break;
            case R.id.meFunSetting:
                openSetting();
                break;
        }
    }

    private void openMyPicList() {
        if (IsUserLogin()){
            startActivity(new Intent(getActivity(), MyPicListActivity.class));
        }
    }

    private void openMyPost() {
        if (IsUserLogin()){
        startActivity(new Intent(getActivity(), MyPostActivity.class));
        }
        else {
            Toast.makeText(getActivity(), "请登录...", Toast.LENGTH_SHORT).show();
        }
    }
public Boolean IsUserLogin(){
    SharedHelper sh=new SharedHelper(getContext());
    Map<String,String> data=sh.read();
    if (data.get("id")!=null&&data.get("id")!=""){
        return true;
       }
       return  false;
}
    private void openSetting() {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }
}
