package com.itheima.quickindex;

import android.graphics.Paint;
import android.graphics.Rect;

public class TextHeightUtil {

    /**
     * 返回文本的高度
     * @param paint
     * @param text
     * @return
     */
    public static int getTextHeigth(Paint paint,String text) {
	Rect bounds=new Rect();
	paint.getTextBounds(text, 0, text.length(), bounds);
	return bounds.height();
    }
}
