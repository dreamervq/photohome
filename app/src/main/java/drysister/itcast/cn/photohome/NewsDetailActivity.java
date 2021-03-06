package drysister.itcast.cn.photohome;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import drysister.itcast.cn.photohome.bean.News;
import drysister.itcast.cn.photohome.tool.SharedHelper;

public class NewsDetailActivity extends AppCompatActivity {
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtContent)
    TextView txtContent;
    @BindView(R.id.listCotent)
    LinearLayout listCotent;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.img5)
    ImageView img5;
    @BindView(R.id.img6)
    ImageView img6;
    @BindView(R.id.news_detail_like)
    CheckBox newsDetailLike;
    private News tempBeanInfo;
    @BindView(R.id.newInfotext)
    TextView newInfotext;
    @BindView(R.id.txt_newsbar)
    TextView txtNewsbar;
    @BindView(R.id.info_list)
    ScrollView infoList;
    private List<ImageView> allImagList;
    private Context context;
    private int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        Intent i = getIntent();
        Bundle bd = i.getExtras();
        n = bd.getInt("type");
        if (n == 1) {
            Drawable drawable = getResources().getDrawable(R.drawable.delete_item);
            newsDetailLike.setBackground(drawable);

        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.like_item);
            newsDetailLike.setBackground(drawable);
        }
        tempBeanInfo = (News) bd.getSerializable("newsInfo");
        context = NewsDetailActivity.this;
        initData();
    }

    private void initData() {
        //init image list
        allImagList = new ArrayList<>();
        allImagList.add(img1);
        allImagList.add(img2);
        allImagList.add(img3);
        allImagList.add(img4);
        allImagList.add(img5);
        allImagList.add(img6);

        //author name
        if (tempBeanInfo.getAuthor().getUsername() != null) {
            txtNewsbar.setText(tempBeanInfo.getAuthor().getUsername() + " 的图记");
        }
        //set tilte
        if (tempBeanInfo.getTitle() != null) {
            newInfotext.setText(tempBeanInfo.getTitle());
        }
        //set date and author
        if (n==1){
            SharedHelper sharedHelper=new SharedHelper(context);
            Map<String,String> data=sharedHelper.read();
            txtDate.setText(tempBeanInfo.getUpdatedAt().substring(0, 16) + "    by_  " + data.get("username"));
        }else{
            txtDate.setText(tempBeanInfo.getUpdatedAt().substring(0, 16) + "    by_  " + tempBeanInfo.getAuthor().getUsername());
        }

        //set content
        if (tempBeanInfo.getBodytxt() != null) {
            txtContent.setText("\n    " + tempBeanInfo.getBodytxt() + "\n");
        } else {
            txtContent.setText("这个人很懒，什么都没留下、");
        }
        //setimage

        for (int i = 0; i < tempBeanInfo.getBodypic().size(); i++) {
            Glide.with(context)
                    .load(tempBeanInfo.getBodypic().get(i))
                    .into(allImagList.get(i));

        }

    }

    @OnClick(R.id.backHome)
    public void onBackViewClicked() {
        myfinish();
    }

    public void myfinish() {
        finish();
        overridePendingTransition(R.anim.inact, R.anim.outact);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        allImagList.clear();
    }

    @OnClick(R.id.news_detail_like)
    public void onLikeViewClicked() {
        if (n == 1) {
            deletePostDialog();

        } else {

            newsDetailLike.setEnabled(false);
            String post = tempBeanInfo.getObjectId();
            News tempNews = new News();
            tempNews.setHots(tempBeanInfo.getHots() + 1);
            tempNews.update(post, new UpdateListener() {
                @Override
                public void done(BmobException e) {

                }
            });
        }
    }
private void deletePostDialog(){
    AlertDialog.Builder builder = new AlertDialog.Builder(NewsDetailActivity.this);
    builder.setTitle("确定删除？");
    builder.setMessage("删除后将图片资源和点赞等一并删除！");
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          deleteThisPost();
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
    private void deleteThisPost() {
        News tempdelte = new News();
        tempdelte.setObjectId(tempBeanInfo.getObjectId());
        tempdelte.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "已删除..." + tempBeanInfo.getObjectId(), Toast.LENGTH_SHORT).show();
                    myfinish();
                } else {
                    Toast.makeText(context, "网络连接错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
