package drysister.itcast.cn.photohome;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import drysister.itcast.cn.photohome.adapter.MyRecycleradapter;
import drysister.itcast.cn.photohome.bean.News;
import drysister.itcast.cn.photohome.fragment.MyFragment1;
import drysister.itcast.cn.photohome.tool.SharedHelper;

public class MyPostActivity extends AppCompatActivity {

    @BindView(R.id.mypostview)
    RecyclerView mypostview;
    @BindView(R.id.mypostrefreash)
    RefreshLayout myRefresh;
    private List<News> mlist;
    private MyRecycleradapter myRecycleradapter;
    private GetMyPost getMyPost;
    private SharedHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        ButterKnife.bind(this);
        getMyPost = new GetMyPost();
        getMyPost.execute();
        intitRefresh();
    }
    private void intitRefresh() {
        myRefresh.setRefreshHeader(new BezierRadarHeader(getBaseContext()));
        myRefresh.setRefreshFooter(new BallPulseFooter(getBaseContext()));
        myRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mlist.clear();
                getMyPost = new GetMyPost();
                getMyPost.execute();
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

    }
    private void initRecyclerView() {
        if (mlist.size() < 0) {
            Toast.makeText(this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        myRecycleradapter = new MyRecycleradapter(MyPostActivity.this, mlist);
        myRecycleradapter.setOnClickListener(new MyRecycleradapter.OnClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                Intent intentForDetail = new Intent(MyPostActivity.this, NewsDetailActivity.class);
                Bundle db = new Bundle();
                db.putInt("type", 1);
                db.putSerializable("newsInfo", mlist.get(position));
                intentForDetail.putExtras(db);
                startActivity(intentForDetail);
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        mypostview.setLayoutManager(linearLayoutManager);
        mypostview.setAdapter(myRecycleradapter);
        mypostview.setHasFixedSize(true);
        mypostview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private class GetMyPost extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            sh = new SharedHelper(getApplicationContext());
            Map<String, String> data = sh.read();
            String userid = data.get("id");
            if (userid != null && userid != "") {
                BmobQuery<News> query = new BmobQuery<>();
                query.addWhereEqualTo("author", userid);
                query.order("updateAt").findObjects(new FindListener<News>() {
                    @Override
                    public void done(List<News> list, BmobException e) {
                        if (e == null) {
                            mlist = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                mlist.add(list.get(i));
                            }
                            initRecyclerView();
                            Log.i("my bmob", "done: " + list.get(0).getTitle());
                        } else {
                            Log.i("my bm", "done: " + e.getMessage());
                        }
                    }
                });
            }


            return null;
        }
    }

    @OnClick({R.id.backHome, R.id.addnewpost})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backHome:
                finish();
                overridePendingTransition(R.anim.inact, R.anim.outact);
                break;
            case R.id.addnewpost:
                startActivity(new Intent(MyPostActivity.this, releaseActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getMyPost = new GetMyPost();
        getMyPost.execute();
    }
}
