package drysister.itcast.cn.photohome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import drysister.itcast.cn.photohome.bean.News;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        Intent i = getIntent();
        tempBeanInfo = (News) i.getSerializableExtra("newsInfo");
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
        txtDate.setText(tempBeanInfo.getUpdatedAt().substring(0, 16)+"    by"+tempBeanInfo.getAuthor().getUsername());
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
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        allImagList.clear();
    }

    @OnClick(R.id.news_detail_like)
    public void onLikeViewClicked() {
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
