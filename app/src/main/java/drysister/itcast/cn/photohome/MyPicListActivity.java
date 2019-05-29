package drysister.itcast.cn.photohome;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import drysister.itcast.cn.photohome.adapter.MyPicrecycleadapter;
import drysister.itcast.cn.photohome.bean.PicList;
import drysister.itcast.cn.photohome.tool.SharedHelper;

public class MyPicListActivity extends AppCompatActivity {
    @BindView(R.id.mypiclistview)
    RecyclerView mypiclistview;
    private List<PicList> mPiclist;
    private SharedHelper sh;
    private Context context;
    private GetMyPiclist getMyPiclist;
private MyPicrecycleadapter myPicrecycleadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pic_list);
        ButterKnife.bind(this);
        context = MyPicListActivity.this;
        sh = new SharedHelper(context);
        getMyPiclist = new GetMyPiclist();
        getMyPiclist.execute();
    }

    public void initRecycleView() {
        if (mPiclist.size() == 0) {
            Toast.makeText(context, "网络错误，请重试..", Toast.LENGTH_SHORT).show();
            return;
        }
        myPicrecycleadapter=new MyPicrecycleadapter(mPiclist,context);
        RecyclerView.LayoutManager manager=new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        mypiclistview.setLayoutManager(manager);
        mypiclistview.setAdapter(myPicrecycleadapter);
        mypiclistview.setHasFixedSize(true);
        mypiclistview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @OnClick({R.id.backHome, R.id.addPiclist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backHome:
                finish();
                overridePendingTransition(R.anim.inact, R.anim.outact);
                break;
            case R.id.addPiclist:
                break;
        }
    }

    private class GetMyPiclist extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            Map<String, String> data = sh.read();
            String userid = data.get("id");
            final  String username=data.get("username");
            if (userid != null && userid != "") {
                BmobQuery<PicList> query = new BmobQuery<>();
                query.addWhereEqualTo("Author", userid);
                query.order("updateAt").findObjects(new FindListener<PicList>() {
                    @Override
                    public void done(List<PicList> list, BmobException e) {
                        if (e == null) {
                            mPiclist=new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                Log.i("my pic list", "done: "+list.get(i).getPicurl());
                                list.get(i).getAuthor().setUsername(username);
                                mPiclist.add(list.get(i));
                            }
                            //init list
                            initRecycleView();
                        } else {

                        }
                    }
                });
            }
            return null;
        }
    }
}
