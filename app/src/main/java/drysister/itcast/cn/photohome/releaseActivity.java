package drysister.itcast.cn.photohome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wildma.pictureselector.PictureSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import drysister.itcast.cn.photohome.bean.News;

public class releaseActivity extends AppCompatActivity {

    @BindView(R.id.newTitle)
    EditText newTitle;
    @BindView(R.id.newContent)
    EditText newContent;
    @BindView(R.id.contentPic1)
    ImageView contentPic1;
    @BindView(R.id.contentPic2)
    ImageView contentPic2;
    @BindView(R.id.contentPic3)
    ImageView contentPic3;
    @BindView(R.id.contentPic4)
    ImageView contentPic4;
    @BindView(R.id.contentPic5)
    ImageView contentPic5;
    @BindView(R.id.contentPic6)
    ImageView contentPic6;
    private List<String> picUrlList;
    private List<ImageView> allImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        ButterKnife.bind(this);
        picUrlList = new ArrayList<>();
        allImages = new ArrayList<>();
        allImages.add(contentPic1);
        allImages.add(contentPic2);
        allImages.add(contentPic3);
        allImages.add(contentPic4);
        allImages.add(contentPic5);
        allImages.add(contentPic6);
    }

    @OnClick({R.id.backHome, R.id.clearAll, R.id.submitAll})
    public void onSubClicked(View view) {
        switch (view.getId()) {
            case R.id.backHome:
                finish();
                break;
            case R.id.clearAll:
                clearAllMessage();
                break;
            case R.id.submitAll:
                subAllMessage();
                break;
        }
    }

    public void clearAllMessage() {
        newTitle.setText("");
        newTitle.setHint("...");
        newContent.setText("");
        picUrlList.clear();
        for (int i = 0; i < 6; i++) {
            allImages.get(i).setImageResource(R.mipmap.addpic);
            allImages.get(i).setVisibility(View.INVISIBLE);
        }
        allImages.get(0).setVisibility(View.VISIBLE);
    }

    public void subAllMessage() {
        if (picUrlList.size() < 1) {
            Toast.makeText(this, "请添加最少一张图片", Toast.LENGTH_SHORT).show();
            return;
        }
        News myNews = new News();
        myNews.setTitle(newTitle.getText().toString());
        myNews.setBodytxt(newContent.getText().toString());
        myNews.setHots(10);
        myNews.setTypes("1");
        myNews.setMainpic(picUrlList.get(0));
        myNews.addAll("bodypic", picUrlList);
        myNews.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    clearAllMessage();
                    Toast.makeText(releaseActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(releaseActivity.this, "发布失败、请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @OnClick({R.id.contentPic1, R.id.contentPic2, R.id.contentPic3, R.id.contentPic4, R.id.contentPic5, R.id.contentPic6})
    public void onPicClicked(View view) {
        switch (view.getId()) {
            default:
                choosePictureFromCal();
                break;
        }
    }
    public void choosePictureFromCal(){
        PictureSelector.create(releaseActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                .selectPicture(false, 200, 200, 1, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                Log.i("yes---", "onActivityResult: " + picturePath);
                saveImgToBmob(picturePath);
                displayPic(picturePath);

            }else {
                Toast.makeText(this, "未选择图片~", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void saveImgToBmob(String imagPath) {
        final BmobFile bmobFile=new BmobFile(new File(imagPath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    Log.i("bmob scuess", "done: "+bmobFile.getFileUrl());
                    picUrlList.add(bmobFile.getFileUrl());
                    if (picUrlList.size()<6){
                        allImages.get(picUrlList.size()).setVisibility(View.VISIBLE);}
                }else
                {
                    Log.i("bmob error", "done: error");
                }
            }
        });
    }

    private void displayPic(String imagPath) {

        if (imagPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagPath);
            int i=picUrlList.size();
            allImages.get(i).setImageBitmap(bitmap);

        } else {
            Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
        }
    }

}
