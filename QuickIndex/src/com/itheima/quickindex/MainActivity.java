package com.itheima.quickindex;

import java.util.ArrayList;
import java.util.Collections;

import com.itheima.quickindex.QuickIndexBar.OnTouchLetterListener;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private QuickIndexBar quickIndexBar;
    private ListView listView;
    private ArrayList<Friend> friends = new ArrayList<Friend>();
    private TextView tv_toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	tv_toast = (TextView) findViewById(R.id.tv_toast);
	listView = (ListView) findViewById(R.id.listview);
	quickIndexBar = (QuickIndexBar) findViewById(R.id.quickIndexBar);

	fillList();
	// 对集合进行排序
	Collections.sort(friends);

	listView.setAdapter(new MyAdapter(this, friends));

	// 快速索引设置字母监听
	quickIndexBar.setOnTouchLetterListener(new OnTouchLetterListener() {
	    @Override
	    public void onTouchLetter(String letter) {
		Log.e("tag", letter);
		// 在接收到所触摸的字母时，遍历集合寻找字母，当找到第一个时，将listview的的条目设置到指定
		for (int i = 0; i < friends.size(); i++) {
		    // 1取出集合i角标的拼音
		    String firstWord = friends.get(i).pinyin.substring(0, 1);
		    // 2与触摸的字幕letter比较
		    if (letter.equals(firstWord)) {
			listView.setSelection(i);
			break;// 在找到第一个时，就退出循环
		    }
		}

		showCurrentWord(letter);
	    }
	});

	// 1先设置控件动画为缩小的不可见模式
	ViewHelper.setScaleX(tv_toast, 0f);
	ViewHelper.setScaleY(tv_toast, 0f);
    }

    private Handler handler = new Handler();

    /**
     * 显示当前字母
     */
    protected void showCurrentWord(String letter) {
	// 每次调用此方法时，将消息清空
	handler.removeCallbacksAndMessages(null);
	// 设置文本字母
	tv_toast.setText(letter);
	// 将控件动画缩放成原来大小
	ViewPropertyAnimator.animate(tv_toast).scaleX(1f).setDuration(400)
		.start();
	ViewPropertyAnimator.animate(tv_toast).scaleY(1f).setDuration(400)
		.start();

	// 创建一个handler，来发送延迟3秒的消息，然后用属性动画将控件隐藏
	handler.postDelayed(new Runnable() {
	    @Override
	    public void run() {
		ViewPropertyAnimator.animate(tv_toast).scaleX(0f)
			.setInterpolator(new OvershootInterpolator())
			.setDuration(400).start();
		ViewPropertyAnimator.animate(tv_toast).scaleY(0f)
			.setInterpolator(new OvershootInterpolator())
			.setDuration(400).start();
	    }
	}, 1000);
    }

    private void fillList() {
	// 虚拟数据
	friends.add(new Friend("李伟"));
	friends.add(new Friend("张三"));
	friends.add(new Friend("阿三"));
	friends.add(new Friend("阿四"));
	friends.add(new Friend("段誉"));
	friends.add(new Friend("段正淳"));
	friends.add(new Friend("张三丰"));
	friends.add(new Friend("陈坤"));
	friends.add(new Friend("林俊杰1"));
	friends.add(new Friend("陈坤2"));
	friends.add(new Friend("王二a"));
	friends.add(new Friend("林俊杰a"));
	friends.add(new Friend("张四"));
	friends.add(new Friend("林俊杰"));
	friends.add(new Friend("王二"));
	friends.add(new Friend("王二b"));
	friends.add(new Friend("赵四"));
	friends.add(new Friend("杨坤"));
	friends.add(new Friend("赵子龙"));
	friends.add(new Friend("杨坤1"));
	friends.add(new Friend("李伟1"));
	friends.add(new Friend("宋江"));
	friends.add(new Friend("宋江1"));
	friends.add(new Friend("李伟3"));
    }

}
