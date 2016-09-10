package com.itheima.quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class QuickIndexBar extends View {

    private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H",
		"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
		"V", "W", "X", "Y", "Z" };
    private int width;
    private Paint paint;
    private float cellHeight;
    public QuickIndexBar(Context context) {
	super(context);
	init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr,
	    int defStyleRes) {
	super(context, attrs, defStyleAttr, defStyleRes);
	init();
    }

    @Override//当尺寸改变此方法调用，在onMeasured之后调用
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取控件的宽度
        width = getMeasuredWidth();
        //获取分割成26份的高度
        cellHeight = getMeasuredHeight()/indexArr.length;
    }

    private void init() {
	paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
	paint.setTextSize(DensityUtils.dp2px(getContext(), 16));//设置画笔写文本的尺寸
	paint.setColor(Color.WHITE);//设置画笔颜色
	paint.setTextAlign(Align.CENTER);//设置从文本底边的正中间开始绘制
    }
    
    @Override//绘制控件上的内容
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将字母数组绘制在控件上
        for (int i = 0; i < indexArr.length; i++) {
//            int textHeight=TextHeightUtil.getTextHeigth(paint, indexArr[i]);
            float x=width/2;
            float y=cellHeight/2+paint.getTextSize()/2+i*cellHeight;
            
            //根据lastIndex去修改画笔颜色，lastIndex其实就相当于是当前手按下的index值
            paint.setColor(lastIndex==i?Color.BLACK:Color.WHITE);
            canvas.drawText(indexArr[i], x, y, paint);
	}
        
    }
    private int lastIndex=-1;//初始化上一个按下的角标索引
    //根据触控获取屏幕上的字母
    @Override
    public boolean onTouchEvent(MotionEvent event) {
	switch (event.getAction()) {
	//因为按下和移动的时候都要获取到字母，所以响应同一代码
	case MotionEvent.ACTION_DOWN:
	case MotionEvent.ACTION_MOVE:
	//可以利用整型相除时，会舍弃小数点的特性，来计算出移动的区域的index（即：按下或移动的y坐标值/字母区域的高度(单元格高度)）
	    int index=(int) (event.getY()/cellHeight);
	    //当当前移动时的index不等于上一次的index时，才去记录值
	    if (index!=lastIndex) {
//		Log.e("tag", indexArr[index]);
		//在此处设置监听回调，先判断index的范围在0-25之间
		if (mListener!=null&&index>=0&&index<indexArr.length) {
		    mListener.onTouchLetter(indexArr[index]);
		}
	    }
	    //将当前的index赋值给lastIndex
	    lastIndex=index;
	    break;
	case MotionEvent.ACTION_UP:
	    //当手抬起的时候，要将lastIndex重置
	    lastIndex=-1;
	    break;
	}
	//得到了当前的index之后，调用重绘的方法,根据index去重绘界面
	invalidate();
        return true;//自己消费事件
    }
    
    
    //设置监听回调
    private OnTouchLetterListener mListener;
    
    public void setOnTouchLetterListener(OnTouchLetterListener listener) {
	this.mListener=listener;
    }
    
    public interface OnTouchLetterListener{
	//字母监听
	void onTouchLetter(String letter);
    }
}
