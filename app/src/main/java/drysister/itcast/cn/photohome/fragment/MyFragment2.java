package drysister.itcast.cn.photohome.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import drysister.itcast.cn.photohome.R;

public class MyFragment2 extends Fragment {


    public MyFragment2() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
