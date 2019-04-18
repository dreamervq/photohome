package drysister.itcast.cn.photohome.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import drysister.itcast.cn.photohome.MainActivity;
import drysister.itcast.cn.photohome.fragment.MyFragment1;
import drysister.itcast.cn.photohome.fragment.MyFragment2;
import drysister.itcast.cn.photohome.fragment.MyFragment3;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT=3;
    private MyFragment1 myFragment1=null;
    private MyFragment2 myFragment2=null;
    private MyFragment3 myFragment3=null;
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        myFragment1=new MyFragment1();
        myFragment2=new MyFragment2();
        myFragment3=new MyFragment3();
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        switch (i){
            case MainActivity.PAGE_ONE:
                fragment = myFragment1;
                break;
            case MainActivity.PAGE_TWO:
                fragment = myFragment2;
                break;
            case MainActivity.PAGE_THREE:
                fragment = myFragment3;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

}
