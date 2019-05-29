package drysister.itcast.cn.photohome.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import drysister.itcast.cn.photohome.R;
import drysister.itcast.cn.photohome.bean.News;
import drysister.itcast.cn.photohome.bean.PicList;

public class MyPicrecycleadapter extends RecyclerView.Adapter<MyPicrecycleadapter.MypicViewHolder> implements View.OnClickListener{
    private List<PicList> mList;
    private Context context;
    private MyRecycleradapter.OnClickListener onClickListener;

    public MyPicrecycleadapter(List<PicList> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public MyRecycleradapter.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(MyRecycleradapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MypicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MypicViewHolder mypicViewHolder;
        View inflate = LayoutInflater.from(context).inflate(R.layout.list_item_piclists, viewGroup, false);
        mypicViewHolder = new MypicViewHolder(inflate);
        return mypicViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MypicViewHolder holder, int i) {
        PicList temp=mList.get(i);


        Glide.with(context)
                .load(temp.getPicurl())
                .placeholder(R.drawable.loading)
                .into(holder.pic);
        holder.title.setText(temp.getTitle());
        holder.views.setText(Integer.toString(temp.getViews()));
        holder.dates.setText(temp.getUpdatedAt().substring(0,10));
        holder.author.setText(temp.getAuthor().getUsername());
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

    @Override
    public void onClick(View v) {

    }

    class MypicViewHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView title;
        TextView author;
        TextView dates;
        TextView views;
        public MypicViewHolder(@NonNull View itemView) {
            super(itemView);
            pic=itemView.findViewById(R.id.pic);
            title=itemView.findViewById(R.id.title);
            author=itemView.findViewById(R.id.author);
            dates=itemView.findViewById(R.id.dates);
            views=itemView.findViewById(R.id.views);
        }
    }
    public interface OnClickListener{
        void onClick(View itemView,int position);
        void onLongClick(View itemView,int position);
    }
}
