package com.itheima.quickindex;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter implements ListAdapter {

    private Context context;
    private ArrayList<Friend> list;

    public MyAdapter(Context context, ArrayList<Friend> list) {
	this.context = context;
	this.list = list;
    }

    @Override
    public int getCount() {
	return list.size();
    }

    @Override
    public Friend getItem(int position) {
	return list.get(position);
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	if (convertView==null) {
	    convertView=View.inflate(context, R.layout.adapter_friend, null);
	}
	ViewHolder holder=ViewHolder.getViewHolder(convertView);
	String currentWord = getItem(position).pinyin.substring(0,1);
	//判断当前条目和上一个条目是否相等，如果相等就将当前条目隐藏
	if (position>0) {//首先确保position大于0
	    if (currentWord.equals(getItem(position-1).pinyin.substring(0, 1))) {
		//如果当前的item的拼音与上一个条目的拼音一样，就将其隐藏
		holder.first_word.setVisibility(View.GONE);
	    }else {
		//否则就将其显示，并设置文本
		 holder.first_word.setVisibility(View.VISIBLE);
		 holder.first_word.setText(currentWord);
	    }
	}else {
	    //等于0的时候要将first_word控件显示，并设置数据
	    holder.first_word.setVisibility(View.VISIBLE);
	    holder.first_word.setText(currentWord);
	}
	holder.name.setText(getItem(position).name);
	return convertView;
    }
    
    static class ViewHolder{
	TextView first_word,name;
	public ViewHolder(View convertView) {
	    first_word=(TextView) convertView.findViewById(R.id.first_word);
	    name=(TextView) convertView.findViewById(R.id.name);
	}
	public static ViewHolder getViewHolder(View convertView) {
	    ViewHolder holder=(ViewHolder) convertView.getTag();
	    if (holder==null) {
		holder=new ViewHolder(convertView);
		convertView.setTag(holder);
	    }
	    return holder;
	}
    }

}
