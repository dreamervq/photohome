package drysister.itcast.cn.photohome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import drysister.itcast.cn.photohome.adapter.MyFragmentPagerAdapter;
import drysister.itcast.cn.photohome.tool.SharedHelper;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    @BindView(R.id.txt_topbar)
    TextView txtTopbar;
    @BindView(R.id.rb_find)
    RadioButton rbFind;
    @BindView(R.id.rb_pics)
    RadioButton rbPics;
    @BindView(R.id.rb_me)
    RadioButton rbMe;
    @BindView(R.id.rg_tab_bar)
    RadioGroup rgTabBar;
    @BindView(R.id.vpager)
    ViewPager vpager;
    @BindView(R.id.addNews)
    ImageView addNews;
    private MyFragmentPagerAdapter mAdapter;
    private SharedHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindView();
        rbFind.setChecked(true);
        Bmob.initialize(this, "65768cbe896b0c1626487bf6aefd6fdd");
    }

    private void bindView() {
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
        rgTabBar.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {
        if (i == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    rbFind.setChecked(true);
                    txtTopbar.setText("首页");
                    addNews.setVisibility(View.VISIBLE);
                    break;
                case PAGE_TWO:
                    rbPics.setChecked(true);
                    txtTopbar.setText("图库");
                    addNews.setVisibility(View.VISIBLE);
                    break;
                case PAGE_THREE:
                    rbMe.setChecked(true);
                    txtTopbar.setText("我的");
                    addNews.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_find:
                vpager.setCurrentItem(PAGE_ONE);
                txtTopbar.setText("首页");
                addNews.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_pics:
                vpager.setCurrentItem(PAGE_TWO);
                addNews.setVisibility(View.VISIBLE);
                txtTopbar.setText("图库");
                break;
            case R.id.rb_me:
                vpager.setCurrentItem(PAGE_THREE);
                txtTopbar.setText("我的");
                addNews.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @OnClick(R.id.addNews)
    public void onAddNewsViewClicked() {
        sh = new SharedHelper(getApplicationContext());
        Map<String, String> data = sh.read();
        if (data.get("id") != null&&data.get("id")!="") {
            Intent intent = new Intent(MainActivity.this, releaseActivity.class);
            startActivity(intent);
        } else {

            Toast.makeText(this, "请登录！享受更多服务", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
