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



public class MyFragment3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    public MyFragment3() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content, container, false);
        TextView txt_content = (TextView) view.findViewById(R.id.tex_content);
        txt_content.setText("第san个Fragment");
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();

    }


}
