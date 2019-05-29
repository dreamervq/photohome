package drysister.itcast.cn.photohome.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import drysister.itcast.cn.photohome.ActivityBanner;
import drysister.itcast.cn.photohome.GlideImageLoader;
import drysister.itcast.cn.photohome.NewsDetailActivity;
import drysister.itcast.cn.photohome.R;
import drysister.itcast.cn.photohome.adapter.MylistAdapter;
import drysister.itcast.cn.photohome.bean.Message;
import drysister.itcast.cn.photohome.bean.News;
import drysister.itcast.cn.photohome.view.NestedView;


public class
MyFragment1 extends Fragment {

    private NestedView listViewid;
    private Context mContext;
    Unbinder unbinder;
    ArrayList<Bitmap> mPicBit;
    private Banner mBanner;
    private ArrayList<String> images;
    private ArrayList<String> imageTitle;
    private ArrayList<String> bannerUrl;
    private RefreshLayout myRefresh;
    private LinkedList<News> allNews;
    private MylistAdapter mAdapter;
    private int newspage = 0;
    NewsTask newsTask;

    public MyFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        mBanner = (Banner) view.findViewById(R.id.frag1_banner);
        listViewid = (NestedView) view.findViewById(R.id.listViewid);
        myRefresh = (RefreshLayout) view.findViewById(R.id.refresh_layout);
        initData();
        intitRefresh();
        initItemClickEvent();
        getNews();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
//init news detail click listener
    private void initItemClickEvent() {
        listViewid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentForDetail=new Intent(getActivity(), NewsDetailActivity.class);
                Bundle db=new Bundle();
                db.putInt("type",3);
                db.putSerializable("newsInfo",allNews.get(position));
                intentForDetail.putExtras(db);
                startActivity(intentForDetail);
            }
        });
    }

    //method get info of list from net
    private void getNews() {
        allNews = new LinkedList<News>();
        newsTask = new NewsTask();
        newsTask.execute();
    }

    private void intitRefresh() {
        myRefresh.setRefreshHeader(new BezierRadarHeader(getContext()));
        myRefresh.setRefreshFooter(new BallPulseFooter(getContext()));
        myRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                newspage = 0;
                allNews.clear();
                newsTask = new NewsTask();
                newsTask.execute();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myRefresh.finishRefresh();
                        myRefresh.setEnableLoadMore(true);
                    }
                }, 1100);
            }
        });
        myRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                newspage++;
                newsTask = new NewsTask();
                newsTask.execute();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myRefresh.finishLoadMore();
                        myRefresh.setEnableLoadMore(true);
                    }
                }, 1200);
            }
        });
        myRefresh.setEnableLoadMore(true);

    }

    //init banner view
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
        mBanner.setDelayTime(3000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImages(images)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Intent it1 = new Intent(getActivity(), ActivityBanner.class);
                        it1.putExtra("bannerUrl", bannerUrl.get(position));
                        getActivity().overridePendingTransition(R.anim.inact, R.anim.outact);
                        startActivity(it1);

                        //Toast.makeText(getContext(), "你点了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();

    }

    //init private data
    private void initData() {
        mContext = getContext();
        images = new ArrayList<>();
        bannerUrl = new ArrayList<>();
        //设置图片标题:自动对应
        imageTitle = new ArrayList<>();
        new BannerTask().execute();
        mPicBit = new ArrayList<>();

    }


    //adjust height of list
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            newspage = 0;
            allNews.clear();
            newsTask = new NewsTask();
            newsTask.execute();
            Toast.makeText(mContext, "正在加载...", Toast.LENGTH_SHORT).show();
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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

    //asynctask for banner data
    public class BannerTask extends AsyncTask<Void, Void, ArrayList<Message>> {

        @Override
        protected ArrayList<Message> doInBackground(Void... params) {
            BmobQuery<Message> query = new BmobQuery<>();
            query.findObjects(new FindListener<Message>() {
                @Override
                public void done(List<Message> list, BmobException e) {
                    int i = 0;
                    if (e == null) {
                        while (list.get(i) != null) {
                            images.add(list.get(i).getPic().getUrl());
                            imageTitle.add(list.get(i).getTitle());
                            bannerUrl.add(list.get(i).getUrl());
                            i++;
                        }
                    } else {
                        initView();

                    }
                }
            });
            return null;
        }

    }

    //asynctask for news data
    public class NewsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            BmobQuery<News> query = new BmobQuery<>();
            //query data
            query.include("author[username]");
            query.setLimit(5).setSkip(5 * newspage).order("-hots").findObjects(new FindListener<News>() {
                @Override
                public void done(List<News> list, BmobException e) {
                    if (e == null) {
                        if (list.size() == 0) {
                            Toast.makeText(mContext, "没有更多了！！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (newspage > 0) {
                            Log.i("load more", "done: list size" + list.size());
                            //mAdapter.addItem(list);
                        }
                        int i = 0;
                        while (i < list.size()) {
                            allNews.add(list.get(i));
                            i++;
                        }
                    } else {
                    }
                    Log.i("allbmob", Integer.toString(allNews.size()));

                    if (newspage == 0) {

                        mAdapter = new MylistAdapter(allNews, mContext);
                        listViewid.setAdapter(mAdapter);

                    }
                    setListViewHeightBasedOnChildren(listViewid);
                }
            });
            //get pic bitmap
//end get pic bitmap

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
        }
    }//end task


}
