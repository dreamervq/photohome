package drysister.itcast.cn.photohome.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import drysister.itcast.cn.photohome.NewsDetailActivity;
import drysister.itcast.cn.photohome.R;
import drysister.itcast.cn.photohome.bean.News;

public class MyRecycleradapter extends RecyclerView.Adapter<MyRecycleradapter.MyViewHolder> implements View.OnClickListener {
    private List<News> mList;
    private Context context;
  private OnClickListener onClickListener;

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MyRecycleradapter(@NonNull Context context, List<News> list) {
       this.context=context;
       this.mList=list;
    }

    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder myViewHolder;
        View inflate = LayoutInflater.from(context).inflate(R.layout.list_item_post, viewGroup, false);
        myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        News temp=mList.get(i);
      //  holder.vhimg.setTag(i);
     //   holder.title.setTag(i);
      //  holder.hots.setTag(i);
       // holder.updatetimes.setTag(i);

        Glide.with(context)
                .load(temp.getMainpic())
                .placeholder(R.drawable.loading)
                .into(holder.vhimg);
        holder.title.setText(temp.getTitle());
        holder.hots.setText(Integer.toString(temp.getHots()));
        holder.updatetimes.setText(temp.getUpdatedAt());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               if (onClickListener!=null){
                   onClickListener.onClick(holder.itemView,holder.getLayoutPosition());
               }

            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView vhimg;
        TextView title;
        TextView hots;
        TextView updatetimes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vhimg=itemView.findViewById(R.id.vhimg);
            title=itemView.findViewById(R.id.title);
            hots=itemView.findViewById(R.id.hots);
            updatetimes=itemView.findViewById(R.id.updatetimes);
        }
    }
    public interface OnClickListener{
        void onClick(View itemView,int position);
        void onLongClick(View itemView,int position);
    }

}
