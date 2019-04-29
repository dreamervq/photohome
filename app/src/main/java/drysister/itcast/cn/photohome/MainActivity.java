package drysister.itcast.cn.photohome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import drysister.itcast.cn.photohome.adapter.MyFragmentPagerAdapter;

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
    private MyFragmentPagerAdapter mAdapter;

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
                    break;
                case PAGE_TWO:
                    rbPics.setChecked(true);
                    txtTopbar.setText("图库");
                    break;
                case PAGE_THREE:
                    rbMe.setChecked(true);
                    txtTopbar.setText("我的");
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
                break;
            case R.id.rb_pics:
                vpager.setCurrentItem(PAGE_TWO);
                txtTopbar.setText("图库");
                break;
            case R.id.rb_me:
                vpager.setCurrentItem(PAGE_THREE);
                txtTopbar.setText("我的");
                break;
        }
    }

    @OnClick(R.id.addNews)
    public void onAddNewsViewClicked() {
        Intent intent=new Intent(MainActivity.this,releaseActivity.class);
        startActivity(intent);
    }
}
