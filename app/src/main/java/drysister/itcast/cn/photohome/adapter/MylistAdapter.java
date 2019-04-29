package drysister.itcast.cn.photohome.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import drysister.itcast.cn.photohome.R;
import drysister.itcast.cn.photohome.async.ImageTas;
import drysister.itcast.cn.photohome.bean.News;

public class MylistAdapter extends BaseAdapter {
    private LinkedList<News> mData;
    private Context mcontext;

    public MylistAdapter(LinkedList<News> mData, Context context) {
        this.mData = mData;
        this.mcontext = context;

    }
public void  addItem(List<News> list){
        for(int i=0;i<list.size();i++){
            mData.add(list.get(i));
        }
        this.notifyDataSetChanged();
}
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.list_item, parent, false);
            holder=new ViewHolder();

          holder.img_icon = (ImageView) convertView.findViewById(R.id.imgtou);
            holder.txt_name = (TextView) convertView.findViewById(R.id.name);
            holder.txt_comment = (TextView) convertView.findViewById(R.id.says);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

      // holder.img_icon.setImageBitmap(imgBit.get(position));

        Glide
                .with(mcontext)
                .load(mData.get(position).getMainpic())
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(holder.img_icon);
        holder.txt_comment.setText(Integer.toString(mData.get(position).getHots()));
        holder.txt_name.setText(mData.get(position).getTitle());
        return convertView;
    }


    static class ViewHolder {
        ImageView img_icon;
        TextView txt_name;
        TextView txt_comment;
    }

}
