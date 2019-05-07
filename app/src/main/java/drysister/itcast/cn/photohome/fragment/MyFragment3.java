package drysister.itcast.cn.photohome.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import drysister.itcast.cn.photohome.LoginActivity;
import drysister.itcast.cn.photohome.R;
import drysister.itcast.cn.photohome.RoundImageView;
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
        RoundImageView imageView=(RoundImageView)view.findViewById(R.id.myRoundImg);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.defhead);
        imageView.setBitmap(bitmap);
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
        startActivity(loginIntent);
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedHelper sh = new SharedHelper(getContext());
        Map<String, String> data = sh.read();
        if (data.get("id") != null) {
            if (meUser == null) {
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject(data.get("id"), new QueryListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            meUser.setObjectId(user.getObjectId());
                            meUsername.setText(user.getUsername());
                            meEmail.setText(user.getEmail());
                            Glide.with(getContext()).load(user.getHeader()).centerCrop().into(myRoundImg);
                        } else {

                        }
                    }
                });
            }
        } else {
                meUser=null;
                meUsername.setText("请立即登录");
                meEmail.setText("登录后享受更多服务");
        }
    }

}
