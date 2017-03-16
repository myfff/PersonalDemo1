package com.example.administrator.personaldemo1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class MyRecyclerAdater extends RecyclerView.Adapter<MyRecyclerAdater.myViewHolder> {
    private  List<String> mData;

    public static  class  myViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;


        public myViewHolder(View itemView) {
            super(itemView);
        }
    }

    public MyRecyclerAdater(List<String> mData) {
        this.mData=mData;
    }

    /**创建视图
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=View.inflate(parent.getContext(),R.layout.itemrecycler,null);
        myViewHolder holder=new myViewHolder(view);

        //在此还可以设置其的点击事件
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return holder;
    }

    /**绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.textView.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
