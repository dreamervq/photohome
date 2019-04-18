package drysister.itcast.cn.photohome.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

import drysister.itcast.cn.photohome.GlideImageLoader;
import drysister.itcast.cn.photohome.MainActivity;
import drysister.itcast.cn.photohome.R;

public class MyFragment1 extends Fragment {
    private Banner mBanner;
    private ArrayList<String> images;
    private ArrayList<String> imageTitle;


    public MyFragment1() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
         mBanner= (Banner) view.findViewById(R.id.frag1_banner);
       initData();
       initView();
        return view;

    }

    private void initView() {
        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR   显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(imageTitle);
        //设置轮播样式（没有标题默认为右边,有标题时默认左边）
        //可选样式:
        //Banner.LEFT   指示器居左
        //Banner.CENTER 指示器居中
        //Banner.RIGHT  指示器居右
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置是否允许手动滑动轮播图
        mBanner.setViewPagerIsScroll(true);
        //设置是否自动轮播（不设置则默认自动）
        mBanner.isAutoPlay(true);
        //设置轮播图片间隔时间（不设置默认为2000）
        mBanner.setDelayTime(2000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImages(images)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(getContext(), "你点了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();

    }

    private void initData() {
        images=new ArrayList<>();
        images.add("http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg");
        images.add("http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg");
        images.add("http://img.zcool.cn/community/01c8dc56e1428e6ac72531cbaa5f2c.jpg");
        images.add("http://img.zcool.cn/community/01fda356640b706ac725b2c8b99b08.jpg");
        images.add("http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg");
        images.add("http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg");
        //设置图片标题:自动对应
        imageTitle = new ArrayList<>();
        imageTitle.add("十大星级品牌联盟，全场2折起");
        imageTitle.add("嗨购5折不要停");
        imageTitle.add("双12趁现在");
        imageTitle.add("嗨购5折不要停，12.12趁现在");
        imageTitle.add("实打实大优惠");
        imageTitle.add("买到就是赚到");

    }


    @Override
    public void onDetach() {
        super.onDetach();

    }


}
